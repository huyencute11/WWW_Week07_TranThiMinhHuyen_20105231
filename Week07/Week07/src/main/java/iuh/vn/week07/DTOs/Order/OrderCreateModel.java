package iuh.vn.week07.DTOs.Order;

import java.time.LocalDate;
import java.util.List;

import iuh.vn.week07.Models.Customer;
import iuh.vn.week07.Models.Order;
import lombok.Data;

@Data
public class OrderCreateModel {
    private long customerId;
    private List<OrderDetailCreateModel> orderDetails;

    public Order ParserToEntity(Customer customer) {
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
        return order;
    }
}
