package iuh.vn.week07.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iuh.vn.week07.DTOs.Employees.EmployeeCreateModel;
import iuh.vn.week07.DTOs.Employees.EmployeeModel;
import iuh.vn.week07.DTOs.Employees.EmployeeUpdateModel;
import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.Models.Employee;
import iuh.vn.week07.Reponsitories.EmployeeReponsitory;
import iuh.vn.week07.common.Pagination;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeReponsitory employeeReponsitory;

    @Autowired
    private AuthService authService;

    public Pagination<EmployeeModel> GetAll(int page, int pageSize, String name, String token) {
        if (authService.CheckToken(token) != null) {
            List<Employee> listCustomer = (List<Employee>) employeeReponsitory.findAll();

            if (name != null) {
                listCustomer = listCustomer.stream()
                        .filter(x -> x.getFullName().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());
            }
            return new Pagination<EmployeeModel>(listCustomer.stream().map(x -> new EmployeeModel(x)).collect(Collectors.toList()), page, pageSize);
        }
        
        return null;
    }

    public long CreateEmployee(EmployeeCreateModel model, ErrorModel errors) {
        Employee creator = authService.CheckToken(model.getToken());
        if (creator != null && !creator.isAdmin()) {
            if (employeeReponsitory.findByEmail(model.getEmail()) != null) {
                errors.Add("Employee already exists");
                return -1;
            }
            Employee employee = model.ParserToEntity(HashPassword(model.getPassword()));
            return employeeReponsitory.save(employee).getEmp_id();
        }
        else {
            errors.Add("Do not have permission to create");
        }
        return -1;
    }

    public void UpdateEmployee(long id, EmployeeUpdateModel model, ErrorModel errors) {
        Employee creator = authService.CheckToken(model.getToken());
        if (creator != null && !creator.isAdmin()) {
            Employee employee = employeeReponsitory.findById(id).orElse(null);
            if (employee == null) {
                errors.Add("Employee not found");
            }
            model.UpdateEntity(employee);
            employeeReponsitory.save(employee);
        }
        else {
            errors.Add("Do not have permission to update");
        }
    }

    private String HashPassword(String password) {
        String encryptedpassword = null;
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedpassword;
    }
}
