package com.example.g;

import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {
    private String name,collegecode,mobile,password,id,clgname;


    public String getName() {
        return name;
    }

    public String getCollegecode() {
        return collegecode;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getClgname() {
        return clgname;
    }

    public void setSharedData(String a1, String a2, String a3, String a4, String a5,String a6) {
       name=a1;
       collegecode=a2;
       mobile=a3;
       password=a4;
       id=a5;
       clgname=a6;
    }
}

