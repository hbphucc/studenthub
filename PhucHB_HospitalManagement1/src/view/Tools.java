package view;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
public class Tools {
    public static Scanner sc = new Scanner(System.in);

    public static String getID(String inputMsg, String errorMsg, String format) {
        String id;
        boolean match;
        while (true) {
            System.out.println(inputMsg);
            id = sc.nextLine().trim().toUpperCase();
            match = id.matches(format);
            if (id.length() == 0 || id.isEmpty() || !match) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }

    public static String getRole(String inputMsg, String errorMsg) {
        String id;
        while (true) {
            System.out.println(inputMsg);
            id = sc.nextLine().trim();

            if (!id.equalsIgnoreCase("Doctor") && !id.equalsIgnoreCase("Nurse") && !id.equalsIgnoreCase("Technician")) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }

    public static double getADouble(String inputMsg, String errorMsg, double min) {
        double n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Double.parseDouble(sc.nextLine());
                if (n < min) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static int getIntegerInRange(String inputMsg, String errorMsg, int min, int max) {
        int n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < min || n > max) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static int getIntegerWithMin(String inputMsg, String errorMsg, int min) {
        int n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < min) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static String getString(String inputMsg, String errorMsg) {
        String id;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine().trim();
            if (id.length() == 0 || id.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }

   public static boolean confirmYesNo(String inputMsg, String errorMsg, String format) {
        String confirm;
        boolean match;
        while (true) {
            System.out.println(inputMsg);
            confirm = sc.nextLine().trim().toUpperCase();
            match = confirm.matches(format);
            if (confirm.length() == 0 || confirm.isEmpty() || !match) {
                System.out.println(errorMsg);
            } else {
                return confirm.equalsIgnoreCase("y");
            }
        }
    }
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        return dateString;
    }

    public static String getDate(String inputMsg, String errorMsg) {
        String data;
        boolean x;
        while (true) {
            System.out.print(inputMsg);
            data = sc.nextLine().trim();
            try {
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                date.setLenient(false);
                date.parse(data);
                return data;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static Date getADate(String inputMsg, String format) {
        Date date = null;
        boolean check = true;
        do {
            try {
                String dateFormat = Tools.getString(inputMsg,"Invalid");
                DateFormat formatter = new SimpleDateFormat(format);
                formatter.setLenient(false);
                date = (Date) formatter.parse(dateFormat);
                check = false;
            } catch (Exception e) {
                System.out.println("Invalid date! Please try again (format date: " + format + ")");
            }
        } while (check);
        return date;
    }

   
    
}
