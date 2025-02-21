package com.example.g;

import androidx.lifecycle.ViewModel;

public class StudentViewModel extends ViewModel {
    private String branch, name, pinno, year, batch,code,clgname,f1tution,f1nongovt,f2tution,f2nongovt,f3tution,f3nongovt;

    public String getBranch() {
        return branch;
    }

    public String getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

    public String getPinno() {
        return pinno;
    }



    public String getYear() {
        return year;
    }

    public String getBatch() {
        return batch;
    }

    public String getF1tution() {
        return f1tution;
    }

    public String getF1nongovt() {
        return f1nongovt;
    }

    public String getF2tution() {
        return f2tution;
    }

    public String getF2nongovt() {
        return f2nongovt;
    }

    public String getF3tution() {
        return f3tution;
    }

    public String getF3nongovt() {
        return f3nongovt;
    }

    public String getClgname() {
        return clgname;
    }

    public void setSharedData(String branch,  String name, String pinno,  String year, String batch, String code,String clgname,String f1tution,String f1nongovt,String f2tution,String f2nongovt,String f3tution,String f3nongovt) {
        this.branch = branch;

        this.name = name;
        this.pinno = pinno;

        this.year = year;
        this.batch = batch;
        this.code=code;
        this.clgname=clgname;
        this.f1tution=f1tution;
        this.f1nongovt=f1nongovt;
        this.f2tution=f2tution;
        this.f2nongovt=f2nongovt;
        this.f3tution=f3tution;
        this.f3nongovt=f3nongovt;
    }
}
