package uz.pdp.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restapi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
