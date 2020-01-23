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

public class AnimalsSetting extends AppCompatActivity implements View.OnClickListener{
    CheckBox cbCamel, cbCow, cbGoat, cbSheep;
    Button btAnimalsNext;
    EditText etCamel, etCow, etGoat, etSheep;
    private final String LOCALSTORAGE = "com.ajisaq.storage";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_animals_setting);
        cbCamel = findViewById(R.id.camel);
        cbCow = findViewById(R.id.cow);
        cbGoat = findViewById(R.id.goat);
        cbSheep = findViewById(R.id.sheep);

        etCamel = findViewById(R.id.camelValue);
        etCow = findViewById(R.id.cowValue);
        etGoat = findViewById(R.id.goatValue);
        etSheep = findViewById(R.id.sheepValue);

        btAnimalsNext = findViewById(R.id.btanimalsNext);

        cbCamel.setOnClickListener(this);
        cbCow.setOnClickListener(this);
        cbGoat.setOnClickListener(this);
        cbSheep.setOnClickListener(this);
        btAnimalsNext.setOnClickListener(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAnimals", true);
        editor.apply();
    }
    private Boolean proceed = false;
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        switch (v.getId()){
            case R.id.btanimalsNext:
                if(cbCamel.isChecked() || cbCow.isChecked() ||cbGoat.isChecked() ||
                        cbSheep.isChecked() ) {
                    if (cbCamel.isChecked()) {
                        if (!etCamel.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("camel", etCamel.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Camel", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbCow.isChecked()) {
                        if (!etCow.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("cow", etCow.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Cow", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbGoat.isChecked()) {
                        if (!etGoat.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("goat", etGoat.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Goat", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbSheep.isChecked()) {
                        if (!etSheep.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("sheep", etSheep.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Sheep", Toast.LENGTH_LONG).show();

                        }
                    }
                    if(proceed) {
                        // proceed to next screen
                        if(preferences.getBoolean("gatePass", false)){
                            editor.putBoolean("gatePass", false);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), GatePassSetting.class));
                        }else if(preferences.getBoolean("loading", false)){
                            editor.putBoolean("loading", false);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), LoadingSetting.class));
                        }else if(preferences.getBoolean("produce", false)){
                            editor.putBoolean("produce", false);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), ProduceSetting.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), Summary.class));

                        }


                    }
                }else {
                    Toast.makeText(this, "A day must be configured", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.camel:
                if(cbCamel.isChecked()){
                    etCamel.setVisibility(View.VISIBLE);
                    btAnimalsNext.setEnabled(true);
                }else{
                    etCamel.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.cow:
                if(cbCow.isChecked()){
                    etCow.setVisibility(View.VISIBLE);
                    btAnimalsNext.setEnabled(true);
                }else{
                    etCow.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.goat:
                if(cbGoat.isChecked()){
                    etGoat.setVisibility(View.VISIBLE);
                    btAnimalsNext.setEnabled(true);
                }else{
                    etGoat.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.sheep:
                if(cbSheep.isChecked()){
                    etSheep.setVisibility(View.VISIBLE);
                    btAnimalsNext.setEnabled(true);
                }else{
                    etSheep.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                break;
        }
    }
}
