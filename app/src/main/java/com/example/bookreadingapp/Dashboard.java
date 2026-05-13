package com.example.bookreadingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    CardView bionicReading, exploreBK, publishBK, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bionicReading = findViewById(R.id.readingCard);
        exploreBK = findViewById(R.id.exploreCard);
        publishBK = findViewById(R.id.publishBKCard);
        logout = findViewById(R.id.logoutCard);

        bionicReading.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, bionicReading.class);
            startActivity(intent);
        });

        exploreBK.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ExploreBooks.class);
            startActivity(intent);
        });

        publishBK.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, AddBook.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(Dashboard.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}