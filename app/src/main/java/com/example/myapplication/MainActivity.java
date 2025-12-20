package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView errorText;
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String THEME_KEY = "theme";
    private static final String LOGIN_PREFS_NAME = "LoginPrefs";
    private static final String IS_LOGGED_IN_KEY = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyTheme();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        recyclerView = findViewById(R.id.recyclerView);
        errorText = findViewById(R.id.errorText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_light_theme) {
            saveTheme("Light");
            recreate();
            return true;
        } else if (item.getItemId() == R.id.action_dark_theme) {
            saveTheme("Dark");
            recreate();
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTheme(String theme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(THEME_KEY, theme);
        editor.apply();
    }

    private String getSavedTheme() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(THEME_KEY, "Light"); // Default to Light
    }

    private void applyTheme() {
        String theme = getSavedTheme();
        if (theme.equals("Light")) {
            setTheme(R.style.Theme_MyApplication_Light);
        } else {
            setTheme(R.style.Theme_MyApplication_Dark);
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(LOGIN_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(IS_LOGGED_IN_KEY, false);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchProducts() {

        new Thread(() -> {

            List<Product> products = ApiClient.fetchProducts();

            runOnUiThread(() -> {

                if (products == null) {
                    errorText.setText("Network error. Loading offline data.");
                } else if (products.isEmpty()) {
                    errorText.setText("No products available.");
                } else {
                    recyclerView.setAdapter(new ProductAdapter(products));
                }
            });
        }).start();
    }

}
