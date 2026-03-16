<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard — Admin</title>
</head>
<body>
<div class="admin-layout">
  <jsp:include page="adminNav.jsp"/>

  <div class="admin-content">
    <div class="admin-header">
      <h1>Dashboard</h1>
      <span style="font-size:13px;color:var(--c-muted);">Xin chào, ${LOGIN_USER.fullName}</span>
    </div>

    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-card__icon">👕</div>
        <div class="stat-card__label">Tổng sản phẩm</div>
        <div class="stat-card__value">${TOTAL_PRODUCTS}</div>
      </div>
      <div class="stat-card">
        <div class="stat-card__icon">📦</div>
        <div class="stat-card__label">Tổng đơn hàng</div>
        <div class="stat-card__value">${TOTAL_ORDERS}</div>
      </div>
      <div class="stat-card">
        <div class="stat-card__icon">👥</div>
        <div class="stat-card__label">Người dùng</div>
        <div class="stat-card__value">${TOTAL_USERS}</div>
      </div>
    </div>

    <div style="display:grid;grid-template-columns:repeat(4,1fr);gap:16px;">
      <a href="AdminController?action=AddProductForm" class="btn btn-primary" style="justify-content:center;padding:14px;">
        <i class="fas fa-plus"></i> Thêm sản phẩm
      </a>
      <a href="AdminController?action=OrderList" class="btn btn-dark" style="justify-content:center;padding:14px;">
        <i class="fas fa-list"></i> Xem đơn hàng
      </a>
      <a href="AdminController?action=CategoryList" class="btn btn-outline" style="justify-content:center;padding:14px;">
        <i class="fas fa-tags"></i> Danh mục
      </a>
      <a href="AdminController?action=UserList" class="btn btn-outline" style="justify-content:center;padding:14px;">
        <i class="fas fa-users"></i> Người dùng
      </a>
    </div>
  </div>
</div>
</body>
</html>
