package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.fileUpload;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRatingCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.*;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.event.Event;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.dto.ReferencedValueCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.CompanyController;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.CompanyCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.CompanyReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.CompanyOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.CompanyRequiredCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.CompanyOptionalFieldsCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.CompanyOptionalListsCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.fieldsSubclasses.LocationCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.required.TeamSizeCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.ProjectController;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.ProjectCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.ProjectReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.ProjectOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.relations.ProjectsRelationsCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.ProjectRequiredCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.runtime.RuntimeCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.TechnologyController;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.TechCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.TechReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.optional.TechOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.required.TechRequiredCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.TrendController;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.TrendCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.TrendReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses.TrendOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses.TrendRequiredCreationDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUploadService {
    @Autowired
    TrendController trendController;
    @Autowired
    CompanyController companyController;
    @Autowired
    ProjectController projectController;
    @Autowired
    TechnologyController technologyController;

    private String coreFieldString = "coreField";
    private String detailedRatingString = "detailedRating";
    private String internalProjectsString = "internalProjects";
    private String internalTrendsString = "internalTrends";
    private String internalCompaniesString = "internalCompanies";
    private String internalTechnologiesString = "internalTechnologies";
    private String descriptionString = "description";
    private String contactsString = "contacts";
    private String externalItemsString = "externalItems";
    private String useCasesString = "useCases";
    private String websiteString = "website";

    public ResponseEntity<Object> fileUploadEntryPoint(MultipartFile file)throws IOException{
        ResponseEntity<Object> response;

        // if clauses are evaluated left to right -> third statement can't throw a NPE
        if (file == null || file.getOriginalFilename() == null || !file.getOriginalFilename().endsWith(".json")) {
            response = new ResponseEntity<>("Invalid file. \n Please send .json files.", HttpStatus.BAD_REQUEST);
        } else {
            response = new ResponseEntity<>("Invalid file contents \n", HttpStatus.BAD_REQUEST);
            Object createdObject = createObject(file);
            if (createdObject != null) {
                response = new ResponseEntity<>(createdObject, HttpStatus.CREATED);
            }
        }
        return response;

    }


    private  Object createObject(MultipartFile file) throws IOException {
        JSONObject jsonObject = createJsonObjectFromFile(file);
        Object returnObject;

        Type type = getType(jsonObject);

        returnObject  = switch (type) {
            case NOTSET -> null;
            case TREND -> createTrend(jsonObject);
            case COMPANY -> createCompany(jsonObject);
            case TECHNOLOGY -> createTechnology(jsonObject);
            case PROJECT -> createProject(jsonObject);
        };

        return returnObject;
    }

    // Used in the above, to extract a JSONObject from the MultipartFile
    private  JSONObject createJsonObjectFromFile(MultipartFile file) throws IOException {
        JSONObject json;
        try (InputStream inputStream = file.getInputStream()) {
            String fileText = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            json = new JSONObject(fileText);

        } catch (IOException e) {
            throw new IOException(e);
        }
        return json;
    }

    // Type decision
    private  Type getType(JSONObject jsonObject){
        Type type = Type.NOTSET;
        if(jsonObject.has(coreFieldString)){
            type = jsonObject.getJSONObject(coreFieldString).getEnum(Type.class, "type");
        }
        return type;
    }

    // TrendCreation
    private TrendReturnDTO createTrend(JSONObject trendJSON) {

        CoreFieldCreationDTO coreField = createCoreField(trendJSON.getJSONObject(coreFieldString));
        DetailedRatingCreationDTO detailedRating = createDetailedRating(trendJSON.getJSONObject(detailedRatingString));
        TrendRequiredCreationDTO trendRequired = createTrendRequired(trendJSON.getJSONObject("trendRequired"));
        TrendOptionalCreationDTO trendOptional = createTrendOptional(trendJSON.getJSONObject("trendOptional"));
        List<Long> internalProjects = getInternalList(internalProjectsString, trendJSON);
        List<Long> internalTrends = getInternalList(internalTrendsString, trendJSON);
        List<Long> internalTechnologies = getInternalList(internalTechnologiesString, trendJSON);
        List<Long> internalCompanies = getInternalList(internalCompaniesString, trendJSON);


        TrendCreationDTO trendCreationDTO = new TrendCreationDTO(coreField, detailedRating, trendRequired, trendOptional, internalProjects, internalTrends, internalTechnologies, internalCompanies);
        return trendController.createTrend(trendCreationDTO);
    }
    private TrendRequiredCreationDTO createTrendRequired(JSONObject trendRequiredJSON) {
        String description = trendRequiredJSON.getString(descriptionString);
        String discussion = trendRequiredJSON.getString("discussion");

        return new TrendRequiredCreationDTO(description, discussion);
    }
    private  TrendOptionalCreationDTO createTrendOptional(JSONObject trendOptionalJSON) {
        ArrayList<Contact> contacts = new ArrayList<>();
        trendOptionalJSON.getJSONArray(contactsString).forEach(contact -> contacts.add(createContact(contact)));

        ArrayList<ExternalItem> externalItems = new ArrayList<>();
        trendOptionalJSON.getJSONArray(externalItemsString).forEach(item -> externalItems.add(createExternalItem(item)));

        return new TrendOptionalCreationDTO(contacts, externalItems);
    }

    //CompanyCreation
    private CompanyReturnDTO createCompany(JSONObject companyJSON) {

        CoreFieldCreationDTO coreField = createCoreField(companyJSON.getJSONObject(coreFieldString));
        DetailedRatingCreationDTO detailedRating = createDetailedRating(companyJSON.getJSONObject(detailedRatingString));
        CompanyRequiredCreationDTO companyRequired = createCompanyRequired(companyJSON.getJSONObject("companyRequired"));
        CompanyOptionalCreationDTO companyOptional = createCompanyOptional(companyJSON.getJSONObject("companyOptional"));
        List<Long> internalProjects = getInternalList(internalProjectsString, companyJSON);
        List<Long> internalTrends = getInternalList(internalTrendsString, companyJSON);
        List<Long> internalTechnologies = getInternalList(internalTechnologiesString, companyJSON);
        List<Long> internalCompanies = getInternalList(internalCompaniesString, companyJSON);

        CompanyCreationDTO companyCreationDTO = new CompanyCreationDTO(coreField, detailedRating, companyRequired, companyOptional, internalProjects, internalTrends, internalTechnologies, internalCompanies);
        return companyController.createCompany(companyCreationDTO);
    }
    private  CompanyRequiredCreationDTO createCompanyRequired(JSONObject companyRequiredJSON) {
        String description = companyRequiredJSON.getString(descriptionString);
        String useCases = companyRequiredJSON.getString(useCasesString);
        TeamSizeCreationDTO teamSizeCreationDTO = createTeamSizeCreationDTO(companyRequiredJSON.getJSONObject("teamSize"));
        String website = companyRequiredJSON.getString(websiteString);
        int foundationYear = companyRequiredJSON.getInt("foundationYear");

        return new CompanyRequiredCreationDTO(description, useCases, teamSizeCreationDTO, website, foundationYear);
    }
    private  CompanyOptionalCreationDTO createCompanyOptional(JSONObject companyOptionalJSON) {

        ArrayList<ExternalItem> externalItems = new ArrayList<>();
        companyOptionalJSON.getJSONArray(externalItemsString).forEach(item -> externalItems.add(createExternalItem(item)));
        CompanyOptionalListsCreationDTO companyOptionalList = createCompanyOptionalListsCreationDTO(companyOptionalJSON.getJSONObject("companyOptionalLists"));
        CompanyOptionalFieldsCreationDTO companyOptionalFields = createCompanyOptionalFieldsCreationDTO(companyOptionalJSON.getJSONObject("companyOptionalFields"));

        return new CompanyOptionalCreationDTO(externalItems, companyOptionalList, companyOptionalFields);
    }

    //TechCreation
    private TechReturnDTO createTechnology(JSONObject techJSON) {
        CoreFieldCreationDTO coreField = createCoreField(techJSON.getJSONObject(coreFieldString));
        DetailedRatingCreationDTO detailedRating = createDetailedRating(techJSON.getJSONObject(detailedRatingString));
        TechRequiredCreationDTO techRequired = createTechRequired(techJSON.getJSONObject("techRequired"));
        TechOptionalCreationDTO techOptional = createTechOptional(techJSON.getJSONObject("techOptional"));
        List<Long> internalProjects = getInternalList(internalProjectsString, techJSON);
        List<Long> internalTrends = getInternalList(internalTrendsString, techJSON);
        List<Long> internalTechnologies = getInternalList(internalTechnologiesString, techJSON);
        List<Long> internalCompanies = getInternalList(internalCompaniesString, techJSON);


        TechCreationDTO techCreationDTO = new TechCreationDTO(coreField, detailedRating, techRequired, techOptional, internalProjects, internalTrends, internalTechnologies, internalCompanies);
        return technologyController.createTech(techCreationDTO);
    }
    private  TechRequiredCreationDTO createTechRequired(JSONObject techRequiredJSON) {

        String description = techRequiredJSON.getString(descriptionString);
        String useCases = techRequiredJSON.getString(useCasesString);
        String discussion = techRequiredJSON.getString("discussion");
        List<String> projectsIAO = jsonArrayToStringList(techRequiredJSON.getJSONArray("projectsIAO"));
        Readiness readiness = techRequiredJSON.getEnum(Readiness.class, "readiness");

        return new TechRequiredCreationDTO(description, useCases, discussion, projectsIAO, readiness);
    }
    private  TechOptionalCreationDTO createTechOptional(JSONObject techOptionalJSON) {

        ArrayList<Contact> contacts = new ArrayList<>();
        techOptionalJSON.getJSONArray(contactsString).forEach(contact -> contacts.add(createContact(contact)));
        List<String> references = jsonArrayToStringList(techOptionalJSON.getJSONArray("references"));
        ArrayList<ExternalItem> externalItems = new ArrayList<>();
        techOptionalJSON.getJSONArray(externalItemsString).forEach(item -> externalItems.add(createExternalItem(item)));
        List<Industry> industriesWithUse = techOptionalJSON.getJSONArray("industriesWithUse")
                .toList()
                .stream()
                .map(Object::toString)
                .map(Industry::valueOf)
                .toList();

        return new TechOptionalCreationDTO(contacts, references, externalItems, industriesWithUse);
    }

    //ProjectCreation
    private ProjectReturnDTO createProject(JSONObject projectJSON) {
        CoreFieldCreationDTO coreField = createCoreField(projectJSON.getJSONObject(coreFieldString));
        DetailedRatingCreationDTO detailedRating = createDetailedRating(projectJSON.getJSONObject(detailedRatingString));
        ProjectRequiredCreationDTO projectRequired = createProjectRequired(projectJSON.getJSONObject("projectRequired"));
        ProjectOptionalCreationDTO projectOptional = createProjectOptional(projectJSON.getJSONObject("projectOptional"));
        List<Long> internalProjects = getInternalList(internalProjectsString, projectJSON);
        List<Long> internalTrends = getInternalList(internalTrendsString, projectJSON);
        List<Long> internalTechnologies = getInternalList(internalTechnologiesString, projectJSON);
        List<Long> internalCompanies = getInternalList(internalCompaniesString, projectJSON);
        List<Event> events = new ArrayList<>();
        projectJSON.getJSONArray("events").forEach(event -> events.add(createEvent(event)));

        ProjectCreationDTO projectCreationDTO = new ProjectCreationDTO(coreField, detailedRating, projectRequired, projectOptional, internalProjects, internalTrends, internalTechnologies, internalCompanies, events);
        return  projectController.createProject(projectCreationDTO);
    }



    private  ProjectRequiredCreationDTO createProjectRequired(JSONObject projectRequiredJSON) {

        String description = projectRequiredJSON.getString(descriptionString);
        ReadinessProject readiness = projectRequiredJSON.getEnum(ReadinessProject.class, "readiness");
        RuntimeCreationDTO runtime = createRuntime(projectRequiredJSON.getJSONObject("runtime"));
        ArrayList<Contact> contacts = new ArrayList<>();
        projectRequiredJSON.getJSONArray(contactsString).forEach(contact -> contacts.add(createContact(contact)));

        return new ProjectRequiredCreationDTO(description, readiness, runtime, contacts);
    }
    private  ProjectOptionalCreationDTO createProjectOptional(JSONObject projectOptionalJSON) {

        ArrayList<ExternalItem> externalItems = new ArrayList<>();
        projectOptionalJSON.getJSONArray("externalItems").forEach(item -> externalItems.add(createExternalItem(item)));
        List<String> publications = jsonArrayToStringList(projectOptionalJSON.getJSONArray("publications"));
        ProjectsRelationsCreationDTO projectsRelations = createProjectRelations(projectOptionalJSON.getJSONObject("projectRelations"));
        ReferencedValueCreationDTO financing = createReferencedValueCreationDTO(projectOptionalJSON.getJSONObject("financing"));
        String website = projectOptionalJSON.getString("website");

        return new ProjectOptionalCreationDTO(externalItems, publications, projectsRelations, financing, website);
    }


    //Standard Methods used by Multiple Types
    private  CoreFieldCreationDTO createCoreField(JSONObject coreFieldJSON) {
        boolean intern = coreFieldJSON.getBoolean("intern");
        String source = coreFieldJSON.getString("source");
        List<String> imageSource = coreFieldJSON.getJSONArray("imageSource")
                .toList()
                .stream()
                .map(Object::toString)
                .toList();
        String headline = coreFieldJSON.getString("headline");
        String teaser = coreFieldJSON.getString("teaser");
        Industry industry = coreFieldJSON.getEnum(Industry.class, "industry");
        List<String> tags = coreFieldJSON.getJSONArray("tags")
                .toList()
                .stream()
                .map(Object::toString)
                .toList();
        Type type = coreFieldJSON.getEnum(Type.class, "type");

        return  new CoreFieldCreationDTO(intern, source, imageSource, headline, teaser, industry, tags, type);
    }
    private  DetailedRatingCreationDTO createDetailedRating(JSONObject detailedRatingJSON) {
        double degreeOfInnovation = detailedRatingJSON.getDouble("degreeOfInnovation");
        double disruptionPotential = detailedRatingJSON.getDouble("disruptionPotential");
        double useCases = detailedRatingJSON.getDouble("useCases");

        return new DetailedRatingCreationDTO(degreeOfInnovation, disruptionPotential, useCases);
    }
    private ExternalItem createExternalItem(Object item) {
        JSONObject jsonItem = (JSONObject) item;

        Type type = jsonItem.getEnum(Type.class, "type");
        String text = jsonItem.getString("text");
        return new ExternalItem(type, text);
    }
    private  ReferencedValueCreationDTO createReferencedValueCreationDTO(JSONObject referencedValueJSON) {
        String value = referencedValueJSON.getString("value");
        int year = referencedValueJSON.getInt("year");
        String reference = referencedValueJSON.getString("reference");

        return new ReferencedValueCreationDTO(value, year, reference);
    }
    private Contact createContact(Object contactObject) {
        JSONObject contactJSON = (JSONObject) contactObject;
        String name = contactJSON.getString("name");
        String email = contactJSON.getString("email");
        String telephone = contactJSON.getString("telephone");
        String organisation = contactJSON.getString("organisation");

        return new Contact(name, email, telephone, organisation);
    }
    private static List<String> jsonArrayToStringList(JSONArray jsonArray){
        return jsonArray.toList()
                .stream()
                .map(Object::toString)
                .toList();
    }
    private  List<Long> getInternalList(String internal, JSONObject json) {
        return  json.getJSONArray(internal)
                .toList()
                .stream()
                .map(Object::toString)
                .map(Long::valueOf)
                .toList();
    }
    private  Event createEvent(Object event) {

        JSONObject eventJSON = (JSONObject) event;
        Long date = eventJSON.getLong("date");
        String location = eventJSON.getString("location");
        String description = eventJSON.getString(descriptionString);
        String imageSource = eventJSON.getString("imageSource");
        String website = eventJSON.getString(websiteString);

        return new Event(date, location, description, imageSource, website);
    }

    // Methods used by only one Type, but got extracted due to design choices.
    private ProjectsRelationsCreationDTO createProjectRelations(JSONObject projectRelationsJSON) {

        List<String> fundingSources = jsonArrayToStringList(projectRelationsJSON.getJSONArray("fundingSources"));
        List<String> promoters = jsonArrayToStringList(projectRelationsJSON.getJSONArray("promoters"));
        List<String> projectPartners = jsonArrayToStringList(projectRelationsJSON.getJSONArray("projectPartners"));
        List<String> usePartners = jsonArrayToStringList(projectRelationsJSON.getJSONArray("usePartners"));

        return new ProjectsRelationsCreationDTO(fundingSources, promoters, projectPartners, usePartners);
    }
    private RuntimeCreationDTO createRuntime(JSONObject runtimeJSON) {

        Long start = runtimeJSON.getLong("start");
        Long finished = runtimeJSON.getLong("finished");
        boolean isUseOnlyYear = runtimeJSON.getBoolean("useOnlyYear");

        return new RuntimeCreationDTO(start, finished, isUseOnlyYear);
    }
    private TeamSizeCreationDTO createTeamSizeCreationDTO(JSONObject teamSizeJSON) {
        TeamSizeEnum teamSize = teamSizeJSON.getEnum(TeamSizeEnum.class, "teamSize");
        int year = teamSizeJSON.getInt("year");
        String reference = teamSizeJSON.getString("reference");

        return new TeamSizeCreationDTO(teamSize, year, reference);
    }
    private  CompanyOptionalFieldsCreationDTO createCompanyOptionalFieldsCreationDTO(JSONObject companyOptionalFieldsJSON) {
        LocationCreationDTO location = createLocationCreationDTO(companyOptionalFieldsJSON.getJSONObject("location"));
        Readiness productReadiness = companyOptionalFieldsJSON.getEnum(Readiness.class, "productReadiness");
        ReferencedValueCreationDTO numberOfCustomers = createReferencedValueCreationDTO(companyOptionalFieldsJSON.getJSONObject("numberOfCustomers"));
        ReferencedValueCreationDTO revenue = createReferencedValueCreationDTO(companyOptionalFieldsJSON.getJSONObject("revenue"));
        ReferencedValueCreationDTO profit = createReferencedValueCreationDTO(companyOptionalFieldsJSON.getJSONObject("profit"));

        return new CompanyOptionalFieldsCreationDTO(location, productReadiness, numberOfCustomers, revenue, profit);
    }
    private LocationCreationDTO createLocationCreationDTO(JSONObject location) {
        String country = location.getString("country");
        String city = location.getString("city");

        return new LocationCreationDTO(country, city);
    }
    private  CompanyOptionalListsCreationDTO createCompanyOptionalListsCreationDTO(JSONObject companyOptionalListsJSON) {
        ArrayList<Contact> contacts = new ArrayList<>();
        companyOptionalListsJSON.getJSONArray(contactsString).forEach(contact -> contacts.add(createContact(contact)));
        List<Markets> targetMarkets = companyOptionalListsJSON.getJSONArray("targetMarkets")
                .toList()
                .stream()
                .map(Object::toString)
                .map(Markets::valueOf)
                .toList();
        List<Alignment> alignments = companyOptionalListsJSON.getJSONArray("alignments")
                .toList()
                .stream()
                .map(Object::toString)
                .map(Alignment::valueOf)
                .toList();
        List<String> partners = jsonArrayToStringList(companyOptionalListsJSON.getJSONArray("partners"));
        List<String> investors = jsonArrayToStringList(companyOptionalListsJSON.getJSONArray("investors"));

        return new CompanyOptionalListsCreationDTO(contacts, targetMarkets, alignments, partners, investors);
    }
}
