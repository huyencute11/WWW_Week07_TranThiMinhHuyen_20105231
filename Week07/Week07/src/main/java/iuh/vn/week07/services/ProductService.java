package iuh.vn.week07.services;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.DTOs.Product.CreateProductModel;
import iuh.vn.week07.DTOs.Product.ProductDetail;
import iuh.vn.week07.DTOs.Product.ProductListModel;
import iuh.vn.week07.Models.Product;
import iuh.vn.week07.Models.ProductImage;
import iuh.vn.week07.Models.ProductPrice;
import iuh.vn.week07.Reponsitories.ProductImageReponsitory;
import iuh.vn.week07.Reponsitories.ProductPriceReponsitory;
import iuh.vn.week07.Reponsitories.ProductRepository;
import iuh.vn.week07.common.Pagination;

@Service
public class ProductService {

    @Autowired 
    private ProductRepository productRepository;

    @Autowired 
    private ProductImageReponsitory productImageReponsitory;

    @Autowired 
    private ProductPriceReponsitory productPriceReponsitory;

    @Autowired
    private AuthService authService;

    @Autowired 
    private ImageService imageService;
    
    public Pagination<ProductListModel> GetAll(int page, int pageSize, String productName) {
       // if (authService.CheckToken(token) != null) {, String token
            List<Product> products = productRepository.findByIsDeletedFalse();

            if (productName != null) {
                products = products.stream()
                        .filter(x -> x.getName().toLowerCase().contains(productName.toLowerCase()))
                        .collect(Collectors.toList());
            }

            return new Pagination<ProductListModel>(
                    products.stream().map(x -> new ProductListModel(x)).collect(Collectors.toList()), page, pageSize);
        //}
        //return null;
    }

    public ProductDetail GetDetail(Long id, ErrorModel errors) {
       // if (authService.CheckToken(token) != null) {
            Product product = productRepository.findById(id).orElse(null);
            if (product != null && !product.isDeleted()) {
                return new ProductDetail(product);
            } else {
                errors.Add("Product not found");
            }
            return null;
       // }
       /// else {, String token
           /// errors.Add("Do not have permission to view this product");
       // }
        //return null;
    }

    public long CreateProduct(CreateProductModel model, ErrorModel errors) {
        if (authService.CheckToken(model.getToken()) != null) {
            List<ProductImage> productImages = imageService.UploadProductImage(model.getImages(), errors);
            if (errors.IsEmpty()) {
                Product product = productRepository.save(model.ParserToEntity());

                productImages.forEach(x -> x.setProduct(product));
                productImageReponsitory.saveAll(productImages);

                ProductPrice productPrice = new ProductPrice();
                productPrice.setProduct(product);
                productPrice.setPrice(model.getPrice());
                productPrice.setPriceDateTime(new java.sql.Date(System.currentTimeMillis()));
                productPriceReponsitory.save(productPrice);

                return product.getProductID();
            }
        }
        else {
            errors.Add("Do not have permission to create product");
        }
        return -1;
    }

    public void UpdateProduct(long id, CreateProductModel model, ErrorModel errors) {
        if (authService.CheckToken(model.getToken()) != null) {
            Product product = productRepository.findById(id).orElse(null);
            if (product == null) {
                errors.Add("Product not found");
            } else {
                List<ProductImage> productImages = imageService.UploadProductImage(model.getImages(), errors);
                product.getProductImageList().forEach(x -> imageService.delete(x.getPath()));
                if (errors.IsEmpty()) {
                    product.getProductImageList().clear();
                    model.UpdateProduct(product);
                    productRepository.save(product);

                    productImages.forEach(x -> x.setProduct(product));
                    productImageReponsitory.saveAll(productImages);

                    ProductPrice productPrice = new ProductPrice();
                    productPrice.setProduct(product);
                    productPrice.setPrice(model.getPrice());
                    productPrice.setPriceDateTime(new java.sql.Date(System.currentTimeMillis()));
                    productPriceReponsitory.save(productPrice);
                }
            }
        }
        else {
            errors.Add("Do not have permission to update product");
        }
    }

    public void DeleteProduct(long productID, String token, ErrorModel errors) {
        if (authService.CheckToken(token) != null) {
            Product product = productRepository.findById(productID).orElse(null);
            if (product == null) {
                errors.Add("Product not found");
                return;
            }
            product.setDeleted(true);
            productRepository.save(product);
        } else {
            errors.Add("Do not have permission to delete product");
        }
    }
}
