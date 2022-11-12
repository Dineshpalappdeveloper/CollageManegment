package com.example.collagemanegment.Faculty;

import static android.provider.MediaStore.Images.Media.insertImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.collagemanegment.MainActivity;
import com.example.collagemanegment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddTeacherActivity extends AppCompatActivity {
    private Button addTecherBtn;
    private CardView b1;
    private EditText name, email, post;
    private Spinner teacherCategory;
    private ImageView image ;
    private final int REQ = 1;
    private String Category,TeacherName,TeacherEmail,TeacherPost="";
    private Bitmap bitmap= null;
    String downloadUrl = "";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference,dbRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_teacher);
        addTecherBtn = findViewById(R.id.addTecherBtn);
        name = findViewById(R.id.teacherName);
        email = findViewById(R.id.teacherEmail);
        image = findViewById(R.id.teacher_image);
        post = findViewById(R.id.teacherPost);
       pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");
        storageReference = FirebaseStorage.getInstance().getReference();

        teacherCategory = findViewById(R.id.addTeacher_category);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        addTecherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeacherName = name.getText().toString();
                TeacherEmail = email.getText().toString();
                TeacherPost = post.getText().toString();



                if (TeacherName.isEmpty()) {
                    name.setError("Empty");
                    name.requestFocus();
                } else if (TeacherEmail.isEmpty()) {
                    email.setError("Empty");
                    email.requestFocus();
                } else if (TeacherPost.isEmpty()) {
                    post.setError("Empty");
                    post.requestFocus();
                }else if (Category.equals("Select Category")){
                    Toast.makeText(AddTeacherActivity.this, "Please select Teacher Category", Toast.LENGTH_SHORT).show();
                }else if (bitmap == null) {
                    pd.setTitle("Uploading Teacher");
                    pd.setMessage("Please Wait....");
                    pd.show();
                    insertData();
                    Toast.makeText(AddTeacherActivity.this, " Image not set", Toast.LENGTH_SHORT).show();
                } else {
                    pd.setTitle("Uploading Teacher");
                    pd.setMessage("Please Wait....");
                    pd.show();

                    insertImage();
                }
            }
        });
        String[] items = new String[]{"Select Category","Computer Application & IT","Law","BEd","Other Department"};
        teacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,items));
        teacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category = teacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void insertImage() {


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] finalimg = baos.toByteArray();
            final StorageReference filePath;
            filePath = storageReference.child("Teacher").child(finalimg + "jpg");
            final UploadTask uploadTask = filePath.putBytes(finalimg);
            uploadTask.addOnCompleteListener(AddTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        downloadUrl = String.valueOf(uri);
                                        insertData();
                                    }
                                });
                            }
                        });
                    } else {
                        pd.dismiss();
                        Toast.makeText(AddTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    private void insertData() {
        dbRef = reference.child(Category);
        final String uniquekey = dbRef.push().getKey();

        TeacherData teacherData = new TeacherData(TeacherName, TeacherEmail,TeacherPost, downloadUrl, uniquekey);

        dbRef.child(uniquekey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddTeacherActivity.this, "Teacher Added", Toast.LENGTH_SHORT).show();
                Intent intent12 = new Intent(AddTeacherActivity.this, MainActivity.class);
                startActivity(intent12);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(bitmap);
        }
    }
}