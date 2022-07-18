package com.example.coldemail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class list extends AppCompatActivity {
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    Button signout;
    TextView name;
    RecyclerView recyclerView;
    Adapter adapter;
    AsyncHttpClient client;
    Workbook workbook;
    List<String> name_array;
    List<String> email_array;
    List<String> email_temp;
    ProgressBar progressBar;

    private SharedPreferences sharedPreferences;

    private static final String PREFER_NAME = "Reg";
    UserSession session;


    public List<String> getList() { return email_array; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        signout = findViewById(R.id.signout);
        name = findViewById(R.id.name);
        recyclerView = findViewById(R.id.list_data);
        progressBar = findViewById(R.id.progressBar);
        EditText searchField = findViewById(R.id.searchField);
        searchField.clearFocus();
        name_array = new ArrayList<>();
        email_array = new ArrayList<>();
        email_temp = new ArrayList<>();
        email_temp.addAll(email_array);
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        // User Session Manager
        session = new UserSession(getApplicationContext());
        String uemail = null;

        if (sharedPreferences.contains("email_key"))
        {
            uemail = sharedPreferences.getString("email_key", "");

        }
        name.setText(uemail);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //String pos = email_temp.get(position);
                Toast.makeText(list.this, email_array.get(position), Toast.LENGTH_SHORT).show();
                String[] pos = new String[1];
                pos[0] = email_array.get(position);
                composeEmail(pos);

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(list.this, email_array.get(position), Toast.LENGTH_SHORT).show();

            }
        }));
//        searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filter_List(newText);
//                return false;
//            }
//        });



        //url attachment
        String url = "https://github.com/simar1810/ColdEmail/blob/main/Email%20List.xls?raw=true";
        client = new AsyncHttpClient();
        progressBar.setVisibility(View.VISIBLE);
        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(list.this, "Something Went Wrong/ Download Fail", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(list.this, "File Downloaded.", Toast.LENGTH_SHORT).show();
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                if(file !=null){
                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);
                        for(int i = 0; i < sheet.getRows() ; i++){
                            Cell[] row = sheet.getRow(i);
                            name_array.add(row[0].getContents());
                            email_array.add(row[1].getContents());
                            
                            
                            
                        }
                        showdata();
                    } catch (IOException | BiffException e) {
                        e.printStackTrace();
                    }

                }

            }
        });




        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                filter_List(s.toString());

            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct !=null){
            String personName = acct.getDisplayName();
            name.setText(personName);
        }
        signout.setOnClickListener(view -> signoutmethod());
    }
    void signoutmethod(){
        gsc.signOut().addOnCompleteListener(task -> {
            finish();
            startActivity(new Intent(list.this,login.class));

        });
    }


    private void showdata(){
        adapter = new Adapter(this,name_array,email_array);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
     //TO Search WIth BOth Parameters
//    void filter(String text){
//        List<String> temp = new ArrayList();
//        List<String> email_temp = new ArrayList();
//       for(String c: email_array){
//           if(c.contains(text)){
//               email_temp.add(c);
//           }
//       }
//        for(String d: name_array) {
//            //or use .equal(text) with you want equal match
//            //use .toLowerCase() for better matches
//            if(d.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))){
//                temp.add(d);
//
//
//
//            }
//        }
//        //update recyclerview
//        adapter.updateList(temp);
//        adapter.update_email(email_temp);
//    }
    public void composeEmail(String[] address) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Intent intent1 = intent.setData(Uri.parse("mailto:"));
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        intent.putExtra(Intent.EXTRA_TEXT,"This Mail Is Sent Using Cold Email");
        intent.setPackage("com.google.android.gm");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please Wait Installing GMail", Toast.LENGTH_SHORT).show();
            String link = "https://play.google.com/store/apps/details?id=com.google.android.gm&hl=en_IN&gl=US";
            Uri webpage = Uri.parse(link);
            Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(intent2);

            }
    }
    private void filter_List(String text){
        List<String> filtered = new ArrayList<>();
        List<String> emailtemp = new ArrayList<>();
        for(String c: email_array){
            if(!c.isEmpty()){
                emailtemp.add(c);
            }
        }
        for (String name : name_array ){
            if (name.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))){
                filtered.add(name);

            }
        }
        if (filtered.isEmpty()){
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.updateList(filtered);
            adapter.update_email(emailtemp);

        }
    }

}