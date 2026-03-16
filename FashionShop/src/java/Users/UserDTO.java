package Users;
/**
 *
 * @author hoang
 */

import java.time.LocalDateTime;

public class UserDTO {
    private String userID;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role; 
    private boolean status;
    private LocalDateTime createdAt;

    public UserDTO() {}

    public UserDTO(String userID, String fullName, String email, String password, String phone, String address, String role, boolean status, LocalDateTime createdAt) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdmin(){
        return "admin".equalsIgnoreCase(role); 
    }
    
    public boolean isCustomer() {
        return "customer".equalsIgnoreCase(role); 
    }
}
