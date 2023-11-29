package iuh.vn.week07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import iuh.vn.week07.DTOs.Auth.LoginModel;
import iuh.vn.week07.DTOs.Auth.LoginResultModel;
import iuh.vn.week07.services.AuthService;
import iuh.vn.week07.services.EmployeeService;

@RestController
@CrossOrigin("http://localhost:3000")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResultModel> Login(@RequestBody LoginModel model) {
        LoginResultModel result = authService.Login(model);
        return result == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) : ResponseEntity.ok(result);
    }
}
