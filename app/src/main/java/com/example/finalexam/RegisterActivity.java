package com.example.finalexam;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalexam.db.dbHelper;
import com.example.finalexam.adapter.LoginAdapter;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mRegister;

    private Context mContext;
    private dbHelper mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mManager = new dbHelper(this);
        mContext = this;

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsername.getText().toString().trim().toLowerCase();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                if (password.equals(confirmPassword)) {
                    LoginAdapter user = new LoginAdapter(username, password);
                    long rowId = mManager.registerUser(user);

                    if (rowId == -1) {
                        String message = getString(R.string.register_error_message);
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    } else {
                        String message = getString(R.string.register_success);
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    String message = getString(R.string.register_password_error);
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
