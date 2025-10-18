package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText emailInput;
    Button resetButton;
    TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        emailInput = findViewById(R.id.email_input);
        resetButton = findViewById(R.id.reset_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // 🔹 Back to login
        loginRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
        });

        // 🔹 Reset check
        resetButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            if (email.isEmpty()) {
                emailInput.setError("Enter your email");
                return;
            }
            checkEmailInFirebase(email);
        });
    }

    // ✅ Check if email exists in Firebase (query-based)
    private void checkEmailInFirebase(String email) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("email").equalTo(email);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    Toast.makeText(ForgetPasswordActivity.this, "User found. Proceed to reset password.", Toast.LENGTH_SHORT).show();
                } else {
                    emailInput.setError("Email not found");
                    emailInput.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ForgetPasswordActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
