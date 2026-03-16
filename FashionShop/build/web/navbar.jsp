<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<%
    java.util.Map<?,?> cart = (java.util.Map<?,?>) session.getAttribute("CART");
    int cartCount = (cart != null) ? cart.size() : 0;
    String currentPath = request.getServletPath();
%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<nav class="navbar">
  <div class="container">
    <div class="navbar__inner">

      <a href="${pageContext.request.contextPath}/MainController?action=ShoppingPage" class="navbar__logo">
        ✦ FASHION SHOP
      </a>

      <div class="navbar__search">
        <form action="${pageContext.request.contextPath}/MainController" method="get">
          <input type="hidden" name="action" value="SearchProduct">
          <input type="text" name="search" placeholder="Tìm kiếm sản phẩm..."
                 value="${SEARCH_KEYWORD}">
          <button type="submit"><i class="fas fa-search"></i></button>
        </form>
      </div>

      <nav class="navbar__nav">
        <a href="${pageContext.request.contextPath}/MainController?action=ShoppingPage">Trang chủ</a>
        <a href="${pageContext.request.contextPath}/MainController?action=FilterGender&gender=male">Nam</a>
        <a href="${pageContext.request.contextPath}/MainController?action=FilterGender&gender=female">Nữ</a>
        <a href="${pageContext.request.contextPath}/MainController?action=FilterGender&gender=unisex">Unisex</a>
      </nav>

      <div class="navbar__right">
        <div class="navbar__user">
          <c:choose>
            <c:when test="${not empty LOGIN_USER}">
              Xin chào, <a href="${pageContext.request.contextPath}/MainController?action=UserProfile">${LOGIN_USER.fullName}</a>
              <c:if test="${LOGIN_USER.admin}">
                | <a href="${pageContext.request.contextPath}/AdminController?action=Dashboard" style="color:var(--c-accent)">Admin</a>
              </c:if>
              | <a href="${pageContext.request.contextPath}/MainController?action=Logout">Đăng xuất</a>
            </c:when>
            <c:otherwise>
              <a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
              | <a href="${pageContext.request.contextPath}/MainController?action=Register">Đăng ký</a>
            </c:otherwise>
          </c:choose>
        </div>
        <div class="navbar__cart">
          <a href="${pageContext.request.contextPath}/MainController?action=ViewCart">
            <i class="fas fa-shopping-bag"></i>
            Giỏ hàng
            <% if (cartCount > 0) { %><span class="cart-count"><%= cartCount %></span><% } %>
          </a>
        </div>
      </div>

    </div>
  </div>
</nav>
