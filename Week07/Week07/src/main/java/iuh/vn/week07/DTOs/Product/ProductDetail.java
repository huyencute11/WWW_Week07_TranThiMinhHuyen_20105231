package iuh.vn.week07.DTOs.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import iuh.vn.week07.Models.Product;
import iuh.vn.week07.Models.ProductPrice;
import iuh.vn.week07.enums.ProductStatus;
import lombok.Data;

@Data
public class ProductDetail {
    private String name;
    private String description;
    private String unit;
    private String manufacturer;
    private double price;
    private ProductStatus status;
    private List<String> images;

    public ProductDetail(Product product) {
        this.description = product.getDescription();
        this.name = product.getName();
        this.unit = product.getUnit();
        this.manufacturer = product.getManufacturer();
        this.status = product.getStatus();
        this.price = product.getProductPriceList().stream().sorted(Comparator.comparing(ProductPrice::getPriceDateTime))
            .collect(Collectors.toList()).get(0).getPrice();
        this.images = product.getProductImageList().stream().map(x ->
             ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/uploads/")
            .path(x.getPath())
            .toUriString()
        ).collect(Collectors.toList());
    }
}
