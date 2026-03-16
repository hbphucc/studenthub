<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đăng Ký — Fashion Shop</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    body { display:flex; align-items:center; justify-content:center; min-height:100vh; background:var(--c-bg); padding:32px 0; }
    .auth-card { width:460px; background:var(--c-surface); border-radius:16px; box-shadow:var(--shadow-lg); overflow:hidden; }
    .auth-header { background:linear-gradient(135deg,#2C1810,#C8956C); padding:28px 32px; text-align:center; color:#fff; }
    .auth-header h1 { font-family:var(--font-display); font-size:26px; }
    .auth-body  { padding:32px; }
    .auth-footer { text-align:center; font-size:13px; color:var(--c-muted); margin-top:20px; }
    .auth-footer a { color:var(--c-accent); font-weight:500; }
    .form-row   { display:grid; grid-template-columns:1fr 1fr; gap:16px; }
  </style>
</head>
<body>
<div class="auth-card">
  <div class="auth-header">
    <h1>✦ Fashion Shop</h1>
    <p>Tạo tài khoản mới</p>
  </div>
  <div class="auth-body">

    <c:if test="${not empty ERROR}">
      <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${ERROR}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/MainController" method="post">
      <input type="hidden" name="action" value="Register">

      <div class="form-group">
        <label class="form-label">Họ và tên</label>
        <input type="text" name="fullName" class="form-control" placeholder="Nguyễn Văn A" required>
      </div>

      <div class="form-group">
        <label class="form-label">Email</label>
        <input type="email" name="email" class="form-control" placeholder="email@example.com" required>
      </div>

      <div class="form-group">
        <label class="form-label">Số điện thoại</label>
        <input type="tel" name="phone" class="form-control" placeholder="0901234567">
      </div>

      <div class="form-row">
        <div class="form-group">
          <label class="form-label">Mật khẩu</label>
          <input type="password" name="password" id="pwd" class="form-control" placeholder="••••••••" required minlength="6">
        </div>
        <div class="form-group">
          <label class="form-label">Xác nhận mật khẩu</label>
          <input type="password" name="password2" id="pwd2" class="form-control" placeholder="••••••••" required>
        </div>
      </div>
      <div id="pwdError" style="color:var(--c-danger);font-size:13px;margin-top:-8px;margin-bottom:12px;display:none;">
        Mật khẩu không khớp
      </div>

      <button type="submit" class="btn btn-primary btn-full" onclick="return checkPwd()">
        <i class="fas fa-user-plus"></i> Đăng ký
      </button>
    </form>

    <div class="auth-footer">
      Đã có tài khoản? <a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
    </div>
  </div>
</div>
<script>
function checkPwd() {
  const ok = document.getElementById('pwd').value === document.getElementById('pwd2').value;
  document.getElementById('pwdError').style.display = ok ? 'none' : 'block';
  return ok;
}
</script>
</body>
</html>
