package com.example.g;

public class Admin {
    private String name;
    private String adminId;
    private String collegeCode;
    private String password;
    private String mobile;
    private String clgname;

    public Admin() {
        // Required empty constructor for Firebase
    }

    public Admin(String name, String adminId, String collegeCode, String mobile, String password,String clgname) {
        this.name = name;
        this.adminId = adminId;
        this.collegeCode = collegeCode;
        this.mobile = mobile;
        this.password = password;
        this.clgname=clgname;
    }

    public String getName() {
        return name;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public String getpassword() {
        return password;
    }

    public String getClgname() {
        return clgname;
    }

    public void setClgname(String clgname) {
        this.clgname = clgname;
    }

    public String getmobile() {
        return mobile;
    }
}

