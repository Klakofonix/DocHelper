package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dochelper.Medicine;
import com.example.dochelper.Patientcard;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.SickLeave;
import com.example.dochelper.adapters.MedicinesAdapter;
import com.example.dochelper.responses.MedicineResponse;
import com.example.dochelper.responses.SickLeaveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Patientcard_details extends AppCompatActivity {

    TextView TextView_doctor, TextView_date, TextView_symptoms, TextView_other, TextView_sickleave;
    int patientid;
    private RecyclerView recyclerView;
    MedicinesAdapter medicinesAdapter;
    List<Medicine> medicineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientcard_details);

        TextView_date = findViewById(R.id.textView_patientcard_details_date);
        TextView_doctor = findViewById(R.id.textView_patientcard_details_doctor);
        TextView_symptoms = findViewById(R.id.textView_patientcard_details_symptoms);
        TextView_other = findViewById(R.id.textView_patientcard_details_other);
        TextView_sickleave = findViewById(R.id.textView_patientcard_details_sickleave);

        recyclerView = findViewById(R.id.RecyclerView_patientcard_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        medicinesAdapter = new MedicinesAdapter();

        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            Patientcard pc = (Patientcard) intent.getSerializableExtra("pc");
            int id = pc.getId();
            String date = pc.getDate();
            String doctor = pc.getDoctor();
            String symptoms = pc.getSymptoms();
            String other = pc.getOther();
            this.patientid = id;

            TextView_date.setText(date);
            TextView_doctor.setText(doctor);
            TextView_symptoms.setText(symptoms);
            TextView_other.setText(other);

            getSickLeave();
            getMedicines();

        }

    }

    private void getSickLeave(){
        Call<SickLeaveResponse> call = RetrofitClient.getInstance().getApi().getSickLeave(patientid);

        call.enqueue(new Callback<SickLeaveResponse>() {
            @Override
            public void onResponse(Call<SickLeaveResponse> call, Response<SickLeaveResponse> response) {
               try{
                   List<SickLeave> sl = response.body().getSickLeave();
                   String sickleave="";
                   sickleave=sl.get(0).getStartDate()+" - "+sl.get(0).getEndDate();

                   TextView_sickleave.setText(sickleave);

               } catch (Exception e) {
                   e.printStackTrace();
               }
            }

            @Override
            public void onFailure(Call<SickLeaveResponse> call, Throwable t) {

            }
        });

    }

    private void getMedicines(){

        Call<MedicineResponse> call = RetrofitClient.getInstance().getApi().getMedicines(patientid);

        call.enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
                medicineList = response.body().getMedicines();
                medicinesAdapter.setData(medicineList);
                recyclerView.setAdapter(medicinesAdapter);
            }

            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {

            }
        });

    }
}
