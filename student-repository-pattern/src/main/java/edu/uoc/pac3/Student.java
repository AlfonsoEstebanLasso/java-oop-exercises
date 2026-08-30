package edu.uoc.pac3;

public class Student {

    private final String name;
    private final double[] grades;
    private final String email;

    public Student(String name, double[] grades, String email) {
        this.name = name;
        this.grades = grades;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public double[] getGrades() {
        return grades;
    }

    public String getEmail() {
        return email;
    }

    public double calculateAverage() {
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return grades.length == 0 ? 0 : sum / grades.length;
    }
}
