import org.jetbrains.annotations.NotNull;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A regular chess board.
 * Only method for interaction is the moveWithValidation function.
 * At the end of a move boardChanged is called.
 * Can use getMoves to find the individual moves made - e.g. in castling what moves were made.
 */
public class Board {
    private final Collection<BoardListener> boardListeners = new ArrayList<>(1);
    private int currentTurn = Piece.WHITE_PIECE;
    private final Piece[][] board  =  new Piece[8][8];
    private Move lastMoveMade;
    private final ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private final ArrayList<Piece> whitePieces = new ArrayList<>(16);
    private final ArrayDeque<Move> moves = new ArrayDeque<>(40);
    private final ArrayDeque<Move> redoMoves = new ArrayDeque<>(40);

    /**
     * Initialises the board with the pieces in default positions.
     * Blank squares are null.
     */
    public Board(){
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Knight(1, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Bishop(2, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Queen(4, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Bishop(5, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Knight(6, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Rook(7, 0, Piece.WHITE_PIECE));
        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Rook(0, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Knight(1, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Bishop(2, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Queen(4, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Bishop(5, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Knight(6, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Rook(7, 7, Piece.BLACK_PIECE));
        for(int x = 0; x < 8; x++){
            blackPieces.add(new Pawn(x, 6, Piece.BLACK_PIECE));
            whitePieces.add(new Pawn(x, 1, Piece.WHITE_PIECE));
        }
        for(int y = 2; y < 6; y++){
            for(int x = 0; x < 8; x++){
                board[y][x] = new Blank(x, y);
            }
        }
        for(Piece piece : blackPieces){
            board[piece.getY()][piece.getX()] = piece;
        }
        for(Piece piece : whitePieces){
            board[piece.getY()][piece.getX()] = piece;
        }
    }

    /**
     * Intiialises the board with only the pieces in the collection. Their position on the board
     * is determined by their x and y values. Every other square is blank.
     * @param whitePieces
     * @param blackPieces
     */
    public Board(Collection<Piece> whitePieces, Collection<Piece> blackPieces){
        this.whitePieces.addAll(whitePieces);
        this.blackPieces.addAll(blackPieces);
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                board[y][x] = new Blank(x, y);
            }
        }
        for(Piece piece : blackPieces){
            board[piece.getY()][piece.getX()] = piece;
        }
        for(Piece piece : whitePieces){
            board[piece.getY()][piece.getX()] = piece;
        }
    }

    /**
     * Finds the piece in a specific square
     * @param x x position
     * @param y y position
     * @return the piece on that square.
     */
    @NotNull
    public Piece getPiece(int x, int y){
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return new Blank(x, y);
        return board[y][x];
    }

    /**
     * Checks if a square is blank or contains a piece
     * @param x x value of square to check
     * @param y y value of square to check
     * @return if the square is blank
     */
    public boolean isSquareBlank(int x, int y){return board[y][x] instanceof Blank;}

    void setSquare(int x, int y, Piece piece){board[y][x] = piece;}

    ArrayList<Piece> getColourPieces(int direction){
        if(direction == Piece.BLACK_PIECE)
            return blackPieces;
        return whitePieces;
    }

    List<Piece> getColourPieces(@NotNull Piece piece){
        if(piece.getDirection() == Piece.BLACK_PIECE)
            return blackPieces;
        return whitePieces;
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
        Move move = new Move(newX, newY, pieceToCheck, this);
        boolean isMoveUnsafe = isKingInCheck(pieceToCheck.getDirection());
        move.undo();
        return isMoveUnsafe;
    }

    /**
     * Checks if a given King is in check.
     * @param direction black or white king which will be checked
     * @return if the king is in check
     */
    boolean isKingInCheck(int direction){
        King king = (King) getColourPieces(direction).getFirst();
        Coordinate kingPos = new Coordinate(king.getX(), king.getY());
        Iterable<Piece> enemyPieces = getColourPieces(direction * -1);
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
        ArrayList<Piece> enemyPieces = getColourPieces(currentTurn);
        for(int i = 0; i < enemyPieces.size(); i++){
            Piece piece = enemyPieces.get(i);
            for(Coordinate move : piece.getPossibleMoves(this)){
                if(!isMoveUnsafe(move.x(), move.y(), piece))
                    return false;
            }
        }
        return true;
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
    public void moveWithValidation(int oldX, int oldY, int newX, int newY){
        redoAllMoves();
        Piece piece = getPiece(oldX, oldY);
        if(!piece.getPossibleMoves(this).contains(new Coordinate(newX, newY))) // if invalid move
            return;
        Move move = new Move(newX,newY,board[oldY][oldX],this); // makes a move
        piece.firstMove(); // if a piece has a first move constraint e.g. pawn, rook, king activates it
        if(!moves.isEmpty() && moves.getFirst().getPiece() instanceof Pawn previousPawn) // stops previous pawn from being en passanted
            previousPawn.setCanBePassanted(false);
        moves.push(move);
        lastMoveMade = moves.getFirst();
        nextTurn();
        notifyBoardChanged(oldX, oldY, newX, newY);
        if(isCheckmate()) {
            King king = (King) getColourPieces(currentTurn).getFirst();
            notifyCheckmate(king.getX(), king.getY());
        }
    }

    /**
     * @see Board#moveWithValidation(int, int, int,int)
     * @param move instance of move which specifies the piece being moved and where to.
     */
    public void moveWithValidation(@NotNull MoveValue move){
        moveWithValidation(move.piece().getX(), move.piece().getY(), move.newX(), move.newY());
    }

    /**
     * Pops the last move off the stack and pushes it onto the redo moves stack.
     */
    public void undoMove(){
        if(moves.isEmpty())
            return;
        Move move = moves.pop();
        lastMoveMade = move;
        move.undo();
        redoMoves.push(move);
        notifyBoardChanged(move.getPiece().getX(), move.getPiece().getY(), move.getX(), move.getY());
    }

    /**
     * Moves forward one move. Does nothing if there are no more moves to be made.
     * Pops the last move off the stack and pushes it onto the moves made stack.
     */
    public void redoMove(){
        if(redoMoves.isEmpty())
            return;
        Move move = redoMoves.pop();
        lastMoveMade = move;
        move.makeMove();
        moves.push(move);
        notifyBoardChanged(move.getPiece().getX(), move.getPiece().getY(), move.getX(), move.getY());
        if(isCheckmate()) {
            King king = (King) getColourPieces(currentTurn).getFirst();
            notifyCheckmate(king.getX(), king.getY());
        }
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

    /**
     * During a board change event, contains the moves performed on the board.
     * @return a list of individual moves taken to reach the new board state.
     */
    public Iterable<MoveValue> getLastMoveMade(){return lastMoveMade.getMovesToUndo();}

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
}
