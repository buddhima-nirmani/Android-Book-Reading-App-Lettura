package com.example.bookreadingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class pdfAdapter extends RecyclerView.Adapter<pdfAdapter.HolderPdfAdapter> {

    private Context context;
    private ArrayList<pdfDetails> pdfList;
    private FirebaseAuth firebaseAuth;

    public pdfAdapter(Context context, ArrayList<pdfDetails> pdfList) {
        this.context = context;
        this.pdfList = pdfList;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public HolderPdfAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdfview, parent, false);
        return new HolderPdfAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdfAdapter holder, int position) {
        pdfDetails pdfDetails = pdfList.get(position);

        String title = pdfDetails.getTitle();
        String author = pdfDetails.getAuthor();
        String url = pdfDetails.getUrl();
        String uploadedBy = pdfDetails.getUid();

        holder.bookTitle.setText(title);
        holder.authorName.setText(author);

        if (pdfDetails.getTimestamp() > 0) {
            String formattedDate = MyApplication.formatTimestamp(pdfDetails.getTimestamp());
            holder.dateTv.setText(formattedDate);
        } else {
            holder.dateTv.setText("");
        }

        holder.itemView.setOnClickListener(v -> openPdfLink(url));

        String currentUserId = firebaseAuth.getUid();

        if (currentUserId != null && currentUserId.equals(uploadedBy)) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
        } else {
            holder.deleteBtn.setVisibility(View.GONE);
        }

        holder.deleteBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Book");
            builder.setMessage("Are you sure you want to delete this book?");
            builder.setPositiveButton("Delete", (dialog, which) ->
                    deleteBook(pdfDetails, holder.getAdapterPosition()));
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    private void openPdfLink(String url) {
        if (url != null && !url.trim().isEmpty()) {
            Intent intent = new Intent(context, PdfViewerActivity.class);
            intent.putExtra("pdfUrl", url);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "PDF link not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBook(pdfDetails pdfDetails, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Books")
                .child(String.valueOf(pdfDetails.getId()));

        databaseReference.removeValue()
                .addOnSuccessListener(aVoid -> {
                    if (position != RecyclerView.NO_POSITION) {
                        pdfList.remove(position);
                        notifyItemRemoved(position);
                    }

                    Toast.makeText(context, "Book deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Failed to delete book", Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public static class HolderPdfAdapter extends RecyclerView.ViewHolder {

        TextView bookTitle, authorName, dateTv;
        ImageButton deleteBtn;

        public HolderPdfAdapter(@NonNull View itemView) {
            super(itemView);

            bookTitle = itemView.findViewById(R.id.bookTitle);
            authorName = itemView.findViewById(R.id.authorName);
            dateTv = itemView.findViewById(R.id.dateTv);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}