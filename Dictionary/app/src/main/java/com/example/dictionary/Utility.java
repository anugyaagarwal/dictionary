package com.example.dictionary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by HP on 02-07-2016.
 */
public class Utility {


    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return  networkInfo!=null && networkInfo.isConnected() && networkInfo.isAvailable();
    }
}
