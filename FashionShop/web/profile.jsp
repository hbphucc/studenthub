<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Tài khoản — Fashion Shop</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container section">
  <div class="section-header">
    <h2 class="section-title">Tài khoản của tôi</h2>
  </div>

  <c:if test="${param.msg == 'updated'}">
    <div class="alert alert-success">Cập nhật thông tin thành công!</div>
  </c:if>

  <div style="display:grid;grid-template-columns:240px 1fr;gap:32px;">

    <!-- Sidebar menu -->
    <div>
      <div style="background:var(--c-surface);border-radius:12px;border:1px solid var(--c-border);overflow:hidden;">
        <div style="padding:20px;border-bottom:1px solid var(--c-border);text-align:center;">
          <div style="width:60px;height:60px;background:linear-gradient(135deg,var(--c-accent),var(--c-accent-dk));border-radius:50%;display:flex;align-items:center;justify-content:center;margin:0 auto 10px;font-size:24px;color:#fff;">
            ${LOGIN_USER.fullName.charAt(0).toUpperCase()}
          </div>
          <div style="font-weight:600;">${LOGIN_USER.fullName}</div>
          <div style="font-size:12px;color:var(--c-muted);">${LOGIN_USER.email}</div>
        </div>
        <div>
          <a href="MainController?action=UserProfile"   style="display:block;padding:12px 20px;font-size:14px;border-bottom:1px solid var(--c-border);background:rgba(200,149,108,.08);color:var(--c-accent);">
            <i class="fas fa-user fa-fw"></i> Thông tin cá nhân
          </a>
          <a href="MainController?action=OrderHistory"  style="display:block;padding:12px 20px;font-size:14px;border-bottom:1px solid var(--c-border);color:var(--c-text);">
            <i class="fas fa-box fa-fw"></i> Đơn hàng của tôi
          </a>
          <a href="MainController?action=Logout" style="display:block;padding:12px 20px;font-size:14px;color:var(--c-danger);">
            <i class="fas fa-sign-out-alt fa-fw"></i> Đăng xuất
          </a>
        </div>
      </div>
    </div>

    <!-- Profile form -->
    <div class="form-card">
      <h3>Thông tin cá nhân</h3>
      <form action="MainController" method="post">
        <input type="hidden" name="action" value="UpdateProfile">
        <div class="form-group">
          <label class="form-label">Họ và tên</label>
          <input type="text" name="fullName" class="form-control" value="${LOGIN_USER.fullName}" required>
        </div>
        <div class="form-group">
          <label class="form-label">Email (không thể thay đổi)</label>
          <input type="email" class="form-control" value="${LOGIN_USER.email}" disabled style="background:var(--c-bg);color:var(--c-muted);">
        </div>
        <div class="form-group">
          <label class="form-label">Số điện thoại</label>
          <input type="tel" name="phone" class="form-control" value="${LOGIN_USER.phone}" placeholder="0901234567">
        </div>
        <div class="form-group">
          <label class="form-label">Địa chỉ mặc định</label>
          <textarea name="address" class="form-control">${LOGIN_USER.address}</textarea>
        </div>
        <button type="submit" class="btn btn-primary">
          <i class="fas fa-save"></i> Lưu thay đổi
        </button>
      </form>
    </div>

  </div>
</div>

<div class="footer"><div class="container"><div class="footer-bottom">© 2026 Fashion Shop.</div></div></div>
</body>
</html>
