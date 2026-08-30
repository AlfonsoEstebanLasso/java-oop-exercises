package edu.uoc.pac4;

import edu.uoc.pac4.ship.SpaceShip;

import java.util.ArrayList;
import java.util.List;


public class SpaceGame {



    private static final int MAX_SPACESHIPS = 10;
    private String galaxyName;
    private List<SpaceShip> spaceships;


    public SpaceGame(String galaxyName) throws SpaceGameException {
        setGalaxyName(galaxyName);
        this.spaceships = new ArrayList<>(MAX_SPACESHIPS);
    }


    public String getGalaxyName() {
        return galaxyName;
    }


    public void setGalaxyName(String galaxyName) throws SpaceGameException {
        if (galaxyName == null || galaxyName.trim().isEmpty()) {
            throw new SpaceGameException(SpaceGameException.INVALID_GALAXY_NAME);
        }
        this.galaxyName = galaxyName;
    }


    public List<SpaceShip> getSpaceships() {
        return spaceships;
    }


    public int getMaxSpaceships() {
        return MAX_SPACESHIPS;
    }


    public boolean addSpaceShip(SpaceShip spaceShip) throws SpaceGameException {
        if (spaceShip == null) {
            throw new SpaceGameException(SpaceGameException.NULL_SPACESHIP);
        }

        if (spaceships.contains(spaceShip)) {
            throw new SpaceGameException(SpaceGameException.SPACESHIP_ALREADY_EXISTS);
        }

        if (spaceships.size() >= MAX_SPACESHIPS) {
            throw new SpaceGameException(SpaceGameException.MAX_SPACESHIPS_REACHED);
        }

        return spaceships.add(spaceShip);
    }



    public boolean removeSpaceShip(SpaceShip spaceShip) throws SpaceGameException {
        if (spaceShip == null) {
            throw new SpaceGameException(SpaceGameException.NULL_SPACESHIP);
        }

        if (!spaceships.contains(spaceShip)) {
            throw new SpaceGameException(SpaceGameException.SPACESHIP_NOT_FOUND);
        }

        return spaceships.remove(spaceShip);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{ \"galaxyName\": \"")
                .append(galaxyName)
                .append("\", \"spaceships\": [");

        for (int i = 0; i < spaceships.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(spaceships.get(i).toString());
        }

        sb.append(" ] }");

        return sb.toString();
    }
}
