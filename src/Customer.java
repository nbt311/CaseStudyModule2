package src;

import Print.StudentFormat;

import java.io.Serializable;

public class Customer implements Serializable {
    private  String name;
    private  String phoneNumber;
    private  String IDcard;

    public Customer(String name, String phoneNumber, String IDcard) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.IDcard = IDcard;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public  String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }
    @Override
    public String toString() {
        return StudentFormat.formatStudent(this);
    }
}
