package chessboard;

import common.BoardListener;
import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import common.Pieces;
import exception.InvalidFenStringException;
import exception.InvalidMoveException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A chess game api. The board can be built using a FEN string or just in the default position.
 * Add a {@link BoardListener} to be notified of events. At the end of a move boardChanged is called.
 *
 * @author Toby
 */
public class ChessInterface implements Chess, Undoable {
    private final ChessGame game;
    private final FenGenerator fenGenerator;
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);

    /**
     * Creates a {@code ChessGame} with the pieces in the default position.
     */
    public ChessInterface() {
        game = new ChessGame();
        fenGenerator = new FenGenerator(game);
    }

    /**
     * Creates a {@code ChessGame} with the setup based on a FEN String.
     *
     * @param fenString a FEN string which details a board position
     * @throws InvalidFenStringException when the give FEN string is invalid
     */
    public ChessInterface(String fenString) throws InvalidFenStringException {
        game = new ChessGame(fenString);
        fenGenerator = new FenGenerator(game);
    }

    @Override
    public PieceColour getCurrentTurn() {
        return game.getTurn();
    }

    public void makeMove(Coordinate oldPos, Coordinate newPos, Pieces promotionPiece) throws InvalidMoveException {
        game.makeMove(oldPos, newPos, promotionPiece);
        notifyMoveMade(oldPos, newPos);
        if (game.isDraw())
            notifyDraw();
        if (game.isCheckmate()) {
            notifyCheckmate(game.getKing());
        }
    }

    @Override
    public void makeMove(Coordinate oldPos, Coordinate newPos) throws InvalidMoveException {
        if (game.isMovePromotion(oldPos, newPos)) {
            notifyPromotion();
            return;
        }
        makeMove(oldPos, newPos, null);
    }

    @Override
    public void makeMove(MoveValue moveValue) throws InvalidMoveException {
        makeMove(moveValue.oldPos(), moveValue.newPos());
    }

    @Override
    public void makeMove(@NotNull String chessMove) throws InvalidMoveException {
        MoveValue move = game.chessToMove(chessMove);
        makeMove(move.oldPos(), move.newPos());
    }

    /**
     * Adds the given BoardListener to receive events from this board.
     *
     * @param listener the board listener
     */
    public void addBoardListener(BoardListener listener) {
        boardListeners.add(listener);
    }

    private void notifyMoveMade(Coordinate oldPos, Coordinate newPos) {
        for (BoardListener listener : boardListeners) {
            listener.moveMade(oldPos, newPos);
        }
    }

    private void notifyBoardChanged(@NotNull MoveValue move) {
        for (BoardListener listener : boardListeners) {
            listener.boardChanged(move.oldPos(), move.newPos());
        }
    }

    private void notifyCheckmate(Coordinate kingPos) {
        for (BoardListener listener : boardListeners)
            listener.checkmate(kingPos);
    }

    private void notifyDraw() {
        Coordinate whitePos = game.getKing(PieceColour.WHITE);
        Coordinate blackPos = game.getKing(PieceColour.BLACK);
        for (BoardListener listener : boardListeners)
            listener.draw(whitePos, blackPos);
    }

    private void notifyPromotion() {
        for (BoardListener listener : boardListeners) {
            listener.promotion();
        }
    }

    @Override
    public void redoMove() {
        if (!game.canRedoMove())
            return;
        MoveValue move = game.redoMove();
        notifyBoardChanged(move);
        if (game.isCheckmate()) {
            notifyCheckmate(game.getKing());
        }
    }

    @Override
    public void undoMove() {
        if (!game.canUndoMove())
            return;
        MoveValue move = game.undoMove();
        notifyBoardChanged(move);
    }

    @Override
    public void undoMultipleMoves(int numOfMoves) {
        for (int i = 0; i < numOfMoves; i++) {
            undoMove();
        }
    }

    @Override
    public void redoAllMoves() {
        while (game.canRedoMove()) {
            redoMove();
        }
    }

    @Override
    public boolean isCheckmate() {
        return game.isCheckmate();
    }

    @Override
    public boolean isDraw() {
        return game.isDraw();
    }

    @Override
    public String getFenString() {
        return fenGenerator.getFenString();
    }

    @Override
    public PieceColour getColour(Coordinate position) {
        return game.getColour(position);
    }

    @Override
    public Collection<Coordinate> getPossibleMoves(Coordinate position) {
        return game.getPossibleMoves(position);
    }

    @Override
    public Collection<Coordinate> getAllColourPieces(PieceColour turn) {
        return game.getAllColourPieces(turn);
    }

}
