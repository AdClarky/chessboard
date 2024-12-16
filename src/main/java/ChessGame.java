public class ChessGame {
    private final Chessboard board;
    private final ChessLogic logic;
    private final BoardHistory history;

    ChessGame() {
        board = new ChessboardBuilder().defaultSetup();
        logic = new ChessLogic(board);
        history = new BoardHistory();
    }

    public void makeMove(Coordinate oldPos, Coordinate newPos) throws InvalidMoveException {
        if(logic.isValidMove(oldPos, newPos))
            throw new InvalidMoveException(oldPos, newPos);
        if(history.canRedoMove())
            history.clearRedoMoves();
        Move move = new Move(board, oldPos, newPos);
        history.push(move);
        logic.calculatePossibleMoves();
    }

    public PieceColour getColour(Coordinate position){
        return board.getColour(position);
    }

    public Pieces getPiece(Coordinate position){
        return board.getPiece(position);
    }

    public PieceColour getTurn(){
        return board.getTurn();
    }

    public MoveValue redoMove() {
        Move move = history.redoMove();
        logic.calculatePossibleMoves();
        return new MoveValue(move.getOldPos(), move.getNewPos());
    }

    public boolean canRedoMove(){
        return history.canRedoMove();
    }

    public MoveValue undoMove() {
        Move move = history.undoMove();
        logic.calculatePossibleMoves();
        return new MoveValue(move.getOldPos(), move.getNewPos());
    }

    public boolean canUndoMove(){
        return history.canUndoMove();
    }

    public int getNumHalfMoves(){
        return history.getNumHalfMoves();
    }

    public int getNumFullMoves(){
        return history.getNumFullMoves();
    }

    public long getCastlingRights(){
        return board.getCastlingRights();
    }

    public Coordinate getEnPassantSquare() {
        return board.getEnPassantSquare();
    }
}
