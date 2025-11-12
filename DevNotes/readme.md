# Quản Lý Cửa Hàng Tiện Lợi

**Đồ án môn:** Phân tích thiết kế hệ thống hướng dối tượng
**Ngôn ngữ:** Java + JDBC  
**Cơ sở dữ liệu:** MySQL  

---

## Progress: 55% ⏳

## 📌 Mô tả & phạm vi dự án

Xây dựng hệ thống quản lý bán hàng cho cửa hàng tiện lợi, hỗ trợ:

- Quản lý loại sản phẩm, đơn vị tính, nhà cung cấp  ⏳
- Nhập hàng, bán hàng, quản lý tồn kho  ⏳
- Quản lý khách hàng, nhân viên  ⏳
- Quản lý tài khoản (login, phân quyền)  ✔
- Báo cáo doanh thu, tồn kho, sản phẩm bán chạy   ⏳
- Đảm bảo tính nhất quán, xử lý đồng thời, audit, rollback, status thay vì xóa thật  ⏳
- Các chức năng cho menu nhân viên (bán hàng, xem sản phẩm,....)
- Các chứa năng cho menu admin (xem báo cáo / thống kê)

---

## ⚠ Các vấn đề, ghi chú cần được sửa/cải tiến

## Mục lục

1. [Sản Phẩm (SanPham)](#sản-phẩm-sanpham)
2. [Khách Hàng (KhachHang)](#khách-hàng-khachhang)
3. [Nhân Viên (NhanVien)](#nhân-viên-nhanvien)
4. [Phiếu Nhập (PhieuNhap)](#phiếu-nhập-phieunhap)
5. [Nhà Cung Cấp (NhaCungCap)](#nhà-cung-cấp-nhacungcap)
6. [Hóa Đơn (HoaDon)](#hóa-đơn-hoadon)
7. [Tài Khoản (TaiKhoan)](#tài-khoản-TaiKhoan)
8. [Chung (All)](#chung-all)

---

## Sản Phẩm (SanPham)

### ✅ Hoàn thành

- Kiếm được tên sản phẩm bằng tiếng Việt
- Thêm ràng buộc khi nhập ngày (29/02 năm nhuận hợp lệ, 31/02 không hợp lệ)
- Menu sửa sản phẩm (thêm / sửa / đổi trạng thái)
- Đổi trạng thái sản phẩm từ `inactive → active`

### ⏳ Cần làm (ưu tiên)

- **[CAO]** Fix lỗi `No operations allowed after connection closed` khi xuất danh sách sản phẩm
  - Lỗi: DAO method không giữ Connection mở khi trả List
  - Fix: dùng try-with-resources, map ResultSet → DTO trước khi close Connection
- **[CAO]** Lỗi thống kê sản phẩm (thống kê theo loại / bán chạy)
- **[TRUNG]** Thống nhất 1 ngôn ngữ cho data: loại bỏ `active/inactive` → `có sẵn/hết hàng/vô hiệu hóa`
- **[TRUNG]** Format bảng danh sách sản phẩm (đồng bộ với bảng khác)
- **[THẤP]** HSD sản phẩm tối thiểu 1 tháng kể từ ngày nhập

### Ghi chú

- Dùng cột `TrangThai` thay vì xóa thật
- NSX không được vượt quá ngày tạo phiếu nhập

---

## Khách Hàng (KhachHang)

### ✅ Hoàn thành

- Menu khách hàng cơ bản
- Xác nhận trước khi xóa / chỉnh sửa

### ⏳ Cần làm (ưu tiên)

- **[CAO]** Lỗi thêm danh sách khách hàng: `FileNotFoundException: data\khachhang.txt`
  - Nguyên nhân: thư mục `data/` không tồn tại
  - Fix: tạo thư mục `data/` trong project root; kiểm tra đường dẫn absolute
- **[CAO]** Fix lỗi xóa khách hàng (xuất hiện lỗi sau khi xóa)
  - Thêm cột `Status` thay vì xóa thực sự
- **[TRUNG]** Thống nhất giao diện cho các kết quả tìm kiếm khách hàng
- **[TRUNG]** Kiểm tra input Mã KH, Tên (không chứa số / ký tự đặc biệt)
- **[TRUNG]** Tuổi KH tối thiểu 5 (năm sinh tối đa 2019)
- **[THẤP]** Địa chỉ không chứa ký tự đặc biệt (ngoại trừ `\`, `,`, `.`)

### Ghi chú

- Scanner exception khi input sai: `java.util.NoSuchElementException`
  - Fix: đặt check `hasNextInt()` trước `nextInt()`
- Import file: kiểm tra encoding UTF-8

---

## Nhân Viên (NhanVien)

### ✅ Hoàn thành

- Menu nhân viên
- Xác nhận trước khi xóa / chỉnh sửa

### ⏳ Cần làm (ưu tiên)

- **[CAO]** Lỗi ghi audit log: `data\auditnhanvien.txt (The system cannot find the path specified)`
  - Fix: tạo thư mục `data/`; kiểm tra quyền ghi
- **[CAO]** Không thể thoát trong khi thêm NV (lặp vô tận)
  - Fix: thêm option thoát (nhập `0` hoặc `n`)
- **[TRUNG]** Tự động tạo mã NV khi thêm NV (ví dụ: `NV001`, `NV002`, ...)
- **[TRUNG]** Nhân viên tối thiểu 18 tuổi (năm sinh tối đa 2006)
- **[TRUNG]** SĐT hạn chế nhập chuỗi như `0000000000`
  - Check: SĐT phải có ít nhất 2 chữ số khác nhau, độ dài 10
- **[TRUNG]** Tên & Họ không chứa số & ký tự đặc biệt
- **[TRUNG]** Địa chỉ không chứa ký tự đặc biệt (ngoại trừ `\`, `,`, `.`)
- **[TRUNG]** Format DANH SÁCH NHÂN VIÊN (đồng bộ UI)
- **[TRUNG]** Format THÔNG TIN NHÂN VIÊN
- **[THẤP]** Kiểm tra lại ràng buộc mã NV, tên, địa chỉ (case-insensitive)
- **[THẤP]** Thêm phần enter bỏ qua cho địa chỉ nv khi thêm
- **[THẤP]** Cho lương nv tối thiểu 1đ

### Ghi chú

- Tuổi tối thiểu từ 18 trở lên (không phải 5)
- Audit log cho các thao tác: thêm, sửa, xóa

---

## Phiếu Nhập (PhieuNhap)

### ✅ Hoàn thành

- (Còn ít)

### ⏳ Cần làm (ưu tiên)

- **[CAO]** Lỗi lặp vô tận khi nhà cung cấp không tồn tại
  - Fix: thêm option thoát hoặc kiểm tra NCC trước khi tạo PN
- **[CAO]** Lỗi thêm chi tiết phiếu nhập (check foreign key)
  - Fix: transaction + rollback; kiểm tra tồn tại `MaPhieu` trước insert
- **[TRUNG]** NSX vượt qua ngày tạo phiếu nhập
  - Fix: validate NSX ≤ ngày hiện tại
- **[TRUNG]** Ngày bắt đầu & ngày kết thúc tìm kiếm phiếu nhập vượt qua ngày đang tìm
  - Fix: ngày BĐ ≤ ngày KT ≤ ngày hiện tại; hoặc check trong trigger DB
- **[TRUNG]** Cho phép nhập lại NSX & HSD nếu nhập sai trong phiếu nhập
- **[TRUNG]** So sánh case-insensitive cho mã phiếu nhập khi tìm kiếm
- **[TRUNG]** Format phiếu nhập (UI)
- **[TRUNG]** Format TK phiếu nhập theo thời gian
- **[TRUNG]** Format menu phiếu nhập
- **[TRUNG]** Format THỐNG KÊ PHIẾU NHẬP THEO NHÀ CUNG CẤP
- **[TRUNG]** Format THỐNG KÊ PHIẾU NHẬP THEO NHÂN VIÊN
- **[TRUNG]** Format THỐNG KÊ PHIẾU NHẬP THEO SẢN PHẨM
- **[TRUNG]** Format THỐNG KÊ PHIẾU NHẬP THEO THÁNG/NĂM
- **[TRUNG]** Format Menu HỆ THỐNG QUẢN LÝ CHI TIẾT PHIẾU NHẬP
- **[TRUNG]** Format CHI TIẾT PHIẾU NHẬP trong tìm kiếm
- **[TRUNG]** Format DANH SÁCH CHI TIẾT PHIẾU NHẬP
- **[TRUNG]** Format TOP SẢN PHẨM NHẬP NHIỀU NHẤT
- **[THẤP]** Xuất file phiếu nhập vào folder `data/` thay vì main
- **[THẤP]** Xuất file báo cáo phiếu nhập vào folder `data/` thay vì main

### Ghi chú

- Cascade delete: xóa PN → xóa chi tiết PN (DB trigger)
- HSD SP tối thiểu 1 tháng kể từ ngày nhập
- Kiểm tra date validation (29/02, khoảng ngày hợp lệ)

---

## Nhà Cung Cấp (NhaCungCap)

### ✅ Hoàn thành

- (Còn ít)

### ⏳ Cần làm (ưu tiên)

- **[TRUNG]** Lỗi thêm nhà cung cấp: nhấn Y & N vẫn không thêm được
  - Fix: xác nhận input, debug logic
- **[TRUNG]** Format bảng nhà cung cấp (đồng bộ UI)
- **[TRUNG]** Kiểm tra input: Tên NCC (không chứa số / ký tự đặc biệt)
- **[TRUNG]** Địa chỉ không chứa ký tự đặc biệt (ngoại trừ `\`, `,`, `.`)
- **[THẤP]** SĐT NCC: hạn chế chuỗi như `0000000000`

### Ghi chú

- Dùng cột `Status` thay vì xóa thật
- Liên kết với PN (kiểm tra FK)

---

## Hóa Đơn (HoaDon)

### ✅ Hoàn thành

- Menu hóa đơn cơ bản

### ⏳ Cần làm (ưu tiên)

- **[TRUNG]** Format bảng hóa đơn
- **[TRUNG]** Kiểm tra date validation (ngày xuất hóa đơn ≤ ngày hiện tại)
- **[THẤP]** Xuất báo cáo hóa đơn vào `data/`

### Ghi chú

- Liên kết: HD ← KH, NV
- Dùng cột `Status` cho tình trạng HD (chưa thanh toán, đã thanh toán, hủy)

---

## Tài Khoản (TaiKhoan)

### ✅ Hoàn thành

- Đăng nhập với role (Admin / NV)
- Thêm welcome message theo giờ (buổi sáng, trưa, chiều, tối)

### ⏳ Cần làm (ưu tiên)

- **[THẤP]** Format màn hình đăng nhập (nếu cần)

### Ghi chú

- Role: `Admin` / `NhanVien`
- Greeting icon & time từ `xinchaoDAO`

---

## Chung (All)

### ✅ Hoàn thành

- Không xóa dữ liệu thật (dùng `Status`)
- Xác nhận trước xóa / chỉnh sửa
- Menu nhân viên (thiết kế UI)

### ⏳ Cần làm (ưu tiên chung)

- **[CAO]** Tạo thư mục `data/` trong project root
  - Kiểm tra: `mkdir data` hoặc tạo thủ công
  - File import/export: khách hàng, nhân viên, báo cáo
- **[CAO]** Fix Scanner exception: `java.util.NoSuchElementException`
  - Fix: check `hasNext*()` trước `.next*()`; không close `System.in`
- **[TRUNG]** Thống nhất ngôn ngữ: loại bỏ `active/inactive` → `có sẵn/hết hàng/vô hiệu hóa`
- **[TRUNG]** Thống nhất giao diện (menu, bảng, thông báo)
- **[TRUNG]** User input sai → thông báo chỗ sai (chi tiết hơn)
- **[TRUNG]** Date validation chung: dd/MM/yyyy; kiểm tra năm nhuận, khoảng ngày
- **[TRUNG]** DAO pattern: try-with-resources cho Connection/PreparedStatement/ResultSet
- **[THẤP]** Unit test: parse date, validate input, DAO basic ops
- **[TRUNG]** Format QuanLiTaiKhoan
- **[THấP]** Nhập mk để đổi mk tài khoản
- **[Không cần thiết]** MK mới khác mk cũ
- **[Không cần thiết]** Che MK

### Error Logs & Fix Tips

```cmd
🔄 FileNotFoundException: data\khachhang.txt
→ Fix: mkdir data; update path to absolute or check working directory

🔄 Scanner.NoSuchElementException
→ Fix: check hasNextInt() before nextInt(); avoid closing System.in

🔄 No operations allowed after connection closed
→ Fix: DAO method return List INSIDE try-with-resources; close conn after mapping

🔄 Date parsing: 29/02/2025 invalid → 29/02/2024 valid
→ Check: leap year; use LocalDate.parse with ResolverStyle.STRICT

🔄 Cannot add child row (FK constraint)
→ Fix: transaction; check parent record exists before insert child
```

---

## Priority Matrix (ưu tiên)

| Mức độ | Module | Task | Estimate |
|-------|--------|------|----------|
| 🔴 CAO | SanPham | Fix "No operations after closed" | 2h |
| 🔴 CAO | KhachHang | Fix FileNotFoundException `data/` | 1h |
| 🔴 CAO | NhanVien | Fix audit log path | 1h |
| 🔴 CAO | PhieuNhap | Fix NCC loop + FK check | 3h |
| 🟡 TRUNG | All | Thống nhất Status language | 4h |
| 🟡 TRUNG | All | Thống nhất UI/format | 5h |
| 🟡 TRUNG | All | Validate date + input | 3h |
| 🟢 THẤP | All | Export files `data/` | 2h |

---

```sql
Cannot add or update a child row: a foreign key constraint fails (`ql_chtienloi`.`chitietphieunhap`, CONSTRAINT `fk_ctpn_phieu` FOREIGN KEY (`MaPhieu`) REFERENCES `phieunhap` (`MaPhieu`) ON DELETE CASCADE)
```

- **Thêm khả năng Handling Transactions** dùng `commit()` `rollback()` giúp tránh lỗi cho DAO .  ⏳

- **Tìm kiếm tên nhà cung cấp chưa đúng chuỗi**  .  ⏳
- **Báo lỗi ngay sau ki nhập sai SĐT & email khi thêm NCC**  .  ⏳
- **Lặp vô hạn sau khi sửa xong 1 NCC thêm (y/n)**  .  ⏳

---

## 🛠 Quy trình & Kiến trúc 3 lớp

### 🔁 Quy trình chạy chương trình (Flow – từ Main → View → DTO → DAO)

### Kiến trúc (dự định ⏳)

<pre>
src/
 ├── main/
 │    ├── view/         ← các lớp giao diện / UI
 │    ├── dto/          ← các lớp DTO
 │    ├── util/         ← các lớp util hay dùng để import vào các file
 │    └── dao/          ← lớp DAO / truy xuất DB
 ├── test/              ← viết unit test
 ├── SQL/               ← source code của database đồ án
 ├── database/          ← class connect tới database
 ├── DevNotes/          ← note cho contributors
 ├── lib/               ← thư viện .jar
 └── docs/              ← tài liệu báo cáo đồ án : file word, sơ đồ uml,...
</pre>
---

## 🏗 Kiến trúc & thiết kế lớp (mô hình 3 lớp)

- **DTO (Data Transfer Objects)**: các lớp đơn giản chứa dữ liệu (ví dụ: `SanPhamDTO`, `KhachHangDTO`).  ⏳
- **VIEW (BUS / BLL)**: lớp xử lý nghiệp vụ — insert, update, delete, kiểm tra hợp lệ, điều phối workflow.  ⏳
- **DAO / DAL**: lớp thực thi SQL / JDBC (PreparedStatement, Transaction), mapping DTO ↔ DB.⏳

Ví dụ (Java pseudocode):

```java
public class EmployeeDTO {
    private String employeeID;
    private String name;
    private String email;
    private double salary;
    // getters và setters
}

public class EmployeeBUS {
    public static boolean insert(EmployeeDTO e) {
        if (EmployeeDAO.exists(e.getEmployeeID())) {
            return false;
        }
        return EmployeeDAO.insert(e);
    }
    // update, delete, select
}

public class EmployeeDAO {
    public static boolean insert(EmployeeDTO e) {
        String sql = "INSERT INTO NHANVIEN (MaNV, Ho, Ten, ...) VALUES (?, ?, ?, ...)";
        // dùng PreparedStatement, transaction nếu cần
        // thực thi và trả về kết quả
    }
}
```

## ✅ Test case thiết yếu (Test Suite) ⏳

<!-- ... (giữ phần test case như trước)  

--- -->

## 🚀 Hướng phát triển & mở rộng ⏳

<!-- ... (phần hướng phát triển như trước)  

--- -->
