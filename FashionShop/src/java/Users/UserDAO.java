package Users;
/**
 *
 * @author hoang
 */

import Utils.DBUtils;
import Utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

public UserDTO login(String email, String password) {
    String sql = "SELECT * FROM tblUsers WHERE email = ? AND password = ? AND status = 1";
    try (Connection conn = DBUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        ps.setString(2, password);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapRow(rs);
            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
}

public boolean register(UserDTO user) {
    if (findByEmail(user.getEmail()) != null) return false;
    String sql = "INSERT INTO tblUsers (userID, fullName, email, password, phone, address, role, status) "
               + "VALUES (NEWID(), ?, ?, ?, ?, ?, 'customer', 1)";
    try (Connection conn = DBUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, user.getFullName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getPhone());
        ps.setString(5, user.getAddress());
        return ps.executeUpdate() > 0;
    }catch(Exception e){
        e.printStackTrace();
    }
    return false;
}

    public UserDTO findByEmail(String email) {
        String sql = "SELECT * FROM tblUsers WHERE email = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }catch(Exception e){
            e.printStackTrace(); 
        }
        return null;
    }

    public UserDTO findByID(String userID) {
        String sql = "SELECT * FROM tblUsers WHERE userID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblUsers ORDER BY createdAt DESC";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(String userID, boolean status) {
        String sql = "UPDATE tblUsers SET status = ? WHERE userID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status ? 1 : 0);
            ps.setString(2, userID);
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProfile(UserDTO user) {
        String sql = "UPDATE tblUsers SET fullName=?, phone=?, address=? WHERE userID=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getUserID());
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace(); 
        }
        return false;
    }

    private UserDTO mapRow(ResultSet rs) throws SQLException {
        UserDTO u = new UserDTO();
        u.setUserID(rs.getString("userID"));
        u.setFullName(rs.getString("fullName"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setPhone(rs.getString("phone"));
        u.setAddress(rs.getString("address"));
        u.setRole(rs.getString("role"));
        u.setStatus(rs.getBoolean("status"));
        return u;
    }
}
