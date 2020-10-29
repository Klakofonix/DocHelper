package com.example.dochelper.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.dochelper.Patient;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.responses.PatientsResponse;
import com.google.zxing.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class Scanner extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView codeScannerView;
    String patientid;
    Patient patient;
    private List<Patient> patientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        codeScannerView=findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this,codeScannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        patientid=result.getText();
                        getPatient();
                    }
                });

            }
        });
        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
    public  void getPatient(){


        Call<PatientsResponse> call = RetrofitClient.getInstance().getApi().getPatient(parseInt(patientid));

        call.enqueue(new Callback<PatientsResponse>() {
                         @Override
                         public void onResponse(Call<PatientsResponse> call, Response<PatientsResponse> response) {
                             patientList = response.body().getPatients();
                             patient = patientList.get(0);
                             Log.e("Patient imie",patient.getName());
                             startActivity(new Intent(Scanner.this,patients_details.class).putExtra("data",patient));

                         }

                         @Override
                         public void onFailure(Call<PatientsResponse> call, Throwable t) {
                             Log.e("failure",t.getLocalizedMessage());
                             Toast.makeText(Scanner.this, "Bledny kod QR", Toast.LENGTH_LONG).show();


                         }
                     }

        );

    }

}
