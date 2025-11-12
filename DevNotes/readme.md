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
<!-- - **Giới hạn ký tự nhập**: .  ⏳ -->
- **Hạn chế HSD (ngày hết hạn)**: không cho nhập HSD ≤ ngày hiện tại — kiểm tra từ ứng dụng hoặc trigger.  ⏳

- **Thống nhất ngôn ngữ** sài 1 định dạng ngôn ngữ cho data / database.  ⏳
- **Thống nhất UI** sài 1 định dạng giao diện cho các menu admin & nhân viên và các menu khác.  ⏳
- **Menu nhân viên** .  ✔
- **Kiếm được tên sản phẩm bằng tiếng việt** .  ✔
- **Lỗi thống kê sản phẩm** .  ⏳
- **Lỗi xóa khách hàng**  xóa được nhưng sau đó xuất hiện lỗi :

- **Thêm ràng buộc khi nhập ngày**: VD khi nhập `29022025` thì ngày không hợp lệ còn nhập `29022024` thì là ngày hợp lệ (đã fix ở sản phẩm)
- **Thêm thoát trong khi thêm NV**:
- **Sửa SĐT hạn chế nhập bậy như `0000000000`**
- **Tên & Họ không chứa số & không chứa kí tự**
- **Đ/c không chứa kí tự đặc biệt ngoại trừ `\` `,` `.`**
- **Cho KH & NV có độ tuổi từ 5 trở lên (năm sinh tối thiểu từ 2020 trở đi )**
- **Kiểm tra lại việc xóa khách hàng thêm cột Status**
- **Lỗi thêm danh sách khách hàng**
- **NSX vượt qua ngày tạo phiếu nhập**
- **Ngày bắt đầu & ngày kết thúc tìm kiếm phiếu nhập vượt qua ngày đang tìm phiếu nhập**
- **Ngày bắt đầu khác ngày kết thúc tìm kiếm phiếu nhập vượt qua ngày đang tìm phiếu nhập**
- **Lỗi thêm nhà cung cấp Nhấn Y & N vẫn không thêm được nhà cung cấp**
- **Format bảng nhà cung cấp**
- **Format phiếu nhập**
- **Format TK phiếu nhập theo TG**
- **Format menu phiếu nhập**
- **Format THỐNG KÊ PHIẾU NHẬP THEO NHÀ CUNG CẤP**
- **Format THỐNG KÊ PHIẾU NHẬP THEO NHÂN VIÊN**
- **Format THỐNG KÊ PHIẾU NHẬP THEO SẢN PHẨM**
- **Format THỐNG KÊ PHIẾU NHẬP THEO THÁNG NĂM**
- **Format Menu HỆ THỐNG QUẢN LÝ CHI TIẾT PHIẾU NHẬP**
- **Format CHI TIẾT PHIẾU NHẬP trong tìm kiếm chi tiết PN**
- **Format DANH SÁCH CHI TIẾT PHIẾU NHẬP**
- **Format TOP SẢN PHẨM NHẬP NHIỀU NHẤT**
- **Format bảng KQ tìm kiếm phiếu nhập theo khoảng ngày**
- **Xuất file phieu nhap vào folder data thay vì main**
- **Xuất file báo cáo phieu nhap vào folder data thay vì main**
- **Cho phép nhập lại NSX & HSD nếu nhập sai trong phiếu nhập**
- **Thêm so sánh loại bỏ chữ hoa cho mã phiếu nhập trong khi tìm phiếu nhập bằng mã NV**
- **User input sai chỗ nào thì tb chỗ đó**
- **Cho HSD sp tối thiểu 1 tháng kể từ ngày nhập**
- **KT lại ràng buộc của ngày BĐ & ngày KT , ngày SX & HSD(trước sau ,trùng ngày , ngày nhuần 29/2)**
- **KT input Mã NV, Tên, địa chỉ**
- **Không thể ghi audit log: data\auditnhanvien.txt (The system cannot find the path specified)**

```cmd
🔄 Đang đọc file: data/khachhang.txt
❌ Lỗi khi đọc file: data\khachhang.txt (The system cannot find the path specified)
java.io.FileNotFoundException: data\khachhang.txt (The system cannot find the path specified)
        at java.base/java.io.FileInputStream.open0(Native Method)
        at java.base/java.io.FileInputStream.open(FileInputStream.java:185)
        at java.base/java.io.FileInputStream.<init>(FileInputStream.java:139)
        at java.base/java.io.FileInputStream.<init>(FileInputStream.java:109)
        at dao.KhachHangDAO.importDSKH(KhachHangDAO.java:301)
        at view.QuanLyKhachHang.menuQuanLyKhachHang(QuanLyKhachHang.java:82)
        at main.Main.menuAdmin(Main.java:175)
        at main.Main.main(Main.java:93)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
        at java.base/java.lang.reflect.Method.invoke(Method.java:565)
        at jdk.compiler/com.sun.tools.javac.launcher.SourceLauncher.execute(SourceLauncher.java:254)
        at jdk.compiler/com.sun.tools.javac.launcher.SourceLauncher.run(SourceLauncher.java:138)
        at jdk.compiler/com.sun.tools.javac.launcher.SourceLauncher.main(SourceLauncher.java:76)
```

```cmd
📝 Nhập ngày sinh (dd/MM/yyyy) - Enter để bỏ qua: 29022025
❌ Định dạng ngày không đúng! Vui lòng nhập theo định dạng dd/MM/yyyy
📝 Nhập ngày sinh (dd/MM/yyyy) - Enter để bỏ qua: 29022024
❌ Định dạng ngày không đúng! Vui lòng nhập theo định dạng dd/MM/yyyy
📝 Nhập ngày sinh (dd/MM/yyyy) - Enter để bỏ qua: 31102025
❌ Định dạng ngày không đúng! Vui lòng nhập theo định dạng dd/MM/yyyy
📝 Nhập ngày sinh (dd/MM/yyyy) - Enter để bỏ qua: 31102005
❌ Định dạng ngày không đúng! Vui lòng nhập theo định dạng dd/MM/yyyy
```

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
