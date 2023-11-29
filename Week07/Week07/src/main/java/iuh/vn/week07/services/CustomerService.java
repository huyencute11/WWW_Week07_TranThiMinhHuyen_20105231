package iuh.vn.week07.services;

import iuh.vn.week07.DTOs.Customer.CustomerCreateUpdateModel;
import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.Models.Customer;
import iuh.vn.week07.Reponsitories.CustomerRepository;
import iuh.vn.week07.common.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthService authService;

    public Pagination<Customer> getAllCustomers(int page, int amount, String name) {
        
       // if (authService.CheckToken(token) != null) {
            List<Customer> listCustomer = (List<Customer>) customerRepository.findByIsDeletedFalse();

            if (name != null) {
                listCustomer = listCustomer.stream()
                        .filter(x -> x.getCustName().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());
            }
            return new Pagination<Customer>(listCustomer, page, amount);
       // }
       // return null;
    }

    public long Create(CustomerCreateUpdateModel model, ErrorModel errors) {
      //  if (authService.CheckToken(model.getToken()) != null) {
            if (customerRepository.findByEmail(model.getEmail()) != null) {
                errors.Add("Customer already exists");
            }
            Customer customer = model.ParseToEntity();
            return customerRepository.save(customer).getCustId();
      //  }
      //  else {
          ///  errors.Add("Do not have permission to create");
//}
       // return -1;
    }

    public void UpdateCustomer(long id, CustomerCreateUpdateModel model, ErrorModel errors) {
       // if (authService.CheckToken(model.getToken()) != null) {
            Customer customer = customerRepository.findById(id).orElse(null);
            if (customer == null) {
                errors.Add("Customer not found");
            }
            model.UpdateEntity(customer);
            customerRepository.save(customer);
        
      //  }
       // else {
           // errors.Add("Do not have permission to update");
        //}
    }

    public void DeleteCustomer(long id, String token, ErrorModel errors) {
        if (authService.CheckToken(token) != null) {
            Customer customer = customerRepository.findById(id).orElse(null);
            if (customer == null) {
                errors.Add("Customer not found");
            }
            customer.setDeleted(true);
            customerRepository.save(customer);
        }
        else {
            errors.Add("Do not have permission to delete");
        }
    }
}
