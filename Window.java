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
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                add(new Square(currentColour));
                currentColour = (currentColour == Color.WHITE) ? Color.BLACK : Color.WHITE;
            }
            currentColour = (currentColour == Color.WHITE) ? Color.BLACK : Color.WHITE;
        }

        setVisible(true);
    }
}
