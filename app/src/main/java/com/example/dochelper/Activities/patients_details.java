package com.example.dochelper.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.dochelper.Disease;
import com.example.dochelper.Patient;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.Sensization;
import com.example.dochelper.responses.DiseaseResponse;
import com.example.dochelper.responses.SensizationResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class patients_details extends Activity implements View.OnClickListener {

    TextView TextView_fullname, TextView_birthdate, TextView_pesel, TextView_adress, TextView_CDiseases, TextView_Sensi;
    Button Button_patientcard, Button_addDoS, Button_addPatientVisit;
    int patientid;

    List<Disease> diseaseList;
    String sensitization="";
    String disease="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_details);

        TextView_fullname = findViewById(R.id.textView_PatientDetails_FullName);
        TextView_birthdate = findViewById(R.id.textView_PatientDetails_BirthDate);
        TextView_pesel = findViewById(R.id.textView_PatientDetails_Pesel);
        TextView_adress = findViewById(R.id.textView_PatientDetails_Adress);
        TextView_CDiseases = findViewById(R.id.textView_PatientDetails_CDiseases);
        TextView_Sensi = findViewById(R.id.textView_PatientDetails_Sensi);

        findViewById(R.id.button_PatientDetails_addNewVisit).setOnClickListener(this);
        findViewById(R.id.button_PatientDetails_PatientCard).setOnClickListener(this);
        findViewById(R.id.button_PatientDetails_addDoS).setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            Patient patient = (Patient) intent.getSerializableExtra("data");
            int id = patient.getIdPatient();
            String name = patient.getName();
            String surname = patient.getSurname();
            String pesel = patient.getPesel();
            String birthdate = patient.getBirthdate();
            String adress = patient.getAdress();
            this.patientid = id;

            TextView_fullname.setText(name+" "+surname);
            TextView_birthdate.setText(birthdate);
            TextView_adress.setText(adress);
            TextView_pesel.setText(pesel);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensitization="";
        disease="";
        getDisises();
        getSensitization();
    }

    private void getDisises(){
    Call<DiseaseResponse> call = RetrofitClient.getInstance().getApi().getDiseases(patientid);

    call.enqueue(new Callback<DiseaseResponse>() {
        @Override
        public void onResponse(Call<DiseaseResponse> call, Response<DiseaseResponse> response) {
            try {
                List<Disease> dis = response.body().getDiseases();
                for(int i=0;i<dis.size();i++){
                    disease += dis.get(i).getDisease()+ " ";}
                Log.e("succes",dis.get(0).getDisease());
                TextView_CDiseases.setText(disease);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<DiseaseResponse> call, Throwable t) {

        }
    });
}
    private void getSensitization(){
        Call<SensizationResponse> call = RetrofitClient.getInstance().getApi().getSensizations(patientid);

        call.enqueue(new Callback<SensizationResponse>() {
            @Override
            public void onResponse(Call<SensizationResponse> call, Response<SensizationResponse> response) {
                try {
                    List<Sensization> sens = response.body().getSensization();
                    for(int i=0;i<sens.size();i++){
                        sensitization += sens.get(i).getSensization()+ " ";}
                    Log.e("succes",sens.get(0).getSensization());
                    TextView_Sensi.setText(sensitization);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SensizationResponse> call, Throwable t) {

            }
        });
    }
    private void ClickedPatientCard(int id) {

        startActivity(new Intent(this,PatientcardList.class).putExtra("id",id));
    }
    private void ClickedAddDiseaseOrSensitization(String disease, String sensitization) {

        startActivity(new Intent(this,NewDiseaseOrSensitization.class)
                .putExtra("disease",disease)
                .putExtra("sensitization",sensitization)
                .putExtra("patientid", patientid));
    }
    private void ClickedAddNewVisit(){
        startActivity(new Intent(this,NewPatientVisit.class)
                .putExtra("id", patientid));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_PatientDetails_addNewVisit:
                ClickedAddNewVisit();
                break;
            case R.id.button_PatientDetails_PatientCard:
                ClickedPatientCard(patientid);
                break;
            case R.id.button_PatientDetails_addDoS:
                ClickedAddDiseaseOrSensitization(disease,sensitization);
                break;
        }
    }
}

