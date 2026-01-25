package htsc;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import htsc.src_BE.dao.TaiKhoanDAO;
import htsc.src_BE.dto.TaiKhoanDTO;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public void initialize() {
        // Clear error message initially
        errorMessageLabel.setText("");
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            errorMessageLabel.setText("Vui lòng nhập tên đăng nhập và mật khẩu!");
            return;
        }

        // Clear previous error message
        errorMessageLabel.setText("");

        // Authenticate user
        TaiKhoanDTO account = TaiKhoanDAO.kiemTraTaiKhoan(username, password);

        if (account != null) {
            // Check if account is active
            if ("Inactive".equals(account.getStatus())) {
                errorMessageLabel.setText("Tài khoản của bạn đã bị khóa!");
                return;
            }

            // Store current account information
            App.CURRENT_ACCOUNT = account;

            // Route based on role
            try {
                if ("Admin".equals(account.getRole())) {
                    // Load admin dashboard
                    App.setRoot("primary");
                } else if ("NhanVien".equals(account.getRole())) {
                    // Load employee dashboard
                    App.setRoot("employee");
                } else {
                    errorMessageLabel.setText("Vai trò không xác định!");
                }
            } catch (Exception e) {
                errorMessageLabel.setText("Lỗi khi tải giao diện: " + e.getMessage());
            }
        } else {
            // Authentication failed
            errorMessageLabel.setText("Tên đăng nhập hoặc mật khẩu không chính xác!");
            passwordField.clear();
        }
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
