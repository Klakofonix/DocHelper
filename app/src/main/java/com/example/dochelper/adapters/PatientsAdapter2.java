package com.example.dochelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dochelper.Activities.Menu;
import com.example.dochelper.Patient;
import com.example.dochelper.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PatientsAdapter2 extends RecyclerView.Adapter<PatientsAdapter2.PatientsViewHolder>  {

    private List<Patient> patientList;
    private Context mCtx;
    private ClickedItem clickedItem;


    public PatientsAdapter2(ClickedItem clickedItem){
        this.clickedItem = clickedItem;
    }

    public void setData(List<Patient> patientList){
        this.patientList = patientList;
    }


    public void filterList(ArrayList<Patient> filteredList) {
        patientList = filteredList;
        notifyDataSetChanged();
    }

    public static class PatientsViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName, textViewAdress, textViewPesel, textViewBirthdate;

        public PatientsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAdress = itemView.findViewById(R.id.textViewAdress);
            textViewPesel = itemView.findViewById(R.id.textViewPesel);
            textViewBirthdate = itemView.findViewById(R.id.textViewBirthdate);

        }
    }

    @NonNull
    @Override
    public PatientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCtx = parent.getContext();
        return new PatientsAdapter2.PatientsViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_patients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsViewHolder holder, int position) {

        Patient patient = patientList.get(position);

        patient.getIdPatient();
        holder.textViewName.setText(patient.getName()+" "+patient.getSurname());
        holder.textViewBirthdate.setText(patient.getBirthdate());
        holder.textViewAdress.setText(patient.getAdress());
        holder.textViewPesel.setText(patient.getPesel());
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                clickedItem.ClickedPatient(patient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public interface ClickedItem{
         void ClickedPatient(Patient patientsResponse);


    }
}
