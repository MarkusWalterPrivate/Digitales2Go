package de.fraunhofer.DigiTales2Go.company;


import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.CompanyOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.CompanyRequiredCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.CompanyOptionalFieldsCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.CompanyOptionalListsCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.fieldsSubclasses.LocationCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.required.TeamSizeCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.CompanyOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.CompanyRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Alignment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Markets;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.TeamSizeEnum;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.CompanyOptionalFields;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.CompanyOptionalLists;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.requiredSubclasses.TeamSize;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Readiness;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.dto.ReferencedValueCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyHelpersTest {

    private final Long id = 42069L;
    private final CoreField coreField = new CoreField(true, "internSauce","ImageSource", "Headline", "Teaser", Industry.FINANCE, Arrays.asList("Tag1", "Tag2"), Type.TREND);

    //private final Runtime runtime = new Runtime(1234567L, 12345678L);
    private final String description = "Description";
    private final String useCases = "useCases";
    private final TeamSizeEnum teamSizeEnum = TeamSizeEnum.MEDIUM;
    private final int year = 2001;
    private final String reference = "Reference";
    private final String website = "website";
    private final int foundationYear = 1999;
    private final TeamSize teamSize = new TeamSize(teamSizeEnum, year, reference);
    private final DetailedRating detailedRating = new DetailedRating(id, 1d,1d,1d);
    private final Contact contact = new Contact("Name", "Email", "Telephone", "Organisation");
    private final ArrayList<Contact> contacts = new ArrayList<>(Lists.list(contact));
    private final String projects = "projects";
    private final ArrayList<Project> internalProjects = new ArrayList<>();
    private final ArrayList<Long> internalProjectsLongs = new ArrayList<>(Lists.list(1L,2L));
    private final ArrayList<Markets> targetMarkets = new ArrayList<>(Lists.list(Markets.EU, Markets.AUSTRALIA));
    private final ArrayList<Alignment> alignments = new ArrayList<>(Lists.list(Alignment.B2B, Alignment.B2B2C));
    private final ArrayList<String> partners = new ArrayList<>(Lists.list("Partner1"));
    private final ArrayList<String> investors = new ArrayList<>(Lists.list("Investor1"));
    private final TeamSizeCreationDTO teamSizeEditingDTO = new TeamSizeCreationDTO( teamSizeEnum, year, reference);
    private final String country = "DE";
    private final String city = "Stuttgart";
    private final LocationCreationDTO location = new LocationCreationDTO( country, city);
    private final Readiness readiness = Readiness.DEVELOPMENT;
    private final ReferencedValueCreationDTO numberOfCustomers = new ReferencedValueCreationDTO( "1", year, reference);
    private final float value = 1.0f;
    private final ReferencedValueCreationDTO revenue = new ReferencedValueCreationDTO( String.valueOf(value), year, reference);
    private final ReferencedValueCreationDTO profit = new ReferencedValueCreationDTO( String.valueOf(value), year, reference);
    private final CompanyOptionalListsCreationDTO companyOptionalListsEditingDTO =  new CompanyOptionalListsCreationDTO( contacts, targetMarkets, alignments, partners, investors);
    private final CompanyOptionalFieldsCreationDTO companyOptionalFieldsEditingDTO = new CompanyOptionalFieldsCreationDTO( location, readiness, numberOfCustomers, revenue, profit);
    private final CompanyOptionalFields companyOptionalFields = new CompanyOptionalFields();
    private final CompanyOptionalLists companyOptionalLists = new CompanyOptionalLists(contacts, targetMarkets, alignments, partners, investors);
    private final List<ExternalItem> externalItemList = new ArrayList<>();
    private final CompanyRequiredCreationDTO companyRequiredEditingDTO = new CompanyRequiredCreationDTO( description, useCases, teamSizeEditingDTO, website, foundationYear);
    private final CompanyOptionalCreationDTO companyOptionalEditingDTO = new CompanyOptionalCreationDTO( externalItemList, companyOptionalListsEditingDTO, companyOptionalFieldsEditingDTO);
    private final CompanyRequired companyRequired = new CompanyRequired(description, useCases, teamSize, website, foundationYear);
    private final CompanyOptional companyOptional = new CompanyOptional(externalItemList, companyOptionalLists, companyOptionalFields);
    private final ArrayList<Comment> comments = new ArrayList<>();

    @Test
    void createBackupCompanyRequired() {
        CompanyRequired backupCmpanyRequired = new CompanyRequired(id, description, useCases, teamSize, website, foundationYear);

        assertEquals(id, backupCmpanyRequired.getId());
        assertEquals(description, backupCmpanyRequired.getDescription());
        assertEquals(useCases, backupCmpanyRequired.getUseCases());
        assertEquals(teamSize, backupCmpanyRequired.getTeamSize());
        assertEquals(website, backupCmpanyRequired.getWebsite());
        assertEquals(foundationYear, backupCmpanyRequired.getFoundationYear());
    }

    @Test
    void createEditingDTOCompanyRequired() {
        CompanyRequired companyRequiredEDTO = new CompanyRequired(companyRequiredEditingDTO, teamSize.getId());

        assertEquals(companyRequiredEditingDTO.getDescription(), companyRequiredEDTO.getDescription());
        assertEquals(companyRequiredEditingDTO.getUseCases(), companyRequiredEDTO.getUseCases());
        assertEquals(companyRequiredEditingDTO.getTeamSize().getTeamSize(), companyRequiredEDTO.getTeamSize().getTeamSize());
        assertEquals(companyRequiredEditingDTO.getWebsite(), companyRequiredEDTO.getWebsite());
        assertEquals(companyRequiredEditingDTO.getFoundationYear(), companyRequiredEDTO.getFoundationYear());
    }

    /*
    @Test
    void createEditingDTOCompanyOptional() {
        CompanyOptional companyOptionalEDTO = new CompanyOptional(companyOptionalEditingDTO, null, )


    }
     */




}
