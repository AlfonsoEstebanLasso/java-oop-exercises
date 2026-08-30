package edu.uoc.pac4.alien;

import java.util.Locale;

public class VoidMorphAlien extends EtherealAlien implements ShapeShifter {

    private static final double TRANSPARENCY_LEVEL = 0.90;

    private double morphingAbility;

    public VoidMorphAlien(String name, double morphingAbility) throws AlienException {
        super(name, TRANSPARENCY_LEVEL);
        setMorphingAbility(morphingAbility);
    }

    public double getMorphingAbility() {
        return morphingAbility;
    }

    public void setMorphingAbility(double morphingAbility) throws AlienException {
        if (morphingAbility < 0.0 || morphingAbility > 1.0) {
            throw new AlienException(AlienException.INVALID_MORPHING_ABILITY);
        }
        this.morphingAbility = morphingAbility;
    }

    @Override
    public void changeName() throws AlienException {

        shapeShift();
        setName(getName() + " (VoidMorph)");
    }

    @Override
    public String toString() {
        String transparencyStr = String.format(Locale.US, "%.2f", getTransparencyLevel());
        String morphingStr      = String.format(Locale.US, "%.2f", morphingAbility);

        return "{ \"type\": \"VoidMorphAlien\", \"etherealAlienDetails\": { "
                + "\"alienDetails\": { \"name\": \"" + getName() + "\" }, "
                + "\"transparencyLevel\": " + transparencyStr
                + " }, \"morphingAbility\": " + morphingStr + " }";
    }
}
