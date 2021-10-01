package com.example.social_network_app;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.social_network_app.utils.SQLiteHelper;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    TextInputLayout  et_password, et_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.btnRegSubmit);

        et_password = findViewById(R.id.regPassword);
        et_email = findViewById(R.id.regEmail);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getEditText().getText().toString();
                String password = et_password.getEditText().getText().toString();
                if (email.trim().equals("")||password.trim().equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"Please enter your email address and password",Toast.LENGTH_LONG).show();
                    return;
                }

                SQLiteHelper helper = new SQLiteHelper(RegisterActivity.this);
                ContentValues values = new ContentValues();
                values.put("email", email);
                values.put("password", password);
                long res = helper.UserInfo_insert(values);
                helper.close();

                if(res > 0){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);

                    Toast.makeText(RegisterActivity.this,"Registered successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"Registered successfully", Toast.LENGTH_SHORT).show();

                }


            }


            });
    }


}
