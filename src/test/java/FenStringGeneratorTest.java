import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FenStringGeneratorTest {
    @Test
    void blankBoardTest(){
        Chessboard board = new Chessboard();
        String fenString = new FenGenerator(board).getFenString();
        assertEquals("", fenString);
    }

    @Test
    void defaultStartingTest(){

    }
}
