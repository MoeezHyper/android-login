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

public class ResetPasswordActivity extends AppCompatActivity {

    EditText resetPassword, reResetPassword;
    Button resetButton;
    TextView signupRedirectText;

    String email; // Email passed from ForgetPasswordActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        // 🔹 Initialize UI
        resetPassword = findViewById(R.id.reset_password);
        reResetPassword = findViewById(R.id.rereset_password);
        resetButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        // 🔹 Get email from Intent
        email = getIntent().getStringExtra("email");

        // 🔹 Go back to login
        signupRedirectText.setOnClickListener(v ->
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class))
        );

        // 🔹 Handle Reset
        resetButton.setOnClickListener(v -> {
            String newPass = resetPassword.getText().toString().trim();
            String rePass = reResetPassword.getText().toString().trim();

            if (newPass.isEmpty()) {
                resetPassword.setError("Enter new password");
                return;
            }

            if (rePass.isEmpty()) {
                reResetPassword.setError("Retype password");
                return;
            }

            if (!newPass.equals(rePass)) {
                reResetPassword.setError("Passwords do not match");
                reResetPassword.requestFocus();
                return;
            }

            // ✅ If same → update Firebase
            updatePasswordInFirebase(email, newPass);
        });
    }

    // ✅ Update password in Firebase Realtime Database
    private void updatePasswordInFirebase(String email, String newPass) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("email").equalTo(email);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        userSnapshot.getRef().child("password").setValue(newPass);
                    }

                    Toast.makeText(ResetPasswordActivity.this, "Password reset successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "User not found in database.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResetPasswordActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
