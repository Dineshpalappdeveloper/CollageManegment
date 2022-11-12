package com.example.collagemanegment.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.collagemanegment.R;
import com.example.collagemanegment.UploadFacultyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.network.UpdateMetadataNetworkRequest;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {
    private Button UpdateTeacherUpdateBtn,UpdateTeacherDeleteBtn;
    private ImageView UpdateTeacherImage;
    private EditText UpdateTeacherName,UpdateTeacherEmail,UpdateTeacherPost;
    private String name,email,image,post,category,uniquKey;
    private final int REQ = 1;

    String downloadUrl = "";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference,dbRef;
    private Bitmap bitmap = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_update_teacher);
        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");
        storageReference = FirebaseStorage.getInstance().getReference();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        post = getIntent().getStringExtra("post");

        UpdateTeacherImage = findViewById(R.id.UpdateTeacherImage);
        UpdateTeacherUpdateBtn = findViewById(R.id.UpdateTeacherUpdateBtn);
        UpdateTeacherDeleteBtn = findViewById(R.id.UpdateTeacherDeleteBtn);
        UpdateTeacherName = findViewById(R.id.UpdateTeacherName);
        UpdateTeacherEmail = findViewById(R.id.UpdateTeacherEmail);
        UpdateTeacherPost = findViewById(R.id.UpdateTeacherPost);

         uniquKey = getIntent().getStringExtra("key");
         category = getIntent().getStringExtra("category");

        UpdateTeacherName.setText(name);
        UpdateTeacherEmail.setText(email);
        UpdateTeacherPost.setText(post);
        try {
            Picasso.get().load(image).placeholder(R.drawable.ic_persion).into(UpdateTeacherImage);
           // Glide.with(context).load(item.getImage()).placeholder(R.drawable.ic_developer).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        UpdateTeacherDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               deleteData();
            }
        });
        UpdateTeacherUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = UpdateTeacherName.getText().toString();
                email  = UpdateTeacherEmail.getText().toString();
                post = UpdateTeacherPost.getText().toString();

               chekValidation();
            }
        });

    }

    private void deleteData() {
        reference.child(category).child(uniquKey).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateTeacherActivity.this, "Teacher Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateTeacherActivity.this, UploadFacultyActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateTeacherActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void chekValidation() {
       if (name.isEmpty()){
          UpdateTeacherName.setError("empty");
          UpdateTeacherName.requestFocus();
       }else if (email.isEmpty()){
           UpdateTeacherEmail.setError("empty");
           UpdateTeacherEmail.requestFocus();
       }else if (post.isEmpty()){
           UpdateTeacherPost.setError("empty");
           UpdateTeacherPost.requestFocus();
       }else if (bitmap==null){
           pd.setMessage("Please wait....");
           pd.show();
           updateData(image);
       }else {
           pd.setMessage("Please wait....");
           pd.show();
           updateImage();
       }

    }

    private void updateImage(){


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Teacher").child(finalimg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UpdateTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {

        HashMap<String, Object> hp = new HashMap();

         hp.put("name",name);
         hp.put("email",email);
         hp.put("post",post);
//        hp.put("name", UpdateTeacherName.getText().toString().trim());
//        hp.put("email",UpdateTeacherEmail.getText().toString().trim());
//        hp.put("post", UpdateTeacherPost.getText().toString().trim());
     hp.put("image",s);


        reference.child(category).child(uniquKey).updateChildren(hp).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                pd.dismiss();
                Log.d("shu", task.getResult().toString());
                Toast.makeText(UpdateTeacherActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateTeacherActivity.this, UploadFacultyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
            UpdateTeacherImage.setImageBitmap(bitmap);
        }
    }
}