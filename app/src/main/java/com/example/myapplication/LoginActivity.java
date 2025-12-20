package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView signupRedirectText, forgotRedirectText;
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String THEME_KEY = "theme";
    private static final String LOGIN_PREFS_NAME = "LoginPrefs";
    private static final String IS_LOGGED_IN_KEY = "isLoggedIn";
    private static final String EMAIL_KEY = "email";
    private static final String NAME_KEY = "name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyTheme();

        // Check if the user is already logged in
        if (isLoggedIn()) {
            launchMainActivity();
            return;
        }

        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        forgotRedirectText = findViewById(R.id.forgotRedirectText);

        loginButton.setOnClickListener(v -> {
            if (!validateEmail() | !validatePassword()) return;
            checkUser();
        });

        signupRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });

        forgotRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
        });
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

    public boolean validateEmail() {
        String val = loginEmail.getText().toString().trim();
        if (val.isEmpty()) {
            loginEmail.setError("Email cannot be empty");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            loginEmail.setError("Enter a valid email address");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String val = loginPassword.getText().toString().trim();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userEmail = loginEmail.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);
                        String nameFromDB = userSnapshot.child("name").getValue(String.class);

                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            saveLoginState(userEmail, nameFromDB);
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            launchMainActivity();
                            return;
                        } else {
                            loginPassword.setError("Invalid password");
                            loginPassword.requestFocus();
                            return;
                        }
                    }
                } else {
                    loginEmail.setError("User does not exist");
                    loginEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLoginState(String email, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(LOGIN_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(IS_LOGGED_IN_KEY, true);
        editor.putString(EMAIL_KEY, email);
        editor.putString(NAME_KEY, name);
        editor.apply();
    }

    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(LOGIN_PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(IS_LOGGED_IN_KEY, false);
    }

    private void launchMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        SharedPreferences prefs = getSharedPreferences(LOGIN_PREFS_NAME, MODE_PRIVATE);
        intent.putExtra(EMAIL_KEY, prefs.getString(EMAIL_KEY, ""));
        intent.putExtra(NAME_KEY, prefs.getString(NAME_KEY, ""));
        startActivity(intent);
        finish();
    }
}
