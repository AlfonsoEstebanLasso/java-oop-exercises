package pac4;

import java.util.Locale;

public abstract class EtherealAlien extends Alien {

    private double transparencyLevel;

    protected EtherealAlien(String name, double transparencyLevel) throws AlienException {
        super(name);
        setTransparencyLevel(transparencyLevel);
    }

    public double getTransparencyLevel() {
        return transparencyLevel;
    }

    public void setTransparencyLevel(double transparencyLevel) {
        if (transparencyLevel < 0.0) {
            this.transparencyLevel = 0.0;
        } else if (transparencyLevel > 1.0) {
            this.transparencyLevel = 1.0;
        } else {
            this.transparencyLevel = transparencyLevel;
        }
    }

    @Override
    public String toString() {
        String transparencyStr = String.format(Locale.US, "%.2f", getTransparencyLevel());
        return "{ \"alienDetails\": { \"name\": \"" + getName() + "\" }, " +
                "\"transparencyLevel\": " + transparencyStr + " }";
    }
}

