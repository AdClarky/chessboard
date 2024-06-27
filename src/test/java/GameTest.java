import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;

class GameTest {
    ChessGame chessGame;

    @BeforeEach
    void setUp() {
        chessGame = new ChessGame();
    }

    /**
     * <a href="https://www.chess.com/game/live/107820135780">Game Link</a>
     */
    @Test
    void GameOneTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame);
        autoplay.importGame(Path.of("src/test/resources/AdClarky_vs_kuldeepbhakuni317_2024.06.18.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertEquals(418651116, chessGame.boardState()); // hash value of the board, if hashing changes this will need to be recalculated
    }

    /**
     * <a href="https://www.chess.com/game/live/107825855352">Game Link</a>
     */
    @Test
    void GameTwoTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame);
        autoplay.importGame(Path.of("src/test/resources/andredar63_vs_AdClarky_2024.06.18.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertEquals(-873082511, chessGame.boardState());
    }

    /**
     * <a href="https://www.chess.com/game/live/112479669593">Game Link</a>
     */
    @Test
    void checkmateTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame);
        autoplay.importGame(Path.of("src/test/resources/checkmateGame.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(chessGame.isCheckmate());
        assertEquals(264244004, chessGame.boardState());
    }

    /**
     * <a href="https://www.chess.com/game/computer/132962671">Game Link</a>
     */
    @Test
    void stalemateTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame);
        autoplay.importGame(Path.of("src/test/resources/stalemateGame.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(chessGame.isDraw(Piece.BLACK_PIECE));
        assertTrue(chessGame.isStalemate(Piece.BLACK_PIECE));
    }

    @Test
    void repetitionTest() throws IOException {
        Autoplay autoplay = new Autoplay(chessGame);
        autoplay.importGame(Path.of("src/test/resources/repetitionGame.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(chessGame.isDraw(Piece.BLACK_PIECE));
        assertTrue(chessGame.is3Repetition());
    }
}
