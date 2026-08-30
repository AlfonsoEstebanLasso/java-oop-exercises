package edu.uoc.pac3;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStudentRepository implements StudentRepository {

    private final Map<String, Student> database = new HashMap<>();

    @Override
    public void save(Student student) {
        database.put(student.getEmail(), student);
        System.out.println("Saving " + student.getName() + " to database...");
    }

    @Override
    public Student findStudentByEmail(String email) {
        System.out.println("Searching for student with email: " + email);
        Student student = database.get(email);

        if (student == null) {
            System.out.println("No student found with that email.");
        } else {
            System.out.println("Student found: " + student.getName());
        }

        return student;
    }

    @Override
    public void listAll() {
        System.out.println("=== All students in database ===");
        for (Student s : database.values()) {
            System.out.println("- " + s.getName() + " (" + s.getEmail() + ")");
        }
    }
}
