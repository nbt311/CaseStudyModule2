package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Example {
    private static Pattern pattern;
    private static Matcher matcher;
    private static final String CUSTOMER_NAME_REGEX = "^[\\p{L}]+(?:[\\s'-][\\p{L}]+)*$";
    private static final String PHONE_NUMBER_REGEX = "^0\\d{9}$|^\\+84\\d{9}$";

    public static boolean checkCustomerName(String customerName){
        pattern = Pattern.compile(CUSTOMER_NAME_REGEX);
        matcher = pattern.matcher(customerName);
        return matcher.matches();
    }
    public static boolean checkPhoneNumber(String phoneNumber){
        pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
