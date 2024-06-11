import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class Rook extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_rook.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_rook.png");

    public Rook(int x, int y, Icon icon, int direction) {
        super(x, y, icon, direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for(int x = this.x+1; x < 8 && x >= 0; x++){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1; x < 8 && x >= 0; x--){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int y = this.y+1; y < 8 && y >= 0; y++){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int y = this.y-1; y < 8 && y >= 0; y--){
            if(cantMove(x, y, board, moves))
                break;
        }
        return moves;
    }

    @Override
    public String toString() {
        return "Rook";
    }
}
