import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;

public class Window extends JFrame {
    private final Square[][] squares = new Square[8][8];
    private static Color light = new Color(180, 180, 180);
    private static Color dark = new Color(124, 124, 124);

    public Window(){
        super();
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);

        Color currentColour = light;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                squares[y][x] = new Square(null, currentColour);
                add(squares[y][x]);
                currentColour = (currentColour == light) ? dark : light;
            }
            currentColour = (currentColour == light) ? dark : light;
        }
        setupBoard();
        setVisible(true);
    }

    private void setupBoard(){
        squares[0][0].setCurrentPiece(new Rook(0, 0, Rook.white));
        squares[0][1].setCurrentPiece(new Knight(1, 0, Knight.white));
        squares[0][2].setCurrentPiece(new Bishop(2, 0, Bishop.white));
        squares[0][3].setCurrentPiece(new Queen(3, 0, Queen.white));
        squares[0][4].setCurrentPiece(new King(4, 0, King.white));
        squares[0][5].setCurrentPiece(new Bishop(5, 0, Bishop.white));
        squares[0][6].setCurrentPiece(new Knight(6, 0, Knight.white));
        squares[0][7].setCurrentPiece(new Rook(7, 0, Rook.white));
        squares[7][0].setCurrentPiece(new Rook(0, 0, Rook.black));
        squares[7][1].setCurrentPiece(new Knight(1, 0, Knight.black));
        squares[7][2].setCurrentPiece(new Bishop(2, 0, Bishop.black));
        squares[7][3].setCurrentPiece(new Queen(3, 0, Queen.black));
        squares[7][4].setCurrentPiece(new King(4, 0, King.black));
        squares[7][5].setCurrentPiece(new Bishop(5, 0, Bishop.black));
        squares[7][6].setCurrentPiece(new Knight(6, 0, Knight.black));
        squares[7][7].setCurrentPiece(new Rook(7, 0, Rook.black));
        for(int x = 0; x < 8; x++){
            squares[6][x].setCurrentPiece(new Pawn(x, 6, Pawn.black));
            squares[1][x].setCurrentPiece(new Pawn(x, 1, Pawn.white));
        }
    }
}
