package com.example.social_network_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.social_network_app.utils.SQLiteHelper;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    //Variables
    TextInputLayout et_email, et_password;
    Button btn_login, btn_register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {
        et_email = (TextInputLayout) findViewById(R.id.accountUsername);
        et_password = (TextInputLayout) findViewById(R.id.accountPassword);
        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_register = (Button) findViewById(R.id.btnRegister);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getEditText().getText().toString();
                String password = et_password.getEditText().getText().toString();
                if (email.trim().equals("")||password.trim().equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Please enter your email address and password",Toast.LENGTH_LONG).show();
                    return;
                }

                boolean isAccountRight = false;

                SQLiteHelper helper = new SQLiteHelper(LoginActivity.this);
                Cursor rece =  helper.UserInfo_query();
                while (rece.moveToNext()){
                    @SuppressLint("Range") String email_db = rece.getString(rece.getColumnIndex("email"));
                    @SuppressLint("Range") String password_db = rece.getString(rece.getColumnIndex("password"));
                    if(email.equals(email_db) && password.equals(password_db)){
                        isAccountRight = true;
                        break;
                    }
                }
                rece.close();
                helper.close();

                if(isAccountRight){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_LONG).show();
                }
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
