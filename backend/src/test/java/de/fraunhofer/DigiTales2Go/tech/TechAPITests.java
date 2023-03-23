package de.fraunhofer.DigiTales2Go.tech;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TechAPITests {

    @Autowired
    private MockMvc mockMvc;


    private final static String id = "42";
    private final static HashMap<String, String> coreFieldValues = new HashMap<>();
    private final static HashMap<String, String> detailedRatingValues = new HashMap<>();
    private final static HashMap<String, String> techRequiredValues = new HashMap<>();
    private final static HashMap<String, String> techOptionalValues = new HashMap<>();
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
        coreFieldValues.put("id", "42");
        coreFieldValues.put("lastUpdated", "1654438851102");
        coreFieldValues.put("creationDate", "1654438851102");
        coreFieldValues.put("rating", "0.0");
        coreFieldValues.put("intern", "true");
        coreFieldValues.put("source", "source");
        coreFieldValues.put("imageSource", "imageSource");
        coreFieldValues.put("headline", "Sail");
        coreFieldValues.put("teaser", "Discover new Lands through green energy");
        coreFieldValues.put("industry", "ALTERNATEENERGY");
        coreFieldValues.put("type", "TECHNOLOGY");

        detailedRatingValues.put("degreeOfInnovation", "0.0");
        detailedRatingValues.put("disruptionPotential", "0.0");
        detailedRatingValues.put("useCases", "0.0");

        techRequiredValues.put("id", "42");
        techRequiredValues.put("description", "This is demo text");
        techRequiredValues.put("discussion", "asuhdiasbdias");
        techRequiredValues.put("readiness", "COMMERCIAL");
        techRequiredValues.put("useCases", "sdasdasd");
        techRequiredValues.put("iaoProjects1", "asdasd");
        techRequiredValues.put("iaoProjects2", "afasd");

        techOptionalValues.put("id", "42");
        techOptionalValues.put("contactsId", "42");
        techOptionalValues.put("contactsName", "Peter");
        techOptionalValues.put("contactsEmail", "peter@parker.net");
        techOptionalValues.put("contactsTelephone", "180231270");
        techOptionalValues.put("contactsOrganisation", "Avanger");
        techOptionalValues.put("references", null);
        techOptionalValues.put("internalProjects", null);
        techOptionalValues.put("externalProjects", null);
        techOptionalValues.put("industriesWithUse1", "AEROSPACE");
        techOptionalValues.put("industriesWithUse2", "AUTOMOBILE");
        techOptionalValues.put("industriesWithUse3", "BEVERAGE");

        minimalBody =
                "{" +
                "    \"coreField\": {" +
                "       \"intern\": " + coreFieldValues.get("intern") + "," +
                "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                "        \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                "        \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                "        \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                "        \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                "        \"tags\": [\"New\", \"Hot\"]," +
                "        \"type\": \""  + coreFieldValues.get("type") + "\"" +
                "    }," +
                "   \"detailedRating\":{"+
                "       \"degreeOfInnovation\": 1,"+
                "       \"disruptionPotential\": 1,"+
                "       \"useCases\": 1"+
                "   }," +
                "    \"techRequired\": {" +
                "        \"description\": \"" + techRequiredValues.get("description") + "\"," +
                "        \"useCases\": \"" + techRequiredValues.get("useCases") + "\"," +
                "        \"discussion\": \"" + techRequiredValues.get("discussion") + "\"," +
                "        \"projectsIAO\": [" +
                "            \"" + techRequiredValues.get("iaoProjects1") + "\"," +
                "            \"" + techRequiredValues.get("iaoProjects2") + "\"" +
                "        ]," +
                "        \"readiness\": \"" + techRequiredValues.get("readiness") + "\"" +
                "    }," +
                "    \"comments\": []" +
                "}";

        String allOptionals =
                "    \"techOptional\": {" +
                "        \"contacts\": [" +
                "            {" +
                "                \"name\": \"" + techOptionalValues.get("contactsName") + "\"," +
                "                \"email\": \"" + techOptionalValues.get("contactsEmail") + "\"," +
                "                \"telephone\": \"" + techOptionalValues.get("contactsTelephone") + "\"," +
                "                \"organisation\": \"" + techOptionalValues.get("contactsOrganisation") + "\"" +
                "            }" +
                "        ]," +
                "        \"references\": []," +
                "        \"internalProjects\": []," +
                "        \"externalProjects\": []," +
                "        \"industriesWithUse\": [" +
                "            \"" + techOptionalValues.get("industriesWithUse1") + "\"," +
                "            \"" + techOptionalValues.get("industriesWithUse2") + "\"," +
                "            \"" + techOptionalValues.get("industriesWithUse3") + "\"" +
                "        ]" +
                "    },";

        fullBody = buildBody(allOptionals);
    }

    private static String buildBody(String optionalValues){
        return "{" +
                "    \"coreField\": {" +
                "       \"intern\": " + coreFieldValues.get("intern") + "," +
                "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                "        \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                "        \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                "        \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                "        \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                "        \"tags\": [\"New\", \"Hot\"]," +
                "        \"type\": \""  + coreFieldValues.get("type") + "\"" +
                "    }," +
                "   \"detailedRating\":{"+
                "       \"degreeOfInnovation\": 1,"+
                "       \"disruptionPotential\": 1,"+
                "       \"useCases\": 1"+
                "   }," +
                "    \"techRequired\": {" +
                "        \"description\": \"" + techRequiredValues.get("description") + "\"," +
                "        \"useCases\": \"" + techRequiredValues.get("useCases") + "\"," +
                "        \"discussion\": \"" + techRequiredValues.get("discussion") + "\"," +
                "        \"projectsIAO\": [" +
                "            \"" + techRequiredValues.get("iaoProjects1") + "\"," +
                "            \"" + techRequiredValues.get("iaoProjects2") + "\"" +
                "        ]," +
                "        \"readiness\": \"" + techRequiredValues.get("readiness") + "\"" +
                "    }," +
                optionalValues +
                "    \"comments\": []" +
                "}";

    }


    @Test
    void createNewMinimalTech() throws Exception{
        //create new tech
        MvcResult result = this.mockMvc.perform(post("/tech/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
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
                .andExpect(jsonPath("$.coreField.industry").value("Alternative Energie"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("New", "Hot")))
                .andExpect(jsonPath("$.coreField.type").value("Technologie"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.techRequired.description").isString())
                .andExpect(jsonPath("$.techRequired.description").value(techRequiredValues.get("description")))
                .andExpect(jsonPath("$.techRequired.useCases").isString())
                .andExpect(jsonPath("$.techRequired.useCases").value(techRequiredValues.get("useCases")))
                .andExpect(jsonPath("$.techRequired.discussion").isString())
                .andExpect(jsonPath("$.techRequired.discussion").value(techRequiredValues.get("discussion")))
                .andExpect(jsonPath("$.techRequired.readiness").value("In kommerzieller Nutzung"))
                .andExpect(jsonPath("$.techRequired.projectsIAO").isArray())
                .andExpect(jsonPath("$.techRequired.projectsIAO", hasSize(2)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)))
                .andReturn();
        //save tech id
        String response = result.getResponse().getContentAsString();
        int testTechId = JsonPath.parse(response).read("$['id']");
        //delete tech
        this.mockMvc.perform(delete("/tech/"+testTechId).header("Authorization", "Bearer " + adminToken));
    }

    @Test
    void createNewFullTech() throws Exception{
        //create new tech
        MvcResult result = this.mockMvc.perform(post("/tech/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(fullBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
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
                .andExpect(jsonPath("$.coreField.industry").value("Alternative Energie"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("New", "Hot")))
                .andExpect(jsonPath("$.coreField.type").value("Technologie"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.techRequired.description").isString())
                .andExpect(jsonPath("$.techRequired.description").value(techRequiredValues.get("description")))
                .andExpect(jsonPath("$.techRequired.useCases").isString())
                .andExpect(jsonPath("$.techRequired.useCases").value(techRequiredValues.get("useCases")))
                .andExpect(jsonPath("$.techRequired.discussion").isString())
                .andExpect(jsonPath("$.techRequired.discussion").value(techRequiredValues.get("discussion")))
                .andExpect(jsonPath("$.techRequired.readiness").value("In kommerzieller Nutzung"))
                .andExpect(jsonPath("$.techRequired.projectsIAO").isArray())
                .andExpect(jsonPath("$.techRequired.projectsIAO", hasSize(2)))
                .andExpect(jsonPath("$.techOptional.contacts").isArray())
                .andExpect(jsonPath("$.techOptional.contacts[0].name").isString())
                .andExpect(jsonPath("$.techOptional.contacts[0].name").value(techOptionalValues.get("contactsName")))
                .andExpect(jsonPath("$.techOptional.contacts[0].email").isString())
                .andExpect(jsonPath("$.techOptional.contacts[0].email").value(techOptionalValues.get("contactsEmail")))
                .andExpect(jsonPath("$.techOptional.contacts[0].telephone").isString())
                .andExpect(jsonPath("$.techOptional.contacts[0].telephone").value(techOptionalValues.get("contactsTelephone")))
                .andExpect(jsonPath("$.techOptional.contacts[0].organisation").isString())
                .andExpect(jsonPath("$.techOptional.contacts[0].organisation").value(techOptionalValues.get("contactsOrganisation")))
                .andExpect(jsonPath("$.techOptional.references").isArray())
                .andExpect(jsonPath("$.techOptional.references", hasSize(0)))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                .andExpect(jsonPath("$.techOptional.externalItems").isArray())
                .andExpect(jsonPath("$.techOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.techOptional.industriesWithUse").isArray())
                .andExpect(jsonPath("$.techOptional.industriesWithUse", hasSize(3)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)))
                .andReturn();
        //save tech id
        String response = result.getResponse().getContentAsString();
        int testTechId = JsonPath.parse(response).read("$['id']");
        //delete tech
        this.mockMvc.perform(delete("/tech/"+testTechId).header("Authorization", "Bearer " + adminToken));
    }
    @Test
    void deleteTech() throws Exception{
        //Create tech
        MvcResult result =  this.mockMvc.perform(post("/tech/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andReturn();
        //Save tech id
        String response = result.getResponse().getContentAsString();
        int testTechId = JsonPath.parse(response).read("$['id']");
        //Delete tech
        this.mockMvc.perform(delete("/tech/"+testTechId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }
    @Test
    void getTech() throws Exception{
        //create new tech
        MvcResult result = this.mockMvc.perform(post("/tech/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andReturn();
        //save tech id
        String response = result.getResponse().getContentAsString();
        int testTechId = JsonPath.parse(response).read("$['id']");
        //get tech
        this.mockMvc.perform(get("/tech/"+testTechId).header("Authorization", "Bearer " + adminToken))
                .andExpect(jsonPath("$.id").isNumber())
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
                .andExpect(jsonPath("$.coreField.industry").value("Alternative Energie"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("New", "Hot")))
                .andExpect(jsonPath("$.coreField.type").value("Technologie"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.techRequired.description").isString())
                .andExpect(jsonPath("$.techRequired.description").value(techRequiredValues.get("description")))
                .andExpect(jsonPath("$.techRequired.useCases").isString())
                .andExpect(jsonPath("$.techRequired.useCases").value(techRequiredValues.get("useCases")))
                .andExpect(jsonPath("$.techRequired.discussion").isString())
                .andExpect(jsonPath("$.techRequired.discussion").value(techRequiredValues.get("discussion")))
                .andExpect(jsonPath("$.techRequired.readiness").value("In kommerzieller Nutzung"))
                .andExpect(jsonPath("$.techRequired.projectsIAO").isArray())
                .andExpect(jsonPath("$.techRequired.projectsIAO", hasSize(2)))
                .andExpect(jsonPath("$.techOptional.contacts").isArray())
                .andExpect(jsonPath("$.techOptional.references").isArray())
                .andExpect(jsonPath("$.techOptional.references", hasSize(0)))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                .andExpect(jsonPath("$.techOptional.externalItems").isArray())
                .andExpect(jsonPath("$.techOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.techOptional.industriesWithUse").isArray())
                .andExpect(jsonPath("$.techOptional.industriesWithUse", hasSize(0)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));
        //delete tech
        this.mockMvc.perform(delete("/tech/"+testTechId).header("Authorization", "Bearer " + adminToken));
    }
    @Test
    void getTechWithFaultyRequest() throws Exception{
        this.mockMvc.perform(get("/tech/asdas").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isBadRequest());
    }
    @Test
    void editTech() throws Exception{
        //create tech
        MvcResult result = this.mockMvc.perform(post("/tech/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andReturn();
        //Save tech id
        String response = result.getResponse().getContentAsString();
        int testTechId = JsonPath.parse(response).read("$['id']");
        //update tech
        this.mockMvc.perform(put("/tech/"+testTechId).header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(fullBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
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
                .andExpect(jsonPath("$.coreField.industry").value("Alternative Energie"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("New", "Hot")))
                .andExpect(jsonPath("$.coreField.type").value("Technologie"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.techRequired.description").isString())
                .andExpect(jsonPath("$.techRequired.description").value(techRequiredValues.get("description")))
                .andExpect(jsonPath("$.techRequired.useCases").isString())
                .andExpect(jsonPath("$.techRequired.useCases").value(techRequiredValues.get("useCases")))
                .andExpect(jsonPath("$.techRequired.discussion").isString())
                .andExpect(jsonPath("$.techRequired.discussion").value(techRequiredValues.get("discussion")))
                .andExpect(jsonPath("$.techRequired.readiness").value("In kommerzieller Nutzung"))
                .andExpect(jsonPath("$.techRequired.projectsIAO").isArray())
                .andExpect(jsonPath("$.techRequired.projectsIAO", hasSize(2)))
                .andExpect(jsonPath("$.techOptional.contacts").isArray())
                .andExpect(jsonPath("$.techOptional.contacts[0].name").isString())
                .andExpect(jsonPath("$.techOptional.contacts[0].name").value(techOptionalValues.get("contactsName")))
                .andExpect(jsonPath("$.techOptional.contacts[0].email").isString())
                .andExpect(jsonPath("$.techOptional.contacts[0].email").value(techOptionalValues.get("contactsEmail")))
                .andExpect(jsonPath("$.techOptional.contacts[0].telephone").isString())
                .andExpect(jsonPath("$.techOptional.contacts[0].telephone").value(techOptionalValues.get("contactsTelephone")))
                .andExpect(jsonPath("$.techOptional.contacts[0].organisation").isString())
                .andExpect(jsonPath("$.techOptional.contacts[0].organisation").value(techOptionalValues.get("contactsOrganisation")))
                .andExpect(jsonPath("$.techOptional.references").isArray())
                .andExpect(jsonPath("$.techOptional.references", hasSize(0)))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                .andExpect(jsonPath("$.techOptional.externalItems").isArray())
                .andExpect(jsonPath("$.techOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.techOptional.industriesWithUse").isArray())
                .andExpect(jsonPath("$.techOptional.industriesWithUse", hasSize(3)))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));
        //delete tech
        this.mockMvc.perform(delete("/tech/"+testTechId).header("Authorization", "Bearer " + adminToken));
    }

    @Test
    void getUnusedTech() throws Exception{
        // if for some reason a tech with this ID does exist, change the number
        this.mockMvc.perform(get("/tech/1353453423").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

}
