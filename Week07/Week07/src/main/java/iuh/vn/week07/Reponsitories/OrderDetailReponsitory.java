package iuh.vn.week07.Reponsitories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import iuh.vn.week07.Models.OrderDetail;
import iuh.vn.week07.ids.OrderDetailId;

@Repository
public interface OrderDetailReponsitory extends CrudRepository<OrderDetail, OrderDetailId> {
}
