package edu.uoc.pac4.ship;


public class SpaceShip {


    private String id;
    private int peopleCapacity;
    private double cargoCapacity;
    private SpaceShipRolType spaceShipRolType;


    public SpaceShip(String id, int peopleCapacity, double cargoCapacity,
                     SpaceShipRolType spaceShipRolType) throws SpaceShipException {
        setId(id);
        setPeopleCapacity(peopleCapacity);
        setCargoCapacity(cargoCapacity);
        setSpaceShipRolType(spaceShipRolType);
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


    public void setPeopleCapacity(int peopleCapacity) {
        if (peopleCapacity < 0) {
            this.peopleCapacity = 0;
        } else {
            this.peopleCapacity = peopleCapacity;
        }
    }


    public double getCargoCapacity() {
        return cargoCapacity;
    }


    private void setCargoCapacity(double cargoCapacity) {
        if (cargoCapacity < 0) {
            this.cargoCapacity = 0.0;
        } else {
            this.cargoCapacity = cargoCapacity;
        }
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


    @Override
    public String toString() {
        return "{ \"id\": \"" + id + "\", " +
                "\"peopleCapacity\": " + peopleCapacity + ", " +
                "\"cargoCapacity\": " + cargoCapacity + ", " +
                "\"rol\": \"" + spaceShipRolType.toString() + "\" }";
    }
}

