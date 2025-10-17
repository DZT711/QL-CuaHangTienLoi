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

---

## ⚠ Các vấn đề, ghi chú cần được sửa/cải tiến

- **Không xóa dữ liệu thật**: dùng cột `TrangThai / Status` thay vì `DELETE`, để tránh mất dữ liệu lịch sử. ✔  
- **Xác nhận trước khi xóa/ chỉnh dữ liệu** từ UI: hiển thị popup “Bạn có chắc muốn xóa / chỉnh sửa?”  ✔
- **Xóa phiếu nhập → xóa chi tiết phiếu nhập** theo cascade hoặc trigger để giữ tính liên kết.  ⏳
- **Giới hạn ký tự nhập**: .  ⏳
- **Hạn chế HSD (ngày hết hạn)**: không cho nhập HSD ≤ ngày hiện tại — kiểm tra từ ứng dụng hoặc trigger.  ⏳

- **Thống nhất ngôn ngữ** sài 1 định dạng ngôn ngữ cho data / database.  ⏳
- **Thống nhất UI** sài 1 định dạng giao diện cho các menu admin & nhân viên và các menu khác.  ⏳
- **Menu nhân viên** .  ✔
- **Chưa kiếm được tên sản phẩm bằng tiếng việt** .  ⏳
- **Lỗi thống kê sản phẩm** .  ⏳
- **Lỗi xóa khách hàng**  xóa được nhưng sau đó xuất hiện lỗi :

```cmd
Nhập lựa chọn của bạn: Vui lòng nhập số hợp lệ.
Exception in thread "main" java.util.NoSuchElementException
        at java.base/java.util.Scanner.throwFor(Scanner.java:962)
        at java.base/java.util.Scanner.next(Scanner.java:1503)
        at view.QuanLyKhachHang.menuQuanLyKhachHang(QuanLyKhachHang.java:50)
        at main.Main.menuAdmin(Main.java:142)
        at main.Main.main(Main.java:67)
```

- **Lỗi thêm danh sách khách hàng** Lỗi khi đọc file: data\khachhang.txt (The system cannot find the path specified) .  ⏳
- **Thêm menu chỉnh sản phẩm để tiện hơn cho việc chỉnh sửa** .  ⏳
- **Thêm khả năng đổi trạng thái sản phẩm từ `inactive -> active`** .  ⏳
- **Thống nhất 1 ngôn ngữ cho data** vd loại bỏ `active/inactive` thành `có sẵn/hết hàng/vô hiệu hóa` .  ⏳
- **Thống nhất 1 giao diện cho các kết quả tìm kiếm khách hàng** .  ⏳
- **Lỗi lặp vô tận khi nhà cung cấp không tồn tại trong khi tạo phiếu nhập không thể thoát dù nhập n** .  ⏳
- **Lỗi khi thêm chi tiết phiếu nhập:** .

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
