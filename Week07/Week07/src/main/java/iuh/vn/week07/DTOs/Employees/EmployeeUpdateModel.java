package iuh.vn.week07.DTOs.Employees;

import iuh.vn.week07.Models.Employee;
import iuh.vn.week07.enums.EmployeeStatus;
import lombok.Data;

@Data
public class EmployeeUpdateModel extends EmployeeCreateModel {
    private EmployeeStatus status;
    private boolean isAdmin;

    public void UpdateEntity(Employee emp) {
        emp.setStatus(status);
        emp.setAdmin(isAdmin);
        emp.setFullName(getFullName());
        emp.setEmail(getEmail());
        emp.setPhone(getPhone());
        emp.setAddress(getAddress());
        emp.setAddress(getAddress());
    }
}
