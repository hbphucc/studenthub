<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Thanh Toán — Fashion Shop</title>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="container">
  <div style="padding:20px 0 8px;">
    <h2 style="font-family:var(--font-display);font-size:28px;">Thanh toán</h2>
  </div>

  <c:if test="${not empty ERROR}">
    <div class="alert alert-danger">${ERROR}</div>
  </c:if>

  <div class="checkout-layout">

    <form action="MainController" method="post" id="checkoutForm">
      <input type="hidden" name="action" value="PlaceOrder">

      <div class="form-card">
        <h3><i class="fas fa-map-marker-alt" style="color:var(--c-accent);margin-right:8px;"></i>Thông tin nhận hàng</h3>
        <div class="form-group">
          <label class="form-label">Họ và tên người nhận *</label>
          <input type="text" name="fullName" class="form-control"
                 value="${LOGIN_USER.fullName}" required placeholder="Nguyễn Văn A">
        </div>
        <div class="form-group">
          <label class="form-label">Số điện thoại *</label>
          <input type="tel" name="phone" class="form-control"
                 value="${LOGIN_USER.phone}" required placeholder="0901234567">
        </div>
        <div class="form-group">
          <label class="form-label">Địa chỉ giao hàng *</label>
          <textarea name="address" class="form-control" required
                    placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố">${LOGIN_USER.address}</textarea>
        </div>
        <div class="form-group">
          <label class="form-label">Ghi chú</label>
          <input type="text" name="note" class="form-control" placeholder="Ghi chú cho người giao hàng...">
        </div>
      </div>

      <div class="form-card" style="margin-top:20px;">
        <h3><i class="fas fa-wallet" style="color:var(--c-accent);margin-right:8px;"></i>Phương thức thanh toán</h3>
        <div class="payment-options">
          <div class="payment-option selected" id="opt-cod">
            <input type="radio" name="paymentMethod" id="cod" value="COD" checked onchange="selectPayment('COD')">
            <label for="cod"><i class="fas fa-money-bill-wave" style="color:var(--c-success);"></i> Thanh toán khi nhận hàng (COD)</label>
          </div>
          <div class="payment-option" id="opt-wallet">
            <input type="radio" name="paymentMethod" id="wallet" value="wallet" onchange="selectPayment('wallet')">
            <label for="wallet"><i class="fas fa-qrcode" style="color:var(--c-accent);"></i> Chuyển khoản ngân hàng (QR)</label>
          </div>
        </div>

        <div id="qr-panel" style="display:none;margin-top:20px;padding:20px;
             background:var(--c-surface2,#fafaf8);border:1.5px solid var(--c-border);
             border-radius:12px;text-align:center;">

          <p style="font-size:13px;color:var(--c-muted);margin-bottom:14px;">
            Quét mã QR để chuyển khoản. Đơn hàng sẽ được xử lý sau khi xác nhận thanh toán.
          </p>

          <%--
            Ảnh QR đặt tại: Web Pages/assets/images/qr-payment.png
            Đổi tên file cho đúng nếu bạn đặt tên khác
          --%>
          <img src="${pageContext.request.contextPath}/assets/images/qr-payment.png"
               alt="QR chuyển khoản"
               style="width:240px;height:240px;border-radius:12px;
                      border:1px solid var(--c-border);
                      display:block;margin:0 auto 16px;
                      object-fit:contain;background:#fff;"
               onerror="this.style.display='none';document.getElementById('qr-fallback').style.display='flex'">
          <div id="qr-fallback" style="display:none;width:240px;height:240px;
               border:2px dashed var(--c-border);border-radius:12px;
               align-items:center;justify-content:center;flex-direction:column;
               gap:8px;margin:0 auto 16px;color:var(--c-muted);font-size:13px;">
            <i class="fas fa-image" style="font-size:32px;opacity:.4;"></i>
            <span>Không tìm thấy ảnh QR</span>
            <span style="font-size:11px;">assets/images/qr-payment.png</span>
          </div>

          <div style="font-size:13px;line-height:2;text-align:left;
                      max-width:280px;margin:0 auto;">
            <div style="display:flex;justify-content:space-between;
                 padding:7px 0;border-bottom:1px solid var(--c-border);">
              <span style="color:var(--c-muted);">Ngân hàng</span>
              <strong>ACB</strong>
            </div>
            <div style="display:flex;justify-content:space-between;
                 padding:7px 0;border-bottom:1px solid var(--c-border);">
              <span style="color:var(--c-muted);">Số tài khoản</span>
              <strong style="letter-spacing:.5px;">21169847</strong>
            </div>
            <div style="display:flex;justify-content:space-between;
                 padding:7px 0;border-bottom:1px solid var(--c-border);">
              <span style="color:var(--c-muted);">Chủ tài khoản</span>
              <strong>FASHION SHOP</strong>
            </div>
            <div style="display:flex;justify-content:space-between;
                 padding:7px 0;border-bottom:1px solid var(--c-border);">
              <span style="color:var(--c-muted);">Số tiền</span>
              <strong style="color:var(--c-accent);font-size:15px;">
                <fmt:formatNumber value="${CART_TOTAL}" type="number" groupingUsed="true"/> ₫
              </strong>
            </div>
            <div style="display:flex;justify-content:space-between;padding:7px 0;">
              <span style="color:var(--c-muted);">Nội dung CK</span>
              <strong>Thanh toan Fashion Shop</strong>
            </div>
          </div>

          <div style="margin-top:14px;padding:10px 14px;background:var(--c-accent-lt,#f5ebe0);
               border-radius:8px;font-size:12.5px;color:var(--c-accent);
               border:1px solid #EDD8C4;display:flex;align-items:center;gap:8px;">
            <i class="fas fa-circle-info"></i>
            Vui lòng chuyển khoản đúng số tiền và nội dung để đơn hàng được xử lý nhanh.
          </div>

          <div style="margin-top:14px;padding:10px 14px;
               background:var(--c-success-lt,#edf7f2);border-radius:8px;
               font-size:12.5px;color:var(--c-success,#3a8c5c);
               border:1px solid #B5D9C5;display:flex;align-items:center;gap:8px;">
            <i class="fas fa-circle-check"></i>
            Sau khi chuyển khoản, nhấn <strong style="margin:0 3px;">"Xác nhận đặt hàng"</strong>.
            Admin sẽ kiểm tra và xử lý đơn hàng của bạn.
          </div>
        </div>
      </div>

    </form>

    <!-- Order Summary -->
    <div>
      <div class="cart-summary">
        <h3>Đơn hàng của bạn</h3>
        <c:forEach var="entry" items="${CART_ITEMS}">
          <c:set var="item" value="${entry.value}"/>
          <div style="display:flex;align-items:center;gap:12px;padding:10px 0;border-bottom:1px solid var(--c-border);">
            <div style="width:48px;height:60px;background-image:url('${item.img}');background-size:cover;background-position:center;border-radius:6px;flex-shrink:0;background-color:#F0EBE3;"></div>
            <div style="flex:1;">
              <div style="font-size:13px;font-weight:500;">${item.productName}</div>
              <div style="font-size:12px;color:var(--c-muted);">Size: ${item.size} × ${item.quantity}</div>
            </div>
            <div style="font-size:13px;font-weight:600;color:var(--c-accent);">
              <fmt:formatNumber value="${item.subTotal}" type="number" groupingUsed="true"/> ₫
            </div>
          </div>
        </c:forEach>
        <div class="summary-row" style="margin-top:8px;">
          <span>Vận chuyển</span><span style="color:var(--c-success);">Miễn phí</span>
        </div>
        <div class="summary-row total">
          <span>Tổng cộng</span>
          <span><fmt:formatNumber value="${CART_TOTAL}" type="number" groupingUsed="true"/> ₫</span>
        </div>
        <button type="submit" form="checkoutForm" id="submitBtn"
                class="btn btn-primary btn-full" style="margin-top:20px;font-size:15px;padding:14px;">
          <i class="fas fa-check-circle"></i> Xác nhận đặt hàng
        </button>
        <a href="MainController?action=ViewCart" class="btn btn-outline btn-full" style="margin-top:10px;">
          ← Quay lại giỏ hàng
        </a>
      </div>
    </div>

  </div>
</div>

<div class="footer"><div class="container"><div class="footer-bottom">© 2026 Fashion Shop.</div></div></div>

<script>
function selectPayment(method) {
  document.querySelectorAll('.payment-option').forEach(el => el.classList.remove('selected'));
  document.getElementById('opt-' + method.toLowerCase()).classList.add('selected');
  var qrPanel = document.getElementById('qr-panel');
  var submitBtn = document.getElementById('submitBtn');

  if (method === 'wallet') {
    qrPanel.style.display = 'block';
    setTimeout(function() {
      qrPanel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }, 100);

    submitBtn.disabled = false;
    submitBtn.style.opacity = '1';
    submitBtn.style.cursor = 'pointer';
    submitBtn.innerHTML = '<i class="fas fa-check-circle"></i> Xác nhận đặt hàng';
  } else {
    qrPanel.style.display = 'none';
    submitBtn.disabled = false;
    submitBtn.style.opacity = '1';
    submitBtn.style.cursor = 'pointer';
    submitBtn.innerHTML = '<i class="fas fa-check-circle"></i> Xác nhận đặt hàng';
  }
}

document.getElementById('opt-cod').classList.add('selected');
</script>

</body>
</html>
