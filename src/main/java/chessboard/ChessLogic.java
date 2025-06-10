package chessboard;

import common.Coordinate;

class ChessLogic {
    private final Chessboard board;
    private final PossibleMoves possibleMoves;
    private BoardHistory history;

    public ChessLogic(Chessboard board, BoardHistory history) {
        this.board = board;
        possibleMoves = new PossibleMoves(board);
        possibleMoves.calculatePossibleMoves();
        this.history = history;
    }

    public boolean isCheckmate() {
        if (!possibleMoves.isKingInCheck())
            return false;
        return possibleMoves.hasMoves();
    }

    public boolean isDraw() {
        return isStalemate() ||
                isDraw50Move() ||
                history.isRepetition();
    }

    public boolean isStalemate() {
        if (possibleMoves.isKingInCheck())
            return false;
        return !possibleMoves.hasMoves();
    }

    private boolean isDraw50Move() {
        return history.getNumHalfMoves() >= 50;
    }

    public boolean isSquareBlank(int x, int y) {
        return isSquareBlank(new Coordinate(x, y));
    }

    public boolean isSquareBlank(Coordinate position) {
        return board.isSquareBlank(position);
    }

    public boolean isInvalidMove(Coordinate oldPos, Coordinate newPos) {
        Bitboard board = possibleMoves.get(oldPos);
        if (board == null)
            return true;
        return !board.contains(newPos);
    }

    public Bitboard getPossibleMoves(Coordinate piece) {
        return possibleMoves.get(piece);
    }

    public boolean isKingInCheck(){
        return possibleMoves.isKingInCheck();
    }
}
