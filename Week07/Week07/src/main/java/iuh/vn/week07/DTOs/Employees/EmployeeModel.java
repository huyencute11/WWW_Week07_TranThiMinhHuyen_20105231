package iuh.vn.week07.DTOs.Employees;

import java.time.LocalDate;

import iuh.vn.week07.Models.Employee;
import iuh.vn.week07.enums.EmployeeStatus;
import lombok.Data;

@Data
public class EmployeeModel {
    private String fullName;
    private LocalDate dob;
    private String email;
    private String phone;
    private String address;
    private EmployeeStatus status;
    private boolean isAdmin;
    private long emp_id;

    public EmployeeModel(Employee emp) {
        emp_id = emp.getEmp_id();
        fullName = emp.getFullName();
        dob = emp.getDob();
        email = emp.getEmail();
        phone = emp.getPhone();
        address = emp.getAddress();
        status = emp.getStatus();
        isAdmin = emp.isAdmin();
    }
}
