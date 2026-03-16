package Clothes;
/**
 *
 * @author hoang
 */

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClothesDTO {
    private String productID;
    private String productName;
    private String categoryID;
    private String categoryName;  
    private String gender;         
    private double price;
    private String description;
    private String img;
    private int stock;
    private boolean status;
    private List<String> availableSizes;  

    public ClothesDTO() {}

    public String getProductID(){
        return productID; 
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getAvailableSizes() {
        return availableSizes;
    }
    public void setAvailableSizes(List<String> v) {
        this.availableSizes = v; 
    }
    
    private Map<String, Integer> sizeQuantities = new LinkedHashMap<>();


    public Map<String, Integer> getSizeQuantities() { return sizeQuantities; }
    public void setSizeQuantities(Map<String, Integer> sizeQuantities) {
        this.sizeQuantities = sizeQuantities;
    }

    public String getFormattedPrice() {
        return String.format("%,.0f ₫", price);
    }
    
}
