import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Moves and stores all necessary pieces to make a chess move.
 * Allows the move to be undone and redone as many times as you would like.
 * There is no move validation, it will always make the move. */
class Move {
    private final Chessboard board;
    private final Piece piece;
    private final Coordinate movePosition;
    private final List<MoveValue> movesToUndo = new ArrayList<>(3);
    private final List<MoveValue> movesMade;
    private final Coordinate previousEnPassant;
    private final long castlingRights;
    private boolean undone = false;
    private boolean taking = false;
    private long enemyPossible;

    /**
     * Initialises move and then moves the piece to the new location.
     * Used so it can undo the first move condition on the previous piece if necessary.
     */
    public Move(Coordinate newPos, @NotNull Piece piece, Chessboard board){
        movePosition = newPos;
        this.piece = piece;
        this.board = board;
        previousEnPassant = board.getEnPassantSquare();
        castlingRights = board.getCastlingRights();
        movesMade = piece.getMoves(new ChessLogic(board), newPos.x(), newPos.y());
        enemyPossible = board.getPossible(PieceColour.getOtherColour(board.getCurrentTurn()));
        makeMove();
    }

    public Move(int x, int y, @NotNull Piece piece, Chessboard board){
        this(new Coordinate(x, y), piece, board);
    }

    /**
     * Moves the relevant pieces with no validation.
     * Saves the moves made so they can be undone.
     */
    public void makeMove(){
        undone = false;
        movesToUndo.clear();
        if(piece instanceof Pawn && Math.abs(movePosition.y() - piece.getY()) == 2)
            board.setEnPassantSquare(movePosition);
        else
            board.setEnPassantSquare(null);
        board.pieceMoved(piece.getPosition());
        for(MoveValue move : movesMade){
            if(!board.isSquareBlank(move.newPos()))
                takePiece(move);
            movesToUndo.add(MoveValue.createStationaryMove(move.piece()));
            board.movePiece(move);
        }
        board.nextTurn();
    }

    private void takePiece(@NotNull MoveValue move){
        if(move.isPieceInSamePosition()) // promotion
            board.addPiece(move.piece());
        else
            taking = true;
        Piece pieceTaken = board.getPiece(move.newPos());
        movesToUndo.add(new MoveValue(pieceTaken, move.newPos()));
        board.removePiece(pieceTaken);
    }

    /**
     * Undoes the move that was just made.
     * The board is left in the exact same state as before being run.
     */
    public void undo(){
        undone = true;
        for(MoveValue move : movesToUndo.reversed()){
            addOrRemovePiece(move.piece(), move);
            board.movePiece(move);
        }
        board.setEnPassantSquare(previousEnPassant);
        board.setCastlingRights(castlingRights);
        board.nextTurn();
        board.setPossibleMoves(PieceColour.getOtherColour(board.getCurrentTurn()), enemyPossible);
    }

    /** Checks if a piece was taken or if it was a promotion and restores it. */
    private void addOrRemovePiece(@NotNull Piece pieceToMove, @NotNull MoveValue move){
        if(!move.isPieceInSamePosition())
            return;
        // a piece moving to the same spot only occurs as the last move when it's a promotion
        if(move == movesToUndo.getLast())
            board.removePiece(pieceToMove);
        else
            board.addPiece(pieceToMove);
    }

    public int getX() {
        return movePosition.x();
    }

    public int getY() {
        return movePosition.y();
    }

    public int getPieceX(){
        return piece.getX();
    }

    public int getPieceY(){
        return piece.getY();
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isPieceAPawn(){
        return piece instanceof Pawn;
    }

    public boolean isPieceColourBlack(){
        return piece.getColour() == PieceColour.BLACK;
    }

    public List<MoveValue> getMovesToUndo() {
        return undone ? movesToUndo : movesMade;
    }

    public boolean hasTaken() {
        return taking;
    }
}
