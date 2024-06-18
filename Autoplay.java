import chessboard.Board;
import chessboard.ChessUtils;
import chessboard.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Autoplay {
    private static final List<String> moves = Arrays.asList(
            "e4"
    );
    private final Board board;

    public Autoplay(Board board){
        this.board = board;
    }

    public void play(int delay){
        for(String move : moves){
            board.moveWithValidation(ChessUtils.chessToMove(move, board));
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            }catch (InterruptedException e){
                return;
            }
        }
    }
}
