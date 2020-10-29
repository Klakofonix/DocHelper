package com.example.dochelper;

public class Disease {

    private int idPatient;
    private String disease;

    Disease(int idPatient, String disease){
        this.idPatient = idPatient;
        this.disease = disease;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public String getDisease() {
        return disease;
    }
}
