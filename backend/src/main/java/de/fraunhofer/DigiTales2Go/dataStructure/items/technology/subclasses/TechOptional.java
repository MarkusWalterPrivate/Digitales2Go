package de.fraunhofer.DigiTales2Go.dataStructure.items.technology.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.optional.TechOptionalCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tech optional.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tech_optional")
public class TechOptional {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "tech_references", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "reference")
    @Lob
    private List<String> references = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<ExternalItem> externalItems = new ArrayList<>();
    @ElementCollection // 1
    @CollectionTable(name = "tech_industries_use", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "industries") //
    private List<Industry> industriesWithUse = new ArrayList<>();


    /**
     * Constructor from Creation DTO
     *
     * @param optional the optional
     */
    public TechOptional(TechOptionalCreationDTO optional) {
        if (optional.getContacts() != null) {
            this.contacts = optional.getContacts();
        }
        if (optional.getExternalItems() != null) {
            this.externalItems = optional.getExternalItems();
        }
        if (optional.getReferences() != null) {
            this.references = optional.getReferences();
        }
        if (optional.getIndustriesWithUse() != null) {
            this.industriesWithUse = optional.getIndustriesWithUse();
        }


    }
}
