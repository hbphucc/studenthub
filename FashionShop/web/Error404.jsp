<%-- 
    Document   : Error404
    Created on : Mar 15, 2026, 3:41:43 PM
    Author     : hoang
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>404 — Không tìm thấy trang</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    body { display:flex; align-items:center; justify-content:center; min-height:100vh; background:var(--c-bg,#F5F2ED); margin:0; }
    .err-wrap { text-align:center; max-width:460px; padding:56px 40px; background:var(--c-surface,#fff); border-radius:20px; border:1px solid var(--c-border,#E8E2D9); box-shadow:0 16px 48px rgba(0,0,0,.1); }
    .err-code { font-family:var(--font-display,'Cormorant Garamond',serif); font-size:100px; font-weight:700; line-height:1; color:var(--c-accent,#C8956C); margin-bottom:8px; }
    .err-title { font-family:var(--font-display,'Cormorant Garamond',serif); font-size:26px; color:var(--c-text,#1C1815); margin-bottom:12px; font-weight:600; }
    .err-msg { font-size:14px; color:var(--c-muted,#8A8078); line-height:1.75; margin-bottom:32px; }
    .err-icon { font-size:48px; margin-bottom:16px; color:var(--c-accent,#C8956C); opacity:.4; }
    .btn-group { display:flex; gap:10px; justify-content:center; flex-wrap:wrap; }
  </style>
</head>
<body>
  <div class="err-wrap">
    <div class="err-icon"><i class="fas fa-compass"></i></div>
    <div class="err-code">404</div>
    <div class="err-title">Trang không tồn tại</div>
    <p class="err-msg">Trang bạn đang tìm có thể đã bị xóa, đổi tên<br>hoặc tạm thời không khả dụng.</p>
    <div class="btn-group">
      <a href="${pageContext.request.contextPath}/MainController?action=ShoppingPage" class="btn btn-primary">
        <i class="fas fa-home"></i> Về trang chủ
      </a>
      <a href="javascript:history.back()" class="btn btn-outline">
        <i class="fas fa-arrow-left"></i> Quay lại
      </a>
    </div>
  </div>
</body>
</html>

