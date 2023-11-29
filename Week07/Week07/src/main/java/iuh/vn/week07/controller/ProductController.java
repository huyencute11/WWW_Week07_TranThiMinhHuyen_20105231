package iuh.vn.week07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.DTOs.Product.CreateProductModel;
import iuh.vn.week07.DTOs.Product.ProductDetail;
import iuh.vn.week07.DTOs.Product.ProductListModel;
import iuh.vn.week07.common.Pagination;
import iuh.vn.week07.enums.ProductStatus;
import iuh.vn.week07.services.ProductService;


@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000")
public class ProductController {
    
    @Autowired
    private ProductService productSerice;

    @GetMapping()
    public ResponseEntity<Pagination<ProductListModel>> getAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int amount,
                                                @RequestParam(required = false) String name
                                                ) {
        Pagination<ProductListModel> products = productSerice.GetAll(page, amount, name);//, token@RequestParam(required = true) String token
        return products != null ? ResponseEntity.ok(products) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetail> getDetail(@PathVariable("id") Long id
            ) {
        ErrorModel errors = new ErrorModel();//, token,@RequestParam(required = true) String token
        ProductDetail product = productSerice.GetDetail(id, errors);
        return product == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) :ResponseEntity.ok(product);
    }

    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("manufacturer") String manufacturer,
            @RequestParam("unit") String unit, @RequestParam("status") String status,
            @RequestParam("price") double price, @RequestParam("images") MultipartFile[] images, @RequestParam("token") String token) {
        ErrorModel errors = new ErrorModel();
        CreateProductModel model = new CreateProductModel(name, description, unit, manufacturer, price,
                ProductStatus.valueOf(status), images, token);
        Long productId = productSerice.CreateProduct(model, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(productId)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") long id, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("manufacturer") String manufacturer,
            @RequestParam("unit") String unit, @RequestParam("status") String status,
            @RequestParam("price") double price, @RequestParam("images") MultipartFile[] images, @RequestParam(required = true) String token) {
        ErrorModel errors = new ErrorModel();
        CreateProductModel model = new CreateProductModel(name, description, unit, manufacturer, price,
                ProductStatus.valueOf(status), images, token);
        productSerice.UpdateProduct(id, model, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(null)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id, @RequestParam(required = true) String token) {
        ErrorModel errors = new ErrorModel();
        productSerice.DeleteProduct(id, token, errors);
        return errors.IsEmpty() ? ResponseEntity.ok(null) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
