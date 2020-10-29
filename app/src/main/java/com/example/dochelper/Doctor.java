package com.example.dochelper;

public class Doctor {

    private int id;
    private String name, surname, role;

    public Doctor(int id, String name, String surname, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public  String getName() {
        return name;
    }

    public  String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }
}
