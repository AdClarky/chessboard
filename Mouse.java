import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    private Square previousSquare = null;

    private Board board;

    public Mouse(Board board){
        this.board = board;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!(e.getSource() instanceof Square clickedSquare))
            return;
        System.out.println("Clicked: "+clickedSquare.getCoords());

        if(previousSquare == null){
            if(clickedSquare.getCurrentPiece() != null) { // if clicked on a piece
                previousSquare = clickedSquare;
                previousSquare.clicked();
            }
        }else if(previousSquare.getCurrentPiece().possibleMoves(board).contains(clickedSquare.getCoords())){// if it is a possible move
            clickedSquare.setCurrentPiece(previousSquare.getCurrentPiece());
            previousSquare.setCurrentPiece(null);
            previousSquare.clicked();
            previousSquare = null;
        }else{
            previousSquare.clicked();
            previousSquare = null;
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
