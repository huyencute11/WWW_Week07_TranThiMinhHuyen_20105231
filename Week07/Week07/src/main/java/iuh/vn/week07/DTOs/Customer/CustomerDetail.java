package iuh.vn.week07.DTOs.Customer;

import iuh.vn.week07.Models.Customer;
import lombok.Data;

@Data
public class CustomerDetail {
    private String custName;
    private String email;
    private String phone;
    private String address;

    public CustomerDetail(Customer customer) {
        custName = customer.getCustName();
        email = customer.getEmail();
        phone = customer.getPhone();
        address = customer.getAddress();
    }
}
