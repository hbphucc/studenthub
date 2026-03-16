package Clothes;

public class CategoryDTO {
    private String categoryID;
    private String categoryName;
    private String gender;  
    private String description;
    private boolean status;

    public CategoryDTO() {
    }
    
    public CategoryDTO(String categoryID, String categoryName, String gender,
                       String description, boolean status) {
        this.categoryID   = categoryID;
        this.categoryName = categoryName;
        this.gender       = gender;
        this.description  = description;
        this.status       = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
