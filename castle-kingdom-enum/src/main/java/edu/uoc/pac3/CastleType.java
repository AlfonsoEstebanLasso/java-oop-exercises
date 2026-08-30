package edu.uoc.pac3;

public enum CastleType {
    FORTRESS("Fortress", 40, 20),
    ROYAL_PALACE("Royal palace", 150, 50),
    STRONGHOLD("Stronghold", 400, 250),
    CITADEL("Citadel", 800, 500);

    private static final double SOLDIER_VALUE = 1.5;
    private static final double ARCHER_VALUE = 2.0;

    private final String description;
    private final int soldiers;
    private final int archers;

    CastleType(String description, int soldiers, int archers) {
        this.description = description;
        this.soldiers = soldiers;
        this.archers = archers;
    }

    public String getDescription() {
        return description;
    }

    public int getSoldiers() {
        return soldiers;
    }

    public int getArchers() {
        return archers;
    }

    public double getTotalValue() {
        return SOLDIER_VALUE * soldiers + ARCHER_VALUE * archers;
    }

    public static CastleType getType(String description) {
        if (description == null) return null;
        for (CastleType t : values()) {
            if (t.description.equals(description)) {
                return t;
            }
        }
        return null;
    }


    public CastleType nextType() {
        CastleType[] all = values();
        int i = ordinal();
        return (i < all.length - 1) ? all[i + 1] : this;
    }
}
