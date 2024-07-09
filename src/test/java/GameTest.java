import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;

class GameTest {
    private ChessGame chessGame;

    @BeforeEach
    void setUp() {
        chessGame = new ChessGame();
    }

    /**
     * <a href="https://www.chess.com/game/live/107820135780">Game Link</a>
     */
    @Test
    void GameOneTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame, Path.of("src/test/resources/randomGame1.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
    }

    /**
     * <a href="https://www.chess.com/game/live/107825855352">Game Link</a>
     */
    @Test
    void GameTwoTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame, Path.of("src/test/resources/randomGame2.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
    }

    /**
     * <a href="https://www.chess.com/game/live/112479669593">Game Link</a>
     */
    @Test
    void checkmateTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame, Path.of("src/test/resources/checkmateGame.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(chessGame.isCheckmate());
    }

    /**
     * <a href="https://www.chess.com/game/computer/132962671">Game Link</a>
     */
    @Test
    void stalemateTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame, Path.of("src/test/resources/stalemateGame.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(chessGame.isDraw());
    }

    @Test
    void repetitionTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame, Path.of("src/test/resources/repetitionGame.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(chessGame.isDraw());
    }

    @Test
    void draw50Move() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame, Path.of("src/test/resources/50move.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(chessGame.isDraw());
    }
}
