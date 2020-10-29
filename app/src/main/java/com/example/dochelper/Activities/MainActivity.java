package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dochelper.Doctor;
import com.example.dochelper.responses.LoginResponse;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;
import com.example.dochelper.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextLogin;
    private EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonZaloguj).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonZaloguj:
                Log.e("klikniete zaloguj","cyk");
                userLogin();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(MainActivity.this, Menu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userLogin() {

        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(login.isEmpty()){
            editTextLogin.setError("Musisz wpisac login");
            editTextLogin.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Musisz wpisac haslo");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance()
                .getApi()
                .userLogin(login,password);
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(!loginResponse.isError()){
                    SharedPrefManager.getInstance(MainActivity.this)
                           .saveUser(loginResponse.getUser());
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
            }
        });

    }
}



