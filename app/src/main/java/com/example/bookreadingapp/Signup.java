package com.example.bookreadingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class Signup extends AppCompatActivity {

    private EditText txtEmail, txtUsername, txtPassword, txtConfirmPassword;
    private Button btnSignup;
    private TextView loginText;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txt_email);
        txtUsername = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_pswd);
        txtConfirmPassword = findViewById(R.id.txt_cpswd);
        btnSignup = findViewById(R.id.btn_reg);
        loginText = findViewById(R.id.login);

        loginText.setOnClickListener(v -> {
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        });

        btnSignup.setOnClickListener(v -> validateSignup());
    }

    private void validateSignup() {
        String username = txtUsername.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            txtUsername.setError("Username is required");
            txtUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Enter a valid email address");
            txtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            txtPassword.setError("Password must be at least 6 characters");
            txtPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            txtConfirmPassword.setError("Confirm your password");
            txtConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            txtConfirmPassword.setError("Passwords do not match");
            txtConfirmPassword.requestFocus();
            return;
        }

        createAccount(email, password);
    }

    private void createAccount(String email, String password) {
        btnSignup.setEnabled(false);
        btnSignup.setText("Creating...");

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    btnSignup.setEnabled(true);
                    btnSignup.setText("Sign Up");

                    if (task.isSuccessful()) {
                        Toast.makeText(Signup.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Signup.this, Login.class);
                        startActivity(intent);
                        finish();


                    } else {
                        showSignupError(task.getException());
                    }
                });
    }

    private void showSignupError(Exception exception) {
        String message = "Signup failed. Please try again.";

        if (exception instanceof FirebaseAuthWeakPasswordException) {
            message = "Password is too weak.";
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            message = "Invalid email address.";
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            message = "This email is already registered.";
        } else if (exception != null && exception.getMessage() != null) {
            message = exception.getMessage();
        }

        Toast.makeText(Signup.this, message, Toast.LENGTH_LONG).show();
    }
}