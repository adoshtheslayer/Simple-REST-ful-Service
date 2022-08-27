package uz.pdp.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.restapi.entity.Customer;
import uz.pdp.restapi.payload.ApiResponse;
import uz.pdp.restapi.payload.CustomerDto;
import uz.pdp.restapi.service.CustomerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerControllerSecond {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public HttpEntity<ApiResponse> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        ApiResponse apiResponse = customerService.addCustomer(customerDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> editCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerDto customerDto) {

        ApiResponse apiResponse = customerService.editCustomer(id, customerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ?
                HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        ApiResponse apiResponse = customerService.deleteCustomer(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }
}
