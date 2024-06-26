import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Autoplay {
    private final Collection<String> moves = new ArrayList<>(30);
    private final Board board;

    public Autoplay(Board board){
        this.board = board;
    }

    public void importGame(Path path){
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(this::processLine);
        } catch (IOException ex) {
            System.err.println("Could not find file");
        }
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

    public void play(int delay){
        for(String move : moves){
            board.makeMove(ChessUtils.chessToMove(move, board));
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            }catch (InterruptedException e){
                return;
            }
        }
    }

    public void play(){
        play(0);
    }
    public Collection<String> getMoves(){return moves;}
}
