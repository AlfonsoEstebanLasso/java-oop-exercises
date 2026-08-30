package pac4;

public interface ShapeShifter {

    void changeName() throws AlienException;

    default void shapeShift() {
        System.out.print("Shape shifting...");
    }
}

