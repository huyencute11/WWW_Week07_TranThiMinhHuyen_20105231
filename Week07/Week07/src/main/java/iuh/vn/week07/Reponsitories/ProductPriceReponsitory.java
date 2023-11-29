package iuh.vn.week07.Reponsitories;

import iuh.vn.week07.Models.ProductPrice;
import iuh.vn.week07.ids.ProductPriceId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceReponsitory extends CrudRepository<ProductPrice, ProductPriceId> {
}
