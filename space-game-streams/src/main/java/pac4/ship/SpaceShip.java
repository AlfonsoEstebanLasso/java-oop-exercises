package pac4.ship;

import pac4.Alien;

import java.util.LinkedList;
import java.util.List;

public abstract class SpaceShip {

    private String id;
    private int peopleCapacity;
    private double cargoCapacity;
    private SpaceShipRolType spaceShipRolType;
    private List<Alien> aliens;

    protected SpaceShip(String id,
                        int peopleCapacity,
                        double cargoCapacity,
                        SpaceShipRolType spaceShipRolType) throws SpaceShipException {

        setId(id);
        this.peopleCapacity = peopleCapacity;
        setCargoCapacity(cargoCapacity);
        setSpaceShipRolType(spaceShipRolType);
        this.aliens = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    private void setId(String id) throws SpaceShipException {
        if (id == null || !id.startsWith("SS-")) {
            throw new SpaceShipException(SpaceShipException.INVALID_SPACESHIP_ID);
        }
        this.id = id;
    }

    public int getPeopleCapacity() {
        return peopleCapacity;
    }

    protected void setPeopleCapacity(int peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }

    public double getCargoCapacity() {
        return cargoCapacity;
    }

    private void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public SpaceShipRolType getSpaceShipRolType() {
        return spaceShipRolType;
    }

    public void setSpaceShipRolType(SpaceShipRolType spaceShipRolType) throws SpaceShipException {
        if (spaceShipRolType == null) {
            throw new SpaceShipException(SpaceShipException.INVALID_SPACESHIP_ROL_TYPE);
        }
        this.spaceShipRolType = spaceShipRolType;
    }

    public List<Alien> getAliens() {
        return aliens;
    }

    public boolean addAlien(Alien alien) throws SpaceShipException {
        if (alien == null) {
            throw new SpaceShipException(SpaceShipException.NULL_ALIEN);
        }
        if (aliens.contains(alien)) {
            throw new SpaceShipException(SpaceShipException.ALIEN_ALREADY_EXISTS);
        }
        if (aliens.size() >= peopleCapacity) {
            throw new SpaceShipException(SpaceShipException.PEOPLE_CAPACITY_EXCEEDED);
        }
        return aliens.add(alien);
    }

    public boolean removeAlien(Alien alien) throws SpaceShipException {
        if (alien == null) {
            throw new SpaceShipException(SpaceShipException.NULL_ALIEN);
        }
        return aliens.remove(alien);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"id\": \"").append(id)
                .append("\", \"peopleCapacity\": ").append(peopleCapacity)
                .append(", \"cargoCapacity\": ").append(cargoCapacity)
                .append(", \"rol\": \"").append(spaceShipRolType.toString())
                .append("\", \"aliens\": [");

        for (int i = 0; i < aliens.size(); i++) {
            sb.append(aliens.get(i).toString());
            if (i < aliens.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("] }");
        return sb.toString();
    }
}

