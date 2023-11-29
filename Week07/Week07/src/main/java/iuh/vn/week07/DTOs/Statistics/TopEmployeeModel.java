package iuh.vn.week07.DTOs.Statistics;

import java.util.List;

import iuh.vn.week07.Models.OrderDetail;
import lombok.Data;

@Data
public class TopEmployeeModel {
    private String employeeName;
    private double totalPrice;

    public TopEmployeeModel(String name, List<OrderDetail> orders) {
        employeeName = name;
        totalPrice = orders.stream().mapToDouble(x -> x.getPrice() * x.getQuantity()).sum();
    }
}
