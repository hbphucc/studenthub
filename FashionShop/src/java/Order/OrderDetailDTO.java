package Order;

public class OrderDetailDTO {
    private int    detailID;
    private String orderID;
    private String productID;
    private String productName;
    private String size;
    private int    quantity;
    private double price;

    public OrderDetailDTO() {}

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getSubTotal(){
        return price * quantity; 
    }
    public String getFormattedPrice(){
        return String.format("%,.0f ₫", price); 
    }
    public String getFormattedSubTotal(){
        return String.format("%,.0f ₫", getSubTotal()); 
    }
}
