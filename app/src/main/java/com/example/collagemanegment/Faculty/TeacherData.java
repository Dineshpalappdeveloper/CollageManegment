package com.example.collagemanegment.Faculty;

public class TeacherData {
    private String TeacherName,TeacherEmail,TeacherPost,image,key;

    public TeacherData() {
    }

    public TeacherData(String teacherName, String teacherEmail, String teacherPost, String image, String key) {
        TeacherName = teacherName;
        TeacherEmail = teacherEmail;
        TeacherPost = teacherPost;
        this.image = image;
        this.key = key;
    }


    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherEmail() {
        return TeacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        TeacherEmail = teacherEmail;
    }

    public String getTeacherPost() {
        return TeacherPost;
    }

    public void setTeacherPost(String teacherPost) {
        TeacherPost = teacherPost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
