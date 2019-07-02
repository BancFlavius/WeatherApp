package com.pentastagiu.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pentastagiu.weatherapp.R;



public class SearchActivity extends AppCompatActivity {
    private EditText cityNameEditText;
    private Button searchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity_layout);
        initUi();
        setListeners();
    }

    private void initUi(){
        searchButton = findViewById(R.id.btn_search);
    }

    private void setListeners(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, AutocompleteSupportFragmentActivity.class);
                startActivity(intent);
            }
        });

    }

    public void toBufferActivity(View view) {
        SearchActivity.this.startActivity(new Intent(SearchActivity.this, BufferActivity.class));
    }
}
