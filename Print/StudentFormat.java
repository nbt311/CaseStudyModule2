package Print;

import src.Guest;

public class StudentFormat {
    public static String formatStudent(Guest customer){
        return "Name: " + customer.getName() +
                ", PhoneNumber: " + customer.getPhoneNumber() +
                ", ID Card: " + customer.getIDcard();
    }
}
