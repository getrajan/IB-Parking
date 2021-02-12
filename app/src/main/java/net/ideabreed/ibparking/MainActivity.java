package net.ideabreed.ibparking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import net.ideabreed.ibparking.database.DatabaseHelper;
import net.ideabreed.ibparking.model.User;
import net.ideabreed.ibparking.view.auth.LoginActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File kipuDb = getApplicationContext().getDatabasePath("kipu.db");
        if (kipuDb.exists()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            Log.d("hi","no dab");
            DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);

            User conductor = new User("test","test123",false);
            dbHelper.addUser(conductor);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}