package iuh.vn.week07.DTOs.Customer;

import iuh.vn.week07.Models.Customer;
import lombok.Data;

@Data
public class CustomerCreateUpdateModel {
    private String custName;
    private String email;
    private String phone;
    private String address;
    private String token;

    public Customer ParseToEntity() {
        Customer customer = new Customer();
        customer.setCustName(custName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);

        return customer;
    }

    public void UpdateEntity(Customer customer) {
        customer.setCustName(custName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
    }
}
