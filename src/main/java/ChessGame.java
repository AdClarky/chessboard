import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A regular chess board.
 * Only method for interaction is the moveWithValidation function.
 * At the end of a move boardChanged is called.
 * Can use getMoves to find the individual moves made - e.g. in castling what moves were made.
 */
public class ChessGame {
    private final Chessboard board;
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);
    private PieceColour currentTurn = PieceColour.WHITE;

    public ChessGame(){
        board = new ChessboardBuilder().defaultSetup();
    }

    public ChessGame(String fenString){
        board = new ChessboardBuilder().FromFen(fenString);
        currentTurn = board.getCurrentTurn();
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
     * @param oldX current x position of the piece
     * @param oldY current y position of the piece
     * @param newX new x position of the piece
     * @param newY new y position of the piece
     */
    public void makeMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
        redoAllMoves();
        board.makeMove(oldX, oldY, newX, newY);
        nextTurn();
        notifyBoardChanged(oldX, oldY, newX, newY);
        if(board.isDraw())
            notifyDraw();
        if(board.isCheckmate()) {
            King king = board.getKing(currentTurn);
            notifyCheckmate(king.getX(), king.getY());
        }
    }

    /**
     * @see ChessGame#makeMove(int, int, int,int)
     * @param move instance of move which specifies the piece being moved and where to.
     */
    public void makeMove(@NotNull MoveValue move) throws InvalidMoveException {
        makeMove(move.piece().getX(), move.piece().getY(), move.newX(), move.newY());
    }

    /**
     * During a board change event, contains the moves performed on the board.
     * @return a list of individual moves taken to reach the new board state.
     */
    public Iterable<MoveValue> getLastMoveMade(){return board.getLastMoveMade();}


    // TODO: test api listeners
    /**
     * Adds a board listener to receive events from this board.
     * Events which are notified are board change events, when a change has been made to the board and
     * checkmate events - when the king is checkmated.
     * @param listener the board listener
     */
    public void addBoardListener(BoardListener listener){
        boardListeners.add(listener);
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

    private void notifyDraw(){
        King whiteKing = board.getKing(PieceColour.WHITE);
        King blackKing = board.getKing(PieceColour.BLACK);
        for(BoardListener listener : boardListeners)
            listener.draw(whiteKing.getX(), whiteKing.getY(), blackKing.getX(), blackKing.getY());
    }

    /**
     * Moves forward one move. Does nothing if there are no more moves to be made.
     * Pops the last move off the stack and pushes it onto the moves made stack.
     */
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

    /**
     * Pops the last move off the stack and pushes it onto the redo moves stack.
     */
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

    /**
     * Sets the board back to the most recent position.
     * Can be called if there have been no move undo's made.
     */
    public void redoAllMoves(){
        while(board.canRedoMove()){
            redoMove();
        }
    }

    ArrayList<Piece> getColourPieces(PieceColour colour){return board.getColourPieces(colour);}

    Piece getPiece(int x, int y){return board.getPiece(x, y);}
    public int getState(){return board.getState();}
    public boolean isCheckmate(){return board.isCheckmate();}
    public boolean isDraw(){return board.isDraw();}
    public boolean isStalemate(){return board.isStalemate();}
    public boolean is3Repetition(){return board.is3Repetition();}
    public boolean isDraw50Move(){return board.isDraw50Move();}
}
