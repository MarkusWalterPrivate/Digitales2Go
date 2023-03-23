package de.fraunhofer.DigiTales2Go.dataStructure.user.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleChangeDTO {
    Role role;
}
