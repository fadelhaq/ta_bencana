package com.example.v.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.v.R;

public class splashcreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashcreen);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                Intent MainActivity = new Intent(splashcreen.this, com.example.v.activites.MainActivity.class);
                startActivity(MainActivity);
                finish();
            }
        }, 3000);

    }
}

