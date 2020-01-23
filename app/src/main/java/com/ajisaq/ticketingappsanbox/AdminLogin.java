package com.ajisaq.ticketingappsanbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ajisaq.ticketingappsanbox.Utils.ButtonDelayUtils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


public class AdminLogin extends AppCompatActivity {
    SharedPreferences preferences;
    private final String LOCALSTORAGE = "com.ajisaq.storage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check for first installation of the app and present loging activity

        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        if(preferences.getBoolean("login", false)){
            startActivity(new Intent(this, MainActivity.class));
        }
        setContentView(R.layout.activity_admin_login);


        Button btunlock = findViewById(R.id.unlock);
        btunlock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText secretKey = findViewById(R.id.secretKey);

                String key = secretKey.getText().toString();
                //Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();
                String passkey = getMacAddr();
                //Toast.makeText(getApplicationContext(), passkey, Toast.LENGTH_LONG).show();
                if (key.equals(passkey) && key != null){
                    //move to next screen
                    startActivity(new Intent(getApplicationContext(), DeviceSetting.class));
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid Key Try Again", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String hex = Integer.toHexString(b & 0xFF);
                    if (hex.length() == 1)
                        hex = "0".concat(hex);
                    res1.append(hex.concat(""));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return null;
    }
}
