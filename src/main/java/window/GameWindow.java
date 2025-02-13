import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Window which links with chessboard package to display a chess board.
 * Click once to select, click again to either deselect or move.
 * Member of BoardListener and is updated when boardChanged is called.
 */
public class GameWindow extends JFrame implements BoardListener, MouseListener, KeyListener {
    private static final Color LIGHT_SQUARE = new Color(180, 180, 180);
    private static final Color DARK_SQUARE = new Color(124, 124, 124);
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private final Square[][] squares = new Square[8][8];
    private Square squareSelected;
    private final ChessGame chessGame;
    private final PieceColour turn;
    private PieceColour currentTurn;
    private Collection<Coordinate> possibleMoves = new ArrayList<>(8);
    private Square checkmated;

    public GameWindow(ChessGame chessGame, PieceColour colour){
        super();
        this.chessGame = chessGame;
        this.turn = colour;
        if(colour == PieceColour.BLANK)
            currentTurn = PieceColour.WHITE;
        else
            currentTurn = colour;
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        Color currentColour = LIGHT_SQUARE;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                Piece piece = chessGame.getPiece(x, y);
                squares[y][x] = new Square(piece, currentColour, x, y);
                squares[y][x].addMouseListener(this);
                squares[y][x].addKeyListener(this);
                add(squares[y][x]);
                currentColour = (currentColour == LIGHT_SQUARE) ? DARK_SQUARE : LIGHT_SQUARE;
            }
            currentColour = (currentColour == LIGHT_SQUARE) ? DARK_SQUARE : LIGHT_SQUARE;
        }
        setVisible(true);
    }

    /**
     * Run when a square is clicked.
     * Window tracks which square is currently highlighted.
     * Logic is handled here for clicking e.g. clicked on an enemy square etc
     * @param square the square which has been clicked
     */
    private void squareClicked(@NotNull Square square){
        chessGame.redoAllMoves();
        if((squareSelected == null && square.isBlank()) || chessGame.getCurrentTurn() != currentTurn)
            return;
        Piece piece = square.getCurrentPiece();
        if(square.isBlank() || piece.getColour() != chessGame.getCurrentTurn()){ // if clicked a blank or enemy square
            Piece pieceSelected = squareSelected.getCurrentPiece();
            try {
                chessGame.makeMove(pieceSelected.getX(), pieceSelected.getY(), square.getBoardX(), square.getBoardY());
            } catch (InvalidMoveException e) {
                System.err.println(e.getMessage());
            }
            unselectSquare();
        } else { // if the player's piece
            unselectSquare();
            showPossibleMoves(piece);
            squareSelected = square;
            squareSelected.selected();
        }
    }

    /**
     * Unselects the selected square tracked by {@link #squareSelected}.
     * Also, unhighlights any possible moves.
     */
    private void unselectSquare(){
        if(squareSelected == null)
            return;
        squareSelected.unhighlight();
        squareSelected = null;
        unhighlightPossibleMoves();
    }

    /**
     * Unhighlights the previously highlighted squares then highlights the new possible squares.
     * @param piece the piece whose possible moves will be calculated
     */
    private void showPossibleMoves(@NotNull Piece piece) {
        unhighlightPossibleMoves();
        possibleMoves = piece.getPossibleMoves();
        for(Coordinate move : possibleMoves){
            squares[move.y()][move.x()].showPossibleMove();
        }
    }

    private void unhighlightPossibleMoves(){
        for (Coordinate move : possibleMoves) { // remove old possible moves
            squares[move.y()][move.x()].unhighlight();
        }
        possibleMoves.clear();
    }

    @Override
    public void boardChanged(int oldX, int oldY, int newX, int newY) {
        for(MoveValue move : chessGame.getLastMoveMade()) {
            Square oldSquare = findPiece(move.piece());
            Piece oldSquarePiece = chessGame.getPiece(oldSquare.getBoardX(), oldSquare.getBoardY());
            oldSquare.setCurrentPiece(oldSquarePiece);
            Piece newSquarePiece = chessGame.getPiece(move.newX(), move.newY());
            squares[move.newY()][move.newX()].setCurrentPiece(newSquarePiece);
        }
    }

    @Nullable
    private Square findPiece(Piece piece){
        for(Square[] row : squares){
            for(Square square : row){
                if(square.getCurrentPiece().equals(piece)){
                    return square;
                }
            }
        }
        return null;
    }

    @Override
    public void moveMade(int i, int i1, int i2, int i3) {
        boardChanged(0, 0, 0, 0);
        if(turn == PieceColour.BLANK)
            currentTurn = chessGame.getCurrentTurn();
    }

    @Override
    public void checkmate(int x, int y) {
        checkmated = squares[y][x];
        squares[y][x].showPossibleMove();
    }

    @Override
    public void draw(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!(e.getSource() instanceof Square clickedSquare))
            return;
        squareClicked(clickedSquare);
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            chessGame.undoMove();
            if(checkmated != null) {
                checkmated.unhighlight();
                checkmated = null;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            chessGame.redoMove();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
