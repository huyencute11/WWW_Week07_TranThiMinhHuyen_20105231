package iuh.vn.week07.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iuh.vn.week07.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private long emp_id;
    @Column(name = "full_name", length = 150, nullable = false)
    private String fullName;
    @Column(nullable = false)
    private LocalDate dob;

    @Column(unique = true, length = 150, nullable = false)
    private String email;
    @Column(length = 15, nullable = false)
    private String phone;

    @Column(length = 250, nullable = false)
    private String address;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(length = 250, nullable = false)
    private String token;

    @Column(name = "date_exprired")
    private LocalDate dateExprired;

    @Column(length = 250, nullable = false)
    private String password;

    @Column(nullable = false)
    private EmployeeStatus status;

    @OneToMany(mappedBy = "employee",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Order> listOrder;
}
