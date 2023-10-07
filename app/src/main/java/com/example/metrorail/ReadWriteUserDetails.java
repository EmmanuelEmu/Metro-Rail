package com.example.metrorail;

public class ReadWriteUserDetails {

    String fullName, firstName, lastName, nid, hbd, email, phone;
    public ReadWriteUserDetails(String fName, String lName, String nationalID, String dob, String email, String phone ){
        this.firstName = fName;
        this.lastName = lName;
        this.fullName = firstName+" "+lastName;
        this.nid = nationalID;
        this.hbd = dob;
        this.email = email;
        this.phone = phone;
    }
}
