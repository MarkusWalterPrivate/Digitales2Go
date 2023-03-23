package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Alignment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Markets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Company optional lists creation dto.
 *
 * @author Markus Walter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyOptionalListsCreationDTO {
    @Valid
    private List<Contact> contacts = new ArrayList<>();

    private List<Markets> targetMarkets = new ArrayList<>();

    private List<Alignment> alignments = new ArrayList<>();

    private List<String> partners = new ArrayList<>();

    private List<String> investors = new ArrayList<>();
}
