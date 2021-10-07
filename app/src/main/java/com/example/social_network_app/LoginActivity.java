package com.example.social_network_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Basic_classes.UserDao.UserDao;
import com.example.social_network_app.utils.SQLiteHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    //Variables
    TextInputLayout et_email, et_password;
    Button btn_login, btn_register;
    List<User> userList = new ArrayList<>();
    User current;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initView();

        userList = getUserList();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            userList = (List<User>) bundle.getSerializable("userList");
        }

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
                    Toast.makeText(LoginActivity.this,"Please enter your email address and password！",Toast.LENGTH_LONG).show();
                    return;
                }

                for(User user : userList){
                    if(user.getEmail().equals(email)){
                        current = user;
                        break;
                    }
                }

                if(current == null){
                    Toast.makeText(LoginActivity.this,"No user! Please register!",Toast.LENGTH_LONG).show();
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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentUser", current);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"Password error!",Toast.LENGTH_LONG).show();
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

    public List<User> getUserList(){
        UserDao userDao = new UserDao();
        return userDao.findAllUsers(this);
    }

}
