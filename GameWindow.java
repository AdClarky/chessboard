import chessboard.Board;
import chessboard.BoardListener;
import chessboard.Coordinate;
import chessboard.Move;
import chessboard.Piece;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Window which links with chessboard package to display a chess board.
 * Click once to select, click again to either deselect or move.
 * Member of BoardListener and is updated when boardChanged is called.
 */
public class GameWindow extends JFrame implements BoardListener, MouseListener {
    private static final Color LIGHT_SQUARE = new Color(180, 180, 180);
    private static final Color DARK_SQUARE = new Color(124, 124, 124);
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private final Square[][] squares = new Square[8][8];
    private Square squareSelected;
    private final Board board;
    private Collection<Coordinate> possibleMoves = new ArrayList<>(8);

    /**
     * Creates a new window and populates it with squares which icons are set based on the board input.
     * @param board the board to be linked with.
     */
    public GameWindow(Board board){
        super();
        this.board = board;
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        Color currentColour = LIGHT_SQUARE;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                Piece piece = board.getPiece(x, y);
                squares[y][x] = new Square(piece, currentColour, x, y);
                squares[y][x].addMouseListener(this);
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
    private void squareClicked(Square square){
        Piece piece = square.getCurrentPiece();
        if(squareSelected == null && piece == null)
            return;
        if(piece == null){ // if clicked a blank square
            Piece pieceSelected = squareSelected.getCurrentPiece();
            board.moveWithValidation(pieceSelected.getX(), pieceSelected.getY(), square.getBoardX(), square.getBoardY());
            unselectSquare();
        } else if(piece.getDirection() == board.getTurn()) { // if the player's piece
            unselectSquare();
            squareSelected = square;
            showPossibleMoves(piece);
            squareSelected.selected();
        } else if(squareSelected != null){ // if enemy piece
            Piece pieceSelected = squareSelected.getCurrentPiece();
            board.moveWithValidation(pieceSelected.getX(), pieceSelected.getY(), square.getBoardX(), square.getBoardY());
            unselectSquare();
        }
    }

    private void unselectSquare(){
        if(squareSelected == null)
            return;
        squareSelected.unhighlight();
        squareSelected = null;
        showPossibleMoves(null);
    }

    private void showPossibleMoves(Piece piece) {
        for (Coordinate move : possibleMoves) { // remove old possible moves
            squares[move.getY()][move.getX()].unhighlight();
        }
        possibleMoves.clear();
        if(piece != null) {
            possibleMoves = piece.getPossibleMoves(board);
            for(Coordinate move : possibleMoves){
                squares[move.getY()][move.getX()].showPossibleMove();
            }
        }
    }

    @Override
    public void boardChanged(int oldX, int oldY, int newX, int newY) {
        for(Move move : board.getMovesMade()) {
            squares[move.getOldY()][move.getOldX()].setCurrentPiece(null);
            squares[move.getNewY()][move.getNewX()].setCurrentPiece(board.getPiece(move.getNewX(), move.getNewY()));
        }
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
}
