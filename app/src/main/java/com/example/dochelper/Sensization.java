package com.example.dochelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sensization {

    @SerializedName("idPatient")
    @Expose
    private Integer idPatient;
    @SerializedName("sensization")
    @Expose
    private String sensization;

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }

    public String getSensization() {
        return sensization;
    }

    public void setSensization(String sensization) {
        this.sensization = sensization;
    }

}