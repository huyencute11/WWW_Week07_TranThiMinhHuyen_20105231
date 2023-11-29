package iuh.vn.week07.DTOs.Statistics;

import iuh.vn.week07.Models.OrderDetail;
import java.util.List;

import lombok.Data;

@Data
public class SaleReportModel {
    private int month;
    private double sale;

    public SaleReportModel(int month, List<OrderDetail> orders) {
        this.month = month;
        this.sale = orders.stream().mapToDouble(x -> x.getPrice() * x.getQuantity()).sum();
    }
}
