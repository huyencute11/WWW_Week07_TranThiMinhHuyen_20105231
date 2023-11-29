package iuh.vn.week07.Reponsitories;

import iuh.vn.week07.Models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeReponsitory extends CrudRepository<Employee, Long> {
    Employee findByEmail(String email);
    Employee findByToken(String token);
}
