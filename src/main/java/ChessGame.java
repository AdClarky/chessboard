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
        fenGenerator = new FenGenerator(board);
    }

    /**
     * The current turn of the board, i.e. black or white.
     * @return the current turn
     */
    public PieceColour getCurrentTurn(){
        return board.getCurrentTurn();
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
        if(ChessLogic.isValidMove(getPiece(oldPos), newPos))
            throw new InvalidMoveException(oldPos, newPos);
        board.makeMove(oldPos, newPos);
        chessLogic.calculatePossibleMoves();
        notifyMoveMade(oldPos, newPos);
        if(chessLogic.isDraw())
            notifyDraw();
        if(chessLogic.isCheckmate()) {
            notifyCheckmate(board.getKingPos(board.getCurrentTurn()));
        }
    }

    /**
     * {@link ChessGame#makeMove(Coordinate, Coordinate)}
     * @param chessMove a chess move in algabraic notation
     * @throws InvalidMoveException when the move given is not a valid move
     */
    public void makeMove(@NotNull String chessMove) throws InvalidMoveException {
        MoveValue move = ChessUtils.chessToMove(board, chessMove);
        makeMove(move.oldPos(), move.newPos());
    }

    /**
     * After {@link BoardListener#moveMade(Coordinate, Coordinate)} or
     * {@link BoardListener#boardChanged(Coordinate, Coordinate)},
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

    private void notifyMoveMade(Coordinate oldPos, Coordinate newPos){
        for(BoardListener listener : boardListeners){
            listener.moveMade(oldPos, newPos);
        }
    }

    private void notifyBoardChanged(@NotNull Move move){
        for(BoardListener listener : boardListeners){
            listener.boardChanged(move.getOldPos(), move.getNewPos());
        }
    }

    private void notifyCheckmate(Coordinate kingPos){
        for(BoardListener listener : boardListeners)
            listener.checkmate(kingPos);
    }

    private void notifyDraw(){
        Coordinate whitePos = board.getKingPos(PieceColour.WHITE);
        Coordinate blackPos = board.getKingPos(PieceColour.BLACK);
        for(BoardListener listener : boardListeners)
            listener.draw(whitePos, blackPos);
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
            notifyCheckmate(board.getKingPos(board.getCurrentTurn()));
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
    public Collection<Coordinate> getColourPieces(PieceColour colour){
        return board.getAllColourPositions(colour);
    }

    /**
     * Gets the piece located at x and y.
     *
     * @param pos@return Piece in that position
     */
    public Pieces getPiece(Coordinate pos){
        return board.getPiece(pos);
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

    public boolean isInCheck(){
        return chessLogic.isKingInCheck(getCurrentTurn());
    }

    public Coordinate getEnPassantSquare() {
        return board.getEnPassantSquare();
    }
}
