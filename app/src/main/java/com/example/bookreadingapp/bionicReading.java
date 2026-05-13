package com.example.bookreadingapp;


import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//ref - https://github.com/Cveinnt/bionify/tree/main & chatGPT

public class bionicReading extends AppCompatActivity {
    private static final int MAX_WORD_COUNT = 20;
    private EditText editText;
    private TextView wordCountText;
    private TextView bionicTextView;
    private Button convertButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bionic_reading);

        editText = findViewById(R.id.inputText);
        wordCountText = findViewById(R.id.wordCountText);
        bionicTextView = findViewById(R.id.bionicTextView);
        convertButton = findViewById(R.id.convertButton);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int wordCount = countWords(charSequence.toString());
                wordCountText.setText("Words: " + wordCount + "/" + MAX_WORD_COUNT);
                if (wordCount > MAX_WORD_COUNT) {
                    editText.setError("Maximum word count exceeded!");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                int wordCount = countWords(text);
                if (wordCount > MAX_WORD_COUNT) {
                    Toast.makeText(bionicReading.this, "Word limit exceeded!", Toast.LENGTH_SHORT).show();
                } else {
                    SpannableString bionicText = convertToBionicText(text);
                    bionicTextView.setText(bionicText);
                }
            }
        });
    }

    private int countWords(String text) {
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        return trimmed.split("\\s+").length;
    }

    private SpannableString convertToBionicText(String text) {
        String[] words = text.split("\\s+");
        SpannableString spannableString = new SpannableString(text);
        int start = 0;

        for (String word : words) {
            int length = word.length();
            int boldLength = Math.max(1, (int) Math.ceil(length * 0.4)); // Bold 40% of the word

            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, start + boldLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start += word.length() + 1; // Move to the next word (+1 for the space)
        }

        return spannableString;
    }
}

