package de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Readiness;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.ReferencedValue;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.CompanyOptionalFieldsCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.optionalFieldSubclasses.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Company optional fields.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company_optional_fields")
public class CompanyOptionalFields {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location = new Location();
    private Readiness productReadiness = Readiness.NOTSET;
    @OneToOne(cascade = CascadeType.ALL)
    private ReferencedValue numberOfCustomers = new ReferencedValue();
    @OneToOne(cascade = CascadeType.ALL)
    private ReferencedValue revenue = new ReferencedValue();
    @OneToOne(cascade = CascadeType.ALL)
    private ReferencedValue profit = new ReferencedValue();


    /**
     * Constructor from CreationDTO
     *
     * @param optionalFields the optional fields
     */
    public CompanyOptionalFields(CompanyOptionalFieldsCreationDTO optionalFields) {
        if (optionalFields.getLocation() != null) {
            this.location = new Location(optionalFields.getLocation());
        }
        if (optionalFields.getProductReadiness() != null) {
            this.productReadiness = optionalFields.getProductReadiness();
        }
        if (optionalFields.getNumberOfCustomers() != null) {
            this.numberOfCustomers = new ReferencedValue(optionalFields.getNumberOfCustomers());
        }
        if (optionalFields.getRevenue() != null) {
            this.revenue = new ReferencedValue(optionalFields.getRevenue());
        }
        if (optionalFields.getProfit() != null) {
            this.profit = new ReferencedValue(optionalFields.getProfit());
        }
    }

    /**
     * Constructor for an empty existing object
     *
     * @param ids the ids
     */
    public CompanyOptionalFields(Long[] ids) {
        this.id = ids[0];
        this.location = new Location(ids[1]);
        this.numberOfCustomers = new ReferencedValue(ids[2]);
        this.revenue = new ReferencedValue(ids[3]);
        this.profit = new ReferencedValue(ids[4]);
    }

    /**
     * Instantiates a new Company optional fields.
     *
     * @param optionalFields the optional fields
     * @param ids            the ids
     */
    public CompanyOptionalFields(CompanyOptionalFieldsCreationDTO optionalFields, Long[] ids) {
        if (ids.length != 5) {
            throw new IllegalArgumentException();
        }
        this.id = ids[0];
        if (optionalFields.getLocation() != null) {
            this.location = new Location(optionalFields.getLocation(), ids[1]);
        } else {
            this.location = new Location(ids[1]);
        }
        if (optionalFields.getProductReadiness() != null) {
            this.productReadiness = optionalFields.getProductReadiness();
        }
        if (optionalFields.getNumberOfCustomers() != null) {
            this.numberOfCustomers = new ReferencedValue(optionalFields.getNumberOfCustomers(), ids[2]);
        } else {
            this.numberOfCustomers = new ReferencedValue(ids[2]);
        }
        if (optionalFields.getRevenue() != null) {
            this.revenue = new ReferencedValue(optionalFields.getRevenue(), ids[3]);
        } else {
            this.revenue = new ReferencedValue(ids[3]);
        }
        if (optionalFields.getProfit() != null) {
            this.profit = new ReferencedValue(optionalFields.getProfit(), ids[4]);
        } else {
            this.profit = new ReferencedValue(ids[4]);
        }
    }
}
