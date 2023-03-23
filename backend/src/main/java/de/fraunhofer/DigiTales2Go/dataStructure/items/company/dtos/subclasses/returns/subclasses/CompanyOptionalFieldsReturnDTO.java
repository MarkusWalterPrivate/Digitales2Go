package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.ReferencedValue;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.CompanyOptionalFields;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.optionalFieldSubclasses.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Company optional fields return dto.
 */
@Data
@NoArgsConstructor
public class CompanyOptionalFieldsReturnDTO {

    private Long id;

    private Location location = new Location();
    private String productReadiness = "";

    private ReferencedValue numberOfCustomers = new ReferencedValue();

    private ReferencedValue revenue = new ReferencedValue();

    private ReferencedValue profit = new ReferencedValue();


    /**
     * Instantiates a new Company optional fields return dto.
     *
     * @param fields the fields
     */
    public CompanyOptionalFieldsReturnDTO(CompanyOptionalFields fields) {
        this.id = fields.getId();
        this.location = fields.getLocation();
        this.productReadiness = fields.getProductReadiness().getReadinessString();
        this.numberOfCustomers = fields.getNumberOfCustomers();
        this.revenue = fields.getRevenue();
        this.profit = fields.getProfit();
    }
}
