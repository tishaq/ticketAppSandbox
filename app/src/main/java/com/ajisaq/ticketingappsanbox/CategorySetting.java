package com.ajisaq.ticketingappsanbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class CategorySetting extends AppCompatActivity implements View.OnClickListener{
    CheckBox cbProduce, cbGatePass, cbAnimals, cbLoading;
    Button btFinish;
    SharedPreferences preferences;

    private final String LOCALSTORAGE = "com.ajisaq.storage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_category_setting);
         cbProduce = findViewById(R.id.cbProduce);
         cbGatePass = findViewById(R.id.cbGatePass);
         cbAnimals = findViewById(R.id.cbAnimals);
         cbLoading = findViewById(R.id.cbLoading);
         btFinish = findViewById(R.id.btcategoryNext);

         cbProduce.setOnClickListener(this);
         cbGatePass.setOnClickListener(this);
         cbAnimals.setOnClickListener(this);
         cbLoading.setOnClickListener(this);
         btFinish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        switch (v.getId()){
            case R.id.btcategoryNext:
                if(cbProduce.isChecked() || cbGatePass.isChecked() || cbAnimals.isChecked() || cbLoading.isChecked()){
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
                    }else if(preferences.getBoolean("produce", false)){
                        editor.putBoolean("produce", false);
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), ProduceSetting.class));
                    }
                }else {
                    Toast.makeText(this, "Check an item", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cbProduce:
                btFinish.setEnabled(true);
                editor.putBoolean("produce", cbProduce.isChecked());
                editor.apply();
                break;
            case R.id.cbGatePass:
                btFinish.setEnabled(true);
                editor.putBoolean("gatePass", cbGatePass.isChecked());
                editor.apply();
                break;
            case R.id.cbAnimals:
                btFinish.setEnabled(true);
                editor.putBoolean("animals", cbAnimals.isChecked());
                editor.apply();
                break;
            case R.id.cbLoading:
                btFinish.setEnabled(true);
                editor.putBoolean("loading", cbLoading.isChecked());
                editor.apply();
                break;
            default:
                break;

        }
    }
}
