package edu.uoc.pac4.alien;

import java.text.DecimalFormat;

public class SpecterAlien extends EtherealAlien {

    private static final double TRANSPARENCY_LEVEL = 0.95;

    private int hauntingPower;

    public SpecterAlien(String name, int hauntingPower) throws AlienException {
        super(name, TRANSPARENCY_LEVEL);
        setHauntingPower(hauntingPower);
    }

    public int getHauntingPower() {
        return hauntingPower;
    }

    public void setHauntingPower(int hauntingPower) throws AlienException {
        if (hauntingPower <= 0) {
            throw new AlienException(AlienException.INVALID_HAUNTING_POWER);
        }
        this.hauntingPower = hauntingPower;
    }

    @Override
    public String toString() {

        return "{ \"type\": \"SpecterAlien\", " +
                "\"etherealAlienDetails\": " + super.toString() + ", " +
                "\"hauntingPower\": " + hauntingPower + " }";
    }
}

