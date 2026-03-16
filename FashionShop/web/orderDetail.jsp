<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Chi Tiết Đơn Hàng — Fashion Shop</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container section">

  <c:if test="${param.success == '1'}">
    <div class="alert alert-success" style="font-size:15px;padding:16px 20px;">
      <i class="fas fa-check-circle" style="font-size:18px;"></i>
      <strong>Đặt hàng thành công!</strong> Cảm ơn bạn đã mua sắm tại Fashion Shop.
    </div>
  </c:if>

  <div class="section-header">
    <h2 class="section-title">Chi tiết đơn hàng</h2>
    <a href="MainController?action=OrderHistory" class="btn btn-outline btn-sm">← Lịch sử đơn hàng</a>
  </div>

  <c:if test="${not empty ORDER}">
    <div style="display:grid;grid-template-columns:1fr 340px;gap:24px;">

      <!-- Products -->
      <div>
        <div class="form-card">
          <h3>Sản phẩm đã đặt</h3>
          <table style="width:100%;border-collapse:collapse;">
            <thead>
              <tr style="background:var(--c-bg);">
                <th style="padding:12px;text-align:left;font-size:12px;text-transform:uppercase;letter-spacing:.5px;color:var(--c-muted);">Sản phẩm</th>
                <th style="padding:12px;text-align:center;font-size:12px;text-transform:uppercase;letter-spacing:.5px;color:var(--c-muted);">Size</th>
                <th style="padding:12px;text-align:center;font-size:12px;text-transform:uppercase;letter-spacing:.5px;color:var(--c-muted);">SL</th>
                <th style="padding:12px;text-align:right;font-size:12px;text-transform:uppercase;letter-spacing:.5px;color:var(--c-muted);">Thành tiền</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="d" items="${ORDER.details}">
                <tr style="border-top:1px solid var(--c-border);">
                  <td style="padding:14px 12px;">${d.productName}</td>
                  <td style="padding:14px 12px;text-align:center;"><span class="badge" style="background:var(--c-bg);color:var(--c-text);border:1px solid var(--c-border);">${d.size}</span></td>
                  <td style="padding:14px 12px;text-align:center;">${d.quantity}</td>
                  <td style="padding:14px 12px;text-align:right;font-weight:600;color:var(--c-accent);">${d.formattedSubTotal}</td>
                </tr>
              </c:forEach>
            </tbody>
            <tfoot>
              <tr style="border-top:2px solid var(--c-border);">
                <td colspan="3" style="padding:14px 12px;font-weight:600;text-align:right;">Tổng cộng:</td>
                <td style="padding:14px 12px;text-align:right;font-family:var(--font-display);font-size:18px;color:var(--c-accent);font-weight:700;">
                  <fmt:formatNumber value="${ORDER.totalAmount}" type="number" groupingUsed="true"/> ₫
                </td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>

      <!-- Order info -->
      <div>
        <div class="form-card">
          <h3>Thông tin đơn hàng</h3>
          <div style="display:flex;flex-direction:column;gap:12px;font-size:14px;">
            <div style="display:flex;justify-content:space-between;">
              <span style="color:var(--c-muted);">Mã đơn</span>
              <strong style="font-family:monospace;">#${ORDER.orderID.substring(0,8).toUpperCase()}</strong>
            </div>
            <div style="display:flex;justify-content:space-between;">
              <span style="color:var(--c-muted);">Trạng thái</span>
              <span class="badge badge-${ORDER.status}">${ORDER.statusLabel}</span>
            </div>
            <div style="display:flex;justify-content:space-between;">
              <span style="color:var(--c-muted);">Thanh toán</span>
              <strong>${ORDER.paymentMethod}</strong>
            </div>
            <hr style="border:none;border-top:1px solid var(--c-border);">
            <div><span style="color:var(--c-muted);">Người nhận:</span> <strong>${ORDER.fullName}</strong></div>
            <div><span style="color:var(--c-muted);">Điện thoại:</span> ${ORDER.phone}</div>
            <div><span style="color:var(--c-muted);">Địa chỉ:</span> ${ORDER.address}</div>
            <c:if test="${not empty ORDER.note}">
              <div><span style="color:var(--c-muted);">Ghi chú:</span> ${ORDER.note}</div>
            </c:if>
          </div>
        </div>
      </div>

    </div>
  </c:if>
</div>

<div class="footer"><div class="container"><div class="footer-bottom">© 2026 Fashion Shop.</div></div></div>
</body>
</html>
