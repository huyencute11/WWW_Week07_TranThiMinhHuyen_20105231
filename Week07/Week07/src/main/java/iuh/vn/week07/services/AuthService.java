package iuh.vn.week07.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iuh.vn.week07.DTOs.Auth.LoginModel;
import iuh.vn.week07.DTOs.Auth.LoginResultModel;
import iuh.vn.week07.Models.Employee;
import iuh.vn.week07.Reponsitories.EmployeeReponsitory;

@Service
public class AuthService {
    
    @Autowired
    private EmployeeReponsitory employeeReponsitory;

    public LoginResultModel Login(LoginModel model) {
        Employee employee = employeeReponsitory.findByEmail(model.getEmail().toLowerCase());
        if (employee == null || !employee.getPassword().equals(HashPassword(model.getPassword())))
        {
            return null;
        }
        employee.setToken(createToken(30));
        employee.setDateExprired(LocalDate.now());
        employeeReponsitory.save(employee);
        return new LoginResultModel(employee);
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
    
    private String createToken(int n) 
    {
        // choose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb 
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public Employee CheckToken(String token) {
        Employee employee = employeeReponsitory.findByToken(token);
        if (employee == null || employee.getDateExprired().isAfter(LocalDate.now())) {
            return null;  
        }
        return employee;
    }
}
