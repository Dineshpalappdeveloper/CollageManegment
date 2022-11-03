package com.example.collagemanegment.Faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagemanegment.R;
import com.example.collagemanegment.UploadFacultyActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {

    private List<TeacherData> list;
    private Context context;
    private String category;
    public TeacherAdapter(List<TeacherData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;

    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout, parent, false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {
        TeacherData item = list.get(position);
        holder.name.setText(item.getTeacherName());
        holder.email.setText(item.getTeacherEmail());
        holder.post.setText(item.getTeacherPost());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               Intent intent = new Intent(context,UpdateTeacherActivity.class);
               intent.putExtra("name",item.getTeacherName());
               intent.putExtra("email",item.getTeacherEmail());
               intent.putExtra("post",item.getTeacherPost());
               intent.putExtra("image",item.getImage());
               intent.putExtra("key",item.getKey());
               intent.putExtra("category",category);
               context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {
        private TextView name, email, post;
        private ImageView imageView;
        private Button update;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.facultyItemName);
            email = itemView.findViewById(R.id.facultyItemEmail);
            post = itemView.findViewById(R.id.facultyItemPost);
            imageView = itemView.findViewById(R.id.facultyItemImage);
            update = itemView.findViewById(R.id.facultyInfoUpdate);
        }
    }
}
