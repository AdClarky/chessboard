import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

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
    private final FenGenerator fenGenerator;
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);
    private PieceColour currentTurn = PieceColour.WHITE;

    public ChessGame(){
        board = new ChessboardBuilder().defaultSetup();
        fenGenerator = new FenGenerator(board);
    }

    public ChessGame(String fenString){
        board = new ChessboardBuilder().FromFen(fenString);
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
        board.makeMove(oldX, oldY, newX, newY);
        nextTurn();
        notifyMoveMade(oldX, oldY, newX, newY);
        if(board.isDraw())
            notifyDraw();
        if(board.isCheckmate()) {
            King king = board.getKing(currentTurn);
            notifyCheckmate(king.getX(), king.getY());
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

    private void notifyBoardChanged(int oldX, int oldY, int newX, int newY){
        for(BoardListener listener : boardListeners){
            listener.boardChanged(oldX, oldY, newX, newY);
        }
    }

    private void notifyCheckmate(int x, int y){
        for(BoardListener listener : boardListeners)
            listener.checkmate(x, y);
    }

    @TestOnly
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
        notifyBoardChanged(move.getPiece().getX(), move.getPiece().getY(), move.getX(), move.getY());
        if(board.isCheckmate()) {
            King king = board.getKing(currentTurn);
            notifyCheckmate(king.getX(), king.getY());
        }
    }

    /** Moves backwards one move. Does nothing if there are no more moves to be made. */
    public void undoMove(){
        Move move = board.undoMove();
        if(move == null)
            return;
        notifyBoardChanged(move.getPiece().getX(), move.getPiece().getY(), move.getX(), move.getY());
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

    public List<Piece> getColourPieces(PieceColour colour){return board.getAllColourPieces(colour);}
    public Piece getPiece(int x, int y){return board.getPiece(x, y);}
    public boolean isCheckmate(){return board.isCheckmate();}
    public boolean isDraw(){return board.isDraw();}

    public String getFenString() {
        return fenGenerator.getFenString();
    }
}
