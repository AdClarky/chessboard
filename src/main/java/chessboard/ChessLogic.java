package chessboard;

import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import common.PieceValue;
import common.Pieces;

import java.util.Collection;

class ChessLogic {
    private final Chessboard board;
    private final MaskGenerator maskGenerator;
    private Bitboard enemyPossible;
    private PossibleMoves possibleMoves;
    private BoardHistory history;
    private Hasher hasher;

    public ChessLogic(Chessboard board, BoardHistory history) {
        this.board = board;
        maskGenerator = new MaskGenerator(board);
        possibleMoves = new PossibleMoves();
        this.history = history;
        hasher = new Hasher(board);
    }

    private ChessLogic(Chessboard board, Bitboard enemyPossible, PossibleMoves possibleMoves, BoardHistory history) {
        this.board = board;
        maskGenerator = new MaskGenerator(board);
        this.enemyPossible = enemyPossible;
        this.possibleMoves = possibleMoves;
        this.history = history;
        hasher = new Hasher(board);
    }

    public void calculatePossibleMoves(){
        enemyPossible = calculatePieces(board.getTurn().invert());
        long temp = enemyPossible.getBoard();
        possibleMoves = new PossibleMoves();
        calculateFriendlyPieces();
        enemyPossible = new Bitboard(temp);
    }

    private Bitboard calculatePieces(PieceColour colour){
        Collection<Coordinate> pieces = board.getAllColourPositions(colour);
        long possible = 0;
        for(Coordinate piecePos : pieces) {
            possible |= maskGenerator.getMaskForPiece(piecePos);
        }
        return new Bitboard(possible);
    }

    private void calculateFriendlyPieces(){
        Collection<Coordinate> pieces = board.getAllColourPositions(board.getTurn());
        for(Coordinate piecePos : pieces){
            Bitboard possible = new Bitboard(maskGenerator.getMaskForPiece(piecePos));
            removeMovesInCheck(piecePos, possible);
            possibleMoves.addMoves(piecePos, possible);
        }
    }

    private void removeMovesInCheck(Coordinate pos, Bitboard possible){
        possible.removeIf(move -> isMoveUnsafe(pos, move));
        if(board.getPiece(pos) == Pieces.KING)
            removeCastlingThroughCheck(possible, pos);
    }

    private boolean isMoveUnsafe(Coordinate position, Coordinate movePos){
        // TODO: if the king is in check, only compute moves that block
        if(!isKingInCheck() && !doesMoveExposeKing(position, movePos))
            return false;
        PieceColour previousTurn = board.getTurn();
        Move move = new Move(board, position, movePos);
        Bitboard possible = calculatePieces(board.getTurn());
        boolean isKingInCheck = isKingInCheck(previousTurn, possible);
        move.undo();
        return isKingInCheck;
    }

    private boolean doesMoveExposeKing(Coordinate position, Coordinate movePosition) {
        if(board.getPiece(position) != Pieces.KING && !enemyPossible.contains(position))
            return false;
        Coordinate kingPos = board.getKingPos(board.getTurn());
        if(position.x() == kingPos.x() && movePosition.x() != position.x()) {
            return canEnemyPieceSeeKing(kingPos, position);
        }
        else if(position.y() == kingPos.y() && movePosition.y() != position.y()) {
            return canEnemyPieceSeeKing(kingPos, position);
        }
        else if((Math.abs(position.x() - kingPos.x()) == Math.abs(position.y() - kingPos.y())) &&
                ((position.x() - kingPos.x()) * (movePosition.y() - kingPos.y()) - (position.y() - kingPos.y()) * (movePosition.x() - kingPos.x())) != 0) {
            return canEnemyPieceSeeKing(kingPos, position);
        }
        return false;
    }

    private boolean canEnemyPieceSeeKing(Coordinate kingPos, Coordinate position) {
        int xDifference = Integer.signum(position.x() - kingPos.x());
        int yDifference = Integer.signum(position.y() - kingPos.y());
        if(xDifference == 0 && yDifference == 0)
            return true;
        PieceColour colour = board.getColour(kingPos);
        for(int x = kingPos.x()+xDifference, y = kingPos.y()+yDifference; ; x+=xDifference, y+=yDifference) {
            Coordinate square = new Coordinate(x, y);
            if(square.isNotInRange())
                return false;
            if(square.x() == position.x() && square.y() == position.y())
                continue;
            if(board.isSquareColour(square, colour))
                return false;
            if(board.isSquareColour(square, colour.invert()))
                return canPieceSeeSquare(board.getPiece(square), square, kingPos, xDifference, yDifference);
        }
    }

    private boolean canPieceSeeSquare(Pieces piece, Coordinate piecePos, Coordinate kingPos, int xDiff, int yDiff) {
        int absDiff = Math.abs(yDiff) +  Math.abs(xDiff);
        if(absDiff == 2){ // diagonal, bishop, pawn, queen
            if(piece == Pieces.BISHOP || piece == Pieces.QUEEN)
                return true;
            return piece == Pieces.PAWN && kingPos.x() + xDiff == piecePos.x() && kingPos.y() + yDiff == piecePos.y();
        }
        else{ // horizontal, rook, queen
            return piece == Pieces.ROOK || piece == Pieces.QUEEN;
        }
    }

    private void removeCastlingThroughCheck(Bitboard possible, Coordinate position){
        Coordinate left = new Coordinate(position.x()-1, position.y());
        Coordinate right = new Coordinate(position.x()+1, position.y());
        if(isKingInCheck()){
            possible.remove(new Coordinate(position.x()-2, position.y()));
            possible.remove(new Coordinate(position.x()+2, position.y()));
        }
        if(!possible.contains(left))
            possible.remove(new Coordinate(position.x()-2, position.y()));
        if(!possible.contains(right))
            possible.remove(new Coordinate(position.x()+2, position.y()));
    }

    public boolean isKingInCheck(){
        Coordinate kingPos = board.getKingPos(board.getTurn());
        return enemyPossible.contains(kingPos);
    }

    public boolean isKingInCheck(PieceColour colour, Bitboard possible){
        return possible.contains(board.getKingPos(colour));
    }

    public boolean isCheckmate(){
        if(!isKingInCheck())
            return false;
        return possibleMoves.isEmpty();
    }

    public boolean isDraw(){
        return isStalemate() ||
                isDraw50Move() ||
                isRepetition();
    }

    public boolean isStalemate(){
        if(isKingInCheck())
            return false;
        return possibleMoves.isEmpty();
    }

    private boolean isRepetition(){
        if(history.getNumFullMoves() < 4)
            return false;
        long boardState = hasher.getHash();
        for(int i = 0; i < 2; i++){
            history.undoMultipleMoves(4);
            if(boardState != hasher.getHash()) {
                history.redoAllMoves();
                return false;
            }
        }
        history.redoAllMoves();
        return true;
    }

    private boolean isDraw50Move(){
        return history.getNumHalfMoves() >= 50;
    }

    public boolean isSquareBlank(int x, int y){
        return isSquareBlank(new Coordinate(x, y));
    }

    public boolean isSquareBlank(Coordinate position){
        return board.isSquareBlank(position);
    }

    public boolean isEnemyPiece(int x, int y, PieceColour colour){
        return board.isSquareColour(new Coordinate(x, y), colour.invert());
    }

    public MoveValue getMoveForOtherPiece(int x, int y, int newX, int newY){
        return new MoveValue(new Coordinate(x, y), new Coordinate(newX, newY));
    }

    public boolean isValidMove(Coordinate oldPos, Coordinate newPos){
        return possibleMoves.isPossible(oldPos, newPos);
    }

    public Collection<Coordinate> getPossibleMoves(Coordinate piece) {
        return possibleMoves.getPossibleMove(piece);
    }

    public PossibleMoves getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(PossibleMoves possibleMoves) {
        this.possibleMoves = possibleMoves;
        enemyPossible = calculatePieces(board.getTurn().invert());
    }

    public void updatePossibleMoves(Coordinate oldPos, Coordinate newPos) {

    }

    ChessLogic copy(Chessboard board) {
        Bitboard enemyPossibleCopy = new Bitboard(enemyPossible.getBoard());
        PossibleMoves possibleMovesCopy = possibleMoves.copy();
        BoardHistory boardHistoryCopy = history.copy();
        return new ChessLogic(board, enemyPossibleCopy, possibleMovesCopy, boardHistoryCopy);
    }
}
