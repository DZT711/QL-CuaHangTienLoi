package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.TaiKhoanDTO;
import util.JDBCUtil;

public class TaiKhoanDAO {
    public static TaiKhoanDTO checkAccount(String username, String password) {
        TaiKhoanDTO taiKhoan = null;

        String query = "SELECT * FROM TAIKHOAN WHERE UserName = ? AND PassWord = ?";

        try {
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            rs = stmt.executeQuery();

            if(rs.next()){
                String role = rs.getString("VaiTro");
                String fullName = rs.getString("HoTen");
                // String maNV = rs.getString("MaNV");
                taiKhoan = new TaiKhoanDTO(username, password, role, fullName);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra tài khoản: " + e.getMessage());
        }
        return taiKhoan;
    }
}
