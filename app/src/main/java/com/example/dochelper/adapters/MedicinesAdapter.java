package com.example.dochelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dochelper.Medicine;
import com.example.dochelper.R;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.MedicinesViewHolder> {
    private Context mCtx;
    private List<Medicine> medicines;

    public class MedicinesViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMedicine, textViewDose;
        public MedicinesViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMedicine = itemView.findViewById(R.id.textView_Medicines_Medicine);
            textViewDose = itemView.findViewById(R.id.textView_Medicines_Dose);
        }
    }
    @NonNull
    @Override
    public MedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCtx=parent.getContext();
        return new MedicinesAdapter.MedicinesViewHolder(LayoutInflater.from(mCtx)
                .inflate(R.layout.recyclerview_medicines,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinesViewHolder holder, int position) {

        Medicine medicine = medicines.get(position);

        holder.textViewMedicine.setText(medicine.getMedicine());
        holder.textViewDose.setText("Dawka: "+medicine.getDose());
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void setData(List<Medicine> medicineList) {
        this.medicines = medicineList;
    }


}
