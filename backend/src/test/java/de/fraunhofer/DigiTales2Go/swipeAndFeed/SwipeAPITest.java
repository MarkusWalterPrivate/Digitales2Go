package de.fraunhofer.DigiTales2Go.swipeAndFeed;

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
class TechSwipeApiTests {

    @Autowired
   MockMvc mockMvc;
    private final static HashMap<String, String> coreFieldValues = new HashMap<>();
    private final static HashMap<String, String> trendRequiredValues = new HashMap<>();
    private static String minimalBody;
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

    trendRequiredValues.put("description", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

    trendRequiredValues.put("discussion", "tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo");

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
}


    @Test
    void upvoteTech() throws Exception{
        //create tech
        MvcResult result = this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andReturn();
        //Save tech id
        String response = result.getResponse().getContentAsString();
        int testTechId = JsonPath.parse(response).read("$['id']");
        System.out.println("This is the testTechId: " + testTechId);
        //upvote tech
        this.mockMvc.perform(post("/swipe/upvote/"+testTechId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        //Check if other object representing tech are synched
        this.mockMvc.perform(get("/trend/"+testTechId).header("Authorization", "Bearer " + adminToken))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.coreField.rating").value(1));
        this.mockMvc.perform(get("/feed/"+testTechId).header("Authorization", "Bearer " + adminToken))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.coreField.rating").value(1));
        //delete tech
        this.mockMvc.perform(delete("/trend/"+testTechId).header("Authorization", "Bearer " + adminToken));
    }
    @Test
    void downvoteTech() throws Exception{
        //create tech
        MvcResult result = this.mockMvc.perform(post("/trend/").header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(minimalBody))
                .andReturn();
        //Save tech id
        String response = result.getResponse().getContentAsString();
        int testTechId = JsonPath.parse(response).read("$['id']");
        System.out.println("This is the testTechId: " + testTechId);
        //downvote tech
        this.mockMvc.perform(post("/swipe/downvote/"+testTechId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        //Check if other object representing tech are synched
        this.mockMvc.perform(get("/trend/"+testTechId).header("Authorization", "Bearer " + adminToken))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.coreField.rating").value(-1));
        this.mockMvc.perform(get("/feed/"+testTechId).header("Authorization", "Bearer " + adminToken))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.coreField.rating").value(-1));
        //delete tech
        this.mockMvc.perform(delete("/trend/"+testTechId).header("Authorization", "Bearer " + adminToken));
    }

}