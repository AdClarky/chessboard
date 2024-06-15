package ChessBoard;

import java.util.ArrayList;

public class Board {
    private final ArrayList<BoardListener> boardListeners = new ArrayList<>(1);
    private int turn = Piece.DOWN;
    private final Piece[][] board  =  new Piece[8][8];
    private final King whiteKing;
    private final King blackKing;
    private final ArrayList<Move> tempMoves = new ArrayList<>(3);
    private Pawn passantable;

    public Board(){
        board[0][0] = new Rook(0, 0, Rook.white, Piece.DOWN);
        board[0][1] = new Knight(1, 0, Knight.white, Piece.DOWN);
        board[0][2] = new Bishop(2, 0, Bishop.white, Piece.DOWN);
        board[0][3] = new Queen(3, 0, Queen.white, Piece.DOWN);
        whiteKing = new King(4, 0, King.white, Piece.DOWN);
        board[0][4] = whiteKing;
        board[0][5] = new Bishop(5, 0, Bishop.white, Piece.DOWN);
        board[0][6] = new Knight(6, 0, Knight.white, Piece.DOWN);
        board[0][7] = new Rook(7, 0, Rook.white, Piece.DOWN);
        board[7][0] = new Rook(0, 7, Rook.black, Piece.UP);
        board[7][1] = new Knight(1, 7, Knight.black, Piece.UP);
        board[7][2] = new Bishop(2, 7, Bishop.black, Piece.UP);
        board[7][3] = new Queen(3, 7, Queen.black, Piece.UP);
        blackKing = new King(4, 7, King.black, Piece.UP);
        board[7][4] = blackKing;
        board[7][5] = new Bishop(5, 7, Bishop.black, Piece.UP);
        board[7][6] = new Knight(6, 7, Knight.black, Piece.UP);
        board[7][7] = new Rook(7, 7, Rook.black, Piece.UP);
        for(int x = 0; x < 8; x++){
            board[6][x] = new Pawn(x, 6, Pawn.black, Piece.UP);
            board[1][x] = new Pawn(x, 1, Pawn.white, Piece.DOWN);
        }
    }

    public Piece getPiece(int x, int y){
        return board[y][x];
    }

    public int getTurn(){return turn;}

    public King getKing(){
        if(turn == Piece.UP){
            return blackKing;
        }
        return whiteKing;
    }

    public Pawn getPassantable(){return passantable;}

    public boolean inCheck(int x, int y, Piece pieceToCheck){
        tempMove(x, y, pieceToCheck);
        Coordinate kingPos = new Coordinate(getKing().getX(), getKing().getY());
        for(Piece[] row : board){
            for(Piece piece : row){
                if(piece == null || piece.getDirection() == pieceToCheck.getDirection())
                    continue;
                if(piece.getPossibleMoves(this).contains(kingPos)) {
                    undoTempMove();
                    return true;
                }
            }
        }
        undoTempMove();
        return false;
    }

    public boolean moveAndValidatePiece(int x, int y, int newX, int newY){
        System.out.println(x + " " + y + " " + newX + " " + newY);
        Piece piece = getPiece(x, y);
        if(!piece.getPossibleMoves(this).contains(new Coordinate(newX, newY))){ // if invalid move
            return false;
        }
        for(Move move : getMoves(newX, newY, piece)){
            movePiece(move.getPiece().getX(), move.getPiece().getY(), move.getX(), move.getY());
        }
        passantable = null;
        if(piece instanceof King king)
            king.moved();
        if (piece instanceof Rook rook)
            rook.moved();
        if (piece instanceof Pawn pawn)
            passantable = pawn;
        nextTurn();
        return true;
    }

    public void movePiece(int oldX, int oldY, int newX, int newY){
        if(oldX == newX && oldY == newY){ // if a promotion
            if(oldY == 0)
                board[oldY][oldX] = new Queen(oldX, oldY, Queen.black, Piece.UP);
            else
                board[oldY][oldX] = new Queen(oldX, oldY, Queen.white, Piece.DOWN);
        }else {
            board[newY][newX] = board[oldY][oldX];
            board[oldY][oldX] = null;
        }
        board[newY][newX].setX(newX);
        board[newY][newX].setY(newY);
        notifyBoardChanged(oldX, oldY, newX, newY);
    }

    private void nextTurn(){
        if(turn == Piece.DOWN)
            turn = Piece.UP;
        else
            turn = Piece.DOWN;
    }

    private void tempMove(int x, int y, Piece piece){
        tempMoves.clear();
        for(Move move : getMoves(x, y, piece)){
            if(board[move.getY()][move.getX()] != null)
                tempMoves.add(new Move(board[move.getY()][move.getX()], move.getX(), move.getY()));
            tempMoves.add(new Move(move.getPiece(), move.getPiece().getX(), move.getPiece().getY()));
            board[move.getPiece().getY()][move.getPiece().getX()] = null;
            move.getPiece().setX(move.getX());
            move.getPiece().setY(move.getY());
            board[move.getY()][move.getX()] = move.getPiece();
        }
    }

    private void undoTempMove(){
        for(Move move : tempMoves.reversed()){
            board[move.getPiece().getY()][move.getPiece().getX()] = null;
            move.getPiece().setX(move.getX());
            move.getPiece().setY(move.getY());
            board[move.getY()][move.getX()] = move.getPiece();
        }
        tempMoves.clear();
    }

    private ArrayList<Move> getMoves(int x, int y, Piece piece){
        ArrayList<Move> moves = new ArrayList<>(2);
        if(piece instanceof Pawn pawn) {
            if(y == 7 || y == 0) { // if pawn promotion
                Queen queen;
                if (pawn.getDirection() == Piece.UP)
                    queen = new Queen(x, y, Queen.black, Piece.UP);
                else
                    queen = new Queen(x, y, Queen.white, Piece.DOWN);
                moves.add(new Move(pawn, x, y));
                moves.add(new Move(queen, x, y));
            }else if(x != pawn.getX() && (board[y][x] == null)){ // if passanting
                moves.add(new Move(board[y-pawn.getDirection()][x], x, y));
                moves.add(new Move(pawn, x, y));
            }else{
                moves.add(new Move(pawn, x, y));
            }
        }else if(piece instanceof King && Math.abs(x - piece.getX()) == 2){ // castling
            if(x - piece.getX() == -2) { // long castle
                moves.add(new Move(board[y][0], 3, y));
            }else {
                moves.add(new Move(board[y][7], 5, y));

            }
            moves.add(new Move(piece, x, y));
        }else{ // if not a pawn promotion
            moves.add(new Move(piece, x, y));
        }
        return moves;
    }

    public void addBoardListener(BoardListener listener){
        boardListeners.add(listener);
    }

    private void notifyBoardChanged(int oldX, int oldY, int newX, int newY){
        for(BoardListener listener : boardListeners){
            listener.boardChanged(oldX, oldY, newX, newY);
        }
    }
}
