package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.JDBCUtil;
import dto.NhanVienDTO;

public class NhanVienDAO {
    public static NhanVienDTO timNhanVienTheoMa(String maNV) {
        String query = 
            "SELECT MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email, Luong, ChucVu, TrangThai " +
            "FROM NHANVIEN " +
            "WHERE MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new NhanVienDTO(
                    rs.getString("MaNV"),
                    rs.getString("Ho"),
                    rs.getString("Ten"),
                    rs.getString("GioiTinh"),
                    rs.getDate("NgaySinh").toLocalDate(),
                    rs.getString("DiaChi"),
                    rs.getString("Email"),
                    rs.getInt("Luong"),
                    rs.getString("ChucVu")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm nhân viên theo mã: " + e.getMessage());
        }
        return null;
    }
}

