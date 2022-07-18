package com.example.coldemail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class list_view extends AppCompatActivity {
    TextView email;
    TextView name;
    ArrayList email_array;
    private list list;

    public list_view(){
        list = new list();
        List<String> email_temp = list.getList();
        email_array = new ArrayList<>();
        email_array.addAll(email_temp);
    }

    public static void setOnClickListener(View.OnClickListener onClickListener) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        email = findViewById(R.id.email_list);
        name = findViewById(R.id.name_list);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }




}