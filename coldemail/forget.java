package com.example.coldemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class forget extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText password;
    TextView forget;
    Button change_pass;
    Button set_pass;
    UserSession session;
    // DBHelper db;
    private SharedPreferences sharedPreferences;

    private static final String PREFER_NAME = "Reg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        username = findViewById(R.id.username_text);
        email = findViewById(R.id.email_text);
        password = findViewById(R.id.new_pass);
        change_pass = findViewById(R.id.forget_btn);
        set_pass = findViewById(R.id.set_pass);


        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        // User Session Manager
        session = new UserSession(getApplicationContext());

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = username.getText().toString();
                String pa = password.getText().toString();
                String em = email.getText().toString();
                if(us.trim().length() > 0 && em.trim().length() > 0) {

                    String uName = null;
                    String uPassword = null;
                    String uemail = null;

                    if (sharedPreferences.contains("username_key")) {

                        uName = sharedPreferences.getString("username_key", "");

                }

                    if (sharedPreferences.contains("password_key")) {
                        uPassword = sharedPreferences.getString("password_key", "");

                }
                    if (sharedPreferences.contains("email_key")) {
                        uemail = sharedPreferences.getString("email_key", "");

                }
                    if (us.equals(uName) && em.equals(uemail)){
                        session.createUserLoginSession(uName,
                                uPassword);
                        password.setVisibility(View.VISIBLE);
                        set_pass.setVisibility(View.VISIBLE);
                }
                    if (!us.equals(uName) && !em.equals(uemail)){
                        Toast.makeText(forget.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                    Toast.makeText(forget.this, "Please Fill THe Details", Toast.LENGTH_SHORT).show();
                }}
        });
        set_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = username.getText().toString();
                String pa = password.getText().toString();
                String em = email.getText().toString();
                if (pa.trim().length()>0){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password_key",pa);
                    editor.commit();
                    Toast.makeText(forget.this, "Password Successfully Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(forget.this , login.class));
                }
            }
        });
    }
}