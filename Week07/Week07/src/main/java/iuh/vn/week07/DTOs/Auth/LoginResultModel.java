package iuh.vn.week07.DTOs.Auth;

import iuh.vn.week07.Models.Employee;
import lombok.Data;

@Data
public class LoginResultModel {
    private String token;
    private boolean isAdmin;

    public LoginResultModel(Employee employee) {
        token = employee.getToken();
        isAdmin = employee.isAdmin();
    }
}
