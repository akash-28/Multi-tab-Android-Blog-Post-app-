package app.com.stapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by AKASH on 1/12/2018.
 */

public class StApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


    }
}
