import assets.ImageUtils;

import javax.swing.Icon;
import java.util.ArrayList;

public class Bishop extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_bishop.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_bishop.png");

    public Bishop(int x, int y, Icon icon, int direction) {
        super(x, y, icon, direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for(int x = this.x+1, y = this.y+1; x < 8 && x>=0 && y>=0 && y < 8; x++, y++) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1, y = this.y-1; x < 8 && x>=0 && y>=0 && y < 8; x--, y--) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x+1, y = this.y-1; x < 8 && x>=0 && y>=0 && y < 8; x++, y--) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1, y = this.y+1; x < 8 && x>=0 && y>=0 && y < 8; x--, y++) {
            if(cantMove(x, y, board, moves))
                break;
        }

        return moves;
    }

    @Override
    public String toString() {
        return "Bishop";
    }
}
