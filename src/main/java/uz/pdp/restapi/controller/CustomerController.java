package uz.pdp.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping(value = "/{id}")
    public Customer getCustomer(@PathVariable Integer id){
       return customerService.getCustomerById(id);
    }

    @PostMapping
    public ApiResponse addCustomer(@Valid @RequestBody CustomerDto customerDto){
       return customerService.addCustomer(customerDto);
    }

    @PutMapping(value = "/{id}")
    public ApiResponse editCustomer(@PathVariable Integer id,@Valid @RequestBody CustomerDto customerDto){
       return customerService.editCustomer(id, customerDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteCustomer(@PathVariable Integer id){
       return customerService.deleteCustomer(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
                });
        return errors;
    }
}
