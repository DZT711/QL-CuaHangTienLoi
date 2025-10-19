CREATE DATABASE IF NOT EXISTS QL_chtienloi;
USE QL_chtienloi;

-- Bảng LOAI
CREATE TABLE LOAI (
  MaLoai INT(11) PRIMARY KEY AUTO_INCREMENT,
  TenLoai VARCHAR(255) NOT NULL,
  MoTa VARCHAR(255) DEFAULT NULL
);

INSERT INTO LOAI (MaLoai, TenLoai, MoTa) VALUES
(1, 'Đồ uống', 'Các sản phẩm phục vụ nhu cầu giải khát hằng ngày'),
(2, 'Thực phẩm ăn liền', 'Thực phẩm chế biến sẵn, tiện lợi, sử dụng nhanh'),
(3, 'Bánh kẹo & Snack', 'Sản phẩm ăn vặt, dùng trong các bữa phụ hoặc giải trí'),
(4, 'Sữa & sản phẩm từ sữa', 'Sản phẩm cung cấp dinh dưỡng từ sữa và chế phẩm liên quan'),
(5, 'Thực phẩm khô & gia vị', 'Sản phẩm dùng trong nấu nướng và bảo quản lâu dài'),
(6, 'Đồ gia dụng & vệ sinh cá nhân', 'Sản phẩm phục vụ sinh hoạt và chăm sóc cá nhân hằng ngày'),
(7, 'Mỹ phẩm & chăm sóc cơ thể', 'Sản phẩm làm đẹp và chăm sóc da, tóc, cơ thể'),
(8, 'Đồ dùng văn phòng & tiện ích', 'Sản phẩm phục vụ học tập, làm việc và sinh hoạt hằng ngày'),
(9, 'Thức ăn & phụ kiện thú cưng', 'Sản phẩm dành cho chăm sóc và dinh dưỡng thú nuôi'),
(10, 'Đồ y tế & chăm sóc sức khỏe', 'Sản phẩm hỗ trợ bảo vệ và chăm sóc sức khỏe cá nhân');

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
  MoTa VARCHAR(255) DEFAULT NULL,
  TrangThai ENUM('active', 'inactive') DEFAULT 'active',
  CONSTRAINT fk_sanpham_loai FOREIGN KEY (Loai) REFERENCES LOAI (MaLoai),
  CONSTRAINT fk_sanpham_donvi FOREIGN KEY (DonViTinh) REFERENCES DONVI (MaDonVi)
);

INSERT INTO SANPHAM (MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, MoTa, TrangThai) VALUES
INSERT INTO SANPHAM (MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, MoTa, TrangThai) VALUES
-- 1. ĐỒ UỐNG
('SP001', 'Coca-Cola 330ml', 1, 80, 3, 10000, 'Nước giải khát có gas vị truyền thống', 'active'),
('SP002', 'Pepsi 330ml', 1, 40, 3, 10000, 'Nước giải khát có gas vị cola', 'active'),
('SP003', '7Up 330ml', 1, 130, 3, 10000, 'Nước giải khát có gas vị chanh', 'active'),
('SP004', 'Aquafina 500ml', 1, 100, 1, 8000, 'Nước suối tinh khiết', 'active'),
('SP005', 'Trà xanh 0 độ 455ml', 1, 60, 1, 12000, 'Trà xanh hương tự nhiên', 'active'),

-- 2. THỰC PHẨM ĂN LIỀN
('SP006', 'Mì Hảo Hảo tôm chua cay', 2, 100, 2, 6000, 'Mì gói ăn liền hương tôm chua cay', 'active'),
('SP007', 'Phở ăn liền Gấu Đỏ bò hầm', 2, 80, 2, 7000, 'Phở ăn liền tiện lợi', 'active'),
('SP008', 'Miến Phú Hương gà hầm', 2, 70, 2, 7000, 'Miến ăn liền vị gà hầm', 'active'),

-- 3. BÁNH KẸO & SNACK
('SP009', 'Snack khoai tây Lays vị muối biển 52g', 3, 120, 2, 15000, 'Snack khoai tây chiên giòn', 'active'),
('SP010', 'Bánh ChocoPie hộp 6 cái', 3, 80, 4, 30000, 'Bánh mềm nhân marshmallow phủ socola', 'active'),
('SP011', 'Kẹo bạc hà Mentos 37g', 3, 50, 2, 12000, 'Kẹo thơm miệng vị bạc hà', 'active'),

-- 4. SỮA & SẢN PHẨM TỪ SỮA
('SP012', 'Sữa tươi Vinamilk 180ml', 4, 100, 1, 8000, 'Sữa tươi tiệt trùng có đường', 'active'),
('SP013', 'Sữa chua uống Yomost 180ml', 4, 70, 1, 9000, 'Sữa chua uống vị cam', 'active'),
('SP014', 'Sữa Milo hộp 180ml', 4, 80, 4, 10000, 'Thức uống lúa mạch bổ sung năng lượng', 'active'),

-- 5. THỰC PHẨM KHÔ & GIA VỊ
('SP015', 'Nước mắm Nam Ngư 500ml', 5, 40, 1, 18000, 'Nước mắm cá cơm truyền thống', 'active'),
('SP016', 'Tương ớt Chinsu 250g', 5, 50, 1, 12000, 'Tương ớt đậm vị cay', 'active'),
('SP017', 'Muối i-ốt 500g', 5, 60, 9, 5000, 'Muối i-ốt tinh khiết', 'active'),

-- 6. ĐỒ GIA DỤNG & VỆ SINH CÁ NHÂN
('SP018', 'Bàn chải P/S trung bình', 6, 40, 1, 15000, 'Bàn chải đánh răng lông mềm', 'active'),
('SP019', 'Kem đánh răng CloseUp 180g', 6, 30, 1, 25000, 'Kem đánh răng hương bạc hà', 'active'),
('SP020', 'Giấy vệ sinh Bless You 10 cuộn', 6, 30, 8, 48000, 'Giấy vệ sinh cao cấp', 'active'),

-- 7. MỸ PHẨM & CHĂM SÓC CƠ THỂ
('SP021', 'Sữa rửa mặt Hazeline 100g', 7, 40, 1, 45000, 'Sữa rửa mặt chiết xuất nghệ & kiwi', 'active'),
('SP022', 'Dầu gội Clear Men 340ml', 7, 40, 1, 75000, 'Dầu gội sạch gàu cho nam', 'active'),
('SP023', 'Lăn khử mùi Nivea Men 50ml', 7, 35, 1, 65000, 'Khử mùi và chăm sóc da', 'active'),

-- 8. ĐỒ DÙNG VĂN PHÒNG & TIỆN ÍCH
('SP024', 'Bút bi Thiên Long TL-027', 8, 100, 1, 5000, 'Bút bi màu xanh 0.5mm', 'active'),
('SP025', 'Tập 200 trang Campus', 8, 80, 1, 18000, 'Tập học sinh 200 trang kẻ ngang', 'active'),
('SP026', 'Keo dán giấy Thiên Long', 8, 60, 1, 12000, 'Keo dán tiện lợi', 'active'),

-- 9. THỨC ĂN & PHỤ KIỆN THÚ CƯNG
('SP027', 'Thức ăn mèo Whiskas 1.2kg', 9, 20, 9, 95000, 'Thức ăn khô cho mèo trưởng thành', 'active'),
('SP028', 'Thức ăn chó Pedigree 1.3kg', 9, 20, 9, 100000, 'Thức ăn khô cho chó lớn', 'active'),
('SP029', 'Bát ăn nhựa cho thú cưng', 9, 15, 1, 25000, 'Bát nhựa dùng cho chó mèo', 'active'),

-- 10. ĐỒ Y TẾ & CHĂM SÓC SỨC KHỎE
('SP030', 'Khẩu trang y tế 4 lớp hộp 50 cái', 10, 50, 4, 45000, 'Khẩu trang y tế lọc bụi, vi khuẩn', 'active'),
('SP031', 'Nhiệt kế điện tử Microlife', 10, 10, 1, 120000, 'Nhiệt kế đo thân nhiệt nhanh', 'active'),
('SP032', 'Dung dịch rửa tay Lifebuoy 235ml', 10, 40, 1, 40000, 'Dung dịch rửa tay kháng khuẩn', 'active');


CREATE TABLE HANGHOA (
    MaHang VARCHAR(20) PRIMARY KEY,
    MaSP VARCHAR(20) NOT NULL,
    SoLuongConLai INT(11) NOT NULL CHECK (SoLuongConLai >= 0),
    NgaySanXuat DATE DEFAULT NULL,
    HanSuDung DATE DEFAULT NULL,
    TrangThai ENUM ('active', 'inactive', 'expired') DEFAULT 'active',
    CONSTRAINT fk_hanghoa_sanpham FOREIGN KEY (MaSP) REFERENCES SANPHAM (MaSP)
);

INSERT INTO HANGHOA (MaHang, MaSP, SoLuongConLai, NgaySanXuat, HanSuDung, TrangThai) VALUES
-- 1. Đồ uống (Coca, Pepsi, 7Up, Aquafina, Trà xanh)
('MH001', 'SP001', 50, '2025-08-01', '2026-08-01', 'active'),
('MH002', 'SP001', 30, '2025-09-10', '2026-09-10', 'active'),
('MH003', 'SP002', 40, '2025-08-15', '2026-08-15', 'active'),
('MH033', 'SP003', 80, '2025-08-20', '2026-08-20', 'active'),
('MH034', 'SP003', 50, '2025-09-15', '2026-09-15', 'active'),
('MH004', 'SP004', 100, '2025-07-01', '2026-07-01', 'active'),
('MH005', 'SP005', 60, '2025-09-01', '2026-09-01', 'active'),

-- 2. Thực phẩm ăn liền
('MH006', 'SP006', 100, '2025-06-01', '2026-06-01', 'active'),
('MH007', 'SP007', 80, '2025-07-15', '2026-07-15', 'active'),
('MH008', 'SP008', 70, '2025-07-20', '2026-07-20', 'active'),

-- 3. Bánh kẹo & Snack
('MH009', 'SP009', 60, '2025-08-05', '2026-08-05', 'active'),
('MH035', 'SP009', 60, '2025-09-01', '2026-09-01', 'active'),
('MH010', 'SP010', 40, '2025-07-01', '2026-07-01', 'active'),
('MH036', 'SP010', 40, '2025-08-10', '2026-08-10', 'active'),
('MH011', 'SP011', 50, '2025-08-01', '2026-08-01', 'active'),

-- 4. Sữa & sản phẩm từ sữa
('MH012', 'SP012', 100, '2025-09-01', '2025-12-01', 'active'),
('MH013', 'SP013', 70, '2025-08-15', '2025-11-15', 'active'),
('MH014', 'SP014', 80, '2025-09-05', '2025-12-05', 'active'),

-- 5. Thực phẩm khô & gia vị
('MH015', 'SP015', 40, '2025-05-10', '2027-05-10', 'active'),
('MH016', 'SP016', 50, '2025-04-01', '2026-04-01', 'active'),
('MH017', 'SP017', 60, '2025-03-01', '2027-03-01', 'active'),

-- 6. Đồ gia dụng & vệ sinh cá nhân
('MH018', 'SP018', 40, '2025-01-01', NULL, 'active'),
('MH019', 'SP019', 30, '2025-02-01', '2028-02-01', 'active'),
('MH020', 'SP020', 30, '2025-06-01', NULL, 'active'),

-- 7. Mỹ phẩm & chăm sóc cơ thể
('MH021', 'SP021', 40, '2025-03-01', '2027-03-01', 'active'),
('MH022', 'SP022', 40, '2025-02-15', '2027-02-15', 'active'),
('MH023', 'SP023', 35, '2025-02-20', '2027-02-20', 'active'),

-- 8. Văn phòng phẩm & tiện ích
('MH024', 'SP024', 100, '2025-01-01', NULL, 'active'),
('MH025', 'SP025', 80, '2025-01-01', NULL, 'active'),
('MH026', 'SP026', 60, '2025-01-01', NULL, 'active'),

-- 9. Thức ăn & phụ kiện thú cưng
('MH027', 'SP027', 20, '2025-05-01', '2026-05-01', 'active'),
('MH028', 'SP028', 20, '2025-05-01', '2026-05-01', 'active'),
('MH029', 'SP029', 15, '2025-05-01', NULL, 'active'),

-- 10. Đồ y tế & chăm sóc sức khỏe
('MH030', 'SP030', 50, '2025-06-01', '2027-06-01', 'active'),
('MH031', 'SP031', 10, '2025-06-01', '2028-06-01', 'active'),
('MH032', 'SP032', 40, '2025-07-01', '2027-07-01', 'active');

-- Bảng NHANVIEN (Phải tạo trước vì PHIEUNHAP và HOADON tham chiếu đến)
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

-- Bảng KHACHHANG (Phải tạo trước vì HOADON tham chiếu đến)
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

-- Bảng NHACUNGCAP
CREATE TABLE NHACUNGCAP (
  MaNCC VARCHAR(20) PRIMARY KEY,
  TenNCC VARCHAR(255) NOT NULL,
  DiaChi VARCHAR(255) NOT NULL,
  DienThoai CHAR(10) NOT NULL,
  Email VARCHAR(40) NOT NULL,
  TrangThai ENUM('active', 'inactive') NOT NULL
);

INSERT INTO NHACUNGCAP (MaNCC, TenNCC, DiaChi, DienThoai, Email, TrangThai) VALUES
('NCC001', 'Công ty TNHH Vinamilk', '12 Đinh Tiên Hoàng, Q.1, TP.HCM', '0909123456', 'contact@vinamilk.com.vn', 'active'),
('NCC002', 'Công ty CP Acecook Việt Nam', '45 Nguyễn Văn Linh, TP. Cần Thơ', '0912123456', 'info@acecookvietnam.vn', 'active'),
('NCC003', 'Công ty TNHH Tân Hiệp Phát', '219 Đại lộ Bình Dương, Bình Dương', '0933456789', 'support@thp.com.vn', 'active'),
('NCC004', 'Công ty CP Masan Consumer', '9 Nguyễn Đình Chiểu, Q.1, TP.HCM', '0908787878', 'masan@consumer.vn', 'active'),
('NCC005', 'Công ty CP Bibica', '443 Lý Thường Kiệt, Q.10, TP.HCM', '0977888999', 'lienhe@bibica.vn', 'active'),
('NCC006', 'Công ty CP Sữa TH', 'Nghĩa Đàn, Nghệ An', '0944666777', 'thmilk@thgroup.vn', 'active'),
('NCC007', 'Công ty CP Vissan', '420 Nơ Trang Long, Q.Bình Thạnh, TP.HCM', '0909555444', 'info@vissan.com.vn', 'active');

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

INSERT INTO PHIEUNHAP (MaPhieu, MaNCC, MaNV, TongTien) VALUES
('PN001', 'NCC001', 'NV002', 2480000),
('PN002', 'NCC002', 'NV005', 1965000),
('PN003', 'NCC003', 'NV003', 1820000),
('PN004', 'NCC004', 'NV001', 2240000),
('PN005', 'NCC005', 'NV004', 4150000),
('PN006', 'NCC006', 'NV006', 3125000),
('PN007', 'NCC007', 'NV007', 3750000),
('PN008', 'NCC001', 'NV002', 1490000),
('PN009', 'NCC002', 'NV005', 2210000),
('PN010', 'NCC003', 'NV003', 4325000);

-- Bảng CHITIETPHIEUNHAP
CREATE TABLE CHITIETPHIEUNHAP (
  MaPhieu VARCHAR(50) NOT NULL,
  MaHang VARCHAR(20) NOT NULL,
  SoLuong INT(11) NOT NULL CHECK (SoLuong >= 0),
  GiaNhap INT(11) NOT NULL CHECK (GiaNhap >= 0),
  ThanhTien INT(11) NOT NULL CHECK (ThanhTien >= 0),
  PRIMARY KEY (MaPhieu, MaHang),
  CONSTRAINT fk_ctpn_phieu FOREIGN KEY (MaPhieu) REFERENCES PHIEUNHAP (MaPhieu) ON DELETE CASCADE,
  CONSTRAINT fk_ctpn_hang FOREIGN KEY (MaHang) REFERENCES HANGHOA (MaHang)
);

INSERT INTO CHITIETPHIEUNHAP (MaPhieu, MaHang, SoLuong, GiaNhap, ThanhTien) VALUES
('PN001', 'MH012', 100, 6000, 600000),
('PN001', 'MH013', 80, 6500, 520000),
('PN001', 'MH014', 70, 8000, 560000),
('PN001', 'MH015', 50, 16000, 800000),

('PN002', 'MH006', 200, 4000, 800000),
('PN002', 'MH007', 150, 4500, 675000),
('PN002', 'MH008', 100, 4900, 490000),

('PN003', 'MH001', 100, 7000, 700000),
('PN003', 'MH002', 80, 7000, 560000),
('PN003', 'MH033', 80, 7000, 560000),

('PN004', 'MH009', 60, 11000, 660000),
('PN004', 'MH010', 40, 23000, 920000),
('PN004', 'MH011', 60, 11000, 660000),

('PN005', 'MH018', 50, 10000, 500000),
('PN005', 'MH019', 40, 20000, 800000),
('PN005', 'MH020', 30, 35000, 1050000),
('PN005', 'MH021', 40, 45000, 1800000),

('PN006', 'MH027', 20, 70000, 1400000),
('PN006', 'MH028', 20, 75000, 1500000),
('PN006', 'MH029', 15, 15000, 225000),

('PN007', 'MH030', 50, 35000, 1750000),
('PN007', 'MH031', 10, 80000, 800000),
('PN007', 'MH032', 40, 30000, 1200000),

('PN008', 'MH004', 100, 6000, 600000),
('PN008', 'MH005', 60, 9000, 540000),
('PN008', 'MH034', 50, 7000, 350000),

('PN009', 'MH016', 50, 9000, 450000),
('PN009', 'MH017', 60, 3000, 180000),
('PN009', 'MH035', 60, 11000, 660000),
('PN009', 'MH036', 40, 23000, 920000),

('PN010', 'MH022', 40, 60000, 2400000),
('PN010', 'MH023', 35, 55000, 1925000);

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

CREATE TABLE CHITIETHOADON (
  MaHD VARCHAR(20) NOT NULL,
  MaHang VARCHAR(20) NOT NULL,
  SoLuong INT(11) NOT NULL CHECK (SoLuong >= 0),
  DonGia INT(11) NOT NULL CHECK (DonGia >= 0),
  ThanhTien INT(11) NOT NULL CHECK (ThanhTien >= 0),
  PRIMARY KEY (MaHD, MaHang),
  CONSTRAINT fk_cthd_hd FOREIGN KEY (MaHD) REFERENCES HOADON (MaHD) ON DELETE CASCADE,
  CONSTRAINT fk_cthd_sp FOREIGN KEY (MaHang) REFERENCES HANGHOA (MaHang)
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

-- Tạo index tối ưu tìm kiếm
CREATE INDEX idx_sanpham_ten ON SANPHAM(TenSP);
CREATE INDEX idx_sanpham_loai ON SANPHAM(Loai);
CREATE INDEX idx_sanpham_soLuongTon ON SANPHAM(SoLuongTon);

CREATE INDEX idx_hanghoa_masp ON HANGHOA(MaSP);
CREATE INDEX idx_hanghoa_trangthai ON HANGHOA(TrangThai);
CREATE INDEX idx_hanghoa_hansudung ON HANGHOA(HanSuDung);

CREATE INDEX idx_hoadon_kh ON HOADON (MaKH);
CREATE INDEX idx_hoadon_nv ON HOADON (MaNV);
CREATE INDEX idx_hoadon_thoigian ON HOADON (ThoiGianLapHD);

CREATE INDEX idx_cthd_hh ON CHITIETHOADON (MaHang);

CREATE INDEX idx_phieunhap_ncc ON PHIEUNHAP(MaNCC);
CREATE INDEX idx_phieunhap_nv ON PHIEUNHAP(MaNV);
CREATE INDEX idx_phieunhap_ngay ON PHIEUNHAP(NgayLapPhieu);

CREATE INDEX idx_chitietphieunhap_hh ON CHITIETPHIEUNHAP(MaHang);

CREATE INDEX idx_khachhang_dienthoai ON KHACHHANG (DienThoai);
CREATE INDEX idx_khachhang_trangthai ON KHACHHANG (TrangThai);

CREATE INDEX idx_nhanvien_chucvu ON NHANVIEN(ChucVu);
CREATE INDEX idx_nhanvien_trangthai ON NHANVIEN(TrangThai);

CREATE INDEX idx_taikhoan_manv ON TAIKHOAN(MaNV);
CREATE INDEX idx_taikhoan_vaitro ON TAIKHOAN(VaiTro);
