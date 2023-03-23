package de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.CompanyOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.CompanyOptionalFields;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.CompanyOptionalLists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Company optional.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_optional")
public class CompanyOptional {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ExternalItem> externalItems = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private CompanyOptionalLists companyOptionalLists = new CompanyOptionalLists();
    @OneToOne(cascade = CascadeType.ALL)
    private CompanyOptionalFields companyOptionalFields = new CompanyOptionalFields();


    /**
     * Instantiates a new Company optional.
     *
     * @param externalItems         the external items
     * @param companyOptionalLists  the company optional lists
     * @param companyOptionalFields the company optional fields
     */
    public CompanyOptional(List<ExternalItem> externalItems, CompanyOptionalLists companyOptionalLists, CompanyOptionalFields companyOptionalFields) {
        this.externalItems = externalItems;
        this.companyOptionalLists = companyOptionalLists;
        this.companyOptionalFields = companyOptionalFields;
    }

    /**
     * Instantiates a new Company optional.
     *
     * @param optional the optional
     */
    public CompanyOptional(CompanyOptionalCreationDTO optional) {
        if (optional.getCompanyOptionalFields() != null) {
            this.companyOptionalFields = new CompanyOptionalFields(optional.getCompanyOptionalFields());
        }
        if (optional.getCompanyOptionalLists() != null) {
            this.companyOptionalLists = new CompanyOptionalLists(optional.getCompanyOptionalLists());
        }
    }


    /**
     * Instantiates a new Company optional.
     *
     * @param optional the optional
     * @param fieldIDs the field i ds
     * @param listsId  the lists id
     */
    public CompanyOptional(CompanyOptionalCreationDTO optional, Long[] fieldIDs, Long listsId) {
        if (optional.getCompanyOptionalFields() != null) {
            this.companyOptionalFields = new CompanyOptionalFields(optional.getCompanyOptionalFields(), fieldIDs);
        } else {
            this.companyOptionalFields = new CompanyOptionalFields(fieldIDs);
        }
        if (optional.getCompanyOptionalLists() != null) {
            this.companyOptionalLists = new CompanyOptionalLists(optional.getCompanyOptionalLists(), listsId);
        } else {
            this.companyOptionalLists = new CompanyOptionalLists(listsId);
        }
        if (optional.getExternalItems() != null) {
            this.externalItems = optional.getExternalItems();
        }

    }

    /**
     * Instantiates a new Company optional.
     *
     * @param fieldIDs the field i ds
     * @param listId   the list id
     */
    public CompanyOptional(Long[] fieldIDs, Long listId) {
        this.companyOptionalFields = new CompanyOptionalFields(fieldIDs);
        this.companyOptionalLists = new CompanyOptionalLists(listId);
    }
}
