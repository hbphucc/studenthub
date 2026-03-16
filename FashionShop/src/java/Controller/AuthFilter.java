package Controller;

import Users.UserDTO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter that enforces authentication and authorization.
 * - /admin/*  → requires role == "admin"
 * - /order/*  → requires any logged-in user
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("LOGIN_USER") : null;

        if (path.startsWith("/admin")) {
            if (user == null || !user.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/login.jsp?msg=unauthorized");
                return;
            }
        } else if (path.startsWith("/order") || path.startsWith("/cart")) {
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp?msg=login_required");
                return;
            }
        }
        chain.doFilter(req, res);
    }

    @Override public void init(FilterConfig fc) {}
    @Override public void destroy() {}
}
