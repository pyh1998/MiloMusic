package com.example.social_network_app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.utils.SQLiteHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    TextInputLayout  et_password, et_email,et_name,et_age;
    RadioGroup sex;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.btnRegSubmit);

        et_password = findViewById(R.id.regPassword);
        et_email = findViewById(R.id.regEmail);
        sex = findViewById(R.id.rg_sex);
        et_name = findViewById(R.id.user_name);
        et_age = findViewById(R.id.user_age);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getEditText().getText().toString();
                String password = et_password.getEditText().getText().toString();
                RadioButton radioButton = findViewById(sex.getCheckedRadioButtonId());
                String sex = radioButton.getText().toString();
                String name = et_name.getEditText().getText().toString();
                int age = Integer.parseInt(et_age.getEditText().getText().toString());
                userList = getUserList();
                User current = new User(userList.size()+1,name,email,age,sex);
                userList.add(current);
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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userList", (Serializable) userList);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    Toast.makeText(RegisterActivity.this,"Registered successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"Registered successfully", Toast.LENGTH_SHORT).show();

                }


            }


            });
    }

    public List<User> getUserList(){
        String myjson = getJson(this, "user.json");
        Gson gson = new Gson();
        return  gson .fromJson(myjson, new TypeToken<List<User>>(){}.getType());
    }

    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}
