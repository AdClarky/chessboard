import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class AutoplayTest {
    @Test
    void emptyMovesInput(){
        ChessGame game = new ChessGame();
        Collection<String> moves = new ArrayList<>(0);
        Autoplay autoplay = new Autoplay(game, moves);
        assertTrue(autoplay.getMoves().isEmpty());
        assertDoesNotThrow(()->{
            autoplay.play(1);
        });
    }

    @Test
    void twoMovesMade(){
        ChessGame game = new ChessGame();
        Collection<String> moves = new ArrayList<>(0);
        moves.add("e4");
        moves.add("e5");
        Autoplay autoplay = new Autoplay(game, moves);
        assertEquals(autoplay.getMoves(), moves);
        assertDoesNotThrow(()->{
            autoplay.play(1);
        });
        assertEquals("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2", game.getFenString());
    }


    @Test
    void invalidMoveMade(){
        ChessGame game = new ChessGame();
        Collection<String> moves = new ArrayList<>(0);
        moves.add("e4");
        moves.add("e4");
        Autoplay autoplay = new Autoplay(game, moves);
        assertEquals(autoplay.getMoves(), moves);
        assertThrows(InvalidMoveException.class, ()->{
            autoplay.play(1);
        });
    }

    @Test
    void randomStringInput(){
        ChessGame game = new ChessGame();
        Collection<String> moves = new ArrayList<>(0);
        moves.add("asdkaslk");
        moves.add("123sdaxz");
        Autoplay autoplay = new Autoplay(game, moves);
        assertEquals(autoplay.getMoves(), moves);
        assertThrows(InvalidMoveException.class, ()->{
            autoplay.play(1);
        });
    }
}