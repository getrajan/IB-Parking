package net.ideabreed.ibparking.view.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.posapi.PosApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.database.DatabaseHelper;
import net.ideabreed.ibparking.model.User;
import net.ideabreed.ibparking.presenter.LoginActivityPresenter;
import net.ideabreed.ibparking.presenter.LoginService;
import net.ideabreed.ibparking.view.home.HomeActivity;

public class LoginActivity extends AppCompatActivity implements LoginService.View {
    private EditText loginUsernameET, loginPasswordET;
    private Button loginButton;
    private LoginService.Presenter loginPresenter;

    private PosApi posApi;


    public static final String MyPREFERENCES = "MyPrefs" ;


    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
//        PowerUtils.power("1");
//        posApi = PosApi.getInstance(this);
////        posApi.initDeviceEx("/dev/ttyMT2");
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        loginPresenter = new LoginActivityPresenter(this,databaseHelper);
        initView();
    }

    private void initView() {
        loginUsernameET = findViewById(R.id.loginUsernameET);
        loginPasswordET = findViewById(R.id.loginPasswordET);
        loginButton = findViewById(R.id.loginBtn);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//        login button clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                get email and password data
                String loginUsername = loginUsernameET.getText().toString();
                String loginPassword = loginPasswordET.getText().toString();

                if (TextUtils.isEmpty(loginUsername) || TextUtils.isEmpty(loginPassword)) {
                    onError("Username and password required");
                } else {
                    User loginUser = new User(loginUsername,loginPassword);
//                    set boolean to share pref
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("isLogin",true);
                    editor.commit();
                    loginPresenter.doLogin(loginUser);
                }
            }
        });
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNavigate() {
    Intent intent = new Intent(this,HomeActivity.class);
    startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}