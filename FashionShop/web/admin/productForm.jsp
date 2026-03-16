<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>${empty PRODUCT ? 'Thêm' : 'Sửa'} sản phẩm — Admin</title>
  <style>
    .size-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(80px, 1fr));
      gap: 12px;
      background: #f8f9fa;
      padding: 15px;
      border-radius: 8px;
      border: 1px solid #e0e0e0;
    }
    .size-item label {
      display: block;
      font-weight: 600;
      margin-bottom: 5px;
      font-size: 14px;
      text-align: center;
    }
  </style>
</head>
<body>
<div class="admin-layout">
  <jsp:include page="adminNav.jsp"/>
  <div class="admin-content">

    <div class="admin-header">
      <h1>${empty PRODUCT ? 'Thêm sản phẩm mới' : 'Chỉnh sửa sản phẩm'}</h1>
      <a href="AdminController?action=ProductList" class="btn btn-outline btn-sm">← Quay lại</a>
    </div>

    <div style="max-width:700px;">
      <div class="form-card">
        <form action="${pageContext.request.contextPath}/AdminController" method="post">
          <input type="hidden" name="action"    value="${empty PRODUCT ? 'AddProduct' : 'UpdateProduct'}">
          <input type="hidden" name="productID" value="${PRODUCT.productID}">

          <div class="form-group">
            <label class="form-label">Tên sản phẩm *</label>
            <input type="text" name="productName" class="form-control"
                   value="${PRODUCT.productName}" required placeholder="Áo Polo Nam Classic">
          </div>

          <div style="display:grid;grid-template-columns:1fr 1fr;gap:16px;">
            <div class="form-group">
              <label class="form-label">Danh mục *</label>
              <select name="categoryID" class="form-control" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="cat" items="${CATEGORY_LIST}">
                  <option value="${cat.categoryID}"
                    ${cat.categoryID == PRODUCT.categoryID ? 'selected' : ''}>
                    ${cat.categoryName}
                  </option>
                </c:forEach>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">Giá (₫) *</label>
              <input type="number" name="price" class="form-control"
                     value="${PRODUCT.price}" required min="0" placeholder="350000">
            </div>
          </div>

          <div class="form-group" style="margin-top: 10px; margin-bottom: 20px;">
            <label class="form-label" style="font-weight: bold; color: var(--c-primary);">Số lượng nhập kho theo Size *</label>
            <div class="size-grid">
              <div class="size-item">
                <label>Size S</label>
                <input type="number" name="stock_S" value="${PRODUCT != null && PRODUCT.sizeQuantities['S'] != null ? PRODUCT.sizeQuantities['S'] : 0}" min="0" class="form-control" style="text-align: center;">
              </div>
              <div class="size-item">
                <label>Size M</label>
                <input type="number" name="stock_M" value="${PRODUCT != null && PRODUCT.sizeQuantities['M'] != null ? PRODUCT.sizeQuantities['M'] : 0}" min="0" class="form-control" style="text-align: center;">
              </div>
              <div class="size-item">
                <label>Size L</label>
                <input type="number" name="stock_L" value="${PRODUCT != null && PRODUCT.sizeQuantities['L'] != null ? PRODUCT.sizeQuantities['L'] : 0}" min="0" class="form-control" style="text-align: center;">
              </div>
              <div class="size-item">
                <label>Size XL</label>
                <input type="number" name="stock_XL" value="${PRODUCT != null && PRODUCT.sizeQuantities['XL'] != null ? PRODUCT.sizeQuantities['XL'] : 0}" min="0" class="form-control" style="text-align: center;">
              </div>
              <div class="size-item">
                <label>Size XXL</label>
                <input type="number" name="stock_XXL" value="${PRODUCT != null && PRODUCT.sizeQuantities['XXL'] != null ? PRODUCT.sizeQuantities['XXL'] : 0}" min="0" class="form-control" style="text-align: center;">
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="form-label">URL ảnh</label>
            <input type="url" name="img" class="form-control"
                   value="${PRODUCT.img}" placeholder="https://...">
          </div>

          <c:if test="${not empty PRODUCT.img}">
            <div class="form-group">
              <label class="form-label">Ảnh hiện tại</label>
              <br/>
              <img src="${PRODUCT.img}" style="height:120px; border-radius:8px; object-fit:cover; border: 1px solid #ddd; padding: 3px;">
            </div>
          </c:if>

          <div class="form-group">
            <label class="form-label">Mô tả</label>
            <textarea name="description" class="form-control" rows="4">${PRODUCT.description}</textarea>
          </div>

          <div style="display:flex;gap:12px;margin-top:20px;">
            <button type="submit" class="btn btn-primary" style="padding: 10px 20px;">
              <i class="fas fa-save"></i> ${empty PRODUCT ? 'Thêm sản phẩm' : 'Cập nhật'}
            </button>
            <a href="AdminController?action=ProductList" class="btn btn-outline" style="padding: 10px 20px;">Huỷ</a>
          </div>
        </form>
      </div>
    </div>

  </div>
</div>
</body>
</html>
