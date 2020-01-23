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

public class GatePassSetting extends AppCompatActivity implements View.OnClickListener {
    CheckBox cbCar, cbKeke, cbBus, cbHilux, cbSienna, cbJeep,
            cbTrailer, cbCanter, cbTangul, cbJ5,
                cbWheelbarrow;
    EditText etCar, etKeke, etBus, etHilux, etSienna, etJeep,
            etTrailer, etCanter, etTangul, etJ5,
            etWheelbarrow;
    Button btgatepassNext;
    SharedPreferences preferences;

    private final String LOCALSTORAGE = "com.ajisaq.storage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_gate_pass_setting);

        cbCar = findViewById(R.id.gcar);
        cbKeke = findViewById(R.id.gkeke);
        cbBus = findViewById(R.id.gbus);
        cbHilux = findViewById(R.id.ghilux);
        cbSienna = findViewById(R.id.gsienna);
        cbJeep = findViewById(R.id.gjeep);
        cbTrailer = findViewById(R.id.gtrailer);
        cbCanter = findViewById(R.id.gcanter);
        cbTangul = findViewById(R.id.gtangul);
        cbJ5 = findViewById(R.id.gj5);
        cbWheelbarrow = findViewById(R.id.gwheelbarrow);
        btgatepassNext = findViewById(R.id.btgatepassNext);

        etCar = findViewById(R.id.gcarValue);
        etKeke = findViewById(R.id.gkekeValue);
        etBus = findViewById(R.id.gbusValue);
        etHilux = findViewById(R.id.ghiluxValue);
        etSienna = findViewById(R.id.gsiennaValue);
        etJeep = findViewById(R.id.gjeepValue);
        etTrailer = findViewById(R.id.gtrailerValue);
        etCanter = findViewById(R.id.gcanterValue);
        etTangul = findViewById(R.id.gtangulValue);
        etJ5 = findViewById(R.id.gj5Value);
        etWheelbarrow = findViewById(R.id.gwheelbarrowValue);

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
        btgatepassNext.setOnClickListener(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isGatePass", true);
        editor.apply();
    }

    private Boolean proceed = false;
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        switch (v.getId()){
            case R.id.btgatepassNext:
                if(cbCar.isChecked() || cbKeke.isChecked() ||cbBus.isChecked() ||
                    cbHilux.isChecked() || cbSienna.isChecked() || cbJeep.isChecked() ||
                    cbTrailer.isChecked() || cbCanter.isChecked() || cbTangul.isChecked() ||
                    cbJ5.isChecked() || cbWheelbarrow.isChecked()) {
                    if (cbCar.isChecked()) {
                        if (!etCar.getText().toString().isEmpty()) {
                            //store in local storage
                            editor.putString("gcar", etCar.getText().toString());
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
                            editor.putString("gkeke", etKeke.getText().toString());
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
                            editor.putString("gbus", etBus.getText().toString());
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
                            editor.putString("ghilux", etHilux.getText().toString());
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
                            editor.putString("gsienna", etSienna.getText().toString());
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
                            editor.putString("gjeep", etJeep.getText().toString());
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
                            editor.putString("gtrailer", etTrailer.getText().toString());
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
                            editor.putString("gcanter", etCanter.getText().toString());
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
                            editor.putString("gtangul", etTangul.getText().toString());
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
                            editor.putString("gj5", etJ5.getText().toString());
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
                            editor.putString("gwheelbarrow", etWheelbarrow.getText().toString());
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
                            startActivity(new Intent(getApplicationContext(), AnimalsSetting.class));
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
                    Toast.makeText(this, "An Item must be configured", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.gcar:
                if(cbCar.isChecked()){
                    etCar.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etCar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gkeke:
                if(cbKeke.isChecked()){
                    etKeke.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etKeke.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gbus:
                if(cbBus.isChecked()){
                    etBus.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etBus.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.ghilux:
                if(cbHilux.isChecked()){
                    etHilux.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etHilux.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gsienna:
                if(cbSienna.isChecked()){
                    etSienna.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etSienna.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gjeep:
                if(cbJeep.isChecked()){
                    etJeep.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etJeep.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gtrailer:
                if(cbTrailer.isChecked()){
                    etTrailer.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etTrailer.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gcanter:
                if(cbCanter.isChecked()){
                    etCanter.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etCanter.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gtangul:
                if(cbTangul.isChecked()){
                    etTangul.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etTangul.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gj5:
                if(cbJ5.isChecked()){
                    etJ5.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etJ5.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.gwheelbarrow:
                if(cbWheelbarrow.isChecked()){
                    etWheelbarrow.setVisibility(View.VISIBLE);
                    btgatepassNext.setEnabled(true);
                }else{
                    etWheelbarrow.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
