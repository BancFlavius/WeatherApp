package com.pentastagiu.weatherapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pentastagiu.weatherapp.ApiConstants;
import com.pentastagiu.weatherapp.R;
import com.pentastagiu.weatherapp.database.AppDatabase;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_register;
    private EditText et_email;
    private EditText et_password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity_layout);

        initUi();
        addApplicationLogo();
        setListeners();

    }

    private void initUi(){
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
    }

    private void setListeners(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()){
                    doLogin();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void doLogin(){
        ApiConstants.userId = AppDatabase.getInstance().userDao().getUserIdByEmail(et_email.getText().toString());
        LoginActivity.this.startActivity(new Intent(LoginActivity.this, BufferActivity.class));
    }

    private boolean validateFields(){
        SharedPreferences sharedPreferences = getSharedPreferences(ApiConstants.SHARE_PREF_NAME, MODE_PRIVATE);
        if(sharedPreferences.getString(ApiConstants.EMAIL_KEY, " ").equals(et_email.getText().toString())
                && sharedPreferences.getString(ApiConstants.PASSWORD_KEY, " ").equals(et_password.getText().toString())
        && isEmailValid(et_email.getText().toString())){
            return true;
        } else if (et_password.getText().toString().equals("") || et_email.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Email or password is invalid", Toast.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(getApplicationContext(), "Email or password is invalid", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean isEmailValid(String email){
        try{
            AppDatabase.getInstance().userDao().getUserByEmail(email).toString();
            return true;
        }catch (NullPointerException e){
            return false;
        }
    }

    private void addApplicationLogo(){
        ImageView imgTest = findViewById(R.id.img_logo);
        Glide.with(this)
                .load("https://i.imgur.com/QQ9CXHJ.png")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgTest);
    }
}
