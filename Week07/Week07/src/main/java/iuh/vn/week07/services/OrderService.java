package iuh.vn.week07.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.DTOs.Order.OrderCreateModel;
import iuh.vn.week07.DTOs.Order.OrderDetailCreateModel;
import iuh.vn.week07.DTOs.Order.OrderListModel;
import iuh.vn.week07.DTOs.Order.OrderModel;
import iuh.vn.week07.Models.Customer;
import iuh.vn.week07.Models.Employee;
import iuh.vn.week07.Models.Order;
import iuh.vn.week07.Models.OrderDetail;
import iuh.vn.week07.Models.Product;
import iuh.vn.week07.Reponsitories.CustomerRepository;
import iuh.vn.week07.Reponsitories.EmployeeReponsitory;
import iuh.vn.week07.Reponsitories.OrderDetailReponsitory;
import iuh.vn.week07.Reponsitories.OrderReponsitory;
import iuh.vn.week07.Reponsitories.ProductRepository;
import iuh.vn.week07.common.Pagination;

@Service
public class OrderService {
    
    @Autowired
    private OrderReponsitory orderReponsitory;

    @Autowired
    private EmployeeReponsitory employeeReponsitory;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailReponsitory orderDetailReponsitory;

    @Autowired
    private AuthService authService;

    public Pagination<OrderListModel> GetAll(int page, int pageSize, String name, String token) {
        if (authService.CheckToken(token) != null) {
            List<Order> orders = (List<Order>) orderReponsitory.findAll();

            if (name != null) {
                orders = orders.stream()
                        .filter(x -> x.getCustomer().getCustName().toLowerCase().contains(name.toLowerCase())
                                || x.getEmployee().getFullName().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());
            }
            return new Pagination<OrderListModel>(
                    orders.stream().map(x -> new OrderListModel(x)).collect(Collectors.toList()), page, pageSize);
        }

        return null;
    }
    
    public long Create(OrderCreateModel model, ErrorModel errors) {
        Customer customer = customerRepository.findById(model.getCustomerId()).orElse(null);
        if (customer == null || customer.isDeleted()) {
            errors.Add("Customer not found");
        } else {
            List<OrderDetail> details = new ArrayList<>();
            for (OrderDetailCreateModel orderDetail : model.getOrderDetails()) {
                Product product = productRepository.findById(orderDetail.getProductId()).orElse(null);
                if (product == null || product.isDeleted()) {
                    errors.Add("Product not found with id " + orderDetail.getProductId());
                    return -1;
                }
                details.add(orderDetail.ParserToEntity(product));
            }

            Order order = model.ParserToEntity(customer);
            orderReponsitory.save(order);

            details.forEach(x -> x.setOrder(order));
            orderDetailReponsitory.saveAll(details);

            return order.getOrderId();
        }
        return -1;
    }
    
    public OrderModel GetDetails(long id, String token, ErrorModel errors) {
        if (authService.CheckToken(token) != null) {
            Order order = orderReponsitory.findById(id).orElse(null);
            if (order == null) {
                errors.Add("Order not found");
                return null;
            }
            return new OrderModel(order);
        }
        else {
            errors.Add("Do not have permission");
        }
        return null;
    }

    public void ConfirmOrder(long id, String token, ErrorModel errors) {
        Employee employee = authService.CheckToken(token);
        if (employee != null) {
            Order order = orderReponsitory.findById(id).orElse(null);
            if (order == null) {
                errors.Add("Order not found");
            }
            else if (order.getEmployee() != null) {
                errors.Add("Order already confirmed");
            }
            order.setEmployee(employee);
            orderReponsitory.save(order);
        } else {
            errors.Add("Do not have permission");
        }
    }
    
    public Pagination<OrderListModel> GetByEmployee(long employeeId, int page, int pageSize, String name, String token) {
        Employee employee = authService.CheckToken(token);
        if (employee != null && employee.isAdmin()) {
            List<Order> orders = (List<Order>) orderReponsitory.findByEmp_id(employeeId);
            if (name != null) {
                orders = orders.stream()
                        .filter(x -> x.getCustomer().getCustName().toLowerCase().contains(name.toLowerCase())
                                || x.getEmployee().getFullName().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());
            }
            return new Pagination<OrderListModel>(
                    orders.stream().map(x -> new OrderListModel(x)).collect(Collectors.toList()), page, pageSize);
        }

        return null;
    }
}
