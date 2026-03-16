<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Quản lý sản phẩm — Admin</title>
  <style>
    .backdrop {
      position:fixed;inset:0;background:rgba(12,10,7,.52);
      backdrop-filter:blur(5px);-webkit-backdrop-filter:blur(5px);
      z-index:1000;display:flex;align-items:center;justify-content:center;
      padding:20px;opacity:0;pointer-events:none;transition:opacity .22s ease;
    }
    .backdrop.open{opacity:1;pointer-events:all;}
    .modal{background:var(--c-surface,#fff);border-radius:20px;
      box-shadow:0 28px 72px rgba(0,0,0,.18),0 8px 24px rgba(0,0,0,.08);
      width:100%;max-height:90vh;overflow-y:auto;
      transform:translateY(18px) scale(.975);
      transition:transform .26s cubic-bezier(.34,1.45,.64,1);}
    .backdrop.open .modal{transform:translateY(0) scale(1);}
    .modal-sm{max-width:420px;}.modal-lg{max-width:720px;}
    .modal::-webkit-scrollbar{width:4px;}
    .modal::-webkit-scrollbar-thumb{background:var(--c-border-dk,#ccc);border-radius:10px;}
    .modal-head{display:flex;align-items:center;justify-content:space-between;
      padding:20px 26px 16px;border-bottom:1px solid var(--c-border,#eee);
      position:sticky;top:0;background:var(--c-surface,#fff);z-index:5;border-radius:20px 20px 0 0;}
    .modal-title{display:flex;align-items:center;gap:10px;
      font-family:var(--font-display,serif);font-size:18px;font-weight:600;color:var(--c-text,#111);}
    .title-ico{width:32px;height:32px;border-radius:8px;display:flex;align-items:center;justify-content:center;font-size:13px;}
    .modal-x{width:32px;height:32px;border-radius:8px;border:1.5px solid var(--c-border,#eee);
      background:transparent;color:var(--c-muted,#888);cursor:pointer;font-size:14px;
      display:flex;align-items:center;justify-content:center;transition:all .14s;}
    .modal-x:hover{background:var(--c-bg,#f5f5f5);color:var(--c-text,#111);}
    .modal-body{padding:22px 26px;}
    .modal-foot{padding:14px 26px 22px;border-top:1px solid var(--c-border,#eee);
      display:flex;align-items:center;justify-content:flex-end;gap:10px;}

    .view-layout{display:grid;grid-template-columns:160px 1fr;gap:22px;}
    .view-img{width:100%;aspect-ratio:3/4;border-radius:12px;background-size:cover;
      background-position:center;background-color:var(--c-accent-lt,#f5ebe0);border:1px solid var(--c-border,#eee);}
    .info-block{display:flex;flex-direction:column;gap:14px;}
    .info-row{display:flex;flex-direction:column;gap:3px;}
    .info-lbl{font-size:10px;font-weight:700;text-transform:uppercase;letter-spacing:.8px;color:var(--c-muted,#888);}
    .info-val{font-size:14px;color:var(--c-text,#111);}
    .info-val.big{font-family:var(--font-display,serif);font-size:22px;color:var(--c-accent,#c8956c);font-weight:600;}
    .info-val.sm{font-size:13px;color:var(--c-muted,#888);line-height:1.65;}
    .info-2col{display:grid;grid-template-columns:1fr 1fr;gap:12px;}
    .sz-grid{display:grid;grid-template-columns:repeat(5,1fr);gap:7px;margin-top:3px;}
    .sz-box{border:1.5px solid var(--c-border,#eee);border-radius:9px;padding:9px 4px;text-align:center;background:var(--c-surface2,#fafaf8);}
    .sz-box .lbl{font-size:10px;font-weight:700;color:var(--c-muted,#888);letter-spacing:.5px;}
    .sz-box .qty{font-family:var(--font-display,serif);font-size:18px;font-weight:600;line-height:1.3;color:var(--c-text,#111);}
    .sz-box.ok{border-color:#A8D5B8;background:var(--c-success-lt,#edf7f2);}
    .sz-box.ok .qty{color:var(--c-success,#3a8c5c);}
    .sz-box.out{border-color:#EBC0C0;background:var(--c-danger-lt,#fdf0f0);}
    .sz-box.out .qty{color:var(--c-danger,#c94040);}

    .edit-grid{display:grid;grid-template-columns:1fr 1fr;gap:14px;}
    .edit-grid .full{grid-column:1/-1;}
    .fg{display:flex;flex-direction:column;gap:4px;}
    .fl{font-size:11px;font-weight:700;text-transform:uppercase;letter-spacing:.6px;color:var(--c-muted,#888);}
    .fl .req{color:var(--c-danger,#c94040);}
    .sz-inputs{display:grid;grid-template-columns:repeat(5,1fr);gap:8px;}
    .sz-inp-g{display:flex;flex-direction:column;align-items:center;gap:5px;}
    .sz-inp-g .sz-lbl{font-size:10px;font-weight:800;color:var(--c-muted,#888);
      background:var(--c-bg,#f5f2ed);border:1.5px solid var(--c-border,#eee);
      width:30px;height:30px;border-radius:6px;display:flex;align-items:center;justify-content:center;}
    .sz-inp-g input{width:100%;padding:7px 4px;border:1.5px solid var(--c-border,#eee);
      border-radius:7px;font-size:14px;font-weight:600;text-align:center;
      color:var(--c-text,#111);outline:none;transition:border-color .15s;}
    .sz-inp-g input:focus{border-color:var(--c-accent,#c8956c);box-shadow:0 0 0 3px rgba(200,149,108,.1);}

    .del-body{text-align:center;padding:6px 0;}
    .del-ico{width:60px;height:60px;border-radius:18px;background:var(--c-danger-lt,#fdf0f0);
      border:2px solid #EBC0C0;display:flex;align-items:center;justify-content:center;
      margin:0 auto 14px;font-size:24px;color:var(--c-danger,#c94040);}
    .del-body h3{font-family:var(--font-display,serif);font-size:18px;margin-bottom:8px;}
    .del-body p{font-size:13px;color:var(--c-muted,#888);line-height:1.65;max-width:280px;margin:0 auto;}
    .del-name{font-weight:700;color:var(--c-danger,#c94040);font-style:italic;}
    .del-warn{margin-top:12px;padding:9px 13px;background:var(--c-danger-lt,#fdf0f0);
      border:1px solid #EBC0C0;border-radius:8px;font-size:12px;color:var(--c-danger,#c94040);
      display:flex;align-items:center;gap:7px;}

    .act-btn{width:32px;height:32px;border-radius:8px;border:1.5px solid var(--c-border,#eee);
      background:var(--c-surface,#fff);color:var(--c-muted,#888);cursor:pointer;
      display:inline-flex;align-items:center;justify-content:center;
      font-size:12.5px;transition:all .16s;position:relative;}
    .act-btn::after{content:attr(data-tip);position:absolute;bottom:calc(100% + 7px);left:50%;
      transform:translateX(-50%);background:#1C1815;color:#fff;font-size:11px;
      padding:3px 8px;border-radius:5px;white-space:nowrap;pointer-events:none;
      opacity:0;transition:opacity .14s;font-family:var(--font-body,sans-serif);}
    .act-btn::before{content:'';position:absolute;bottom:calc(100% + 3px);left:50%;
      transform:translateX(-50%);border:4px solid transparent;border-top-color:#1C1815;
      pointer-events:none;opacity:0;transition:opacity .14s;}
    .act-btn:hover::after,.act-btn:hover::before{opacity:1;}
    .act-btn:hover{transform:translateY(-1px);box-shadow:0 2px 8px rgba(0,0,0,.1);}
    .act-btn.v:hover{color:#2563EB;border-color:#2563EB;background:#EFF4FF;}
    .act-btn.e:hover{color:var(--c-accent,#c8956c);border-color:var(--c-accent,#c8956c);background:var(--c-accent-lt,#f5ebe0);}
    .act-btn.d:hover{color:var(--c-danger,#c94040);border-color:var(--c-danger,#c94040);background:var(--c-danger-lt,#fdf0f0);}

    .tbl-toolbar{display:flex;align-items:center;gap:10px;margin-bottom:16px;flex-wrap:wrap;}
    .tbl-search{position:relative;flex:1;max-width:280px;}
    .tbl-search i{position:absolute;left:11px;top:50%;transform:translateY(-50%);color:var(--c-muted,#888);font-size:12px;pointer-events:none;}
    .tbl-search input{width:100%;padding:8px 12px 8px 32px;border:1.5px solid var(--c-border,#eee);
      border-radius:8px;font-family:var(--font-body,sans-serif);font-size:13px;
      background:var(--c-surface,#fff);color:var(--c-text,#111);outline:none;transition:border-color .16s;}
    .tbl-search input:focus{border-color:var(--c-accent,#c8956c);}
    .tbl-sel{padding:8px 12px;border:1.5px solid var(--c-border,#eee);border-radius:8px;
      font-family:var(--font-body,sans-serif);font-size:13px;
      background:var(--c-surface,#fff);color:var(--c-text,#111);outline:none;cursor:pointer;}
    .tbl-sel:focus{border-color:var(--c-accent,#c8956c);}
    .vis-info{margin-left:auto;font-size:12px;color:var(--c-muted,#888);}
    @keyframes spin{to{transform:rotate(360deg);}}
    .spin{width:15px;height:15px;border:2px solid rgba(255,255,255,.3);border-top-color:#fff;
      border-radius:50%;animation:spin .65s linear infinite;display:none;}
    .loading .spin{display:inline-block;}.loading .lbl{display:none;}
  </style>
</head>
<body>
<div class="admin-layout">
  <jsp:include page="adminNav.jsp"/>
  <div class="admin-content">

    <div class="admin-header">
      <h1>Quản lý sản phẩm</h1>
      <a href="AdminController?action=AddProductForm" class="btn btn-primary">
        <i class="fas fa-plus"></i> Thêm sản phẩm
      </a>
    </div>

    <c:if test="${param.msg == 'added'}">  <div class="alert alert-success"><i class="fas fa-check-circle"></i> Thêm sản phẩm thành công!</div></c:if>
    <c:if test="${param.msg == 'updated'}"><div class="alert alert-success"><i class="fas fa-check-circle"></i> Cập nhật thành công!</div></c:if>
    <c:if test="${param.msg == 'deleted'}"><div class="alert alert-success"><i class="fas fa-eye-slash"></i> Đã ẩn sản phẩm.</div></c:if>

    <div class="tbl-toolbar">
      <div class="tbl-search">
        <i class="fas fa-search"></i>
        <input type="text" id="srch" placeholder="Tìm tên sản phẩm…">
      </div>
      <select id="stF" class="tbl-sel">
        <option value="">Tất cả trạng thái</option>
        <option value="active">Đang bán</option>
        <option value="hidden">Đã ẩn</option>
      </select>
      <select id="catF" class="tbl-sel"><option value="">Tất cả danh mục</option></select>
      <button class="btn btn-outline btn-sm" onclick="resetF()"><i class="fas fa-rotate-left"></i> Đặt lại</button>
      <span class="vis-info">Hiển thị <strong id="visCnt">0</strong> sản phẩm</span>
    </div>

    <table class="admin-table">
      <thead>
        <tr>
          <th>Ảnh</th><th>Tên sản phẩm</th><th>Danh mục</th>
          <th>Giá</th><th>Tồn kho</th><th>Trạng thái</th>
          <th style="text-align:center;">Thao tác</th>
        </tr>
      </thead>
      <tbody id="tbody">
        <c:choose>
          <c:when test="${empty PRODUCT_LIST}">
            <tr><td colspan="7" style="text-align:center;padding:48px;color:var(--c-muted);">
              <i class="fas fa-box-open" style="font-size:32px;display:block;margin-bottom:10px;opacity:.35;"></i>
              Chưa có sản phẩm nào
            </td></tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="p" items="${PRODUCT_LIST}">
              <c:set var="szStr" value=""/>
              <c:forEach var="e" items="${p.sizeQuantities}" varStatus="s">
                <c:set var="szStr" value="${szStr}${s.first?'':','}${e.key}:${e.value}"/>
              </c:forEach>
              <c:set var="tot" value="0"/>
              <c:forEach var="e" items="${p.sizeQuantities}">
                <c:set var="tot" value="${tot + e.value}"/>
              </c:forEach>
              <tr class="prow"
                  data-id="${p.productID}"
                  data-name="${fn:escapeXml(p.productName)}"
                  data-cat="${fn:escapeXml(p.categoryName)}"
                  data-catid="${p.categoryID}"
                  data-gender="${p.gender}"
                  data-price="${p.price}"
                  data-img="${fn:escapeXml(p.img)}"
                  data-desc="${fn:escapeXml(p.description)}"
                  data-sizes="${szStr}"
                  data-stock="${tot}"
                  data-status="${p.status ? 'active' : 'hidden'}">
                <td>
                  <div style="width:46px;height:56px;border-radius:8px;background-image:url('${p.img}');
                       background-size:cover;background-position:center;
                       background-color:var(--c-accent-lt);border:1px solid var(--c-border);"></div>
                </td>
                <td>
                  <strong>${fn:escapeXml(p.productName)}</strong>
                  <div style="font-size:11.5px;color:var(--c-muted);margin-top:2px;">
                    ${p.gender=='male'?'Nam':p.gender=='female'?'Nữ':'Unisex'}
                  </div>
                </td>
                <td><span class="badge" style="background:var(--c-bg);color:var(--c-text);border:1px solid var(--c-border);">${fn:escapeXml(p.categoryName)}</span></td>
                <td style="color:var(--c-accent);font-weight:600;">
                  <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>₫
                </td>
                <td>
                  <span style="font-weight:600;">${tot}</span>
                  <div style="width:50px;height:4px;background:var(--c-border);border-radius:2px;margin-top:4px;">
                    <div style="height:100%;border-radius:2px;width:${tot>100?100:tot}%;
                         background:${tot>20?'var(--c-success)':tot>0?'var(--c-warn)':'var(--c-danger)'};"></div>
                  </div>
                </td>
                <td>
                  <c:choose>
                    <c:when test="${p.status}"><span class="badge badge-delivered">Đang bán</span></c:when>
                    <c:otherwise><span class="badge badge-cancelled">Đã ẩn</span></c:otherwise>
                  </c:choose>
                </td>
                <td style="text-align:center;">
                  <div class="actions">
                    <button class="act-btn v" data-tip="Xem chi tiết" onclick="openView(this.closest('tr'))"><i class="fas fa-eye"></i></button>
                    <button class="act-btn e" data-tip="Chỉnh sửa"   onclick="openEdit(this.closest('tr'))"><i class="fas fa-pen"></i></button>
                    <button class="act-btn d" data-tip="Ẩn sản phẩm" onclick="openDel(this.closest('tr'))"><i class="fas fa-trash-can"></i></button>
                  </div>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>
</div>

<%-- MODAL XEM --%>
<div class="backdrop" id="mView" onclick="bdClick(event,'mView')">
  <div class="modal modal-lg">
    <div class="modal-head">
      <div class="modal-title"><div class="title-ico" style="background:#EFF4FF;color:#2563EB;"><i class="fas fa-eye"></i></div>Chi tiết sản phẩm</div>
      <button class="modal-x" onclick="close('mView')"><i class="fas fa-xmark"></i></button>
    </div>
    <div class="modal-body">
      <div class="view-layout">
        <div><div class="view-img" id="vImg"></div></div>
        <div class="info-block">
          <div class="info-row"><div class="info-lbl">Tên sản phẩm</div><div class="info-val" id="vName" style="font-size:17px;font-weight:600;"></div></div>
          <div class="info-2col">
            <div class="info-row"><div class="info-lbl">Danh mục</div><div class="info-val" id="vCat"></div></div>
            <div class="info-row"><div class="info-lbl">Giới tính</div><div class="info-val" id="vGender"></div></div>
          </div>
          <div class="info-row"><div class="info-lbl">Giá bán</div><div class="info-val big" id="vPrice"></div></div>
          <div class="info-row"><div class="info-lbl">Mô tả</div><div class="info-val sm" id="vDesc"></div></div>
          <div class="info-row"><div class="info-lbl">Tồn kho theo size</div><div class="sz-grid" id="vSz"></div></div>
        </div>
      </div>
    </div>
    <div class="modal-foot">
      <button class="btn btn-outline" onclick="close('mView')">Đóng</button>
      <button class="btn btn-accent" onclick="switchToEdit()"><i class="fas fa-pen"></i> Chỉnh sửa</button>
    </div>
  </div>
</div>

<%-- MODAL SỬA --%>
<div class="backdrop" id="mEdit" onclick="bdClick(event,'mEdit')">
  <div class="modal modal-lg">
    <div class="modal-head">
      <div class="modal-title"><div class="title-ico" style="background:var(--c-accent-lt);color:var(--c-accent);"><i class="fas fa-pen"></i></div>Chỉnh sửa sản phẩm</div>
      <button class="modal-x" onclick="close('mEdit')"><i class="fas fa-xmark"></i></button>
    </div>
    <div class="modal-body">
      <form action="AdminController" method="post" id="editForm">
        <input type="hidden" name="action" value="UpdateProduct">
        <input type="hidden" name="productID" id="eID">
        <div class="edit-grid">
          <div class="fg full"><label class="fl">Tên sản phẩm <span class="req">*</span></label><input type="text" name="productName" id="eName" class="form-control" required></div>
          <div class="fg"><label class="fl">Danh mục <span class="req">*</span></label>
            <select name="categoryID" id="eCat" class="form-control" required>
              <c:forEach var="cat" items="${CATEGORY_LIST}">
                <option value="${cat.categoryID}">${fn:escapeXml(cat.categoryName)} (${cat.gender=='male'?'Nam':cat.gender=='female'?'Nữ':'Unisex'})</option>
              </c:forEach>
            </select>
          </div>
          <div class="fg"><label class="fl">Giá bán (₫) <span class="req">*</span></label><input type="number" name="price" id="ePrice" class="form-control" min="0" step="1000" required></div>
          <div class="fg full"><label class="fl">URL ảnh</label><input type="text" name="img" id="eImg" class="form-control" placeholder="https://…"></div>
          <div class="fg full"><label class="fl">Mô tả</label><textarea name="description" id="eDesc" class="form-control" rows="3"></textarea></div>
          <div class="fg full">
            <label class="fl" style="margin-bottom:8px;">Tồn kho theo size <span class="req">*</span></label>
            <div class="sz-inputs">
              <c:forEach var="sz" items="${['S','M','L','XL','XXL']}">
                <div class="sz-inp-g"><div class="sz-lbl">${sz}</div><input type="number" name="stock_${sz}" id="eS_${sz}" min="0" value="0"></div>
              </c:forEach>
            </div>
          </div>
        </div>
      </form>
    </div>
    <div class="modal-foot">
      <button class="btn btn-outline" onclick="close('mEdit')">Hủy bỏ</button>
      <button class="btn btn-accent" id="eSave" onclick="submitEdit()"><span class="lbl"><i class="fas fa-floppy-disk"></i> Lưu</span><div class="spin"></div></button>
    </div>
  </div>
</div>

<%-- MODAL XÓA --%>
<div class="backdrop" id="mDel" onclick="bdClick(event,'mDel')">
  <div class="modal modal-sm">
    <div class="modal-head">
      <div class="modal-title"><div class="title-ico" style="background:var(--c-danger-lt);color:var(--c-danger);"><i class="fas fa-triangle-exclamation"></i></div>Xác nhận ẩn</div>
      <button class="modal-x" onclick="close('mDel')"><i class="fas fa-xmark"></i></button>
    </div>
    <div class="modal-body">
      <div class="del-body">
        <div class="del-ico"><i class="fas fa-trash-can"></i></div>
        <h3>Ẩn sản phẩm này?</h3>
        <p>Sản phẩm "<span class="del-name" id="dName"></span>" sẽ bị ẩn khỏi cửa hàng.</p>
        <div class="del-warn"><i class="fas fa-circle-exclamation"></i> Khách hàng sẽ không thể tìm thấy sản phẩm này.</div>
      </div>
    </div>
    <div class="modal-foot" style="justify-content:center;gap:12px;">
      <button class="btn btn-outline" style="flex:1;" onclick="close('mDel')"><i class="fas fa-xmark"></i> Hủy</button>
      <a id="dBtn" href="#" class="btn btn-danger" style="flex:1;"><i class="fas fa-trash-can"></i> Xác nhận</a>
    </div>
  </div>
</div>

<script>
const SIZES=['S','M','L','XL','XXL'];
let curRow=null;
function open(id){document.getElementById(id).classList.add('open');document.body.style.overflow='hidden';}
function close(id){document.getElementById(id).classList.remove('open');if(!document.querySelector('.backdrop.open'))document.body.style.overflow='';}
function bdClick(e,id){if(e.target===document.getElementById(id))close(id);}
document.addEventListener('keydown',e=>{if(e.key==='Escape'){document.querySelectorAll('.backdrop.open').forEach(b=>b.classList.remove('open'));document.body.style.overflow='';}});
function parseSz(str){const o={S:0,M:0,L:0,XL:0,XXL:0};if(!str)return o;str.split(',').forEach(p=>{const[k,v]=p.split(':');if(k)o[k.trim()]=parseInt(v)||0;});return o;}
function fmtP(n){return parseFloat(n).toLocaleString('vi-VN')+' ₫';}
function gL(g){return g==='male'?'Nam':g==='female'?'Nữ':'Unisex';}

function openView(row){
  curRow=row;const d=row.dataset,sz=parseSz(d.sizes);
  document.getElementById('vImg').style.backgroundImage=d.img?`url('${d.img}')`:'none';
  document.getElementById('vName').textContent=d.name;
  document.getElementById('vCat').textContent=d.cat;
  document.getElementById('vGender').textContent=gL(d.gender);
  document.getElementById('vPrice').textContent=fmtP(d.price);
  document.getElementById('vDesc').textContent=d.desc||'(Chưa có mô tả)';
  document.getElementById('vSz').innerHTML=SIZES.map(s=>`<div class="sz-box ${sz[s]>0?'ok':'out'}"><div class="lbl">${s}</div><div class="qty">${sz[s]}</div></div>`).join('');
  open('mView');
}
function openEdit(row){
  curRow=row;const d=row.dataset,sz=parseSz(d.sizes);
  document.getElementById('eID').value=d.id;
  document.getElementById('eName').value=d.name;
  document.getElementById('ePrice').value=d.price;
  document.getElementById('eImg').value=d.img||'';
  document.getElementById('eDesc').value=d.desc||'';
  const sel=document.getElementById('eCat');
  for(const o of sel.options)o.selected=(o.value===d.catid);
  SIZES.forEach(s=>{const i=document.getElementById('eS_'+s);if(i)i.value=sz[s]??0;});
  open('mEdit');
}
function submitEdit(){const b=document.getElementById('eSave');b.classList.add('loading');b.disabled=true;document.getElementById('editForm').submit();}
function switchToEdit(){close('mView');setTimeout(()=>openEdit(curRow),130);}
function openDel(row){
  curRow=row;const d=row.dataset;
  document.getElementById('dName').textContent=d.name;
  document.getElementById('dBtn').href=`AdminController?action=DeleteProduct&id=${d.id}`;
  open('mDel');
}
(function init(){
  const cats=new Set();
  document.querySelectorAll('.prow').forEach(r=>cats.add(r.dataset.cat));
  const sel=document.getElementById('catF');
  [...cats].sort().forEach(c=>{const o=document.createElement('option');o.value=c;o.textContent=c;sel.appendChild(o);});
  filter();
})();
function filter(){
  const kw=document.getElementById('srch').value.toLowerCase().trim();
  const st=document.getElementById('stF').value;
  const cat=document.getElementById('catF').value;
  let n=0;
  document.querySelectorAll('.prow').forEach(r=>{
    const ok=(!kw||r.dataset.name.toLowerCase().includes(kw))&&(!st||r.dataset.status===st)&&(!cat||r.dataset.cat===cat);
    r.style.display=ok?'':'none';if(ok)n++;
  });
  document.getElementById('visCnt').textContent=n;
}
function resetF(){document.getElementById('srch').value='';document.getElementById('stF').value='';document.getElementById('catF').value='';filter();}
document.getElementById('srch').addEventListener('input',filter);
document.getElementById('stF').addEventListener('change',filter);
document.getElementById('catF').addEventListener('change',filter);
</script>
</body>
</html>
