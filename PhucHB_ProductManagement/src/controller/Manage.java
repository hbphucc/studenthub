/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
/**
 *
 * @author hoang
 */
import constant.Constant;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Product;

public class Manage {

    ArrayList<Product> productList;

    public Manage() {
        productList = new ArrayList<>();
        // Thêm một số sản phẩm mẫu vào danh sách productList
//        productList.add(new Product("P1", "Product 1", 10.0, 100, 1));
//        productList.add(new Product("P2", "Product 2", 15.0, 150, 2));
//        productList.add(new Product("P3", "Product 3", 20.0, 200, 1));
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public ArrayList<Product> findProductsByName(String searchName) {
        ArrayList<Product> foundProducts = new ArrayList<>();
        // Lặp qua danh sách sản phẩm hiện có và kiểm tra tên sản phẩm
        for (Product product : productList) {
            if (product.getProductName().toUpperCase().contains(searchName.toUpperCase())) {
                // Nếu tên sản phẩm chứa chuỗi tìm kiếm, thêm sản phẩm này vào danh sách kết quả
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public void saveDataToFile() throws IOException {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(Constant.FILE_NAME);
            bufferedWriter = new BufferedWriter(fileWriter);

            //ghi tung thong tin cua tung product vao ben trong file
            for (Product product : productList) {
                bufferedWriter.write(product.toString());
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new IOException();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException ex) {
                throw new IOException();
            }

        }
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void loadDataFromFile() throws FileNotFoundException, IOException, ParseException {
        productList.clear();
        FileReader fileReader = new FileReader(Constant.FILE_NAME);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split("[|]");
            String id = parts[0].trim();
            String name = parts[1].trim();
            String priceString = parts[2].trim().replace(',', '.');
            double price =0.0;
            price = Double.parseDouble(priceString);
            int quantity = Integer.parseInt(parts[3].trim());
            int status = parts[4].trim().equalsIgnoreCase("Available")
                    ? Constant.STATUS_AVAILABLE : Constant.STATUS_NOT_AVAILABLE;

            Product product = new Product(id, name, price, quantity, status);
            productList.add(product);
        }
        bufferedReader.close();
        fileReader.close();
    }

    public void sortProduct(ArrayList<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                //sort theo quantity ( descending)
                int result = o2.getQuantity() - o1.getQuantity();
                if (result == 0) {
                    result = Double.compare(o1.getQuantity(), o2.getQuantity());
                }
                return result;
            }
        });
    }

    public Product findProductByName(String name) {
        for (Product product : productList) {
            if (product.getProductName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public Product findProductById(String id) {
        for (Product product : productList) {
            if (product.getProductId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

}
