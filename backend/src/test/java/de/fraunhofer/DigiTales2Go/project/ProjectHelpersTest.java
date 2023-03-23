package de.fraunhofer.DigiTales2Go.project;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.event.Event;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.ReferencedValue;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.dto.ReferencedValueCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.ProjectOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.relations.ProjectsRelationsCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.ProjectRequiredCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.runtime.RuntimeCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases.ProjectRelations;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases.Runtime;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.ReadinessProject;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

 class ProjectHelpersTest {

    private final long id = 1111L;
    private final Long start = 111222333L;
    private final Long finished = 444555666L;
    private final String description = "Description";
    private final ReadinessProject readinessProject = ReadinessProject.DEVELOPMENT;
    private final Runtime runtime = new Runtime(start, finished, false);
    private final RuntimeCreationDTO runtimeCreationDTO = new RuntimeCreationDTO(start, finished, false);
    private final Contact contact = new Contact("Name", "Email", "Telephone", "Organisation");
    private final ArrayList<Contact> contacts = new ArrayList<>(Lists.list(contact));
    private final ArrayList<Technology> internalTechnologies = new ArrayList<>();
    private final ArrayList<Long> internalTechnologiesLongs = new ArrayList<>(Lists.list(1L, 2L));
    private final ArrayList<ExternalItem> externalTechnologies = new ArrayList<>();
    private final ArrayList<String> publications = new ArrayList<>(Lists.list("SuperInterestingPublication"));
    private final ArrayList<String> fundingSources = new ArrayList<>();
    private final ArrayList<String> promoters = new ArrayList<>();
    private final ArrayList<String> projectPartners = new ArrayList<>();
    private final ArrayList<String> usePartners = new ArrayList<>();
    private final ProjectRelations projectRelations = new ProjectRelations(fundingSources, promoters, projectPartners, usePartners);
    private final ProjectsRelationsCreationDTO projectsRelationsCreationDTO = new ProjectsRelationsCreationDTO(fundingSources, promoters, projectPartners, usePartners);
    private final ReferencedValueCreationDTO financing = new ReferencedValueCreationDTO("1234321.0", 123, "dkasgd");
    private final String website = "linkToAWebsite";
    private final ArrayList<Event> events = new ArrayList<>();

    @Test
    void createBackupProjectRequired() {
        ProjectRequired backupProjectRequired = new ProjectRequired(id, description, readinessProject, runtime, contacts);

        assertEquals(id, backupProjectRequired.getId());
        assertEquals(description, backupProjectRequired.getDescription());
        assertEquals(readinessProject, backupProjectRequired.getReadiness());
        assertEquals(runtime, backupProjectRequired.getRuntime());
        assertEquals(contacts, backupProjectRequired.getContacts());
    }

    @Test
    void createBackupProjectOptional() {
        ReferencedValue financingValue = new ReferencedValue(financing);
        ProjectOptional backupProjectOptional = new ProjectOptional(id, externalTechnologies, publications, projectRelations,financingValue , website);

        assertEquals(id, backupProjectOptional.getId());
        assertEquals(externalTechnologies, backupProjectOptional.getExternalItems());
        assertEquals(publications, backupProjectOptional.getPublications());
        assertEquals(projectRelations, backupProjectOptional.getProjectRelations());
        assertEquals(financingValue, backupProjectOptional.getFinancing());
        assertEquals(website, backupProjectOptional.getWebsite());
    }

    @Test
    void createBackupProjectRelations() {
        ProjectRelations backupProjectRelations = new ProjectRelations(id, fundingSources, promoters, projectPartners, usePartners);

        assertEquals(id, backupProjectRelations.getId());
        assertEquals(fundingSources, backupProjectRelations.getFundingSources());
        assertEquals(promoters, backupProjectRelations.getPromoters());
        assertEquals(projectPartners, backupProjectRelations.getProjectPartners());
        assertEquals(usePartners, backupProjectRelations.getUsePartners());
    }


    @Test
    void createProjectRequiredCreationDTO() {
        ProjectRequiredCreationDTO projectRequiredCreationDTO = new ProjectRequiredCreationDTO(description, readinessProject, runtimeCreationDTO, contacts);

        assertEquals(description, projectRequiredCreationDTO.getDescription());
        assertEquals(readinessProject, projectRequiredCreationDTO.getReadiness());
        assertEquals(runtimeCreationDTO, projectRequiredCreationDTO.getRuntime());
        assertEquals(contacts, projectRequiredCreationDTO.getContacts());
    }

    @Test
    void createProjectOptionalCreationDTO() {
        ProjectOptionalCreationDTO projectOptionalCreationDTO = new ProjectOptionalCreationDTO( externalTechnologies, publications, projectsRelationsCreationDTO, financing, website);


        assertEquals(externalTechnologies, projectOptionalCreationDTO.getExternalItems());
        assertEquals(publications, projectOptionalCreationDTO.getPublications());
        assertEquals(projectsRelationsCreationDTO, projectOptionalCreationDTO.getProjectRelations());
        assertEquals(financing, projectOptionalCreationDTO.getFinancing());
        assertEquals(website, projectOptionalCreationDTO.getWebsite());
    }
}
