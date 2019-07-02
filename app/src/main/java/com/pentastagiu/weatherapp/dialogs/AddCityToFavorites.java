package com.pentastagiu.weatherapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.pentastagiu.weatherapp.ApiConstants;
import com.pentastagiu.weatherapp.activities.ForecastActivity;
import com.pentastagiu.weatherapp.database.AppDatabase;


public class AddCityToFavorites extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = "Add City to favorites";
        String message = "Are you sure you want to add this city to favorites?";
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("ADD CITY TO FAVORITES", "positive button");
                        com.pentastagiu.weatherapp.data.City city = new com.pentastagiu.weatherapp.data.City(0, ForecastActivity.cityName, ApiConstants.userId);
                        AppDatabase.getInstance().cityDao().insert(city);
                        Toast.makeText(getContext(), "City added to favorites", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("ADD CITY TO FAVORITES", "negative button");
                dismiss();
            }
        });

        return builder.create();
    }
}
