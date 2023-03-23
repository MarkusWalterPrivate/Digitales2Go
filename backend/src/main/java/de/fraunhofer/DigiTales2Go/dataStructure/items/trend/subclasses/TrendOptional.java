package de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses.TrendOptionalCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Trend optional.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trend_optional")
public class TrendOptional {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<ExternalItem> externalProjects = new ArrayList<>();


    /**
     * Constructor from Creation DTo
     *
     * @param optional the optional
     */
    public TrendOptional(TrendOptionalCreationDTO optional) {
        if (optional.getContacts() != null) {
            this.contacts = optional.getContacts();
        }
        if (optional.getExternalProjects() != null) {
            this.externalProjects = optional.getExternalProjects();
        }

    }
}
