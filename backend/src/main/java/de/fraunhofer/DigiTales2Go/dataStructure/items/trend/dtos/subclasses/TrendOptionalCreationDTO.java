package de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Trend optional creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendOptionalCreationDTO {

    private List<Contact> contacts = new ArrayList<>();

    private List<ExternalItem> externalProjects = new ArrayList<>();

}
