package iuh.vn.week07.DTOs.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import iuh.vn.week07.Models.Order;
import lombok.Data;

@Data
public class OrderModel {
    private LocalDate orderDate;
    private Long employeeId = null;
    private String employeeName;
    private long customerId;
    private String customerName;
    private List<OrderDetailModel> orderDetails;

    public OrderModel(Order order) {
        orderDate = order.getOrderDate();
        if (order.getEmployee() != null) {
            employeeId = order.getEmployee().getEmp_id();
            employeeName = order.getEmployee().getFullName();
        }
        customerId = order.getCustomer().getCustId();
        customerName = order.getCustomer().getCustName();
        orderDetails = order.getOrderDetails().stream().map(x -> new OrderDetailModel(x)).collect(Collectors.toList());
    }
}
