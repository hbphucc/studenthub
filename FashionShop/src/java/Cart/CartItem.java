package Cart;
/**
 *
 * @author hoang
 */

import java.io.Serializable;
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String productID;
    private String productName;
    private String img;
    private String size;
    private double price;
    private int    quantity;

    public CartItem() {}

    public CartItem(String productID, String productName, String img,
                    String size, double price, int quantity) {
        this.productID   = productID;
        this.productName = productName;
        this.img         = img;
        this.size        = size;
        this.price       = price;
        this.quantity    = quantity;
    }

    public String getProductID() {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getSubTotal(){
        return price * quantity; 
    }

    public String getKey(){ 
        return productID + "_" + size; 
    }
}
