package de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.optionalFieldSubclasses;


import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.fieldsSubclasses.LocationCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Location.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String country = "";
    private String city = "";


    /**
     * Constructor for creating an empty existing Location
     *
     * @param id the id
     */
    public Location(Long id) {
        this.id = id;
    }

    /**
     * Constructor from Creation DTO
     *
     * @param location the location
     */
    public Location(LocationCreationDTO location) {
        if (location.getCity() != null) {
            this.city = location.getCity();
        }
        if (location.getCountry() != null) {
            this.country = location.getCountry();
        }
    }

    /**
     * Constructo from Editing DTO
     *
     * @param location the location
     * @param id       the id
     */
    public Location(LocationCreationDTO location, Long id) {
        this.id = id;
        if (location.getCity() != null) {
            this.city = location.getCity();
        }
        if (location.getCountry() != null) {
            this.country = location.getCountry();
        }

    }
}
