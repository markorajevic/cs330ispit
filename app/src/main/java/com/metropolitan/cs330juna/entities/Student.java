package com.metropolitan.cs330juna.entities;

public class Student {

    private Long indeks;
    private String firstName;
    private String lastName;

    public Student() {

    }

    public Student(Long indeks, String firstName, String lastName) {
        this.indeks = indeks;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getIndeks() {
        return indeks;
    }

    public void setIndeks(Long indeks) {
        this.indeks = indeks;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
