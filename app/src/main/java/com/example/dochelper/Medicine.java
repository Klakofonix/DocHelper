package com.example.dochelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medicine {
    @SerializedName("medicine")
    @Expose
    private String medicine;
    @SerializedName("dose")
    @Expose
    private String dose;

    public Medicine(){}

    public Medicine(String medicine, String dose){
        this.medicine=medicine;
        this.dose=dose;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
