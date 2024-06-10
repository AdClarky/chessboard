import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;

public class Window extends JFrame {
    private final Square[][] squares = new Square[8][8];
    private static Color light = new Color(180, 180, 180);
    private static Color dark = new Color(124, 124, 124);
    private final Board board = new Board();
    private final Mouse mouse = new Mouse(board);

    public Window(){
        super();
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);

        Color currentColour = light;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                squares[y][x] = new Square(board.getPiece(x,y), currentColour);
                squares[y][x].addMouseListener(mouse);
                add(squares[y][x]);
                currentColour = (currentColour == light) ? dark : light;
            }
            currentColour = (currentColour == light) ? dark : light;
        }
        setVisible(true);
    }
}
