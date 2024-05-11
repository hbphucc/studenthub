/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
/**
 *
 * @author hoang
 */
public class Product {

    private String id;
    private String name;
    private double unitPrice;
    private int quantity;
    private int status;

    public Product(String id, String name, double unitPrice, int quantity, int status) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
    }

    public String getProductId() {
        return id;
    }

    public String getProductName() {
        return name;
    }

    public void setProductName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusString() {
        String result = null;
        switch (status) {
            case constant.Constant.STATUS_AVAILABLE:
                result = "Available";
                break;

            case constant.Constant.STATUS_NOT_AVAILABLE:
                result = "Not Available";
                break;
            default:
                throw new AssertionError();
        }
        return result;
    }

    @Override
    public String toString() {
        String s = String.format("%-15s | %-15s | %-15.2f | %-15d | %-15s",
                id, name, unitPrice, quantity, getStatusString());
        return s;
    }
}

