package iuh.vn.week07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.DTOs.Order.OrderCreateModel;
import iuh.vn.week07.DTOs.Order.OrderListModel;
import iuh.vn.week07.DTOs.Order.OrderModel;
import iuh.vn.week07.common.Pagination;
import iuh.vn.week07.services.OrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin("http://localhost:3000")
public class OrderController {
    
    @Autowired
    private OrderService orderService; 

    @GetMapping() 
    public ResponseEntity<Pagination<OrderListModel>> GetAll(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10000") int amount,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = true) String token) 
    {
        Pagination<OrderListModel> paging = orderService.GetAll(page, amount, name, token);
        return paging != null ? ResponseEntity.ok(paging) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/byEmployee/{id}")
    public ResponseEntity<Pagination<OrderListModel>> GetAllByEmployee(@PathVariable("id") long employeeId, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int amount,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = true) String token) 
    {
        Pagination<OrderListModel> paging = orderService.GetByEmployee(employeeId, page, amount, name, token);
        return paging != null ? ResponseEntity.ok(paging) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody OrderCreateModel model) {
        ErrorModel errors = new ErrorModel();
        long id = orderService.Create(model, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(id) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> GetAll(@PathVariable("id") long id,
                                            @RequestParam(required = true) String token) 
    {
        ErrorModel errors = new ErrorModel();
        OrderModel order = orderService.GetDetails(id, token, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(order) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity ConfirmOrder(@PathVariable("id") long id,
                                            @RequestParam(required = true) String token) 
    {
        ErrorModel errors = new ErrorModel();
        orderService.ConfirmOrder(id, token, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(null) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
