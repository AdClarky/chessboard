import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/** Parses a set of moves in a text file and then plays the move in order to a given board. */
public class Autoplay {
    private final Collection<String> moves = new ArrayList<>(30);
    private final ChessGame chessGame;


    public Autoplay(ChessGame chessGame){
        this.chessGame = chessGame;
    }

    public Autoplay(ChessGame chessGame, Path path) throws IOException {
        this.chessGame = chessGame;
        importGame(path);
    }

    public void importGame(Path path) throws IOException {
        Stream<String> lines = Files.lines(path);
        lines.forEach(this::processLine);
    }

    void processLine(@NotNull String line){
        if(line.isEmpty() || line.charAt(0) == '[')
            return;
        StringBuilder currentMove = new StringBuilder();
        boolean active = !Character.isDigit(line.charAt(0));
        int length = line.length() - 1;
        for(int i = 0; i < length; i++){
            active = processCharacter(line.charAt(i), line.charAt(i+1), currentMove, active);
        }
        processCharacter(line.charAt(line.length()-1), ' ', currentMove, active);
        if(!currentMove.isEmpty())
            moves.add(currentMove.toString());
    }

    boolean processCharacter(Character character, Character nextChar,
                                     StringBuilder move, boolean active){
        if(character == ' '){
            if(active) {
                moves.add(move.toString());
                move.setLength(0);
            }
            return !Character.isDigit(nextChar);
        }else if(active) {
            move.append(character);
            return true;
        }
        return false;
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
