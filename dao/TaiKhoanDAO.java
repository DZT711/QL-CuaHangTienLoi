package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.TaiKhoanDTO;
import database.JDBCUtil;

public class TaiKhoanDAO {
    public static TaiKhoanDTO checkAccount(String username, String password) {
        TaiKhoanDTO taiKhoan = null;

        String query = "SELECT * FROM TAIKHOAN WHERE UserName = ? AND PassWord = ?";

        try {
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if(rs.next()){
                String role = rs.getString("VaiTro");
                // String maNV = rs.getString("MaNV");
                taiKhoan = new TaiKhoanDTO(username, password, role);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra tài khoản: " + e.getMessage());
        }
        return taiKhoan;
    }
}
