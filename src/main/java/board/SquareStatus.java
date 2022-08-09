package board;

public enum SquareStatus {
    EMPTY("."), SHIP("S"), HIT("H"), MISSED("M");

    private final String character;

    SquareStatus(String unicodeCharacter) {
        this.character = unicodeCharacter;
    }

    public String getCharacter() {
        return character;
    }
}
