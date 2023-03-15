package com.example.v.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.v.R;
import com.example.v.status;
import com.google.android.gms.common.api.internal.IStatusCallback;

public class MainActivity extends AppCompatActivity {
    Button btlpr,bsts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btlpr = findViewById(R.id.btlapor);
        bsts  = findViewById(R.id.btstatus);

        btlpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), report.class));
            }
        });

        bsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), status.class));
            }
        });

    }
}