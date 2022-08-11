package ship;

/**
 * Enum Storing information of ShipTypes, such as their length
 */
public enum ShipType {
    /**
     * Carrier ShipType
     */
    CARRIER(1),
    /**
     * Cruiser ShipType
     */
    CRUISER(2),
    /**
     * Battleship shipType
     */
    BATTLESHIP(3),
    /**
     * Submarine shipType
     */
    SUBMARINE(4),
    /**
     * Destroyer shipType
     */
    DESTROYER(4);
    /**
     * represents number of fields occupied by ship
     */
    private final int length;

    /**
     * Constructor, inti length of the shipType
     * @param length length of the ship
     */
    ShipType(int length) {
        this.length = length;
    }

    /**
     * Method used for getting length of the shipType
     * @return length of the ShipType value
     */
    public int getLength() {
        return length;
    }
}
