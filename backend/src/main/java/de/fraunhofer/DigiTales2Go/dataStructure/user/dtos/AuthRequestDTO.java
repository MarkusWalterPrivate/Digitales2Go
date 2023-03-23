package de.fraunhofer.DigiTales2Go.dataStructure.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {

    @NotBlank
    @Email()
    private String email = "";

    @NotBlank
    @Size(min = 6, message = "{validation.password.size.too_short}")
    private String password = "";
}
