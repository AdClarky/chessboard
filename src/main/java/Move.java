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
    private final Piece piece;
    private final Piece previousPawn;
    private final int x;
    private final int y;
    private final List<MoveValue> movesToUndo = new ArrayList<>(3);
    private final List<MoveValue> movesMade;
    private boolean undone = false;
    private boolean taking = false;
    private boolean notHadFirstMove = true;
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
        movesMade = piece.getMoves(new ChessLogic(board), x, y);
        makeMove();
    }

    /**
     * Moves the relevant pieces with no validation.
     * Saves the moves made so they can be undone.
     */
    public void makeMove(){
        undone = false;
        movesToUndo.clear();
        for(MoveValue move : movesMade){
            if(!board.isSquareBlank(move.newX(), move.newY()))
                takePiece(move);
            movesToUndo.add(MoveValue.createStationaryMove(move.piece()));
            board.movePiece(move);
        }
        wasPreviousPawnPassantable = previousPawn.hadFirstMove();
        previousPawn.undoMoveCondition();
        notHadFirstMove = !piece.hadFirstMove();
        piece.firstMove();
    }

    private void takePiece(@NotNull MoveValue move){
        if(move.isPieceInSamePosition()) // promotion
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
        for(MoveValue move : movesToUndo.reversed()){
            addOrRemovePiece(move.piece(), move);
            board.movePiece(move);
        }
        if(notHadFirstMove)
            piece.undoMoveCondition();
        if(wasPreviousPawnPassantable)
            previousPawn.firstMove();
    }

    /** Checks if a piece was taken or if it was a promotion and restores it. */
    private void addOrRemovePiece(@NotNull Piece pieceToMove, @NotNull MoveValue move){
        if(!move.isPieceInSamePosition())
            return;
        // a piece moving to the same spot only occurs as the last move when it's a promotion
        if(move == movesToUndo.getLast())
            board.removePiece(pieceToMove);
        else
            board.addPiece(pieceToMove);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPieceX(){
        return piece.getX();
    }

    public int getPieceY(){
        return piece.getY();
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isPieceAPawn(){
        return piece instanceof Pawn;
    }

    public boolean isPieceColourBlack(){
        return piece.getColour() == PieceColour.BLACK;
    }

    public List<MoveValue> getMovesToUndo() {
        return undone ? movesToUndo : movesMade;
    }

    public boolean hasTaken() {
        return taking;
    }
}
