package iuh.vn.week07.DTOs.Order;

import iuh.vn.week07.Models.OrderDetail;
import lombok.Data;

@Data
public class OrderDetailModel {
    private long productId;
    private String productName;
    private long quantity;
    private double price;
    private String note;

    public OrderDetailModel(OrderDetail detail) {
        productId = detail.getProduct().getProductID();
        productName = detail.getProduct().getName();
        quantity = detail.getQuantity();
        price = detail.getPrice();
        note = detail.getNote();
    }
}
