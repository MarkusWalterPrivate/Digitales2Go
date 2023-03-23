package de.fraunhofer.DigiTales2Go.trend;

import com.jayway.jsonpath.JsonPath;
import de.fraunhofer.DigiTales2Go.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
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
class TrendControllerTest {

    @Autowired
    MockMvc mockMvc;

    private final static String id = "42";
    private final static HashMap<String, String> coreFieldValues = new HashMap<>();
    private final static HashMap<String, String> trendRequiredValues = new HashMap<>();
    private final static HashMap<String, String> trendOptionalValues = new HashMap<>();
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
        coreFieldValues.put("lastUpdated", "1654438851102");
        coreFieldValues.put("creationDate", "1654438851102");
        coreFieldValues.put("rating", "9001.0");
        coreFieldValues.put("intern", "true");
        coreFieldValues.put("source", "source");
        coreFieldValues.put("imageSource", "imageSource");
        coreFieldValues.put("headline", "Demo headline");
        coreFieldValues.put("teaser", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr.");
        coreFieldValues.put("industry", "AEROSPACE");
        coreFieldValues.put("tag1", "Tag1");
        coreFieldValues.put("tag2", "Tag2");
        coreFieldValues.put("type", "TREND");

        trendRequiredValues.put("id", "1111");
        trendRequiredValues.put("description", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        trendRequiredValues.put("discussion", "tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo");

        trendOptionalValues.put("id", "333");

        trendOptionalValues.put("contactsId", "187");
        trendOptionalValues.put("contactsName", "Hans");
        trendOptionalValues.put("contactsEmail", "hans@Wurst.der");
        trendOptionalValues.put("contactsTelephone", "+(049)123 45678789");
        trendOptionalValues.put("contactsOrganisation", "Metzgerei Hans");
        trendOptionalValues.put("internalProjects", null);
        trendOptionalValues.put("externalProjects", null);

        minimalBody =
                "{" +
                "   \"coreField\": {" +
                "       \"intern\": " + coreFieldValues.get("intern") + "," +
                "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                "       \"tags\": [" +
                "           \"" + coreFieldValues.get("tag1") + "\"," +
                "           \"" + coreFieldValues.get("tag2") + "\"" +
                "       ]," +
                "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                "   }," +
                "   \"detailedRating\":{"+
                "       \"degreeOfInnovation\": 1,"+
                "       \"disruptionPotential\": 1,"+
                "       \"useCases\": 1"+
                "   }," +
                "   \"trendRequired\": {" +
                "       \"description\": \"" + trendRequiredValues.get("description") + "\"," +
                "       \"discussion\": \"" + trendRequiredValues.get("discussion") + "\"" +
                "   }" +
                "}";

        String allOptionals =
                "   \"trendOptional\": {" +
                "       \"contacts\": [" +
                "           {" +
                "               \"name\": \"" + trendOptionalValues.get("contactsName") + "\"," +
                "               \"email\": \"" + trendOptionalValues.get("contactsEmail") + "\"," +
                "               \"telephone\": \"" + trendOptionalValues.get("contactsTelephone") + "\"," +
                "               \"organisation\": \"" + trendOptionalValues.get("contactsOrganisation") +"\"" +
                "           }" +
                "       ]," +
                "       \"internalProjects\": []," +
                "       \"externalProjects\": []" +
                "   }";

        fullBody = buildBody(allOptionals);
    }

    
    private String buildBody(String optionalValues){
        return "{" +
                "   \"coreField\": {" +
                "       \"intern\": " + coreFieldValues.get("intern") + "," +
                "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                "       \"tags\": [" +
                "           \"" + coreFieldValues.get("tag1") + "\"," +
                "           \"" + coreFieldValues.get("tag2") + "\"" +
                "       ]," +
                "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                "   }," +
                "   \"detailedRating\":{"+
                "       \"degreeOfInnovation\": 1,"+
                "       \"disruptionPotential\": 1,"+
                "       \"useCases\": 1"+
                "   }," +
                "   \"trendRequired\": {" +
                "       \"description\": \"" + trendRequiredValues.get("description") + "\"," +
                "       \"discussion\": \"" + trendRequiredValues.get("discussion") + "\"" +
                "   }," +
                optionalValues +
                "}";

    }

    @Test
    void getAllTrends() throws Exception {
        this.mockMvc.perform(get("/trend/").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getTrend() throws Exception {
        //create new trend
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //get trend
        this.mockMvc.perform(get("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(testTrendId))
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").value(0))
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Trend"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.trendRequired.id").isNumber())
                .andExpect(jsonPath("$.trendRequired.description").isString())
                .andExpect(jsonPath("$.trendRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                .andExpect(jsonPath("$.trendRequired.discussion").isString())
                .andExpect(jsonPath("$.trendRequired.discussion").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));
        //delete tech
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void getTrendWithFaultyRequest() throws Exception {
        this.mockMvc.perform(get("/trend/asdas").header("Authorization", "Bearer " + adminToken)).andExpect(status().isBadRequest());
    }
    @Test
    void getUnusedTrend() throws Exception {
        // if for some reason a trend with this ID does exist, change the number
        this.mockMvc.perform(get("/trend/1353453423").header("Authorization", "Bearer " + adminToken)).andExpect(status().isNotFound());
    }

    @Test
    void createMinimal() throws Exception {
        //create new trend
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(minimalBody))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").isNumber())
                        .andExpect(jsonPath("$.coreField.id").isNumber())
                        .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                        .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                        .andExpect(jsonPath("$.coreField.rating").value(0))
                        .andExpect(jsonPath("$.coreField.intern").isBoolean())
                        .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                        .andExpect(jsonPath("$.coreField.source").isString())
                        .andExpect(jsonPath("$.coreField.source").value("source"))
                        .andExpect(jsonPath("$.coreField.imageSource").isArray())
                        .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                        .andExpect(jsonPath("$.coreField.headline").isString())
                        .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                        .andExpect(jsonPath("$.coreField.teaser").isString())
                        .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                        .andExpect(jsonPath("$.coreField.industry").isString())
                        .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                        .andExpect(jsonPath("$.coreField.tags").isArray())
                        .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                        .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                        .andExpect(jsonPath("$.coreField.type").isString())
                        .andExpect(jsonPath("$.coreField.type").value("Trend"))
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                        .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                        .andExpect(jsonPath("$.trendRequired.id").isNumber())
                        .andExpect(jsonPath("$.trendRequired.description").isString())
                        .andExpect(jsonPath("$.trendRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                        .andExpect(jsonPath("$.trendRequired.discussion").isString())
                        .andExpect(jsonPath("$.trendRequired.discussion").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                        .andReturn();
        //save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //delete trend
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void createWithContacts() throws Exception {
        String bodyWithContacts = buildBody("   \"trendOptional\": {" +
                                                        "       \"contacts\": [" +
                                                        "           {" +
                                                        "               \"name\": \"" + trendOptionalValues.get("contactsName") + "\"," +
                                                        "               \"email\": \"" + trendOptionalValues.get("contactsEmail") + "\"," +
                                                        "               \"telephone\": \"" + trendOptionalValues.get("contactsTelephone") + "\"," +
                                                        "               \"organisation\": \"" + trendOptionalValues.get("contactsOrganisation") +"\"" +
                                                        "           }" +
                                                        "       ]" +
                                                        "   }");
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bodyWithContacts))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").isNumber())
                        .andExpect(jsonPath("$.coreField.id").isNumber())
                        .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                        .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                        .andExpect(jsonPath("$.coreField.rating").value(0))
                        .andExpect(jsonPath("$.coreField.intern").isBoolean())
                        .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                        .andExpect(jsonPath("$.coreField.source").isString())
                        .andExpect(jsonPath("$.coreField.source").value("source"))
                        .andExpect(jsonPath("$.coreField.imageSource").isArray())
                        .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                        .andExpect(jsonPath("$.coreField.headline").isString())
                        .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                        .andExpect(jsonPath("$.coreField.teaser").isString())
                        .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                        .andExpect(jsonPath("$.coreField.industry").isString())
                        .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                        .andExpect(jsonPath("$.coreField.tags").isArray())
                        .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                        .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                        .andExpect(jsonPath("$.coreField.type").isString())
                        .andExpect(jsonPath("$.coreField.type").value("Trend"))
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                        .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                        .andExpect(jsonPath("$.trendRequired.id").isNumber())
                        .andExpect(jsonPath("$.trendRequired.description").isString())
                        .andExpect(jsonPath("$.trendRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                        .andExpect(jsonPath("$.trendRequired.discussion").isString())
                        .andExpect(jsonPath("$.trendRequired.discussion").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                        .andExpect(jsonPath("$.trendOptional.id").isNumber())
                        //.andExpect(jsonPath("$.trendOptional.contacts[0].id").isNumber())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].name").isString())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].name").value("Hans"))
                        .andExpect(jsonPath("$.trendOptional.contacts[0].email").isString())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].email").value("hans@Wurst.der"))
                        .andExpect(jsonPath("$.trendOptional.contacts[0].telephone").isString())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].telephone").value("+(049)123 45678789"))
                        .andExpect(jsonPath("$.trendOptional.contacts[0].organisation").isString())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].organisation").value("Metzgerei Hans"))
                        .andReturn();
        //save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //delete trend
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void createFull() throws Exception {
        //create new trend
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullBody))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").isNumber())
                        .andExpect(jsonPath("$.coreField.id").isNumber())
                        .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                        .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                        .andExpect(jsonPath("$.coreField.rating").value(0))
                        .andExpect(jsonPath("$.coreField.intern").isBoolean())
                        .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                        .andExpect(jsonPath("$.coreField.source").isString())
                        .andExpect(jsonPath("$.coreField.source").value("source"))
                        .andExpect(jsonPath("$.coreField.imageSource").isArray())
                        .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                        .andExpect(jsonPath("$.coreField.headline").isString())
                        .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                        .andExpect(jsonPath("$.coreField.teaser").isString())
                        .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                        .andExpect(jsonPath("$.coreField.industry").isString())
                        .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                        .andExpect(jsonPath("$.coreField.tags").isArray())
                        .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                        .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                        .andExpect(jsonPath("$.coreField.type").isString())
                        .andExpect(jsonPath("$.coreField.type").value("Trend"))
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                        .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                        .andExpect(jsonPath("$.trendRequired.id").isNumber())
                        .andExpect(jsonPath("$.trendRequired.description").isString())
                        .andExpect(jsonPath("$.trendRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                        .andExpect(jsonPath("$.trendRequired.discussion").isString())
                        .andExpect(jsonPath("$.trendRequired.discussion").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                        .andExpect(jsonPath("$.trendOptional.id").isNumber())
                        //.andExpect(jsonPath("$.trendOptional.contacts[0].id").isNumber())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].name").isString())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].name").value("Hans"))
                        .andExpect(jsonPath("$.trendOptional.contacts[0].email").isString())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].email").value("hans@Wurst.der"))
                        .andExpect(jsonPath("$.trendOptional.contacts[0].telephone").isString())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].telephone").value("+(049)123 45678789"))
                        .andExpect(jsonPath("$.trendOptional.contacts[0].organisation").isString())
                        .andExpect(jsonPath("$.trendOptional.contacts[0].organisation").value("Metzgerei Hans"))
                        .andExpect(jsonPath("$.internalProjects").isArray())
                        .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                        .andExpect(jsonPath("$.internalCompanies").isArray())
                        .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                        .andExpect(jsonPath("$.internalTrends").isArray())
                        .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                        .andExpect(jsonPath("$.internalTechnologies").isArray())
                        .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                        .andExpect(jsonPath("$.trendOptional.externalItems").isArray())
                        .andExpect(jsonPath("$.trendOptional.externalItems", hasSize(0)))
                        .andReturn();
        //save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //delete tech
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void editMinimal() throws Exception {
        //create trend
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //Save trend ids
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int trendRequiredId = JsonPath.parse(response).read("$.trendRequired.id");
        String changedBody =
                "{" +
                        "   \"id\": " + testTrendId + "," +
                        "   \"coreField\": {" +
                        "       \"id\": " + coreFieldId + "," +
                        "       \"lastUpdated\": " + coreFieldValues.get("lastUpdated") + "," +
                        "       \"creationDate\": " + coreFieldValues.get("creationDate") + "," +
                        "       \"rating\": " + coreFieldValues.get("rating") + "," +
                        "       \"intern\": " + coreFieldValues.get("intern") + "," +
                        "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                        "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                        "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                        "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                        "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                        "       \"tags\": [" +
                        "           \"" + coreFieldValues.get("tag1") + "\"," +
                        "           \"" + coreFieldValues.get("tag2") + "\"" +
                        "       ]," +
                        "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                        "   }," +
                        "   \"detailedRating\":{"+
                        "       \"degreeOfInnovation\": 1,"+
                        "       \"disruptionPotential\": 1,"+
                        "       \"useCases\": 1"+
                        "   }," +
                        "   \"trendRequired\": {" +
                        "       \"id\": " + trendRequiredId + "," +
                        "       \"description\": \"" + trendRequiredValues.get("description") + "\"," +
                        "       \"discussion\": \"" + trendRequiredValues.get("discussion") + "\"" +
                        "   }" +
                        "}";

        //update trend
        this.mockMvc.perform(put("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(changedBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(testTrendId))
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").value(0))
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Trend"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.trendRequired.id").isNumber())
                .andExpect(jsonPath("$.trendRequired.description").isString())
                .andExpect(jsonPath("$.trendRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                .andExpect(jsonPath("$.trendRequired.discussion").isString())
                .andExpect(jsonPath("$.trendRequired.discussion").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));

        //delete trend
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void editWithOnlyTrendOptionalId() throws Exception {
        //create trend
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //Save trend ids
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int trendRequiredId = JsonPath.parse(response).read("$.trendRequired.id");
        int trendOptionalId = JsonPath.parse(response).read("$.trendOptional.id");
        String changedBody =
                "{" +
                        "   \"id\": " + testTrendId + "," +
                        "   \"coreField\": {" +
                        "       \"id\": " + coreFieldId + "," +
                        "       \"lastUpdated\": " + coreFieldValues.get("lastUpdated") + "," +
                        "       \"creationDate\": " + coreFieldValues.get("creationDate") + "," +
                        "       \"rating\": " + coreFieldValues.get("rating") + "," +
                        "       \"intern\": " + coreFieldValues.get("intern") + "," +
                        "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                        "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                        "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                        "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                        "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                        "       \"tags\": [" +
                        "           \"" + coreFieldValues.get("tag1") + "\"," +
                        "           \"" + coreFieldValues.get("tag2") + "\"" +
                        "       ]," +
                        "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                        "   }," +
                        "   \"detailedRating\":{"+
                        "       \"degreeOfInnovation\": 1,"+
                        "       \"disruptionPotential\": 1,"+
                        "       \"useCases\": 1"+
                        "   }," +
                        "   \"trendRequired\": {" +
                        "       \"id\": " + trendRequiredId + "," +
                        "       \"description\": \"" + trendRequiredValues.get("description") + "\"," +
                        "       \"discussion\": \"" + trendRequiredValues.get("discussion") + "\"" +
                        "   }," +
                        "   \"trendOptional\": {" +
                        "       \"id\": " + trendOptionalId +
                        "   }" +
                        "}";

        //update trend
        this.mockMvc.perform(put("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(changedBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(testTrendId))
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").value(0))
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Trend"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.trendRequired.id").isNumber())
                .andExpect(jsonPath("$.trendRequired.description").isString())
                .andExpect(jsonPath("$.trendRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                .andExpect(jsonPath("$.trendRequired.discussion").isString())
                .andExpect(jsonPath("$.trendRequired.discussion").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));

        //delete trend
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void editFullTrend() throws Exception {
        //create trend
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //Save trend ids
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int trendRequiredId = JsonPath.parse(response).read("$.trendRequired.id");
        int trendOptionalId = JsonPath.parse(response).read("$.trendOptional.id");
        String changedBody =
                "{" +
                        "   \"id\": " + testTrendId + "," +
                        "   \"coreField\": {" +
                        "       \"id\": " + coreFieldId + "," +
                        "       \"lastUpdated\": " + coreFieldValues.get("lastUpdated") + "," +
                        "       \"creationDate\": " + coreFieldValues.get("creationDate") + "," +
                        "       \"rating\": " + coreFieldValues.get("rating") + "," +
                        "       \"intern\": " + coreFieldValues.get("intern") + "," +
                        "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                        "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                        "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                        "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                        "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                        "       \"tags\": [" +
                        "           \"" + coreFieldValues.get("tag1") + "\"," +
                        "           \"" + coreFieldValues.get("tag2") + "\"" +
                        "       ]," +
                        "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                        "   }," +
                        "   \"detailedRating\":{"+
                        "       \"degreeOfInnovation\": 1,"+
                        "       \"disruptionPotential\": 1,"+
                        "       \"useCases\": 1"+
                        "   }," +
                        "   \"trendRequired\": {" +
                        "       \"id\": " + trendRequiredId + "," +
                        "       \"description\": \"" + trendRequiredValues.get("description") + "\"," +
                        "       \"discussion\": \"" + trendRequiredValues.get("discussion") + "\"" +
                        "   }," +
                        "   \"trendOptional\": {" +
                        "       \"id\": " + trendOptionalId + "," +
                        "       \"contacts\": [" +
                        "           {" +
                        "               \"name\": \"" + trendOptionalValues.get("contactsName") + "\"," +
                        "               \"email\": \"" + trendOptionalValues.get("contactsEmail") + "\"," +
                        "               \"telephone\": \"" + trendOptionalValues.get("contactsTelephone") + "\"," +
                        "               \"organisation\": \"" + trendOptionalValues.get("contactsOrganisation") +"\"" +
                        "           }" +
                        "       ]," +
                        "       \"internalProjects\": []," +
                        "       \"externalProjects\": []" +
                        "   }" +
                        "}";

        //update trend
        this.mockMvc.perform(put("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(changedBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(testTrendId))
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.rating").value(0))
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.imageSource").isArray())
                .andExpect(jsonPath("$.coreField.imageSource[*]", containsInAnyOrder("imageSource")))
                .andExpect(jsonPath("$.coreField.headline").isString())
                .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Trend"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.trendRequired.id").isNumber())
                .andExpect(jsonPath("$.trendRequired.description").isString())
                .andExpect(jsonPath("$.trendRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                .andExpect(jsonPath("$.trendRequired.discussion").isString())
                .andExpect(jsonPath("$.trendRequired.discussion").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments", hasSize(0)));
        //delete trend
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void editNonExistingTrend() throws Exception {
        //create trend
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //Save trend ids
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int trendRequiredId = JsonPath.parse(response).read("$.trendRequired.id");
        int trendOptionalId = JsonPath.parse(response).read("$.trendOptional.id");
        String changedBody =
                "{" +
                        "   \"id\": " + testTrendId + "," +
                        "   \"coreField\": {" +
                        "       \"id\": " + coreFieldId + "," +
                        "       \"lastUpdated\": " + coreFieldValues.get("lastUpdated") + "," +
                        "       \"creationDate\": " + coreFieldValues.get("creationDate") + "," +
                        "       \"rating\": " + coreFieldValues.get("rating") + "," +
                        "       \"intern\": " + coreFieldValues.get("intern") + "," +
                        "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                        "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                        "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                        "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                        "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                        "       \"tags\": [" +
                        "           \"" + coreFieldValues.get("tag1") + "\"," +
                        "           \"" + coreFieldValues.get("tag2") + "\"" +
                        "       ]," +
                        "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                        "   }," +
                        "   \"detailedRating\":{"+
                        "       \"degreeOfInnovation\": 1,"+
                        "       \"disruptionPotential\": 1,"+
                        "       \"useCases\": 1"+
                        "   }," +
                        "   \"trendRequired\": {" +
                        "       \"id\": " + trendRequiredId + "," +
                        "       \"description\": \"" + trendRequiredValues.get("description") + "\"," +
                        "       \"discussion\": \"" + trendRequiredValues.get("discussion") + "\"" +
                        "   }," +
                        "   \"trendOptional\": {" +
                        "       \"id\": " + trendOptionalId + "," +
                        "       \"contacts\": [" +
                        "           {" +
                        "               \"name\": \"" + trendOptionalValues.get("contactsName") + "\"," +
                        "               \"email\": \"" + trendOptionalValues.get("contactsEmail") + "\"," +
                        "               \"telephone\": \"" + trendOptionalValues.get("contactsTelephone") + "\"," +
                        "               \"organisation\": \"" + trendOptionalValues.get("contactsOrganisation") +"\"" +
                        "           }" +
                        "       ]," +
                        "       \"internalProjects\": []," +
                        "       \"externalProjects\": []" +
                        "   }" +
                        "}";

        this.mockMvc.perform(put("/trend/1234567890").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(changedBody))
                .andExpect(status().isNotFound());

        //delete trend
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void deleteTrend() throws Exception {
        //Create trend
        MvcResult result =
                this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //Save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //Delete trend
        this.mockMvc.perform(delete("/trend/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void deleteNonExistingTrend() throws Exception {
        this.mockMvc.perform(delete("/trend/1234567890").header("Authorization", "Bearer " + adminToken)).andExpect(status().isNotFound());
    }
}