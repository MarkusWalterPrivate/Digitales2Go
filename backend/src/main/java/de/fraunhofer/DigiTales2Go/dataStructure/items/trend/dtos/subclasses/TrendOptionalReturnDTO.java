package de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendOptional;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Trend optional return dto.
 *
 * @author Markus Walter
 */
@Data
public class TrendOptionalReturnDTO {

    private Long id;

    private List<Contact> contacts = new ArrayList<>();

    private List<ExternalItem> externalItems = new ArrayList<>();


    /**
     * Instantiates a new Trend optional return dto.
     *
     * @param optional the optional
     */
    public TrendOptionalReturnDTO(TrendOptional optional) {
        this.id = optional.getId();
        this.contacts = optional.getContacts();
        this.externalItems = optional.getExternalProjects();
    }
}
