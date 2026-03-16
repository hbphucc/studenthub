<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${PRODUCT.productName} — Fashion Shop</title>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="container">
  <p style="padding:16px 0;font-size:13px;color:var(--c-muted);">
    <a href="MainController?action=ShoppingPage">Trang chủ</a> &rsaquo;
    <a href="MainController?action=FilterCategory&categoryID=${PRODUCT.categoryID}">${PRODUCT.categoryName}</a> &rsaquo;
    ${PRODUCT.productName}
  </p>

  <div class="product-detail">
    <div class="product-detail__img"
         style="background-image:url('${PRODUCT.img}')">
    </div>

    <div class="product-detail__info">
      <div class="product-detail__cat">${PRODUCT.categoryName} &bull; ${PRODUCT.gender == 'male' ? 'Nam' : PRODUCT.gender == 'female' ? 'Nữ' : 'Unisex'}</div>
      <h1>${PRODUCT.productName}</h1>
      <div class="product-detail__price">
        <fmt:formatNumber value="${PRODUCT.price}" type="number" groupingUsed="true"/> ₫
      </div>
      <p class="product-detail__desc">${PRODUCT.description}</p>

      <form action="MainController" method="get" id="addCartForm">
        <input type="hidden" name="action" value="AddToCart">
        <input type="hidden" name="id"     value="${PRODUCT.productID}">
        <input type="hidden" name="size"   id="selectedSize" value="">

        <div style="margin-bottom:20px;">
          <div class="form-label">Chọn size:</div>
          <c:choose>
            <c:when test="${empty PRODUCT.availableSizes}">
              <p style="color:var(--c-danger);font-size:14px;">Tạm hết hàng</p>
            </c:when>
                <c:otherwise>
                    <div class="size-selector">
                    <c:forEach var="sz" items="${PRODUCT.availableSizes}">
                      <button type="button" class="size-btn"
                              onclick="selectSize(this, '${sz}', ${PRODUCT.sizeQuantities[sz]})">
                        ${sz}
                      </button>
                    </c:forEach>
                  </div>
                    <p id="size-quantity-info" style="margin-top:8px;font-size:13px;min-height:18px;"></p>
                  </c:otherwise>
          </c:choose>
        </div>

        <div class="form-label">Số lượng:</div>
        <div class="qty-control" style="margin-bottom:24px;">
          <button type="button" onclick="changeQty(-1)">−</button>
          <input type="number" name="qty" id="qtyInput" value="1" min="1" max="10">
          <button type="button" onclick="changeQty(1)">+</button>
        </div>

        <div style="display:flex;gap:12px;">
          <button type="button" class="btn btn-primary" style="flex:1;" onclick="submitCart()">
            <i class="fas fa-cart-plus"></i> Thêm vào giỏ
          </button>
          <a href="MainController?action=ShoppingPage" class="btn btn-outline">Tiếp tục mua</a>
        </div>
      </form>
    </div>

  </div>
</div>

<div class="footer">
  <div class="container">
    <div class="footer-bottom">© 2026 Fashion Shop. All rights reserved.</div>
  </div>
</div>

<script>
  function selectSize(btn, size, qty) {
    document.querySelectorAll('.size-btn').forEach(b => b.classList.remove('selected'));
    btn.classList.add('selected');
    document.getElementById('selectedSize').value = size;

    const info = document.getElementById('size-quantity-info');
    if (qty > 0) {
        info.textContent = 'Còn lại: ' + qty + ' sản phẩm';
        info.style.color = '#555';
    } else {
        info.textContent = 'Size này tạm hết hàng';
        info.style.color = '#e74c3c';
    }

    const qtyInput = document.getElementById('qtyInput');
    qtyInput.max   = qty || 1;
    qtyInput.value = 1;
  }

  function changeQty(delta) {
    const input  = document.getElementById('qtyInput');
    const maxQty = parseInt(input.max) || 1;
    input.value  = Math.max(1, Math.min(maxQty, parseInt(input.value) + delta));
  }

  function submitCart() {
    const size = document.getElementById('selectedSize').value;
    if (!size) { alert('Vui lòng chọn size!'); return; }
    document.getElementById('addCartForm').submit();
  }
</script>
</body>
</html>