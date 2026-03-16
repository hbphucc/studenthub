package Order;
/**
 *
 * @author hoang
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderDTO {
    private String orderID;
    private String userID;
    private String userFullName;   
    private double totalAmount;
    private String fullName;      
    private String phone;
    private String address;
    private String note;
    private String paymentMethod;  
    private String status;       
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderDetailDTO> details;

    public OrderDTO() {}

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public List<OrderDetailDTO> getDetails() {
        return details; 
    }
    public void   setDetails(List<OrderDetailDTO> v){
        this.details = v; 
    }

    public String getFormattedTotal() {
        return String.format("%,.0f ₫", totalAmount);
    }

    public String getStatusLabel() {
        switch (status == null ? "" : status) {
            case "pending":    return "Chờ xử lý";
            case "processing": return "Đang xử lý";
            case "shipped":    return "Đang giao";
            case "delivered":  return "Đã giao";
            case "cancelled":  return "Đã huỷ";
            default:           return status;
        }
    }

    public String getFormattedDate() {
        if (createdAt == null) return "—";
        return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
