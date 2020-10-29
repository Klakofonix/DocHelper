package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.dochelper.Patient;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.adapters.PatientsAdapter2;
import com.example.dochelper.responses.PatientsResponse;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatiensList extends AppCompatActivity implements PatientsAdapter2.ClickedItem {

    private RecyclerView recyclerView;
    private List<Patient> patientList;
    PatientsAdapter2 patientsAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patiens_list);
        recyclerView = findViewById(R.id.recyclerView_patientlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        patientsAdapter2 = new PatientsAdapter2(this);

        EditText Search = findViewById(R.id.Search_Patient_List);

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        getAllPatients();
    }

    public  void getAllPatients(){

        Call<PatientsResponse> call = RetrofitClient.getInstance().getApi().getPatients();

        call.enqueue(new Callback<PatientsResponse>() {
                         @Override
                         public void onResponse(Call<PatientsResponse> call, Response<PatientsResponse> response) {
                             patientList = response.body().getPatients();
                             patientsAdapter2.setData(patientList);
                             recyclerView.setAdapter(patientsAdapter2);
                         }

                         @Override
                         public void onFailure(Call<PatientsResponse> call, Throwable t) {
                             Log.e("failure",t.getLocalizedMessage());
                         }
                     }
        );

    }

    @Override
    public void ClickedPatient(Patient patient) {

        startActivity(new Intent(this,patients_details.class).putExtra("data",patient));
    }


    public boolean onCreateOptionsMenu(com.example.dochelper.Activities.Menu menu) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = (menu).findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void filter(String text) {
        ArrayList<Patient> filteredList = new ArrayList<>();
        for (Patient p :patientList){
            if(p.getSurname().toLowerCase().contains(text.toLowerCase())
                    ||p.getName().toLowerCase().contains(text.toLowerCase())
                    ||p.getPesel().toLowerCase().contains(text.toLowerCase())
                    ||p.getAdress().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(p);
            }
        }
        patientsAdapter2.filterList(filteredList);
    }
}
