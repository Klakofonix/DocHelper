package com.example.dochelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dochelper.Activities.Menu;
import com.example.dochelper.Patientcard;
import com.example.dochelper.R;

import java.util.ArrayList;
import java.util.List;


public class PatientCardAdapter extends RecyclerView.Adapter<PatientCardAdapter.PatientsCardViewHolder> {
    private Context mCtx;
    private List<Patientcard> patientcardList;
    private ClickedItem clickedItem;

    public PatientCardAdapter(ClickedItem clickedItem){
        this.clickedItem = clickedItem;
        notifyDataSetChanged();
    }
    public void setData(List<Patientcard> patientcardList){
        this.patientcardList = patientcardList;

    }

    @NonNull
    @Override
    public PatientsCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCtx = parent.getContext();
        return new PatientCardAdapter.PatientsCardViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_patientcard,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull PatientsCardViewHolder holder, int position) {

        Patientcard pc = patientcardList.get(position);


        holder.textViewDate.setText(pc.getDate());
        holder.textViewDoctor.setText("Doktor: "+pc.getDoctor());
        holder.textViewSymptoms.setText("Symptomy: "+pc.getSymptoms());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedCard(pc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientcardList.size();
    }

    public void filterList(ArrayList<Patientcard> filteredList) {
        patientcardList = filteredList;
        notifyDataSetChanged();
    }

    public class PatientsCardViewHolder extends RecyclerView.ViewHolder {
        TextView  textViewDate, textViewDoctor, textViewSymptoms;
        public PatientsCardViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.textView_PatientsCards_Date);
            textViewDoctor= itemView.findViewById(R.id.textView_PatientsCards_Doctor);
            textViewSymptoms= itemView.findViewById(R.id.textView_PatientsCards_Symptoms);

        }
    }

    public interface ClickedItem{
         void ClickedCard(Patientcard pc);

        boolean onCreateOptionsMenu(Menu menu);
    }
}
