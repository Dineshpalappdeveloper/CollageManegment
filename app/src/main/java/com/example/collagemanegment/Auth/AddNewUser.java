package com.example.collagemanegment.Auth;

public class AddNewUser {
    String Name,PhoneNo,Password;

    public AddNewUser() {
    }

    public AddNewUser(String name, String phoneNo, String password) {
        Name = name;
        PhoneNo = phoneNo;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
