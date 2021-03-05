package net.ideabreed.ibparking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.posapi.PosApi;

import androidx.appcompat.app.AppCompatActivity;

import net.ideabreed.ibparking.database.DatabaseHelper;
import net.ideabreed.ibparking.model.User;
import net.ideabreed.ibparking.view.auth.LoginActivity;
import net.ideabreed.ibparking.view.home.HomeActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    private PosApi posApi;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        PowerUtils.power("1");
//        posApi = PosApi.getInstance(this);
//        posApi.initDeviceEx("/dev/ttyMT2");

        File kipuDb = getApplicationContext().getDatabasePath("kipu.db");
        if (kipuDb.exists()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    boolean login = sharedPreferences.getBoolean("isLogin", false);
                    if (login) {
                        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
            User conductor = new User("test", "test123", false, 1);
            dbHelper.addUser(conductor);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}