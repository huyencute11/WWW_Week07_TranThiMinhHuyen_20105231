package iuh.vn.week07.Reponsitories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import iuh.vn.week07.Models.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByIsDeletedFalse();
    Customer findByEmail(String email);
}
