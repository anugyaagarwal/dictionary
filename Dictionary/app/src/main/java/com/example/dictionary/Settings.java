package com.example.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class Settings extends Activity {
    ImageView home;
    Spinner spinner1, spinner2, spinner3, spinner4;
    final String[] fontsize = {"20", "22", "24", "26", "28", "30", "32", "34", "36", "38"};
    final String[] fontcolor = {"white", "red", "yellow", "black", "blue", "violet"};
    final int[] fontcolorId = {R.color.white, R.color.red, R.color.yellow, R.color.black, R.color.blue, R.color.violet,};
    final String[] fontface = {"carrois", "cursive", "cutive_mono", "dancing_mono", "clock"};
    final String[] background = {"white", "red", "yellow", "black", "blue", "violet"};
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        ImageView imageView = (ImageView) findViewById(R.id.home);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Settings.this, Welcome.class));
            }
        });


        preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        editor = preferences.edit();


        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, fontsize);
        spinner1.setAdapter(arrayAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString("fontsize", parent.getSelectedItem().toString());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, fontcolor);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                editor.putInt("fontcolor", fontcolorId[parent.getSelectedItemPosition()]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, fontface);
        spinner3.setAdapter(arrayAdapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString("fontface", parent.getSelectedItem().toString());
                editor.commit();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, background);
        spinner4.setAdapter(arrayAdapter4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("background", fontcolorId[parent.getSelectedItemPosition()]);
                editor.commit();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}

