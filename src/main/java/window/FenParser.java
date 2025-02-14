package window;

import common.Coordinate;
import common.PieceValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FenParser implements Iterable<PieceValue> {
    private final List<PieceValue> pieces = new ArrayList<>(64);

    public FenParser(String fen){
        int x = 0, y = 7;
        for (char c : fen.toCharArray()) {
            if(c == '/')
                continue;
            if(c == ' ')
                break;
            if(Character.isDigit(c)){
                for(int i=0; i<c-'0'; i++){
                    x++;
                }
            } else {
                pieces.add(PieceValue.of(new Coordinate(x, y), c));
                x++;
            }
            if(x == 8){
                x = 0;
                y--;
            }
        }
    }

    public List<PieceValue> getPieces(){
        return pieces;
    }

    @NotNull
    @Override
    public Iterator<PieceValue> iterator() {
        return pieces.iterator();
    }
}
