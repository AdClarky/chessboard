import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

class RegExTest {
    private final Pattern regex = Pattern.compile("([prknqb|0-8]{1,8}/){7}[prknqb|0-8]{1,8} [wb] [-kq]{1,4} (-|([a-h][1-8])) (\\d+) (\\d+)", Pattern.CASE_INSENSITIVE);

    @Test
    void regexTest(){
        String testString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQk - 0 1";
        assertTrue(regex.matcher(testString).matches());
    }

    @Test
    void regexTestOnDefault(){
        String testString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        assertTrue(regex.matcher(testString).matches());
    }

    @Test
    void regexTestOnEnPassant(){
        String testString = "4k3/8/8/4Pp2/8/8/8/4K3 w - f6 0 1";
        assertTrue(regex.matcher(testString).matches());
    }
}