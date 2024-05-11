package view;


import control.EmployeeList;
import java.text.ParseException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
public class HospitalManagement {

    public static void main(String[] args) throws ParseException {

        EmployeeList empList = new EmployeeList();
        empList.loadEmployeeFromFile("employees.txt");
        while (true) {
            empList.printMenu();
            int choice = Tools.getIntegerInRange("Choose (1..12):  ", "Must from 1 to 12 !!", 1, 12);
            switch (choice) {
                case 1:
                    empList.addNewEmployee();
                    break;
                case 2:
                    empList.searchEmployee();
                    break;
                case 3:
                    empList.removeEmployee();
                    break;
                case 4:
                    empList.updateEmployee();
                    break;
                case 5:
                    empList.viewList();
                    break;
                case 6:
                    empList.viewTakeOn();
                    break;
                case 7:
                    empList.viewResignList();
                    break;
                case 8:
                    empList.viewDoctorList();
                    break;
                case 9:
                    empList.viewNurseList();
                    break;
                case 10:
                    empList.viewTechnicianList();
                    break;
                case 11:
                    empList.saveEmployeeToFile("employees.txt");
                    break;
                case 12:
                    System.out.println("Good Byeeee!!");
                    return;

            }
        }
    }

}
