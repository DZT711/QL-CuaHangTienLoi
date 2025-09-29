CREATE DATABASE QL_chtienloi;
USE QL_chtienloi;

-- Bảng loại sản phẩm
CREATE TABLE LOAI (
  MaLoai INT(11) PRIMARY KEY AUTO_INCREMENT,
  TenLoai VARCHAR(255) NOT NULL,
  MoTa VARCHAR(255) DEFAULT NULL
);

-- Bảng đơn vị tính
CREATE TABLE DONVI (
  MaDonVi INT(11) PRIMARY KEY AUTO_INCREMENT,
  TenDonVi VARCHAR(50) NOT NULL,
  MoTa VARCHAR(255) DEFAULT NULL
);

INSERT INTO DONVI (MaDonVi, TenDonVi, MoTa) VALUES
(1, 'chai', 'Chai'),
(2, 'gói', 'Gói'),
(3, 'hộp', 'Hộp'),
(4, 'cái', 'Cái'),
(5, 'thùng', 'Thùng'),
(6, 'bộ', 'Bộ'),
(7, 'vỉ', 'Vỉ'),
(8, 'cuộn', 'Cuộn');

-- Bảng sản phẩm
CREATE TABLE SANPHAM (
  MaSP VARCHAR(20) PRIMARY KEY,
  TenSP VARCHAR(255) NOT NULL,
  Loai INT(11) NOT NULL,
  SoLuongTon INT(11) NOT NULL DEFAULT 0,
  DonViTinh INT(11) NOT NULL,
  GiaBan INT(11) NOT NULL CHECK (GiaBan >= 0),
  NgaySanXuat DATE DEFAULT NULL,
  HanSuDung DATE DEFAULT NULL,
  MoTa VARCHAR(255) DEFAULT NULL,
  TrangThai ENUM('active', 'inactive') DEFAULT 'active',
  CONSTRAINT fk_sanpham_loai FOREIGN KEY (Loai) REFERENCES LOAI(MaLoai),
  CONSTRAINT fk_sanpham_donvi FOREIGN KEY (DonViTinh) REFERENCES DONVI(MaDonVi)
);

-- Bảng nhà cung cấp
CREATE TABLE NHACUNGCAP (
  MaNCC VARCHAR(20) PRIMARY KEY,
  TenNCC VARCHAR(255) NOT NULL,
  DiaChi VARCHAR(255) NOT NULL,
  DienThoai CHAR(10) NOT NULL,
  Email VARCHAR(40) NOT NULL,
  TrangThai ENUM('active', 'inactive') NOT NULL
);

-- Bảng nhân viên
CREATE TABLE NHANVIEN (
  MaNV CHAR(20) PRIMARY KEY,
  Ho VARCHAR(20) NOT NULL,
  Ten VARCHAR(20) NOT NULL,
  GioiTinh ENUM('Nam', 'Nu') DEFAULT NULL,
  NgaySinh DATE DEFAULT NULL,
  DiaChi VARCHAR(255) DEFAULT NULL,
  Email VARCHAR(50) NOT NULL,
  Luong INT(11) NOT NULL,
  ChucVu ENUM('QL', 'NV') NOT NULL,
  TrangThai ENUM('active', 'inactive') DEFAULT 'active'
);

-- Bảng khách hàng
CREATE TABLE KHACHHANG (
  MaKH VARCHAR(20) PRIMARY KEY,
  Ho VARCHAR(20) NOT NULL,
  Ten VARCHAR(20) NOT NULL,
  GioiTinh ENUM('Nam', 'Nu') DEFAULT NULL,
  NgaySinh DATE DEFAULT NULL,
  DienThoai CHAR(20) NOT NULL UNIQUE,
  DiaChi VARCHAR(255) DEFAULT NULL,
  TrangThai ENUM('active', 'inactive') DEFAULT 'active'
);

-- Bảng phiếu nhập
CREATE TABLE PHIEUNHAP (
  MaPhieu VARCHAR(50) PRIMARY KEY,
  MaNCC VARCHAR(20) DEFAULT NULL,
  MaNV VARCHAR(20) NOT NULL,
  NgayLapPhieu DATETIME DEFAULT CURRENT_TIMESTAMP,
  TongTien INT(11) NOT NULL DEFAULT 0,
  CONSTRAINT FK_PHIEUNHAP_NCC FOREIGN KEY (MaNCC) REFERENCES NHACUNGCAP(MaNCC),
  CONSTRAINT FK_PHIEUNHAP_NV FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
);


-- Chi tiết phiếu nhập
CREATE TABLE CHITIETPHIEUNHAP (
  MaPhieu VARCHAR(50) NOT NULL,
  MaSP VARCHAR(20) NOT NULL,
  SoLuong INT(11) NOT NULL CHECK (SoLuong >= 0),
  GiaNhap INT(11) NOT NULL CHECK (DonGia >= 0),
  PRIMARY KEY (MaPhieu, MaSP),
  CONSTRAINT fk_ctpn_phieu FOREIGN KEY (MaPhieu) REFERENCES PHIEUNHAP(MaPhieu) ON DELETE CASCADE,
  CONSTRAINT fk_ctpn_sp FOREIGN KEY (MaSP) REFERENCES SANPHAM(MaSP)
);

-- Bảng hóa đơn
CREATE TABLE HOADON (
  MaHD VARCHAR(20) PRIMARY KEY,
  MaKH VARCHAR(20) NOT NULL,
  MaNV VARCHAR(20) NOT NULL,
  ThoiGianLapHD DATETIME DEFAULT CURRENT_TIMESTAMP,
  TongTien INT(11) DEFAULT 0,
  CONSTRAINT fk_hd_kh FOREIGN KEY (MaKH) REFERENCES KHACHHANG(MaKH),
  CONSTRAINT fk_hd_nv FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
);

-- Chi tiết hóa đơn
CREATE TABLE CHITIETHOADON (
  MaHD VARCHAR(20) NOT NULL,
  MaSP VARCHAR(20) NOT NULL,
  SoLuong INT(11) NOT NULL CHECK (SoLuong >= 0),
  DonGia INT(11) NOT NULL CHECK (DonGia >= 0),
  PRIMARY KEY (MaHD, MaSP),
  CONSTRAINT fk_cthd_hd FOREIGN KEY (MaHD) REFERENCES HOADON(MaHD) ON DELETE CASCADE,
  CONSTRAINT fk_cthd_sp FOREIGN KEY (MaSP) REFERENCES SANPHAM(MaSP)
);

-- Bảng tài khoản
CREATE TABLE TAIKHOAN (
  UserName VARCHAR(50) PRIMARY KEY,
  PassWord VARCHAR(255) NOT NULL,
  HoTen VARCHAR(255) DEFAULT NULL,
  VaiTro ENUM('Admin', 'NhanVien') DEFAULT 'NhanVien',
  TrangThai ENUM('Active', 'Inactive') DEFAULT 'Active',
  Email VARCHAR(50) DEFAULT NULL
);

INSERT INTO TAIKHOAN (UserName, PassWord, HoTen, VaiTro, TrangThai, Email) VALUES
('tuanadmin', 'tuanadmin', 'TuanRemix', 'Admin', 'Active', 'vantuanw911@gmail.com'),
('huyadmin', 'huyadmin', 'HuyBanTumLum', 'Admin', 'Active', 'nguyensihuynsh711@gmail.com'),
('cuongadmin', 'cuongadmin', 'CuongHaySuy', 'Admin', 'Active', 'vancuonghp9014@gmail.com'),
('sonadmin', 'sonadmin', 'SonCuBe', 'Admin', 'Active', 'simpson061125@gmail.com'),
('staff', 'staff', 'Staff', 'NhanVien', 'Active', 'staff@gmail.com');


-- Tạo index tối ưu tìm kiếm
CREATE INDEX idx_sanpham_ten ON SANPHAM(TenSP);
CREATE INDEX idx_sanpham_loai ON SANPHAM(Loai);
CREATE INDEX idx_sanpham_soLuongTon ON SANPHAM(SoLuongTon);

CREATE INDEX idx_hoadon_kh ON HOADON(MaKH);
CREATE INDEX idx_hoadon_nv ON HOADON(MaNV);
CREATE INDEX idx_hoadon_thoigian ON HOADON(ThoiGianLapHD);

CREATE INDEX idx_chitiethoadon_sp ON CHITIETHOADON(MaSP);

CREATE INDEX idx_phieunhap_ncc ON PHIEUNHAP(MaNCC);
CREATE INDEX idx_phieunhap_nv ON PHIEUNHAP(MaNV);
CREATE INDEX idx_phieunhap_ngay ON PHIEUNHAP(NgayLapPhieu);

CREATE INDEX idx_chitietphieunhap_sp ON CHITIETPHIEUNHAP(MaSP);

CREATE INDEX idx_khachhang_dienthoai ON KHACHHANG(DienThoai);
CREATE INDEX idx_khachhang_trangthai ON KHACHHANG(TrangThai);

CREATE INDEX idx_nhanvien_chucvu ON NHANVIEN(ChucVu);
CREATE INDEX idx_nhanvien_trangthai ON NHANVIEN(TrangThai);
