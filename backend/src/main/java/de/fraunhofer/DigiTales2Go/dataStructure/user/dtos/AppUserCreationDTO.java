package de.fraunhofer.DigiTales2Go.dataStructure.user.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserCreationDTO {
    @NotBlank
    private String firstName = "";
    private String lastName = "";
    @NotBlank
    @Email()
    private String email = "";
    private String company = "";
    private String job = "";
    @NotNull
    private Industry industry = Industry.NOTSET;
    @NotBlank
    @Size(min = 6, message = "{validation.password.size.too_short}")
    private String password = "";
    private List<Industry> interests = new ArrayList<>();
}
