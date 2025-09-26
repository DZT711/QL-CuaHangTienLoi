QUẢN LÝ CỬA HÀNG TIỆN LỢI
Đồ án môn: Phát triển phần mềm hướng dịch vụ
Ngôn ngữ: Java + JDBC
Cơ sở dữ liệu: MySQL

--------------------------------------------
I. Thành viên thực hiện:
| STT | MSSV       | Họ và tên            | % Đóng góp |
|-----|------------|----------------------|------------|
| 1   | 3123411122 | Nguyễn Sĩ Huy        |            |
| 2   | 3123411328 | Trương Văn Tuấn      |            |
| 3   | 3123411258 | Đặng Thành Sơn       |            |
| 4   | 3123411045 | Nguyễn Văn Cường     |            |

--------------------------------------------
II. Đề bài / mô tả hệ thống

Xây dựng một hệ thống quản lý bán hàng cho cửa hàng tiện lợi, hỗ trợ các chức năng chính:
- Quản lý loại sản phẩm, đơn vị tính, nhà cung cấp.
- Nhập hàng: lập phiếu nhập, chi tiết phiếu nhập, tính tổng tiền, cập nhật tồn kho.
- Bán hàng: lập hoá đơn, chi tiết hoá đơn, tính tổng tiền, giảm tồn kho.
- Quản lý khách hàng, nhân viên.
- Quản lý tài khoản (login, phân quyền Admin / Nhân viên).
- Báo cáo cơ bản (doanh thu theo ngày, tồn kho, sản phẩm bán chạy).
- Đảm bảo tính nhất quán dữ liệu, xử lý đồng thời khi nhiều người bán cùng sản phẩm.

--------------------------------------------
III. Hướng dẫn cài đặt và chạy

1. Cài đặt MySQL, tạo database (ví dụ: `qlcuahang`), import file `qlcuahangtienloi.sql` (phiên bản đã sửa).  
2. Cấu hình file kết nối trong Java (JDBC): URL, username, mật khẩu.  
3. Chạy chương trình Java (IDE hoặc jar).  
4. Đăng nhập tài khoản admin để vào hệ thống.  
5. Nhập dữ liệu mẫu nếu cần, rồi thử các chức năng: quản lý sản phẩm, nhập hàng, bán hàng.

--------------------------------------------
IV. Kiến trúc & công nghệ sử dụng

- Java (phiên bản 8 trở lên)  
- JDBC (PreparedStatement, Connection pooling)  
- MySQL (InnoDB)  
- Giao diện: (GUI / console)  


--------------------------------------------
V. Ghi chú & hướng phát triển

- Có thể thêm chức năng khuyến mãi, giảm giá theo chương trình, tích điểm khách hàng.  
- Cải thiện giao diện, thêm báo cáo chi tiết / biểu đồ.  
- Bổ sung bảo mật (log out, xác thực session, mã hoá kết nối).  
- Cải thiện hiệu năng: paging, caching, Query tối ưu.
- Thêm rollback(),commit() cho phần sử lý với database để tránh lỗi phát sinh trong quá trình sửa đổi dữ liệu
--------------------------------------------


