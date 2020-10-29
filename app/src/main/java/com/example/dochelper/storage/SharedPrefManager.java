package com.example.dochelper.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dochelper.Doctor;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_pref";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx)
    {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(Doctor doctor){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", doctor.getId());
        editor.putString("name", doctor.getName());
        editor.putString("surname",doctor.getSurname());
        editor.putString("role",doctor.getRole());

        editor.apply();

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id",-1) != -1;

    }

    public Doctor getDoctor(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new Doctor(
               sharedPreferences.getInt("id", -1),
               sharedPreferences.getString("name", null),
               sharedPreferences.getString("surname", null),
               sharedPreferences.getString("role", null)
       );
    }
public void clear() {
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.clear();
    editor.apply();
}


}
