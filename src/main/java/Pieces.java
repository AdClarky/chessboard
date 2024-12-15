public enum Pieces {
    ROOK('R'),
    KNIGHT('N'),
    BISHOP('B'),
    PAWN('\u0000'),
    QUEEN('Q'),
    KING('K'),
    BLANK('Z');

    private final char character;

    Pieces(char character) {
        this.character = character;
    }

    public char toCharacter(){
        return character;
    }
}
