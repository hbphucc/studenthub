<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Fashion Shop — Thời Trang Online</title>
</head>
<body>
<%
    if (request.getAttribute("LIST_PRODUCT") == null) {
        request.getRequestDispatcher("/MainController?action=ShoppingPage")
               .forward(request, response);
        return;
    }
%>
<jsp:include page="navbar.jsp"/>

<!-- Hero -->
<div class="hero">
  <div class="container">
    <h1>Thời Trang Hiện Đại</h1>
    <p>Khám phá bộ sưu tập mới nhất dành cho nam và nữ</p>
    <a href="MainController?action=ShoppingPage" class="btn btn-primary" style="font-size:16px;padding:14px 32px;">
      Mua Sắm Ngay
    </a>
  </div>
</div>

<!-- Products -->
<div class="section">
  <div class="container">

    <!-- Filter bar -->
    <div class="filter-bar">
      <strong style="align-self:center;font-size:13px;margin-right:4px;">Lọc:</strong>
      <a href="MainController?action=ShoppingPage"
         class="${empty CURRENT_GENDER && empty CURRENT_CATEGORY ? 'active' : ''}">Tất cả</a>
      <a href="MainController?action=FilterGender&gender=male"
         class="${CURRENT_GENDER == 'male' ? 'active' : ''}">Nam</a>
      <a href="MainController?action=FilterGender&gender=female"
         class="${CURRENT_GENDER == 'female' ? 'active' : ''}">Nữ</a>
      <a href="MainController?action=FilterGender&gender=unisex"
         class="${CURRENT_GENDER == 'unisex' ? 'active' : ''}">Unisex</a>
      <span style="color:var(--c-border);margin:0 4px;">|</span>
      <c:forEach var="cat" items="${LIST_CATEGORY}">
        <a href="MainController?action=FilterCategory&categoryID=${cat.categoryID}"
           class="${CURRENT_CATEGORY == cat.categoryID ? 'active' : ''}">${cat.categoryName}</a>
      </c:forEach>
    </div>

    <!-- Result info -->
    <c:if test="${not empty SEARCH_KEYWORD}">
      <p class="text-muted mb-2">Kết quả tìm kiếm cho: <strong>"${SEARCH_KEYWORD}"</strong></p>
    </c:if>

    <!-- Product Grid -->
    <c:choose>
      <c:when test="${empty LIST_PRODUCT}">
        <div style="text-align:center;padding:64px 0;color:var(--c-muted);">
          <div style="font-size:48px;margin-bottom:16px;">🔍</div>
          <h3>Không tìm thấy sản phẩm</h3>
          <p>Hãy thử tìm kiếm với từ khóa khác</p>
        </div>
      </c:when>
      <c:otherwise>
        <div class="product-grid">
          <c:forEach var="p" items="${LIST_PRODUCT}">
            <%-- Pre-build size→quantity JSON string for data attribute --%>
            <c:set var="sqJson">{</c:set>
            <c:forEach var="e" items="${p.sizeQuantities}" varStatus="vs">
              <c:set var="sqJson">${sqJson}"${e.key}":${e.value}<c:if test="${!vs.last}">,</c:if></c:set>
            </c:forEach>
            <c:set var="sqJson">${sqJson}}</c:set>

            <div class="product-card">
              <div class="product-card__img"
                   style="background-image:url('${fn:escapeXml(p.img)}')">
                <span class="product-card__badge">${p.categoryName}</span>

                <%-- Quick View hover trigger — carries all product data --%>
                <button type="button" class="qv-trigger"
                        data-id="${p.productID}"
                        data-name="${fn:escapeXml(p.productName)}"
                        data-img="${fn:escapeXml(p.img)}"
                        data-price="${p.formattedPrice}"
                        data-desc="${fn:escapeXml(p.description)}"
                        data-cat="${fn:escapeXml(p.categoryName)}"
                        data-gender="${p.gender}"
                        data-sizes='${sqJson}'
                        onclick="openQuickView(this)">
                  <i class="fas fa-eye"></i> Xem nhanh
                </button>
              </div>

              <div class="product-card__body">
                <div class="product-card__cat">${p.gender == 'male' ? 'Nam' : p.gender == 'female' ? 'Nữ' : 'Unisex'}</div>
                <div class="product-card__name">${p.productName}</div>
                <div class="product-card__price">
                  <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/> ₫
                </div>
              </div>

              <div class="product-card__actions">
                <a href="MainController?action=ViewDetail&id=${p.productID}"
                   class="btn btn-outline btn-sm">Chi tiết</a>
                <%-- "Thêm" now opens Quick View for size & qty selection --%>
                <button type="button" class="btn btn-primary btn-sm"
                        onclick="openQuickViewFromCard(this)">
                  <i class="fas fa-cart-plus"></i> Thêm
                </button>
              </div>
            </div>
          </c:forEach>
        </div>
      </c:otherwise>
    </c:choose>

  </div>
</div>

<!-- Footer -->
<div class="footer">
  <div class="container">
    <div class="footer-grid">
      <div>
        <h4>Fashion Shop</h4>
        <p style="font-size:13px;line-height:1.8;">Thời trang hiện đại, chất lượng cao cấp dành cho mọi phong cách.</p>
      </div>
      <div>
        <h4>Danh mục</h4>
        <ul>
          <li><a href="MainController?action=FilterGender&gender=male">Quần áo Nam</a></li>
          <li><a href="MainController?action=FilterGender&gender=female">Quần áo Nữ</a></li>
          <li><a href="MainController?action=FilterGender&gender=unisex">Unisex</a></li>
        </ul>
      </div>
      <div>
        <h4>Hỗ trợ</h4>
        <ul>
          <li>Hướng dẫn mua hàng</li>
          <li>Chính sách đổi trả</li>
          <li>Chăm sóc khách hàng</li>
        </ul>
      </div>
      <div>
        <h4>Liên hệ</h4>
        <p style="font-size:13px;line-height:2;">FPT University<br>Phone: 0889629278<br>Email: shop@fashionshop.vn</p>
      </div>
    </div>
    <div class="footer-bottom">© 2026 Fashion Shop. All rights reserved.</div>
  </div>
</div>

<div id="qvBackdrop" class="qv-backdrop" role="dialog" aria-modal="true" aria-labelledby="qvName">

  <div id="qvModal" class="qv-modal">

    <button class="qv-close" onclick="closeQuickView()" title="Đóng (Esc)">
      <i class="fas fa-times"></i>
    </button>
    <div id="qvImg" class="qv-modal__img"></div>

    <div class="qv-modal__body">

      <div id="qvCat"   class="qv-cat"></div>
      <div id="qvName"  class="qv-name"></div>
      <div id="qvPrice" class="qv-price"></div>
      <p   id="qvDesc"  class="qv-desc"></p>

      <!-- Size selection -->
      <span class="qv-form-label">Chọn size:</span>
      <div id="qvSizes" class="qv-sizes"></div>
      <p   id="qvStockInfo" class="qv-stock-info"></p>

      <!-- Quantity -->
      <span class="qv-form-label">Số lượng:</span>
      <div class="qty-control" style="margin-bottom:22px;">
        <button type="button" onclick="changeQvQty(-1)">−</button>
        <input  type="number"  id="qvQtyInput" value="1" min="1" max="1">
        <button type="button" onclick="changeQvQty(1)">+</button>
      </div>

      <!-- Hidden form — same convention as productDetail.jsp -->
      <form id="qvCartForm" action="MainController" method="get">
        <input type="hidden" name="action" value="AddToCart">
        <input type="hidden" name="id"     id="qvProductId"    value="">
        <input type="hidden" name="size"   id="qvSelectedSize" value="">
        <input type="hidden" name="qty"    id="qvQtyHidden"    value="1">
      </form>

      <!-- Footer buttons -->
      <div class="qv-footer">
        <button type="button" class="btn btn-primary" onclick="submitQvCart()">
          <i class="fas fa-cart-plus"></i> Thêm vào giỏ
        </button>
        <a id="qvDetailLink" href="#" class="btn btn-outline">
          <i class="fas fa-expand-alt"></i> Xem chi tiết
        </a>
      </div>

    </div>
  </div>
</div>


<script>
(function () {
  'use strict';

  const backdrop  = document.getElementById('qvBackdrop');

  /* ── Open ──────────────────────────────────────────────────── */
  window.openQuickView = function (trigger) {
    const d = trigger.dataset;

    // Parse size→quantity map from JSON data attribute
    let sizes = {};
    try { sizes = JSON.parse(d.sizes || '{}'); } catch (e) { sizes = {}; }

    // ── Populate static fields ──
    document.getElementById('qvImg').style.backgroundImage = "url('" + d.img + "')";
    document.getElementById('qvCat').textContent    = d.cat  || '';
    document.getElementById('qvName').textContent   = d.name || '';
    document.getElementById('qvPrice').textContent  = d.price || '';
    document.getElementById('qvDesc').textContent   = d.desc  || '';
    document.getElementById('qvProductId').value    = d.id    || '';
    document.getElementById('qvDetailLink').href    =
      'MainController?action=ViewDetail&id=' + (d.id || '');

    // ── Reset interactive fields ──
    document.getElementById('qvSelectedSize').value = '';
    document.getElementById('qvQtyInput').value     = 1;
    document.getElementById('qvQtyInput').max       = 1;
    document.getElementById('qvQtyHidden').value    = 1;
    document.getElementById('qvStockInfo').textContent = '';

    // ── Build size buttons ──
    const sizesEl  = document.getElementById('qvSizes');
    const sizeKeys = Object.keys(sizes);
    sizesEl.innerHTML = '';

    if (sizeKeys.length === 0) {
      sizesEl.innerHTML =
        '<p style="color:var(--c-danger);font-size:13px;font-weight:500;">' +
        '<i class="fas fa-exclamation-circle" style="margin-right:5px;"></i>Tạm hết hàng</p>';
    } else {
      sizeKeys.forEach(function (sz) {
        const qty = sizes[sz];
        const btn = document.createElement('button');
        btn.type        = 'button';
        btn.className   = 'qv-size-btn' + (qty <= 0 ? ' out-of-stock' : '');
        btn.textContent = sz;
        btn.dataset.size = sz;
        btn.dataset.qty  = qty;
        btn.title        = qty > 0 ? sz + ': còn ' + qty + ' sp' : sz + ': hết hàng';
        if (qty > 0) {
            btn.addEventListener('click', function () { window.selectQvSize(this); });
}
        sizesEl.appendChild(btn);
      });
    }

    // ── Show modal with animation ──
    backdrop.classList.add('is-open');
    document.body.style.overflow = 'hidden';
  };

  /* ── Open via card action button (finds sibling .qv-trigger) ── */
  window.openQuickViewFromCard = function (btn) {
    const trigger = btn.closest('.product-card').querySelector('.qv-trigger');
    if (trigger) openQuickView(trigger);
  };

  /* ── Close ─────────────────────────────────────────────────── */
  window.closeQuickView = function () {
    backdrop.classList.remove('is-open');
    document.body.style.overflow = '';
  };

  /* ── Select size ───────────────────────────────────────────── */
  window.selectQvSize = function (btn) {
    document.querySelectorAll('.qv-size-btn').forEach(function (b) {
      b.classList.remove('selected');
    });
    btn.classList.add('selected');

    const size      = btn.dataset.size;
    const qty       = parseInt(btn.dataset.qty, 10) || 0;
    const stockInfo = document.getElementById('qvStockInfo');
    const qtyInput  = document.getElementById('qvQtyInput');

    document.getElementById('qvSelectedSize').value = size;

    if (qty > 0) {
      stockInfo.textContent = '✓ Còn lại: ' + qty + ' sản phẩm';
      stockInfo.style.color = 'var(--c-success)';
    } else {
      stockInfo.textContent = 'Size này tạm hết hàng';
      stockInfo.style.color = 'var(--c-danger)';
    }

    qtyInput.max   = qty > 0 ? qty : 1;
    qtyInput.value = 1;
    document.getElementById('qvQtyHidden').value = 1;
  };

  /* ── Change quantity ───────────────────────────────────────── */
  window.changeQvQty = function (delta) {
    const input  = document.getElementById('qvQtyInput');
    const maxQty = parseInt(input.max, 10) || 1;
    const newVal = Math.max(1, Math.min(maxQty, parseInt(input.value, 10) + delta));
    input.value = newVal;
    document.getElementById('qvQtyHidden').value = newVal;
  };

  /* ── Submit cart form ──────────────────────────────────────── */
  window.submitQvCart = function () {
    const size = document.getElementById('qvSelectedSize').value;
    if (!size) {
      // Pulse the size section to draw attention
      const sizesEl = document.getElementById('qvSizes');
      sizesEl.style.outline = '2px solid var(--c-danger)';
      sizesEl.style.borderRadius = '8px';
      setTimeout(function () { sizesEl.style.outline = ''; }, 1200);
      document.getElementById('qvStockInfo').textContent = 'Vui lòng chọn size!';
      document.getElementById('qvStockInfo').style.color = 'var(--c-danger)';
      return;
    }
    // Sync qty hidden input before submit
    document.getElementById('qvQtyHidden').value =
      document.getElementById('qvQtyInput').value;
    document.getElementById('qvCartForm').submit();
  };

  /* ── Close on backdrop click ───────────────────────────────── */
  backdrop.addEventListener('click', function (e) {
    if (e.target === backdrop) closeQuickView();
  });

  /* ── Close on Escape key ───────────────────────────────────── */
  document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape' && backdrop.classList.contains('is-open')) {
      closeQuickView();
    }
  });

}());
</script>

</body>
</html>
