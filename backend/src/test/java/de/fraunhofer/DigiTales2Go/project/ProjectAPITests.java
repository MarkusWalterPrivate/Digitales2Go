package de.fraunhofer.DigiTales2Go.project;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import com.jayway.jsonpath.JsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectAPITests {

    @Autowired
    private MockMvc mockMvc;

    private final static String id = "42";
    private final static HashMap<String, String> coreFieldValues = new HashMap<>();
    private final static HashMap<String, String> projectRequiredValues = new HashMap<>();
    private final static HashMap<String, String> projectOptionalValues = new HashMap<>();
    private final static HashMap<String, String> projectEventValues = new HashMap<>();
    private final static HashMap<String, String> comments = new HashMap<>();
    private static String minimalBody;
    private static String fullBody;

    private static String adminMail;
    private static String adminPassword;
    private String adminToken;

    public String generateAdminToken() throws Exception {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("./src/main/resources/application.properties"));
            properties.load(fis);
            adminMail = (String) properties.get("admin.mail");
            adminPassword = (String) properties.get("admin.password");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String res = mockMvc.perform(post("/auth/login").content("{\"email\": \"" + adminMail + "\", \"password\": \"" + adminPassword + "\"}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String token = JsonPath.parse(res).read("$.token");
        return token;
    }

    @BeforeAll
    private void init() {
        try {
            adminToken = generateAdminToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        coreFieldValues.put("id", "1337");
        coreFieldValues.put("lastUpdated", "1654805033432");
        coreFieldValues.put("creationDate", "1654805033432");
        coreFieldValues.put("rating", "9001.0");
        coreFieldValues.put("intern", "true");
        coreFieldValues.put("source", "source");
        coreFieldValues.put("imageSource", "imageSource");
        coreFieldValues.put("headline", "DigiTales2Go");
        coreFieldValues.put("teaser", "Do incredible amount of work at almost zero cost by enslaving students");
        coreFieldValues.put("industry", "SOFTWARE");
        coreFieldValues.put("tag1", "Tag1");
        coreFieldValues.put("tag2", "Tag2");
        coreFieldValues.put("type", "PROJECT");

        projectRequiredValues.put("id", "1111");
        projectRequiredValues.put("description", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
        projectRequiredValues.put("readiness", "DEVELOPMENT");
        projectRequiredValues.put("runtimeId", "231");
        projectRequiredValues.put("runtimeStart", "21321321");
        projectRequiredValues.put("runtimeFinished", "214523545456");
        projectRequiredValues.put("contactsId", "78778");
        projectRequiredValues.put("contactsName", "Hans");
        projectRequiredValues.put("contactsEmail", "hans@Wurst.der");
        projectRequiredValues.put("contactsTelephone", "+(049)123 45678789");
        projectRequiredValues.put("contactsOrganisation", "Metzgerei Hans");

        projectOptionalValues.put("id", "333");
        projectOptionalValues.put("internalTechnologies", null);
        projectOptionalValues.put("externalTechnologies", null);
        projectOptionalValues.put("publications", null);
        projectOptionalValues.put("projectRelationsId", null);
        projectOptionalValues.put("projectRelationsFundingSources", null);
        projectOptionalValues.put("projectRelationsPromoters", null);
        projectOptionalValues.put("projectRelationsProjectPartners", null);
        projectOptionalValues.put("projectRelationsUsePartners", null);
        projectOptionalValues.put("financing", "1000.0");
        projectOptionalValues.put("financingReference", "Google.com");
        projectOptionalValues.put("website", "https://rr.noordstar.me/1ab81afc");

        minimalBody =
                "{" +
                "    \"coreField\": {" +
                "       \"intern\": " + coreFieldValues.get("intern") + "," +
                "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                "        \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                "        \"headline\": \"" + coreFieldValues.get("headline") +"\"," +
                "        \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                "        \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                "        \"tags\": [" +
                "            \"" + coreFieldValues.get("tag1") + "\"," +
                "            \"" + coreFieldValues.get("tag2") + "\"" +
                "        ]," +
                "        \"type\": \"" + coreFieldValues.get("type") + "\"" +
                "    }," +
                        "   \"detailedRating\":{"+
                        "       \"degreeOfInnovation\": 1,"+
                        "       \"disruptionPotential\": 1,"+
                        "       \"useCases\": 1"+
                        "   }," +
                "    \"projectRequired\": {" +
                "        \"description\": \"" + projectRequiredValues.get("description") + "\"," +
                "        \"readiness\": \"" + projectRequiredValues.get("readiness") + "\"," +
                "        \"runtime\": {" +
                "            \"start\": " + projectRequiredValues.get("runtimeStart") + "," +
                "            \"finished\": " + projectRequiredValues.get("runtimeFinished") +
                "        }," +
                "        \"contacts\": [" +
                "            {" +
                "                \"name\": \"" + projectRequiredValues.get("contactsName") + "\"," +
                "                \"email\": \"" + projectRequiredValues.get("contactsEmail") + "\"," +
                "                \"telephone\": \"" + projectRequiredValues.get("contactsTelephone") + "\"," +
                "                \"organisation\": \"" + projectRequiredValues.get("contactsOrganisation") + "\"" +
                "            }" +
                "        ]" +
                "    }," +
                "    \"events\": []" +
                "}";

        String allOptionals =
                "    \"projectOptional\": {" +
                "        \"internalTechnologies\": []," +
                "        \"externalTechnologies\": []," +
                "        \"publications\": []," +
                "        \"projectRelations\": {" +
                "            \"fundingSources\": []," +
                "            \"promoters\": []," +
                "            \"projectPartners\": []," +
                "            \"usePartners\": []" +
                "        }," +
                "        \"financing\": {"+
                "           \"value\": \""  + projectOptionalValues.get("financing") + "\"," +
                "           \"year\": 2000," +
                "           \"reference\": \""+ projectOptionalValues.get("financingReference") +"\""+
                "        }," +
                "        \"website\": \"" + projectOptionalValues.get("website") + "\"" +
                "    }";

        fullBody = buildBody(allOptionals);
    }

    private static String buildBody(String optionalValues){
        return "{" +
                "    \"coreField\": {" +
                "       \"intern\": " + coreFieldValues.get("intern") + "," +
                "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                "        \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                "        \"headline\": \"" + coreFieldValues.get("headline") +"\"," +
                "        \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                "        \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                "        \"tags\": [" +
                "            \"" + coreFieldValues.get("tag1") + "\"," +
                "            \"" + coreFieldValues.get("tag2") + "\"" +
                "        ]," +
                "        \"type\": \"" + coreFieldValues.get("type") + "\"" +
                "    }," +
                "   \"detailedRating\":{"+
                "       \"degreeOfInnovation\": 1,"+
                "       \"disruptionPotential\": 1,"+
                "       \"useCases\": 1"+
                "   }," +
                "    \"projectRequired\": {" +
                "        \"description\": \"" + projectRequiredValues.get("description") + "\"," +
                "        \"readiness\": \"" + projectRequiredValues.get("readiness") + "\"," +
                "        \"runtime\": {" +
                "            \"start\": " + projectRequiredValues.get("runtimeStart") + "," +
                "            \"finished\": " + projectRequiredValues.get("runtimeFinished") +
                "        }," +
                "        \"contacts\": [" +
                "            {" +
                "                \"name\": \"" + projectRequiredValues.get("contactsName") + "\"," +
                "                \"email\": \"" + projectRequiredValues.get("contactsEmail") + "\"," +
                "                \"telephone\": \"" + projectRequiredValues.get("contactsTelephone") + "\"," +
                "                \"organisation\": \"" + projectRequiredValues.get("contactsOrganisation") + "\"" +
                "            }" +
                "        ]" +
                "    }," +
                optionalValues + "," +
                "    \"events\": []," +
                "    \"comments\": []" +
                "}";

    }


    @Test
    void createNewMinimalProject() throws Exception{
        //create new project with minimal allowed values
        MvcResult result = this.mockMvc.perform(post("/project/").contentType(MediaType.APPLICATION_JSON).content(minimalBody).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").isNumber())
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value(coreFieldValues.get("headline")))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value(coreFieldValues.get("teaser")))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Software und Computerdienstleistungen"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Projekt"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.projectRequired.description").isString())
                .andExpect(jsonPath("$.projectRequired.description").value(projectRequiredValues.get("description")))
                .andExpect(jsonPath("$.projectRequired.readiness").isString())
                .andExpect(jsonPath("$.projectRequired.readiness").value("In Entwicklung"))
                .andExpect(jsonPath("$.projectRequired.runtime.id").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").value(Long.valueOf(projectRequiredValues.get("runtimeStart"))))
                .andExpect(jsonPath("$.projectRequired.runtime.finished").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.finished").value(Long.valueOf(projectRequiredValues.get("runtimeFinished"))))
                .andExpect(jsonPath("$.projectRequired.contacts").isArray())
                .andExpect(jsonPath("$.projectRequired.contacts", hasSize(1)))
                .andExpect(jsonPath("$.projectRequired.contacts[0].id").isNumber())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").value(projectRequiredValues.get("contactsName")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").value(projectRequiredValues.get("contactsEmail")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").value(projectRequiredValues.get("contactsTelephone")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").value(projectRequiredValues.get("contactsOrganisation")))
                .andExpect(jsonPath("$.events").isArray())
                .andExpect(jsonPath("$.events", hasSize(0)))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)))
                .andReturn();
        //save project id
        String response = result.getResponse().getContentAsString();
        int testProjectId = JsonPath.parse(response).read("$['id']");
        //delete project
        this.mockMvc.perform(delete("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void createNewFullProject() throws Exception{
        //create new project with all allowed values
        MvcResult result = this.mockMvc.perform(post("/project/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(fullBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").isNumber())
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value(coreFieldValues.get("headline")))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value(coreFieldValues.get("teaser")))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Software und Computerdienstleistungen"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Projekt"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.projectRequired.description").isString())
                .andExpect(jsonPath("$.projectRequired.description").value(projectRequiredValues.get("description")))
                .andExpect(jsonPath("$.projectRequired.readiness").isString())
                .andExpect(jsonPath("$.projectRequired.readiness").value("In Entwicklung"))
                .andExpect(jsonPath("$.projectRequired.runtime.id").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").value(Long.valueOf(projectRequiredValues.get("runtimeStart"))))
                .andExpect(jsonPath("$.projectRequired.runtime.finished").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.finished").value(Long.valueOf(projectRequiredValues.get("runtimeFinished"))))
                .andExpect(jsonPath("$.projectRequired.contacts").isArray())
                .andExpect(jsonPath("$.projectRequired.contacts", hasSize(1)))
                .andExpect(jsonPath("$.projectRequired.contacts[0].id").isNumber())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").value(projectRequiredValues.get("contactsName")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").value(projectRequiredValues.get("contactsEmail")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").value(projectRequiredValues.get("contactsTelephone")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").value(projectRequiredValues.get("contactsOrganisation")))
                .andExpect(jsonPath("$.projectOptional.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.externalItems").isArray())
                .andExpect(jsonPath("$.projectOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.publications").isArray())
                .andExpect(jsonPath("$.projectOptional.publications", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.projectRelations.fundingSources").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.fundingSources", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.promoters").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.promoters", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.projectPartners").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.projectPartners", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.usePartners").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.usePartners", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.financing.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.financing.value").isString())
                .andExpect(jsonPath("$.projectOptional.financing.value").value("1000.0"))
                .andExpect(jsonPath("$.projectOptional.financing.year").isNumber())
                .andExpect(jsonPath("$.projectOptional.financing.year").value(2000))
                .andExpect(jsonPath("$.projectOptional.financing.reference").isString())
                .andExpect(jsonPath("$.projectOptional.financing.reference").value("Google.com"))
                .andExpect(jsonPath("$.projectOptional.website").isString())
                .andExpect(jsonPath("$.projectOptional.website").value(projectOptionalValues.get("website")))
                .andExpect(jsonPath("$.events").isArray())
                .andExpect(jsonPath("$.events", hasSize(0)))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)))
                .andReturn();
        //save project id
        String response = result.getResponse().getContentAsString();
        int testProjectId = JsonPath.parse(response).read("$['id']");
        //delete project
        this.mockMvc.perform(delete("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }



    @Test
    void getAllProjects() throws Exception {
        this.mockMvc.perform(get("/project/").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getProjectMinimal() throws Exception{
        //create new Project
        MvcResult result = this.mockMvc.perform(post("/project/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andReturn();
        //save project id
        String response = result.getResponse().getContentAsString();
        int testProjectId = JsonPath.parse(response).read("$['id']");
        //get Project
        this.mockMvc.perform(get("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").isNumber())
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value(coreFieldValues.get("headline")))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value(coreFieldValues.get("teaser")))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Software und Computerdienstleistungen"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Projekt"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.projectRequired.description").isString())
                .andExpect(jsonPath("$.projectRequired.description").value(projectRequiredValues.get("description")))
                .andExpect(jsonPath("$.projectRequired.readiness").isString())
                .andExpect(jsonPath("$.projectRequired.readiness").value("In Entwicklung"))
                .andExpect(jsonPath("$.projectRequired.runtime.id").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").value(Long.valueOf(projectRequiredValues.get("runtimeStart"))))
                .andExpect(jsonPath("$.projectRequired.runtime.finished").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.finished").value(Long.valueOf(projectRequiredValues.get("runtimeFinished"))))
                .andExpect(jsonPath("$.projectRequired.contacts").isArray())
                .andExpect(jsonPath("$.projectRequired.contacts", hasSize(1)))
                .andExpect(jsonPath("$.projectRequired.contacts[0].id").isNumber())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").value(projectRequiredValues.get("contactsName")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").value(projectRequiredValues.get("contactsEmail")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").value(projectRequiredValues.get("contactsTelephone")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").value(projectRequiredValues.get("contactsOrganisation")))
                .andExpect(jsonPath("$.events").isArray())
                .andExpect(jsonPath("$.events", hasSize(0)))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));
        //delete Project
        this.mockMvc.perform(delete("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void getProjectFull() throws Exception{
        //create new Project
        MvcResult result = this.mockMvc.perform(post("/project/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(fullBody))
                .andReturn();
        //save project id
        String response = result.getResponse().getContentAsString();
        int testProjectId = JsonPath.parse(response).read("$['id']");
        //get Project
        this.mockMvc.perform(get("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").isNumber())
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value(coreFieldValues.get("headline")))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value(coreFieldValues.get("teaser")))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Software und Computerdienstleistungen"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Projekt"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.projectRequired.description").isString())
                .andExpect(jsonPath("$.projectRequired.description").value(projectRequiredValues.get("description")))
                .andExpect(jsonPath("$.projectRequired.readiness").isString())
                .andExpect(jsonPath("$.projectRequired.readiness").value("In Entwicklung"))
                .andExpect(jsonPath("$.projectRequired.runtime.id").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").value(Long.valueOf(projectRequiredValues.get("runtimeStart"))))
                .andExpect(jsonPath("$.projectRequired.runtime.finished").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.finished").value(Long.valueOf(projectRequiredValues.get("runtimeFinished"))))
                .andExpect(jsonPath("$.projectRequired.contacts").isArray())
                .andExpect(jsonPath("$.projectRequired.contacts", hasSize(1)))
                .andExpect(jsonPath("$.projectRequired.contacts[0].id").isNumber())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").value(projectRequiredValues.get("contactsName")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").value(projectRequiredValues.get("contactsEmail")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").value(projectRequiredValues.get("contactsTelephone")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").value(projectRequiredValues.get("contactsOrganisation")))
                .andExpect(jsonPath("$.projectOptional.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.externalItems").isArray())
                .andExpect(jsonPath("$.projectOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.publications").isArray())
                .andExpect(jsonPath("$.projectOptional.publications", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.projectRelations.fundingSources").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.fundingSources", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.promoters").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.promoters", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.projectPartners").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.projectPartners", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.usePartners").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.usePartners", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.financing.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.financing.value").isString())
                .andExpect(jsonPath("$.projectOptional.financing.value").value("1000.0"))
                .andExpect(jsonPath("$.projectOptional.financing.year").isNumber())
                .andExpect(jsonPath("$.projectOptional.financing.year").value(2000))
                .andExpect(jsonPath("$.projectOptional.financing.reference").isString())
                .andExpect(jsonPath("$.projectOptional.financing.reference").value("Google.com"))
                .andExpect(jsonPath("$.projectOptional.website").isString())
                .andExpect(jsonPath("$.projectOptional.website").value(projectOptionalValues.get("website")))
                .andExpect(jsonPath("$.events").isArray())
                .andExpect(jsonPath("$.events", hasSize(0)))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));
        //delete Project
        this.mockMvc.perform(delete("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void getProjectWithFaultyRequest() throws Exception{
        this.mockMvc.perform(get("/project/asdas").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUnusedProject() throws Exception{
        // if for some reason a tech with this ID does exist, change the number
        this.mockMvc.perform(get("/Project/1353453423").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void editProject() throws Exception{
        //create Project
        MvcResult result = this.mockMvc.perform(post("/project/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        //Save project id
        String response = result.getResponse().getContentAsString();
        int testProjectId = JsonPath.parse(response).read("$['id']");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int projectOptionalId = JsonPath.parse(response).read("$.projectOptional.id");
        String changeBody =
                    "{" +
                    "    \"id\": " + testProjectId +
                    "    \"coreField\": {" +
                    "        \"id\": " + coreFieldId +
                    "       \"intern\": " + coreFieldValues.get("intern") + "," +
                    "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                    "        \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                    "        \"headline\": \"" + coreFieldValues.get("headline") +"\"," +
                    "        \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                    "        \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                    "        \"tags\": [" +
                    "            \"" + coreFieldValues.get("tag1") + "\"," +
                    "            \"" + coreFieldValues.get("tag2") + "\"" +
                    "        ]," +
                    "        \"type\": \"" + coreFieldValues.get("type") + "\"" +
                    "    }," +
                            "   \"detailedRating\":{"+
                            "       \"degreeOfInnovation\": 1,"+
                            "       \"disruptionPotential\": 1,"+
                            "       \"useCases\": 1"+
                            "   }," +
                    "    \"projectRequired\": {" +
                    "        \"description\": \"" + projectRequiredValues.get("description") + "\"," +
                    "        \"readiness\": \"" + projectRequiredValues.get("readiness") + "\"," +
                    "        \"runtime\": {" +
                    "            \"start\": " + projectRequiredValues.get("runtimeStart") + "," +
                    "            \"finished\": " + projectRequiredValues.get("runtimeFinished") +
                    "        }," +
                    "        \"contacts\": [" +
                    "            {" +
                    "                \"name\": \"" + projectRequiredValues.get("contactsName") + "\"," +
                    "                \"email\": \"" + projectRequiredValues.get("contactsEmail") + "\"," +
                    "                \"telephone\": \"" + projectRequiredValues.get("contactsTelephone") + "\"," +
                    "                \"organisation\": \"" + projectRequiredValues.get("contactsOrganisation") + "\"" +
                    "            }" +
                    "        ]" +
                    "    }," +
                    "    \"projectOptional\": {" +
                    "        \" id\": " + projectOptionalId +
                    "        \"internalTechnologies\": []," +
                    "        \"externalTechnologies\": []," +
                    "        \"publications\": []," +
                    "        \"projectRelations\": {" +
                    "            \"fundingSources\": []," +
                    "            \"promoters\": []," +
                    "            \"projectPartners\": []," +
                    "            \"usePartners\": []" +
                    "        }," +
                    "        \"financing\": {"+
                    "           \"value\":"+ projectOptionalValues.get("financing") + "," +
                    "           \"year\": 2000," +
                    "           \"reference\":"+ projectOptionalValues.get("financingReference") +
                    "        }," +
                    "        \"website\": \"" + projectOptionalValues.get("website") + "\"" +
                    "    }," +
                    "    \"events\": []" +
                    "}";

        //update Project
        this.mockMvc.perform(put("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(fullBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").isNumber())
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value(coreFieldValues.get("headline")))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value(coreFieldValues.get("teaser")))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Software und Computerdienstleistungen"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Projekt"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.projectRequired.description").isString())
                .andExpect(jsonPath("$.projectRequired.description").value(projectRequiredValues.get("description")))
                .andExpect(jsonPath("$.projectRequired.readiness").isString())
                .andExpect(jsonPath("$.projectRequired.readiness").value("In Entwicklung"))
                .andExpect(jsonPath("$.projectRequired.runtime.id").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.start").value(Long.valueOf(projectRequiredValues.get("runtimeStart"))))
                .andExpect(jsonPath("$.projectRequired.runtime.finished").isNumber())
                .andExpect(jsonPath("$.projectRequired.runtime.finished").value(Long.valueOf(projectRequiredValues.get("runtimeFinished"))))
                .andExpect(jsonPath("$.projectRequired.contacts").isArray())
                .andExpect(jsonPath("$.projectRequired.contacts", hasSize(1)))
                .andExpect(jsonPath("$.projectRequired.contacts[0].id").isNumber())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].name").value(projectRequiredValues.get("contactsName")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].email").value(projectRequiredValues.get("contactsEmail")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].telephone").value(projectRequiredValues.get("contactsTelephone")))
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").isString())
                .andExpect(jsonPath("$.projectRequired.contacts[0].organisation").value(projectRequiredValues.get("contactsOrganisation")))
                .andExpect(jsonPath("$.projectOptional.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.externalItems").isArray())
                .andExpect(jsonPath("$.projectOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.publications").isArray())
                .andExpect(jsonPath("$.projectOptional.publications", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.projectRelations.fundingSources").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.fundingSources", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.promoters").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.promoters", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.projectPartners").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.projectPartners", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.projectRelations.usePartners").isArray())
                .andExpect(jsonPath("$.projectOptional.projectRelations.usePartners", hasSize(0)))
                .andExpect(jsonPath("$.projectOptional.financing.id").isNumber())
                .andExpect(jsonPath("$.projectOptional.financing.value").isString())
                .andExpect(jsonPath("$.projectOptional.financing.value").value("1000.0"))
                .andExpect(jsonPath("$.projectOptional.financing.year").isNumber())
                .andExpect(jsonPath("$.projectOptional.financing.year").value(2000))
                .andExpect(jsonPath("$.projectOptional.financing.reference").isString())
                .andExpect(jsonPath("$.projectOptional.financing.reference").value("Google.com"))
                .andExpect(jsonPath("$.projectOptional.website").isString())
                .andExpect(jsonPath("$.projectOptional.website").value(projectOptionalValues.get("website")))
                .andExpect(jsonPath("$.events").isArray())
                .andExpect(jsonPath("$.events", hasSize(0)))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));
                System.out.println("HELLO");
        //delete project
        this.mockMvc.perform(delete("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void editNonExistingProject() throws Exception{
        //update Project
        this.mockMvc.perform(put("/project/1234567890").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(fullBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProject() throws Exception{
        //Create Project
        MvcResult result =  this.mockMvc.perform(post("/project/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andReturn();
        //Save project id
        String response = result.getResponse().getContentAsString();
        int testProjectId = JsonPath.parse(response).read("$['id']");
        //Delete project
        this.mockMvc.perform(delete("/project/"+testProjectId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNonExistingTrend() throws Exception {
        this.mockMvc.perform(delete("/project/1234567890").header("Authorization", "Bearer " + adminToken)).andExpect(status().isNotFound());
    }
}