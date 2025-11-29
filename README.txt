QUẢN LÝ CỬA HÀNG TIỆN LỢI
Đồ án môn: Phát triển phần mềm hướng dịch vụ
Ngôn ngữ: Java + JDBC
Cơ sở dữ liệu: MySQL

--------------------------------------------
I. Thành viên thực hiện:
| STT | MSSV       | Họ và tên            | 
|-----|------------|----------------------|
| 1   | 3123411122 | Nguyễn Sĩ Huy        |          
| 2   | 3123411328 | Trương Văn Tuấn      |          
| 3   | 3123411258 | Đặng Thành Sơn       |          
| 4   | 3123411045 | Nguyễn Văn Cường     |       

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
V. Hướng dẫn cài đặt và sử dụng phần mềm 

Yêu cầu trước khi cài đặt
Cài đặt MySQL (bản ổn định) trên máy của bạn.
Có driver JDBC cho MySQL (ví dụ mysql-connector.jar) — thư mục lib/ trong repo chứa thư viện cần thiết.
Có một IDE Java như Eclipse, IntelliJ IDEA, hoặc sử dụng Visual Studio Code + plugin Java.
Java JDK (phiên bản 8 trở lên) — phù hợp với code Java của dự án.

Bước 1 — Tải source về máy
git clone https://github.com/DZT711/QL-CuaHangTienLoi.git
Hoặc tải file ZIP từ GitHub và giải nén vào thư mục bạn chọn.

Bước 2 — Chuẩn bị cơ sở dữ liệu
Mở MySQL, tạo một database mới — ví dụ đặt tên qlcuahang.
Trong repo, tìm file SQL (file SQL/qlcuahangtienloi.sql). Import file này vào database "ql_chtienloi" bạn vừa tạo để tạo các bảng cần thiết và (nếu có) dữ liệu ban đầu.

Bước 3 — Cấu hình kết nối từ Java
Mở file cấu hình kết nối (thường trong code: nơi khai báo URL, username, password để kết nối JDBC).
Đảm bảo driver MySQL (ví dụ mysql-connector.jar) được thêm vào classpath / thư viện của project — tùy IDE bạn dùng, cách thêm có thể khác nhau.
Ví dụ: trong IDE VSCode thì cài extension Java Project , sau đó thêm đường dãn tới driver đã có sẵn trong lib/mysql-connector.jar 

Bước 4 — Chạy ứng dụng
Mở dự án trong IDE (Eclipse / IntelliJ / VSCode + plugin Java).
Thiết lập cấu hình run: chọn main class đúng (src/main/main.java) .
Nếu setup đúng — hệ thống sẽ kết nối tới database và chạy.
Đăng nhập bằng tài khoản quản trị viên (username :an / password :an) để truy cập hệ thống
hoặc
Đăng nhập bằng tài khoản nhân viên test (username :anh / password :anh ) để truy cập hệ thống.

Bước 5 — Kiểm tra và sử dụng
Sau khi chạy thành công, bạn có thể thử các chức năng: quản lý sản phẩm, nhập hàng, bán hàng, quản lý khách/nhân viên, báo cáo,...
