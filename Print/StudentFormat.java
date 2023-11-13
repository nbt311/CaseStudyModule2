package Print;

import src.Customer;

public class StudentFormat {
    public static String formatStudent(Customer customer){
        return "Name: " + customer.getName() +
                ", PhoneNumber: " + customer.getPhoneNumber() +
                ", ID Card: " + customer.getIDcard();
    }
}
