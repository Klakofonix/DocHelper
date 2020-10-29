package com.example.dochelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Patientcard implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("patient")
    @Expose
    private Integer patient;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("symptoms")
    @Expose
    private String symptoms;
    @SerializedName("other")
    @Expose
    private String other;
    @SerializedName("doctor")
    @Expose
    private String doctor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatient() {
        return patient;
    }

    public void setPatient(Integer patient) {
        this.patient = patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

}
