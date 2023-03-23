package de.fraunhofer.DigiTales2Go.dataStructure.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReturnDTO {
    Long id;
    String token;
}
