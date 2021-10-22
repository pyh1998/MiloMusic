package com.example.social_network_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.social_network_app.Basic_classes.UserDao.CurrentUser;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Basic_classes.UserDao.UserDao;
import com.example.social_network_app.utils.SQLiteHelper;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qihong Zeng
 * The Login page
 */
public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
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

        SQLiteHelper helper = new SQLiteHelper(this);
        ContentValues values = new ContentValues();
        values.put("email", "1");
        values.put("password", "1");
        helper.UserInfo_insert(values);

        initView();
        GlobalVariable global = (GlobalVariable) getApplication();
        userList = global.getUserList();
    }

    private void initView() {
        et_email = findViewById(R.id.accountUsername);
        et_password = findViewById(R.id.accountPassword);
        btn_login = findViewById(R.id.btnLogin);
        btn_register = findViewById(R.id.btnRegister);

        btn_login.setOnClickListener(view -> {
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
                GlobalVariable global = (GlobalVariable) getApplication();
                CurrentUser.delUser();
                CurrentUser user = CurrentUser.getInstance(current);
                userList.add(user);
                global.setUserList(userList);
                global.setUser(user);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"Password error!",Toast.LENGTH_LONG).show();
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


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG," -- onConfigurationChanged");
        setContentView(R.layout.activity_login);
        initView();
    }

}
