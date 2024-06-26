import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Moves and stores all necessary pieces to make a chess move.
 * Allows the move to be undone and redone as many times as you would like.
 * There is no move validation, it will always make the move.
 * This should not be used in a regular chess situation. Instead use {@see }
 */
public class Move {
    private final Board board;
    private final Piece piece;
    private final Piece previousPiece;
    private final int x;
    private final int y;
    private final List<MoveValue> movesToUndo = new ArrayList<>(3);
    private final List<MoveValue> movesMade;
    private boolean undone = false;
    private boolean taking = false;
    private boolean notHadFirstMove;


    /**
     * Initialises move and then moves the piece to the new location.
     * @param x new x position
     * @param y new y position
     * @param piece the piece being moved
     * @param board the board the piece is on
     */
    public Move(int x, int y, @NotNull Piece piece, Board board){
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.board = board;
        previousPiece = new Blank(0, 0);
        movesMade = piece.getMoves(x, y, board);
        makeMove();
    }

    /**
     * Used so it can undo the first move condition on the previous piece if necessary.
     * If the piece is not a pawn used the other constructor.
     * @param previousPiece the piece to move before this one
     * @see Move#Move(int, int, Piece, Board)
     */
    public Move(int x, int y, @NotNull Piece piece, @NotNull Pawn previousPiece, Board board){
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.board = board;
        this.previousPiece = previousPiece;
        movesMade = piece.getMoves(x, y, board);
        makeMove();
    }

    /**
     * Moves the relevant pieces with no validation.
     * Saves the moves made so they can be undone.
     */
    public void makeMove(){
        undone = false;
        previousPiece.undoMoveCondition();
        for(MoveValue move : movesMade){
            if(!board.isSquareBlank(move.newX(), move.newY())) { // taking
                if(move.newX() == move.piece().getX() && move.newY() == move.piece().getY()) // promotion
                    board.getColourPieces(move.piece()).add(move.piece());
                else
                    taking = true;
                Piece pieceTaken = board.getPiece(move.newX(), move.newY());
                movesToUndo.add(new MoveValue(pieceTaken, move.newX(), move.newY()));
                board.getColourPieces(pieceTaken).remove(pieceTaken);
            }
            Piece pieceToMove = move.piece();
            movesToUndo.add(new MoveValue(pieceToMove, pieceToMove.getX(), pieceToMove.getY()));
            board.setSquare(pieceToMove.getX(), pieceToMove.getY(), new Blank(pieceToMove.getX(), pieceToMove.getY()));
            board.setSquare(move.newX(), move.newY(), pieceToMove);
            pieceToMove.setX(move.newX());
            pieceToMove.setY(move.newY());
        }
        notHadFirstMove = !piece.hadFirstMove();
        piece.firstMove(); // if a piece has a first move constraint e.g. pawn, rook, king activates it
    }

    /**
     * Undoes the move that was just made.
     * The board returns to the exact same state as before being run.
     */
    public void undo(){
        undone = true;
        previousPiece.firstMove();
        for(MoveValue move : movesToUndo.reversed()){
            Piece pieceToMove = move.piece();
            if(pieceToMove.getX() == move.newX() && pieceToMove.getY() == move.newY()){
                // a piece moving to the same spot only occurs as the last move when it's a promotion
                if(move == movesToUndo.getLast())
                    board.getColourPieces(pieceToMove).remove(pieceToMove);
                else
                    board.getColourPieces(move.piece()).add(move.piece());
            }
            board.setSquare(pieceToMove.getX(), pieceToMove.getY(), new Blank(pieceToMove.getX(), pieceToMove.getY()));
            board.setSquare(move.newX(), move.newY(), pieceToMove);
            pieceToMove.setX(move.newX());
            pieceToMove.setY(move.newY());
        }
        if(piece instanceof Pawn pawn && pawn.hadFirstMove() || notHadFirstMove)
            piece.undoMoveCondition();
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public Piece getPiece() {return piece;}
    public List<MoveValue> getMovesToUndo() {return undone ? movesMade : movesToUndo;}
    public boolean hasTaken() {return taking;}
}
