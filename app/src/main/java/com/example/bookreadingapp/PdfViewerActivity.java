package com.example.bookreadingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfViewerActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView);

        String pdfUrl = getIntent().getStringExtra("pdfUrl");

        if (pdfUrl != null && !pdfUrl.isEmpty()) {

            pdfUrl = convertGoogleDriveLink(pdfUrl);

            new RetrievePdfStream().execute(pdfUrl);

        } else {
            Toast.makeText(this, "Invalid PDF link", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private String convertGoogleDriveLink(String url) {

        if (url.contains("drive.google.com")) {

            String fileId = "";

            String[] split = url.split("/d/");

            if (split.length > 1) {

                String[] secondSplit = split[1].split("/");

                fileId = secondSplit[0];
            }

            return "https://drive.google.com/uc?export=download&id=" + fileId;
        }

        return url;
    }

    private class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream = null;

            try {

                URL url = new URL(strings[0]);

                HttpURLConnection urlConnection =
                        (HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == 200) {

                    inputStream = urlConnection.getInputStream();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {

            if (inputStream != null) {

                pdfView.fromStream(inputStream)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .load();

            } else {

                Toast.makeText(PdfViewerActivity.this,
                        "Failed to load PDF",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}