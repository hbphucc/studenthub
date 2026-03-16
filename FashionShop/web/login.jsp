<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đăng Nhập — Fashion Shop</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    body { display:flex; align-items:center; justify-content:center; min-height:100vh; background:var(--c-bg); }
    .auth-card { width:420px; background:var(--c-surface); border-radius:16px; box-shadow:var(--shadow-lg); overflow:hidden; }
    .auth-header { background:linear-gradient(135deg,#2C1810,#C8956C); padding:32px; text-align:center; color:#fff; }
    .auth-header h1 { font-family:var(--font-display); font-size:28px; }
    .auth-header p  { font-size:13px; opacity:.8; margin-top:6px; }
    .auth-body  { padding:32px; }
    .auth-footer { text-align:center; font-size:13px; color:var(--c-muted); margin-top:20px; }
    .auth-footer a { color:var(--c-accent); font-weight:500; }
  </style>
</head>
<body>
<div class="auth-card">
  <div class="auth-header">
    <h1>✦ Fashion Shop</h1>
    <p>Đăng nhập tài khoản</p>
  </div>
  <div class="auth-body">

    <c:if test="${not empty ERROR}">
      <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${ERROR}</div>
    </c:if>
    <c:if test="${param.msg == 'register_success'}">
      <div class="alert alert-success"><i class="fas fa-check-circle"></i> Đăng ký thành công! Hãy đăng nhập.</div>
    </c:if>
    <c:if test="${param.msg == 'login_required'}">
      <div class="alert alert-danger">Vui lòng đăng nhập để tiếp tục.</div>
    </c:if>
    <c:if test="${param.msg == 'unauthorized'}">
      <div class="alert alert-danger">Bạn không có quyền truy cập trang này.</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/MainController" method="post">
      <input type="hidden" name="action" value="Login">
      <div class="form-group">
        <label class="form-label">Email</label>
        <input type="email" name="email" class="form-control" placeholder="email@example.com" required autofocus>
      </div>
      <div class="form-group">
        <label class="form-label">Mật khẩu</label>
        <input type="password" name="password" class="form-control" placeholder="••••••••" required>
      </div>
      <button type="submit" class="btn btn-primary btn-full" style="margin-top:8px;">
        <i class="fas fa-sign-in-alt"></i> Đăng nhập
      </button>
    </form>

    <div class="auth-footer">
      Chưa có tài khoản? <a href="${pageContext.request.contextPath}/MainController?action=Register">Đăng ký ngay</a>
    </div>
  </div>
</div>
</body>
</html>
