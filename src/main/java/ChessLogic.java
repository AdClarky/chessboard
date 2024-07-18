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
        if(!isKingInCheck(board.getCurrentTurn()) && !canSquareSeeKing(piece.getPosition()))
            return false;
        PieceColour previousTurn = board.getCurrentTurn();
        Move move = new Move(movePos.x(), movePos.y(), piece, board.getLastPieceMoved(), board);
        calculatePieces(board.getCurrentTurn());
        boolean isMoveUnsafe = isKingInCheck(previousTurn);
        move.undo();
        return isMoveUnsafe;
    }

    private boolean canSquareSeeKing(Coordinate position) {
        Coordinate kingPos = board.getKing(board.getCurrentTurn()).getPosition();
        if(position.x() == kingPos.x())
            return areSquareBetweenBlank(position, kingPos);
        else if(position.y() == kingPos.y())
            return areSquareBetweenBlank(position, kingPos);
        else if(Math.abs(position.x() - kingPos.x()) == Math.abs(position.y() - kingPos.y()))
            return areSquareBetweenBlank(position, kingPos);
        return false;
    }

    private boolean areSquareBetweenBlank(Coordinate position1, Coordinate position2){
        int xDifference = Integer.signum(Math.abs(position1.x() - position2.x()));
        int yDifference = Integer.signum(Math.abs(position1.y() - position2.y()));
        for(int x = position1.x()+xDifference, y = position1.y()+yDifference; x != position2.x() && y != position2.y(); x+=xDifference, y+=yDifference) {
            if(!board.isSquareBlank(x, y))
                return false;
        }
        return true;
    }

    public boolean isKingInCheck(@NotNull PieceColour kingToCheck){
        Coordinate kingPos = board.getKing(kingToCheck).getPosition();
        PieceColour enemyColour = PieceColour.getOtherColour(kingToCheck);
        return board.isPossible(enemyColour, kingPos);
    }

    private boolean isMoveTakingFriendly(Piece piece, Coordinate movePos){
        return board.getPiece(movePos).getColour() == piece.getColour();
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
        return board.isEnemyPiece(new Coordinate(x, y), colour);
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
