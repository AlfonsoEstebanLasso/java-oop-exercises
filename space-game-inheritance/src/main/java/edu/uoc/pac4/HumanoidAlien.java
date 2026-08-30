package edu.uoc.pac4.alien;

public class HumanoidAlien extends Alien {

    private int stamina;

    public HumanoidAlien(String name, int stamina) throws AlienException {
        super(name);
        setStamina(stamina);
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        if (stamina < 0) {
            this.stamina = 0;
        } else if (stamina > 100) {
            this.stamina = 100;
        } else {
            this.stamina = stamina;
        }
    }

    @Override
    public String toString() {

        return "{ \"type\": \"HumanoidAlien\", \"alienDetails\": " +
                super.toString() +
                ", \"stamina\": " + stamina + " }";
    }
}
