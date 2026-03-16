package Controller;
/**
 *
 * @author hoang
 */

import Cart.CartItem;
import Clothes.ClothesDAO;
import Clothes.ClothesDTO;
import Order.OrderDAO;
import Order.OrderDTO;
import Users.UserDAO;
import Users.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;

public class MainController extends HttpServlet {

    private ClothesDAO clothesDAO = new ClothesDAO();
    private UserDAO    userDAO    = new UserDAO();
    private OrderDAO   orderDAO   = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        handle(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        handle(req, res);
    }

    private void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "ShoppingPage";

        switch (action) {
            case "ShoppingPage":
                showShoppingPage(req, res);
                break;
            case "SearchProduct":
                searchProduct(req, res); 
                break;
            case "FilterCategory":
                filterCategory(req, res);
                break;
            case "FilterGender": 
                filterGender(req, res); 
                break;
            case "ViewDetail":   
                viewDetail(req, res);  
                break;

            case "Register":     
                register(req, res);  
                break;
            case "Login":   
                login(req, res); 
                break;
            case "Logout":   
                logout(req, res); 
                break;
            case "ProductDetail":
            String id = req.getParameter("id");
            ClothesDTO product = clothesDAO.getByID(id); 
            req.setAttribute("PRODUCT", product);
            req.getRequestDispatcher("product-detail.jsp").forward(req, res);
            break;

            case "AddToCart":  
                addToCart(req, res);  
                break;
            case "UpdateCart": 
                updateCart(req, res); 
                break;
            case "RemoveFromCart": 
                removeFromCart(req, res); 
                break;
            case "ViewCart":   
                viewCart(req, res);   
                break;

            case "Checkout":    
                checkout(req, res);    
                break;
            case "PlaceOrder":  
                placeOrder(req, res); 
                break;
            case "OrderHistory": 
                orderHistory(req, res);   
                break;
            case "OrderDetail":  
                orderDetail(req, res);    
                break;

            case "UserProfile":   
                userProfile(req, res);     
                break;
            case "UpdateProfile":  
                updateProfile(req, res);
                break;

            default:               
                showShoppingPage(req, res);
        }
    }


        private void showShoppingPage(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            List<ClothesDTO> products = clothesDAO.getAllClothes();
            req.setAttribute("LIST_PRODUCT",products);
            req.setAttribute("LIST_CATEGORY",clothesDAO.getAllCategories());
            req.getRequestDispatcher("/index.jsp").forward(req, res);
        }

        private void searchProduct(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            String keyword = nullSafe(req.getParameter("search"));
            req.setAttribute("LIST_PRODUCT",clothesDAO.searchByName(keyword));
            req.setAttribute("LIST_CATEGORY",clothesDAO.getAllCategories());
            req.setAttribute("SEARCH_KEYWORD",keyword);
            req.getRequestDispatcher("/index.jsp").forward(req, res);
        }

        private void filterCategory(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            String catID = req.getParameter("categoryID");
            req.setAttribute("LIST_PRODUCT",clothesDAO.getByCategory(catID));
            req.setAttribute("LIST_CATEGORY",clothesDAO.getAllCategories());
            req.setAttribute("CURRENT_CATEGORY",catID);
            req.getRequestDispatcher("/index.jsp").forward(req, res);
        }

        private void filterGender(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            String gender = req.getParameter("gender");
            req.setAttribute("LIST_PRODUCT",  clothesDAO.getByGender(gender));
            req.setAttribute("LIST_CATEGORY", clothesDAO.getAllCategories());
            req.setAttribute("CURRENT_GENDER", gender);
            req.getRequestDispatcher("/index.jsp").forward(req, res);
        }

        private void viewDetail(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            String id = req.getParameter("id");
            ClothesDTO product = clothesDAO.getByID(id);
            if (product == null){
                res.sendRedirect("MainController?action=ShoppingPage");
                return; 
            }
            req.setAttribute("PRODUCT", product);
            req.getRequestDispatcher("/productDetail.jsp").forward(req, res);
        }


        private void register(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            if ("GET".equals(req.getMethod())) {
                req.getRequestDispatcher("/register.jsp").forward(req, res);
                return;
            }
            String fullName  = req.getParameter("fullName");
            String email     = req.getParameter("email");
            String password  = req.getParameter("password");
            String password2 = req.getParameter("password2");
            String phone     = req.getParameter("phone");

            if (!password.equals(password2)) {
                req.setAttribute("ERROR", "Mật khẩu xác nhận không khớp");
                req.getRequestDispatcher("/register.jsp").forward(req, res); 
                return;
            }
            UserDTO user = new UserDTO();
            user.setFullName(fullName); 
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            boolean ok = userDAO.register(user);
            if (ok) {
                res.sendRedirect("login.jsp?msg=register_success");
            } else {
                req.setAttribute("ERROR", "Email đã được đăng ký");
                req.getRequestDispatcher("/register.jsp").forward(req, res);
            }
        }

        private void login(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            String email    = req.getParameter("email");
            String password = req.getParameter("password");
            UserDTO user = userDAO.login(email, password);
            if (user != null) {
                req.getSession().invalidate();
                HttpSession newSession = req.getSession(true);
                newSession.setAttribute("LOGIN_USER", user);
                if (user.isAdmin()) {
                    res.sendRedirect(req.getContextPath() + "/AdminController?action=Dashboard");
                } else {
                    res.sendRedirect("MainController?action=ShoppingPage");
                }
            } else {
                req.setAttribute("ERROR", "Email hoặc mật khẩu không đúng");
                req.getRequestDispatcher("/login.jsp").forward(req, res);
            }
        }

        private void logout(HttpServletRequest req, HttpServletResponse res)
                throws IOException {
            req.getSession().invalidate();
            res.sendRedirect("MainController?action=ShoppingPage");
        }

        @SuppressWarnings("unchecked")
        private Map<String, CartItem> getCart(HttpServletRequest req) {
            HttpSession session = req.getSession();
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("CART");
            if (cart == null){ 
                cart = new LinkedHashMap<>();
                session.setAttribute("CART", cart); }
            return cart;
        }

        private void addToCart(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String productID = req.getParameter("id");
        String size      = nullSafe(req.getParameter("size"));
        int    qty       = parseInt(req.getParameter("qty"), 1);

        ClothesDTO product = clothesDAO.getByID(productID);
        if (product == null) { 
            res.sendRedirect("MainController?action=ShoppingPage");
            return; 
        }

        int maxStock = product.getSizeQuantities().getOrDefault(size, 0);

        Map<String, CartItem> cart = getCart(req);
        String key = productID + "_" + size;
        
        if (cart.containsKey(key)) {
            int newQty = cart.get(key).getQuantity() + qty;
            if (newQty > maxStock) {
                cart.get(key).setQuantity(maxStock);
            } else {
                cart.get(key).setQuantity(newQty);
            }
        } else {
            if (qty > maxStock) qty = maxStock;
            cart.put(key, new CartItem(productID, product.getProductName(),
                                       product.getImg(), size, product.getPrice(), qty));
        }
        res.sendRedirect("MainController?action=ViewCart");
    }

        private void updateCart(HttpServletRequest req, HttpServletResponse res)
                throws IOException {
            String key = req.getParameter("key");
            int    qty = parseInt(req.getParameter("qty"), 1);
            Map<String, CartItem> cart = getCart(req);
            if (qty <= 0) cart.remove(key);
            else if (cart.containsKey(key)) cart.get(key).setQuantity(qty);
            res.sendRedirect("MainController?action=ViewCart");
        }

        private void removeFromCart(HttpServletRequest req, HttpServletResponse res)
                throws IOException {
            String key = req.getParameter("key");
            getCart(req).remove(key);
            res.sendRedirect("MainController?action=ViewCart");
        }

        private void viewCart(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            Map<String, CartItem> cart = getCart(req);
            double total = cart.values().stream().mapToDouble(CartItem::getSubTotal).sum();
            req.setAttribute("CART_ITEMS", cart);
            req.setAttribute("CART_TOTAL", total);
            req.getRequestDispatcher("/cart.jsp").forward(req, res);
        }

        private void checkout(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            UserDTO user = (UserDTO) req.getSession().getAttribute("LOGIN_USER");
                if (user == null) {
                    res.sendRedirect("login.jsp?msg=login_required");
                    return; 
                }
                Map<String, CartItem> cart = getCart(req);
                if (cart.isEmpty()) {
                    res.sendRedirect("MainController?action=ViewCart"); 
                    return;
                }
            double total = cart.values().stream().mapToDouble(CartItem::getSubTotal).sum();
            req.setAttribute("CART_ITEMS", cart);
            req.setAttribute("CART_TOTAL", total);
            req.getRequestDispatcher("/checkout.jsp").forward(req, res);
        }

        private void placeOrder(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            UserDTO user = (UserDTO) req.getSession().getAttribute("LOGIN_USER");
            if (user == null) { 
                res.sendRedirect("login.jsp");
                return; 
            }
            Map<String, CartItem> cart = getCart(req);
            if (cart.isEmpty()) {
                res.sendRedirect("MainController?action=ViewCart");
                return; 
            }

            double total = cart.values().stream().mapToDouble(CartItem::getSubTotal).sum();
            OrderDTO order = new OrderDTO();
            order.setUserID(user.getUserID());
            order.setTotalAmount(total);
            order.setFullName(req.getParameter("fullName"));
            order.setPhone(req.getParameter("phone"));
            order.setAddress(req.getParameter("address"));
            order.setNote(req.getParameter("note"));
            order.setPaymentMethod(nullSafe(req.getParameter("paymentMethod"), "COD"));

            String orderID = orderDAO.placeOrder(order, cart);
            if (orderID != null) {
                cart.clear();
                req.getSession().setAttribute("CART", cart);
                res.sendRedirect("MainController?action=OrderDetail&id=" + orderID + "&success=1");
            } else {
                req.setAttribute("ERROR", "Đặt hàng thất bại, vui lòng thử lại");
                req.getRequestDispatcher("/checkout.jsp").forward(req, res);
            }
        }

        private void orderHistory(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            UserDTO user = (UserDTO) req.getSession().getAttribute("LOGIN_USER");
            if (user == null){
                res.sendRedirect("login.jsp");
                return;
            }
            req.setAttribute("ORDER_LIST", orderDAO.getOrdersByUser(user.getUserID()));
            req.getRequestDispatcher("/orderHistory.jsp").forward(req, res);
        }

        private void orderDetail(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            String orderID = req.getParameter("id");
            OrderDTO order = orderDAO.getOrderDetail(orderID);
            req.setAttribute("ORDER", order);
            req.getRequestDispatcher("/orderDetail.jsp").forward(req, res);
        }


        private void userProfile(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            req.getRequestDispatcher("profile.jsp").forward(req, res);
        }

        private void updateProfile(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            UserDTO user = (UserDTO) req.getSession().getAttribute("LOGIN_USER");
            if (user == null) {
                res.sendRedirect("login.jsp?msg=login_required");
                return;
            }
            user.setFullName(req.getParameter("fullName"));
            user.setPhone(req.getParameter("phone"));
            user.setAddress(req.getParameter("address"));
            userDAO.updateProfile(user);
            req.getSession().setAttribute("LOGIN_USER", user);
            res.sendRedirect("MainController?action=UserProfile&msg=updated");
        }


        private String nullSafe(String v){ 
            return v == null ? "" : v.trim(); 
        }

        private String nullSafe(String v, String def){ 
            return (v == null || v.trim().isEmpty()) ? def : v.trim(); 
        }
        private int    parseInt(String v, int def){ 
            try{ 
                return Integer.parseInt(v); 
            }catch(Exception e){ 
                return def; 
            } 
    }
}