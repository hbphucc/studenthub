package model;


import view.Tools;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
public class Employee {
    String ID, name, phone, role;
    int salary, contractTime;
    Date bDate, hiredDate, resignDate = null;

    public Employee() {
    }

    public Employee(String ID, String name, String phone, String role, int salary, int contractTime, Date bDate, Date hiredDate) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.salary = salary;
        this.contractTime = contractTime;
        this.bDate = bDate;
        this.hiredDate = hiredDate;
    }
    public Employee(String ID, String name, String phone, String role, int salary, int contractTime, Date bDate, Date hiredDate, Date resignDate) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.salary = salary;
        this.contractTime = contractTime;
        this.bDate = bDate;
        this.hiredDate = hiredDate;
        this.resignDate = resignDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getContractTime() {
        return contractTime;
    }

    public void setContractTime(int contractTime) {
        this.contractTime = contractTime;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Date getResignDate() {
        return resignDate;
    }

    public void setResignDate(Date resignDate) {
        this.resignDate = resignDate;
    }

    @Override
    public String toString() {
        return  ID + "," + name + "," + phone + "," + role + "," + salary + "," + 
                contractTime + "," +  Tools.formatDate(bDate) + "," + 
                Tools.formatDate(hiredDate) + "," + resignDate;
    }
    
    public String toInfo(){
    return  ID + " | " + name + " | " + phone + " | " + role + " | " + salary + 
            " | " + contractTime + " | " +  Tools.formatDate(bDate) + " | " +
            Tools.formatDate(hiredDate) + " | " + resignDate;
    }
}
