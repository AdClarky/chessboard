import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoplayTest {
    Autoplay autoplay;
    ChessGame chessGame;
    @BeforeEach
    void setUp() {
        chessGame = new ChessGame();
        autoplay = new Autoplay(chessGame);
    }

    @Test
    void validCharacterInput(){
        StringBuilder stringBuilder = new StringBuilder();
        boolean active = autoplay.processCharacter('e', '4', stringBuilder, true);
        assertTrue(active);
        assertEquals("e", stringBuilder.toString());
        assertTrue(autoplay.getMoves().isEmpty());
    }

    @Test
    void validCharacterInputNextSpace(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('e');
        boolean active = autoplay.processCharacter('4', ' ', stringBuilder, true);
        assertTrue(active);
        assertEquals("e4", stringBuilder.toString());
        assertTrue(autoplay.getMoves().isEmpty());
    }

    @Test
    void spaceWithMoveAsPreviousAndValidAsNext(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("e4");
        boolean active = autoplay.processCharacter(' ', 'e', stringBuilder, true);
        assertTrue(active);
        assertEquals("", stringBuilder.toString());
        assertTrue(autoplay.getMoves().contains("e4"));
    }

    @Test
    void spaceWithMoveAsPreviousAndInvalidAsNext(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("e4");
        boolean active = autoplay.processCharacter(' ', '2', stringBuilder, true);
        assertFalse(active);
        assertEquals("", stringBuilder.toString());
        assertTrue(autoplay.getMoves().contains("e4"));
    }

    @Test
    void inactiveWithInvalidAsNext(){
        StringBuilder stringBuilder = new StringBuilder();
        boolean active = autoplay.processCharacter('2', '.', stringBuilder, false);
        assertFalse(active);
        assertEquals("", stringBuilder.toString());
        assertTrue(autoplay.getMoves().isEmpty());
    }

    @Test
    void inactiveWithSpaceAsNext(){
        StringBuilder stringBuilder = new StringBuilder();
        boolean active = autoplay.processCharacter('.', ' ', stringBuilder, false);
        assertFalse(active);
        assertEquals("", stringBuilder.toString());
        assertTrue(autoplay.getMoves().isEmpty());
    }

    @Test
    void inactiveSpaceWithValidNext(){
        StringBuilder stringBuilder = new StringBuilder();
        boolean active = autoplay.processCharacter(' ', 'N', stringBuilder, false);
        assertTrue(active);
        assertEquals("", stringBuilder.toString());
        assertTrue(autoplay.getMoves().isEmpty());
    }

    @Test
    void oneMoveNumberStart(){
        autoplay.processLine("1. e4 e5");
        assertEquals(2, autoplay.getMoves().size());
        assertTrue(autoplay.getMoves().contains("e4"));
        assertTrue(autoplay.getMoves().contains("e5"));
    }

    @Test
    void oneMoveDotEnd(){
        autoplay.processLine("1. e4 e5 2.");
        assertEquals(2, autoplay.getMoves().size());
        assertTrue(autoplay.getMoves().contains("e4"));
        assertTrue(autoplay.getMoves().contains("e5"));
    }

    @Test
    void startingWithMove(){
        autoplay.processLine("e4 e5");
        assertEquals(2, autoplay.getMoves().size());
        assertTrue(autoplay.getMoves().contains("e4"));
        assertTrue(autoplay.getMoves().contains("e5"));
    }
}
