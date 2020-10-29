package com.example.dochelper.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dochelper.responses.DefaultResponse;
import com.example.dochelper.R;
import com.example.dochelper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextLogin, editTextPassword, editTextName, editTextSurname, editTextRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextRole = findViewById(R.id.editTextRole);

        findViewById(R.id.btnRegister).setOnClickListener(this);

    }

    private void userSignUp(){
        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String role = editTextRole.getText().toString().trim();

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

        retrofit2.Call<DefaultResponse> call = RetrofitClient.getInstance()
                .getApi()
                .createUser(login, password, name, surname, role);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()== 201){
                    DefaultResponse dr = response.body();
                    Toast.makeText(SignUp.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                }else if (response.code() == 422)
                    Toast.makeText(SignUp.this, "Wystapil blad", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                userSignUp();
                break;
        }
    }
}
