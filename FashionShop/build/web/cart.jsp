<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Giỏ Hàng — Fashion Shop</title>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="container">
  <div style="padding:20px 0 8px;">
    <h2 style="font-family:var(--font-display);font-size:28px;">Giỏ hàng của bạn</h2>
  </div>

  <c:choose>
    <c:when test="${empty CART_ITEMS}">
      <div style="text-align:center;padding:80px 0;color:var(--c-muted);">
        <div style="font-size:60px;margin-bottom:20px;">🛍️</div>
        <h3 style="margin-bottom:12px;">Giỏ hàng trống</h3>
        <p style="margin-bottom:24px;">Hãy thêm sản phẩm yêu thích vào giỏ hàng</p>
        <a href="MainController?action=ShoppingPage" class="btn btn-primary">Tiếp tục mua sắm</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="cart-layout">

        <!-- Table -->
        <div class="cart-table">
          <table>
            <thead>
              <tr>
                <th>Sản phẩm</th>
                <th>Size</th>
                <th>Đơn giá</th>
                <th>Số lượng</th>
                <th>Thành tiền</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="entry" items="${CART_ITEMS}">
                <c:set var="item" value="${entry.value}"/>
                <tr>
                  <td>
                    <div style="display:flex;align-items:center;gap:14px;">
                      <div class="cart-item-img"
                           style="background-image:url('${item.img}');flex-shrink:0;"></div>
                      <div>
                        <div class="cart-item-name">${item.productName}</div>
                      </div>
                    </div>
                  </td>
                  <td><span class="badge" style="background:var(--c-bg);color:var(--c-text);border:1px solid var(--c-border);">${item.size}</span></td>
                  <td style="color:var(--c-accent);font-weight:500;">
                    <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> ₫
                  </td>
                  <td>
                    <form action="MainController" method="get" style="display:flex;align-items:center;gap:4px;">
                      <input type="hidden" name="action" value="UpdateCart">
                      <input type="hidden" name="key"    value="${item.key}">
                      <div class="qty-control" style="border-radius:6px;">
                        <button type="button" onclick="this.nextElementSibling.stepDown();this.form.submit()">−</button>
                        <input type="number" name="qty" value="${item.quantity}" min="0" max="10"
                               style="width:40px;" onchange="this.form.submit()">
                        <button type="button" onclick="this.previousElementSibling.stepUp();this.form.submit()">+</button>
                      </div>
                    </form>
                  </td>
                  <td style="font-weight:600;font-family:var(--font-display);">
                    <fmt:formatNumber value="${item.subTotal}" type="number" groupingUsed="true"/> ₫
                  </td>
                  <td>
                    <a href="MainController?action=RemoveFromCart&key=${item.key}"
                       class="btn btn-outline btn-sm"
                       onclick="return confirm('Xóa sản phẩm này?')"
                       style="color:var(--c-danger);border-color:var(--c-danger);">
                      <i class="fas fa-trash"></i>
                    </a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>

        <!-- Summary -->
        <div class="cart-summary">
          <h3>Tóm tắt đơn hàng</h3>
          <div class="summary-row">
            <span>Tạm tính</span>
            <span><fmt:formatNumber value="${CART_TOTAL}" type="number" groupingUsed="true"/> ₫</span>
          </div>
          <div class="summary-row">
            <span>Phí vận chuyển</span>
            <span style="color:var(--c-success);">Miễn phí</span>
          </div>
          <div class="summary-row total">
            <span>Tổng cộng</span>
            <span><fmt:formatNumber value="${CART_TOTAL}" type="number" groupingUsed="true"/> ₫</span>
          </div>
          <a href="MainController?action=Checkout" class="btn btn-primary btn-full" style="margin-top:20px;font-size:15px;padding:13px;">
            <i class="fas fa-credit-card"></i> Tiến hành đặt hàng
          </a>
          <a href="MainController?action=ShoppingPage" class="btn btn-outline btn-full" style="margin-top:10px;">
            ← Tiếp tục mua sắm
          </a>
        </div>

      </div>
    </c:otherwise>
  </c:choose>
</div>

<div class="footer"><div class="container"><div class="footer-bottom">© 2026 Fashion Shop.</div></div></div>
</body>
</html>
