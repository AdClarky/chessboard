package window;

import chessboard.ChessInterface;
import common.BoardListener;
import common.Coordinate;
import common.PieceColour;
import common.PieceValue;
import common.Pieces;
import exception.InvalidMoveException;
import org.jetbrains.annotations.NotNull;

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
    private final ChessInterface board;
    private final PieceColour turn;
    private PieceColour currentTurn;
    private Collection<PieceValue> pieces = new ArrayList<>();
    private Collection<Coordinate> possibleMoves = new ArrayList<>(8);
    private Square checkmated;

    public GameWindow(ChessInterface board, PieceColour colour){
        super();
        this.board = board;
        turn = colour;
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        Color currentColour = LIGHT_SQUARE;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                squares[y][x] = new Square(new PieceValue(new Coordinate(x, y), Pieces.BLANK, null), currentColour);
                squares[y][x].addMouseListener(this);
                squares[y][x].addKeyListener(this);
                add(squares[y][x]);
                currentColour = (currentColour == LIGHT_SQUARE) ? DARK_SQUARE : LIGHT_SQUARE;
            }
            currentColour = (currentColour == LIGHT_SQUARE) ? DARK_SQUARE : LIGHT_SQUARE;
        }
        updateBoard();
        setVisible(true);
    }

    public Square getSquare(Coordinate position){
        return squares[position.y()][position.x()];
    }

    /**
     * Run when a square is clicked.
     * Window tracks which square is currently highlighted.
     * Logic is handled here for clicking e.g. clicked on an enemy square etc
     * @param square the square which has been clicked
     */
    private void squareClicked(@NotNull Square square){
        board.redoAllMoves();
        if((squareSelected == null && square.isBlank()) || board.getCurrentTurn() != currentTurn)
            return;
        PieceColour clickedColour = board.getColour(square.getPosition());
        if(square.isBlank() || clickedColour != board.getCurrentTurn()){ // if clicked a blank or enemy square
            try {
                board.makeMove(squareSelected.getPosition(), square.getPosition());
            } catch (InvalidMoveException e) {
                System.err.println(e.getMessage());
            }
            unselectSquare();
        } else { // if the player's piece
            unselectSquare();
            showPossibleMoves(square.getPosition());
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
     * @param square the square of the piece whose possible moves will be calculated
     */
    private void showPossibleMoves(@NotNull Coordinate square) {
        unhighlightPossibleMoves();
        possibleMoves = board.getPossibleMoves(square);
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

    public void updateBoard(){
        String fenString = board.getFenString();
        FenParser fen = new FenParser(fenString);
        Collection<PieceValue> newPieces = fen.getPieces();
        removeMovedPieces(newPieces);
        updateMovedPieces(newPieces);
        pieces = newPieces;
        currentTurn = board.getCurrentTurn();
    }

    private void removeMovedPieces(Collection<PieceValue> newPieces){
        Collection<PieceValue> movedPieces = new ArrayList<>(pieces);
        movedPieces.removeAll(newPieces);
        for(PieceValue piece : movedPieces){
            getSquare(piece.position()).setCurrentPiece(PieceValue.blank());
        }
    }

    private void updateMovedPieces(Collection<PieceValue> newPieces){
        Collection<PieceValue> movedPieces = new ArrayList<>(newPieces);
        movedPieces.removeAll(pieces);
        for(PieceValue piece : movedPieces){
            getSquare(piece.position()).setCurrentPiece(piece);
        }
    }

    @Override
    public void boardChanged(Coordinate oldPos, Coordinate newPos) {
        updateBoard();
    }

    @Override
    public void moveMade(Coordinate oldPos, Coordinate newPos) {
        updateBoard();
        currentTurn = board.getCurrentTurn();
    }

    @Override
    public void checkmate(Coordinate kingPos) {
        checkmated = squares[kingPos.y()][kingPos.x()];
        squares[kingPos.y()][kingPos.x()].showPossibleMove();
    }

    @Override
    public void draw(Coordinate white, Coordinate black) {

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
            board.undoMove();
            if(checkmated != null) {
                checkmated.unhighlight();
                checkmated = null;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            board.redoMove();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
