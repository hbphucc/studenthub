package Controller;
/**
 *
 * @author hoang
 */

import Clothes.ClothesDAO;
import Clothes.ClothesDTO;
import Clothes.CategoryDTO;
import Order.OrderDAO;
import Users.UserDAO;



import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.annotation.WebServlet;

public class AdminController extends HttpServlet {

    private ClothesDAO clothesDAO = new ClothesDAO();
    private OrderDAO   orderDAO   = new OrderDAO();
    private UserDAO    userDAO    = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException { handle(req, res); }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException { handle(req, res); }

    private void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = nullSafe(req.getParameter("action"), "Dashboard");
        switch (action) {
            case "Dashboard":
                dashboard(req, res);
                break;

            case "ProductList":
                productList(req, res);
                break;
            case "AddProductForm":
                addProductForm(req, res);
                break;
            case "AddProduct":
                addProduct(req, res);
                break;
            case "EditProductForm":
                editProductForm(req, res);
                break;
            case "UpdateProduct":
                updateProduct(req, res);
                break;
            case "DeleteProduct":
                deleteProduct(req, res);
                break;

            case "GetSizeStock":
                getSizeStock(req, res);
                break;

            case "CategoryList":
                categoryList(req, res);
                break;
            case "AddCategory":
                addCategory(req, res);
                break;
            case "EditCategory":
                editCategory(req, res);
                break;
            case "DeleteCategory":
                deleteCategory(req, res);
                break;

            case "OrderList":
                orderList(req, res);
                break;
            case "OrderDetail":
                orderDetail(req, res);
                break;
            case "UpdateOrderStatus":
                updateOrderStatus(req, res);
                break;

            case "UserList":
                userList(req, res);
                break;
            case "ToggleUser":
                toggleUser(req, res);
                break;

            default: dashboard(req, res);
        }
    }

    private void dashboard(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("TOTAL_PRODUCTS", clothesDAO.getAllForAdmin().size());
        req.setAttribute("TOTAL_ORDERS",   orderDAO.getAllOrders().size());
        req.setAttribute("TOTAL_USERS",    userDAO.getAllUsers().size());
        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, res);
    }

    private void productList(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("PRODUCT_LIST",   clothesDAO.getAllForAdmin());
        req.setAttribute("CATEGORY_LIST",  clothesDAO.getAllCategories());
        req.getRequestDispatcher("/admin/productList.jsp").forward(req, res);
    }

    private void addProductForm(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("CATEGORY_LIST", clothesDAO.getAllCategories());
        req.getRequestDispatcher("/admin/productForm.jsp").forward(req, res);
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ClothesDTO p = buildProduct(req);
        clothesDAO.addProduct(p);
        res.sendRedirect("AdminController?action=ProductList&msg=added");
    }

    private void editProductForm(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("PRODUCT",       clothesDAO.getByID(req.getParameter("id")));
        req.setAttribute("CATEGORY_LIST", clothesDAO.getAllCategories());
        req.getRequestDispatcher("/admin/productForm.jsp").forward(req, res);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ClothesDTO p = buildProduct(req);
        p.setProductID(req.getParameter("productID"));
        clothesDAO.updateProduct(p);
        res.sendRedirect("AdminController?action=ProductList&msg=updated");
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        clothesDAO.deleteProduct(req.getParameter("id"));
        res.sendRedirect("AdminController?action=ProductList&msg=deleted");
    }

   private void getSizeStock(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String productID = req.getParameter("id");
        Map<String, Integer> sizes = clothesDAO.getSizeQuantities(productID);
        String[] ALL_SIZES = {"S", "M", "L", "XL", "XXL"};
        for (String sz : ALL_SIZES) sizes.putIfAbsent(sz, 0);
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (String sz : ALL_SIZES) {
            if (!first) json.append(",");
            json.append("\"").append(sz).append("\":").append(sizes.get(sz));
            first = false;
        }
        json.append("}");

        res.setContentType("application/json;charset=UTF-8");
        res.setHeader("Cache-Control", "no-cache");
        try (PrintWriter out = res.getWriter()) {
            out.print(json.toString());
        }
    }
   
   private void categoryList(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("CATEGORY_LIST", clothesDAO.getAllCategories());
        req.getRequestDispatcher("/admin/categoryList.jsp").forward(req, res);
    }

    private void addCategory(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        CategoryDTO c = new CategoryDTO();
        c.setCategoryName(req.getParameter("categoryName"));
        c.setGender(req.getParameter("gender"));
        c.setDescription(req.getParameter("description"));
        clothesDAO.addCategory(c);
        res.sendRedirect("AdminController?action=CategoryList&msg=added");
    }

    private void editCategory(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        CategoryDTO c = new CategoryDTO();
        c.setCategoryID(req.getParameter("categoryID"));
        c.setCategoryName(req.getParameter("categoryName"));
        c.setGender(req.getParameter("gender"));
        c.setDescription(req.getParameter("description"));
        clothesDAO.updateCategory(c);
        res.sendRedirect("AdminController?action=CategoryList&msg=updated");
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        clothesDAO.deleteCategory(req.getParameter("id"));
        res.sendRedirect("AdminController?action=CategoryList&msg=deleted");
    }

    private void orderList(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("ORDER_LIST", orderDAO.getAllOrders());
        req.getRequestDispatcher("/admin/orderList.jsp").forward(req, res);
    }

    private void orderDetail(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("ORDER", orderDAO.getOrderDetail(req.getParameter("id")));
        req.getRequestDispatcher("/admin/orderDetail.jsp").forward(req, res);
    }

    private void updateOrderStatus(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String orderID = req.getParameter("orderID");
        String status  = req.getParameter("status");
        orderDAO.updateStatus(orderID, status);
        res.sendRedirect("AdminController?action=OrderDetail&id=" + orderID + "&msg=updated");
    }

    private void userList(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("USER_LIST", userDAO.getAllUsers());
        req.getRequestDispatcher("/admin/userList.jsp").forward(req, res);
    }

    private void toggleUser(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String  userID = req.getParameter("id");
        boolean status = "1".equals(req.getParameter("status"));
        userDAO.updateStatus(userID, status);
        res.sendRedirect("AdminController?action=UserList");
    }

    private ClothesDTO buildProduct(HttpServletRequest req) {
        ClothesDTO p = new ClothesDTO();
        p.setProductName(req.getParameter("productName"));
        p.setCategoryID(req.getParameter("categoryID"));
        p.setPrice(parseDouble(req.getParameter("price"), 0));
        p.setDescription(req.getParameter("description"));
        p.setImg(req.getParameter("img"));

        Map<String, Integer> sizes = new java.util.LinkedHashMap<>();
        sizes.put("S",   parseInt(req.getParameter("stock_S"),   0));
        sizes.put("M",   parseInt(req.getParameter("stock_M"),   0));
        sizes.put("L",   parseInt(req.getParameter("stock_L"),   0));
        sizes.put("XL",  parseInt(req.getParameter("stock_XL"),  0));
        sizes.put("XXL", parseInt(req.getParameter("stock_XXL"), 0));
        p.setSizeQuantities(sizes);
        return p;
    }

    private String nullSafe(String v, String def) {
        return (v == null || v.trim().isEmpty()) ? def : v.trim();
    }
    private double parseDouble(String v, double def) {
        try { 
            return Double.parseDouble(v);
        }catch(Exception e) {
            return def;
        }
    }
    private int parseInt(String v, int def) {
        try {
            return Integer.parseInt(v); }
        catch (Exception e){
            return def; 
        }
    }
}