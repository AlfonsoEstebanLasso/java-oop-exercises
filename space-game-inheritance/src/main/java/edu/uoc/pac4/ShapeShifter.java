package edu.uoc.pac4.alien;

public interface ShapeShifter {

    void changeName() throws AlienException;

    default void shapeShift() {
        System.out.print("Shape shifting...");
    }
}

