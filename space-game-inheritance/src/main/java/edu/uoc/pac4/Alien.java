package edu.uoc.pac4.alien;

public abstract class Alien {

    private String name;

    protected Alien(String name) throws AlienException {
        setName(name);
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) throws AlienException {
        if (name == null || name.trim().isEmpty()) {
            throw new AlienException(AlienException.INVALID_ALIEN_NAME);
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name + "\" }";
    }
}
