import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

class ChessLogic {
    private final Chessboard board;

    public ChessLogic(Chessboard board) {
        this.board = board;
    }


    public void calculatePossibleMoves(){
        calculatePieces(board.getCurrentTurn());
        calculatePieces(PieceColour.getOtherColour(board.getCurrentTurn()));
        removeMovesInCheck();
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
        Iterable<Piece> pieces = new ArrayList<>(board.getAllColourPieces(board.getCurrentTurn()));
        for(Piece piece : pieces){
            for(Coordinate move : piece.getPossibleMoves()){
                if(isMoveUnsafe(piece, move)) {
                    piece.removePossibleMove(move);
                    board.removePossible(piece.getColour(), move);
                }
            }
            if(piece instanceof King king){
                king.removeCastlingThroughCheck();
            }
        }
    }

    private boolean isMoveUnsafe(Piece piece, Coordinate movePos){
        if(isMoveTakingFriendly(piece, movePos))
            return true;
        if(!isKingInCheck(board.getCurrentTurn()) && !doesMoveExposeKing(piece.getPosition(), movePos))
            return false;
        PieceColour previousTurn = board.getCurrentTurn();
        Move move = new Move(movePos.x(), movePos.y(), piece, board.getLastPieceMoved(), board);
        calculatePieces(board.getCurrentTurn());
        boolean isMoveUnsafe = isKingInCheck(previousTurn);
        move.undo();
        return isMoveUnsafe;
    }

    private boolean doesMoveExposeKing(Coordinate position, Coordinate movePosition) {
        Coordinate kingPos = board.getKing(board.getCurrentTurn()).getPosition();
        if(position.x() == kingPos.x() && movePosition.x() != position.x())
            return areSquareBetweenBlank(position, kingPos);
        else if(position.y() == kingPos.y() && movePosition.y() != position.y())
            return areSquareBetweenBlank(position, kingPos);
        else if((Math.abs(position.x() - kingPos.x()) == Math.abs(position.y() - kingPos.y())) &&
                (Math.abs(movePosition.x() - kingPos.x()) != Math.abs(movePosition.y() - kingPos.y()))) {
            if (areSquareBetweenBlank(position, kingPos))
                return canEnemyPieceSeeKing(kingPos, position);
            return false;
        }
        return false;
    }

    private boolean areSquareBetweenBlank(Coordinate position, Coordinate kingPos){
        int xDifference = Integer.signum(kingPos.x() - position.x());
        int yDifference = Integer.signum(kingPos.y() - position.y());
        for(int x = position.x()+xDifference, y = position.y()+yDifference; x != kingPos.x() || y != kingPos.y(); x+=xDifference, y+=yDifference) {
            if(!board.isSquareBlank(x, y))
                return false;
        }
        return true;
    }

    private boolean canEnemyPieceSeeKing(Coordinate kingPos, Coordinate position) {
//        int xDifference = Integer.signum(Math.abs(position.x() - kingPos.x()));
//        int yDifference = Integer.signum(Math.abs(position.y() - kingPos.y()));
//        for(int x = position1.x()+xDifference, y = position1.y()+yDifference; x != position2.x() && y != position2.y(); x+=xDifference, y+=yDifference) {
//            if(!board.isSquareBlank(x, y))
//                return false;
//        }
        return true;
//        return false;
    }

    public boolean isKingInCheck(@NotNull PieceColour kingToCheck){
        Coordinate kingPos = board.getKing(kingToCheck).getPosition();
        PieceColour enemyColour = PieceColour.getOtherColour(kingToCheck);
        return board.isPossible(enemyColour, kingPos);
    }

    private boolean isMoveTakingFriendly(Piece piece, Coordinate movePos){
        return board.isSquareColour(movePos, piece.getColour());
    }

    public Coordinate getEnPassantSquare(){
        return board.getEnPassantSquare();
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
        return board.isCheckmate(board.getCurrentTurn());
    }

    private boolean isRepetition(){
        if(board.getNumFullMoves() < 4)
            return false;
        int boardState = board.hashCode();
        long possibleMovesWhite = board.getPossible(PieceColour.WHITE);
        long possibleMovesBlack = board.getPossible(PieceColour.BLACK);
        for(int i = 0; i < 2; i++){
            board.undoMultipleMoves(4);
            if(boardState != board.hashCode()) {
                board.redoAllMoves();
                board.setPossibleMoves(PieceColour.WHITE, possibleMovesWhite);
                board.setPossibleMoves(PieceColour.BLACK, possibleMovesBlack);
                return false;
            }
        }
        board.redoAllMoves();
        board.setPossibleMoves(PieceColour.WHITE, possibleMovesWhite);
        board.setPossibleMoves(PieceColour.BLACK, possibleMovesBlack);
        return true;
    }

    private boolean isDraw50Move(){
        return board.getNumHalfMoves() >= 50;
    }

    public boolean isSquareBlank(int x, int y){
        return isSquareBlank(new Coordinate(x, y));
    }

    public boolean isSquareBlank(Coordinate position){
        return board.isSquareBlank(position);
    }

    public boolean isEnemyPiece(int x, int y, PieceColour colour){
        return board.isSquareColour(new Coordinate(x, y), PieceColour.getOtherColour(colour));
    }

    public MoveValue getMoveForOtherPiece(int x, int y, int newX, int newY){
        return new MoveValue(board.getPiece(x, y), newX, newY);
    }

    public static boolean isValidMove(@NotNull Piece piece, int newX, int newY){
        return !piece.getPossibleMoves().contains(new Coordinate(newX, newY));
    }

    public boolean canAnythingCastle() {
        return board.canAnythingCastle();
    }

    public boolean canKingCastle(PieceColour colour) {
        return board.canKingCastle(colour);
    }

    public boolean canCastle(Coordinate position) {
        return board.canCastle(position);
    }
}
