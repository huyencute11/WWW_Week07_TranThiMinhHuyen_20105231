package iuh.vn.week07.DTOs.Order;

import java.util.Comparator;
import java.util.stream.Collectors;

import iuh.vn.week07.Models.Order;
import iuh.vn.week07.Models.OrderDetail;
import iuh.vn.week07.Models.Product;
import iuh.vn.week07.Models.ProductPrice;
import lombok.Data;

@Data
public class OrderDetailCreateModel {
    private long productId;
    private long quantity;
    private String note;

    public OrderDetail ParserToEntity(Product product) {
        OrderDetail detail = new OrderDetail();
        detail.setProduct(product);
        detail.setQuantity(quantity);
        detail.setPrice(product.getProductPriceList().stream().sorted(Comparator.comparing(ProductPrice::getPriceDateTime))
            .collect(Collectors.toList()).get(0).getPrice());
        detail.setNote(note);
        return detail;
    }
}
