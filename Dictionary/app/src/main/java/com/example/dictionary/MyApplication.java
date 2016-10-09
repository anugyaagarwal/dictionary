package com.example.dictionary;

import android.app.Application;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Reconnect on 25-07-2016.
 */
@ReportsCrashes(
        mailTo = "dictionarytestuser@gmail.com"
)

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}
