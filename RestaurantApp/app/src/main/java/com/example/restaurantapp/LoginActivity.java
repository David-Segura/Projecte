package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnEntra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntra = findViewById(R.id.btnEntra);

        btnEntra.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(), TaulesActivity.class);
        startActivity(i);
    }
}