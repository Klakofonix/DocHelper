package com.example.dochelper;

import java.io.Serializable;

public class Patient implements Serializable {
    private int id;
    private String name,surname,birthdate,pesel,adress;

public Patient(int id,String name,String surname, String birthdate, String pesel, String adress){
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.birthdate = birthdate;
    this.pesel = pesel;
    this.adress = adress;
}

    public int getIdPatient() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPesel() {
        return pesel;
    }

    public String getAdress() {
        return adress;
    }
}
