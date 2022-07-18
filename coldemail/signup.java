package com.example.coldemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class signup extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText password;
    EditText repassword;
    Button signup;
    TextView backbtn;
    String mypref = "mypref";
    String username_key = "username_key";
    String email_key = "email_key";
    String password_key = "password_key";
    Context context;


    //DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = findViewById(R.id.username_sign);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password_sign);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.signupbutton);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this , login.class));
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String em = email.getText().toString();
                checkdataempty();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
// get editor to edit in file
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (pass.equals(repass) && isEmail(email) && !isEmpty(username) && !isEmpty((EditText) password)){
                    editor.putString(username_key,user);
                    editor.putString(email_key,em);
                    editor.putString(password_key ,pass);
                    editor.commit();
                    Toast.makeText(signup.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signup.this , login.class));

                }
                else{
                    Toast.makeText(signup.this, "Password Not Equals To Re-Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        }

    public void checkdataempty(){
        if (isEmpty(username)) {
            username.setError("Enter valid Username");
            Toast.makeText(signup.this, "Please Enter Your Username", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty((EditText) password)) {
            Toast.makeText(signup.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(repassword)) {
            repassword.setError("Enter valid Repassword");
            Toast.makeText(signup.this, "Please Re-Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        if (!isEmail(email)) {
            email.setError("Enter valid email!");
            Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

        }

        }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


        }