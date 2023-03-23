package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Readiness;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.dto.ReferencedValueCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.fieldsSubclasses.LocationCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * The type Company optional fields creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyOptionalFieldsCreationDTO {
    @Valid
    private LocationCreationDTO location = new LocationCreationDTO();
    private Readiness productReadiness;
    @Valid
    private ReferencedValueCreationDTO numberOfCustomers = new ReferencedValueCreationDTO();
    @Valid
    private ReferencedValueCreationDTO revenue = new ReferencedValueCreationDTO();
    @Valid
    private ReferencedValueCreationDTO profit = new ReferencedValueCreationDTO();

}
