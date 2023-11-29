package iuh.vn.week07.Reponsitories;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iuh.vn.week07.Models.Order;

@Repository
public interface OrderReponsitory extends CrudRepository<Order, Long> {
    
    @Query("SELECT o FROM Order o WHERE o.employee.emp_id = :emp_id")
    List<Order> findByEmp_id(@Param("emp_id") Long employeeId);

    @Query("SELECT o.employee.id, o FROM Order o WHERE o.id IS NOT NULL AND o.employee IS NOT NULL AND o.orderDate >= :dateFrom AND o.orderDate <= :dateTo")
    List<Object[]> findOrdersGroupedByEmployeeId(
        @Param("dateFrom") LocalDate dateFrom,
        @Param("dateTo") LocalDate dateTo
    );

    @Query("SELECT MONTH(o.orderDate) AS month, o " +
           "FROM Order o " +
           "WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.employee IS NOT NULL ")
        //    "GROUP BY MONTH(o.orderDate) " +
        //    "ORDER BY MONTH(o.orderDate)")
    List<Object[]> findOrdersGroupedByMonth(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    @Query("SELECT o " +
           "FROM Order o " +
           "WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.employee IS NOT NULL ")
        //    "GROUP BY MONTH(o.orderDate) " +
        //    "ORDER BY MONTH(o.orderDate)")
    List<Order> findOrdersInMonth(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    @Query("SELECT o.employee.id, o FROM Order o " +
            "WHERE o.id IS NOT NULL AND o.employee IS NOT NULL AND o.orderDate >= :dateFrom " +
            "AND o.orderDate <= :dateTo AND o.employee.id IS NOT NULL AND o.employee.emp_id IN :empIds")
    List<Object[]> findOrdersByEmployeeIds(
        @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            @Param("empIds") List<Long> empIds
    );
}
