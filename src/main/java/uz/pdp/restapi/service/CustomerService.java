package uz.pdp.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.restapi.entity.Customer;
import uz.pdp.restapi.payload.ApiResponse;
import uz.pdp.restapi.payload.CustomerDto;
import uz.pdp.restapi.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null);
    }

    public ApiResponse addCustomer(CustomerDto customerDto) {
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new ApiResponse("Phone number already exist", false);
        }

        Customer customer = Customer.builder()
                .fullName(customerDto.getFullName())
                .phoneNumber(customerDto.getPhoneNumber())
                .address(customerDto.getAddress())
                .build();
        customerRepository.save(customer);

        return new ApiResponse("Customer saved", true);
    }

    public ApiResponse editCustomer(Integer id, CustomerDto customerDto) {
        boolean phoneNumberAndIdNot =
                customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);

        if (phoneNumberAndIdNot) {
            return new ApiResponse("Such a telephone digital client already exists", false);
        }

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            return new ApiResponse("This customer is not exist", false);
        }
        Customer customer = optionalCustomer.get();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("Customer edited", true);
    }

    public ApiResponse deleteCustomer(Integer id) {
        try {
            customerRepository.deleteById(id);
            return new ApiResponse("Customer deleted", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik", false);
        }
    }

}
