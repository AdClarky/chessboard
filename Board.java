import java.util.ArrayList;

public class Board {
    private final ArrayList<BoardListener> boardListeners = new ArrayList<>(1);
    private Piece pieceSelected = null;
    private int turn = Piece.DOWN;
    private final Piece[][] board  =  new Piece[8][8];
    private King whiteKing;
    private King blackKing;
    private final ArrayList<Move> tempMoves = new ArrayList<>(3);

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

    private void nextTurn(){
        if(turn == Piece.DOWN)
            turn = Piece.UP;
        else
            turn = Piece.DOWN;
    }

    public int getTurn(){return turn;}

    public King getKing(){
        if(turn == Piece.UP){
            return blackKing;
        }
        return whiteKing;
    }

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

    public void tempMove(int x, int y, Piece piece){
        tempMoves.clear();
        for(Move move : getMoves(x, y, piece)){
            tempMoves.add(new Move(piece, piece.getX(), piece.getY()));
            if(board[move.getY()][move.getX()] != null)
                tempMoves.add(new Move(board[move.getY()][move.getX()], move.getX(), move.getY()));
            board[move.getPiece().getY()][move.getPiece().getX()] = null;
            move.getPiece().setX(move.getX());
            move.getPiece().setY(move.getY());
            board[move.getY()][move.getX()] = move.getPiece();
        }
    }

    public void undoTempMove(){
        for(Move move : tempMoves){
            board[move.getPiece().getY()][move.getPiece().getX()] = null;
            move.getPiece().setX(move.getX());
            move.getPiece().setY(move.getY());
            board[move.getY()][move.getX()] = move.getPiece();
        }
        tempMoves.clear();
    }

    public Piece getPiece(int x, int y){
        return board[y][x];
    }

    public void squareClicked(int x, int y){
        Piece piece = getPiece(x, y);
        if(piece == null){ // if clicked a blank square
            if(pieceSelected == null)
                return;
            movePiece(x, y, pieceSelected);
        } else if(piece.getDirection() == turn) { // if the player's piece
            setSelectedPiece(piece);
        } else if(pieceSelected != null){ // if enemy piece
            movePiece(x, y, pieceSelected);
        }
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
                pawn.setCanBePassanted(Math.abs(y - pawn.getY()) == 2);
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

    private void movePiece(int x, int y, Piece piece){
        if(!piece.getPossibleMoves(this).contains(new Coordinate(x, y))){ // if invalid move
            setSelectedPiece(null);
            return;
        }
        for(Move move : getMoves(x, y, piece)){
            board[move.getPiece().getY()][move.getPiece().getX()] = null;
            move.getPiece().setX(move.getX());
            move.getPiece().setY(move.getY());
            board[move.getY()][move.getX()] = move.getPiece();
            notifyBoardChanged(move.getPiece());
        }
        pieceSelected = null;
        nextTurn();
    }

    private void setSelectedPiece(Piece piece){
        if(pieceSelected != null){
            notifyPieceSelected(pieceSelected);
        }
        pieceSelected = piece;
        notifyPieceSelected(piece);
    }

    public void addBoardListener(BoardListener listener){
        boardListeners.add(listener);
    }

    private void notifyBoardChanged(Piece piece){
        for(BoardListener listener : boardListeners){
            listener.boardChanged(piece);
        }
    }

    private void notifyPieceSelected(Piece pieceSelected){
        for(BoardListener listener : boardListeners){
            listener.pieceSelected(pieceSelected);
        }
    }
}
