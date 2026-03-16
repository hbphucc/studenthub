package Clothes;
/**
 *
 * @author hoang
 */

import Utils.DBUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClothesDAO {

    public List<ClothesDTO> getAllClothes() {
        String sql = "SELECT c.*, cat.categoryName, cat.gender, "
                   + "ISNULL((SELECT SUM(ps.stock) FROM tblProductSizes ps WHERE ps.productID = c.productID), 0) AS stock "
                   + "FROM tblClothes c JOIN tblCategories cat ON c.categoryID = cat.categoryID "
                   + "WHERE c.status = 1 ORDER BY c.createdAt DESC";
        List<ClothesDTO> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClothesDTO p = mapRow(rs);
                p.setStock(rs.getInt("stock"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        populateSizes(list);
        return list;
    }


    public List<ClothesDTO> searchByName(String keyword) {
        String sql = "SELECT c.*, cat.categoryName, cat.gender, "
                   + "ISNULL((SELECT SUM(ps.stock) FROM tblProductSizes ps WHERE ps.productID = c.productID), 0) AS stock "
                   + "FROM tblClothes c JOIN tblCategories cat ON c.categoryID = cat.categoryID "
                   + "WHERE c.status = 1 AND c.productName LIKE ? ORDER BY c.productName";
        List<ClothesDTO> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        populateSizes(list);
        return list;
    }

    public List<ClothesDTO> getByCategory(String categoryID) {
        String sql = "SELECT c.*, cat.categoryName, cat.gender, "
                   + "ISNULL((SELECT SUM(ps.stock) FROM tblProductSizes ps WHERE ps.productID = c.productID), 0) AS stock "
                   + "FROM tblClothes c JOIN tblCategories cat ON c.categoryID = cat.categoryID "
                   + "WHERE c.status = 1 AND c.categoryID = ? ORDER BY c.productName";
        List<ClothesDTO> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        populateSizes(list);
        return list;
    }

    public List<ClothesDTO> getByGender(String gender) {
        String sql = "SELECT c.*, cat.categoryName, cat.gender, "
                   + "ISNULL((SELECT SUM(ps.stock) FROM tblProductSizes ps WHERE ps.productID = c.productID), 0) AS stock "
                   + "FROM tblClothes c JOIN tblCategories cat ON c.categoryID = cat.categoryID "
                   + "WHERE c.status = 1 AND cat.gender = ? ORDER BY c.productName";
        List<ClothesDTO> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gender);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        populateSizes(list);
        return list;
    }

    public ClothesDTO getByID(String productID) {
        String sql = "SELECT c.*, cat.categoryName, cat.gender "
                   + "FROM tblClothes c JOIN tblCategories cat ON c.categoryID = cat.categoryID "
                   + "WHERE c.productID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClothesDTO p = mapRow(rs);
                    p.setAvailableSizes(getSizes(productID));
                    p.setSizeQuantities(getSizeQuantities(productID)); 
                    return p;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<ClothesDTO> getAllForAdmin() {
        List<ClothesDTO> list = query(
            "SELECT c.*, cat.categoryName, cat.gender, "
          + "ISNULL((SELECT SUM(ps.stock) FROM tblProductSizes ps WHERE ps.productID = c.productID), 0) AS stock "
          + "FROM tblClothes c JOIN tblCategories cat ON c.categoryID = cat.categoryID "
          + "ORDER BY c.createdAt DESC");

        Map<String, Map<String, Integer>> allSizes = getAllSizeQuantities();
        for (ClothesDTO p : list) {
            Map<String, Integer> sizes = allSizes.getOrDefault(p.getProductID(), new LinkedHashMap<>());
            p.setSizeQuantities(sizes);
            int total = sizes.values().stream().mapToInt(Integer::intValue).sum();
            if (p.getStock() == 0 && total > 0) p.setStock(total);
        }
        return list;
    }

    private Map<String, Map<String, Integer>> getAllSizeQuantities() {
        Map<String, Map<String, Integer>> result = new LinkedHashMap<>();
        String sql = "SELECT productID, size, stock FROM tblProductSizes "
                   + "ORDER BY productID, "
                   + "CASE size WHEN 'S' THEN 1 WHEN 'M' THEN 2 WHEN 'L' THEN 3 "
                   + "          WHEN 'XL' THEN 4 WHEN 'XXL' THEN 5 ELSE 6 END";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String pid   = rs.getString("productID");
                String size  = rs.getString("size");
                int    stock = rs.getInt("stock");
                result.computeIfAbsent(pid, k -> new LinkedHashMap<>()).put(size, stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addProduct(ClothesDTO p) {
        String productID = java.util.UUID.randomUUID().toString(); 
        p.setProductID(productID);

        String sqlProduct = "INSERT INTO tblClothes (productID, productName, categoryID, price, description, img, status) "
                          + "VALUES (?, ?, ?, ?, ?, ?, 1)";
        String sqlSize = "INSERT INTO tblProductSizes (productID, size, stock) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sqlProduct)) {
                ps.setString(1, p.getProductID());
                ps.setString(2, p.getProductName());
                ps.setString(3, p.getCategoryID());
                ps.setDouble(4, p.getPrice());
                ps.setString(5, p.getDescription());
                ps.setString(6, p.getImg());
                ps.executeUpdate();
            }

            try (PreparedStatement psSize = conn.prepareStatement(sqlSize)) {
                for (Map.Entry<String, Integer> entry : p.getSizeQuantities().entrySet()) {
                    psSize.setString(1, p.getProductID());
                    psSize.setString(2, entry.getKey());
                    psSize.setInt(3, entry.getValue());
                    psSize.addBatch();
                }
                psSize.executeBatch();
            }

            conn.commit(); 
            return true;
        } catch (Exception e) {
            if (conn != null) try{
                conn.rollback();
            } catch (Exception ex) {}
            e.printStackTrace();
        } finally {
            if (conn != null) try{
                conn.setAutoCommit(true);
                conn.close(); 
            } catch (Exception ex) {}
        }
        return false;
    }

    public boolean updateProduct(ClothesDTO p) {
        String sqlProduct    = "UPDATE tblClothes SET productName=?, categoryID=?, price=?, description=?, img=? WHERE productID=?";
        String sqlDeleteSize = "DELETE FROM tblProductSizes WHERE productID=?";
        String sqlInsertSize = "INSERT INTO tblProductSizes (productID, size, stock) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            conn.setAutoCommit(false); 

            try (PreparedStatement ps = conn.prepareStatement(sqlProduct)) {
                ps.setString(1, p.getProductName());
                ps.setString(2, p.getCategoryID());
                ps.setDouble(3, p.getPrice());
                ps.setString(4, p.getDescription());
                ps.setString(5, p.getImg());
                ps.setString(6, p.getProductID());
                ps.executeUpdate();
            }
            try (PreparedStatement psDel = conn.prepareStatement(sqlDeleteSize)) {
                psDel.setString(1, p.getProductID());
                psDel.executeUpdate();
            }

            try (PreparedStatement psSize = conn.prepareStatement(sqlInsertSize)) {
                for (Map.Entry<String, Integer> entry : p.getSizeQuantities().entrySet()) {
                    psSize.setString(1, p.getProductID());
                    psSize.setString(2, entry.getKey());
                    psSize.setInt(3, entry.getValue());
                    psSize.addBatch();
                }
                psSize.executeBatch();
            }

            conn.commit(); 
            return true;
        } catch (Exception e) {
            if (conn != null) try {
                conn.rollback(); 
            }catch(Exception ex) {}
            e.printStackTrace();
        } finally{
            if (conn != null)try{
                conn.setAutoCommit(true);
                conn.close(); }
            catch (Exception ex){}
        }
        return false;
    }

    public boolean deleteProduct(String productID) {
        String sql = "UPDATE tblClothes SET status = 0 WHERE productID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getSizes(String productID) {
        List<String> sizes = new ArrayList<>();
        String sql = "SELECT size FROM tblProductSizes WHERE productID = ? AND stock > 0 "
                   + "ORDER BY CASE size WHEN 'S' THEN 1 WHEN 'M' THEN 2 WHEN 'L' THEN 3 WHEN 'XL' THEN 4 WHEN 'XXL' THEN 5 ELSE 6 END";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) sizes.add(rs.getString("size"));
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return sizes;
    }

    public Map<String, Integer> getSizeQuantities(String productID) {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT size, stock FROM tblProductSizes "
                   + "WHERE productID = ? "
                   + "ORDER BY CASE size WHEN 'S' THEN 1 WHEN 'M' THEN 2 WHEN 'L' THEN 3 WHEN 'XL' THEN 4 WHEN 'XXL' THEN 5 ELSE 6 END";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getString("size"), rs.getInt("stock"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return map;
    }

    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCategories WHERE status = 1 ORDER BY gender, categoryName";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new CategoryDTO(
                    rs.getString("categoryID"), rs.getString("categoryName"),
                    rs.getString("gender"),     rs.getString("description"),
                    rs.getBoolean("status")));
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return list;
    }

    public boolean addCategory(CategoryDTO c) {
        String sql = "INSERT INTO tblCategories (categoryID, categoryName, gender, description) VALUES (NEWID(),?,?,?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getGender());
            ps.setString(3, c.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e){ 
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCategory(CategoryDTO c) {
        String sql = "UPDATE tblCategories SET categoryName=?, gender=?, description=? WHERE categoryID=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getGender());
            ps.setString(3, c.getDescription());
            ps.setString(4, c.getCategoryID());
            return ps.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(String categoryID) {
        String sql = "UPDATE tblCategories SET status = 0 WHERE categoryID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryID);
            return ps.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private List<ClothesDTO> query(String sql) {
        List<ClothesDTO> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private String addStockSubquery(String baseSql) {
        return baseSql.replace(
            "FROM tblClothes c JOIN",
            ", ISNULL((SELECT SUM(ps.stock) FROM tblProductSizes ps WHERE ps.productID = c.productID), 0) AS stock "
            + "FROM tblClothes c JOIN"
        );
    }

    private void populateSizes(List<ClothesDTO> list) {
        if (list.isEmpty()) return;
        Map<String, Map<String, Integer>> allSizes = getAllSizeQuantities();
        for (ClothesDTO p : list) {
            p.setSizeQuantities(allSizes.getOrDefault(p.getProductID(), new LinkedHashMap<>()));
        }
    }


    private ClothesDTO mapRow(ResultSet rs) throws SQLException {
        ClothesDTO c = new ClothesDTO();
        c.setProductID(rs.getString("productID"));
        c.setProductName(rs.getString("productName"));
        c.setCategoryID(rs.getString("categoryID"));
        c.setCategoryName(rs.getString("categoryName"));
        c.setGender(rs.getString("gender"));
        c.setPrice(rs.getDouble("price"));
        c.setDescription(rs.getString("description"));
        c.setImg(rs.getString("img"));
        try { c.setStock(rs.getInt("stock")); } catch (SQLException ex) { c.setStock(0); }
        c.setStatus(rs.getBoolean("status"));
        return c;
    }
}