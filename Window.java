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
public class Window extends JFrame implements BoardListener, MouseListener {
    private static final Color light = new Color(180, 180, 180);
    private static final Color dark = new Color(124, 124, 124);
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private final Square[][] squares = new Square[8][8];
    private Square squareSelected = null;
    private final Board board;
    private Collection<Coordinate> possibleMoves = new ArrayList<>(8);

    /**
     * Creates a new window and populates it with squares which icons are set based on the board input.
     * @param board the board to be linked with - adds itself as a listener.
     */
    public Window(Board board){
        super();
        this.board = board;
        board.addBoardListener(this);
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        Color currentColour = light;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                squares[y][x] = new Square(board.getPiece(x,y), currentColour, x, y);
                squares[y][x].addMouseListener(this);
                add(squares[y][x]);
                currentColour = (currentColour == light) ? dark : light;
            }
            currentColour = (currentColour == light) ? dark : light;
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
        if(piece == null){ // if clicked a blank square
            if(squareSelected == null)
                return;
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
