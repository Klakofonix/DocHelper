package com.example.dochelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dochelper.Medicine;
import com.example.dochelper.R;

import java.util.List;


public class NewMedicineAdapter extends RecyclerView.Adapter<NewMedicineAdapter.NewMedicinesViewHolder> {

    private Context mCtx;
    private List<Medicine> medicines;

    public class NewMedicinesViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMedicine, textViewDose;
        ImageView delete;

        public NewMedicinesViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMedicine = itemView.findViewById(R.id.textView_NewMedicine_Medicine);
            textViewDose = itemView.findViewById(R.id.textView_NewMedicine_Dose);
            delete = itemView.findViewById(R.id.image_NewMedicine_delete);
        }
    }

    @NonNull
    @Override
    public NewMedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       mCtx = parent.getContext();
       return  new NewMedicineAdapter.NewMedicinesViewHolder(LayoutInflater.from(mCtx)
               .inflate(R.layout.recyclerview_addmedicines,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewMedicinesViewHolder holder, int position) {

        Medicine medicine = medicines.get(position);

        holder.textViewMedicine.setText(medicine.getMedicine());
        holder.textViewDose.setText(medicine.getDose());
        holder.delete.setOnClickListener(v -> {
            medicines.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void setData(List<Medicine> medicineList) {
        this.medicines = medicineList;
    }

}


