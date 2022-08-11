package board;

/**
 * Enum used store possible values for squareStatus
 */
public enum SquareStatus {
    /**
     * Empty SquareStatus
     */
    EMPTY("."),
    /**
     * Ship SquareStatus
     */
    SHIP("S"),
    /**
     * Hit SquareStatus
     */
    HIT("H"),
    /**
     * Missed SquareStatus
     */
    MISSED("M"),
    /**
     * Sink SquareStatus
     */
    SINK("X");
    /**
     * Field storing character representing status
     */
    private final String character;

    /**
     * Constructor of enum SquareStatus
     * @param unicodeCharacter String to represent SquareStatus value
     */
    SquareStatus(String unicodeCharacter) {
        this.character = unicodeCharacter;
    }

    /**
     * Method used to get character of SquareStatus value
     * @return character of SquareStatus value
     */

    public String getCharacter() {
        return character;
    }
}
