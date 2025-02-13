package common;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Parses a given file for Chess moves.
 * Assumes there is a space after each move number and after each move.
 */
public class PGNParser {
    private final Collection<String> moves = new ArrayList<>(30);
    private final StringBuilder currentMove = new StringBuilder();
    private boolean active = false;

    /**
     * Creates {@code PGNParser} which immediately processes a file.
     * @param path path of the file
     * @throws IOException when an error occurs when accessing the file
     */
    public PGNParser(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(this::processLine);
        }
    }

    /**
     * Creates {@code PGNParser} which immediately processes a string.
     * @param pgnString a string which is the equivalent of a PGN file.
     */
    public PGNParser(String pgnString) {
        processLine(pgnString);
    }

    /**
     * Gets moves which have been calculated.
     * @return get the chess moves.
     */
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
