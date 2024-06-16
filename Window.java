import ChessBoard.Board;
import ChessBoard.BoardListener;
import ChessBoard.Coordinate;
import ChessBoard.Piece;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Window extends JFrame implements BoardListener, MouseListener {
    private final Square[][] squares = new Square[8][8];
    private Piece pieceSelected = null;
    private static Color light = new Color(180, 180, 180);
    private static Color dark = new Color(124, 124, 124);
    private final Board board;
    private ArrayList<Coordinate> possibleMoves = new ArrayList<>();

    public Window(Board board){
        super();
        this.board = board;
        board.addBoardListener(this);
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);

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

    public Square findPiece(Piece piece){
        for(Square[] row : squares){
            for(Square square : row){
                if(piece.equals(square.getCurrentPiece()))
                    return square;
            }
        }
        return null;
    }

    public void squareClicked(Square square){
        Piece piece = square.getCurrentPiece();
        if(piece == null){ // if clicked a blank square
            if(pieceSelected == null) {
                unselectSquare(square);
                return;
            }
            if(!board.moveAndValidatePiece(pieceSelected.getX(), pieceSelected.getY(), square.getBoardX(), square.getBoardY()))
                unselectSquare(findPiece(pieceSelected));
        } else if(piece.getDirection() == board.getTurn()) { // if the player's piece
            pieceSelected = piece;
            showPossibleMoves(piece);
            square.selected();
        } else if(pieceSelected != null){ // if enemy piece
            if(!board.moveAndValidatePiece(pieceSelected.getX(), pieceSelected.getY(), square.getBoardX(), square.getBoardY()))
                unselectSquare(findPiece(pieceSelected));
        }
    }

    public void unselectSquare(Square square){
        square.unhighlight();
        pieceSelected = null;
        showPossibleMoves(null);
    }

    public void showPossibleMoves(Piece piece) {
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
        Square square = squares[oldY][oldX];
        square.unhighlight();
        squares[oldY][oldX].setCurrentPiece(board.getPiece(oldX, oldY));
        squares[newY][newX].setCurrentPiece(board.getPiece(newX, newY));
        showPossibleMoves(null);
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
