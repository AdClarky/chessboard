import org.jetbrains.annotations.NotNull;

public class ChessLogic {
    private Chessboard board;

    public ChessLogic(Chessboard board) {
        this.board = board;
    }

    /**
     * Calculates if moving a piece to a position would put that teams king in check.
     * This assumes the piece moving to the new position is a valid move.
     * @param pieceToCheck the piece being moved.
     * @return true if in check, false if not
     */
    public boolean isMoveUnsafe(int newX, int newY, @NotNull Piece pieceToCheck){
        Piece lastPiece = board.getLastPieceMoved();
        Move move = new Move(newX, newY, pieceToCheck, lastPiece, board);
        boolean isMoveUnsafe = isKingInCheck(pieceToCheck.getColour());
        move.undo();
        return isMoveUnsafe;
    }

    public boolean isKingInCheck(@NotNull PieceColour kingToCheck){
        King king = board.getKing(kingToCheck);
        Coordinate kingPos = new Coordinate(king);
        Iterable<Piece> enemyPieces = board.getAllColourPieces(PieceColour.getOtherColour(kingToCheck));
        for(Piece piece : enemyPieces){
            if(piece.getPossibleMoves(this).contains(kingPos)){
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate(){
        if(!isKingInCheck(board.getCurrentTurn()))
            return false;
        Iterable<Piece> enemyPieces = board.getAllColourPieces(board.getCurrentTurn());
        for (Piece enemyPiece : enemyPieces) {
            if(!enemyPiece.getPossibleMoves(this).isEmpty())
                return false;
        }
        return true;
    }

    public boolean isDraw(){
        return isStalemate() ||
                isDraw50Move() ||
                isRepetition();
    }

    private boolean isStalemate(){
        if(isKingInCheck(board.getCurrentTurn()))
            return false;
        Iterable<Piece> pieces = board.getAllColourPieces(board.getCurrentTurn());
        for(Piece piece : pieces){
            if(!piece.getPossibleMoves(this).isEmpty())
                return false;
        }
        return true;
    }

    private boolean isRepetition(){
        if(board.getNumFullMoves() < 4)
            return false;
        int boardState = board.hashCode();
        for(int i = 0; i < 2; i++){
            board.undoMultipleMoves(4);
            if(boardState != board.hashCode()) {
                board.redoAllMoves();
                return false;
            }
        }
        board.undoMultipleMoves(4);
        return true;
    }

    private boolean isDraw50Move(){
        return board.getNumHalfMoves() == 50;
    }

    public PieceColour getCurrentTurn(){
        return board.getCurrentTurn();
    }

    public boolean isSquareBlank(int x, int y){
        return board.isSquareBlank(x, y);
    }

    public boolean isEnemyPiece(int x, int y, PieceColour colour){
        return board.getPieceColour(x, y) == PieceColour.getOtherColour(colour);
    }

    public boolean hasPieceHadFirstMove(int x, int y){
        return board.hasPieceHadFirstMove(x, y);
    }

    public MoveValue getMoveForOtherPiece(int x, int y, int newX, int newY){
        return new MoveValue(board.getPiece(x, y), newX, newY);
    }

    public boolean isPiecePawn(int x, int y){
        return board.getPiece(x, y) instanceof Pawn;
    }

    public boolean isValidMove(Piece piece, int newX, int newY){
        return !piece.getPossibleMoves(this).contains(new Coordinate(newX, newY));
    }
}
