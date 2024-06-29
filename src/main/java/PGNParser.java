import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class PGNParser {
    private final Collection<String> moves = new ArrayList<>(30);
    private final StringBuilder currentMove = new StringBuilder();
    private boolean active = false;

    public PGNParser(Path path) throws IOException {
        Stream<String> lines = Files.lines(path);
        lines.forEach(this::processLine);
    }

    public PGNParser(String pgnString) {
        processLine(pgnString);
    }


    public Collection<String> getMoves(){
        return moves;
    }

    private void processLine(@NotNull String line){
        if(line.isEmpty() || line.charAt(0) == '[')
            return;
        active = !Character.isDigit(line.charAt(0));
        int length = line.length() - 1;
        for(int i = 0; i < length; i++){
            processCharacter(line.charAt(i), line.charAt(i+1));
        }
        processCharacter(line.charAt(line.length()-1), ' ');
        if(!currentMove.isEmpty()) {
            moves.add(currentMove.toString());
            currentMove.setLength(0);
        }
    }

    private void processCharacter(Character character, Character nextChar){
        if(character == ' '){
            if(active) {
                moves.add(currentMove.toString());
                currentMove.setLength(0);
            }
            active = !Character.isDigit(nextChar);
        }else if(active)
            currentMove.append(character);
    }
}
