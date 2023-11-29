package iuh.vn.week07.DTOs.Statistics;

import iuh.vn.week07.Models.Product;
import lombok.Data;

@Data
public class TopProductModel {
    private long productId;
    private String productName;
    private double totalPrice;

    public TopProductModel(Product product, double totalPrice) {
        productId = product.getProductID();
        productName = product.getName();
        this.totalPrice = totalPrice;
    }
}
