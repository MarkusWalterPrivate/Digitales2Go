package de.fraunhofer.DigiTales2Go.dataStructure.user.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class AppUserDataChangeDTO {
    @NotBlank
    private String firstName;
    private String lastName;
    @NotBlank
    @Email()
    private String email;
    private String company;
    @NotNull
    private String job;
    private Industry industry;

    private List<Industry> interests = new ArrayList<>();
}
