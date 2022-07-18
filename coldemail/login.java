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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

public class login extends AppCompatActivity {
    EditText username;
    EditText password;
    MaterialButton login;
    TextView forget;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    ImageView googlebtn;
    Button signup;
    UserSession session;
   // DBHelper db;
   private SharedPreferences sharedPreferences;

    private static final String PREFER_NAME = "Reg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
          //Google Code
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc!= null){
            navigateToSecondActivity();
        }
        googlebtn = findViewById(R.id.googlebtn);
        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        forget = findViewById(R.id.forget);
        signup = findViewById(R.id.signup);
        //db = new DBHelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,signup.class));
                
                
            }
        });
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        // User Session Manager
        session = new UserSession(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUsername();
                String us = username.getText().toString();
                String pa = password.getText().toString();
                if(us.trim().length() > 0 && pa.trim().length() > 0){
                    String uName = null;
                    String uPassword =null;
                    String uemail = null;

                    if (sharedPreferences.contains("username_key"))
                    {
                        uName = sharedPreferences.getString("username_key", "");

                    }

                    if (sharedPreferences.contains("password_key"))
                    {
                        uPassword = sharedPreferences.getString("password_key", "");

                    }
                    if (sharedPreferences.contains("email_key"))
                    {
                        uemail = sharedPreferences.getString("email_key", "");

                    }

                    // Object uName = null;
                    // Object uEmail = null;
                    if((us.equals(uName) && pa.equals(uPassword)) || (us.equals(uemail) && pa.equals(uPassword))){

                        session.createUserLoginSession(uName,
                                uPassword);
                        Toast.makeText(login.this, "Login Successfull Welcome: " + uemail , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this,list.class));
                        finish();
                    }
                    else{
                        Toast.makeText(login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }

}}});

// Forget password page pending
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(login.this, "Enter Your Username And Email", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(login.this, forget.class));
            }
        });

    }
    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            navigateToSecondActivity();

            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }
    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(login.this,list.class);
        startActivity(intent);
    }
    void checkUsername() {
        boolean isValid = true;
        if (isEmpty(username)) {
            username.setError("You must enter username to login!");
            isValid = false;
//        } else {
//            if (!isEmail(username)) {
//                username.setError("Enter valid email!");
//                isValid = false;
//            }
        }

        if (isEmpty(password)) {
            password.setError("You must enter password to login!");
            isValid = false;
        } else {
            if (password.getText().toString().length() < 4) {
                password.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }

        //check email and password
        //IMPORTANT: here should be call to backend or safer function for local check; For example simple check is cool
        //For example simple check is cool
        if (isValid) {
            String usernameValue = username.getText().toString();
            String passwordValue = password.getText().toString();

        }

    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

}