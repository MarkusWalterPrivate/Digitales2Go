package de.fraunhofer.DigiTales2Go.trend;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses.TrendOptionalCreationDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrendHelpersTest {

    private final Long id = 123L;
    private final ArrayList<Contact> contacts = new ArrayList<>();
    private final ArrayList<ExternalItem> externalProjects = new ArrayList<>();



    @Test
    void createBackupTrendRequired() {
        final String description = "Description";
        final String discussion = "Discussion";

        TrendRequired trendRequired = new TrendRequired(id, description, discussion);

        assertEquals(trendRequired.getId(), id);
        assertEquals(trendRequired.getDescription(), description);
        assertEquals(trendRequired.getDiscussion(), discussion);
    }

    @Test
    void createBackupTrendOptionals() {
        TrendOptional trendOptional = new TrendOptional(id, contacts, externalProjects);

        assertEquals(trendOptional.getId(), id);
        assertEquals(trendOptional.getContacts(), contacts);
        assertEquals(trendOptional.getExternalProjects(), externalProjects);
    }

    @Test
    void createOptionalCreationDTO() {
        TrendOptionalCreationDTO trendOptionalCreationDTO = new TrendOptionalCreationDTO(contacts,  externalProjects);

        assertEquals(trendOptionalCreationDTO.getContacts(), contacts);
        assertEquals(trendOptionalCreationDTO.getExternalProjects(), externalProjects);
    }


}
