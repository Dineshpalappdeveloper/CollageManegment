package com.example.collagemanegment.Syllabus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collagemanegment.R;
import com.example.collagemanegment.UploadEbookActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class UploadSyllabusActivity extends AppCompatActivity {
    private CardView PdfPicer;
    private EditText PdfTitle;
    private TextView PdfName;
    private Button PdfuploadBtn1;
    private String pdfName,title;
    private ProgressDialog pd;
    private final int REQ = 1;
    private Bitmap bitmap;
    private Uri pdfData;
    String fileName = "";
    String downloadUrl = "";
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_upload_syllabus);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);

        fileName = String.valueOf(new Date().getTime());
        getSupportActionBar().setTitle("Upload Syllabus");

        PdfName = findViewById(R.id.PdfName1);
        PdfTitle = findViewById(R.id.PdfTitle1);
        PdfPicer = findViewById(R.id.PdfPicer1);
        PdfuploadBtn1 = findViewById(R.id.UploadPdfBtn1);

        PdfPicer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        PdfuploadBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = PdfTitle.getText().toString();
                if (title.isEmpty()){
                    PdfTitle.setError("Empty");
                    PdfTitle.requestFocus();
                }else if (pdfData == null){
                    Toast.makeText(getApplicationContext(), "Please Select Pdf", Toast.LENGTH_SHORT).show();
                }else {
                    pd.setTitle("Syllabus uploading");
                    pd.setMessage("Please wait....");
                    pd.setCancelable(false);
                    pd.show();
                    uploadPdf();

                }
            }
        });

    }

    private void uploadPdf() {
        PdfName.setText("");
        StorageReference reference = storageReference.child("Syllabus/"+ pdfName+"-"+System.currentTimeMillis()+".Syllabus");
        reference.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void uploadData(String downloadUrl) {
        String uniqueKey = databaseReference.child("Syllabus").push().getKey();

        HashMap data = new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);

        databaseReference.child("Syllabus").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Syllabus Uploaded ", Toast.LENGTH_SHORT).show();
                PdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to upload Syllabus", Toast.LENGTH_SHORT).show();
            }
        });
        PdfName.setText("");
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        // intent.setType("*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),REQ);
    }


    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK ) {

            pdfData = data.getData();
            if (pdfData.toString().startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = UploadSyllabusActivity.this.getContentResolver().query(pdfData,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (pdfData.toString().startsWith("file://")){
                pdfName = new File(pdfData.toString()).getName();
            }

            PdfName.setText(pdfName);
        }
    }

}