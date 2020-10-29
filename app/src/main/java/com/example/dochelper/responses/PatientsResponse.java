package com.example.dochelper.responses;

import com.example.dochelper.Patient;

import java.io.Serializable;
import java.util.List;

public class PatientsResponse {

    private boolean error;
    private List<Patient> patients;

    public PatientsResponse(boolean error, List<Patient> patients) {
        this.error = error;
        this.patients = patients;
    }

    public boolean isError() {
        return error;
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
