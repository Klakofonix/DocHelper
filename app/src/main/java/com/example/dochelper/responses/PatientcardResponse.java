package com.example.dochelper.responses;

import com.example.dochelper.Patientcard;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientcardResponse {
    public PatientcardResponse(boolean error, List<Patientcard> patientcards){
        this.error = error;
        this.patientscards = patientcards;
    }
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("patientscards")
    @Expose
    private List<Patientcard> patientscards = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Patientcard> getPatientscards() {
        return patientscards;
    }

    public void setPatientscards(List<Patientcard> patientscards) {
        this.patientscards = patientscards;
    }
}
