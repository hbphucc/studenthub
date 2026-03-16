<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>Quản lý người dùng — Admin</title></head>
<body>
<div class="admin-layout">
  <jsp:include page="adminNav.jsp"/>
  <div class="admin-content">

    <div class="admin-header">
      <h1>Quản lý người dùng</h1>
      <span style="color:var(--c-muted);font-size:14px;">${USER_LIST.size()} tài khoản</span>
    </div>

    <table class="admin-table">
      <thead>
        <tr>
          <th>Họ tên</th>
          <th>Email</th>
          <th>Điện thoại</th>
          <th>Vai trò</th>
          <th>Trạng thái</th>
          <th>Hành động</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="u" items="${USER_LIST}">
          <tr>
            <td><strong>${u.fullName}</strong></td>
            <td>${u.email}</td>
            <td>${u.phone}</td>
            <td>
              <c:choose>
                <c:when test="${u.admin}">
                  <span class="badge badge-processing">Admin</span>
                </c:when>
                <c:otherwise>
                  <span class="badge" style="background:var(--c-bg);color:var(--c-text);border:1px solid var(--c-border);">Khách hàng</span>
                </c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:choose>
                <c:when test="${u.status}"><span class="badge badge-delivered">Hoạt động</span></c:when>
                <c:otherwise><span class="badge badge-cancelled">Đã khoá</span></c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:if test="${!u.admin}">
                <c:choose>
                  <c:when test="${u.status}">
                    <a href="AdminController?action=ToggleUser&id=${u.userID}&status=0"
                       class="btn btn-danger btn-sm"
                       onclick="return confirm('Khoá tài khoản này?')">
                      <i class="fas fa-lock"></i> Khoá
                    </a>
                  </c:when>
                  <c:otherwise>
                    <a href="AdminController?action=ToggleUser&id=${u.userID}&status=1"
                       class="btn btn-outline btn-sm" style="color:var(--c-success);border-color:var(--c-success);">
                      <i class="fas fa-unlock"></i> Mở khoá
                    </a>
                  </c:otherwise>
                </c:choose>
              </c:if>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

  </div>
</div>
</body>
</html>
