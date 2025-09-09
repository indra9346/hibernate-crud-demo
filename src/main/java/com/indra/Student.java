package com.indra;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_data") // Optional: specifies the table name in the database
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int no; // Student roll number
    private double attendance;
    private double score;
    private String feedetails;

    // Constructors
    public Student() {}

    public Student(String name, int no, double attendance, double score, String feedetails) {
        this.name = name;
        this.no = no;
        this.attendance = attendance;
        this.score = score;
        this.feedetails = feedetails;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public double getAttendance() {
        return attendance;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFeedetails() {
        return feedetails;
    }

    public void setFeedetails(String feedetails) {
        this.feedetails = feedetails;
    }

    // ToString
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", no=" + no +
                ", attendance=" + attendance +
                ", score=" + score +
                ", feedetails='" + feedetails + '\'' +
                '}';
    }
}