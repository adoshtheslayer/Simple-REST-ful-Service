package uz.pdp.restapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomerDto {

    @NotNull(message = "Fullname is not be empty")
    private String fullName;

    @NotNull(message = "Phone number is not be empty")
    private String phoneNumber;

    @NotNull(message = "Phone number is not be empty")
    private String address;

}
