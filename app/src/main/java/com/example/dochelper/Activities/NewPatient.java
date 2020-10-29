package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dochelper.ConfirmationDialog;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.responses.DefaultResponse;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPatient extends AppCompatActivity implements ConfirmationDialog.ConfirmationDialogListener, View.OnClickListener {

    private TextView DisplayDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditText editTextName, editTextSurname, editTextPESEL, editTextAdress;
    private String birthdate=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        findViewById(R.id.button_NewPatient_addPatient).setOnClickListener(this);

        editTextName = findViewById(R.id.textView_NewPatient_Name);
        editTextSurname = findViewById(R.id.textView_NewPatient_Surname);
        editTextPESEL = findViewById(R.id.textView_NewPatient_PESEL);
        editTextAdress = findViewById(R.id.textView_NewPatient_Adress);


        DisplayDate = findViewById(R.id.DatePicker_NewPatient);
        DisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewPatient.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year,month,day);
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    month++;
                    Log.d("onDateSet:"," mm/dd/yyyy:  "+ month +"/" + day +"/"+year);

                    String date = year+"-"+month+"-"+day;
                    DisplayDate.setText(date);
                    birthdate = date;

                }


        };

    }

    private void newUser(){
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String PESEL = editTextPESEL.getText().toString().trim();
        String Adress = editTextAdress.getText().toString().trim();


        if(name.isEmpty()){
            editTextName.setError("Musisz wpisac imię");
            editTextName.requestFocus();
            return;
        }

        if(surname.isEmpty()){
            editTextSurname.setError("Musisz wpisac nazwisko");
            editTextSurname.requestFocus();
            return;
        }
        if(PESEL.isEmpty()){
            editTextPESEL.setError("Musisz wpisac PESEL");
            editTextPESEL.requestFocus();
            return;
        }
        if(PESEL.length()<11){
            editTextPESEL.setError("PESEL musi zawierać 11 cyfr");
            editTextPESEL.requestFocus();
            return;
        }
        if(Adress.isEmpty()){
            editTextAdress.setError("Musisz wpisac adres");
            editTextAdress.requestFocus();
            return;
        }
        if(birthdate==null){
            Toast toast = Toast.makeText(this, "Musisz wybrać datę urodzin", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return;
        }

        Log.e("Birthdate",birthdate);

        retrofit2.Call<DefaultResponse> call = RetrofitClient.getInstance()
                .getApi()
                .newPatient(name,surname,birthdate,PESEL,Adress);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()== 201){
                DefaultResponse dr =response.body();
                Toast.makeText(NewPatient.this, dr.getMsg(), Toast.LENGTH_LONG).show();}
                else if (response.code() == 422)
                    Toast.makeText(NewPatient.this, "Wystapil blad", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
        editTextName.setText("");
        editTextSurname.setText("");
        editTextPESEL.setText("");
        editTextAdress.setText("");
        DisplayDate.setText("Wybierz date");
        birthdate=null;

    }

    @Override
    public void onYesClicked() {
        newUser();
    }

    private void ConfirmationDialog(){
        String message= "Czy na pewno chcesz dodać tego pacjenta?\nCzynności tej nie można cofnąć!";
        ConfirmationDialog dialog = new ConfirmationDialog(message);
        dialog.show(getSupportFragmentManager(),"diseaseDialog");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_NewPatient_addPatient:
                ConfirmationDialog();
                break;
        }
    }
}
