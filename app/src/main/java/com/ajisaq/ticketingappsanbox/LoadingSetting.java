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

public class LoadingSetting extends AppCompatActivity implements View.OnClickListener{
    CheckBox cbCar, cbKeke, cbBus, cbHilux, cbSienna, cbJeep,
            cbTrailer, cbCanter, cbTangul, cbJ5,
            cbWheelbarrow;
    EditText etCar, etKeke, etBus, etHilux, etSienna, etJeep,
            etTrailer, etCanter, etTangul, etJ5,
            etWheelbarrow;
    Button btloadingNext;
    SharedPreferences preferences;

    private final String LOCALSTORAGE = "com.ajisaq.storage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_loading_setting);


        cbCar = findViewById(R.id.lcar);
        cbKeke = findViewById(R.id.lkeke);
        cbBus = findViewById(R.id.lbus);
        cbHilux = findViewById(R.id.lhilux);
        cbSienna = findViewById(R.id.lsienna);
        cbJeep = findViewById(R.id.ljeep);
        cbTrailer = findViewById(R.id.ltrailer);
        cbCanter = findViewById(R.id.lcanter);
        cbTangul = findViewById(R.id.ltangul);
        cbJ5 = findViewById(R.id.lj5);
        cbWheelbarrow = findViewById(R.id.lwheelbarrow);
        btloadingNext = findViewById(R.id.btloadingNext);

        etCar = findViewById(R.id.lcarValue);
        etKeke = findViewById(R.id.lkekeValue);
        etBus = findViewById(R.id.lbusValue);
        etHilux = findViewById(R.id.lhiluxValue);
        etSienna = findViewById(R.id.lsiennaValue);
        etJeep = findViewById(R.id.ljeepValue);
        etTrailer = findViewById(R.id.ltrailerValue);
        etCanter = findViewById(R.id.lcanterValue);
        etTangul = findViewById(R.id.ltangulValue);
        etJ5 = findViewById(R.id.lj5Value);
        etWheelbarrow = findViewById(R.id.lwheelbarrowValue);

        cbCar.setOnClickListener(this);
        cbKeke.setOnClickListener(this);
        cbBus.setOnClickListener(this);
        cbHilux.setOnClickListener(this);
        cbSienna.setOnClickListener(this);
        cbJeep.setOnClickListener(this);
        cbTrailer.setOnClickListener(this);
        cbCanter.setOnClickListener(this);
        cbTangul.setOnClickListener(this);
        cbJ5.setOnClickListener(this);
        cbWheelbarrow.setOnClickListener(this);
        btloadingNext.setOnClickListener(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoading", true);
        editor.apply();
    }

    private Boolean proceed = false;
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        switch (v.getId()){
            case R.id.btloadingNext:
                if(cbCar.isChecked() || cbKeke.isChecked() ||cbBus.isChecked() ||
                        cbHilux.isChecked() || cbSienna.isChecked() || cbJeep.isChecked() ||
                        cbTrailer.isChecked() || cbCanter.isChecked() || cbTangul.isChecked() ||
                        cbJ5.isChecked() || cbWheelbarrow.isChecked()) {
                    if (cbCar.isChecked()) {
                        if (!etCar.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("lcar", etCar.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Car", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbKeke.isChecked()) {
                        if (!etKeke.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("lkeke", etKeke.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Keke Napep", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbBus.isChecked()) {
                        if (!etBus.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("lbus", etBus.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Bus", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbHilux.isChecked()) {
                        if (!etHilux.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("lhilux", etHilux.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Hilux", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbSienna.isChecked()) {
                        if (!etSienna.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("lsienna", etSienna.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Sienna", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbJeep.isChecked()) {
                        if (!etJeep.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("ljeep", etJeep.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Jeep", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbTrailer.isChecked()) {
                        if (!etTrailer.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("ltrailer", etTrailer.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Trailer", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbCanter.isChecked()) {
                        if (!etCanter.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("lcanter", etCanter.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Canter", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbTangul.isChecked()) {
                        if (!etTangul.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("ltangul", etTangul.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Tangul", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbJ5.isChecked()) {
                        if (!etJ5.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("lj5", etJ5.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for J5", Toast.LENGTH_LONG).show();

                        }
                    }
                    if (cbWheelbarrow.isChecked()) {
                        if (!etWheelbarrow.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("lwheelbarrow", etWheelbarrow.getText().toString());
                            editor.apply();
                            proceed = true;
                        }else {
                            proceed = false;
                            Toast.makeText(this, "Input value for Wheelbarrow", Toast.LENGTH_LONG).show();

                        }
                    }
                    if(proceed) {
                        // proceed to next screen
                        if(preferences.getBoolean("animals", false)){
                            editor.putBoolean("animals", false);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), GatePassSetting.class));
                        }else if(preferences.getBoolean("gatePass", false)){
                            editor.putBoolean("gatePass", false);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), LoadingSetting.class));
                        }else if(preferences.getBoolean("produce", false)){
                            editor.putBoolean("produce", false);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), ProduceSetting.class));
                        }else if(preferences.getBoolean("facility", false)){
                            editor.putBoolean("facility", false);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), FacilitySetting.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), Summary.class));

                        }


                    }
                }else {
                    Toast.makeText(this, "An Item must be configured", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.lcar:
                if(cbCar.isChecked()){
                    etCar.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etCar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lkeke:
                if(cbKeke.isChecked()){
                    etKeke.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etKeke.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lbus:
                if(cbBus.isChecked()){
                    etBus.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etBus.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lhilux:
                if(cbHilux.isChecked()){
                    etHilux.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etHilux.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lsienna:
                if(cbSienna.isChecked()){
                    etSienna.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etSienna.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.ljeep:
                if(cbJeep.isChecked()){
                    etJeep.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etJeep.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.ltrailer:
                if(cbTrailer.isChecked()){
                    etTrailer.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etTrailer.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lcanter:
                if(cbCanter.isChecked()){
                    etCanter.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etCanter.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.ltangul:
                if(cbTangul.isChecked()){
                    etTangul.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etTangul.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lj5:
                if(cbJ5.isChecked()){
                    etJ5.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etJ5.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lwheelbarrow:
                if(cbWheelbarrow.isChecked()){
                    etWheelbarrow.setVisibility(View.VISIBLE);
                    btloadingNext.setEnabled(true);
                }else{
                    etWheelbarrow.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
