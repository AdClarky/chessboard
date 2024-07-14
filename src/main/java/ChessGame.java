import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A chess game api. The board can be built using a FEN string or just in the default position.
 * Add a {@link BoardListener} to be notified of events. At the end of a move boardChanged is called.
 * After a move has been made, {@link ChessGame#getLastMoveMade()} to find the individual moves made - e.g. in castling
 * what moves were made.
 * @author Toby
 */
public class ChessGame {
    private final Chessboard board;
    private final ChessLogic chessLogic;
    private final FenGenerator fenGenerator;
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);
    private PieceColour currentTurn = PieceColour.WHITE;

    /**
     * Creates a {@code ChessGame} with the pieces in the default position.
     */
    public ChessGame(){
        board = new ChessboardBuilder().defaultSetup();
        chessLogic = new ChessLogic(board);
        fenGenerator = new FenGenerator(board);
    }

    /**
     * Creates a {@code ChessGame} with the setup based on a FEN String.
     * @param fenString a FEN string which details a board position
     * @throws InvalidFenStringException when the give FEN string is invalid
     */
    public ChessGame(String fenString) throws InvalidFenStringException {
        board = new ChessboardBuilder().fromFen(fenString);
        chessLogic = new ChessLogic(board);
        currentTurn = board.getCurrentTurn();
        fenGenerator = new FenGenerator(board);
    }

    /**
     * The current turn of the board, i.e. black or white.
     * @return the current turn
     */
    public PieceColour getCurrentTurn(){
        return currentTurn;
    }

    private void nextTurn(){
        currentTurn = currentTurn == PieceColour.WHITE ? PieceColour.BLACK : PieceColour.WHITE;
        board.setCurrentTurn(currentTurn);
    }

    /**
     * Moves a piece to a new location while validating it is a valid move.
     * Assumes the provided old coordinates are valid coordinates for a piece.
     * If any moves have been undone, it sets the board back to the current position.
     * After a move has been made, it notifies all listeners then moves on to the next turn.
     * Checks for checkmate and draws.
     * @param oldX the previous x position
     * @param oldY the previous y position
     * @param newX the new x position
     * @param newY the new y position
     * @throws InvalidMoveException when the move given is not a valid move.
     */
    public void makeMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
        redoAllMoves();
        if(ChessLogic.isValidMove(getPiece(oldX, oldY), newX, newY))
            throw new InvalidMoveException(oldX, oldY, newX, newY);
        board.makeMove(oldX, oldY, newX, newY);
        nextTurn();
        chessLogic.calculatePossibleMoves();
        notifyMoveMade(oldX, oldY, newX, newY);
        if(chessLogic.isDraw())
            notifyDraw();
        if(chessLogic.isCheckmate()) {
            notifyCheckmate(board.getKing(currentTurn));
        }
    }

    /**
     * {@link ChessGame#makeMove(int, int, int, int)}
     * @param chessMove a chess move in algabraic notation
     * @throws InvalidMoveException when the move given is not a valid move
     */
    public void makeMove(@NotNull String chessMove) throws InvalidMoveException {
        MoveValue move = ChessUtils.chessToMove(board, chessMove);
        makeMove(move.piece().getX(), move.piece().getY(), move.newX(), move.newY());
    }

    /**
     * After {@link BoardListener#moveMade(int, int, int, int)} or
     * {@link BoardListener#boardChanged(int, int, int, int)},
     * this returns the individual moves performed on the board.
     * @return a list of individual moves taken to reach the new board state. */
    public List<MoveValue> getLastMoveMade(){
        return board.getLastMoves();
    }

    /**
     * Adds the given BoardListener to receive events from this board.
     * @param listener the board listener
     */
    public void addBoardListener(BoardListener listener){
        boardListeners.add(listener);
    }

    private void notifyMoveMade(int oldX, int oldY, int newX, int newY){
        for(BoardListener listener : boardListeners){
            listener.moveMade(oldX, oldY, newX, newY);
        }
    }

    private void notifyBoardChanged(@NotNull Move move){
        int oldX = move.getPieceX(), oldY = move.getPieceY();
        for(BoardListener listener : boardListeners){
            listener.boardChanged(oldX, oldY, move.getX(), move.getY());
        }
    }

    private void notifyCheckmate(King king){
        int x = king.getX(), y = king.getY();
        for(BoardListener listener : boardListeners)
            listener.checkmate(x, y);
    }

    private void notifyDraw(){
        King whiteKing = board.getKing(PieceColour.WHITE);
        King blackKing = board.getKing(PieceColour.BLACK);
        for(BoardListener listener : boardListeners)
            listener.draw(whiteKing.getX(), whiteKing.getY(), blackKing.getX(), blackKing.getY());
    }

    /**
     * Moves forward one move. Does nothing if there are no more moves to be made.
    */
    public void redoMove(){
        Move move = board.redoMove();
        if(move == null)
            return;
        chessLogic.calculatePossibleMoves();
        notifyBoardChanged(move);
        if(chessLogic.isCheckmate()) {
            notifyCheckmate(board.getKing(currentTurn));
        }
    }

    /**
     * Moves backwards one move. Does nothing if there are no more moves to be made.
    */
    public void undoMove(){
        Move move = board.undoMove();
        if(move == null)
            return;
        chessLogic.calculatePossibleMoves();
        notifyBoardChanged(move);
    }

    /**
     * Undoes the given number of moves. Can be greater than the number of moves made.
     * @param numOfMoves the number of moves to undo.
     * @see ChessGame#undoMove()
     */
    public void undoMultipleMoves(int numOfMoves){
        for(int i = 0; i < numOfMoves; i++){
            undoMove();
        }
    }

    /**
     * Sets the board back to the most recent position. Does nothing if there are no moves to redo.
     * @see ChessGame#redoMove()
     */
    public void redoAllMoves(){
        while(board.canRedoMove()){
            redoMove();
        }
    }

    /**
     * Creates a collection of a specific colour of pieces and returns it.
     * @param colour the coloured pieces desired.
     * @return a collection of colour pieces
     */
    public Collection<Piece> getColourPieces(PieceColour colour){
        return board.getAllColourPieces(colour);
    }

    /**
     * Gets the piece located at x and y.
     * Returns {@link Blank} with invalid x and y if the x and y entered are out of range
     * or if there is no piece on that square.
     * @param x x position
     * @param y y position
     * @return Piece in that position
     */
    public Piece getPiece(int x, int y){
        return board.getPiece(x, y);
    }

    /**
     * Calculates if the current position is checkmate.
     * @return true if the current position is checkmate
     */
    public boolean isCheckmate(){
        return chessLogic.isCheckmate();
    }

    /**
     * Calculates if the current position is a draw.
     * @return true if the current position is a draw
     */
    public boolean isDraw(){
        return chessLogic.isDraw();
    }

    /**
     * Calculates a FEN string based on the current position
     * @return a FEN string
     */
    public String getFenString() {
        return fenGenerator.getFenString();
    }
}
