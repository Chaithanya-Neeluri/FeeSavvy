package com.example.g;

public class viewstudent{
    private String branch;
    private String clgname;
    private String feedback;
    private String name;
    private String pinno;
    private String year;
    private String subject;
    private FirstYearFees firstyearfees;
    private SecondYearFees secondyearfees;
    private ThirdYearFees thirdyearfees;
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    // Default constructor (required for Firebase)
    public viewstudent() {
    }

    public viewstudent(String branch, String clgname, String feedback, String name, String pinno,
                       String year, FirstYearFees firstyearfees, SecondYearFees secondyearfees,
                       ThirdYearFees thirdyearfees,boolean isDeleted ,String subject) {
        this.branch = branch;
        this.clgname = clgname;
        this.feedback = feedback;
        this.name = name;
        this.pinno = pinno;
        this.year = year;
        this.firstyearfees = firstyearfees;
        this.secondyearfees = secondyearfees;
        this.thirdyearfees = thirdyearfees;
        this.isDeleted=isDeleted;
        this.subject=subject;

    }

    // Getters and setters for all fields

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getClgname() {
        return clgname;
    }

    public void setClgname(String clgname) {
        this.clgname = clgname;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinno() {
        return pinno;
    }

    public void setPinno(String pinno) {
        this.pinno = pinno;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public FirstYearFees getFirstyearfees() {
        return firstyearfees;
    }

    public void setFirstyearfees(FirstYearFees firstyearfees) {
        this.firstyearfees = firstyearfees;
    }

    public SecondYearFees getSecondyearfees() {
        return secondyearfees;
    }

    public void setSecondyearfees(SecondYearFees secondyearfees) {
        this.secondyearfees = secondyearfees;
    }

    public ThirdYearFees getThirdyearfees() {
        return thirdyearfees;
    }

    public void setThirdyearfees(ThirdYearFees thirdyearfees) {
        this.thirdyearfees = thirdyearfees;
    }

    // Nested classes for fees
    public static class FirstYearFees {
        private String nongovtfee;
        private String tutionfee;

        public FirstYearFees(String nongovtfee, String tutionfee) {
            this.nongovtfee = nongovtfee;
            this.tutionfee = tutionfee;
        }

        public String getNongovtfee() {
            return nongovtfee;
        }

        public void setNongovtfee(String nongovtfee) {
            this.nongovtfee = nongovtfee;
        }

        public String getTutionfee() {
            return tutionfee;
        }

        public void setTutionfee(String tutionfee) {
            this.tutionfee = tutionfee;
        }

        public FirstYearFees() {
        }
// Constructors, getters, and setters for FirstYearFees
    }

    public static class SecondYearFees {
        private String nongovtfee;
        private String tutionfee;

        public SecondYearFees() {
        }

        public SecondYearFees(String nongovtfee, String tutionfee) {
            this.nongovtfee = nongovtfee;
            this.tutionfee = tutionfee;
        }

        public String getNongovtfee() {
            return nongovtfee;
        }

        public void setNongovtfee(String nongovtfee) {
            this.nongovtfee = nongovtfee;
        }

        public String getTutionfee() {
            return tutionfee;
        }

        public void setTutionfee(String tutionfee) {
            this.tutionfee = tutionfee;
        }
// Constructors, getters, and setters for SecondYearFees
    }

    public static class ThirdYearFees {
        private String nongovtfee;
        private String tutionfee;

        public String getNongovtfee() {
            return nongovtfee;
        }

        public void setNongovtfee(String nongovtfee) {
            this.nongovtfee = nongovtfee;
        }

        public ThirdYearFees(String nongovtfee, String tutionfee) {
            this.nongovtfee = nongovtfee;
            this.tutionfee = tutionfee;
        }

        public ThirdYearFees() {
        }

        public String getTutionfee() {
            return tutionfee;
        }

        public void setTutionfee(String tutionfee) {
            this.tutionfee = tutionfee;
        }
        // Constructors, getters, and setters for ThirdYearFees
    }
}

