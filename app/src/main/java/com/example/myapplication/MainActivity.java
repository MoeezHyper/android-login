package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private TextView errorText;
    private ProductAdapter adapter;
    private ProductViewModel productViewModel;
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String THEME_KEY = "theme";
    private static final String LOGIN_PREFS_NAME = "LoginPrefs";
    private static final String IS_LOGGED_IN_KEY = "isLoggedIn";
    private static final String RECYCLER_VIEW_STATE = "recycler_view_state";
    private Parcelable recyclerViewState;

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

        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
        }

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.getProducts().observe(this, products -> {
            if (products != null && !products.isEmpty()) {
                errorText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter = new ProductAdapter(products);
                adapter.setOnItemClickListener(this);
                recyclerView.setAdapter(adapter);
                if (recyclerViewState != null) {
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                    recyclerViewState = null;
                }
            } else {
                errorText.setText("No products available.");
                errorText.setTextColor(Color.BLACK);
                errorText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        productViewModel.getError().observe(this, error -> {
            if (error != null) {
                if (error.startsWith("Network error")) {
                    Snackbar snackbar = Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();
                } else {
                    errorText.setText(error);
                    errorText.setTextColor(Color.RED);
                    errorText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recyclerView != null && recyclerView.getLayoutManager() != null) {
            outState.putParcelable(RECYCLER_VIEW_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
        }
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

    @Override
    public void onItemClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}
