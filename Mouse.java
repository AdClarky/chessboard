import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class Mouse implements MouseListener {
    private Square selectedSquare = null;

    private Board board;

    public Mouse(Board board){
        this.board = board;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!(e.getSource() instanceof Square clickedSquare))
            return;
        if(selectedSquare == null) {
            selectedSquare = clickedSquare;
            return;
        }
        for(Coordinate move : selectedSquare.getCurrentPiece().possibleMoves(board))
            System.out.println(move);
        System.out.println(Arrays.toString(new int[]{clickedSquare.getxPos(), clickedSquare.getyPos()}));
        if(selectedSquare.getCurrentPiece().possibleMoves(board).contains(new int[]{clickedSquare.getxPos(), clickedSquare.getyPos()})) {
            clickedSquare.setCurrentPiece(selectedSquare.getCurrentPiece());
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
