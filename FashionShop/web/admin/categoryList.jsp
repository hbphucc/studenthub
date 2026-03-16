<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>Quản lý danh mục — Admin</title></head>
<body>
<div class="admin-layout">
  <jsp:include page="adminNav.jsp"/>
  <div class="admin-content">

    <div class="admin-header">
      <h1>Quản lý danh mục</h1>
    </div>

    <c:if test="${param.msg == 'added'}"><div class="alert alert-success">Thêm danh mục thành công!</div></c:if>
    <c:if test="${param.msg == 'updated'}"><div class="alert alert-success">Cập nhật thành công!</div></c:if>

    <div style="display:grid;grid-template-columns:1fr 360px;gap:24px;">

      <table class="admin-table">
        <thead>
          <tr>
            <th>Tên danh mục</th>
            <th>Giới tính</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="cat" items="${CATEGORY_LIST}">
            <tr>
              <td><strong>${cat.categoryName}</strong></td>
              <td>
                <span class="badge" style="background:var(--c-bg);color:var(--c-text);border:1px solid var(--c-border);">
                  ${cat.gender == 'male' ? 'Nam' : cat.gender == 'female' ? 'Nữ' : 'Unisex'}
                </span>
              </td>
              <td>
                <div class="actions">
                  <button class="btn btn-outline btn-sm" onclick="fillEdit('${cat.categoryID}','${cat.categoryName}','${cat.gender}','${cat.description}')">
                    <i class="fas fa-edit"></i> Sửa
                  </button>
                  <a href="AdminController?action=DeleteCategory&id=${cat.categoryID}"
                     class="btn btn-danger btn-sm"
                     onclick="return confirm('Xóa danh mục?')">
                    <i class="fas fa-trash"></i>
                  </a>
                </div>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <div class="form-card" id="catForm">
        <h3 id="formTitle">Thêm danh mục mới</h3>
        <form action="${pageContext.request.contextPath}/AdminController" method="post">
          <input type="hidden" name="action"     id="formAction" value="AddCategory">
          <input type="hidden" name="categoryID" id="catID"      value="">
          <div class="form-group">
            <label class="form-label">Tên danh mục *</label>
            <input type="text" name="categoryName" id="catName" class="form-control" required>
          </div>
          <div class="form-group">
            <label class="form-label">Giới tính</label>
            <select name="gender" id="catGender" class="form-control">
              <option value="male">Nam</option>
              <option value="female">Nữ</option>
              <option value="unisex">Unisex</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label">Mô tả</label>
            <input type="text" name="description" id="catDesc" class="form-control">
          </div>
          <button type="submit" class="btn btn-primary btn-full" id="submitBtn">
            <i class="fas fa-plus"></i> Thêm danh mục
          </button>
          <button type="button" class="btn btn-outline btn-full" style="margin-top:8px;" onclick="resetForm()">
            Huỷ
          </button>
        </form>
      </div>
    </div>

  </div>
</div>
<script>
function fillEdit(id, name, gender, desc) {
  document.getElementById('formAction').value = 'EditCategory';
  document.getElementById('catID').value = id;
  document.getElementById('catName').value = name;
  document.getElementById('catGender').value = gender;
  document.getElementById('catDesc').value = desc || '';
  document.getElementById('formTitle').textContent = 'Chỉnh sửa danh mục';
  document.getElementById('submitBtn').innerHTML = '<i class="fas fa-save"></i> Cập nhật';
}
function resetForm() {
  document.getElementById('formAction').value = 'AddCategory';
  document.getElementById('catID').value = '';
  document.getElementById('catName').value = '';
  document.getElementById('catGender').value = 'male';
  document.getElementById('catDesc').value = '';
  document.getElementById('formTitle').textContent = 'Thêm danh mục mới';
  document.getElementById('submitBtn').innerHTML = '<i class="fas fa-plus"></i> Thêm danh mục';
}
</script>
</body>
</html>
