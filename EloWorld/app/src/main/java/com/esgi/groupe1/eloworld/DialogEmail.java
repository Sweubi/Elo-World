package com.esgi.groupe1.eloworld;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;


public class DialogEmail extends DialogFragment {
    AlertDialog.Builder builder;
    LayoutInflater inflater;
    EditText email;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the layout inflater
        builder = new AlertDialog.Builder(getActivity());
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.title_activity_dialog_email);
        builder.setView(inflater.inflate(R.layout.activity_dialog_email,null));
        // Add action send buttons
        builder.setPositiveButton(R.string.send,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // Add action cancel buttons
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogEmail.this.getDialog().cancel();

            }
        });

        return builder.create();
    }




}
