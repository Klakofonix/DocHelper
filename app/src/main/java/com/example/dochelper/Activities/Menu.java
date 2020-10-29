package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.dochelper.Doctor;
import com.example.dochelper.R;
import com.example.dochelper.storage.SharedPrefManager;

public class Menu extends AppCompatActivity implements View.OnClickListener{
    
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.button_menu_logout).setOnClickListener(this);
        findViewById(R.id.button_menu_patients).setOnClickListener(this);
        findViewById(R.id.button_menu_addPatient).setOnClickListener(this);
        findViewById(R.id.button_menu_calendar).setOnClickListener(this);
        findViewById(R.id.button_menu_qr).setOnClickListener(this);

        textView = findViewById(R.id.textView_menu_FullName);
        Doctor doctor = SharedPrefManager.getInstance(this).getDoctor();
        textView.setText(doctor.getName()+" "+doctor.getSurname());



    }
    public void openActivity_PatientList(){
        startActivity(new Intent(this,PatiensList.class));
    }
    public void openActivity_NewPatient(){
        startActivity(new Intent(this,NewPatient.class));
    }
    public void openActivity_Calendar(){
        startActivity(new Intent(this,Calendar.class));
    }
    public void openActivity_QR(){
        startActivity(new Intent(this,Scanner.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    private void logout(){
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_menu_calendar:
                openActivity_Calendar();
                break;
            case R.id.button_menu_addPatient:
                openActivity_NewPatient();
                break;
            case R.id.button_menu_patients:
                openActivity_PatientList();
                break;
            case R.id.button_menu_logout:
                logout();
                break;
            case R.id.button_menu_qr:
                openActivity_QR();
                break;
        }
    }
}
