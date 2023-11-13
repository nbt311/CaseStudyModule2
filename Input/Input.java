package Input;

import Regex.Example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Input {
    static Scanner input = new Scanner(System.in);

    public static String guestName() {
            while (true){
                try {
                    String guest = input.nextLine();
                    boolean isValid = Example.checkCustomerName(guest);
                    if (isValid){
                        return guest;
                    }else {
                        System.out.println("Tên không hợp lệ, vui lòng nhập lại!");
                    }
                }catch (Exception e){
                    System.out.println("Kiểu dữ liệu bị sai");
                }
            }
    }
    public static String PhoneNumber(){
        while (true){
            try {
                String phoneNumber = input.nextLine();
                boolean isValid = Example.checkPhoneNumber(phoneNumber);
                if (isValid){
                    return phoneNumber;
                }else {
                    System.out.println("Số điện thoại không hợp lệ, vui lòng nhập lại!");
                }
            }catch (Exception e){
                System.out.println("Kiểu dữ liệu bị sai");
            }
        }
    }
    public static String IDCard(){
        while (true){
            try {
                String idcard = input.nextLine();
                boolean isValid = Example.checkIDcard(idcard);
                if (isValid){
                    return idcard;
                }else {
                    System.out.println("Số CMT không hợp lệ, vui lòng nhập lại!");
                }
            }catch (Exception e){
                System.out.println("Kiểu dữ liệu bị sai");
            }
        }
    }

    public static int Number(){
        while (true){
            try {
                return Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Kiểu dữ liệu bị sai, vui lòng nhập lại!");
            }
        }
    }

    public static LocalDateTime getLocalDateTimeFromUser(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateTimeString = input.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (Exception e) {
                System.out.println("Lỗi: Định dạng ngày giờ không chính xác. Vui lòng thử lại.");
            }
        }
    }
}
