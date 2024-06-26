import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Moves and stores all necessary pieces to make a chess move.
 * Allows the move to be undone and redone as many times as you would like.
 * There is no move validation, it will always make the move. */
public class Move {
    private final Chessboard board;
    private final Piece piece;
    private final Piece previousPawn;
    private final int x;
    private final int y;
    private final List<MoveValue> movesToUndo = new ArrayList<>(3);
    private final List<MoveValue> movesMade;
    private boolean undone = false;
    private boolean taking = false;
    private boolean notHadFirstMove = false;
    private boolean wasPreviousPawnPassantable = false;

    /**
     * Initialises move and then moves the piece to the new location.
     * Used so it can undo the first move condition on the previous piece if necessary.
     */
    public Move(int x, int y, @NotNull Piece piece, @Nullable Piece previousPiece, Chessboard board){
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.board = board;
        if(previousPiece instanceof Pawn) // only need to track it if it's a pawn
            previousPawn = previousPiece;
        else
            previousPawn = new Blank(0,0);
        movesMade = piece.getMoves(x, y);
        makeMove();
    }

    /**
     * Moves the relevant pieces with no validation.
     * Saves the moves made so they can be undone.
     */
    public void makeMove(){
        undone = false;
        movesToUndo.clear();
        wasPreviousPawnPassantable = previousPawn.hadFirstMove();
        previousPawn.undoMoveCondition();
        for(MoveValue move : movesMade){
            if(!board.isSquareBlank(move.newX(), move.newY()))
                takePiece(move);
            movesToUndo.add(MoveValue.createStationaryMove(move.piece()));
            movePiece(move.piece(), move);
        }
        notHadFirstMove = !piece.hadFirstMove();
        piece.firstMove();
    }

    private void movePiece(@NotNull Piece pieceToMove, @NotNull MoveValue move){
        board.setSquare(pieceToMove.getX(), pieceToMove.getY(), new Blank(pieceToMove.getX(), pieceToMove.getY()));
        board.setSquare(move.newX(), move.newY(), pieceToMove);
        pieceToMove.setX(move.newX());
        pieceToMove.setY(move.newY());
    }

    private void takePiece(@NotNull MoveValue move){
        if(move.newX() == move.piece().getX() && move.newY() == move.piece().getY()) // promotion
            board.addPiece(move.piece());
        else
            taking = true;
        Piece pieceTaken = board.getPiece(move.newX(), move.newY());
        movesToUndo.add(new MoveValue(pieceTaken, move.newX(), move.newY()));
        board.removePiece(pieceTaken);
    }

    /**
     * Undoes the move that was just made.
     * The board is left in the exact same state as before being run.
     */
    public void undo(){
        undone = true;
        if(wasPreviousPawnPassantable)
            previousPawn.firstMove();
        for(MoveValue move : movesToUndo.reversed()){
            addOrRemovePiece(move.piece(), move);
            movePiece(move.piece(), move);
        }
        if(notHadFirstMove)
            piece.undoMoveCondition();
    }

    /** Checks if a piece was taken or if it was a promotion and restores it. */
    private void addOrRemovePiece(@NotNull Piece pieceToMove, @NotNull MoveValue move){
        if(pieceToMove.getX() != move.newX() || pieceToMove.getY() != move.newY())
            return;
        // a piece moving to the same spot only occurs as the last move when it's a promotion
        if(move == movesToUndo.getLast())
            board.removePiece(pieceToMove);
        else
            board.addPiece(move.piece());
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public Piece getPiece() {return piece;}
    public List<MoveValue> getMovesToUndo() {return undone ? movesMade : movesToUndo;}
    public boolean hasTaken() {return taking;}
}
