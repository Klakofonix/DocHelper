package com.example.dochelper.responses;

import com.example.dochelper.Medicine;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicineResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("medicines")
    @Expose
    private List<Medicine> medicines = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
