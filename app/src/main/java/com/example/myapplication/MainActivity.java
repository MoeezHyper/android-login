package com.example.myapplication;

import android.graphics.Shader;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.hello_text);

        // get the name from Intent
        String name = getIntent().getStringExtra("name");

        // show Hello message
        if (name != null) {
            textView.setText("Welcome to your dashboard " + name + "!");
        } else {
            textView.setText("Hello User!");
        }
    }
}