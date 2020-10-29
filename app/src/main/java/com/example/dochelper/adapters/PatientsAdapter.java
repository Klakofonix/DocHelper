package com.example.dochelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dochelper.Patient;
import com.example.dochelper.R;

import java.util.List;


public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientsViewHolder> {

    private Context mCtx;
    private List<Patient> patientList;

    public PatientsAdapter(Context mCtx, List<Patient> patientList){
        this.mCtx = mCtx;
        this.patientList = patientList;

    }



    @NonNull
    @Override
    public PatientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_patients, parent, false);
        return new PatientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsViewHolder holder, int position) {
    Patient patient = patientList.get(position);

    holder.textViewName.setText(patient.getName()+" "+patient.getSurname());
    holder.textViewBirthdate.setText(patient.getBirthdate());
    holder.textViewAdress.setText(patient.getAdress());
    holder.textViewPesel.setText(patient.getPesel());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class PatientsViewHolder extends  RecyclerView.ViewHolder {

        TextView textViewName, textViewAdress, textViewPesel, textViewBirthdate;

        public PatientsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAdress = itemView.findViewById(R.id.textViewAdress);
            textViewPesel = itemView.findViewById(R.id.textViewPesel);
            textViewBirthdate = itemView.findViewById(R.id.textViewBirthdate);

        }
    }
}
