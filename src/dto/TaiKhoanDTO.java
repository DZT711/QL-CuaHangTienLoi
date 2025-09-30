package dto;

public class TaiKhoanDTO {
    private String username;
    private String password;
    // private String maNV;
    private String role;
    private String fullName;

    public TaiKhoanDTO() {}

    public TaiKhoanDTO(String username, String password, String role,String fullName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
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

    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public String getfullName()
    {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }
}   
