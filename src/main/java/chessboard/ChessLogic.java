package chessboard;

import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import common.Pieces;

public class ChessLogic {
    private final Chessboard board;
    private final PossibleMoves possibleMoves;
    private final HashHistory history;
    private boolean possibleMovesCalculated = false;

    public ChessLogic(Chessboard board, HashHistory history) {
        this.board = board;
        possibleMoves = new PossibleMoves(board);
        this.history = history;
    }

    public ChessLogic(Chessboard board) {
        this.board = board;
        possibleMoves = new PossibleMoves(board);
        history = null;
    }

    public boolean isCheckmate() {
        calculatePossibleMoves();
        if (!possibleMoves.isKingInCheck())
            return false;
        return !possibleMoves.hasMoves();
    }

    public boolean isDraw() {
        if(history == null)
            return false;
        return isStalemate() ||
                isDraw50Move() ||
                history.isRepetition();
    }

    public boolean isStalemate() {
        calculatePossibleMoves();
        if (possibleMoves.isKingInCheck())
            return false;
        return !possibleMoves.hasMoves();
    }

    private boolean isDraw50Move() {
        if(history == null)
            return false;
        return history.getNumHalfMoves() >= 50;
    }

    public boolean isInvalidMove(Coordinate oldPos, Coordinate newPos) {
        calculatePossibleMoves();
        Bitboard board = possibleMoves.get(oldPos);
        if (board == null)
            return true;
        return !board.contains(newPos);
    }

    public Bitboard getPossibleMoves(Coordinate piece) {
        calculatePossibleMoves();
        return possibleMoves.get(piece);
    }

    public boolean isKingInCheck(){
        calculatePossibleMoves();
        return possibleMoves.isKingInCheck();
    }

    private void calculatePossibleMoves() {
        if (possibleMovesCalculated)
            return;
        possibleMoves.calculatePossibleMoves();
        possibleMovesCalculated = true;
    }

    public boolean isPromotion(MoveValue move) {
        Coordinate oldPos = move.oldPos();
        Coordinate newPos = move.newPos();
        if (isInvalidMove(oldPos, newPos))
            return false;
        if (board.getPiece(oldPos) != Pieces.PAWN)
            return false;
        return (oldPos.y() == 1 && board.getColour(oldPos) == PieceColour.BLACK) || (oldPos.y() == 6 && board.getColour(oldPos) == PieceColour.WHITE);
    }

    public boolean isTakingMove(MoveValue moveValue) {
        return new Move(board, moveValue.oldPos(), moveValue.newPos(), Pieces.QUEEN).isTaking();
    }
}
