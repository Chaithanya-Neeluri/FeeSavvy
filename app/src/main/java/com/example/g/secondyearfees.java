package com.example.g;

public class secondyearfees {
    private   String tutionfee;
    private  String nongovtfee;

    public secondyearfees(String tutionfee, String nongovtfee) {
        this.tutionfee = tutionfee;
        this.nongovtfee = nongovtfee;
    }

    public secondyearfees() {
    }

    public String getTutionfee() {
        return tutionfee;
    }

    public void setTutionfee(String tutionfee) {
        this.tutionfee = tutionfee;
    }

    public String getNongovtfee() {
        return nongovtfee;
    }

    public void setNongovtfee(String nongovtfee) {
        this.nongovtfee = nongovtfee;
    }
}
