import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class Window extends JFrame implements MouseListener{
    private final Square[][] squares = new Square[8][8];
    private static Color light = new Color(180, 180, 180);
    private static Color dark = new Color(124, 124, 124);
    private final Board board = new Board();

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
                squares[y][x].addMouseListener(this);
                add(squares[y][x]);
                currentColour = (currentColour == light) ? dark : light;
            }
            currentColour = (currentColour == light) ? dark : light;
        }
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() instanceof Square){
            for(int[] moves : ((Square) e.getSource()).getCurrentPiece().possibleMoves(board)) {
                System.out.println(ChessUtils.coordsToChess(moves[0], moves[1]));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
