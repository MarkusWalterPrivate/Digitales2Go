package de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.optional;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.subclasses.TechOptional;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Tech optional return dto.
 *
 * @author Markus Walter
 */
@Data
public class TechOptionalReturnDTO {

    private Long id;

    private List<Contact> contacts = new ArrayList<>();

    private List<String> references = new ArrayList<>();

    private List<ExternalItem> externalItems = new ArrayList<>();

    private List<String> industriesWithUse = new ArrayList<>();


    /**
     * Instantiates a new Tech optional return dto.
     *
     * @param optional the optional
     */
    public TechOptionalReturnDTO(TechOptional optional) {
        this.id = optional.getId();
        this.contacts = optional.getContacts();
        this.references = optional.getReferences();
        this.externalItems = optional.getExternalItems();
        this.industriesWithUse = createIndustryList(optional.getIndustriesWithUse());
    }

    private List<String> createIndustryList(List<Industry> industries) {
        List<String> industryNames = new ArrayList<>();
        for (Industry industry : industries
        ) {
            industryNames.add(industry.getIndustryName());
        }
        return industryNames;
    }
}