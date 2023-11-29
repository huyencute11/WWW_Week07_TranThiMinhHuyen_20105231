package iuh.vn.week07.Reponsitories;

import iuh.vn.week07.Models.ProductImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageReponsitory extends CrudRepository<ProductImage, Long> {
}
