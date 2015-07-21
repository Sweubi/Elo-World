package com.esgi.groupe1.eloworld.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.esgi.groupe1.eloworld.R;


public class DialogEmail extends DialogFragment {
    AlertDialog.Builder builder;
    LayoutInflater inflater;
    ProgressDialog progressDialog;
    String URL_check_mail ="http://192.168.31.1/eloworldweb/code/WebService/divers/checkemailexist.php";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the layout inflater
        builder = new AlertDialog.Builder(getActivity());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.title_activity_dialog_email);
        builder.setView(inflater.inflate(R.layout.activity_dialog_email,null));
        builder.setIcon(R.drawable.ic_action_person_green);

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
                //close the Dialog view
                getDialog().dismiss();

            }
        });
        return builder.create();
    }

    class CheckMailExist extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }
    }




}
