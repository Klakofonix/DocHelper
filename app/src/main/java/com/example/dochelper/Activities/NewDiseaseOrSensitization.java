package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.responses.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewDiseaseOrSensitization extends AppCompatActivity {

    private String disease,sensitization;
    private TextView actual_disease, actual_sensitization;
    private int patientid;
    private EditText editTextDisease,editTextSensitization;
    private Button addSensitization, addDisease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_disease_or_sensitization);
        editTextDisease = findViewById(R.id.editText_NewDoS_NewDisease);
        editTextSensitization = findViewById(R.id.editText_NewDoS_AddSensitization);
        addDisease = findViewById(R.id.Button_NewDoS_AddDiseise);
        addDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDisease();
                if (disease=="brak"){             disease="";}
                disease = disease  +" "+ editTextDisease.getText().toString().trim();
                actual_disease.setText(disease);
                editTextDisease.setText("");

            }
        });
        addSensitization = findViewById(R.id.Button_NewDoS_AddSensitization);
        addSensitization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSensitization();
                if (sensitization=="brak"){             sensitization="";}
                sensitization = sensitization +" "+ editTextSensitization.getText().toString().trim();
                actual_sensitization.setText(sensitization);
                editTextSensitization.setText("");
            }
        });


        GetIntent();

    }

    private void newDisease(){
        String disease = editTextDisease.getText().toString().trim();

        if(disease.isEmpty()){
            editTextDisease.setError("Wpisz chorobę");
            editTextDisease.requestFocus();
            return;
        }
        retrofit2.Call<DefaultResponse> call = RetrofitClient.getInstance()
                .getApi()
                .newDisease(patientid,disease);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()== 201){
                    DefaultResponse dr =response.body();
                    Toast.makeText(NewDiseaseOrSensitization.this, dr.getMsg(), Toast.LENGTH_LONG).show();}
                else if (response.code() == 422)
                    Toast.makeText(NewDiseaseOrSensitization.this, "Wystapil blad", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    private void newSensitization(){
        String sensitization = editTextSensitization.getText().toString().trim();

        if(sensitization.isEmpty()){
            editTextSensitization.setError("Wpisz uczulenie");
            editTextSensitization.requestFocus();
            return;
        }
        retrofit2.Call<DefaultResponse> call = RetrofitClient.getInstance()
                .getApi()
                .newSensitization(patientid,sensitization);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()== 201){
                    DefaultResponse dr =response.body();
                    Toast.makeText(NewDiseaseOrSensitization.this, dr.getMsg(), Toast.LENGTH_LONG).show();}
                else if (response.code() == 422)
                    Toast.makeText(NewDiseaseOrSensitization.this, "Wystapil blad", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    private void GetIntent(){
    Intent intent = getIntent();
    if(intent.getExtras() !=null){
        this.disease = (String) intent.getSerializableExtra("disease");
        this.sensitization = (String) intent.getSerializableExtra("sensitization");
        this.patientid = (int) intent.getSerializableExtra("patientid");

        if(disease.equals("null ")){disease="brak";}
        Log.e("TO COS",disease);
        actual_disease = findViewById(R.id.textView_NewDoS_accualDiseises);
        actual_disease.setText(disease);

        if(sensitization.equals("null ")){sensitization="brak";}
        actual_sensitization = findViewById(R.id.textView_NewDoS_ActualSensitizations);
        actual_sensitization.setText(sensitization);
    }
}
}
