package iuh.vn.week07.DTOs.Product;

import java.util.stream.Collectors;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Comparator;
import iuh.vn.week07.Models.Product;
import iuh.vn.week07.Models.ProductPrice;
import iuh.vn.week07.enums.ProductStatus;
import lombok.Data;

@Data
public class ProductListModel {
    private Long productID;
    private String name;
    private String unit;
    private String manufacturer;
    private ProductStatus status;
    private double price;
    private String image;

    public ProductListModel(Product product) {
        this.productID = product.getProductID();
        this.name = product.getName();
        this.unit = product.getUnit();
        this.manufacturer = product.getManufacturer();
        this.status = product.getStatus();
        this.price = product.getProductPriceList().stream().sorted(Comparator.comparing(ProductPrice::getPriceDateTime))
            .collect(Collectors.toList()).get(0).getPrice();
        this.image = product.getProductImageList().size() > 0 ? 
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/uploads/")
            .path(product.getProductImageList().get(0).getPath())
            .toUriString(): null;
    }
}
