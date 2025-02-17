package window;

import common.Coordinate;
import common.PieceColour;
import common.PieceValue;

import java.util.ArrayList;
import java.util.List;

public class FenParser {
    private final String fen;
    private PieceColour colour;
    private final List<PieceValue> pieces = new ArrayList<>(64);

    public FenParser(String fen){
        this.fen = fen;
        parsePieces();
        parseColour();
    }

    public List<PieceValue> getPieces(){
        return pieces;
    }

    public PieceColour getColour(){
        return colour;
    }

    private void parsePieces(){
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

    private void parseColour(){
        char colour_char = fen.charAt(fen.indexOf(' ')+1);
        colour = colour_char == 'b' ? PieceColour.BLACK : PieceColour.WHITE;
    }
}
