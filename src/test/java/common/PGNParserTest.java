package common;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

class PGNParserTest {
    @Test
    void oneMoveNumberStart(){
        Collection<String> moves = new PGNParser("1. e4 e5").getMoves();
        assertEquals(2, moves.size());
        assertTrue(moves.contains("e4"));
        assertTrue(moves.contains("e5"));
    }

    @Test
    void oneMoveDotEnd(){
        Collection<String> moves = new PGNParser("1. e4 e5 2.").getMoves();
        assertEquals(2, moves.size());
        assertTrue(moves.contains("e4"));
        assertTrue(moves.contains("e5"));
    }

    @Test
    void startingWithMove(){
        Collection<String> moves = new PGNParser("e4 e5").getMoves();
        assertEquals(2, moves.size());
        assertTrue(moves.contains("e4"));
        assertTrue(moves.contains("e5"));
    }

    @Test
    void importGameTesterSecondLine() throws IOException {
        Collection<String> moves = new PGNParser(Path.of("src/test/resources/checkmateGame.pgn")).getMoves();
        assertEquals(46, moves.size());
        assertTrue(moves.contains("Qd2"));
        assertTrue(moves.contains("b5"));
    }
}
