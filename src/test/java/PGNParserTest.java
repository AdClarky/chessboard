import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
}
