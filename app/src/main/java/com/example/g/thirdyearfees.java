package com.example.g;

public class thirdyearfees {
    private   String tutionfee;
    private  String nongovtfee;

    public thirdyearfees(String tutionfee, String nongovtfee) {
        this.tutionfee = tutionfee;
        this.nongovtfee = nongovtfee;
    }

    public thirdyearfees() {
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
