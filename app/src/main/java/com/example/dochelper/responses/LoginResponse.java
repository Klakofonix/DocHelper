package com.example.dochelper.responses;

import com.example.dochelper.Doctor;

public class LoginResponse {

    private boolean error;
    private String message;
    private Doctor user;

    public LoginResponse(boolean error, String message, Doctor user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Doctor getUser() {
        return user;
    }
}
