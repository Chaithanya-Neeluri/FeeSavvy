package com.example.g;

public class firstyearfees {
  private   String tutionfee;
  private  String nongovtfee;

    public firstyearfees(String tutionfee, String nongovtfee) {
        this.tutionfee = tutionfee;
        this.nongovtfee = nongovtfee;
    }

    public firstyearfees() {
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
