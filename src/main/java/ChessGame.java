import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * A chess game api. The board can be built using a FEN string or just in the default position.
 * Add a {@link BoardListener} to be notified of events. At the end of a move boardChanged is called.
 * After a move has been made, {@link ChessGame#getLastMoveMade()} to find the individual moves made - e.g. in castling
 * what moves were made.
 * @author Toby
 */
public class ChessGame {
    private final Chessboard board;
    private final ChessLogic ChessLogic;
    private final FenGenerator fenGenerator;
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);
    private PieceColour currentTurn = PieceColour.WHITE;

    public ChessGame(){
        board = new ChessboardBuilder().defaultSetup();
        ChessLogic = new ChessLogic(board);
        fenGenerator = new FenGenerator(board);
    }

    public ChessGame(String fenString){
        board = new ChessboardBuilder().FromFen(fenString);
        ChessLogic = new ChessLogic(board);
        currentTurn = board.getCurrentTurn();
        fenGenerator = new FenGenerator(board);
    }

    public PieceColour getCurrentTurn(){return currentTurn;}

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
     */
    public void makeMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
        redoAllMoves();
        if(ChessLogic.isValidMove(getPiece(oldX, oldY), newX, newY))
            throw new InvalidMoveException(oldX, oldY, newX, newY);
        board.makeMove(oldX, oldY, newX, newY);
        nextTurn();
        notifyMoveMade(oldX, oldY, newX, newY);
        if(ChessLogic.isDraw())
            notifyDraw();
        if(ChessLogic.isCheckmate()) {
            notifyCheckmate(board.getKing(currentTurn));
        }
    }

    /**
     * @see ChessGame#makeMove(int, int, int,int)
     */
    public void makeMove(@NotNull MoveValue move) throws InvalidMoveException {
        makeMove(move.piece().getX(), move.piece().getY(), move.newX(), move.newY());
    }

    /**
     * After {@link BoardListener#moveMade(int, int, int, int)}, this returns the individual moves performed on the
     * board.
     * @return a list of individual moves taken to reach the new board state. */
    public Iterable<MoveValue> getLastMoveMade(){return board.getLastMoves();}

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

    /** Moves forward one move. Does nothing if there are no more moves to be made. */
    public void redoMove(){
        Move move = board.redoMove();
        if(move == null)
            return;
        notifyBoardChanged(move);
        if(ChessLogic.isCheckmate()) {
            notifyCheckmate(board.getKing(currentTurn));
        }
    }

    /** Moves backwards one move. Does nothing if there are no more moves to be made. */
    public void undoMove(){
        Move move = board.undoMove();
        if(move == null)
            return;
        notifyBoardChanged(move);
    }

    public void undoMultipleMoves(int numOfMoves){
        for(int i = 0; i < numOfMoves; i++){
            undoMove();
        }
    }

     /** Sets the board back to the most recent position. Does nothing if there are no moves to redo. */
    public void redoAllMoves(){
        while(board.canRedoMove()){
            redoMove();
        }
    }

    public Set<Piece> getColourPieces(PieceColour colour){
        return board.getAllColourPieces(colour);
    }

    public Piece getPiece(int x, int y){
        return board.getPiece(x, y);
    }

    public boolean isCheckmate(){
        return ChessLogic.isCheckmate();
    }

    public boolean isDraw(){
        return ChessLogic.isDraw();
    }

    public String getFenString() {
        return fenGenerator.getFenString();
    }

    public ChessLogic getChessLogic(){
        return ChessLogic;
    }
}
