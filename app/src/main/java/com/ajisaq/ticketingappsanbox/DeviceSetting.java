package com.ajisaq.ticketingappsanbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class DeviceSetting extends AppCompatActivity implements View.OnClickListener{
    Button btSave;
    CheckBox cbMon, cbTue, cbWed, cbThu, cbFri, cbSat, cbSun;
    EditText etMon, etTue, etWed, etThu, etFri, etSat, etSun;
    private final String LOCALSTORAGE = "com.ajisaq.storage";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_device_setting);
        initView();
    }
    private void initView(){
        btSave = findViewById(R.id.save);
        btSave.setOnClickListener(this);

        cbMon = findViewById(R.id.monday);
        etMon = findViewById(R.id.mondayValue);
        cbMon.setOnClickListener(this);

        cbTue = findViewById(R.id.tuesday);
        etTue = findViewById(R.id.tuesdayValue);
        cbTue.setOnClickListener(this);

        cbWed = findViewById(R.id.wednesday);
        etWed = findViewById(R.id.wednesdayValue);
        cbWed.setOnClickListener(this);

        cbThu = findViewById(R.id.thursday);
        etThu = findViewById(R.id.thursdayValue);
        cbThu.setOnClickListener(this);

        cbFri = findViewById(R.id.friday);
        etFri = findViewById(R.id.fridayValue);
        cbFri.setOnClickListener(this);

        cbSat = findViewById(R.id.saturday);
        etSat = findViewById(R.id.saturdayValue);
        cbSat.setOnClickListener(this);

        cbSun = findViewById(R.id.sunday);
        etSun = findViewById(R.id.sundayValue);
        cbSun.setOnClickListener(this);
    }

    private Boolean proceed = false;
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.save:
                if(cbMon.isChecked() || cbTue.isChecked() ||cbWed.isChecked() ||
                        cbThu.isChecked() || cbFri.isChecked() || cbSat.isChecked() ||
                        cbSun.isChecked() ) {

                    if (cbMon.isChecked()) {
                        if (!etMon.getText().toString().isEmpty()) {
                            //store in local storage
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Mon", etMon.getText().toString());
                            editor.apply();
                            proceed = true;

                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Monday", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbTue.isChecked()) {
                        if (!etTue.getText().toString().isEmpty()) {
                            //store in local storage
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Tue", etTue.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Tuesday", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbWed.isChecked()) {
                        if (!etWed.getText().toString().isEmpty()) {
                            //store in local storage
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Wed", etWed.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Wednesday", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbThu.isChecked()) {
                        if (!etThu.getText().toString().isEmpty()) {
                            //store in local storage
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Thu", etThu.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Thursday", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbFri.isChecked()) {
                        if (!etFri.getText().toString().isEmpty()) {
                            //store in local storage
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Fri", etFri.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Friday", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbSat.isChecked()) {
                        if (!etSat.getText().toString().isEmpty()) {
                            //store in local storage
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Sat", etSat.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Saturday", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbSun.isChecked()) {
                        if (!etSun.getText().toString().isEmpty()) {
                            //store in local storage
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Sun", etSun.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Sunday", Toast.LENGTH_LONG).show();

                        }
                    }
                    if(proceed) {


                        // proceed to next screen

                        startActivity(new Intent(getApplicationContext(), CategorySetting.class));
                    }
                }else {
                    Toast.makeText(this, "A day must be configured", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.monday:
                if(cbMon.isChecked()){
                    etMon.setVisibility(View.VISIBLE);
                    btSave.setEnabled(true);
                }else{
                    etMon.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tuesday:
                if(cbTue.isChecked()){
                    etTue.setVisibility(View.VISIBLE);
                    btSave.setEnabled(true);
                }else{
                    etTue.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.wednesday:
                if(cbWed.isChecked()){
                    etWed.setVisibility(View.VISIBLE);
                    btSave.setEnabled(true);
                }else{
                    etWed.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.thursday:
                if(cbThu.isChecked()){
                    etThu.setVisibility(View.VISIBLE);
                    btSave.setEnabled(true);
                }else{
                    etThu.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.friday:
                if(cbFri.isChecked()){
                    etFri.setVisibility(View.VISIBLE);
                    btSave.setEnabled(true);
                }else{
                    etFri.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.saturday:
                if(cbSat.isChecked()){
                    etSat.setVisibility(View.VISIBLE);
                    btSave.setEnabled(true);
                }else{
                    etSat.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.sunday:
                if(cbSun.isChecked()){
                    etSun.setVisibility(View.VISIBLE);
                    btSave.setEnabled(true);
                }else{
                    etSun.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
