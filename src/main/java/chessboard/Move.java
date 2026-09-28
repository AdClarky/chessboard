package chessboard;

import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import common.Pieces;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Moves and stores all necessary pieces to make a chess move.
 * Allows the move to be undone and redone as many times as you would like.
 * There is no move validation, it will always make the move.
 */
class Move {
    private final Chessboard oldBoard;
    private final Chessboard newBoard;
    private final Coordinate oldPos;
    private final Coordinate newPos;
    private final Pieces piece;
    private final PieceColour pieceColour;
    private final List<MoveValue> movesMade;
    private final Pieces promotionPiece;
    private Pieces pieceTaken = null;

    public Move(Chessboard oldBoard, Coordinate oldPos, Coordinate newPos) {
        this(oldBoard, oldPos, newPos, Pieces.QUEEN);
    }

    public Move(Chessboard oldBoard, Coordinate oldPos, Coordinate newPos, Pieces promotionPiece) {
        this.oldPos = oldPos;
        this.newPos = newPos;
        this.oldBoard = oldBoard;
        piece = oldBoard.getPiece(oldPos);
        pieceColour = oldBoard.getColour(oldPos);
        movesMade = getMoves();
        this.promotionPiece = promotionPiece;
        newBoard = makeMove();
    }

    private List<MoveValue> getMoves() {
        if (piece == Pieces.KING && Math.abs(oldPos.x() - newPos.x()) == 2) {
            int rookSquare = 0;
            int newRookSquare = 3;
            if (newPos.x() == 6) {
                rookSquare = 7;
                newRookSquare = 5;
            }
            return List.of(new MoveValue(oldPos, newPos), new MoveValue(new Coordinate(rookSquare, oldPos.y()), new Coordinate(newRookSquare, oldPos.y())));
        } else if (piece == Pieces.PAWN) {
            if (oldBoard.isSquareBlank(newPos) && oldPos.x() != newPos.x() && oldPos.y() != newPos.y())
                return List.of(new MoveValue(new Coordinate(newPos.x(), oldPos.y()), newPos), new MoveValue(oldPos, newPos));
            else if (newPos.y() == 7 || newPos.y() == 0)
                return List.of(new MoveValue(oldPos, newPos), new MoveValue(newPos, newPos));
        }
        return List.of(new MoveValue(oldPos, newPos));
    }

    /**
     * Moves the relevant pieces with no validation.
     * Saves the moves made so they can be undone.
     */
    private Chessboard makeMove() {
        Chessboard newBoard = oldBoard;
        if (piece == Pieces.PAWN && Math.abs(newPos.y() - oldPos.y()) == 2)
            newBoard = newBoard.setEnPassantSquare(newPos);
        else
            newBoard = newBoard.setEnPassantSquare(null);
        newBoard = newBoard.removeCastlingRight(oldPos);
        newBoard = newBoard.removeCastlingRight(newPos);
        for (MoveValue move : movesMade) {
            if (!newBoard.isSquareBlank(move.newPos()))
                newBoard = takePiece(newBoard, move);
            newBoard = newBoard.movePiece(move);
        }
        newBoard = newBoard.nextTurn();
        return newBoard;
    }

    private Chessboard takePiece(Chessboard newBoard, @NotNull MoveValue move) {
        if (move.isPieceInSamePosition()) {// promotion
            return newBoard.promotion(move.newPos(), promotionPiece);
        }
        if (pieceTaken == null)
            pieceTaken = newBoard.getPiece(move.newPos());
        return newBoard;
    }


    public Coordinate getOldPos() {
        return oldPos;
    }

    public Coordinate getNewPos() {
        return newPos;
    }

    public Pieces getPiece() {
        return piece;
    }

    public PieceColour getColour() {
        return pieceColour;
    }

    public boolean isPieceColourBlack() {
        return pieceColour == PieceColour.BLACK;
    }

    public boolean isTaking() {
        return pieceTaken != null;
    }

    public Chessboard getOldBoard() {
        return oldBoard;
    }

    public Chessboard getNewBoard() {
        return newBoard;
    }

    public boolean isHalfMove(){
        return isTaking() || piece == Pieces.PAWN;
    }
}
