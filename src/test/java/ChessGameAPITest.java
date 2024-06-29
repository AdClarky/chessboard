import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class ChessGameAPITest {
    private class Listener implements BoardListener {
        int boardChanged = 0;
        int checkmate = 0;
        int draw = 0;

        @Override
        public void boardChanged(int oldX, int oldY, int newX, int newY) {
            boardChanged++;
        }

        @Override
        public void checkmate(int kingX, int kingY) {
            checkmate++;
        }

        @Override
        public void draw(int whiteX, int whiteY, int blackX, int blackY) {
            draw++;
        }
    }
    Listener listener;
    ChessGame chessGame;

    @BeforeEach
    void setUp() {
        listener = new Listener();
        chessGame = new ChessGame();
        chessGame.addBoardListener(listener);
    }

    @Test
    void basicMove(){
        assertDoesNotThrow(()-> chessGame.makeMove(3, 1, 3, 3));
        assertEquals(1, listener.boardChanged);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
    }

    @Test
    void checkmateGame() {
        assertDoesNotThrow( ()->new Autoplay(chessGame, Path.of("src/test/resources/checkmateGame.pgn")).play());
        assertEquals(46, listener.boardChanged);
        assertEquals(1, listener.checkmate);
        assertEquals(0, listener.draw);
    }

    @Test
    void repetitionGame() {
        assertDoesNotThrow( ()->new Autoplay(chessGame, Path.of("src/test/resources/repetitionGame.pgn")).play());
        assertEquals(8, listener.boardChanged);
        assertEquals(0, listener.checkmate);
        assertEquals(1, listener.draw);
    }

    @Test
    void stalemateGame() {
        assertDoesNotThrow( ()->new Autoplay(chessGame, Path.of("src/test/resources/stalemateGame.pgn")).play());
        assertEquals(105, listener.boardChanged);
        assertEquals(0, listener.checkmate);
        assertEquals(1, listener.draw);
    }
}
