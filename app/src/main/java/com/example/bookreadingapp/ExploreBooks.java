package com.example.bookreadingapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExploreBooks extends AppCompatActivity {

    //ref - https://youtu.be/vgWihyzAv-U?list=PLs1bCj3TvmWmtQbEzNewkf-UhBJ2pFr5n

    private ArrayList<pdfDetails> pdfDetailsList;
    private pdfAdapter pdfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_books);


        loadPdfList();

        //search bar
//        EditText searchbar = findViewById(R.id.searchBK);
//        searchbar.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //search as and when user type each letter
//                try {
//                    pdfAdapter.
//
//                }catch (Exception e){
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        //back button
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadPdfList() {
        //init list before adding data
        pdfDetailsList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.orderByChild("timestamp")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pdfDetailsList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            //get data
                            pdfDetails model = ds.getValue(pdfDetails.class);
                            //add to list
                            pdfDetailsList.add(model);
                        }
                        //setup adapter
                        pdfAdapter = new pdfAdapter(ExploreBooks.this, pdfDetailsList);
                        //set adapter to recyclerview
                        RecyclerView recyclerView = findViewById(R.id.bookRV);
                        recyclerView.setAdapter(pdfAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}