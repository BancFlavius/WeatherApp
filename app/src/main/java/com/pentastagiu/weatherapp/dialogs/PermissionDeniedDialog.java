package com.pentastagiu.weatherapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import com.pentastagiu.weatherapp.activities.BufferActivity;
import com.pentastagiu.weatherapp.activities.SearchActivity;

public class PermissionDeniedDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = "Could not find the location";
        String message = "Please search for your city or go back to the main menu.";
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), SearchActivity.class));
                    }
                }).setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(), BufferActivity.class));
            }
        });

        return builder.create();
    }
}
