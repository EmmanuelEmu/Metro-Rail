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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getHbd() {
        return hbd;
    }

    public void setHbd(String hbd) {
        this.hbd = hbd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
