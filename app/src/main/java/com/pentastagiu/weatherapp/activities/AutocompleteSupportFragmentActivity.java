package com.pentastagiu.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.pentastagiu.weatherapp.ApiConstants;
import com.pentastagiu.weatherapp.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;

public class AutocompleteSupportFragmentActivity extends AppCompatActivity {
    private static final String TAG = "AutocompleteSupportFrag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete_support_fragment_activity_layout);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);

        Places.initialize(getApplicationContext(), ApiConstants.PLACES_API_KEY);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                AutocompleteSupportFragmentActivity.this.startActivity(new Intent(AutocompleteSupportFragmentActivity.this, ForecastActivity.class).putExtra(ApiConstants.INTENT_CITY_LIST, place.getName()));
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

}
