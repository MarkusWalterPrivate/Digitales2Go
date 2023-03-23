package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns.subclasses.CompanyOptionalFieldsReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns.subclasses.CompanyOptionalListsReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.CompanyOptional;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Company optional return dto.
 *
 * @author Markus Walter
 */
@Data
public class CompanyOptionalReturnDTO {

    private Long id;

    private List<ExternalItem> externalItems = new ArrayList<>();

    private CompanyOptionalListsReturnDTO companyOptionalLists = new CompanyOptionalListsReturnDTO();

    private CompanyOptionalFieldsReturnDTO companyOptionalFields = new CompanyOptionalFieldsReturnDTO();


    /**
     * Constructor from original class
     *
     * @param optional the optional
     */
    public CompanyOptionalReturnDTO(CompanyOptional optional) {
        this.id = optional.getId();
        this.externalItems = optional.getExternalItems();
        this.companyOptionalLists = new CompanyOptionalListsReturnDTO(optional.getCompanyOptionalLists());
        this.companyOptionalFields = new CompanyOptionalFieldsReturnDTO(optional.getCompanyOptionalFields());
    }
}
