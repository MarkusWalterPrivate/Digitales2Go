package de.fraunhofer.DigiTales2Go.company;

import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
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
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyTest {

    private final Long id = 42069L;
    private final CoreField coreField = new CoreField(true, "internSauce","ImageSource", "Headline", "Teaser", Industry.FINANCE, Arrays.asList("Tag1", "Tag2"), Type.TREND);
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
    private final ArrayList<ExternalItem> externalItems = new ArrayList<>();
    private final ArrayList<Markets> targetMarkets = new ArrayList<>(Lists.list(Markets.EU, Markets.AUSTRALIA));
    private final ArrayList<Alignment> alignments = new ArrayList<>(Lists.list(Alignment.B2B, Alignment.B2B2C));
    private final ArrayList<String> partners = new ArrayList<>(Lists.list("Partner1"));
    private final ArrayList<String> investors = new ArrayList<>(Lists.list("Investor1"));
    private final CompanyOptionalLists companyOptionalLists = new CompanyOptionalLists(contacts, targetMarkets, alignments, partners, investors);
    private final CompanyOptionalFields companyOptionalFields = new CompanyOptionalFields();
    private final CompanyRequired companyRequired = new CompanyRequired(description, useCases, teamSize, website, foundationYear);
    private final CompanyOptional companyOptional = new CompanyOptional(externalItems, companyOptionalLists, companyOptionalFields);
    private final ArrayList<Comment> comments = new ArrayList<>();


    @Test
    void likeCompany() {
        Company likeCompany = new Company(coreField, detailedRating, companyRequired, companyOptional);
        Double oldRating = likeCompany.getCoreField().getRating();

        likeCompany.like();
        assertEquals(oldRating + 1, likeCompany.getCoreField().getRating());
    }

    @Test
    void dislikeCompany() {
        Company dislikeCompany = new Company(coreField, detailedRating, companyRequired, companyOptional);
        Double oldRating = dislikeCompany.getCoreField().getRating();

        dislikeCompany.dislike();
        assertEquals(oldRating - 1, dislikeCompany.getCoreField().getRating());
    }

}
