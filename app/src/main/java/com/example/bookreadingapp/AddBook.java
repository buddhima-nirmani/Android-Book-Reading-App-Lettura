package com.example.bookreadingapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddBook extends AppCompatActivity {

    private EditText bookTitle, authorName, pdfUrl;
    private Button publish;
    private FirebaseAuth firebaseAuth;
    private AlertDialog progressDialog;

    private static final String TAG = "ADD_BOOK_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        firebaseAuth = FirebaseAuth.getInstance();

        bookTitle = findViewById(R.id.txt_title);
        authorName = findViewById(R.id.txt_author);
        pdfUrl = findViewById(R.id.txt_pdf_url);
        publish = findViewById(R.id.btn_publish);

        publish.setOnClickListener(v -> validateAndSaveBook());
    }

    private void validateAndSaveBook() {
        String title = bookTitle.getText().toString().trim();
        String author = authorName.getText().toString().trim();
        String url = pdfUrl.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            bookTitle.setError("Title is required");
            bookTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(author)) {
            authorName.setError("Author is required");
            authorName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(url)) {
            pdfUrl.setError("PDF link is required");
            pdfUrl.requestFocus();
            return;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            pdfUrl.setError("Enter a valid link");
            pdfUrl.requestFocus();
            return;
        }

        saveBookToDatabase(title, author, url);
    }

    private void saveBookToDatabase(String title, String author, String url) {
        showProgressDialog("Saving book...");

        long timestamp = System.currentTimeMillis();
        String uid = firebaseAuth.getUid();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + uid);
        hashMap.put("id", "" + timestamp);
        hashMap.put("title", title);
        hashMap.put("author", author);
        hashMap.put("url", url);
        hashMap.put("timestamp", timestamp);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child("" + timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(unused -> {
                    progressDialog.dismiss();
                    Toast.makeText(AddBook.this, "Book published successfully", Toast.LENGTH_SHORT).show();

                    bookTitle.setText("");
                    authorName.setText("");
                    pdfUrl.setText("");
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(AddBook.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void showProgressDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null);
        ProgressBar progressBar = dialogView.findViewById(R.id.progressBar);
        builder.setView(dialogView);
        builder.setMessage(message);
        progressDialog = builder.create();
        progressDialog.show();
    }
}