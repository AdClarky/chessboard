import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;

class GameTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    /**
     * <a href="https://www.chess.com/game/live/107820135780">Game Link</a>
     */
    @Test
    void GameOneTest() throws IOException {
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("src/test/resources/AdClarky_vs_kuldeepbhakuni317_2024.06.18.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertEquals(418651116, board.boardState()); // hash value of the board, if hashing changes this will need to be recalculated
    }

    /**
     * <a href="https://www.chess.com/game/live/107825855352">Game Link</a>
     */
    @Test
    void GameTwoTest() throws IOException {
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("src/test/resources/andredar63_vs_AdClarky_2024.06.18.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertEquals(-873082511, board.boardState());
    }

    /**
     * <a href="https://www.chess.com/game/live/112479669593">Game Link</a>
     */
    @Test
    void checkmateTest() throws IOException {
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("src/test/resources/junk437_vs_AdClarky_2024.06.18.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(board.isCheckmate());
        assertEquals(264244004, board.boardState());
    }

    /**
     * <a href="https://www.chess.com/game/computer/132962671">Game Link</a>
     */
    @Test
    void stalemateTest() throws IOException {
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("src/test/resources/stalemateGame.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(board.isDraw(Piece.BLACK_PIECE));
        assertTrue(board.isStalemate(Piece.BLACK_PIECE));
    }

    @Test
    void repetitionTest() throws IOException {
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("src/test/resources/repetitionGame.pgn"));
        assertDoesNotThrow(() -> autoplay.play());
        assertTrue(board.isDraw(Piece.BLACK_PIECE));
        assertTrue(board.is3Repetition());
    }
}
