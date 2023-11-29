package iuh.vn.week07.DTOs.Product;

import org.springframework.web.multipart.MultipartFile;

import iuh.vn.week07.Models.Product;
import iuh.vn.week07.enums.ProductStatus;
import lombok.Data;

@Data
public class CreateProductModel {
    private String name;
    private String description;
    private String unit;
    private String manufacturer;
    private double price;
    private ProductStatus status;
    private MultipartFile[] images;
    private String token;

    public Product ParserToEntity() {
        Product product = new Product();
        product.setDescription(description);
        product.setManufacturer(manufacturer);
        product.setStatus(status);
        product.setName(name);
        product.setUnit(unit);

        return product;
    }

    public CreateProductModel(String name, String description, String unit, String manufacturer, double price,
            ProductStatus status, MultipartFile[] images, String token) {
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.manufacturer = manufacturer;
        this.status = status;
        this.price = price;
        this.images = images;
        this.token = token;
    }
    
    public void UpdateProduct(Product product) {
        product.setDescription(description);
        product.setUnit(unit);
        product.setManufacturer(manufacturer);
        product.setStatus(status);
        product.setName(name);
    }
}
