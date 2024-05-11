/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author hoang
 */


import java.util.Scanner;

public class Utils {

    private final static Scanner sc = new Scanner(System.in);

    public static String getString(String welcome, String msg) {
        boolean check = true;
        String result = "";
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getStringreg(String welcome, String pattern, String msg, String msgreg) {
        boolean check = true;
        String result = "";
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static int getInt(String welcome) {
        boolean check = true;
        int number = 0;
        do {
            try {
                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                check = false;
            } catch (Exception e) {
                check = true;
                System.out.println("Input wrong type number!!!");
            }
        } while (check);
        return number;
    }

    public static double getDouble(String welcome) {
        boolean check = true;
        double number = 0;
        do {
            try {
                System.out.print(welcome);
                number = Double.parseDouble(sc.nextLine());
                check = false;
            } catch (Exception e) {
                System.out.println("Input wrong type number!!!");
                check = true;
            }
        } while (check);
        return number;
    }

    public static double getDoubleRange(String welcome, int min, int max) {
        boolean check = true;
        double number = 0;
        do {
            try {

                System.out.print(welcome);
                number = Double.parseDouble(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be in: " + min + "->" + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input wrong type number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static int getIntRange(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be in: " + min + "->" + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input wrong type number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static String getStringOrBlank(String welcome, String pattern, String msgError) {
        String tmpString = "";
        while (true) {
            System.out.print(welcome);
            tmpString = sc.nextLine().trim();
            if (tmpString.isEmpty()) {
                break;
            } else {
                if (tmpString.matches(pattern)) {
                    break;
                } else {
                    System.out.println(msgError);
                }
            }
        }
        return tmpString;
    }
}
