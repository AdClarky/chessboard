import java.util.Collection;

class ChessLogic {
    private final Chessboard board;
    private MaskGenerator maskGenerator;
    private Bitboard enemyPossible;
    private PossibleMoves possibleMoves = new PossibleMoves();
    private BoardHistory history;

    public ChessLogic(Chessboard board, BoardHistory history) {
        this.board = board;
        this.history = history;
        maskGenerator = new MaskGenerator(board);
    }

    public void calculatePossibleMoves(){
        enemyPossible = calculatePieces(board.getTurn().invert());
        calculateFriendlyPieces();
        enemyPossible = calculatePieces(board.getTurn().invert());
    }

    private Bitboard calculatePieces(PieceColour colour){
        Collection<Coordinate> pieces = board.getAllColourPositions(colour);
        Bitboard possible = new Bitboard();
        for(Coordinate piecePos : pieces) {
            possible.addAll(new Bitboard(maskGenerator.getMaskForPiece(piecePos)));
        }
        return possible;
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
        if(!isKingInCheck() && !doesMoveExposeKing(position, movePos))
            return false;
        PieceColour previousTurn = board.getTurn();
        Move move = new Move(board, position, movePos);
        Bitboard possible = calculatePieces(board.getTurn());
        move.undo();
        return isKingInCheck(previousTurn, possible);
    }

    private boolean doesMoveExposeKing(Coordinate position, Coordinate movePosition) {
        Coordinate kingPos = board.getKingPos(board.getTurn());
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
        int boardState = board.hashCode();
        for(int i = 0; i < 2; i++){
            history.undoMultipleMoves(4);
            if(boardState != board.hashCode()) {
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
}
