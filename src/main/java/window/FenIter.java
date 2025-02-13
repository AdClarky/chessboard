package window;

import common.Coordinate;
import common.PieceValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FenIter implements Iterable<PieceValue> {
    private final List<PieceValue> pieces = new ArrayList<>(64);

    public FenIter(String fen){
        int x = 0, y = 7;
        for (char c : fen.toCharArray()) {
            if(Character.isDigit(c)){
                for(int i=0; i<c-'0'; i++){
                    x++;
                }
            } else {
                pieces.add(PieceValue.of(new Coordinate(x, y), c));
            }
            if(x == 7){
                x = 0;
                y--;
            }
        }
    }

    @NotNull
    @Override
    public Iterator<PieceValue> iterator() {
        return pieces.iterator();
    }
}
