package com.example.dochelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmationDialog extends DialogFragment {

    private  ConfirmationDialogListener listener;

    String message;
    public ConfirmationDialog(String message){
        this.message=message;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Uwaga!")
                .setMessage(message)
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYesClicked();
                    }
                });
       return builder.create();
    }

    public interface ConfirmationDialogListener {
        void  onYesClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
try {
    listener = (ConfirmationDialogListener) context;

}catch (ClassCastException e){
    throw new ClassCastException(context.toString()
            + "must implement ConfirmationDialogListener");
}
    }
}
