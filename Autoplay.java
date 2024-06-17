import chessboard.Board;
import chessboard.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Autoplay {
    private static final List<Move> moves = Arrays.asList(
            new Move(3, 1, 3, 3),
            new Move(3,6,3,4),
            new Move(1,0,2,2),
            new Move(6,7,5,5),
            new Move(2,0,5,3),
            new Move(4,6,4,5),
            new Move(4,1,4,2),
            new Move(1,7,2,5)
    );
    private final Board board;

    public Autoplay(Board board){
        this.board = board;
    }

    public void play(){
        for(Move move : moves){
            board.moveWithValidation(move.oldX(), move.oldY(), move.newX(), move.newY());
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                return;
            }
        }
    }
}
