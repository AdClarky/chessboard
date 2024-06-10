import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;

public class Window extends JFrame {
    public Window(){
        super();
        setLayout(new GridLayout(8,8));
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);

        Color currentColour = Color.WHITE;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                add(new Square(new Pawn(x, y, Pawn.black), currentColour));
                currentColour = (currentColour == Color.WHITE) ? Color.BLACK : Color.WHITE;
            }
            currentColour = (currentColour == Color.WHITE) ? Color.BLACK : Color.WHITE;
        }

        setVisible(true);
    }
}
