package common;

import java.util.HashMap;
import java.util.Map;

public enum Pieces {
    ROOK('R'),
    KNIGHT('N'),
    BISHOP('B'),
    PAWN('\u0000'),
    QUEEN('Q'),
    KING('K'),
    BLANK('Z');

    private final char character;
    private static final Map<Character, Pieces> LOOKUP_MAP = new HashMap<>();

    static {
        LOOKUP_MAP.put('R', ROOK);
        LOOKUP_MAP.put('N', KNIGHT);
        LOOKUP_MAP.put('B', BISHOP);
        LOOKUP_MAP.put('P', PAWN);
        LOOKUP_MAP.put('Q', QUEEN);
        LOOKUP_MAP.put('K', KING);
    }

    Pieces(char character) {
        this.character = character;
    }

    public int toIndex(){
        return switch(this){
            case PAWN -> 0;
            case ROOK -> 1;
            case KNIGHT -> 2;
            case BISHOP -> 3;
            case QUEEN -> 4;
            case KING -> 5;
            case BLANK -> -1;
        };
    }

    public char toCharacter(){
        return character;
    }

    public static Pieces fromCharacter(char character){
        return LOOKUP_MAP.get(character);
    }
}
