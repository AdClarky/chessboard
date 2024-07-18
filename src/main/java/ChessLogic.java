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
        Move move = new Move(movePos, piece, board);
        calculatePieces(board.getCurrentTurn());
        boolean isMoveUnsafe = isKingInCheck(previousTurn);
        move.undo();
        return isMoveUnsafe;
    }

    private boolean doesMoveExposeKing(Coordinate position, Coordinate movePosition) {
        Coordinate kingPos = board.getKing(board.getCurrentTurn()).getPosition();
        if(position.x() == kingPos.x() && movePosition.x() != position.x()) {
            return canEnemyPieceSeeKing(kingPos, position);
        }
        else if(position.y() == kingPos.y() && movePosition.y() != position.y()) {
            return canEnemyPieceSeeKing(kingPos, position);
        }
        else if((Math.abs(position.x() - kingPos.x()) == Math.abs(position.y() - kingPos.y())) &&
                (Math.abs(movePosition.x() - kingPos.x()) != Math.abs(movePosition.y() - kingPos.y()))) {
            return canEnemyPieceSeeKing(kingPos, position);
        }
        return false;
    }

    private boolean canEnemyPieceSeeKing(Coordinate kingPos, Coordinate position) {
        int xDifference = Integer.signum(position.x() - kingPos.x());
        int yDifference = Integer.signum(position.y() - kingPos.y());
        if(xDifference == 0 && yDifference == 0)
            return true;
        PieceColour colour = board.getColourAtPosition(kingPos);
        if(colour == PieceColour.BLANK)
            throw new RuntimeException();
        for(int x = kingPos.x()+xDifference, y = kingPos.y()+yDifference; ; x+=xDifference, y+=yDifference) {
            Coordinate square = new Coordinate(x, y);
            if(!square.isInRange())
                return false;
            if(square.x() == position.x() && square.y() == position.y())
                continue;
            if(board.isSquareColour(square, colour))
                return false;
            if(board.isSquareColour(square, PieceColour.getOtherColour(colour)))
                return canPieceSeeSquare(board.getPiece(square), kingPos, xDifference, yDifference);
        }
    }

    private boolean canPieceSeeSquare(Piece piece, Coordinate kingPos, int xDiff, int yDiff) {
        int absDiff = Math.abs(yDiff) +  Math.abs(xDiff);
        if(absDiff == 2){ // diagonal, bishop, pawn, queen
            if(piece instanceof Bishop || piece instanceof Queen)
                return true;
            return piece instanceof Pawn && kingPos.x() + xDiff == piece.getX() && kingPos.y() + yDiff == piece.getY();
        }
        else{ // horizontal, rook, queen
            return piece instanceof Rook || piece instanceof Queen;
        }
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
        return new MoveValue(board.getPiece(x, y), new Coordinate(newX, newY));
    }

    public static boolean isValidMove(@NotNull Piece piece, Coordinate newPos){
        return !piece.getPossibleMoves().contains(newPos);
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
