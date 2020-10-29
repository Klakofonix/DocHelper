package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;

import com.example.dochelper.Doctor;
import com.example.dochelper.Patientcard;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.adapters.PatientCardAdapter;
import com.example.dochelper.responses.PatientcardResponse;
import com.example.dochelper.storage.SharedPrefManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientcardList extends AppCompatActivity implements PatientCardAdapter.ClickedItem {

    int Patientid;
    RecyclerView recyclerView;
    private List<Patientcard> patientcardList;
    PatientCardAdapter pca;
    Button buttonNewPatientVisit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientcard_list);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
           this.Patientid = intent.getIntExtra("id", 0); };


        recyclerView = findViewById(R.id.RecyclerView_patientcard_pclist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        pca = new PatientCardAdapter(this);
        EditText Search = findViewById(R.id.search_Patientcards);
        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



        buttonNewPatientVisit = findViewById(R.id.button_Patientcards_NewVisit);
        buttonNewPatientVisit.setOnClickListener(v -> ClickedNewVisit());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPatientCards();

    }

    public void getPatientCards(){
        Call<PatientcardResponse> call = RetrofitClient.getInstance().getApi().getPatientscards(Patientid);

        call.enqueue(new Callback<PatientcardResponse>() {
            @Override
            public void onResponse(Call<PatientcardResponse> call, Response<PatientcardResponse> response) {
                patientcardList = response.body().getPatientscards();
                pca.setData(patientcardList);
                recyclerView.setAdapter(pca);

            }

            @Override
            public void onFailure(Call<PatientcardResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }
        });

    }

    @Override
    public void ClickedCard(Patientcard pc) {
        startActivity(new Intent(this,Patientcard_details.class).putExtra("pc", (Serializable) pc));
    }

    public void ClickedNewVisit() {
        startActivity(new Intent(this,NewPatientVisit.class).putExtra("id",Patientid));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void filter(String text) {
        ArrayList<Patientcard> filteredList = new ArrayList<>();
        for (Patientcard pc :patientcardList){
            if(pc.getDate().toLowerCase().contains(text.toLowerCase())
                    ||pc.getDoctor().toLowerCase().contains(text.toLowerCase())
                    ||pc.getSymptoms().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(pc);
            }
        }
        pca.filterList(filteredList);
    }
}
