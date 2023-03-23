package de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.optional;


import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Tech optional creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechOptionalCreationDTO {


    private List<Contact> contacts = new ArrayList<>();

    private List<String> references = new ArrayList<>();

    private List<ExternalItem> externalItems = new ArrayList<>();

    private List<Industry> industriesWithUse = new ArrayList<>();

}

