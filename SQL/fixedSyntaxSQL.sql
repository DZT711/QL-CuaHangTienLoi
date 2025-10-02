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
  MaNV VARCHAR(20) PRIMARY KEY,
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
  GiaNhap INT(11) NOT NULL CHECK (GiaNhap >= 0),
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
('huyadmin', 'huyadmin', 'HuyDapZai', 'Admin', 'Active', 'nguyensihuynsh711@gmail.com'),
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

-- -- Chèn dữ liệu mẫu cho LOAI
-- INSERT INTO LOAI (TenLoai, MoTa) VALUES
--   ('Nước giải khát', 'Các loại nước uống'),
--   ('Thực phẩm khô', 'Gạo, mì, đồ khô'),
--   ('Sữa & sản phẩm từ sữa', 'Sữa, sữa chua, phô mai'),
--   ('Đồ gia dụng', 'Dụng cụ nhà bếp, đồ dùng');

-- -- Chèn dữ liệu mẫu cho NHACUNGCAP
-- INSERT INTO NHACUNGCAP (MaNCC, TenNCC, DiaChi, DienThoai, Email, TrangThai) VALUES
--   ('NCC001', 'Công ty ABC', '123 Đường A, Q1, HCM', '0901234567', 'abc@xyz.com', 'active'),
--   ('NCC002', 'Công ty XYZ', '456 Đường B, Q2, HCM', '0912345678', 'xyz@abc.com', 'active'),
--   ('NCC003', 'Công ty Sữa Việt', '789 Đường C, Q3, HCM', '0987654321', 'sua@viet.com', 'inactive');

-- -- Chèn dữ liệu mẫu cho NHANVIEN
-- INSERT INTO NHANVIEN (MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email, Luong, ChucVu, TrangThai) VALUES
--   ('NV001', 'Nguyen', 'An', 'Nam', '1990-05-10', '12 Đường D, Q1, HCM', 'an@xyz.com', 7000000, 'NV', 'active'),
--   ('NV002', 'Le', 'Binh', 'Nu', '1992-07-15', '34 Đường E, Q2, HCM', 'binh@xyz.com', 7500000, 'NV', 'active'),
--   ('NV003', 'Tran', 'Cuong', 'Nam', '1985-11-20', '56 Đường F, Q3, HCM', 'cuong@xyz.com', 10000000, 'QL', 'active'),
--   ('NV004', 'Pham', 'Dung', 'Nu', '1993-12-25', '78 Đường G, Q4, HCM', 'dung@xyz.com', 6500000, 'NV', 'inactive');

-- -- Chèn dữ liệu mẫu cho KHACHHANG
-- INSERT INTO KHACHHANG (MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi, TrangThai) VALUES
--   ('KH001', 'Hoang', 'Minh', 'Nam', '1995-03-03', '0911000111', '123 Đường H, Q1, HCM', 'active'),
--   ('KH002', 'Vu', 'Linh', 'Nu', '1998-08-08', '0922000222', '456 Đường I, Q2, HCM', 'active'),
--   ('KH003', 'Pham', 'Ha', 'Nu', '2000-10-10', '0933000333', '789 Đường J, Q3, HCM', 'inactive');

-- -- Chèn dữ liệu mẫu cho SANPHAM
-- INSERT INTO SANPHAM (MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa, TrangThai) VALUES
--   ('SP001', 'Coca Cola 330ml', 1, 100, 1, 8000, '2024-01-01', '2025-01-01', 'Nước ngọt lon', 'active'),
--   ('SP002', 'Pepsi 330ml', 1, 80, 1, 7500, '2024-02-01', '2025-02-01', 'Nước ngọt lon', 'active'),
--   ('SP003', 'Gạo ST 5kg', 2, 50, 5, 120000, '2024-03-01', '2026-03-01', 'Gạo thơm sạch', 'active'),
--   ('SP004', 'Sữa Vinamilk 1L', 3, 200, 1, 25000, '2024-04-01', '2025-04-01', 'Sữa tươi tiệt trùng', 'active'),
--   ('SP005', 'Chảo Teflon 28cm', 4, 20, 4, 300000, '2023-05-01', '2030-05-01', 'Chảo chống dính', 'active');

-- -- Chèn dữ liệu mẫu cho PHIEUNHAP và CHITIETPHIEUNHAP
-- INSERT INTO PHIEUNHAP (MaPhieu, MaNCC, MaNV, NgayLapPhieu, TongTien) VALUES
--   ('PN001', 'NCC001', 'NV003', '2024-06-01 10:00:00', 0),
--   ('PN002', 'NCC002', 'NV001', '2024-06-05 11:30:00', 0);

-- INSERT INTO CHITIETPHIEUNHAP (MaPhieu, MaSP, SoLuong, GiaNhap) VALUES
--   ('PN001', 'SP001', 50, 6000),
--   ('PN001', 'SP003', 10, 100000),
--   ('PN002', 'SP004', 100, 20000),
--   ('PN002', 'SP002', 60, 5500);


-- -- Chèn dữ liệu mẫu cho HOADON và CHITIETHOADON
-- INSERT INTO HOADON (MaHD, MaKH, MaNV, ThoiGianLapHD, TongTien) VALUES
--   ('HD001', 'KH001', 'NV001', '2024-06-10 14:00:00', 0),
--   ('HD002', 'KH002', 'NV002', '2024-06-12 16:30:00', 0);

-- INSERT INTO CHITIETHOADON (MaHD, MaSP, SoLuong, DonGia) VALUES
--   ('HD001', 'SP001', 2, 9000),
--   ('HD001', 'SP004', 1, 26000),
--   ('HD002', 'SP003', 1, 130000),
--   ('HD002', 'SP002', 3, 8000);



-- -- Chèn dữ liệu mẫu cho TAIKHOAN
-- INSERT INTO TAIKHOAN (UserName, PassWord, HoTen, VaiTro, TrangThai, Email) VALUES

--   ('nv1', 'nv1pass', 'Nguyen Van A', 'NhanVien', 'Active', 'a@example.com'),
--   ('nv2', 'nv2pass', 'Le Thi B', 'NhanVien', 'Active', 'b@example.com');




  -- proceduce tham khảo
--   -- Cập nhật tổng tiền cho PHIEUNHAP
-- UPDATE PHIEUNHAP pn
-- JOIN (
--   SELECT MaPhieu, SUM(SoLuong * GiaNhap) AS TT
--   FROM CHITIETPHIEUNHAP
--   GROUP BY MaPhieu
-- ) ct ON pn.MaPhieu = ct.MaPhieu
-- SET pn.TongTien = ct.TT;
-- -- Cập nhật tổng tiền cho HOADON
-- UPDATE HOADON hd
-- JOIN (
--   SELECT MaHD, SUM(SoLuong * DonGia) AS TT
--   FROM CHITIETHOADON
--   GROUP BY MaHD
-- ) ct2 ON hd.MaHD = ct2.MaHD
-- SET hd.TongTien = ct2.TT;