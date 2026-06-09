<div align="center">

# 🏪 Quản Lý Cửa Hàng Tiện Lợi

### Hệ thống quản lý bán hàng cho cửa hàng tiện lợi — Đồ án Phát triển phần mềm hướng đối tượng

<br/>

<!-- Tech badges (logo = SVG icon từ simpleicons.org thông qua shields.io) -->
[![Java](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![JDBC](https://img.shields.io/badge/JDBC-API-FF6F00?style=for-the-badge&logo=databricks&logoColor=white)](https://docs.oracle.com/javase/tutorial/jdbc/)
[![MySQL](https://img.shields.io/badge/MySQL-InnoDB-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven--less](https://img.shields.io/badge/Build-javac%20%2B%20lib-blue?style=for-the-badge&logo=apachemaven&logoColor=white)](#-cài-đặt--chạy)

[![Architecture](https://img.shields.io/badge/Architecture-3--Layer%20(DAO%2FDTO%2FView)-6DB33F?style=flat-square&logo=spring&logoColor=white)](#-kiến-trúc-hệ-thống)
[![License](https://img.shields.io/badge/License-Educational-lightgrey?style=flat-square)](#-giấy-phép)
[![Status](https://img.shields.io/badge/Status-In%20Development-yellow?style=flat-square)](#-tiến-độ)
[![Platform](https://img.shields.io/badge/Interface-Console%20(CLI)-000000?style=flat-square&logo=gnubash&logoColor=white)](#-tính-năng-chính)

</div>

---

## 📑 Mục lục

- [Giới thiệu](#-giới-thiệu)
- [Tính năng chính](#-tính-năng-chính)
- [Tech Stack & Công cụ](#-tech-stack--công-cụ)
- [Kiến trúc hệ thống](#-kiến-trúc-hệ-thống)
- [Cấu trúc thư mục](#-cấu-trúc-thư-mục)
- [Mô hình cơ sở dữ liệu](#-mô-hình-cơ-sở-dữ-liệu)
- [Cài đặt & Chạy](#-cài-đặt--chạy)
- [Tài khoản mặc định](#-tài-khoản-mặc-định)
- [Tiến độ](#-tiến-độ)
- [Thành viên thực hiện](#-thành-viên-thực-hiện)
- [Giấy phép](#-giấy-phép)

---

## 🎯 Giới thiệu

**Quản Lý Cửa Hàng Tiện Lợi** là một ứng dụng console (CLI) viết bằng **Java thuần** kết nối **MySQL** qua **JDBC**, được phát triển trong khuôn khổ môn **Phát triển phần mềm hướng đối tượng**.

Hệ thống mô phỏng nghiệp vụ thực tế của một cửa hàng tiện lợi: quản lý danh mục sản phẩm, nhập hàng, bán hàng, theo dõi tồn kho, quản lý khách hàng & nhân viên, phân quyền tài khoản và xuất các báo cáo thống kê. Dự án chú trọng vào **tính nhất quán dữ liệu**, **xử lý đồng thời**, **audit log** và **xóa mềm (soft delete)** thay vì xóa cứng.

> 📊 Quy mô: **~15.000 dòng code Java** trải đều trên 3 tầng (DAO / DTO / View) với 12 bảng dữ liệu quan hệ.

---

## ✨ Tính năng chính

| Module | Mô tả |
| :----- | :---- |
| 🔐 **Tài khoản & Đăng nhập** | Xác thực, phân quyền `Admin` / `NhanVien`, mã hoá mật khẩu bằng **BCrypt**, bắt buộc đổi mật khẩu mặc định lần đầu |
| 📦 **Sản phẩm & Danh mục** | Quản lý sản phẩm, loại sản phẩm, đơn vị tính; theo dõi tồn kho |
| 🚚 **Nhà cung cấp** | Thêm / sửa / tìm kiếm nhà cung cấp |
| 📥 **Nhập hàng** | Lập phiếu nhập + chi tiết phiếu nhập, tính tổng tiền, cập nhật tồn kho (transaction + rollback) |
| 🧾 **Bán hàng** | Lập hoá đơn + chi tiết hoá đơn, tính tổng tiền, trừ tồn kho |
| 👥 **Khách hàng** | Quản lý thông tin khách hàng, trạng thái hoạt động |
| 🧑‍💼 **Nhân viên** | Quản lý nhân viên, ràng buộc nghiệp vụ (tuổi, SĐT, mã NV tự sinh) |
| 📈 **Báo cáo & Thống kê** | Doanh thu theo ngày, tồn kho, sản phẩm bán chạy, thống kê phiếu nhập theo NCC/NV/sản phẩm/thời gian |
| 📝 **Audit Log** | Ghi nhật ký thao tác (thêm / sửa / xóa) ra file `data/auditnhanvien.txt` |
| 🗑️ **Soft Delete** | Vô hiệu hoá bản ghi bằng trạng thái `inactive` thay vì xóa thật |

---

## 🛠 Tech Stack & Công cụ

<div align="center">

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-FF6F00?style=for-the-badge&logo=oracle&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![VS Code](https://img.shields.io/badge/VS%20Code-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white)

</div>

### Phiên bản trình biên dịch / runtime

| Thành phần | Phiên bản | Ghi chú |
| :--------- | :-------- | :------ |
| ☕ **Java (JDK)** | **8+** (đã build & test trên **OpenJDK 17**) | Ngôn ngữ chính |
| 🔨 **javac / java** | 17.0.x | Biên dịch & chạy trực tiếp, không dùng build tool |
| 🐬 **MySQL** | 8.x (engine **InnoDB**) | Cơ sở dữ liệu quan hệ |
| 🔌 **JDBC** | API chuẩn (`java.sql`) | Tầng truy cập dữ liệu |

### Thư viện / Package sử dụng

| Thư viện | Phiên bản | Package | Vai trò |
| :------- | :-------- | :------ | :------ |
| 📦 **MySQL Connector/J** | `9.4.0` (`lib/mysql-connector-j-9.4.0.jar`) | `com.mysql.cj.jdbc` | Driver JDBC kết nối MySQL |
| 🔒 **jBCrypt** | `0.4` (`lib/bcrypt-java-0.4.jar`) | `org.mindrot.jbcrypt` | Băm & kiểm tra mật khẩu (BCrypt) |
| 📚 **Java Standard Library** | JDK 8+ | `java.sql`, `java.time`, `java.io`, `java.util` | I/O, ngày giờ, collections, Scanner |

> Toàn bộ dependency được nhúng sẵn dưới dạng `.jar` trong thư mục [`lib/`](lib/) — không cần Maven/Gradle để chạy.

---

## 🏗 Kiến trúc hệ thống

Dự án áp dụng kiến trúc **3 tầng (3-Layer Architecture)** theo hướng đối tượng:

```
┌─────────────────────────────────────────────────────────┐
│                     VIEW (src/view)                       │
│   Menu console, nhập/xuất, điều phối luồng nghiệp vụ      │
│   QuanLyHoaDon · QuanLyNhapHang · QuanLyTaiKhoan · ...     │
└───────────────────────────┬─────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────┐
│                      DAO (src/dao)                        │
│   Truy vấn DB qua JDBC (PreparedStatement), transaction   │
│   HoaDonDAO · NhapHangDAO · SanPhamDAO · TaiKhoanDAO · ... │
└───────────────────────────┬─────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────┐
│                      DTO (src/dto)                        │
│   Đối tượng dữ liệu thuần (POJO) ánh xạ bảng DB           │
│   HoaDonDTO · SanPhamDTO · NhanVienDTO · TaiKhoanDTO · ... │
└─────────────────────────────────────────────────────────┘
        ▲                                          ▲
        │                                          │
┌───────┴────────┐                       ┌─────────┴─────────┐
│  UTIL (src/util)│                       │   MySQL Database  │
│ JDBCUtil ·      │  ◀── JDBC ──▶         │  (InnoDB, 12 bảng) │
│ AuditLogger ·   │                       └───────────────────┘
│ ValidatorUtil · │
│ FormatUtil ·    │
│ tablePrinter    │
└────────────────┘
```

- **View** — Tầng giao diện console: hiển thị menu, nhận input, gọi DAO.
- **DAO** — Tầng truy cập dữ liệu: thực thi SQL qua `PreparedStatement`, quản lý `Connection`/transaction.
- **DTO** — Đối tượng truyền dữ liệu (POJO) tương ứng từng bảng.
- **Util** — Tiện ích dùng chung: kết nối DB (`JDBCUtil`), kiểm tra hợp lệ (`ValidatorUtil`), định dạng bảng (`FormatUtil`, `tablePrinter`), ghi log (`AuditLogger`).

---

## 📂 Cấu trúc thư mục

```
QL-CuaHangTienLoi/
├── src/
│   ├── main/        # 🚀 Điểm vào chương trình (Main.java)
│   ├── view/        # 🖥️  Tầng giao diện console (9 module quản lý)
│   ├── dao/         # 🗄️  Tầng truy cập dữ liệu (10 DAO)
│   ├── dto/         # 📦 Đối tượng dữ liệu (12 DTO)
│   └── util/        # 🔧 Tiện ích: JDBC, Audit, Validator, Format
├── lib/             # 📚 Thư viện .jar (MySQL Connector/J, jBCrypt)
├── SQL/             # 🐬 Script tạo CSDL + dữ liệu mẫu (ql_chtienloi.sql)
├── data/            # 📄 File dữ liệu/log (audit, khách hàng)
├── docs/            # 📐 Tài liệu PTTK, Sequence Diagram (.drawio), báo cáo
├── DevNotes/        # 🗒️  Ghi chú phát triển & checklist
└── README.md
```

---

## 🗃 Mô hình cơ sở dữ liệu

Database `QL_chtienloi` gồm **12 bảng** (InnoDB) với khóa ngoại ràng buộc toàn vẹn:

| Bảng | Ý nghĩa |
| :--- | :------ |
| `LOAI` | Loại sản phẩm |
| `DONVI` | Đơn vị tính |
| `SANPHAM` | Sản phẩm |
| `HANGHOA` | Hàng hoá / lô hàng (NSX, HSD) |
| `NHANVIEN` | Nhân viên |
| `KHACHHANG` | Khách hàng |
| `NHACUNGCAP` | Nhà cung cấp |
| `PHIEUNHAP` | Phiếu nhập |
| `CHITIETPHIEUNHAP` | Chi tiết phiếu nhập |
| `HOADON` | Hoá đơn bán hàng |
| `CHITIETHOADON` | Chi tiết hoá đơn |
| `TAIKHOAN` | Tài khoản đăng nhập (phân quyền) |

---

## ⚙️ Cài đặt & Chạy

### Yêu cầu môi trường

- ☕ **JDK 8 trở lên** (khuyến nghị JDK 17)
- 🐬 **MySQL Server 8.x** đang chạy
- 📚 Các thư viện trong [`lib/`](lib/) (đã có sẵn trong repo)

### Bước 1 — Tải mã nguồn

```bash
git clone https://github.com/DZT711/QL-CuaHangTienLoi.git
cd QL-CuaHangTienLoi
```

### Bước 2 — Chuẩn bị cơ sở dữ liệu

Import script (đã tự tạo database `QL_chtienloi` và dữ liệu mẫu):

```bash
mysql -u root -p < SQL/ql_chtienloi.sql
```

### Bước 3 — Cấu hình kết nối

Mở `src/util/JDBCUtil.java` và chỉnh `url`, `username`, `password` cho phù hợp:

```java
String url = "jdbc:mysql://localhost:3306/QL_chtienloi";
String username = "root";
String password = "";   // đặt mật khẩu MySQL của bạn
```

### Bước 4 — Biên dịch & chạy (dòng lệnh)

```bash
# Biên dịch (đưa thư viện trong lib/ vào classpath)
javac -cp "lib/*" -d out $(find src -name "*.java")

# Chạy chương trình
java -cp "out:lib/*" main.Main      # Linux / macOS
java -cp "out;lib/*" main.Main      # Windows
```

> 💡 Hoặc mở dự án bằng **VS Code (Extension Pack for Java)**, **IntelliJ IDEA** hay **Eclipse**, thêm các `.jar` trong `lib/` vào classpath rồi chạy class `main.Main`.

---

## 🔑 Tài khoản mặc định

| Vai trò | Username | Password |
| :------ | :------- | :------- |
| 👑 Admin | `an` | `an` |
| 🧑‍💼 Nhân viên | `anh` | `anh` |

> ⚠️ Tài khoản dùng để demo/test. Hệ thống sẽ yêu cầu đổi mật khẩu nếu phát hiện mật khẩu mặc định.

---

## 📊 Tiến độ

| Module | Trạng thái |
| :----- | :--------- |
| Tài khoản & Đăng nhập (BCrypt, phân quyền) | ✅ Hoàn thành |
| Quản lý nhân viên | ⏳ Đang hoàn thiện |
| Quản lý sản phẩm / loại / đơn vị | ⏳ Đang hoàn thiện |
| Nhập hàng & chi tiết phiếu nhập | ⏳ Đang hoàn thiện |
| Bán hàng & hoá đơn | ⏳ Đang hoàn thiện |
| Khách hàng, nhà cung cấp | ⏳ Đang hoàn thiện |
| Báo cáo / thống kê | ⏳ Đang hoàn thiện |

---

## 👥 Thành viên thực hiện

| STT | MSSV | Họ và tên |
| :-: | :--- | :-------- |
| 1 | 3123411122 | Nguyễn Sĩ Huy |
| 2 | 3123411328 | Trương Văn Tuấn |
| 3 | 3123411258 | Đặng Thành Sơn |
| 4 | 3123411045 | Nguyễn Văn Cường |

---

## 📄 Giấy phép

Dự án được phát triển cho mục đích **học tập** trong môn *Phát triển phần mềm hướng đối tượng*.

<div align="center">

---

⭐ Nếu thấy hữu ích, hãy để lại một **star** cho repo nhé!

Made with ☕ Java & ❤️ by **Nhóm 11**

</div>
