package de.fraunhofer.DigiTales2Go.company;

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
class CompanyApiTest {

    @Autowired
    private MockMvc mockMvc;

    private final static String id = "42";
    private final static HashMap<String, String> coreFieldValues = new HashMap<>();
    private final static HashMap<String, String> companyRequiredValues = new HashMap<>();
    private final static HashMap<String, String> teamSizeValues = new HashMap<>();

    private final static HashMap<String, String> companyProjectsValues = new HashMap<>();
    private final static HashMap<String, String> companyOptionalListsValues = new HashMap<>();
    private final static HashMap<String, String> companyOptionalFieldsValues = new HashMap<>();
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
        coreFieldValues.put("intern", "true");
        coreFieldValues.put("source", "source");
        coreFieldValues.put("imageSource", "imageSource");
        coreFieldValues.put("headline", "Demo headline");
        coreFieldValues.put("teaser", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr.");
        coreFieldValues.put("industry", "AEROSPACE");
        coreFieldValues.put("tag1", "Tag1");
        coreFieldValues.put("tag2", "Tag2");
        coreFieldValues.put("type", "COMPANY");

        companyRequiredValues.put("description", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore");
        companyRequiredValues.put("useCases", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        companyRequiredValues.put("website", "tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo");
        teamSizeValues.put("teamSize", "TINY");
        teamSizeValues.put("reference", "tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo");

        companyProjectsValues.put("projects", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr");
        companyProjectsValues.put("internalProjects", null);
        companyProjectsValues.put("externalProjects", null);

        companyOptionalFieldsValues.put("locationCountry", "Germany");
        companyOptionalFieldsValues.put("locationCity", "Stuttgart");

        companyOptionalFieldsValues.put("productReadiness", "THEORY");

        companyOptionalFieldsValues.put("numberOfCustomersReference", "invidunt utlabore et dolore magna aliquyam erat, sed diam");

        companyOptionalFieldsValues.put("revenueReference", "invidunt utlabore et dolore magna aliquyam erat, sed diam");

        companyOptionalFieldsValues.put("profitReference", "invidunt utlabore et dolore magna aliquyam erat, sed diam");

        companyOptionalListsValues.put("contactsName", "Hans");
        companyOptionalListsValues.put("contactsEmail", "hans@Wurst.der");
        companyOptionalListsValues.put("contactsTelephone", "+(049)123 45678789");
        companyOptionalListsValues.put("contactsOrganisation", "Metzgerei Hans");

        companyOptionalListsValues.put("targetMarkets1", "\"GERMANY\"");
        companyOptionalListsValues.put("targetMarkets2", "\"USA\"");

        companyOptionalListsValues.put("alignment1", "\"B2B\"");
        companyOptionalListsValues.put("alignment2", "\"B2G\"");

        companyOptionalListsValues.put("partner1", "\"Metzgerei Hans\"");
        companyOptionalListsValues.put("partner2", "\"Metzgerei Horst\"");

        companyOptionalListsValues.put("investor1", "\"Metzgerei Hans\"");
        companyOptionalListsValues.put("investor2", "\"Metzgerei Horst\"");


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
                        "   \"companyRequired\": {" +
                        "       \"description\": \"" + companyRequiredValues.get("description") + "\"," +
                        "       \"useCases\": \"" + companyRequiredValues.get("useCases") + "\"," +
                        "       \"teamSize\": {" +
                        "           \"teamSize\": \"" + teamSizeValues.get("teamSize") + "\"," +
                        "           \"year\": 2020," +
                        "           \"reference\": \"" + teamSizeValues.get("reference") + "\"" +
                        "       }," +
                        "       \"website\": \"" + companyRequiredValues.get("website") + "\"," +
                        "       \"foundationYear\": 2000" +
                        "   }" +
                        "}";

        String allOptionals =
                "   \"companyOptional\": {" +
                "        \"companyProjects\": {" +
                "           \"projects\": \"" + companyProjectsValues.get("projects") + "\"," +
                "           \"internalProjects\": " + companyProjectsValues.get("internalProjects") + "," +
                "           \"externalProjects\": " + companyProjectsValues.get("externalProjects") + "" +
                "        }," +
                "        \"companyOptionalLists\": {" +
                "            \"contacts\": [" +
                "                {" +
                "                   \"name\": \"" + companyOptionalListsValues.get("contactsName") + "\"," +
                "                   \"email\": \"" + companyOptionalListsValues.get("contactsEmail") + "\"," +
                "                   \"telephone\": \"" + companyOptionalListsValues.get("contactsTelephone") + "\"," +
                "                   \"organisation\": \"" + companyOptionalListsValues.get("contactsOrganisation") +"\"" +
                "               }" +
                "           ]," +
                "            \"targetMarkets\": [" +
                "                "+ companyOptionalListsValues.get("targetMarkets1")+","
                                  +companyOptionalListsValues.get("targetMarkets2")+
                "           ]," +
                "            \"alignments\": [" +
                "                "+ companyOptionalListsValues.get("alignment1")+","
                                  +companyOptionalListsValues.get("alignment2")+
                "           ]," +
                "            \"partners\": [" +
                "                "+ companyOptionalListsValues.get("partner1")+","
                                  +companyOptionalListsValues.get("partner2")+
                "           ]," +
                "            \"investors\": [" +
                "                "+ companyOptionalListsValues.get("investor1")+","
                                  +companyOptionalListsValues.get("investor2")+
                "           ]" +
                "        }," +
                "        \"companyOptionalFields\": {" +
                "            \"location\": {"+
                "                   \"country\": \"" + companyOptionalFieldsValues.get("locationCountry") + "\"," +
                "                   \"city\": \"" + companyOptionalFieldsValues.get("locationCity") + "\"" +
                "               }," +
                "            \"productReadiness\": \"" + companyOptionalFieldsValues.get("productReadiness") + "\"," +
                "            \"numberOfCustomers\": {" +
                "                 \"value\": \"42\"," +
                "                 \"year\": 2020," +
                "                 \"reference\": \"" + companyOptionalFieldsValues.get("numberOfCustomersReference") + "\"" +
                "               }," +
                "            \"revenue\": {" +
                "                 \"value\": \"42\"," +
                "                 \"year\": 2020," +
                "                 \"reference\": \"" + companyOptionalFieldsValues.get("revenueReference") + "\"" +
                "               }," +
                "            \"profit\": {" +
                "                 \"value\": \"42\"," +
                "                 \"year\": 2020," +
                "                 \"reference\": \"" + companyOptionalFieldsValues.get("profitReference") + "\"" +
                "               }" +
                "        }" +

                "   }";

        fullBody = buildBody(allOptionals);
    }

    private static String buildBody(String optionalValues){
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
                "   \"companyRequired\": {" +
                "       \"description\": \"" + companyRequiredValues.get("description") + "\"," +
                "       \"useCases\": \"" + companyRequiredValues.get("useCases") + "\"," +
                "       \"teamSize\": {" +
                "           \"teamSize\": \"" + teamSizeValues.get("teamSize") + "\"," +
                "           \"year\": 2020," +
                "           \"reference\": \"" + teamSizeValues.get("reference") + "\"" +
                "       }," +
                "       \"website\": \"" + companyRequiredValues.get("website") + "\"," +
                "       \"foundationYear\": 2000" +
                "   }," +
                optionalValues+"}";

    }

    @Test
    void getAllCompanies() throws Exception {
        this.mockMvc.perform(get("/company/").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getTrend() throws Exception {
        //create new trend
        MvcResult result =
                this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //get trend
        this.mockMvc.perform(get("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.coreField.id").isNumber())
                .andExpect(jsonPath("$.coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("$.coreField.creationDate").isNumber())
                .andExpect(jsonPath("$.coreField.intern").isBoolean())
                .andExpect(jsonPath("$.coreField.intern").value(Boolean.valueOf(coreFieldValues.get("intern"))))
                .andExpect(jsonPath("$.coreField.source").isString())
                .andExpect(jsonPath("$.coreField.source").value("source"))
                .andExpect(jsonPath("$.coreField.rating").isNumber())
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
                .andExpect(jsonPath("$.coreField.type").value("Firma"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.companyRequired.id").isNumber())
                .andExpect(jsonPath("$.companyRequired.description").isString())
                .andExpect(jsonPath("$.companyRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore"))
                .andExpect(jsonPath("$.companyRequired.useCases").isString())
                .andExpect(jsonPath("$.companyRequired.useCases").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                .andExpect(jsonPath("$.companyRequired.teamSize.id").isNumber())
                .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").isString())
                .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").value("Kleinstunternehmen"))
                .andExpect(jsonPath("$.companyRequired.teamSize.year").isNumber())
                .andExpect(jsonPath("$.companyRequired.teamSize.year").value(2020))
                .andExpect(jsonPath("$.companyRequired.teamSize.reference").isString())
                .andExpect(jsonPath("$.companyRequired.teamSize.reference").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.companyRequired.website").isString())
                .andExpect(jsonPath("$.companyRequired.website").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.companyRequired.foundationYear").isNumber())
                .andExpect(jsonPath("$.companyRequired.foundationYear").value(2000))
                .andExpect(jsonPath("$.companyOptional.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.externalItems").isArray())
                .andExpect(jsonPath("$.companyOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").value(""))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)));
        //delete tech
        this.mockMvc.perform(delete("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void getTrendWithFaultyRequest() throws Exception {
        this.mockMvc.perform(get("/company/asdas").header("Authorization", "Bearer " + adminToken)).andExpect(status().isBadRequest());
    }
    @Test
    void getUnusedTrend() throws Exception {
        // if for some reason a trend with this ID does exist, change the number
        this.mockMvc.perform(get("/company/1353453423").header("Authorization", "Bearer " + adminToken)).andExpect(status().isNotFound());
    }

    @Test
    void createMinimal() throws Exception {
        //create new trend
        MvcResult result =
                this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(minimalBody))
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
                        .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                        .andExpect(jsonPath("$.coreField.teaser").isString())
                        .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                        .andExpect(jsonPath("$.coreField.industry").isString())
                        .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                        .andExpect(jsonPath("$.coreField.tags").isArray())
                        .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                        .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                        .andExpect(jsonPath("$.coreField.type").isString())
                        .andExpect(jsonPath("$.coreField.type").value("Firma"))
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                        .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                        .andExpect(jsonPath("$.companyRequired.id").isNumber())
                        .andExpect(jsonPath("$.companyRequired.description").isString())
                        .andExpect(jsonPath("$.companyRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore"))
                        .andExpect(jsonPath("$.companyRequired.useCases").isString())
                        .andExpect(jsonPath("$.companyRequired.useCases").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                        .andExpect(jsonPath("$.companyRequired.teamSize.id").isNumber())
                        .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").isString())
                        .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").value("Kleinstunternehmen"))
                        .andExpect(jsonPath("$.companyRequired.teamSize.year").isNumber())
                        .andExpect(jsonPath("$.companyRequired.teamSize.year").value(2020))
                        .andExpect(jsonPath("$.companyRequired.teamSize.reference").isString())
                        .andExpect(jsonPath("$.companyRequired.teamSize.reference").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                        .andExpect(jsonPath("$.companyRequired.website").isString())
                        .andExpect(jsonPath("$.companyRequired.website").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                        .andExpect(jsonPath("$.companyRequired.foundationYear").isNumber())
                        .andExpect(jsonPath("$.companyRequired.foundationYear").value(2000))
                        .andExpect(jsonPath("$.companyOptional.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.externalItems").isArray())
                        .andExpect(jsonPath("$.companyOptional.externalItems", hasSize(0)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts", hasSize(0)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets", hasSize(0)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments", hasSize(0)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners", hasSize(0)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors", hasSize(0)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").value(""))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").value(""))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").value(""))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").value(-1))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").value(""))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").value(""))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").value(-1))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").value(""))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").value(""))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").value(-1))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").value(""))
                        .andExpect(jsonPath("$.internalProjects").isArray())
                        .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                        .andExpect(jsonPath("$.internalCompanies").isArray())
                        .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                        .andExpect(jsonPath("$.internalTrends").isArray())
                        .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                        .andExpect(jsonPath("$.internalTechnologies").isArray())
                        .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                        .andReturn();
        //save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //delete trend
        this.mockMvc.perform(delete("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void createFull() throws Exception {
        //create new trend
        MvcResult result =
                this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(fullBody))
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
                        .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                        .andExpect(jsonPath("$.coreField.teaser").isString())
                        .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                        .andExpect(jsonPath("$.coreField.industry").isString())
                        .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                        .andExpect(jsonPath("$.coreField.tags").isArray())
                        .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                        .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                        .andExpect(jsonPath("$.coreField.type").isString())
                        .andExpect(jsonPath("$.coreField.type").value("Firma"))
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                        .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                        .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                        .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                        .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                        .andExpect(jsonPath("$.companyRequired.id").isNumber())
                        .andExpect(jsonPath("$.companyRequired.description").isString())
                        .andExpect(jsonPath("$.companyRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore"))
                        .andExpect(jsonPath("$.companyRequired.useCases").isString())
                        .andExpect(jsonPath("$.companyRequired.useCases").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                        .andExpect(jsonPath("$.companyRequired.teamSize.id").isNumber())
                        .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").isString())
                        .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").value("Kleinstunternehmen"))
                        .andExpect(jsonPath("$.companyRequired.teamSize.year").isNumber())
                        .andExpect(jsonPath("$.companyRequired.teamSize.year").value(2020))
                        .andExpect(jsonPath("$.companyRequired.teamSize.reference").isString())
                        .andExpect(jsonPath("$.companyRequired.teamSize.reference").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                        .andExpect(jsonPath("$.companyRequired.website").isString())
                        .andExpect(jsonPath("$.companyRequired.website").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                        .andExpect(jsonPath("$.companyRequired.foundationYear").isNumber())
                        .andExpect(jsonPath("$.companyRequired.foundationYear").value(2000))
                        .andExpect(jsonPath("$.companyOptional.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.externalItems").isArray())
                        .andExpect(jsonPath("$.companyOptional.externalItems", hasSize(0)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts", hasSize(1)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].name").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].name").value("Hans"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].email").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].email").value("hans@Wurst.der"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].telephone").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].telephone").value("+(049)123 45678789"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].organisation").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].organisation").value("Metzgerei Hans"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets", hasSize(2)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets", containsInAnyOrder("Deutschland", "USA")))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments", hasSize(2)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments", containsInAnyOrder("Business to Business", "Business to Government")))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners", hasSize(2)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners", containsInAnyOrder("Metzgerei Hans", "Metzgerei Horst")))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors").isArray())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors", hasSize(2)))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors", containsInAnyOrder("Metzgerei Hans", "Metzgerei Horst")))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").value("Germany"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").value("Stuttgart"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").value("42"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").value(2020))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").value("invidunt utlabore et dolore magna aliquyam erat, sed diam"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").value("42"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").value(2020))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").value("invidunt utlabore et dolore magna aliquyam erat, sed diam"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.id").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").value("42"))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").isNumber())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").value(2020))
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").isString())
                        .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").value("invidunt utlabore et dolore magna aliquyam erat, sed diam"))
                        .andExpect(jsonPath("$.internalProjects").isArray())
                        .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                        .andExpect(jsonPath("$.internalCompanies").isArray())
                        .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                        .andExpect(jsonPath("$.internalTrends").isArray())
                        .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                        .andExpect(jsonPath("$.internalTechnologies").isArray())
                        .andExpect(jsonPath("$.internalTechnologies", hasSize(0)))
                        .andReturn();
        //save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //delete tech
        this.mockMvc.perform(delete("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void editMinimal() throws Exception {
        //create trend
        MvcResult result =
                this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //Save trend ids
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int trendRequiredId = JsonPath.parse(response).read("$.companyRequired.id");
        int teamSizeId =  JsonPath.parse(response).read("$.companyRequired.teamSize.id");
        String changedBody =
                "{" +
                        " \"id\": "+testTrendId+","+
                        "   \"coreField\": {" +
                        "       \"id\": "+coreFieldId+","+
                        "       \"intern\": " + coreFieldValues.get("intern") + "," +
                        "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                        "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                        "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                        "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                        "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                        "       \"tags\": [" +
                        "           \"abcd\"," +
                        "           \"efgh\"" +
                        "       ]," +
                        "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                        "   }," +
                        "   \"detailedRating\":{"+
                        "       \"degreeOfInnovation\": 1,"+
                        "       \"disruptionPotential\": 1,"+
                        "       \"useCases\": 1"+
                        "   }," +
                        "   \"companyRequired\": {" +
                        "       \"id\": "+trendRequiredId+","+
                        "       \"description\": \"" + companyRequiredValues.get("description") + "\"," +
                        "       \"useCases\": \"" + companyRequiredValues.get("useCases") + "\"," +
                        "       \"teamSize\": {" +
                        "           \"id\": "+teamSizeId+","+
                        "           \"teamSize\": \"" + teamSizeValues.get("teamSize") + "\"," +
                        "           \"year\": 2020," +
                        "           \"reference\": \"" + teamSizeValues.get("reference") + "\"" +
                        "       }," +
                        "       \"website\": \"" + companyRequiredValues.get("website") + "\"," +
                        "       \"foundationYear\": 2050" +
                        "   }" +
                        "}";

        //update trend
        this.mockMvc.perform(put("/company/" + testTrendId).contentType(MediaType.APPLICATION_JSON).content(changedBody).header("Authorization", "Bearer " + adminToken))
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
                .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("abcd", "efgh")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Firma"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.companyRequired.id").isNumber())
                .andExpect(jsonPath("$.companyRequired.description").isString())
                .andExpect(jsonPath("$.companyRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore"))
                .andExpect(jsonPath("$.companyRequired.useCases").isString())
                .andExpect(jsonPath("$.companyRequired.useCases").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                .andExpect(jsonPath("$.companyRequired.teamSize.id").isNumber())
                .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").isString())
                .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").value("Kleinstunternehmen"))
                .andExpect(jsonPath("$.companyRequired.teamSize.year").isNumber())
                .andExpect(jsonPath("$.companyRequired.teamSize.year").value(2020))
                .andExpect(jsonPath("$.companyRequired.teamSize.reference").isString())
                .andExpect(jsonPath("$.companyRequired.teamSize.reference").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.companyRequired.website").isString())
                .andExpect(jsonPath("$.companyRequired.website").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.companyRequired.foundationYear").isNumber())
                .andExpect(jsonPath("$.companyRequired.foundationYear").value(2050))
                .andExpect(jsonPath("$.companyOptional.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.externalItems").isArray())
                .andExpect(jsonPath("$.companyOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").value(""))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)));

        this.mockMvc.perform(delete("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void editWithOnlyCompanyOptionalId() throws Exception {
        //create trend
        MvcResult result =
                this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(fullBody)).andReturn();
        //Save trend ids
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int trendRequiredId = JsonPath.parse(response).read("companyRequired.id");
        int trendOptionalId = JsonPath.parse(response).read("$.companyOptional.id");
        int teamSizeId =  JsonPath.parse(response).read("$.companyRequired.teamSize.id");
        String changedBody =
                "{" +
                        " \"id\": "+testTrendId+","+
                        "   \"coreField\": {" +
                        "       \"id\": "+coreFieldId+","+
                        "       \"intern\": " + coreFieldValues.get("intern") + "," +
                        "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                        "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                        "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                        "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                        "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                        "       \"tags\": [" +
                        "           \"abcd\"," +
                        "           \"efgh\"" +
                        "       ]," +
                        "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                        "   }," +
                        "   \"detailedRating\":{"+
                        "       \"degreeOfInnovation\": 1,"+
                        "       \"disruptionPotential\": 1,"+
                        "       \"useCases\": 1"+
                        "   }," +
                        "   \"companyRequired\": {" +
                        "       \"id\": "+trendRequiredId+","+
                        "       \"description\": \"" + companyRequiredValues.get("description") + "\"," +
                        "       \"useCases\": \"" + companyRequiredValues.get("useCases") + "\"," +
                        "       \"teamSize\": {" +
                        "           \"id\": "+teamSizeId+","+
                        "           \"teamSize\": \"" + teamSizeValues.get("teamSize") + "\"," +
                        "           \"year\": 2020," +
                        "           \"reference\": \"" + teamSizeValues.get("reference") + "\"" +
                        "       }," +
                        "       \"website\": \"" + companyRequiredValues.get("website") + "\"," +
                        "       \"foundationYear\": 2050" +
                        "   }," +
                        " \"companyOptional\": {"+
                        "   \"id\": "+trendOptionalId+
                        "   }"   +
                        "}";

        //update trend
        this.mockMvc.perform(put("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(changedBody))
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
                .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("abcd", "efgh")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Firma"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.companyRequired.id").isNumber())
                .andExpect(jsonPath("$.companyRequired.description").isString())
                .andExpect(jsonPath("$.companyRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore"))
                .andExpect(jsonPath("$.companyRequired.useCases").isString())
                .andExpect(jsonPath("$.companyRequired.useCases").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                .andExpect(jsonPath("$.companyRequired.teamSize.id").isNumber())
                .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").isString())
                .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").value("Kleinstunternehmen"))
                .andExpect(jsonPath("$.companyRequired.teamSize.year").isNumber())
                .andExpect(jsonPath("$.companyRequired.teamSize.year").value(2020))
                .andExpect(jsonPath("$.companyRequired.teamSize.reference").isString())
                .andExpect(jsonPath("$.companyRequired.teamSize.reference").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.companyRequired.website").isString())
                .andExpect(jsonPath("$.companyRequired.website").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.companyRequired.foundationYear").isNumber())
                .andExpect(jsonPath("$.companyRequired.foundationYear").value(2050))
                .andExpect(jsonPath("$.companyOptional.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.externalItems").isArray())
                .andExpect(jsonPath("$.companyOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").value(""))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").value(-1))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").value(""))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)));

        this.mockMvc.perform(delete("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void editFullCompany() throws Exception {
        //create trend
        MvcResult result =
                this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(fullBody)).andReturn();
        //Save trend ids
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int trendRequiredId = JsonPath.parse(response).read("$.companyRequired.id");
        int trendOptionalId = JsonPath.parse(response).read("$.companyOptional.id");
        int listsId =  JsonPath.parse(response).read("$.companyOptional.companyOptionalLists.id");
        int fieldId =  JsonPath.parse(response).read("$.companyOptional.companyOptionalFields.id");
        int teamSizeId =  JsonPath.parse(response).read("$.companyRequired.teamSize.id");
        int locationId =  JsonPath.parse(response).read("$.companyOptional.companyOptionalFields.location.id");
        int customersId =  JsonPath.parse(response).read("$.companyOptional.companyOptionalFields.numberOfCustomers.id");
        int revenueId =  JsonPath.parse(response).read("$.companyOptional.companyOptionalFields.revenue.id");
        int profitId =  JsonPath.parse(response).read("$.companyOptional.companyOptionalFields.profit.id");
        int contactId =  JsonPath.parse(response).read("$.companyOptional.companyOptionalLists.contacts[0].id");
        String changedBody =
                "{" +
                        " \"id\": "+testTrendId+","+
                        "   \"coreField\": {" +
                        "       \"id\": "+coreFieldId+","+
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
                        "   \"companyRequired\": {" +
                        "       \"id\": "+trendRequiredId+","+
                        "       \"description\": \"" + companyRequiredValues.get("description") + "\"," +
                        "       \"useCases\": \"" + companyRequiredValues.get("useCases") + "\"," +
                        "       \"teamSize\": {" +
                        "           \"id\": "+teamSizeId+","+
                        "           \"teamSize\": \"" + teamSizeValues.get("teamSize") + "\"," +
                        "           \"year\": 2020," +
                        "           \"reference\": \"" + teamSizeValues.get("reference") + "\"" +
                        "       }," +
                        "       \"website\": \"" + companyRequiredValues.get("website") + "\"," +
                        "       \"foundationYear\": 2000" +
                        "   }," +
                        "   \"companyOptional\": {" +
                        "        \"id\": "+trendOptionalId+","+
                        "        \"companyOptionalLists\": {" +
                        "           \"id\": "+listsId+","+
                        "            \"contacts\": [" +
                        "                {" +
                        "                   \"id\": "+contactId+","+
                        "                   \"name\": \"" + companyOptionalListsValues.get("contactsName") + "\"," +
                        "                   \"email\": \"" + companyOptionalListsValues.get("contactsEmail") + "\"," +
                        "                   \"telephone\": \"" + companyOptionalListsValues.get("contactsTelephone") + "\"," +
                        "                   \"organisation\": \"" + companyOptionalListsValues.get("contactsOrganisation") +"\"" +
                        "               }" +
                        "           ]," +
                        "            \"targetMarkets\": [" +
                        "                "+ companyOptionalListsValues.get("targetMarkets1")+","
                                            +companyOptionalListsValues.get("targetMarkets2")+
                        "           ]," +
                        "            \"alignments\": [" +
                        "                "+ companyOptionalListsValues.get("alignment1")+","
                                            +companyOptionalListsValues.get("alignment2")+
                        "           ]," +
                        "            \"partners\": [" +
                        "                "+ companyOptionalListsValues.get("partner1")+","
                                            +companyOptionalListsValues.get("partner2")+
                        "           ]," +
                        "            \"investors\": [" +
                        "                "+ companyOptionalListsValues.get("investor1")+","
                                            +companyOptionalListsValues.get("investor2")+
                        "           ]" +
                        "        }," +
                        "        \"companyOptionalFields\": {" +
                        "            \"id\": "+fieldId+","+
                        "            \"location\": {"+
                        "                   \"id\": "+locationId+","+
                        "                   \"country\": \"" + companyOptionalFieldsValues.get("locationCountry") + "\"," +
                        "                   \"city\": \"" + companyOptionalFieldsValues.get("locationCity") + "\"" +
                        "               }," +
                        "            \"productReadiness\": \"" + companyOptionalFieldsValues.get("productReadiness") + "\"," +
                        "            \"numberOfCustomers\": {" +
                        "                 \"id\": "+customersId+","+
                        "                 \"value\": 42," +
                        "                 \"year\": 2020," +
                        "                 \"reference\": \"" + companyOptionalFieldsValues.get("numberOfCustomersReference") + "\"" +
                        "               }," +
                        "            \"revenue\": {" +
                        "                   \"id\": "+revenueId+","+
                        "                 \"value\": 42," +
                        "                 \"year\": 2020," +
                        "                 \"reference\": \"" + companyOptionalFieldsValues.get("revenueReference") + "\"" +
                        "               }," +
                        "            \"profit\": {" +
                        "                   \"id\": "+profitId+","+
                        "                 \"value\": 42," +
                        "                 \"year\": 2020," +
                        "                 \"reference\": \"" + companyOptionalFieldsValues.get("profitReference") + "\"" +
                        "               }" +
                        "        }" +

                        "   }}";

        //update trend
        this.mockMvc.perform(put("/company/" + testTrendId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + adminToken).content(changedBody))
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
                .andExpect(jsonPath("$.coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("$.coreField.teaser").isString())
                .andExpect(jsonPath("$.coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                .andExpect(jsonPath("$.coreField.industry").isString())
                .andExpect(jsonPath("$.coreField.industry").value("Luft- und Raumfahrt und Verteidigung"))
                .andExpect(jsonPath("$.coreField.tags").isArray())
                .andExpect(jsonPath("$.coreField.tags", hasSize(2)))
                .andExpect(jsonPath("$.coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("$.coreField.type").isString())
                .andExpect(jsonPath("$.coreField.type").value("Firma"))
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").isNumber())
                .andExpect(jsonPath("$.detailedRating.degreeOfInnovation").value(1.0))
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").isNumber())
                .andExpect(jsonPath("$.detailedRating.disruptionPotential").value(1.0))
                .andExpect(jsonPath("$.detailedRating.useCases").isNumber())
                .andExpect(jsonPath("$.detailedRating.useCases").value(1.0))
                .andExpect(jsonPath("$.companyRequired.id").isNumber())
                .andExpect(jsonPath("$.companyRequired.description").isString())
                .andExpect(jsonPath("$.companyRequired.description").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore"))
                .andExpect(jsonPath("$.companyRequired.useCases").isString())
                .andExpect(jsonPath("$.companyRequired.useCases").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                .andExpect(jsonPath("$.companyRequired.teamSize.id").isNumber())
                .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").isString())
                .andExpect(jsonPath("$.companyRequired.teamSize.teamSize").value("Kleinstunternehmen"))
                .andExpect(jsonPath("$.companyRequired.teamSize.year").isNumber())
                .andExpect(jsonPath("$.companyRequired.teamSize.year").value(2020))
                .andExpect(jsonPath("$.companyRequired.teamSize.reference").isString())
                .andExpect(jsonPath("$.companyRequired.teamSize.reference").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.companyRequired.website").isString())
                .andExpect(jsonPath("$.companyRequired.website").value("tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"))
                .andExpect(jsonPath("$.companyRequired.foundationYear").isNumber())
                .andExpect(jsonPath("$.companyRequired.foundationYear").value(2000))
                .andExpect(jsonPath("$.companyOptional.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.externalItems").isArray())
                .andExpect(jsonPath("$.companyOptional.externalItems", hasSize(0)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts", hasSize(1)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].name").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].name").value("Hans"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].email").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].email").value("hans@Wurst.der"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].telephone").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].telephone").value("+(049)123 45678789"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].organisation").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.contacts[0].organisation").value("Metzgerei Hans"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets", hasSize(2)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.targetMarkets", containsInAnyOrder("Deutschland", "USA")))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments", hasSize(2)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.alignments", containsInAnyOrder("Business to Business", "Business to Government")))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners", hasSize(2)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.partners", containsInAnyOrder("Metzgerei Hans", "Metzgerei Horst")))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors").isArray())
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors", hasSize(2)))
                .andExpect(jsonPath("$.companyOptional.companyOptionalLists.investors", containsInAnyOrder("Metzgerei Hans", "Metzgerei Horst")))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.country").value("Germany"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.location.city").value("Stuttgart"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.value").value("42"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.year").value(2020))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.numberOfCustomers.reference").value("invidunt utlabore et dolore magna aliquyam erat, sed diam"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.value").value("42"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.year").value(2020))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.revenue.reference").value("invidunt utlabore et dolore magna aliquyam erat, sed diam"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.id").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.value").value("42"))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").isNumber())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.year").value(2020))
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").isString())
                .andExpect(jsonPath("$.companyOptional.companyOptionalFields.profit.reference").value("invidunt utlabore et dolore magna aliquyam erat, sed diam"))
                .andExpect(jsonPath("$.internalProjects").isArray())
                .andExpect(jsonPath("$.internalProjects", hasSize(0)))
                .andExpect(jsonPath("$.internalCompanies").isArray())
                .andExpect(jsonPath("$.internalCompanies", hasSize(0)))
                .andExpect(jsonPath("$.internalTrends").isArray())
                .andExpect(jsonPath("$.internalTrends", hasSize(0)))
                .andExpect(jsonPath("$.internalTechnologies").isArray())
                .andExpect(jsonPath("$.internalTechnologies", hasSize(0)));
        //delete trend
        this.mockMvc.perform(delete("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void editNonExistingCompany() throws Exception {
        //create trend
        MvcResult result =
                this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //Save trend ids
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$.id");
        int coreFieldId= JsonPath.parse(response).read("$.coreField.id");
        int trendRequiredId = JsonPath.parse(response).read("$.companyRequired.id");
        int teamSizeId =  JsonPath.parse(response).read("$.companyRequired.teamSize.id");
        String changedBody =
                "{" +
                        " \"id\": "+testTrendId+","+
                        "   \"coreField\": {" +
                        "       \"id\": "+coreFieldId+","+
                        "       \"intern\": " + coreFieldValues.get("intern") + "," +
                        "       \"source\": \"" + coreFieldValues.get("source") + "\"," +
                        "       \"imageSource\": [\"" + coreFieldValues.get("imageSource") + "\"]," +
                        "       \"headline\": \"" + coreFieldValues.get("headline") + "\"," +
                        "       \"teaser\": \"" + coreFieldValues.get("teaser") + "\"," +
                        "       \"industry\": \"" + coreFieldValues.get("industry") + "\"," +
                        "       \"tags\": [" +
                        "           \"abcd\"," +
                        "           \"efgh\"" +
                        "       ]," +
                        "       \"type\": \"" + coreFieldValues.get("type") + "\"" +
                        "   }," +
                        "   \"detailedRating\":{"+
                        "       \"degreeOfInnovation\": 1,"+
                        "       \"disruptionPotential\": 1,"+
                        "       \"useCases\": 1"+
                        "   }," +
                        "   \"companyRequired\": {" +
                        "       \"id\": "+trendRequiredId+","+
                        "       \"description\": \"" + companyRequiredValues.get("description") + "\"," +
                        "       \"useCases\": \"" + companyRequiredValues.get("useCase") + "\"," +
                        "       \"teamSize\": {" +
                        "           \"id\": "+teamSizeId+","+
                        "           \"teamSize\": \"" + teamSizeValues.get("teamSize") + "\"," +
                        "           \"year\": 2020," +
                        "           \"reference\": \"" + teamSizeValues.get("reference") + "\"" +
                        "       }," +
                        "       \"website\": \"" + companyRequiredValues.get("website") + "\"," +
                        "       \"foundationYear\": 2050" +
                        "   }" +
                        "}";

        this.mockMvc.perform(put("/company/1234567890").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(changedBody))
                .andExpect(status().isNotFound());
        this.mockMvc.perform(delete("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void deleteCompany() throws Exception {
        //Create trend
        MvcResult result =
                this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(minimalBody)).andReturn();
        //Save trend id
        String response = result.getResponse().getContentAsString();
        int testTrendId = JsonPath.parse(response).read("$['id']");
        //Delete trend
        this.mockMvc.perform(delete("/company/" + testTrendId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void deleteNonExistingCompany() throws Exception {
        this.mockMvc.perform(delete("/company/1234567890").header("Authorization", "Bearer " + adminToken)).andExpect(status().isNotFound());
    }

}