package com.example.g;

public class Student{
    public String name;
    public String pinno;
    public String branch;
    public String year;
    public String firstyearfees;
    public String secondyearfees;
    public String thirdyearfees;
    public String feedback;
    public String subject,clgname;

    public void setName(String name) {
        this.name = name;
    }

    public void setPinno(String pinno) {
        this.pinno = pinno;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setFirstyearfees(String firstyearfees) {
        this.firstyearfees = firstyearfees;
    }

    public void setSecondyearfees(String secondyearfees) {
        this.secondyearfees = secondyearfees;
    }

    public void setThirdyearfees(String thirdyearfees) {
        this.thirdyearfees = thirdyearfees;
    }

    public Student() {
        // Required empty constructor for Firebase
    }


    public Student(String name, String pinno, String branch, String year, String firstyearfees, String secondyearfees, String thirdyearfees,String subject,String feedback,String clgname) {
        this.name = name;
        this.pinno = pinno;
        this.branch = branch;
        this.year = year;
        this.firstyearfees=firstyearfees;
        this.secondyearfees=secondyearfees;
        this.thirdyearfees=thirdyearfees;
        this.subject=subject;
        this.feedback=feedback;
        this.clgname=clgname;

    }

    public String getClgname() {
        return clgname;
    }

    public void setClgname(String clgname) {
        this.clgname = clgname;
    }

    public String getName() {
        return name;
    }

    public String getFirstyearfees() {
        return firstyearfees;
    }

    public String getSecondyearfees() {
        return secondyearfees;
    }

    public String getThirdyearfees() {
        return thirdyearfees;
    }

    public String getPinno() {
        return pinno;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }
}

