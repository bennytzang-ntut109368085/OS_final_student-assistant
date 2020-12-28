package com.example.os;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Clubtn = (Button) findViewById(R.id.clubtn);
        Button Owebtn = (Button) findViewById(R.id.owebtn);
        Button Curbtn = (Button) findViewById(R.id.curbtn);
        Owebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main_Goodclassmate.class);
                startActivity(intent);
            }
        });
        Clubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Main_Accounting.class);
                startActivity(intent);
            }
        });
        Curbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Main_Timetable.class);
                startActivity(intent);
            }
        });
    }
}
