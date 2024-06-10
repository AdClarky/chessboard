import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    private Square selectedSquare = null;

    private Board board;

    public Mouse(Board board){
        this.board = board;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!(e.getSource() instanceof Square))
            return;

        if(selectedSquare == null)
            selectedSquare = (Square) e.getSource();
        else {
            ((Square) e.getSource()).setCurrentPiece(selectedSquare.getCurrentPiece());
            selectedSquare.setCurrentPiece(null);
            selectedSquare = null;
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
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
