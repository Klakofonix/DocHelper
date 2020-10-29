package com.example.dochelper;

import com.example.dochelper.responses.DefaultResponse;
import com.example.dochelper.responses.DiseaseResponse;
import com.example.dochelper.responses.IdPatientCardResponse;
import com.example.dochelper.responses.LoginResponse;
import com.example.dochelper.responses.MedicineResponse;
import com.example.dochelper.responses.PatientcardResponse;
import com.example.dochelper.responses.PatientsResponse;
import com.example.dochelper.responses.SensizationResponse;
import com.example.dochelper.responses.SickLeaveResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded

    @POST("newpatient")
    Call<DefaultResponse> newPatient(
            @Query("Name") String name,
            @Query("Surname") String surname,
            @Query("BirthDate") String birthdate,
            @Query("PESEL") String PESEL,
            @Query("Adress") String adress

    );

    @POST("createuser")
    Call<DefaultResponse> createUser(
        @Field("login") String login,
        @Field("password") String password,
        @Field("name") String name,
        @Field("surname") String surname,
        @Field("role") String role

    );

    @POST("newsickleave")
    Call<DefaultResponse> newSickLeave(
            @Query("idPatientCard") int idPatientCard,
            @Query("StartDate") String startDate,
            @Query("EndDate") String endDate

    );
    @POST("newmedicine")
    Call<DefaultResponse> newMedicine(
            @Query("idPatientCard") int idPatientCard,
            @Query("Medicine") String medicine,
            @Query("Dose") String dose

    );
    @POST("newpatientcard")
    Call<DefaultResponse> newPatientCard(
            @Query("idDoctor") int idDoctor,
            @Query("idPatient") int idPatient,
            @Query("Symptoms") String Symptoms,
            @Query("Date") String Date,
            @Query("Other") String Other

    );
    @POST("newdisease")
    Call<DefaultResponse> newDisease(
            @Query("id") int id,
            @Query("disease") String disease
    );
    @POST("newsensitization")
    Call<DefaultResponse> newSensitization(
            @Query("id") int id,
            @Query("sensitization") String sensitization
    );


    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("login") String login,
            @Field("password") String password
    );

    @GET("allpatients")
    Call<PatientsResponse> getPatients();

    @GET("alldiseases")
    Call<DiseaseResponse> getDiseases(
            @Query("id") int id
    );

    @GET("allsensizations")
    Call<SensizationResponse> getSensizations(
        @Query("id") int id
    );

    @GET("allpatientscards")
    Call<PatientcardResponse> getPatientscards(
            @Query("id") int id
    );
    @GET("medicines")
    Call<MedicineResponse> getMedicines(
            @Query("id") int id
    );
    @GET("sickleave")
    Call<SickLeaveResponse> getSickLeave(
            @Query("id") int id
    );
    @GET("getPatientCardId")
    Call<IdPatientCardResponse> getPatientCardId(
            @Query("iddoctor") int iddoctor,
            @Query("idpatient") int idpatient
    );
    @GET("getpatient")
    Call<PatientsResponse> getPatient(
            @Query("id") int idpatient
    );

}
