package com.ajisaq.ticketingappsanbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Summary extends AppCompatActivity {
    SharedPreferences preferences;

    private final String LOCALSTORAGE = "com.ajisaq.storage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_summary);
        TextView tvSummary = findViewById(R.id.tvSummary);
        Button btAccept = findViewById(R.id.btAccept);
        SharedPreferences.Editor editor = preferences.edit();
        String summary = "Market Names \n \n";
        summary += "On Monday: " + preferences.getString("Mon", null) +"\n"+
                "On Tuesday: " + preferences.getString("Tue", null) +"\n"+
                "On Wednesday: " + preferences.getString("Wed", null) +"\n"+
                "On Thursday: " + preferences.getString("Thu", null) +"\n"+
                "On Friday: " + preferences.getString("Fri", null) +"\n"+
                "On Saturday: " + preferences.getString("Sat", null) +"\n"+
                "On Sunday: " + preferences.getString("Sun", null)+"\n";

        if(preferences.getBoolean("isProduce", false)){
           summary += "\n Fees for Produce per Bag\n\n";
           if(!preferences.getString("maize", "").isEmpty()){
               summary += "Maize: "+ preferences.getString("maize", null) +"\n";
           }
           if(!preferences.getString("beans", "").isEmpty()){
                summary += "Beans: "+ preferences.getString("beans", null) +"\n";
            }
            if(!preferences.getString("rice", "").isEmpty()){
                summary += "Rice: "+ preferences.getString("rice", null) +"\n";
            }
            if(!preferences.getString("millet", "").isEmpty()){
                summary += "Millet: "+ preferences.getString("millet", null) +"\n";
            }
        }
        if(preferences.getBoolean("isAnimals", false)){
            summary += "\n Fees for Animals per Head\n\n";
            if(!preferences.getString("camel", "").isEmpty()){
                summary += "Camel: "+ preferences.getString("camel", null) +"\n";
            }
            if(!preferences.getString("cow", "").isEmpty()){
                summary += "Cow: "+ preferences.getString("cow", null) +"\n";
            }
            if(!preferences.getString("goat", "").isEmpty()){
                summary += "Goat: "+ preferences.getString("goat", null) +"\n";
            }
            if(!preferences.getString("sheep", "").isEmpty()){
                summary += "Sheep: "+ preferences.getString("sheep", null) +"\n";
            }
        }
        if(preferences.getBoolean("isGatePass", false)){
            summary += "\n Fees for Gate Pass per Vehicle\n\n";
            if(!preferences.getString("gcar", "").isEmpty()){
                summary += "Car: "+ preferences.getString("gcar", null) +"\n";
            }
            if(!preferences.getString("gkeke", "").isEmpty()){
                summary += "Keke Napep: "+ preferences.getString("gkeke", null) +"\n";
            }
            if(!preferences.getString("gbus", "").isEmpty()){
                summary += "Bus: "+ preferences.getString("gbus", null) +"\n";
            }
            if(!preferences.getString("ghilux", "").isEmpty()){
                summary += "Hilux: "+ preferences.getString("ghilux", null) +"\n";
            }
            if(!preferences.getString("gsienna", "").isEmpty()){
                summary += "Sienna: "+ preferences.getString("gsienna", null) +"\n";
            }
            if(!preferences.getString("gjeep", "").isEmpty()){
                summary += "Jeep: "+ preferences.getString("gjeep", null) +"\n";
            }
            if(!preferences.getString("gtrailer", "").isEmpty()){
                summary += "Trailer: "+ preferences.getString("gtrailer", null) +"\n";
            }
            if(!preferences.getString("gcanter", "").isEmpty()){
                summary += "Canter: "+ preferences.getString("gcanter", null) +"\n";
            }
            if(!preferences.getString("gtangul", "").isEmpty()){
                summary += "Tangul: "+ preferences.getString("gtangul", null) +"\n";
            }
            if(!preferences.getString("gj5", "").isEmpty()){
                summary += "J5: "+ preferences.getString("gj5", null) +"\n";
            }
            if(!preferences.getString("gwheelbarrow", "").isEmpty()){
                summary += "Wheelbarrow: "+ preferences.getString("gwheelbarrow", null) +"\n";
            }

        }
        if(preferences.getBoolean("isLoading", false)){
            summary += "\n Fees for Loading/Offloading per Vehicle\n\n";
            if(!preferences.getString("lcar", "").isEmpty()){
                summary += "Car: "+ preferences.getString("lcar", null) +"\n";
            }
            if(!preferences.getString("lkeke", "").isEmpty()){
                summary += "Keke Napep: "+ preferences.getString("lkeke", null) +"\n";
            }
            if(!preferences.getString("lbus", "").isEmpty()){
                summary += "Bus: "+ preferences.getString("lbus", null) +"\n";
            }
            if(!preferences.getString("lhilux", "").isEmpty()){
                summary += "Hilux: "+ preferences.getString("lhilux", null) +"\n";
            }
            if(!preferences.getString("lsienna", "").isEmpty()){
                summary += "Sienna: "+ preferences.getString("lsienna", null) +"\n";
            }
            if(!preferences.getString("ljeep", "").isEmpty()){
                summary += "Jeep: "+ preferences.getString("ljeep", null) +"\n";
            }
            if(!preferences.getString("ltrailer", "").isEmpty()){
                summary += "Trailer: "+ preferences.getString("ltrailer", null) +"\n";
            }
            if(!preferences.getString("lcanter", "").isEmpty()){
                summary += "Canter: "+ preferences.getString("lcanter", null) +"\n";
            }
            if(!preferences.getString("ltangul", "").isEmpty()){
                summary += "Tangul: "+ preferences.getString("ltangul", null) +"\n";
            }
            if(!preferences.getString("lj5", "").isEmpty()){
                summary += "J5: "+ preferences.getString("lj5", null) +"\n";
            }
            if(!preferences.getString("lwheelbarrow", "").isEmpty()){
                summary += "Wheelbarrow: "+ preferences.getString("lwheelbarrow", null) +"\n";
            }

        }
        if(preferences.getBoolean("isFacility", false)){
            summary += "\n Fees for Facility per Bag\n\n";
            if(!preferences.getString("shop", "").isEmpty()){
                summary += "Shop: "+ preferences.getString("shop", null) +"\n";
            }
            if(!preferences.getString("store", "").isEmpty()){
                summary += "Store: "+ preferences.getString("store", null) +"\n";
            }
            if(!preferences.getString("rumfa", "").isEmpty()){
                summary += "Rumfa: "+ preferences.getString("rumfa", null) +"\n";
            }

        }
        tvSummary.setText(summary);
        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("login", true);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

    }
}
