package iuh.vn.week07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iuh.vn.week07.DTOs.Employees.EmployeeCreateModel;
import iuh.vn.week07.DTOs.Employees.EmployeeModel;
import iuh.vn.week07.DTOs.Employees.EmployeeUpdateModel;
import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.Models.Customer;
import iuh.vn.week07.common.Pagination;
import iuh.vn.week07.services.EmployeeService;

@RestController
@RequestMapping("/employees")
@CrossOrigin("http://localhost:3000")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<Pagination<EmployeeModel>> getAll(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int amount,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = true) String token) {
        Pagination<EmployeeModel> employees = employeeService.GetAll(page, amount, name, token);
        return employees != null ? ResponseEntity.ok(employees) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/create")
    public ResponseEntity createEmployee(@RequestBody EmployeeCreateModel model) {
        ErrorModel errors = new ErrorModel();
        long id = employeeService.CreateEmployee(model, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(id)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable("id") long id, @RequestBody EmployeeUpdateModel model) {
        ErrorModel errors = new ErrorModel();
        employeeService.UpdateEmployee(id, model, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(null)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
