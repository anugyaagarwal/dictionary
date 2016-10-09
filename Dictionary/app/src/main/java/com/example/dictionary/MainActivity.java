package com.example.dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Utility.isNetworkAvailable(this)) {
            searchWord();
        } else {
            Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show();
        }

    }

    String word="android";


    public void searchWord()
    {
        Ion.with(this).load(Constant.URL + "headword="+ word).setTimeout(30000).asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String response) {
                        if (e != null) {
                            Toast.makeText(MainActivity.this, "Error Occurred 1", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                                Log.e("RESPONSE ..", response );
                                WordBean wordBean= JsonParser.getDataFromServer(response);
                                Toast.makeText(MainActivity.this, wordBean.getDefinition(), Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this, wordBean.getAudio_url(), Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this, wordBean.getPart_of_speech(), Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this, wordBean.getWord(), Toast.LENGTH_LONG).show();
                            } catch (Exception parseException) {
                                Log.e("MainActivity", parseException.toString());
                                Toast.makeText(MainActivity.this, "Error Occurred 2", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
