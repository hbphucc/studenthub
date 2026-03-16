<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>Chi tiết đơn hàng — Admin</title></head>
<body>
<div class="admin-layout">
  <jsp:include page="adminNav.jsp"/>
  <div class="admin-content">

    <div class="admin-header">
      <h1>Chi tiết đơn hàng</h1>
      <a href="AdminController?action=OrderList" class="btn btn-outline btn-sm">← Danh sách đơn hàng</a>
    </div>

    <c:if test="${param.msg == 'updated'}"><div class="alert alert-success">Cập nhật trạng thái thành công!</div></c:if>

    <c:if test="${not empty ORDER}">
      <div style="display:grid;grid-template-columns:1fr 320px;gap:24px;">

        <div class="form-card">
          <h3>Sản phẩm</h3>
          <table style="width:100%;border-collapse:collapse;margin-top:16px;">
            <thead>
              <tr style="background:var(--c-bg);">
                <th style="padding:10px;text-align:left;font-size:12px;color:var(--c-muted);text-transform:uppercase;">Sản phẩm</th>
                <th style="padding:10px;text-align:center;font-size:12px;color:var(--c-muted);text-transform:uppercase;">Size</th>
                <th style="padding:10px;text-align:center;font-size:12px;color:var(--c-muted);text-transform:uppercase;">SL</th>
                <th style="padding:10px;text-align:right;font-size:12px;color:var(--c-muted);text-transform:uppercase;">Đơn giá</th>
                <th style="padding:10px;text-align:right;font-size:12px;color:var(--c-muted);text-transform:uppercase;">Thành tiền</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="d" items="${ORDER.details}">
                <tr style="border-top:1px solid var(--c-border);">
                  <td style="padding:12px 10px;">${d.productName}</td>
                  <td style="padding:12px 10px;text-align:center;">${d.size}</td>
                  <td style="padding:12px 10px;text-align:center;">${d.quantity}</td>
                  <td style="padding:12px 10px;text-align:right;">${d.formattedPrice}</td>
                  <td style="padding:12px 10px;text-align:right;font-weight:600;color:var(--c-accent);">${d.formattedSubTotal}</td>
                </tr>
              </c:forEach>
            </tbody>
            <tfoot>
              <tr style="border-top:2px solid var(--c-border);">
                <td colspan="4" style="padding:12px 10px;font-weight:600;text-align:right;">Tổng:</td>
                <td style="padding:12px 10px;text-align:right;font-family:var(--font-display);font-size:18px;color:var(--c-accent);font-weight:700;">
                  <fmt:formatNumber value="${ORDER.totalAmount}" type="number" groupingUsed="true"/> ₫
                </td>
              </tr>
            </tfoot>
          </table>
        </div>

        <div style="display:flex;flex-direction:column;gap:20px;">

          <div class="form-card">
            <h3>Thông tin đơn hàng</h3>
            <div style="font-size:14px;display:flex;flex-direction:column;gap:10px;margin-top:12px;">
              <div><span style="color:var(--c-muted);">Mã đơn:</span> <strong style="font-family:monospace;">#${ORDER.orderID.substring(0,8).toUpperCase()}</strong></div>
              <div><span style="color:var(--c-muted);">Khách hàng:</span> <strong>${ORDER.userFullName}</strong></div>
              <div><span style="color:var(--c-muted);">Điện thoại:</span> ${ORDER.phone}</div>
              <div><span style="color:var(--c-muted);">Địa chỉ:</span> ${ORDER.address}</div>
              <div><span style="color:var(--c-muted);">Thanh toán:</span> <strong>${ORDER.paymentMethod}</strong></div>
              <c:if test="${not empty ORDER.note}">
                <div><span style="color:var(--c-muted);">Ghi chú:</span> ${ORDER.note}</div>
              </c:if>
              <div style="padding-top:8px;">
                <span style="color:var(--c-muted);">Trạng thái hiện tại:</span><br>
                <span class="badge badge-${ORDER.status}" style="margin-top:6px;font-size:13px;">${ORDER.statusLabel}</span>
              </div>
            </div>
          </div>

          <div class="form-card">
            <h3>Cập nhật trạng thái</h3>
            <form action="${pageContext.request.contextPath}/AdminController" method="post" style="margin-top:12px;">
              <input type="hidden" name="action"  value="UpdateOrderStatus">
              <input type="hidden" name="orderID" value="${ORDER.orderID}">
              <div class="form-group">
                <select name="status" class="form-control">
                  <option value="pending"    ${ORDER.status == 'pending'    ? 'selected' : ''}>Chờ xử lý</option>
                  <option value="processing" ${ORDER.status == 'processing' ? 'selected' : ''}>Đang xử lý</option>
                  <option value="shipped"    ${ORDER.status == 'shipped'    ? 'selected' : ''}>Đang giao</option>
                  <option value="delivered"  ${ORDER.status == 'delivered'  ? 'selected' : ''}>Đã giao</option>
                  <option value="cancelled"  ${ORDER.status == 'cancelled'  ? 'selected' : ''}>Đã huỷ</option>
                </select>
              </div>
              <button type="submit" class="btn btn-primary btn-full">
                <i class="fas fa-sync"></i> Cập nhật trạng thái
              </button>
            </form>
          </div>

        </div>
      </div>
    </c:if>

  </div>
</div>
</body>
</html>
