package io.sailex.hud;

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

    Direction(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
