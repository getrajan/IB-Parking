package net.ideabreed.ibparking.view.auth;

import android.content.Intent;
import android.os.Bundle;
import android.posapi.PosApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.presenter.LoginService;
import net.ideabreed.ibparking.presenter.helper.PowerUtils;
import net.ideabreed.ibparking.view.home.HomeActivity;

public class LoginActivity extends AppCompatActivity implements LoginService.View {
    private EditText loginEmailET, loginPasswordET;
    private Button loginButton;
    private LoginService.Presenter loginPresenter;

    private PosApi posApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        PowerUtils.power("1");
        posApi = PosApi.getInstance(this);
        posApi.initDeviceEx("/dev/ttyMT2");
        initView();
    }

    private void initView() {
        loginEmailET = findViewById(R.id.loginEmailET);
        loginPasswordET = findViewById(R.id.loginPasswordET);
        loginButton = findViewById(R.id.loginBtn);

//        login button clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                get email and password data
                String loginEmail = loginEmailET.getText().toString();
                String loginPassword = loginPasswordET.getText().toString();

                if (TextUtils.isEmpty(loginEmail) || TextUtils.isEmpty(loginPassword)) {
                    onError("Email and password required");
                } else {
                    loginPresenter.doLogin(loginEmail, loginPassword);
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

}