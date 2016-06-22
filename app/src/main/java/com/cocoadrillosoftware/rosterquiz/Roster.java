package com.cocoadrillosoftware.rosterquiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by chrisgregg on 6/21/16.
 */
public class Roster {
    private String name;
    ArrayList<Student> students;

    Roster(String name) {
        this.name = name;
    }

    void addStudent(Student student) {
        students.add(student);
    }

    void sortStudents(){
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
    }

    int size() {
        return students.size();
    }

    Student get(int index) {
        return students.get(index);
    }

    void printRoster(){
        for (Student student : students) {
            student.printStudent();
        }
    }

    public String toString() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }
}
