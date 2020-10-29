package com.example.dochelper.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdPatientCardResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("idPatientCard")
    @Expose
    private Integer idPatientCard;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getIdPatientCard() {
        return idPatientCard;
    }

    public void setIdPatientCard(Integer idPatientCard) {
        this.idPatientCard = idPatientCard;
    }
}
