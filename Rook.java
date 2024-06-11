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
            if(board.getPiece(x, y) != null){
                if(board.getPiece(x, y).getDirection() != direction)
                    moves.add(new Coordinate(x, y));
                break;
            }
            moves.add(new Coordinate(x, y));
        }
        for(int x = this.x-1; x < 8 && x >= 0; x--){
            if(board.getPiece(x, y) != null){
                if(board.getPiece(x, y).getDirection() != direction)
                    moves.add(new Coordinate(x, y));
                break;
            }
            moves.add(new Coordinate(x, y));
        }
        for(int y = this.y+1; y < 8 && y >= 0; y++){
            if(board.getPiece(x, y) != null){
                if(board.getPiece(x, y).getDirection() != direction)
                    moves.add(new Coordinate(x, y));
                break;
            }
            moves.add(new Coordinate(x, y));
        }
        for(int y = this.y-1; y < 8 && y >= 0; y--){
            if(board.getPiece(x, y) != null){
                if(board.getPiece(x, y).getDirection() != direction)
                    moves.add(new Coordinate(x, y));
                break;
            }
            moves.add(new Coordinate(x, y));
        }
        return moves;
    }

    @Override
    public String toString() {
        return "Rook";
    }
}
