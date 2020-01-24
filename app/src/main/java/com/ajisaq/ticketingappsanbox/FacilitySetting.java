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

public class FacilitySetting extends AppCompatActivity implements View.OnClickListener{
    CheckBox cbShop, cbStore, cbRumfa;
    Button btfacilityNext;
    EditText etShop, etStore, etRumfa;
    private final String LOCALSTORAGE = "com.ajisaq.storage";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_facility_setting);

        cbShop = findViewById(R.id.shop);
        cbStore = findViewById(R.id.store);
        cbRumfa = findViewById(R.id.rumfa);

        etShop = findViewById(R.id.shopValue);
        etStore = findViewById(R.id.storeValue);
        etRumfa = findViewById(R.id.rumfaValue);

        btfacilityNext = findViewById(R.id.btfacilityNext);

        cbShop.setOnClickListener(this);
        cbStore.setOnClickListener(this);
        cbRumfa.setOnClickListener(this);
        btfacilityNext.setOnClickListener(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFacility", true);
        editor.apply();
    }
    private Boolean proceed = false;
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        switch (v.getId()){
            case R.id.btfacilityNext:
                if(cbShop.isChecked() || cbStore.isChecked() ||cbRumfa.isChecked() ) {
                    if (cbShop.isChecked()) {
                        if (!etShop.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("shop", etShop.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Shop", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbStore.isChecked()) {
                        if (!etStore.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("store", etStore.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Store", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbRumfa.isChecked()) {
                        if (!etRumfa.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("rumfa", etRumfa.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Rumfa", Toast.LENGTH_LONG).show();

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
                        }else if(preferences.getBoolean("animals", false)){
                            editor.putBoolean("animals", false);
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

            case R.id.shop:
                if(cbShop.isChecked()){
                    etShop.setVisibility(View.VISIBLE);
                    btfacilityNext.setEnabled(true);
                }else{
                    etShop.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.store:
                if(cbStore.isChecked()){
                    etStore.setVisibility(View.VISIBLE);
                    btfacilityNext.setEnabled(true);
                }else{
                    etStore.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.rumfa:
                if(cbRumfa.isChecked()){
                    etRumfa.setVisibility(View.VISIBLE);
                    btfacilityNext.setEnabled(true);
                }else{
                    etRumfa.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }

}
