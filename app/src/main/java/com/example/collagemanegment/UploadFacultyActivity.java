package com.example.collagemanegment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collagemanegment.Faculty.AddTeacherActivity;
import com.example.collagemanegment.Faculty.TeacherAdapter;
import com.example.collagemanegment.Faculty.TeacherData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UploadFacultyActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView csDepartment, BEdDepartment, LawDepartment, OtherDepartment;
    private LinearLayout csNoData, BEdNoData, LawNoData, OtherNoData;
    private List<TeacherData> list1, list2, list3, list4;

    private DatabaseReference reference, dbRef;
    private TeacherAdapter adapter;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_faculty);
        pd = new ProgressDialog(this);
        fab = findViewById(R.id.AddTeacher);
        csDepartment = findViewById(R.id.csDepartment);
        BEdDepartment = findViewById(R.id.BEdDepartment);
        LawDepartment = findViewById(R.id.LawDepartment);
        OtherDepartment = findViewById(R.id.OtherDepartment);
        csNoData = findViewById(R.id.csNoData);
        BEdNoData = findViewById(R.id.BedNoData);
        LawNoData = findViewById(R.id.LawNoData);
        OtherNoData = findViewById(R.id.OtherNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");


        csDepartment();
        BEdDepartment();
        LawDepartment();
        OtherDepartment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadFacultyActivity.this, AddTeacherActivity.class);
                startActivity(intent);
            }
        });

    }

    private void csDepartment() {
        pd.setMessage("please wait...");
        pd.show();
        dbRef = reference.child("Computer Application & IT");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()) {
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                } else {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(UploadFacultyActivity.this));
                    adapter = new TeacherAdapter(list1, UploadFacultyActivity.this, "Computer Application & IT");
                    csDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
    }

    private void BEdDepartment() {
        pd.setMessage("please wait...");
        pd.show();
        dbRef = reference.child("BEd");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()) {
                    BEdNoData.setVisibility(View.VISIBLE);
                    BEdDepartment.setVisibility(View.GONE);
                } else {
                    BEdNoData.setVisibility(View.GONE);
                    BEdDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    BEdDepartment.setHasFixedSize(true);
                    BEdDepartment.setLayoutManager(new LinearLayoutManager(UploadFacultyActivity.this));
                    adapter = new TeacherAdapter(list2, UploadFacultyActivity.this, "BEd");
                    BEdDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
    }

    private void LawDepartment() {
        pd.setMessage("please wait...");
        pd.show();
        dbRef = reference.child("Law");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()) {
                    LawNoData.setVisibility(View.VISIBLE);
                    LawDepartment.setVisibility(View.GONE);
                } else {
                    LawNoData.setVisibility(View.GONE);
                    LawDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    LawDepartment.setHasFixedSize(true);
                    LawDepartment.setLayoutManager(new LinearLayoutManager(UploadFacultyActivity.this));
                    adapter = new TeacherAdapter(list3, UploadFacultyActivity.this, "Law");
                    LawDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
    }

    private void OtherDepartment() {
        pd.setMessage("please wait...");
        pd.show();
        dbRef = reference.child("Other Department");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if (!snapshot.exists()) {
                    OtherNoData.setVisibility(View.VISIBLE);
                    OtherDepartment.setVisibility(View.GONE);
                } else {
                    OtherNoData.setVisibility(View.GONE);
                    OtherDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    OtherDepartment.setHasFixedSize(true);
                    OtherDepartment.setLayoutManager(new LinearLayoutManager(UploadFacultyActivity.this));
                    adapter = new TeacherAdapter(list4, UploadFacultyActivity.this, "Other Department");
                    OtherDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
    }
}