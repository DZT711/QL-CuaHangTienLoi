-- xong
CREATE TABLE PHIEUNHAP (
  MaPhieu VARCHAR(50) PRIMARY KEY,
  MaNCC VARCHAR(20) DEFAULT NULL,
  NgayLapPhieu DATETIME DEFAULT NULL CURRENT_TIMESTAMP(),
  TongTien DOUBLE NOT NULL DEFAULT 0,
  NguoiLapPhieu VARCHAR(50) DEFAULT NULL,
)

-- xem xét: mã hàng hay mã sản phẩm, constraint    
CREATE TABLE CHITIETPHIEUNHAP (
  MaPhieu VARCHAR(20) NOT NULL,
  MaSP VARCHAR(20) NOT NULL,
  SoLuong INT(11) NOT NULL CHECK (SoLuong >= 0),
  DonGia INT(11) NOT NULL CHECK (DonGia >= 0),
  PRIMARY KEY (MaPhieu, MaSP),
  CONSTRAINT fk_ctpn_phieu FOREIGN KEY (MaPhieu) REFERENCES PHIEUNHAP(MaPhieu),
  CONSTRAINT fk_ctpn_sp FOREIGN KEY (MaSP) REFERENCES SANPHAM(MaSP)
)

-- có phiếu xuất với chí tiết phiếu xuất hay không 


-- xem xét table đơn vị: kg, lít, chai, gói, túi, hộp, ... và thuộc tính DonViTinh của table SANPHAM
CREATE TABLE SANPHAM (
  MaSP VARCHAR(20) PRIMARY KEY,
  TenSP VARCHAR(255) NOT NULL,
  Loai INT(11) NOT NULL,
  SoLuongTon INT(11) NOT NULL,
  Gia DOUBLE NOT NULL,
  HSD int(11) DEFAULT NULL,
  MoTa VARCHAR(255) DEFAULT NULL,
  -- created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  -- updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- xem xét: điểm(tích), trạng thái(active, inactive), ngày tham gia
CREATE TABLE KHACHHANG (
  MaKH VARCHAR(20) PRIMARY KEY,
  Ho VARCHAR(20) NOT NULL,
  Ten VARCHAR(20) NOT NULL,
  GioiTinh VARCHAR(20) DEFAULT NULL CHECK (GioiTinh IN ('Nam', 'Nu')),
  DiaChi VARCHAR(255) DEFAULT NULL,
  Email VARCHAR(20) NOT NULL
  TongChiTieu DECIMAL(12,2) DEFAULT 0,
);

-- xem xét có table đơn vị: kg, lít, chai, gói, túi, hộp, ...
-- xem xét có table hàng hóa (lúc nhập hàng)
-- xem xét có bảng khuyến mãi: MaKM, TenKM, GiaTriKM, NgayBatDau, NgayKetThuc, DieuKienKM, LoaiKM, MaSP, SoLuong, TrangThai(active, inactive)

-- xem xét: trạng thái, mật khẩu
CREATE TABLE NHANVIEN (
  MaNV CHAR(100) PRIMARY KEY,
  Ho VARCHAR(20) NOT NULL,
  Ten VARCHAR(20) NOT NULL,
  GioiTinh VARCHAR(20) DEFAULT NULL CHECK (GioiTinh IN ('Nam', 'Nu')),
  DiaChi VARCHAR(255) DEFAULT NULL,
  DienThoai CHAR(20) NOT NULL,
  Luong INT(11) NOT NULL,
  ChucVu CHAR(3) NOT NULL CHECK (ChucVu IN ('QL', 'NV')),
);

-- xem xét: tiền giảm, khuyến mãi
CREATE TABLE HOADON (
  MaHD VARCHAR(20) PRIMARY KEY,
  MaKH VARCHAR(20) NOT NULL,
  MaNV VARCHAR(20) NOT NULL,
  ThoiGianLapHD DATETIME DEFAULT NULL CURRENT_TIMESTAMP(),
  CONSTRAINT fk_hd_kh FOREIGN KEY (MaKH) REFERENCES KHACHHANG(MaKH),
  CONSTRAINT fk_hd_nv FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
);

-- xong
CREATE TABLE CHITIETHOADON (
  MaHD VARCHAR(20) NOT NULL,
  MaSP VARCHAR(20) NOT NULL,
  SoLuong INT(11) NOT NULL CHECK (SoLuong >= 0),
  DonGia INT(11) NOT NULL CHECK (DonGia >= 0),
  -- created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (MaHD, MaSP),
  CONSTRAINT fk_cthd_hd FOREIGN KEY (MaHD) REFERENCES HOADON(MaHD),
  CONSTRAINT fk_cthd_sp FOREIGN KEY (MaSP) REFERENCES SANPHAM(MaSP)
);

-- xong
CREATE TABLE LOAI (
  MaLoai INT(11) PRIMARY KEY,
  TenLoai VARCHAR(255) NOT NULL,
  MoTa VARCHAR(255) DEFAULT NULL,
)

--xong 
CREATE TABLE NHACUNGCAP (
  MaNCC VARCHAR(20) PRIMARY KEY,
  TenNCC VARCHAR(255) NOT NULL,
  DiaChi VARCHAR(255) NOT NULL,
  DienThoai CHAR(10) NOT NULL,
  Email VARCHAR(40) NOT NULL,
)

-- Tạo index bổ sung
CREATE INDEX idx_chitiethoadon_sp ON CHITIETHOADON(MaSP);
CREATE INDEX idx_hoadon_kh ON HOADON(MaKH);
CREATE INDEX idx_hoadon_nv ON HOADON(MaNV);

-- xem xét table TaiKhoan
CREATE TABLE TaiKhoan (
    Username VARCHAR(50) PRIMARY KEY,
    PasswordHash VARCHAR(255) NOT NULL
);

