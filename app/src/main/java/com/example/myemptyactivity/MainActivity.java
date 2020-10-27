package com.example.myemptyactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent myIntent = new Intent(this, SchemaActivity.class);
//        myIntent.putExtra("key", "value");
//        startActivity(myIntent);

        Intent basicQueryIntent = new Intent(this, BasicQueryActivity.class);
        basicQueryIntent.putExtra("key", "basicQueryActivity");
        startActivity(basicQueryIntent);
    }
}