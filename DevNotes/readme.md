# Quản Lý Cửa Hàng Tiện Lợi

**Đồ án môn:** Phát triển phần mềm hướng dịch vụ  
**Ngôn ngữ:** Java + JDBC  
**Cơ sở dữ liệu:** MySQL  

---

## Progress: 5% ⏳

## 📌 Mô tả & phạm vi dự án

Xây dựng hệ thống quản lý bán hàng cho cửa hàng tiện lợi, hỗ trợ:

- Quản lý loại sản phẩm, đơn vị tính, nhà cung cấp  ⏳
- Nhập hàng, bán hàng, quản lý tồn kho  ⏳
- Quản lý khách hàng, nhân viên  ⏳
- Quản lý tài khoản (login, phân quyền)  ⏳
<!-- - Báo cáo doanh thu, tồn kho, sản phẩm bán chạy   -->
<!-- - Đảm bảo tính nhất quán, xử lý đồng thời, audit, rollback, status thay vì xóa thật   -->

---

## ⚠ Các vấn đề thực tế và giải pháp được áp dụng

- **Không xóa dữ liệu thật**: dùng cột `TrangThai / Status` thay vì `DELETE`, để tránh mất dữ liệu lịch sử.⏳  
- **Xác nhận trước khi xóa dữ liệu** từ UI: hiển thị popup “Bạn có chắc muốn xóa?”  ⏳
- **Xóa phiếu nhập → xóa chi tiết phiếu nhập** theo cascade hoặc trigger để giữ tính liên kết.  ⏳
- **Giới hạn độ ký tự nhập**: thống nhất dùng `VARCHAR(255)` cho phần lớn cột chuỗi.  ⏳
- **Hạn chế HSD (ngày hết hạn)**: không cho nhập HSD ≤ ngày hiện tại — kiểm tra từ ứng dụng hoặc trigger.  ⏳
- **Cấu trúc mô hình 3 lớp (Presentation – Business – Data Access)** giúp tách biệt logic giao diện, nghiệp vụ, truy xuất dữ liệu dễ bảo trì.  ⏳

---

## 🛠 Quy trình & Kiến trúc 3 lớp

### 🔁 Quy trình chạy chương trình (Flow – từ Main → View → DTO → DAO)

### Kiến trúc (dự định ⏳)
<pre>````
src/
 ├── main/
 │    ├── java/
 │    │    ├── app/          ← chứa Main.java
 │    │    ├── view/         ← các lớp giao diện / UI
 │    │    ├── dto/          ← các lớp DTO
 │    │    ├── bus/          ← lớp BUS / logic nghiệp vụ
 │    │    └── dao/          ← lớp DAO / truy xuất DB
 │    └── resources/         ← file cấu hình (db.properties, log config...)
 ├── test/                   ← viết unit test
 ├── datebase/               ← source code của database đồ án
 └── docs/                   ← tài liệu báo cáo đồ án : file word, sơ đồ uml,...
````</pre>
---

## 🏗 Kiến trúc & thiết kế lớp (mô hình 3 lớp)

- **DTO (Data Transfer Objects)**: các lớp đơn giản chứa dữ liệu (ví dụ: `SanPhamDTO`, `KhachHangDTO`).  ⏳
- **BUS / BLL**: lớp xử lý nghiệp vụ — insert, update, delete, kiểm tra hợp lệ, điều phối workflow.  ⏳
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

