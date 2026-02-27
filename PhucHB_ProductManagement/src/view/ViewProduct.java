/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author hoang
 */

import constant.Constant;
import controller.Manage;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

class ViewProduct {

    Manage manage = new Manage();

    void createProduct() {
        String id = InputValid.inputId();
        String name = InputValid.inputName();
        double price = InputValid.inputPrice();
        int quantity = InputValid.inputQuantity();
        int status = InputValid.inputStatus();

        Product product = new Product(id, name, price, quantity, status);
        manage.addProduct(product);
        System.out.println("Add successfull");
    }

    void search() {
        String searchName = InputValid.inputName();
        ArrayList<Product> foundProducts = manage.findProductsByName(searchName);

        if (foundProducts.isEmpty()) {
            System.err.println("NOT FOUND");
        } else {
            displayListProduct(foundProducts);
        }
    }

    private void displayListProduct(ArrayList<Product> list) {
        if (list.isEmpty()) {
            System.err.println("NOTHING TO DISPLAY");
            return;
        }
        System.out.format("%-15s | %-15s | %-15s | %-15s | %-15s\n",
                "Id", "Name", "Price", "Quantity", "Status");
        for (Product product : list) {
            System.out.println(product);
        }
    }

    void saveData() {
        try {
            manage.saveDataToFile();
        } catch (IOException ex) {
            System.err.println("Save data from file error: " + ex.getMessage());
        }
    }

    void loadDataFromFile() {
        try {
            manage.loadDataFromFile();
        } catch (Exception ex) {
            System.err.println("Load data from file error: " + ex.getMessage());
        }
    }

    void printListFromFile() {
        loadDataFromFile();
        ArrayList<Product> list = new ArrayList<>();
        list.addAll(manage.getProductList());
        manage.sortProduct(list);

        displayListProduct(list);
    }

    void checkExistProduct() {
        String id = InputValid.inputId();
        loadDataFromFile();
        Product product = manage.findProductById(id);
        if (product == null) {
            System.err.println("No Product Found!");
        } else {
            System.out.println("Exist Product");
        }
    }

    void deleteProduct() {
        loadDataFromFile();
        String id = InputValid.inputId();
        Product product = manage.findProductById(id);
        if (product == null) {
            System.err.println("Product not exist !!");
            System.err.println("Delete failed");
        }
        //delete
        manage.removeProduct(product);
        //save data to file
        saveData();
        System.out.println("Delete successful !!");
    }

    void updateProduct() {
        loadDataFromFile();
        String id = InputValid.inputId();

        Product product = manage.findProductById(id);
        if (product == null) {
            System.err.println("Not found");
        } else {
            String newName;
            double price;
            int quantity, status;
            while (true) {
                newName = InputValid.getString("Enter name: ", "Must be string",
                        Constant.REGEX_ALL_CHARACTER);
                if (!newName.isEmpty()) {
                    if (newName.matches(Constant.REGEX_NAME)) {
                        product.setProductName(newName);
                        break;
                    } else {
                        System.err.println("Invalid ProductName. Please enter a valid name.");
                    }
                } else {
                    break;
                }
            }
            while (true) {
                String newPrice = InputValid.getString("Enter price: ", "Error",
                        Constant.REGEX_ALL_CHARACTER);
                if (!newPrice.isEmpty()) {
                    if (newPrice.matches(Constant.REGEX_DOUBLE_NUMBER)) {
                        price = Double.parseDouble(newPrice);
                        if (price >= 0) {
                            product.setUnitPrice(price);
                            break;
                        } else {
                            System.err.println("Must be greater equal than 0");
                        }
                    } else {
                        System.err.println("It not's a number");
                    }
                } else {
                    break;
                }

            }
            while (true) {
                String quantityNew = InputValid.getString("Enter new quantity: ",
                        "Error", Constant.REGEX_ALL_CHARACTER);
                if (!quantityNew.isEmpty()) {
                    if (quantityNew.matches(Constant.REGEX_INTEGER_NUMBER)) {
                        quantity = Integer.parseInt(quantityNew);
                        if (quantity > 0) {
                            product.setQuantity(quantity);
                            break;
                        } else {
                            System.err.println("Must be greater than 0");
                        }
                    } else {
                        System.err.println("Must be number");
                    }

                } else {
                    break;
                }
            }

            while (true) {
                String statusNew = InputValid.getString("Enter new status: ",
                        "Error", Constant.REGEX_ALL_CHARACTER);
                if (!statusNew.isEmpty()) {
                    if (statusNew.matches(Constant.REGEX_STATUS)) {
                        status = Integer.parseInt(statusNew);
                        product.setStatus(status);
                        break;
                    } else {
                        System.err.println("Must be 1 or 2");
                    }
                } else {
                    break;
                }
            }

            System.out.println("Product information updated successfully.");

            saveData();
        }
    }


}

