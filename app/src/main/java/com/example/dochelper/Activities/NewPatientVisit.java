package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dochelper.ConfirmationDialog;
import com.example.dochelper.Doctor;
import com.example.dochelper.Medicine;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.adapters.NewMedicineAdapter;
import com.example.dochelper.responses.DefaultResponse;
import com.example.dochelper.responses.IdPatientCardResponse;
import com.example.dochelper.storage.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPatientVisit extends AppCompatActivity implements ConfirmationDialog.ConfirmationDialogListener {

    Doctor doctor = SharedPrefManager.getInstance(this).getDoctor();
    List<Medicine> medicineList = new ArrayList<Medicine>();
    NewMedicineAdapter medicinesAdapter;

    private String EndDate,medicine,dose;
    int doctorid = doctor.getId();
    int patientid,patientCardId;
    String StartDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    private boolean SickLeave=false,Medicines=false;

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Switch switchSickLeave,switchMedicines;
    private Button buttonAddMedicine,buttonAddVisit;
    private EditText editTextSymptoms,editTextOther,editTextMedicine,editTextDose;
    private TextView textViewDateEnd, textViewSetDate, textViewMedicine, textViewDose;
    private RecyclerView recyclerViewMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient_visit);
        medicinesAdapter = new NewMedicineAdapter();

        Log.e("StartDate:",StartDate);
        initialize();
        initializeSwitches();
        selectDate();


        buttonAddMedicine.setOnClickListener(v -> {
            addMedicine();
            getMedicines();
        });
        buttonAddVisit.setOnClickListener(v -> openDialog());





    }

    private void initialize(){
        switchMedicines = findViewById(R.id.switch_newPatientVisit_medicines);
        switchSickLeave = findViewById(R.id.switch_newPatientVisit_sickleave);
        textViewDateEnd = findViewById(R.id.textView_newPatientVisit_DateEnd);
        textViewSetDate = findViewById(R.id.textView_newPatientVisit_SelectDate);
        textViewMedicine = findViewById(R.id.textView_newPatientVisit_Medicine);
        textViewDose = findViewById(R.id.textView_newPatientVisit_Dose);
        editTextSymptoms = findViewById(R.id.editText_newPatientVisit_Symptoms);
        editTextOther = findViewById(R.id.editText_newPatientVisit_Other);
        editTextMedicine = findViewById(R.id.editText_newPatientVisit_Medicine);
        editTextDose = findViewById(R.id.editText_newPatientVisit_Dose);
        buttonAddMedicine = findViewById(R.id.button_newPatientVisit_AddMedicine);
        buttonAddVisit = findViewById(R.id.button_newPatientVisit_AddVisit);


        recyclerViewMedicines = findViewById(R.id.recyclerView_newPatientVisit);
        recyclerViewMedicines.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMedicines.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        textViewDateEnd.setVisibility(View.GONE);
        textViewSetDate.setVisibility(View.GONE);
        recyclerViewMedicines.setVisibility(View.GONE);
        textViewMedicine.setVisibility(View.GONE);
        editTextMedicine.setVisibility(View.GONE);
        textViewDose.setVisibility(View.GONE);
        editTextDose.setVisibility(View.GONE);
        buttonAddMedicine.setVisibility(View.GONE);
        textViewDose.setVisibility(View.GONE);

        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            int patientid= (int) intent.getSerializableExtra("id");
            this.patientid = patientid;}



    }
    private void initializeSwitches(){

        switchSickLeave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchSickLeave.setText("Tak ");
                    textViewDateEnd.setVisibility(View.VISIBLE);
                    textViewSetDate.setVisibility(View.VISIBLE);
                    SickLeave = true;
                }else{
                    switchSickLeave.setText("Nie ");
                    textViewDateEnd.setVisibility(View.GONE);
                    textViewSetDate.setVisibility(View.GONE);
                    SickLeave = false;

                }
            }
        });
        switchMedicines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchMedicines.setText("Tak ");
                    recyclerViewMedicines.setVisibility(View.VISIBLE);
                    textViewMedicine.setVisibility(View.VISIBLE);
                    editTextMedicine.setVisibility(View.VISIBLE);
                    textViewDose.setVisibility(View.VISIBLE);
                    editTextDose.setVisibility(View.VISIBLE);
                    buttonAddMedicine.setVisibility(View.VISIBLE);
                    textViewDose.setVisibility(View.VISIBLE);
                    Medicines = true;

                }else {
                    switchMedicines.setText("Nie ");
                    recyclerViewMedicines.setVisibility(View.GONE);
                    textViewMedicine.setVisibility(View.GONE);
                    editTextMedicine.setVisibility(View.GONE);
                    textViewDose.setVisibility(View.GONE);
                    editTextDose.setVisibility(View.GONE);
                    buttonAddMedicine.setVisibility(View.GONE);
                    textViewDose.setVisibility(View.GONE);
                    Medicines = false;


                }
            }
        });
    }
    private void selectDate(){
        textViewSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewPatientVisit.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year,month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
                textViewSetDate.setText(date);
                EndDate = date;

            }


        };
    }
    private void addMedicine(){

        medicine = editTextMedicine.getText().toString();
        dose = editTextDose.getText().toString();

        if(medicine.isEmpty()){
            editTextMedicine.setError("Musisz wpisać nazwę leku.");
            editTextMedicine.requestFocus();
            return;
        }
        if(dose.isEmpty()){
            editTextDose.setError("Musisz wpisac dawkę.");
            editTextDose.requestFocus();
            return;
        }

        Medicine med = new Medicine(medicine,dose);
        medicineList.add(med);

        editTextMedicine.setText("");
        editTextDose.setText("");
    }
    private void getMedicines(){
        medicinesAdapter.setData(medicineList);
        recyclerViewMedicines.setAdapter(medicinesAdapter);
        medicinesAdapter.notifyDataSetChanged();
    }
    private void  openDialog(){
        String message= "Czy na pewno chcesz dodać tę wizytę?\nCzynności tej nie można cofnąć!";
        ConfirmationDialog dialog = new ConfirmationDialog(message);
        dialog.show(getSupportFragmentManager(),"exampledialog");
       }
    @Override
    public void onYesClicked() {
        addVisit();

    }
    private void addVisit(){
        String symptoms,others;

        symptoms=editTextSymptoms.getText().toString().trim();
        others=editTextOther.getText().toString().trim();

        if(SickLeave){
            if(EndDate==null){
                Toast toast = Toast.makeText(this, "Musisz wybrać datę końca zwolnienia lekarskiego.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return;
            }
        }
        if(Medicines){
            if(medicineList.isEmpty()){
                Toast toast = Toast.makeText(this, "Musisz dodać leki", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return;
            }
        }

        if(symptoms.isEmpty()){
            editTextSymptoms.setError("Musisz wpisac symptomy");
            editTextSymptoms.requestFocus();
            return;
        }

        if(others.isEmpty()){
            others = "brak"; }



        addPatientcard(doctorid,patientid,symptoms,StartDate,others);


    }
    private void getPatientCardId(){

        Call<IdPatientCardResponse> call = RetrofitClient.getInstance().getApi().getPatientCardId(doctorid,patientid);

        call.enqueue(new Callback<IdPatientCardResponse>() {
            @Override
            public void onResponse(Call<IdPatientCardResponse> call, Response<IdPatientCardResponse> response) {
                Log.e("doctorid:",Integer.toString(doctorid));
                Log.e("patientid:",Integer.toString(patientid));
                patientCardId = response.body().getIdPatientCard();
                Log.e("patientCardId:",Integer.toString(patientCardId));

                if(response.isSuccessful()){
                    if(Medicines){addMedicines();}
                    if(SickLeave){addSickLeave();}

}
            }

            @Override
            public void onFailure(Call<IdPatientCardResponse> call, Throwable t) {
                Log.e("error:", String.valueOf(t));
            }
        });

    }
    private void addPatientcard(int doctorid,int patientid,String symptoms,String StartDate,String others){

       Call<DefaultResponse> call = RetrofitClient.getInstance()
                .getApi()
                .newPatientCard(doctorid,patientid,symptoms,StartDate,others);


        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()== 201){
                    DefaultResponse dr =response.body();
                    Toast.makeText(NewPatientVisit.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                    Log.e("Odp:","201");}
                else if (response.code() == 422)
                    Toast.makeText(NewPatientVisit.this, "Wystapil blad", Toast.LENGTH_LONG).show();

                if(response.isSuccessful()){
                    getPatientCardId();
                    editTextSymptoms.setText("");
                    editTextOther.setText("");

                }

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("Failure","asdasd");
            }
        });



    }
    private void addMedicines(){
        for(Medicine med : medicineList){
            Log.e("idPatientCard", String.valueOf(patientCardId));
            Log.e("medicineList",med.getMedicine());
            Call call = RetrofitClient.getInstance().getApi().newMedicine(patientCardId,med.getMedicine(),med.getDose());
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if(response.code()== 201){
                        DefaultResponse dr =response.body();
                        Toast.makeText(NewPatientVisit.this, dr.getMsg(), Toast.LENGTH_LONG).show();}
                    else if (response.code() == 422)
                        Toast.makeText(NewPatientVisit.this, "Wystapil blad", Toast.LENGTH_LONG).show();

                    if(response.isSuccessful()){
                        medicineList.clear();
                        medicinesAdapter.notifyDataSetChanged();
                    }
                }


                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });
        }
    }
    private void addSickLeave(){
        Call call = RetrofitClient.getInstance().getApi().newSickLeave(patientCardId,StartDate,EndDate);
        Log.e("idPatientCard", String.valueOf(patientCardId));
        Log.e("StartDate",StartDate);
        Log.e("StartDate",EndDate);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()== 201){
                    DefaultResponse dr =response.body();
                    Toast.makeText(NewPatientVisit.this, dr.getMsg(), Toast.LENGTH_LONG).show();}
                else if (response.code() == 422)
                    Toast.makeText(NewPatientVisit.this, "Wystapil blad", Toast.LENGTH_LONG).show();
                if(response.isSuccessful()){
                    EndDate=null;
                    textViewSetDate.setText("Wybierz date");
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

}


