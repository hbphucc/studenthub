<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<div class="sidebar">
  <div class="sidebar__logo">⚙ Admin Panel</div>

  <div class="sidebar__section">Tổng quan</div>
  <a href="${pageContext.request.contextPath}/AdminController?action=Dashboard">
    <i class="fas fa-chart-line fa-fw"></i> Dashboard
  </a>

  <div class="sidebar__section">Sản phẩm</div>
  <a href="${pageContext.request.contextPath}/AdminController?action=ProductList">
    <i class="fas fa-tshirt fa-fw"></i> Sản phẩm
  </a>
  <a href="${pageContext.request.contextPath}/AdminController?action=CategoryList">
    <i class="fas fa-tags fa-fw"></i> Danh mục
  </a>

  <div class="sidebar__section">Đơn hàng</div>
  <a href="${pageContext.request.contextPath}/AdminController?action=OrderList">
    <i class="fas fa-shopping-bag fa-fw"></i> Đơn hàng
  </a>

  <div class="sidebar__section">Người dùng</div>
  <a href="${pageContext.request.contextPath}/AdminController?action=UserList">
    <i class="fas fa-users fa-fw"></i> Tài khoản
  </a>

  <div style="margin-top:auto;padding:20px 20px 0;border-top:1px solid rgba(255,255,255,.1);margin-top:32px;">
    <a href="${pageContext.request.contextPath}/MainController?action=ShoppingPage" style="color:rgba(255,255,255,.5);font-size:13px;">
      <i class="fas fa-store fa-fw"></i> Xem cửa hàng
    </a><br><br>
    <a href="${pageContext.request.contextPath}/MainController?action=Logout" style="color:rgba(255,255,255,.5);font-size:13px;">
      <i class="fas fa-sign-out-alt fa-fw"></i> Đăng xuất
    </a>
  </div>
</div>
