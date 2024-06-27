import org.jetbrains.annotations.NotNull;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A regular chess board.
 * Only method for interaction is the moveWithValidation function.
 * At the end of a move boardChanged is called.
 * Can use getMoves to find the individual moves made - e.g. in castling what moves were made.
 */
public class Board {
    private BoardBoard board;
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);
    private final ArrayDeque<Move> moves = new ArrayDeque<>(40);
    private final ArrayDeque<Move> redoMoves = new ArrayDeque<>(40);
    private int currentTurn = Piece.WHITE_PIECE;
    private Move lastMoveMade;
    private int lastPawnOrCapture = 0;

    public Board(){
        board = new BoardBoard();
    }

    public int getCurrentTurn(){return currentTurn;}

    private void nextTurn(){
        currentTurn = currentTurn == Piece.WHITE_PIECE ? Piece.BLACK_PIECE : Piece.WHITE_PIECE;
    }

    /**
     * Calculates if moving a piece to a position would put that teams king in check.
     * This assumes the piece moving to the new position is a valid move.
     * @param newX the x position to move to.
     * @param newY the y position to move to.
     * @param pieceToCheck the piece being moved.
     * @return true if in check, false if not
     */
    boolean isMoveUnsafe(int newX, int newY, Piece pieceToCheck){
        Piece lastPiece = moves.isEmpty() ? null : moves.getFirst().getPiece();
        Move move = new Move(newX, newY, pieceToCheck, lastPiece, this);
        boolean isMoveUnsafe = isKingInCheck(pieceToCheck.getDirection());
        move.undo();
        return isMoveUnsafe;
    }

    /**
     * Checks if a given King is in check.
     * @param colour black or white king which will be checked
     * @return if the king is in check
     */
    boolean isKingInCheck(int colour){
        King king = board.getKing(colour);
        Coordinate kingPos = new Coordinate(king.getX(), king.getY());
        Iterable<Piece> enemyPieces = board.getColourPieces(colour * -1);
        for(Piece piece : enemyPieces){
            if(piece.getPossibleMoves(this).contains(kingPos)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks all possible moves that can be made and if any result in non-check.
     * @return if in checkmate.
     */
    boolean isCheckmate(){
        if(!isKingInCheck(currentTurn))
            return false;
        ArrayList<Piece> enemyPieces = board.getColourPieces(currentTurn);
        for (Piece enemyPiece : enemyPieces) {
            for (Coordinate move : enemyPiece.getPossibleMoves(this)) {
                if (!isMoveUnsafe(move.x(), move.y(), enemyPiece))
                    return false;
            }
        }
        return true;
    }

    /**
     * Calculates if the current position is a draw.
     * @param side the side which may have been stalemated
     * @return if its a draw
     */
    boolean isDraw(int side){
        return isStalemate(side) ||
                isDraw50Move() ||
                is3Repetition();
    }

    boolean isStalemate(int stalemateSide){
        if(isKingInCheck(stalemateSide))
            return false;
        Iterable<Piece> pieces = board.getColourPieces(stalemateSide);
        for(Piece piece : pieces){
            if(!piece.getPossibleMoves(this).isEmpty()){
                return false;
            }
        }
        return true;
    }

    boolean is3Repetition(){
        if(moves.size() < 8)
            return false;
        int boardState = board.getState();
        for(int i = 0; i < 2; i++){
            undoMultipleMovesSilent(4);
            if(boardState != board.getState()) {
                redoAllMovesSilent();
                return false;
            }
        }
        redoAllMovesSilent();
        return true;
    }

    boolean isDraw50Move(){
        return (moves.size() - lastPawnOrCapture) == 50;
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
        if(!board.getPiece(oldX, oldY).getPossibleMoves(this).contains(new Coordinate(newX, newY))) // if invalid move
            throw new InvalidMoveException("That isn't a valid move!");
        pushMove(oldX, oldY, newX, newY);
        nextTurn();
        notifyBoardChanged(oldX, oldY, newX, newY);
        if(isDraw(currentTurn))
            notifyDraw();
        if(isCheckmate()) {
            King king = board.getKing(currentTurn);
            notifyCheckmate(king.getX(), king.getY());
        }
    }

    /**
     * Creates a move and pushes it to the stack.
     * @param oldX current x position of the piece
     * @param oldY current y position of the piece
     * @param newX new x position of the piece
     * @param newY new y position of the piece
     */
    private void pushMove(int oldX, int oldY, int newX, int newY){
        Move move;
        if (moves.isEmpty())
            move = new Move(newX, newY, board.getPiece(oldX, oldY), null,this);
        else
            move = new Move(newX, newY, board.getPiece(oldX, oldY), moves.getFirst().getPiece(), this);
        if(move.getPiece() instanceof Pawn || move.hasTaken())
            lastPawnOrCapture = moves.size();
        lastMoveMade = move;
        moves.push(move);
    }

    /**
     * @see Board#makeMove(int, int, int,int)
     * @param move instance of move which specifies the piece being moved and where to.
     */
    public void makeMove(@NotNull MoveValue move) throws InvalidMoveException {
        makeMove(move.piece().getX(), move.piece().getY(), move.newX(), move.newY());
    }

    /**
     * Pops the last move off the stack and pushes it onto the redo moves stack.
     */
    public void undoMove(){
        if(moves.isEmpty())
            return;
        Move move = undoOneMove();
        notifyBoardChanged(move.getPiece().getX(), move.getPiece().getY(), move.getX(), move.getY());
    }

    private Move undoOneMove(){
        Move move = moves.pop();
        lastMoveMade = move;
        move.undo();
        redoMoves.push(move);
        return move;
    }

    public void undoMultipleMoves(int numOfMoves){
        for(int i = 0; i < numOfMoves; i++){
            undoMove();
        }
    }

    private void undoMultipleMovesSilent(int numOfMoves){
        for(int i = 0; i < numOfMoves; i++){
            undoOneMove();
        }
    }

    /**
     * Moves forward one move. Does nothing if there are no more moves to be made.
     * Pops the last move off the stack and pushes it onto the moves made stack.
     */
    public void redoMove(){
        if(redoMoves.isEmpty())
            return;
        Move move = redoOneMove();
        notifyBoardChanged(move.getPiece().getX(), move.getPiece().getY(), move.getX(), move.getY());
        if(isCheckmate()) {
            King king = board.getKing(currentTurn);
            notifyCheckmate(king.getX(), king.getY());
        }
    }

    private Move redoOneMove(){
        Move move = redoMoves.pop();
        lastMoveMade = move;
        move.makeMove();
        moves.push(move);
        return move;
    }

    /**
     * Sets the board back to the most recent position.
     * Can be called if there have been no move undo's made.
     */
    public void redoAllMoves(){
        while(!redoMoves.isEmpty()){
            redoMove();
        }
    }

    private void redoAllMovesSilent(){
        while(!redoMoves.isEmpty()){
            redoOneMove();
        }
    }

    /**
     * During a board change event, contains the moves performed on the board.
     * @return a list of individual moves taken to reach the new board state.
     */
    public Iterable<MoveValue> getLastMoveMade(){return lastMoveMade.getMovesToUndo();}

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
        King whiteKing = board.getKing(Piece.WHITE_PIECE);
        King blackKing = board.getKing(Piece.BLACK_PIECE);
        for(BoardListener listener : boardListeners)
            listener.draw(whiteKing.getX(), whiteKing.getY(), blackKing.getX(), blackKing.getY());
    }
}
