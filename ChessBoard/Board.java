package ChessBoard;

import java.util.ArrayList;

public class Board {
    private final ArrayList<BoardListener> boardListeners = new ArrayList<>(1);
    private int turn = Piece.DOWN;
    private final Piece[][] board  =  new Piece[8][8];
    private final King whiteKing;
    private final King blackKing;
    private final ArrayList<Move> movesMade = new ArrayList<>(5);
    private final ArrayList<Move> tempMoves = new ArrayList<>(3);
    private final ArrayList<Piece> tempPieces = new ArrayList<>(2);
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

    public boolean inCheck(int newX, int newY, Piece pieceToCheck){
        tempMove(newX, newY, pieceToCheck);
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

    public boolean moveAndValidatePiece(int oldX, int oldY, int newX, int newY){
        Piece piece = getPiece(oldX, oldY);
        if(!piece.getPossibleMoves(this).contains(new Coordinate(newX, newY))){ // if invalid move
            return false;
        }
        movesMade.addAll(getMoves(oldX, oldY, newX, newY));
        for(Move move : movesMade){
            movePiece(move.getOldX(), move.getOldY(), move.getNewX(), move.getNewY());
        }
        passantable = null;
        if(piece instanceof King king)
            king.moved();
        if (piece instanceof Rook rook)
            rook.moved();
        if (piece instanceof Pawn pawn)
            passantable = pawn;
        notifyBoardChanged(oldX, oldY, newX, newY);
        movesMade.clear();
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
    }

    private void nextTurn(){
        if(turn == Piece.DOWN)
            turn = Piece.UP;
        else
            turn = Piece.DOWN;
    }

    private void tempMove(int x, int y, Piece piece){
        tempMoves.clear();
        for(Move move : getMoves(piece.getX(), piece.getY(), x, y)){
            if(board[move.getNewY()][move.getNewX()] != null) { // if taking
                tempMoves.add(new Move(move.getNewX(), move.getNewY(), move.getNewX(), move.getNewY()));
                tempPieces.add(board[move.getNewY()][move.getNewX()]);
            }
            if(move.getNewX() == move.getOldX() && move.getNewY() == move.getOldY()){ // promotion
                tempPieces.add(board[move.getNewY()][move.getNewX()]);
                if(move.getNewY() == 0)
                    board[move.getNewY()][move.getNewX()] = new Queen(move.getNewX(), move.getNewY(), Queen.black, Piece.UP);
                else
                    board[move.getNewY()][move.getNewX()] = new Queen(move.getNewX(), move.getNewY(), Queen.white, Piece.DOWN);
            }
            tempMoves.add(new Move(move.getOldX(), move.getOldY(), move.getNewX(), move.getNewY()));
            Piece temp = getPiece(move.getOldX(), move.getOldY());
            board[move.getOldY()][move.getOldX()] = null;
            temp.setX(move.getNewX());
            temp.setY(move.getNewY());
            board[move.getNewY()][move.getNewX()] = temp;
        }
    }

    private void undoTempMove(){
        for(Move move : tempMoves.reversed()){
            if(move.getOldY() == move.getNewY() && move.getNewX() == move.getOldX()) {
                board[move.getNewY()][move.getNewX()] = tempPieces.getLast();
                tempPieces.removeLast();
            }
            else {
                board[move.getOldY()][move.getOldX()] = board[move.getNewY()][move.getNewX()];
                board[move.getNewY()][move.getNewX()] = null;
            }
            board[move.getOldY()][move.getOldX()].setX(move.getOldX());
            board[move.getOldY()][move.getOldX()].setY(move.getOldY());
        }
        tempMoves.clear();
    }

    /**
     * During a board change event, contains the moves performed on the board.
     * @return a list of individual moves taken to reach the new board state.
     */
    public ArrayList<Move> getMovesMade(){return movesMade;}

    /**
     * Calculates what individual moves must be made to complete a single chess move.
     * Moves that are split into multiple moves are promotion, castling and en passanting.
     * When promoting, the pawn moves first then the Queen 'takes' the pawn.
     * When passanting, the enemy pawn moves backwards then the pawn takes that.
     * @param oldX current x position
     * @param oldY current y position
     * @param newX x to move to
     * @param newY y to move to
     * @return a list of moves to be completed.
     */
    private ArrayList<Move> getMoves(int oldX, int oldY, int newX, int newY){
        Piece piece = getPiece(oldX, oldY);
        ArrayList<Move> moves = new ArrayList<>(2);
        if(piece instanceof Pawn pawn) {
            if(newY == 7 || newY == 0) { // if pawn promotion
                moves.add(new Move(oldX, oldY, newX, newY));
                moves.add(new Move(newX, newY, newX, newY));
            }else if(newX != pawn.getX() && (board[newY][newX] == null)){ // if passanting
                moves.add(new Move(newX, newY-pawn.getDirection(), newX, newY));
                moves.add(new Move(oldX, oldY, newX, newY));
            }else{
                moves.add(new Move(oldX, oldY, newX, newY));
            }
        }else if(piece instanceof King && Math.abs(newX - piece.getX()) == 2){ // castling
            if(newX - piece.getX() == -2) { // long castle
                moves.add(new Move(0, newY, 3, newY));
            }else {
                moves.add(new Move(7, newY, 5, newY));
            }
            moves.add(new Move(oldX, oldY, newX, newY));
        }else{ // normal moves
            moves.add(new Move(oldX, oldY, newX, newY));
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
