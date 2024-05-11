/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import constant.Constant;
import java.util.Scanner;

/**
 *
 * @author hoang
 */
public class InputValid {

    // access modifier + (static) +return type + name method
    public static int getInteger(String message, String error, int min, int max) {
        Scanner scanner = new Scanner(System.in);

        //- Yêu cầu người dùng nhập vào một số nguyên
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    System.err.println("KhÔng được để trống");
                } else {
                    int number = Integer.parseInt(input);
                    //check number in range
                    if (number >= min && number <= max) {
                        return number;
                    } else {
                        System.err.println("Bạn phải nhập trong khoảng " + min + "-" + max);
                    }
                }
            } catch (Exception e) {
                System.err.println(error);
            }
        }
    }

    public static double getDouble(String message, String error, double min, double max) {
        Scanner scanner = new Scanner(System.in);

        //- Yêu cầu người dùng nhập vào một số nguyên
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    System.err.println("KhÔng được để trống");
                } else {
                    double number = Double.parseDouble(input);
                    //check number in range
                    if (number >= min && number <= max) {
                        return number;
                    } else {
                        System.err.println("Bạn phải nhập trong khoảng " + min + "-" + max);
                    }
                }
            } catch (Exception e) {
                System.err.println(error);
            }
        }
    }

    public static float getFloat(String message, String error, float min, float max) {
        Scanner scanner = new Scanner(System.in);

        //- Yêu cầu người dùng nhập vào một số nguyên
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    System.err.println("KhÔng được để trống");
                } else {
                    float number = Float.parseFloat(input);
                    //check number in range
                    if (number >= min && number <= max) {
                        return number;
                    } else {
                        System.err.println("Bạn phải nhập trong khoảng " + min + "-" + max);
                    }
                }
            } catch (Exception e) {
                System.err.println(error);
            }
        }
    }

    public static String getString(String message, String error, String regex) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message);
            //nhap vao
            String input = scanner.nextLine().trim();
            //kiem tra xem input co rong hay khong
            //kiem tra xem input co matches regex hay ko
            //neu nhu matches voi regex => return string
            if (input.matches(regex)) {
                return input;
            } else {
                //tell error
                System.out.println(error);
            }
        }
    }

    public static String inputId() {
        String productId = getString("Enter ProductID: ",
                "Invalid ProductID. Please enter a valid number.", Constant.REGEX_ALL_CHARACTER);
        return productId;
    }

    public static String inputName() {
        String productName = getString("Enter ProductName: ",
                "Invalid ProductName. Please enter a valid name.", Constant.REGEX_NAME);
        return productName;
    }

    public static double inputPrice() {
        double unitPrice = getDouble("Enter UnitPrice: ",
                "Invalid UnitPrice. Please enter a valid number between 0 and 10000.", 0, 10000);
        return unitPrice;
    }

    public static int inputQuantity() {
        int quantity = getInteger("Enter Quantity: ",
                "Invalid Quantity. Please enter a valid number between 0 and 1000.", 0, 1000);
        return quantity;
    }

    public static int inputStatus() {
        int status = getInteger("Enter Status (1: Available | 2:Not Available): ",
                "Invalid Status. Please enter 1 or 2.", Constant.STATUS_AVAILABLE,
                Constant.STATUS_NOT_AVAILABLE);
        return status;
    }

}