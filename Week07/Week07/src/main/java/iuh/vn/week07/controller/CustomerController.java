package iuh.vn.week07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iuh.vn.week07.DTOs.Customer.CustomerCreateUpdateModel;
import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.Models.Customer;
import iuh.vn.week07.common.Pagination;
import iuh.vn.week07.services.CustomerService;

@RestController
@RequestMapping("/customers")
@CrossOrigin("http://localhost:3000")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<Pagination<Customer>> listCustomer(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int amount,
                                            @RequestParam(required = false) String name)//,, token @RequestParam(required = true) String token
    {
        Pagination<Customer> paging = customerService.getAllCustomers(page, amount, name);
        return paging != null ? ResponseEntity.ok(paging) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody CustomerCreateUpdateModel customer) {
        ErrorModel errors = new ErrorModel();
        long id = customerService.Create(customer, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(id) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity updateCustomer(@PathVariable("id") long id, @RequestBody CustomerCreateUpdateModel customer) {
        ErrorModel errors = new ErrorModel();
        customerService.UpdateCustomer(id, customer, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(null)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") long id, @RequestParam(required = true) String token) {
        ErrorModel errors = new ErrorModel();
        customerService.DeleteCustomer(id, token, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(null)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
