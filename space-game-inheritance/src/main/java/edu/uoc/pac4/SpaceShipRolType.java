package edu.uoc.pac4.ship;

public enum SpaceShipRolType {
    BATTLE,
    SCIENCE,
    DIPLOMATIC;

    @Override
    public String toString() {
        switch (this) {
            case BATTLE:
                return "Battle";
            case SCIENCE:
                return "Science";
            case DIPLOMATIC:
                return "Diplomatic";
            default:
                return super.toString();
        }
    }
}
