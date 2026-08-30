package edu.uoc.pac3;

public interface StudentRepository {
    void save(Student student);
    Student findStudentByEmail(String email);
    void listAll();
}