package dto;

public class TaiKhoanDTO {
    private String username;
    private String password;
    private String maNV;
    private String role;
    private String status;
    private String fullName;
    private boolean isDefaultPassword;

    public TaiKhoanDTO() {
    }

    public TaiKhoanDTO(String username, String password, String maNV, String role, String fullName, String status) {
        this.username = username;
        this.password = password;
        this.maNV = maNV;
        this.role = role;
        this.fullName = fullName;
        this.status = status;
        this.isDefaultPassword = false; // Mặc định là false
    }

    public TaiKhoanDTO(String username, String password, String maNV, String role, String fullName, String status,
            boolean isDefaultPassword) {
        this.username = username;
        this.password = password;
        this.maNV = maNV;
        this.role = role;
        this.fullName = fullName;
        this.status = status;
        this.isDefaultPassword = isDefaultPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDefaultPassword() {
        return isDefaultPassword;
    }

    public void setDefaultPassword(boolean isDefaultPassword) {
        this.isDefaultPassword = isDefaultPassword;
    }
}
