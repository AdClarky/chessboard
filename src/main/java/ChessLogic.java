import org.jetbrains.annotations.NotNull;

import java.util.Collection;

class ChessLogic {
    private final Chessboard board;

    public ChessLogic(Chessboard board) {
        this.board = board;
    }


    public void calculatePossibleMoves(){
        calculatePieces(board.getCurrentTurn());
        removeMovesInCheck();
        calculatePieces(PieceColour.getOtherColour(board.getCurrentTurn()));
    }

    private void calculatePieces(PieceColour colour){
        board.clearPossibleMoves(colour);
        Collection<Piece> pieces = board.getAllColourPieces(colour);
        for(Piece piece : pieces){
            piece.calculatePossibleMoves(this);
            board.updatePossibleMoves(piece.getColour(), piece.getPossibleMoves());
        }
    }

    private void removeMovesInCheck(){
        for(Piece piece : board.getAllColourPieces(board.getCurrentTurn())){
            for(Coordinate move : piece.getPossibleMoves()){
                if(isMoveUnsafe(piece, move))
                    piece.removePossibleMove(move);
            }
            if(piece instanceof King king){
                king.removeCastlingThroughCheck();
            }
        }
    }

    private boolean isMoveUnsafe(Piece piece, Coordinate movePos){
        if(isMoveTakingFriendly(piece, movePos))
            return true;
        PieceColour previousTurn = board.getCurrentTurn();
        Move move = new Move(movePos.x(), movePos.y(), piece, board.getLastPieceMoved(), board);
        calculatePieces(board.getCurrentTurn());
        boolean isMoveUnsafe = isKingInCheck(previousTurn);
        move.undo();
        return isMoveUnsafe;
    }

    public boolean isKingInCheck(@NotNull PieceColour kingToCheck){
        King king = board.getKing(kingToCheck);
        Coordinate kingPos = new Coordinate(king);
        PieceColour enemyColour = PieceColour.getOtherColour(kingToCheck);
        if(board.isPossible(enemyColour, kingPos))
            return true;
        return false;
    }

    private boolean isMoveTakingFriendly(Piece piece, Coordinate movePos){
        return board.getPiece(movePos).getColour() == piece.getColour() &&
                board.getPiece(movePos) != piece;
    }

    public boolean isCheckmate(){
        if(!isKingInCheck(board.getCurrentTurn()))
            return false;
        return board.isCheckmate(board.getCurrentTurn());
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
            if(!piece.getPossibleMoves().isEmpty())
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
        board.redoAllMoves();
        return true;
    }

    private boolean isDraw50Move(){
        return board.getNumHalfMoves() >= 50;
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

    public static boolean isValidMove(@NotNull Piece piece, int newX, int newY){
        return !piece.getPossibleMoves().contains(new Coordinate(newX, newY));
    }
}
