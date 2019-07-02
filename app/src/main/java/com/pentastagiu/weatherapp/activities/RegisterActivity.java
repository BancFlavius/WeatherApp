package com.pentastagiu.weatherapp.activities;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.pentastagiu.weatherapp.ApiConstants;
import com.pentastagiu.weatherapp.R;
import com.pentastagiu.weatherapp.data.User;
import com.pentastagiu.weatherapp.database.AppDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private final String TAG = RegisterActivity.class.getSimpleName();
    private String birthday;
    private SharedPreferences sharedPreferences;


    private Button btn_register;
    private Button btn_birthday;
    private EditText et_name;
    private EditText et_email;
    private EditText et_password;
    private EditText et_passwordConfirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeractivity_layout);

        initUit();
        setLiseners();
    }

    private void initUit() {
        btn_register = findViewById(R.id.btn_register);
        btn_birthday = findViewById(R.id.btn_birthday);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_passwordConfirm = findViewById(R.id.et_passwordConfirm);
    }

    private void setLiseners(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()) {
                    doRegister();
                }
            }
        });

        btn_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                birthdayDialog();
            }
        });
    }

    private void birthdayDialog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, R.style.CustomDatePickerDialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        birthday = (day + "/" + (month + 1) + "/" + year);
                        btn_birthday.setText(birthday);
                        btn_birthday.setClickable(false);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void doRegister(){
        if(emailIsADuplicate(et_email.getText().toString())){
            Toast.makeText(getApplicationContext(), "Email was already used", Toast.LENGTH_LONG).show();
        }else {
            sharedPreferences = getSharedPreferences(ApiConstants.SHARE_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(ApiConstants.EMAIL_KEY, et_email.getText().toString());
            editor.putString(ApiConstants.PASSWORD_KEY, et_password.getText().toString());
            editor.apply();

            insertUser(et_name.getText().toString(), et_email.getText().toString(), birthday);

            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, BufferActivity.class));
        }
    }

    private boolean validateFields(){
        if (emailIsValid(et_email) && passwordsMatch(et_password, et_passwordConfirm) && !et_name.getText().toString().equals("")) {
            return true;
        } else if (!emailIsValid(et_email)){
            Toast emailIsInvalid = Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_LONG);
            emailIsInvalid.show();
            return false;
        } else if(!passwordsMatch(et_password, et_passwordConfirm)){
            Toast passwordDoNotMatch = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG);
            passwordDoNotMatch.show();
            return false;
        } else {
            return false;
        }
    }

    private boolean passwordsMatch(EditText et_password, EditText et_passwordConfirm) {
        String passwordText = et_password.getText().toString();
        String passwordConfirmText = et_passwordConfirm.getText().toString();

        return passwordText.equals(passwordConfirmText);
    }

    private boolean emailIsValid(EditText et_email) {
        String emailText = et_email.getText().toString();

        return emailText.contains("@") && emailText.contains(".");
    }


    private void insertUser(String name, String email, String birthday){
        User user = new User(0, name, email, birthday);
        AppDatabase.getInstance().userDao().insert(user);
        ApiConstants.userId = AppDatabase.getInstance().userDao().getUserIdByEmail(email);
    }

    private boolean emailIsADuplicate(String email){
        try{
            AppDatabase.getInstance().userDao().getUserByEmail(email).toString();
            return true;
        }catch (NullPointerException e){
            Log.d(TAG, "//emailIsDuplicate email is not in db");
            return false;
        }
    }
}
