import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A chess game api. The board can be built using a FEN string or just in the default position.
 * Add a {@link BoardListener} to be notified of events. At the end of a move boardChanged is called.
 * @author Toby
 */
public class ChessInterface {
    private final ChessGame game;
    private final FenGenerator fenGenerator;
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);

    /**
     * Creates a {@code ChessGame} with the pieces in the default position.
     */
    public ChessInterface(){
        game = new ChessGame();
        fenGenerator = new FenGenerator(game);
    }

    /**
     * Creates a {@code ChessGame} with the setup based on a FEN String.
     * @param fenString a FEN string which details a board position
     * @throws InvalidFenStringException when the give FEN string is invalid
     */
    public ChessInterface(String fenString) throws InvalidFenStringException {
        game = new ChessGame(fenString);
        fenGenerator = new FenGenerator(game);
    }

    /**
     * The current turn of the board, i.e. black or white.
     * @return the current turn
     */
    public PieceColour getCurrentTurn(){
        return game.getTurn();
    }

    /**
     * Moves a piece to a new location while validating it is a valid move.
     * Assumes the provided old coordinates are valid coordinates for a piece.
     * If any moves have been undone, it sets the board back to the current position.
     * After a move has been made, it notifies all listeners then moves on to the next turn.
     * Checks for checkmate and draws.
     * @throws InvalidMoveException when the move given is not a valid move.
     */
    public void makeMove(Coordinate oldPos, Coordinate newPos) throws InvalidMoveException {
        game.makeMove(oldPos, newPos);
        notifyMoveMade(oldPos, newPos);
        if(game.isDraw())
            notifyDraw();
        if(game.isCheckmate()) {
            notifyCheckmate(game.getKing());
        }
    }

    /**
     * {@link ChessInterface#makeMove(Coordinate, Coordinate)}
     * @param chessMove a chess move in algabraic notation
     * @throws InvalidMoveException when the move given is not a valid move
     */
    public void makeMove(@NotNull String chessMove) throws InvalidMoveException {
        MoveValue move = game.chessToMove(chessMove);
        makeMove(move.oldPos(), move.newPos());
    }

    /**
     * Adds the given BoardListener to receive events from this board.
     * @param listener the board listener
     */
    public void addBoardListener(BoardListener listener){
        boardListeners.add(listener);
    }

    private void notifyMoveMade(Coordinate oldPos, Coordinate newPos){
        for(BoardListener listener : boardListeners){
            listener.moveMade(oldPos, newPos);
        }
    }

    private void notifyBoardChanged(@NotNull MoveValue move){
        for(BoardListener listener : boardListeners){
            listener.boardChanged(move.oldPos(), move.newPos());
        }
    }

    private void notifyCheckmate(Coordinate kingPos){
        for(BoardListener listener : boardListeners)
            listener.checkmate(kingPos);
    }

    private void notifyDraw(){
        Coordinate whitePos = game.getKing(PieceColour.WHITE);
        Coordinate blackPos = game.getKing(PieceColour.BLACK);
        for(BoardListener listener : boardListeners)
            listener.draw(whitePos, blackPos);
    }

    /**
     * Moves forward one move. Does nothing if there are no more moves to be made.
    */
    public void redoMove(){
        if(!game.canRedoMove())
            return;
        MoveValue move = game.redoMove();
        notifyBoardChanged(move);
        if(game.isCheckmate()) {
            notifyCheckmate(game.getKing());
        }
    }

    /**
     * Moves backwards one move. Does nothing if there are no more moves to be made.
    */
    public void undoMove(){
        if(!game.canUndoMove())
            return;
        MoveValue move = game.undoMove();
        notifyBoardChanged(move);
    }

    /**
     * Undoes the given number of moves. Can be greater than the number of moves made.
     * @param numOfMoves the number of moves to undo.
     * @see ChessInterface#undoMove()
     */
    public void undoMultipleMoves(int numOfMoves){
        for(int i = 0; i < numOfMoves; i++){
            undoMove();
        }
    }

    /**
     * Sets the board back to the most recent position. Does nothing if there are no moves to redo.
     * @see ChessInterface#redoMove()
     */
    public void redoAllMoves(){
        while(game.canRedoMove()){
            redoMove();
        }
    }

    /**
     * Calculates if the current position is checkmate.
     * @return true if the current position is checkmate
     */
    public boolean isCheckmate(){
        return game.isCheckmate();
    }

    /**
     * Calculates if the current position is a draw.
     * @return true if the current position is a draw
     */
    public boolean isDraw(){
        return game.isDraw();
    }

    /**
     * Calculates a FEN string based on the current position
     * @return a FEN string
     */
    public String getFenString() {
        return fenGenerator.getFenString();
    }
}
