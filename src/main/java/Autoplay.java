import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Autoplay {
    private List<String> moves = new ArrayList<>(30);
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

    private void processLine(String line){
        if(line.isEmpty() || line.charAt(0) == '[')
            return;
        StringBuilder currentMove = new StringBuilder();
        boolean active = !Character.isDigit(line.charAt(0));
        int length = line.length();
        for(int i = 0; i < length; i++){
            if(line.charAt(i) == ' '){
                if(active) {
                    moves.add(currentMove.toString());
                    currentMove.setLength(0);
                }
                if(Character.isDigit(line.charAt(i+1)))
                    active = false;
                else
                    active = true;
            }else if(active) {
                currentMove.append(line.charAt(i));
            }
        }
        if(!currentMove.isEmpty())
            moves.add(currentMove.toString());
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
