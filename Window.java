import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

public class Window extends JFrame implements BoardListener {
    private final Square[][] squares = new Square[8][8];
    private static Color light = new Color(180, 180, 180);
    private static Color dark = new Color(124, 124, 124);
    private final Board board = new Board();
    private final Mouse mouse = new Mouse(board);
    private ArrayList<Coordinate> possibleMoves = new ArrayList<>();

    public Window(){
        super();
        board.addBoardListener(this);
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);

        Color currentColour = light;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                squares[y][x] = new Square(board.getPiece(x,y), currentColour, new Coordinate(x, y));
                squares[y][x].addMouseListener(mouse);
                add(squares[y][x]);
                currentColour = (currentColour == light) ? dark : light;
            }
            currentColour = (currentColour == light) ? dark : light;
        }
        setVisible(true);
    }

    public Square findSquare(Piece piece){
        for(Square[] row : squares){
            for(Square square : row){
                if(piece.equals(square.getCurrentPiece()))
                    return square;
            }
        }
        return null;
    }

    @Override
    public void boardChanged(Piece piece) {
        Square square = findSquare(piece);
        square.clicked();
        squares[square.getCurrentPiece().getY()][square.getCurrentPiece().getX()].setCurrentPiece(piece);
        square.setCurrentPiece(null);
        for(Coordinate move : possibleMoves){
            squares[move.getY()][move.getX()].setPossibleMove(false);
        }
        possibleMoves.clear();
    }

    @Override
    public void pieceSelected(Piece piece) {
        if(piece == null)
            return;
        findSquare(piece).clicked();
        for(Coordinate move : possibleMoves){
            squares[move.getY()][move.getX()].setPossibleMove(false);
        }
        possibleMoves = piece.getPossibleMoves(board);
        for(Coordinate move : possibleMoves){
            squares[move.getY()][move.getX()].setPossibleMove(true);
        }
    }
}
