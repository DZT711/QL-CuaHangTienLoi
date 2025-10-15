CREATE DATABASE IF NOT EXISTS QL_chtienloi;
USE QL_chtienloi;

-- Bảng LOAI
CREATE TABLE LOAI (
  MaLoai INT(11) PRIMARY KEY AUTO_INCREMENT,
  TenLoai VARCHAR(255) NOT NULL,
  MoTa VARCHAR(255) DEFAULT NULL
);

INSERT INTO LOAI (MaLoai, TenLoai, MoTa) VALUES
(1, 'Đồ uống', 'Các loại nước giải khát và đồ uống đóng chai, lon, hộp'),
(2, 'Thực phẩm ăn liền & khô', 'Thực phẩm chế biến sẵn, tiện lợi, dễ bảo quản'),
(3, 'Thực phẩm tươi & đông lạnh', 'Thực phẩm tươi sống, đóng gói hoặc bảo quản lạnh'),
(4, 'Gia dụng & vệ sinh cá nhân', 'Sản phẩm gia dụng và chăm sóc cá nhân hằng ngày'),
(5, 'Văn phòng phẩm & tiện ích', 'Đồ dùng học tập, văn phòng và các vật dụng tiện ích nhỏ'),
(6, 'Thức ăn cho thú cưng', 'Sản phẩm dinh dưỡng cho thú nuôi'),
(7, 'Y tế & mỹ phẩm cơ bản', 'Sản phẩm chăm sóc sức khỏe và mỹ phẩm thiết yếu');


-- Bảng DONVI
CREATE TABLE DONVI (
  MaDonVi INT(11) PRIMARY KEY AUTO_INCREMENT,
  TenDonVi VARCHAR(50) NOT NULL,
  MoTa VARCHAR(255) DEFAULT NULL
);

INSERT INTO DONVI (MaDonVi, TenDonVi, MoTa) VALUES
(1, 'chai', 'Chai'),
(2, 'gói', 'Gói'),
(3, 'lon', 'Lon'),
(4, 'hộp', 'Hộp'),
(5, 'thùng', 'Thùng'),
(6, 'bộ', 'Bộ'),
(7, 'vỉ', 'Vỉ'),
(8, 'cuộn', 'Cuộn'),
(9, 'túi', 'Túi'),
(10, 'can', 'Can'),
(11, 'bao', 'Bao');

-- Bảng SANPHAM
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
  CONSTRAINT fk_sanpham_loai FOREIGN KEY (Loai) REFERENCES LOAI (MaLoai),
  CONSTRAINT fk_sanpham_donvi FOREIGN KEY (DonViTinh) REFERENCES DONVI (MaDonVi)
);

INSERT INTO SANPHAM (MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa, TrangThai) VALUES
('SP001', 'Coca Cola 330ml', 1, 100, 3, 10000, '2025-01-01', '2026-01-01', 'Nước ngọt có gas Coca Cola lon 330ml', 'active'),
('SP002', 'Sting Dâu 330ml', 1, 80, 3, 9000, '2025-01-10', '2026-01-10', 'Nước tăng lực Sting hương dâu lon 330ml', 'active'),
('SP003', 'Sữa tươi Vinamilk 1L', 1, 50, 1, 30000, '2025-02-01', '2025-08-01', 'Sữa tươi tiệt trùng Vinamilk hộp 1 lít', 'active'),
('SP004', 'Aquafina 500ml', 1, 120, 1, 7000, '2025-01-15', '2026-01-15', 'Nước tinh khiết Aquafina chai 500ml', 'active'),
('SP005', 'Mì Hảo Hảo tôm chua cay', 2, 200, 2, 4000, '2025-02-01', '2026-02-01', 'Mì gói Hảo Hảo vị tôm chua cay 75g', 'active'),
('SP006', 'Phở ăn liền Cung Đình bò hầm', 2, 150, 2, 6500, '2025-01-20', '2026-01-20', 'Phở ăn liền Cung Đình vị bò hầm', 'active'),
('SP007', 'Bánh Oreo 133g', 2, 90, 2, 15000, '2025-01-05', '2025-07-05', 'Bánh quy Oreo nhân kem', 'active'),
('SP008', 'Snack khoai tây Lays 52g', 2, 100, 2, 12000, '2025-02-10', '2026-02-10', 'Snack khoai tây Lays vị BBQ', 'active'),
('SP009', 'Thịt gà đông lạnh 1kg', 3, 40, 11, 75000, '2025-01-25', '2025-06-25', 'Thịt gà đông lạnh đóng bao 1kg', 'active'),
('SP010', 'Cá basa phi lê 500g', 3, 60, 11, 55000, '2025-02-01', '2025-07-01', 'Cá basa phi lê đông lạnh 500g', 'active'),
('SP011', 'Rau cải ngọt 500g', 3, 70, 9, 20000, '2025-02-20', '2025-02-28', 'Rau cải ngọt tươi 500g', 'active'),
('SP012', 'Nước giặt OMO 3.8L', 4, 30, 10, 145000, '2025-01-15', '2028-01-15', 'Nước giặt OMO matic hương ngàn hoa', 'active'),
('SP013', 'Nước rửa chén Sunlight 750ml', 4, 60, 1, 32000, '2025-01-20', '2028-01-20', 'Nước rửa chén Sunlight chanh 750ml', 'active'),
('SP014', 'Bàn chải đánh răng P/S', 4, 100, 4, 12000, '2025-02-01', '2028-02-01', 'Bàn chải đánh răng P/S lông mềm', 'active'),
('SP015', 'Giấy vệ sinh Bless You 10 cuộn', 4, 40, 8, 50000, '2025-01-25', '2028-01-25', 'Giấy vệ sinh Bless You 3 lớp', 'active'),
('SP016', 'Bút bi Thiên Long xanh', 5, 200, 4, 5000, '2025-01-01', '2028-01-01', 'Bút bi Thiên Long mực xanh', 'active'),
('SP017', 'Tập vở 200 trang', 5, 150, 4, 12000, '2025-02-01', '2028-02-01', 'Tập vở kẻ ngang 200 trang', 'active'),
('SP018', 'Kéo cắt giấy 18cm', 5, 50, 4, 25000, '2025-01-15', '2028-01-15', 'Kéo cắt giấy cán nhựa 18cm', 'active'),
('SP019', 'Pate cho mèo Whiskas 85g', 6, 80, 2, 18000, '2025-01-10', '2026-01-10', 'Pate Whiskas vị cá ngừ cho mèo', 'active'),
('SP020', 'Thức ăn hạt cho chó Pedigree 1.5kg', 6, 40, 11, 120000, '2025-01-20', '2026-01-20', 'Thức ăn hạt Pedigree vị gà 1.5kg', 'active'),
('SP021', 'Khẩu trang y tế 50 cái', 7, 100, 11, 40000, '2025-01-01', '2026-01-01', 'Khẩu trang y tế 4 lớp hộp 50 cái', 'active'),
('SP022', 'Nước rửa tay Lifebuoy 500ml', 7, 60, 1, 45000, '2025-01-25', '2026-01-25', 'Nước rửa tay Lifebuoy diệt khuẩn', 'active'),
('SP023', 'Kem đánh răng Colgate 200g', 7, 90, 2, 30000, '2025-01-15', '2026-01-15', 'Kem đánh răng Colgate hương bạc hà', 'active');


-- Bảng NHACUNGCAP
CREATE TABLE NHACUNGCAP (
  MaNCC VARCHAR(20) PRIMARY KEY,
  TenNCC VARCHAR(255) NOT NULL,
  DiaChi VARCHAR(255) NOT NULL,
  DienThoai CHAR(10) NOT NULL,
  Email VARCHAR(40) NOT NULL,
  TrangThai ENUM('active', 'inactive') NOT NULL
);

-- Bảng NHANVIEN
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
INSERT INTO NHANVIEN (MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email, Luong, ChucVu, TrangThai) VALUES
('NV001', 'Truong Van', 'Tuan', 'Nam', '1999-01-01', 'Quảng Ngãi', 'vantuanw911@gmail.com', 15000000, 'QL', 'active'),
('NV002', 'Nguyen Si', 'Huy', 'Nam', '2000-03-05', 'TP.HCM', 'nguyensihuynsh711@gmail.com', 14000000, 'QL', 'active'),
('NV003', 'Nguyen Van', 'Cuong', 'Nam', '1998-07-09', 'Biên Hòa', 'vancuonghp9014@gmail.com', 13500000, 'QL', 'active'),
('NV004', 'Dang Thanh', 'Son', 'Nam', '2001-11-25', 'Đà Lạt', 'simpson061125@gmail.com', 13000000, 'QL', 'active'),
('NV005', 'Nguyen', 'Staff', 'Nam', '2002-04-20', 'Cần Thơ', 'staff@gmail.com', 10000000, 'NV', 'active'),
('NV404', '...', 'Thúy An', 'Nu', '2000-01-01', '...', 'an@example.com', 10000000, 'NV', 'active'),
('NV006', '...', 'Minh Ánh', 'Nu', '2000-05-05', '...', 'anh@example.com', 10000000, 'NV', 'active'),
('NV007', '...', 'Quốc Bình', 'Nam', '1999-09-09', '...', 'binh@example.com', 10000000, 'NV', 'inactive');

-- Bảng KHACHHANG
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

-- Bảng PHIEUNHAP
CREATE TABLE PHIEUNHAP (
  MaPhieu VARCHAR(50) PRIMARY KEY,
  MaNCC VARCHAR(20),
  MaNV VARCHAR(20) NOT NULL,
  NgayLapPhieu DATETIME DEFAULT CURRENT_TIMESTAMP,
  TongTien INT(11) NOT NULL DEFAULT 0,
  CONSTRAINT FK_PHIEUNHAP_NCC FOREIGN KEY (MaNCC) REFERENCES NHACUNGCAP (MaNCC),
  CONSTRAINT FK_PHIEUNHAP_NV FOREIGN KEY (MaNV) REFERENCES NHANVIEN (MaNV)
);

-- Bảng CHITIETPHIEUNHAP
CREATE TABLE CHITIETPHIEUNHAP (
  MaPhieu VARCHAR(50) NOT NULL,
  MaSP VARCHAR(20) NOT NULL,
  SoLuong INT(11) NOT NULL CHECK (SoLuong >= 0),
  GiaNhap INT(11) NOT NULL CHECK (GiaNhap >= 0),
  ThanhTien INT(11) NOT NULL CHECK (ThanhTien >= 0),
  PRIMARY KEY (MaPhieu, MaSP),
  CONSTRAINT fk_ctpn_phieu FOREIGN KEY (MaPhieu) REFERENCES PHIEUNHAP (MaPhieu) ON DELETE CASCADE,
  CONSTRAINT fk_ctpn_sp FOREIGN KEY (MaSP) REFERENCES SANPHAM (MaSP)
);

-- Bảng HOADON
CREATE TABLE HOADON (
  MaHD VARCHAR(20) PRIMARY KEY,
  MaKH VARCHAR(20) NOT NULL,
  MaNV VARCHAR(20) NOT NULL,
  ThoiGianLapHD DATETIME DEFAULT CURRENT_TIMESTAMP,
  TienKhachDua INT(11) DEFAULT 0,
  TienThua INT(11) DEFAULT 0,
  TongTien INT(11) DEFAULT 0,
  PhuongThucTT ENUM('Tiền mặt', 'Chuyển khoản') DEFAULT 'Tiền mặt',
  CONSTRAINT fk_hd_kh FOREIGN KEY (MaKH) REFERENCES KHACHHANG (MaKH),
  CONSTRAINT fk_hd_nv FOREIGN KEY (MaNV) REFERENCES NHANVIEN (MaNV)
);

-- Bảng CHITIETHOADON
CREATE TABLE CHITIETHOADON (
  MaHD VARCHAR(20) NOT NULL,
  MaSP VARCHAR(20) NOT NULL,
  SoLuong INT(11) NOT NULL CHECK (SoLuong >= 0),
  DonGia INT(11) NOT NULL CHECK (DonGia >= 0),
  ThanhTien INT(11) NOT NULL CHECK (ThanhTien >= 0),
  PRIMARY KEY (MaHD, MaSP),
  CONSTRAINT fk_cthd_hd FOREIGN KEY (MaHD) REFERENCES HOADON (MaHD) ON DELETE CASCADE,
  CONSTRAINT fk_cthd_sp FOREIGN KEY (MaSP) REFERENCES SANPHAM (MaSP)
);

-- Bảng TAIKHOAN
CREATE TABLE TAIKHOAN (
  UserName VARCHAR(50) PRIMARY KEY,
  PassWord VARCHAR(255) NOT NULL,
  HoTen VARCHAR(255) DEFAULT NULL,
  MaNV VARCHAR(20) NOT NULL,
  VaiTro ENUM('Admin', 'NhanVien') DEFAULT 'NhanVien',
  TrangThai ENUM('Active', 'Inactive') DEFAULT 'Active',
  Email VARCHAR(50) DEFAULT NULL,
  CONSTRAINT fk_taikhoan_nv FOREIGN KEY (MaNV) REFERENCES NHANVIEN (MaNV)
);
INSERT INTO TAIKHOAN (UserName, PassWord, HoTen, MaNV, VaiTro, TrangThai, Email) VALUES
('tuanadmin', 'tuanadmin', 'TuanRemix', 'NV001', 'Admin', 'Active', 'vantuanw911@gmail.com'),
('huyadmin', 'huyadmin', 'HuyDapZai', 'NV002', 'Admin', 'Active', 'nguyensihuynsh711@gmail.com'),
('cuongadmin', 'cuongadmin', 'CuongHaySuy', 'NV003', 'Admin', 'Active', 'vancuonghp9014@gmail.com'),
('sonadmin', 'sonadmin', 'SonCuBe', 'NV004', 'Admin', 'Active', 'simpson061125@gmail.com'),
('an', 'an', 'Thúy An', 'NV404', 'Admin', 'Active', 'an@example.com'),
('anh', 'anh', 'Minh Ánh', 'NV006', 'NhanVien', 'Active', 'anh@example.com'),
('binh', 'binh', 'Quốc Bình', 'NV007', 'NhanVien', 'Inactive', 'binh@example.com');

INSERT INTO KHACHHANG (MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi, TrangThai) VALUES
('KH001', 'Nguyen Van', 'Nam', 'Nam', '1995-04-10', '0901234567', 'TP. Hồ Chí Minh', 'active'),
('KH002', 'Tran Thi', 'Mai', 'Nu', '1998-08-22', '0912345678', 'Hà Nội', 'active'),
('KH003', 'Le Hoang', 'Phuc', 'Nam', '1990-12-15', '0923456789', 'Đà Nẵng', 'active'),
('KH004', 'Pham Ngoc', 'Lan', 'Nu', '1999-03-30', '0934567890', 'Cần Thơ', 'active'),
('KH005', 'Doan Van', 'Hung', 'Nam', '1988-07-18', '0945678901', 'Huế', 'active'),
('KH006', 'Nguyen Thi', 'Hoa', 'Nu', '2001-05-25', '0956789012', 'Quảng Ninh', 'active'),
('KH007', 'Bui Quoc', 'Tuan', 'Nam', '1996-11-02', '0967890123', 'Nha Trang', 'active'),
('KH008', 'Hoang Kim', 'Anh', 'Nu', '2000-09-09', '0978901234', 'Biên Hòa', 'active'),
('KH009', 'Phan Thanh', 'Dat', 'Nam', '1997-02-17', '0989012345', 'Vũng Tàu', 'active'),
('KH010', 'Vu Thi', 'Huong', 'Nu', '1994-06-12', '0990123456', 'Bắc Ninh', 'active');

-- Tạo index tối ưu tìm kiếm
CREATE INDEX idx_sanpham_ten ON SANPHAM(TenSP);
CREATE INDEX idx_sanpham_loai ON SANPHAM(Loai);
CREATE INDEX idx_sanpham_soLuongTon ON SANPHAM(SoLuongTon);

CREATE INDEX idx_hoadon_kh ON HOADON (MaKH);
CREATE INDEX idx_hoadon_nv ON HOADON (MaNV);
CREATE INDEX idx_hoadon_thoigian ON HOADON (ThoiGianLapHD);

CREATE INDEX idx_cthd_sp ON CHITIETHOADON (MaSP);

CREATE INDEX idx_phieunhap_ncc ON PHIEUNHAP(MaNCC);
CREATE INDEX idx_phieunhap_nv ON PHIEUNHAP(MaNV);
CREATE INDEX idx_phieunhap_ngay ON PHIEUNHAP(NgayLapPhieu);

CREATE INDEX idx_chitietphieunhap_sp ON CHITIETPHIEUNHAP(MaSP);

CREATE INDEX idx_khachhang_dienthoai ON KHACHHANG (DienThoai);
CREATE INDEX idx_khachhang_trangthai ON KHACHHANG (TrangThai);

CREATE INDEX idx_nhanvien_chucvu ON NHANVIEN(ChucVu);
CREATE INDEX idx_nhanvien_trangthai ON NHANVIEN(TrangThai);
