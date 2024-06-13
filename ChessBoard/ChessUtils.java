package ChessBoard;

import java.util.HashMap;
import java.util.Map;

public final class ChessUtils {
    private static Map<Integer, String> xaxis = new HashMap<Integer, String>() {{
        put(0,"a");
        put(1,"b");
        put(2,"c");
        put(3,"d");
        put(4,"e");
        put(5,"f");
        put(6,"g");
        put(7,"h");
    }};
    public static String coordsToChess(int x, int y){
        return xaxis.get(x) + (y+1);
    }
}
