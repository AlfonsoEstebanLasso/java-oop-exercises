package edu.uoc.pac3;

public class Keep {

    private static final double MIN_HEIGHT = 15.0;

    public static final String INVALID_LORD_NAME =
            "[ERROR] Lord name cannot be null, empty or formed by only whitespace";
    public static final String INVALID_FLOORS =
            "[ERROR] Keep must have at least one floor";
    public static final String INVALID_HEIGHT =
            "[ERROR] Height must be at least 15.0 meters";

    private String lordName;
    private int floors;
    private double height;
    private int numGuards;
    private boolean hasDungeon;

    public Keep(String lordName, int floors, double height, int numGuards, boolean hasDungeon) {
        setLordName(lordName);
        setFloors(floors);
        setHeight(height);
        setNumGuards(numGuards);
        setHasDungeon(hasDungeon);
    }

    private void setLordName(String lordName) {
        if (lordName == null || lordName.trim().isEmpty()) {
            throw new IllegalArgumentException(INVALID_LORD_NAME);
        }
        this.lordName = lordName.trim();
    }

    private void setFloors(int floors) {
        if (floors <= 0) {
            throw new IllegalArgumentException(INVALID_FLOORS);
        }
        this.floors = floors;
    }

    private void setHeight(double height) {
        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException(INVALID_HEIGHT);
        }
        this.height = height;
    }

    private void setNumGuards(int numGuards) {
        this.numGuards = Math.max(0, numGuards);
    }

    private void setHasDungeon(boolean hasDungeon) {
        this.hasDungeon = hasDungeon;
    }

    public String getLordName() { return lordName; }

    public int getFloors() { return floors; }

    public double getHeight() { return height; }

    public int getNumGuards() { return numGuards; }

    public boolean isHasDungeon() { return hasDungeon; }
}
