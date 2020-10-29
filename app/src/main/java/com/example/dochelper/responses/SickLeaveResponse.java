package com.example.dochelper.responses;

import com.example.dochelper.SickLeave;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SickLeaveResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("SickLeave")
    @Expose
    private List<SickLeave> sickLeave = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<SickLeave> getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(List<SickLeave> sickLeave) {
        this.sickLeave = sickLeave;
    }
}
