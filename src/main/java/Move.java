import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Moves and stores all necessary pieces to make a chess move.
 * Allows the move to be undone and redone as many times as you would like.
 * There is no move validation, it will always make the move. */
class Move {
    private final Chessboard board;
    private final Coordinate oldPos;
    private final Coordinate newPos;
    private final Pieces piece;
    private final PieceColour pieceColour;
    private final List<MoveValue> movesToUndo = new ArrayList<>(3);
    private final List<MoveValue> movesMade;
    private final Coordinate previousEnPassant;
    private final long castlingRights;
    private final long enemyPossible;
    private boolean undone = false;
    private boolean taking = false;

    /**
     * Initialises move and then moves the piece to the new location.
     * Used so it can undo the first move condition on the previous piece if necessary.
     */
    public Move(Coordinate oldPos, Coordinate newPos, Chessboard board){
        this.oldPos = oldPos;
        this.newPos = newPos;
        this.board = board;
        piece = board.getPiece(oldPos);
        pieceColour = board.getPieceColour(oldPos);
        previousEnPassant = board.getEnPassantSquare();
        castlingRights = board.getCastlingRights();
        movesMade = board.getMoves(oldPos, newPos);
        enemyPossible = board.getPossible(PieceColour.getOtherColour(board.getCurrentTurn()));
        makeMove();
    }

    /**
     * Moves the relevant pieces with no validation.
     * Saves the moves made so they can be undone.
     */
    public void makeMove(){
        undone = false;
        movesToUndo.clear();
        if(piece == Pieces.PAWN && Math.abs(newPos.y() - oldPos.y()) == 2)
            board.setEnPassantSquare(newPos);
        else
            board.setEnPassantSquare(null);
        board.calculateCastling(oldPos);
        for(MoveValue move : movesMade){
            if(!board.isSquareBlank(move.newPos()))
                takePiece(move);
            movesToUndo.add(MoveValue.createStationaryMove(move.oldPos()));
            board.movePiece(move);
        }
        board.nextTurn();
    }

    private void takePiece(@NotNull MoveValue move){
        if(move.isPieceInSamePosition()) // promotion
            board.promotion(move.newPos());
        else
            taking = true;
        Pieces pieceTaken = board.getPiece(move.newPos());
        movesToUndo.add(new MoveValue(pieceTaken, move.newPos()));
    }

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
    private void addOrRemovePiece(Pieces piece, MoveValue move){
        if(!move.isPieceInSamePosition())
            return;
        // a piece moving to the same spot only occurs as the last move when it's a promotion
        if(move == movesToUndo.getLast())
            board.removePiece(move.newPos());
        else
            board.addPiece(piece, move.newPos());
    }

    public Coordinate getOldPos() {
        return oldPos;
    }

    public Coordinate getNewPos() {
        return newPos;
    }

    public boolean isPieceAPawn(){
        return piece == Pieces.PAWN;
    }

    public boolean isPieceColourBlack(){
        return pieceColour == PieceColour.BLACK;
    }

    public List<MoveValue> getMovesToUndo() {
        return undone ? movesToUndo : movesMade;
    }

    public boolean hasTaken() {
        return taking;
    }
}
