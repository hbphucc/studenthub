package control;


import view.Tools;
import model.Employee;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class EmployeeList {

    ArrayList<Employee> employeeList = new ArrayList<>();
    SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");

    public void printMenu() {
        System.out.println("\nHOSPITAL HR PROGRAM");
        System.out.println("1. Add new employee");
        System.out.println("2. Search employee");
        System.out.println("3. Remove employee");
        System.out.println("4. Update employee");
        System.out.println("5. View all list");
        System.out.println("6. View take-on list");
        System.out.println("7. View resign list");
        System.out.println("8. View doctor list");
        System.out.println("9. View nurse list");
        System.out.println("10. View technician list");
        System.out.println("11. Write list to file");
        System.out.println("12. Exit");
    }

    public int searchEmployeeByID(String id) {
        for (int i = 0; i <= employeeList.size() - 1; i++) {
            if (id.equalsIgnoreCase(employeeList.get(i).getID())) {
                return i;
            }
        }
        return -1;
    }

    public void addNewEmployee() throws ParseException {

        System.out.println("-------------------------------------Add New Employee-------------------------------------");
        int pos;
        String ID;
        do {
            do {
                ID = Tools.getID("Enter Employee ID (EMxxxx): ", "Wrong format!", "^EM\\d{4}$");
                pos = searchEmployeeByID(ID);
                if (pos != -1) {
                    System.out.println("This ID is duplicate !!");
                }
            } while (pos != -1);

            String name = Tools.getString("Enter Employee Name: ", "Wrong format!").toUpperCase();
            String phone = Tools.getID("Enter Phone Number: ", "Wrong format!", "^\\d{9,11}$");
            String listRole = loadRoleFromFile("roles.txt");
            String[] listRole1 = listRole.split(",");

            for (int i = 0; i < listRole1.length; i++) {
                System.out.println((i + 1) + "    " + listRole1[i]);
            }
            int choice = Tools.getIntegerInRange("Enter your role: ", "Wrong format!", 1, listRole1.length);
            String role = listRole1[choice - 1];
            System.out.println("You Choose: " + role);
            int salary = Tools.getIntegerInRange("Enter Employee Salary: ", "Must from 100..8000 ($)", 100, 8000);
            int contractTime = Tools.getIntegerInRange("Enter Employee ContractTime: ", "Wrong format!", 1, 30);

            Date bDate = Tools.getADate("Enter Employee BirthDay: ", "dd-MM-yyyy");
            Date hiredDate = Tools.getADate("Enter Employee Hired Day: ", "dd-MM-yyyy");

            employeeList.add(new Employee(ID, name, phone, role, salary, contractTime, bDate, hiredDate));
            employeeList.get(employeeList.size() - 1).toString();
            System.out.println("Add an employee successfully !\n");

        } while (Tools.confirmYesNo("Do you want to continue add?(Y or N): ", "Must input Y or N !", "^[Y|y|n|N]$"));
    }

    public void searchEmployee() {
        String ID = Tools.getID("Enter Employee ID (EMxxxx): ", "Wrong format!", "^EM\\d{4}$");
        int pos = searchEmployeeByID(ID);
        if (employeeList.isEmpty()) {
            System.out.println("List employee empty!");
            return;
        }
        if (pos == -1) {
            System.out.println("This ID does not exist");
            return;
        }
        System.out.printf("%-10s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n", "ID", "Name",
                "Phone", "bDate", "Role", "hiredDate", "Salary", "ContractTime");
        for (Employee em : employeeList) {
            if (ID.equalsIgnoreCase(em.getID())) {
                System.out.printf("%-10s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n", em.getID(), em.getName(), em.getPhone(), Tools.formatDate(em.getbDate()), em.getRole(), Tools.formatDate(em.getHiredDate()), em.getSalary(), em.getContractTime());
            }
        }
    }

    public void removeEmployee() {
        String ID = Tools.getID("Enter Employee ID (EMxxxx): ", "Wrong format!", "^EM\\d{4}$");

        int pos = searchEmployeeByID(ID);
        if (pos == -1) {
            System.out.println("This ID does not exist");
            return;
        }
        Date date = new Date();
        for (Employee em : employeeList) {
            if (ID.equalsIgnoreCase(em.getID())) {
                em.setResignDate(date);
            }
        }
        System.out.println("Remove Employee Successfully !!!");

    }

    public void updateEmployee() throws ParseException {
        String ID = Tools.getID("Enter Employee ID (EMxxxx): ", "Wrong format!", "^EM\\d{4}$");

        int pos = searchEmployeeByID(ID);
        if (pos == -1) {
            System.out.println("This ID does not exist");
            return;
        }

        Scanner sc = new Scanner(System.in);
        boolean cont = false;
        do {
            int choice = Tools.getIntegerInRange("Enter your choice to update [1: Name, 2: Phone, 3: bDate, 4: Role, 5: Contract time, 6: Exit]", "Must from 1-6 !!", 1, 6);
            switch (choice) {
                case 1:
                    System.out.println("Enter Name Update: ");
                    String name = sc.nextLine();
                    if (!name.equals("")) {
                        employeeList.get(pos).setName(name);
                    }
                    break;
                case 2:
                    System.out.println("Enter Phone Update: ");
                    String phone = sc.nextLine();
                    if (!phone.equals("")) {
                        if (phone.matches("^\\d{9,11}$")) {
                            employeeList.get(pos).setPhone(phone);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter BirthDay Update: ");
                    String bDate = sc.nextLine();
                    if (!bDate.equals("")) {
                        try {
                            Date birthday = date.parse(bDate);
                            employeeList.get(pos).setbDate(birthday);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Enter Role Update: ");
                    String role = sc.nextLine();
                    if (!role.equals("")) {
                        if (role.equalsIgnoreCase("Doctor") || role.equalsIgnoreCase("Nurse") && role.equalsIgnoreCase("Technician")) {
                            employeeList.get(pos).setRole(role);
                        }
                    }
                    break;
                case 5:
                    System.out.println("Enter Time Update: ");
                    String contractTime = sc.nextLine();
                    if (!contractTime.equals("")) { // a
                        int contract = Integer.parseInt(contractTime);
                        if (contract >= 1 && contract <= 30) {
                            employeeList.get(pos).setContractTime(contract);
                        }
                    }
                    break;
                case 6:
                    cont = true;
                    break;
            }
        } while (!cont);
        System.out.println("Update Employee ID: " + ID + " Successfully !!");
    }

    public boolean checkRole(String role) {
        boolean check = false;
        for (Employee employee : employeeList) {
            if (employee.getRole().equalsIgnoreCase(role)) {
                check = true;
            }
        }
        return check;
    }
    public boolean checkTakeon() {
        boolean check = false;
        for (Employee employee : employeeList) {
            if (employee.getResignDate() == null) {
                check = true;
            }
        }
        return check;
    }

    public void viewList() {
        if (employeeList.isEmpty()) {
            System.out.println("List employee empty!");
            return;
        }
        System.out.println("----------------------------------EMPLOYEE LIST----------------------------------");
        for (Employee employee : employeeList) {
            System.out.println(employee.toInfo());
        }

    }

    public void viewTakeOn() {
        if (employeeList.isEmpty() || checkTakeon()) {
            System.out.println("List employee empty!");
            return;
        }
        System.out.println("----------------------------------TAKE ON LIST----------------------------------");
        for (Employee employee : employeeList) {
            if (employee.getResignDate() == null) {
                System.out.println(employee.toInfo());
            }
        }
    }

    public void viewResignList() {
        if (employeeList.isEmpty()  || !checkTakeon()) {
            System.out.println("List employee empty!");
            return;
        }
   System.out.println("----------------------------------RESIGN LIST----------------------------------");
        for (Employee employee : employeeList) {
            if (employee.getResignDate() != null) {
                System.out.println(employee.toInfo());
            }
        }
    }

    public void viewDoctorList() {
        if (employeeList.isEmpty() || !checkRole("Doctor")) {
            System.out.println("List employee empty!");
            return;
        }
        System.out.println("----------------------------------DOCTOR LIST----------------------------------");
        for (Employee employee : employeeList) {
            if (employee.getRole().equalsIgnoreCase("Doctor")) {
                System.out.println(employee.toInfo());
            }
        }
    }

    public void viewNurseList() {
        if (employeeList.isEmpty()  || !checkRole("Nurse")) {
            System.out.println("List employee empty!");
            return;
        }
        System.out.println("----------------------------------NURSE LIST----------------------------------");
        for (Employee employee : employeeList) {
            if (employee.getRole().equalsIgnoreCase("Nurse")) {
                System.out.println(employee.toInfo());
            }
        }
    }

    public void viewTechnicianList() {
        if (employeeList.isEmpty()) {
            System.out.println("List employee empty!");
            return;
        }
        System.out.println("----------------------------------TECHNICIAN LIST----------------------------------");
        for (Employee employee : employeeList) {
            if (employee.getRole().equalsIgnoreCase("Technician")) {
                System.out.println(employee.toInfo());
            }
        }
    }

    public boolean saveEmployeeToFile(String fileName) {
        try {
            FileWriter file = new FileWriter(fileName);
            for (int i = 0; i < employeeList.size(); i++) {
                if (i != 0) {
                    file.write("\n");
                }
                file.write(employeeList.get(i).toString());
            }
            System.out.println("Save Employee to file successfully !");
            file.close();
            return true;
        } catch (Exception e) {
            System.out.println("Cannot save to file !");
        }
        return false;
    }

    public void loadEmployeeFromFile(String fileName) {
        try {
            File fileEmployee = new File(fileName);
            if (!fileEmployee.exists()) {
                return;
            }
            FileReader fr = new FileReader(fileEmployee);
            BufferedReader bf = new BufferedReader(fr);
            String details;
            while ((details = bf.readLine()) != null) {
                String[] info = details.split(",");
                String id = info[0].toUpperCase().trim();
                String name = info[1].trim();
                String phone = info[2].trim();
                String role = info[3].trim();
                int salary = Integer.parseInt(info[4].trim());
                int contractTime = Integer.parseInt(info[5].trim());
                Date bDate = date.parse(info[6].trim());
                Date hiredDate = date.parse(info[7].trim());
                Date resignDate;
                if (!info[8].trim().equalsIgnoreCase("null")) {
                    resignDate = date.parse(info[8].trim());
                } else {
                    resignDate = null;
                }
                employeeList.add(new Employee(id, name, phone, role, salary, contractTime, bDate, hiredDate, resignDate));

            }
            System.out.println("Load successfull " + employeeList.size() + " Employeee !!!");
            bf.close();
            fr.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public String loadRoleFromFile(String fileName) {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                return null;
            }
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String details;
            while ((details = bf.readLine()) != null) {
                return details;
            }
            bf.close();
            fr.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

}
