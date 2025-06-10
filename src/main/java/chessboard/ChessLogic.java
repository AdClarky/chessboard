package chessboard;

import common.Coordinate;

class ChessLogic {
    private final Chessboard board;
    private final PossibleMoves possibleMoves;
    private BoardHistory history;
    private Hasher hasher;

    public ChessLogic(Chessboard board, BoardHistory history) {
        this.board = board;
        possibleMoves = new PossibleMoves(board);
        this.history = history;
        hasher = new Hasher(board);
    }

    public boolean isCheckmate() {
        if (!possibleMoves.isKingInCheck())
            return false;
        return possibleMoves.hasMoves();
    }

    public boolean isDraw() {
        return isStalemate() ||
                isDraw50Move() ||
                isRepetition();
    }

    public boolean isStalemate() {
        if (possibleMoves.isKingInCheck())
            return false;
        return possibleMoves.hasMoves();
    }

    private boolean isRepetition() {
        if (history.getNumFullMoves() < 4)
            return false;
        long boardState = hasher.getHash();
        for (int i = 0; i < 2; i++) {
            history.undoMultipleMoves(4);
            if (boardState != hasher.getHash()) {
                history.redoAllMoves();
                return false;
            }
        }
        history.redoAllMoves();
        return true;
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
}
