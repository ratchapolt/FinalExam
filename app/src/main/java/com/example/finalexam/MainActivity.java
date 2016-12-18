package com.example.finalexam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalexam.db.dbHelper;
import com.example.finalexam.adapter.LoginAdapter;

public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private Button mRegister;
    private EditText mUsername;
    private EditText mPassword;


    private Context mContext;
    private dbHelper mManeger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManeger = new dbHelper(this);
        mContext = this;

        mLogin = (Button) findViewById(R.id.btn_login);
        mRegister = (Button) findViewById(R.id.btn_register);
        mUsername = (EditText) findViewById(R.id.userLogin);
        mPassword = (EditText) findViewById(R.id.passwordLogin);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkLogin() {
        String username = mUsername.getText().toString().trim().toLowerCase();
        String password = mPassword.getText().toString().trim();

        LoginAdapter user = new LoginAdapter(username, password);

        LoginAdapter validateUser = mManeger.checkUserLogin(user);

        if (null == validateUser) {
            String message = getString(R.string.login_error_message);
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra(LoginAdapter.Column.USERNAME, validateUser.getUsername());
            intent.putExtra(LoginAdapter.Column.ID, validateUser.getId());
            startActivity(intent);
            finish();
        }
    }


    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}

