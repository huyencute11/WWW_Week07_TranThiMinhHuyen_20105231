package iuh.vn.week07.DTOs.Order;

import java.time.LocalDate;

import iuh.vn.week07.Models.Order;
import lombok.Data;

@Data
public class OrderListModel {
    private long id;
    private LocalDate orderDate;
    private String employeeName;
    private String customerName;
    private double totalPrice;

    public OrderListModel(Order order) {
        id = order.getOrderId();
        orderDate = order.getOrderDate();
        if (order.getEmployee() != null) {
            employeeName = order.getEmployee().getFullName();
        }
        customerName = order.getCustomer().getCustName();
        totalPrice = order.getOrderDetails().stream().mapToDouble(x -> x.getPrice() * x.getQuantity()).sum();
    }
}
