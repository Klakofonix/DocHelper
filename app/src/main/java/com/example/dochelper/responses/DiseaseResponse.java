package com.example.dochelper.responses;

import com.example.dochelper.Disease;

import java.util.List;

public class DiseaseResponse {

    private boolean error;
    private List<Disease> diseases;

    public DiseaseResponse(boolean error, List<Disease> diseases){
        this.error = error;
        this.diseases = diseases;
    }

    public boolean isError() {
        return error;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }
}
