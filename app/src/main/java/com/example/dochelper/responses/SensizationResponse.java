package com.example.dochelper.responses;

import com.example.dochelper.Sensization;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SensizationResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("sensization")
    @Expose
    private List<Sensization> sensization = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Sensization> getSensization() {
        return sensization;
    }

    public void setSensization(List<Sensization> sensization) {
        this.sensization = sensization;
    }
}
