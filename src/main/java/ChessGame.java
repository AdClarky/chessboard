public class ChessGame {
    private final Chessboard board;
    private final ChessLogic chessLogic;

    ChessGame() {
        board = new ChessboardBuilder().defaultSetup();
        chessLogic = new ChessLogic(board);
    }



    public void makeMove(Coordinate oldPos, Coordinate newPos) throws InvalidMoveException {
        if(ChessLogic.isValidMove(getPiece(oldPos), newPos))
            throw new InvalidMoveException(oldPos, newPos);
        board.makeMove(oldPos, newPos);
        chessLogic.calculatePossibleMoves();
    }

    public boolean redoMove() {
        Move move = board.redoMove();
        if(move == null)
            return;
        chessLogic.calculatePossibleMoves();
    }

    public boolean undoMove() {
        Move move = board.undoMove();
        if(move == null)
            return;
        chessLogic.calculatePossibleMoves();
    }
}
