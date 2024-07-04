import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/** Parses a set of moves in a text file and then plays the move in order to a given board. */
public class Autoplay {
    private final Collection<String> moves = new ArrayList<>(30);
    private final ChessGame chessGame;

    public Autoplay(@NotNull ChessGame chessGame, Path path) throws IOException {
        this.chessGame = chessGame;
        moves.addAll(new PGNParser(path).getMoves());
    }

    public Autoplay(@NotNull ChessGame chessGame, Collection<String> moves){
        this.chessGame = chessGame;
        this.moves.addAll(moves);
    }

    public void play(int delay) throws InterruptedException, InvalidMoveException {
        for(String move : moves){
            chessGame.makeMove(ChessUtils.chessToMove(move, chessGame));
            TimeUnit.MILLISECONDS.sleep(delay);

        }
    }

    public void play() throws InterruptedException, InvalidMoveException {
        play(0);
    }

    public Collection<String> getMoves(){return moves;}
}
