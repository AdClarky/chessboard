import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Moves and stores all necessary pieces to make a chess move.
 * Allows the move to be undone and redone as many times as you would like.
 * There is no move validation, it will always make the move. */
class Move {
    private final Chessboard board;
    private final Coordinate oldPos;
    private final Coordinate newPos;
    private final Pieces piece;
    private final PieceColour pieceColour;
    private final List<MoveValue> movesToUndo = new ArrayList<>(3);
    private final List<MoveValue> movesMade;
    private final Coordinate previousEnPassant;
    private final long castlingRights;
    private PossibleMoves possibleMoves = null;
    private boolean undone = false;
    private Pieces pieceTaken = null;
    private PieceColour pieceTakenColour = null;

    /**
     * Initialises move and then moves the piece to the new location.
     * Used so it can undo the first move condition on the previous piece if necessary.
     */
    public Move(Chessboard board, Coordinate oldPos, Coordinate newPos){
        this.oldPos = oldPos;
        this.newPos = newPos;
        this.board = board;
        piece = board.getPiece(oldPos);
        pieceColour = board.getColour(oldPos);
        previousEnPassant = board.getEnPassantSquare();
        castlingRights = board.getCastlingRights();
        movesMade = getMoves();
        makeMove();
    }

    public Move(Chessboard board, Coordinate oldPos, Coordinate newPos, PossibleMoves possibleMoves){
        this(board, oldPos, newPos);
        this.possibleMoves = possibleMoves;
    }

    private List<MoveValue> getMoves(){
        if(piece == Pieces.KING && Math.abs(oldPos.x() - newPos.x()) == 2) {
            int rookSquare = 0;
            int newRookSquare = 3;
            if(newPos.x() == 6) {
                rookSquare = 7;
                newRookSquare = 5;
            }
            return List.of(new MoveValue(oldPos, newPos), new MoveValue(new Coordinate(rookSquare, oldPos.y()), new Coordinate(newRookSquare, oldPos.y())));
        }
        else if(piece == Pieces.PAWN){
            if(board.isSquareBlank(newPos) && oldPos.x() != newPos.x() && oldPos.y() != newPos.y())
                return List.of(new MoveValue(new Coordinate(newPos.x(), oldPos.y()), newPos), new MoveValue(oldPos, newPos));
            else if(newPos.y() == 7 || newPos.y() == 0)
                return List.of(new MoveValue(oldPos, newPos), new MoveValue(newPos, newPos));
        }
        return List.of(new MoveValue(oldPos, newPos));
    }

    /**
     * Moves the relevant pieces with no validation.
     * Saves the moves made so they can be undone.
     */
    public void makeMove(){
        undone = false;
        movesToUndo.clear();
        if(piece == Pieces.PAWN && Math.abs(newPos.y() - oldPos.y()) == 2)
            board.setEnPassantSquare(newPos);
        else
            board.setEnPassantSquare(null);
        board.removeCastlingRight(oldPos);
        for(MoveValue move : movesMade){
            if(!board.isSquareBlank(move.newPos()))
                takePiece(move);
            movesToUndo.add(new MoveValue(move.newPos(), move.oldPos()));
            board.movePiece(move);
        }
        board.nextTurn();
    }

    private void takePiece(@NotNull MoveValue move){
        if(move.isPieceInSamePosition()) {// promotion
            board.promotion(move.newPos());
            return;
        }
        if(pieceTaken == null)
            pieceTaken = board.getPiece(move.newPos());
        pieceTakenColour = board.getColour(move.newPos());
        movesToUndo.add(new MoveValue(move.newPos(), move.newPos()));
    }

    public void undo(){
        undone = true;
        for(MoveValue move : movesToUndo.reversed()){
            addOrRemovePiece(pieceTaken, move);
            board.movePiece(move);
        }
        board.setEnPassantSquare(previousEnPassant);
        board.setCastlingRights(castlingRights);
        board.nextTurn();
    }

    @Nullable
    public PossibleMoves getPossibleMoves(){
        return possibleMoves;
    }

    /** Checks if a piece was taken or if it was a promotion and restores it. */
    private void addOrRemovePiece(Pieces piece, MoveValue move){
        if(!move.isPieceInSamePosition())
            return;
        // a piece moving to the same spot only occurs as the last move when it's a promotion
        if(move == movesToUndo.getLast()) {
            board.removePiece(move.newPos());
            board.addPiece(Pieces.PAWN, move.newPos(), pieceColour);
        }
        else
            board.addPiece(piece, move.newPos(), pieceTakenColour);
    }

    public Coordinate getOldPos() {
        return oldPos;
    }

    public Coordinate getNewPos() {
        return newPos;
    }

    public boolean isPieceAPawn(){
        return piece == Pieces.PAWN;
    }

    public Pieces getPiece(){
        return piece;
    }

    public PieceColour getColour(){
        return pieceColour;
    }

    public boolean isPieceColourBlack(){
        return pieceColour == PieceColour.BLACK;
    }

    public List<MoveValue> getMovesToUndo() {
        return undone ? movesToUndo : movesMade;
    }

    public boolean hasTaken() {
        return pieceTaken != null;
    }
}
