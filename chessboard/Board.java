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
    private Iterable<Move> movesMade;
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
        movesMade = piece.getMoves(newX, newY, this);
        for(Move move : movesMade){
            movePiece(move.oldX(), move.oldY(), move.newX(), move.newY());
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

    private void movePiece(int oldX, int oldY, int newX, int newY){
        if(oldX == newX && oldY == newY){ // if a promotion
            if(oldY == 0)
                board[0][oldX] = new Queen(oldX, 0, Piece.BLACK_PIECE);
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
        Iterable<Move> moves = piece.getMoves(x, y, this);
        for(Move move : moves){
            if(board[move.newY()][move.newX()] != null) { // if taking
                tempMoves.add(new Move(move.newX(), move.newY(), move.newX(), move.newY()));
                tempPieces.add(board[move.newY()][move.newX()]);
            }
            if(move.newX() == move.oldX() && move.newY() == move.oldY()){ // promotion
                tempPieces.add(board[move.newY()][move.newX()]);
                if(move.newY() == 0)
                    board[move.newY()][move.newX()] = new Queen(move.newX(), move.newY(), Piece.BLACK_PIECE);
                else
                    board[move.newY()][move.newX()] = new Queen(move.newX(), move.newY(), Piece.WHITE_PIECE);
            }
            tempMoves.add(new Move(move.oldX(), move.oldY(), move.newX(), move.newY()));
            Piece temp = getPiece(move.oldX(), move.oldY());
            board[move.oldY()][move.oldX()] = null;
            temp.setX(move.newX());
            temp.setY(move.newY());
            board[move.newY()][move.newX()] = temp;
        }
    }

    private void undoTempMove(){
        for(Move move : tempMoves.reversed()){
            if(move.oldY() == move.newY() && move.newX() == move.oldX()) {
                board[move.newY()][move.newX()] = tempPieces.getLast();
                tempPieces.removeLast();
            }
            else {
                board[move.oldY()][move.oldX()] = board[move.newY()][move.newX()];
                board[move.newY()][move.newX()] = null;
            }
            board[move.oldY()][move.oldX()].setX(move.oldX());
            board[move.oldY()][move.oldX()].setY(move.oldY());
        }
        tempMoves.clear();
    }

    /**
     * During a board change event, contains the moves performed on the board.
     * @return a list of individual moves taken to reach the new board state.
     */
    public Iterable<Move> getMovesMade(){return movesMade;}

    public void addBoardListener(BoardListener listener){
        boardListeners.add(listener);
    }

    private void notifyBoardChanged(int oldX, int oldY, int newX, int newY){
        for(BoardListener listener : boardListeners){
            listener.boardChanged(oldX, oldY, newX, newY);
        }
    }
}
