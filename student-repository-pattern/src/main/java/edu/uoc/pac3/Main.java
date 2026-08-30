package edu.uoc.pac3;

public class Main {
    public static void main(String[] args) {
        double[] grades1 = {9.0, 8.5, 7.5, 10.0};
        Student s1 = new Student("David", grades1, "user1@example.com");

        double[] grades2 = {6.0, 7.0, 8.0, 7.5};
        Student s2 = new Student("Carles", grades2, "user2@example.com");

        StudentRepository repo1 = new InMemoryStudentRepository();
        StudentRepository repo2 = new InMemoryStudentRepository();

        repo1.save(s1);
        repo2.save(s2);

        repo1.listAll();

        Student found = repo2.findStudentByEmail("user2@example.com");

        if (found != null) {
            System.out.println("Average grade for " + found.getName() + ": " + found.calculateAverage());
        } else {
            System.out.println("Student not found, cannot send email.");
        }
    }
}
