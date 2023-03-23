package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.CompanyOptionalFieldsCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.CompanyOptionalListsCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Company optional creation dto.
 *
 * @author Markus Walter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyOptionalCreationDTO {

    @Valid
    private List<ExternalItem> externalItems = new ArrayList<>();
    @Valid
    private CompanyOptionalListsCreationDTO companyOptionalLists = new CompanyOptionalListsCreationDTO();
    @Valid
    private CompanyOptionalFieldsCreationDTO companyOptionalFields = new CompanyOptionalFieldsCreationDTO();

}
