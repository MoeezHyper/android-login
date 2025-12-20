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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        errorText = findViewById(R.id.errorText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchProducts();
    }

    private void fetchProducts() {

        new Thread(() -> {

            List<Product> products = ApiClient.fetchProducts();

            runOnUiThread(() -> {

                if (products == null) {
                    errorText.setText("Network error. Loading offline data.");
                    errorText.setVisibility(View.VISIBLE);
                }
                else if (products.isEmpty()) {
                    errorText.setText("No products available.");
                    errorText.setVisibility(View.VISIBLE);
                }
                else {
                    errorText.setVisibility(View.GONE);
                    recyclerView.setAdapter(new ProductAdapter(products));
                }
            });
        }).start();
    }

}
