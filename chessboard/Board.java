package chessboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Board {
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);
    private int currentTurn = Piece.WHITE_PIECE;
    private final Piece[][] board  =  new Piece[8][8];
    private final King whiteKing;
    private final King blackKing;
    private ArrayList<Move> movesMade;
    private final List<Move> tempMoves = new ArrayList<>(3);
    private final ArrayList<Piece> tempPieces = new ArrayList<>(2);
    private Pawn lastPawn;

    public Board(){
        board[0][0] = new Rook(0, 0, Piece.WHITE_PIECE);
        board[0][1] = new Knight(1, 0, Piece.WHITE_PIECE);
        board[0][2] = new Bishop(2, 0, Piece.WHITE_PIECE);
        board[0][3] = new Queen(3, 0, Piece.WHITE_PIECE);
        whiteKing = new King(4, 0, Piece.WHITE_PIECE);
        board[0][4] = whiteKing;
        board[0][5] = new Bishop(5, 0, Piece.WHITE_PIECE);
        board[0][6] = new Knight(6, 0, Piece.WHITE_PIECE);
        board[0][7] = new Rook(7, 0, Piece.WHITE_PIECE);
        board[7][0] = new Rook(0, 7, Piece.BLACK_PIECE);
        board[7][1] = new Knight(1, 7, Piece.BLACK_PIECE);
        board[7][2] = new Bishop(2, 7, Piece.BLACK_PIECE);
        board[7][3] = new Queen(3, 7, Piece.BLACK_PIECE);
        blackKing = new King(4, 7, Piece.BLACK_PIECE);
        board[7][4] = blackKing;
        board[7][5] = new Bishop(5, 7, Piece.BLACK_PIECE);
        board[7][6] = new Knight(6, 7, Piece.BLACK_PIECE);
        board[7][7] = new Rook(7, 7, Piece.BLACK_PIECE);
        for(int x = 0; x < 8; x++){
            board[6][x] = new Pawn(x, 6, Piece.BLACK_PIECE);
            board[1][x] = new Pawn(x, 1, Piece.WHITE_PIECE);
        }
    }

    public Piece getPiece(int x, int y){
        return board[y][x];
    }

    public int getCurrentTurn(){return currentTurn;}

    private King getKing(){
        if(currentTurn == Piece.BLACK_PIECE){
            return blackKing;
        }
        return whiteKing;
    }

    public boolean isInCheck(int newX, int newY, Piece pieceToCheck){
        tempMove(newX, newY, pieceToCheck);
        King king = getKing();
        Coordinate kingPos = new Coordinate(king.getX(), king.getY());
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

    public void moveWithValidation(int oldX, int oldY, int newX, int newY){
        Piece piece = getPiece(oldX, oldY);
        if(!piece.getPossibleMoves(this).contains(new Coordinate(newX, newY))) // if invalid move
            return;
        movesMade = getMoves(oldX, oldY, newX, newY);
        for(Move move : movesMade){
            movePiece(move.getOldX(), move.getOldY(), move.getNewX(), move.getNewY());
        }
        piece.firstMove();
        if(lastPawn != null)
            lastPawn.setCanBePassanted(false);
        if(piece instanceof Pawn pawn) {
            lastPawn = pawn;
        }
        else {
            lastPawn = null;
        }
        notifyBoardChanged(oldX, oldY, newX, newY);
        nextTurn();
    }

    public void movePiece(int oldX, int oldY, int newX, int newY){
        if(oldX == newX && oldY == newY){ // if a promotion
            if(oldY == 0)
                board[oldY][oldX] = new Queen(oldX, oldY, Piece.BLACK_PIECE);
            else
                board[oldY][oldX] = new Queen(oldX, oldY, Piece.WHITE_PIECE);
        }else {
            board[newY][newX] = board[oldY][oldX];
            board[oldY][oldX] = null;
        }
        board[newY][newX].setX(newX);
        board[newY][newX].setY(newY);
    }

    private void nextTurn(){
        if(currentTurn == Piece.WHITE_PIECE)
            currentTurn = Piece.BLACK_PIECE;
        else
            currentTurn = Piece.WHITE_PIECE;
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
                    board[move.getNewY()][move.getNewX()] = new Queen(move.getNewX(), move.getNewY(), Piece.BLACK_PIECE);
                else
                    board[move.getNewY()][move.getNewX()] = new Queen(move.getNewX(), move.getNewY(), Piece.WHITE_PIECE);
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
