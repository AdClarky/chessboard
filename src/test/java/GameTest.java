import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    void GameOneTest(){
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("src/test/resources/AdClarky_vs_kuldeepbhakuni317_2024.06.18.pgn"));
        autoplay.play();
        System.out.println(board.boardState());
    }

    /**
     * <a href="https://www.chess.com/game/live/107825855352">Game Link</a>
     */
    @Test
    void GameTwoTest(){
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("src/test/resources/andredar63_vs_AdClarky_2024.06.18.pgn"));
        autoplay.play();
    }

    /**
     * <a href="https://www.chess.com/game/live/112479669593">Game Link</a>
     */
    @Test
    void GameThreeTest(){
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("src/test/resources/junk437_vs_AdClarky_2024.06.18.pgn"));
        autoplay.play();
        assertTrue(board.isCheckmate());
    }
}
