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

public class ProduceSetting extends AppCompatActivity implements View.OnClickListener{
    CheckBox cbMaize, cbBeans, cbRice, cbMillet;
    Button btProduceNext;
    EditText etMaize, etBeans, etRice, etMillet;
    private final String LOCALSTORAGE = "com.ajisaq.storage";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_produce_setting);
        cbMaize = findViewById(R.id.maize);
        cbBeans = findViewById(R.id.beans);
        cbRice = findViewById(R.id.rice);
        cbMillet = findViewById(R.id.millet);

        etMaize = findViewById(R.id.maizeValue);
        etBeans = findViewById(R.id.beansValue);
        etRice = findViewById(R.id.riceValue);
        etMillet = findViewById(R.id.milletValue);

        btProduceNext = findViewById(R.id.btproduceNext);

        cbMaize.setOnClickListener(this);
        cbRice.setOnClickListener(this);
        cbBeans.setOnClickListener(this);
        cbMillet.setOnClickListener(this);
        btProduceNext.setOnClickListener(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isProduce", true);
        editor.apply();


    }
    private Boolean proceed = false;
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = preferences.edit();

        switch (v.getId()){
            case R.id.btproduceNext:
                if(cbMaize.isChecked() || cbBeans.isChecked() ||cbRice.isChecked() ||
                        cbMillet.isChecked() ) {
                    if (cbMaize.isChecked()) {
                        if (!etMaize.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("maize", etMaize.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Bag of Maize", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbBeans.isChecked()) {
                        if (!etBeans.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("beans", etBeans.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Bag of Beans", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbRice.isChecked()) {
                        if (!etRice.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("rice", etRice.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Bag of Rice", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbMillet.isChecked()) {
                        if (!etMillet.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("millet", etRice.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Bag of Millet", Toast.LENGTH_LONG).show();

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
                        }else if(preferences.getBoolean("animals", false)){
                             editor.putBoolean("animals", false);
                             editor.apply();
                            startActivity(new Intent(getApplicationContext(), AnimalsSetting.class));
                        }else if(preferences.getBoolean("facility", false)){
                             editor.putBoolean("facility", false);
                             editor.apply();
                             startActivity(new Intent(getApplicationContext(), FacilitySetting.class));
                         }else{
                             startActivity(new Intent(getApplicationContext(), Summary.class));

                         }


                    }
                }else {
                    Toast.makeText(this, "A day must be configured", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.maize:
                if(cbMaize.isChecked()){
                    etMaize.setVisibility(View.VISIBLE);
                    btProduceNext.setEnabled(true);
                }else{
                    etMaize.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.rice:
                if(cbRice.isChecked()){
                    etRice.setVisibility(View.VISIBLE);
                    btProduceNext.setEnabled(true);
                }else{
                    etRice.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.beans:
                if(cbBeans.isChecked()){
                    etBeans.setVisibility(View.VISIBLE);
                    btProduceNext.setEnabled(true);
                }else{
                    etBeans.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.millet:
                if(cbMillet.isChecked()){
                    etMillet.setVisibility(View.VISIBLE);
                    btProduceNext.setEnabled(true);
                }else{
                    etMillet.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
