package io.sailex.util;

/**
 * Enum representing the eight sky directions.
 *
 * @author sailex
 */
public enum Direction {

    South("S"),
    SouthWest("SW"),
    West("W"),
    NorthWest("NW"),
    North("N"),
    NorthEast("NE"),
    East("E"),
    SouthEast("SE");

    private final String abbreviation;

    /**
     * Constructs a {@code Direction} with the given abbreviation.
     *
     * @param abbreviation the abbreviation for this direction
     */
    Direction(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Gets the abbreviation for this direction.
     *
     * @return the abbreviation of this direction
     */
    public String getAbbreviation() {
        return abbreviation;
    }

}
