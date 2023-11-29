package iuh.vn.week07.DTOs.Employees;

import java.time.LocalDate;

import iuh.vn.week07.Models.Employee;
import iuh.vn.week07.enums.EmployeeStatus;
import lombok.Data;

@Data
public class EmployeeCreateModel {
    private String fullName;
    private LocalDate dob;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String token;

    public Employee ParserToEntity(String passwordHash) {
        Employee emp = new Employee();
        emp.setFullName(fullName);
        emp.setDob(dob);
        emp.setPhone(phone);
        emp.setEmail(email.toLowerCase());
        emp.setAddress(address);
        emp.setStatus(EmployeeStatus.TERMINATED);
        emp.setPassword(passwordHash);
        emp.setToken("");
        return emp;
    }
}
