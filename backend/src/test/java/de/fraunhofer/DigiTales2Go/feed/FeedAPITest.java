package de.fraunhofer.DigiTales2Go.feed;


import com.jayway.jsonpath.JsonPath;
import de.fraunhofer.DigiTales2Go.exampleItems.ExampleBodyHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class TechFeedApiTests {

    @Autowired
    MockMvc mockMvc;
    private static String adminMail;
    private static String adminPassword;
    private String adminToken;

    private final String body = "{\"itemsPerPage\" : 2,\"page\" : 0}";

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
    }

    @Test
    void getAllFeeds() throws Exception{
        List<Integer> testCompanyIds = createAndPrepareAllItems();
        //get Hot Tech
        this.mockMvc.perform(post("/feed/hot").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feed").isArray())/*
                .andExpect(jsonPath("[0].id").isNumber())
                .andExpect(jsonPath("[0].coreField.id").isNumber())
                .andExpect(jsonPath("[0].coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("[0].coreField.creationDate").isNumber())
                .andExpect(jsonPath("[0].coreField.intern").isBoolean())
                .andExpect(jsonPath("[0].coreField.intern").value(true))
                .andExpect(jsonPath("[0].coreField.source").isString())
                .andExpect(jsonPath("[0].coreField.source").value("source"))
                .andExpect(jsonPath("[0].coreField.rating").isNumber())
                .andExpect(jsonPath("[0].coreField.imageSource").isString())
                .andExpect(jsonPath("[0].coreField.imageSource").value("imageSource"))
                .andExpect(jsonPath("[0].coreField.headline").isString())
                .andExpect(jsonPath("[0].coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("[0].coreField.teaser").isString())
                .andExpect(jsonPath("[0].coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                //compare the industries to evaluate if list is sorted correct (the most rated element first)
                .andExpect(jsonPath("[0].coreField.industry").isString())
                .andExpect(jsonPath("[1].coreField.industry").isString())
                .andExpect(jsonPath("[2].coreField.industry").isString())
                .andExpect(jsonPath("[0,1,2].coreField.industry", containsInAnyOrder("Aerospace and Defense", "General Industrials", "Chemicals")))
                .andExpect(jsonPath("[0].coreField.tags").isArray())
                .andExpect(jsonPath("[0].coreField.tags", hasSize(2)))
                .andExpect(jsonPath("[0].coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("[0].coreField.type").isString())
                .andExpect(jsonPath("[0].coreField.type").value("Company"))*/;

        this.mockMvc.perform(post("/feed/new").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feed").isArray())
                /*
                .andExpect(jsonPath("[0].id").isNumber())
                .andExpect(jsonPath("[0].coreField.id").isNumber())
                .andExpect(jsonPath("[0].coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("[0].coreField.creationDate").isNumber())
                .andExpect(jsonPath("[0].coreField.intern").isBoolean())
                .andExpect(jsonPath("[0].coreField.intern").value(true))
                .andExpect(jsonPath("[0].coreField.source").isString())
                .andExpect(jsonPath("[0].coreField.source").value("source"))
                .andExpect(jsonPath("[0].coreField.rating").isNumber())
                .andExpect(jsonPath("[0].coreField.imageSource").isString())
                .andExpect(jsonPath("[0].coreField.imageSource").value("imageSource"))
                .andExpect(jsonPath("[0].coreField.headline").isString())
                .andExpect(jsonPath("[0].coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("[0].coreField.teaser").isString())
                .andExpect(jsonPath("[0].coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                //compare the industries to evaluate if list is sorted correct (the newest element first)
                .andExpect(jsonPath("[0].coreField.industry").isString())
                .andExpect(jsonPath("[0].coreField.industry").value("Oil Gas and Coal"))
                .andExpect(jsonPath("[1].coreField.industry").isString())
                .andExpect(jsonPath("[1].coreField.industry").value("Alternative Energy"))
                .andExpect(jsonPath("[2].coreField.industry").isString())
                .andExpect(jsonPath("[2].coreField.industry").value("Electricity"))
                .andExpect(jsonPath("[0].coreField.tags").isArray())
                .andExpect(jsonPath("[0].coreField.tags", hasSize(2)))
                .andExpect(jsonPath("[0].coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("[0].coreField.type").isString())
                .andExpect(jsonPath("[0].coreField.type").value("Company"))
                */;


        this.mockMvc.perform(post("/feed/interest").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feed").isArray())
                /*.andExpect(jsonPath("[0].id").isNumber())
                .andExpect(jsonPath("[0].coreField.id").isNumber())
                .andExpect(jsonPath("[0].coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("[0].coreField.creationDate").isNumber())
                .andExpect(jsonPath("[0].coreField.intern").isBoolean())
                .andExpect(jsonPath("[0].coreField.intern").value(true))
                .andExpect(jsonPath("[0].coreField.source").isString())
                .andExpect(jsonPath("[0].coreField.source").value("source"))
                .andExpect(jsonPath("[0].coreField.rating").isNumber())
                .andExpect(jsonPath("[0].coreField.imageSource").isString())
                .andExpect(jsonPath("[0].coreField.imageSource").value("imageSource"))
                .andExpect(jsonPath("[0].coreField.headline").isString())
                .andExpect(jsonPath("[0].coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("[0].coreField.teaser").isString())
                .andExpect(jsonPath("[0].coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                //compare the industries to evaluate if list is sorted correct (the newest element first)
                .andExpect(jsonPath("[0].coreField.industry").isString())
                .andExpect(jsonPath("[0].coreField.industry").value("Technology Hardware and Equipment"))
                .andExpect(jsonPath("[1].coreField.industry").isString())
                .andExpect(jsonPath("[1].coreField.industry").value("Electronic and Electrical Equipment"))
                .andExpect(jsonPath("[2].coreField.industry").isString())
                .andExpect(jsonPath("[2].coreField.industry").value("Industrial Engineering"))
                .andExpect(jsonPath("[0].coreField.tags").isArray())
                .andExpect(jsonPath("[0].coreField.tags", hasSize(2)))
                .andExpect(jsonPath("[0].coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("[0].coreField.type").isString())
                .andExpect(jsonPath("[0].coreField.type").value("Company"))*/;

        deleteAllItems(testCompanyIds);

    }

    List<Integer> createAndPrepareAllItems() throws Exception{
        List<String> allBodies = ExampleBodyHelper.getFullBodies();
        List<Integer> testCompanyIds = new ArrayList<>();
        for(String body : allBodies) {
            MvcResult result =
                    this.mockMvc.perform(post("/company/").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON).content(body))
                            .andExpect(status().isCreated())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn();
            //save trend id
            String response = result.getResponse().getContentAsString();
            int currentId = JsonPath.parse(response).read("$['id']");
            testCompanyIds.add(currentId);
            this.mockMvc.perform(post("/approve/"+currentId).header("Authorization", "Bearer " + adminToken))
                    .andReturn();
            if(allBodies.indexOf(body) < 3) {
                this.mockMvc.perform(post("/swipe/upvote/" + currentId).header("Authorization", "Bearer " + adminToken))
                        .andReturn();
            }
        }
        return testCompanyIds;
    }


    void deleteAllItems(List<Integer> testCompanyIds) throws Exception{
        for(int testCompanyId : testCompanyIds) {
            log.info("Trying to delete {}", testCompanyId);
            this.mockMvc.perform(delete("/company/" + testCompanyId).header("Authorization", "Bearer " + adminToken))
                    .andExpect(status().isOk());
        }
    }

    /*@Test
    void getInterest() throws Exception{
        //get tech matching interest
        createAllItems();
        //get Hot Tech
        this.mockMvc.perform(get("/feed/interest").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(jsonPath("[0].id").isNumber())
                .andExpect(jsonPath("[0].coreField.id").isNumber())
                .andExpect(jsonPath("[0].coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("[0].coreField.creationDate").isNumber())
                .andExpect(jsonPath("[0].coreField.intern").isBoolean())
                .andExpect(jsonPath("[0].coreField.intern").value(true))
                .andExpect(jsonPath("[0].coreField.source").isString())
                .andExpect(jsonPath("[0].coreField.source").value("source"))
                .andExpect(jsonPath("[0].coreField.rating").isNumber())
                .andExpect(jsonPath("[0].coreField.imageSource").isString())
                .andExpect(jsonPath("[0].coreField.imageSource").value("imageSource"))
                .andExpect(jsonPath("[0].coreField.headline").isString())
                .andExpect(jsonPath("[0].coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("[0].coreField.teaser").isString())
                .andExpect(jsonPath("[0].coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                //compare the industries to evaluate if list is sorted correct (the newest element first)
                .andExpect(jsonPath("[0].coreField.industry").isString())
                .andExpect(jsonPath("[0].coreField.industry").value("Technology Hardware and Equipment"))
                .andExpect(jsonPath("[1].coreField.industry").isString())
                .andExpect(jsonPath("[1].coreField.industry").value("Electronic and Electrical Equipment"))
                .andExpect(jsonPath("[2].coreField.industry").isString())
                .andExpect(jsonPath("[2].coreField.industry").value("Industrial Engineering"))
                .andExpect(jsonPath("[0].coreField.tags").isArray())
                .andExpect(jsonPath("[0].coreField.tags", hasSize(2)))
                .andExpect(jsonPath("[0].coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("[0].coreField.type").isString())
                .andExpect(jsonPath("[0].coreField.type").value("Company"));

        deleteAllItems();
    }
    @Test
    void getNew() throws Exception{
        //get new tech
        createAllItems();
        //get Hot Tech
        this.mockMvc.perform(get("/feed/new").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[0].id").isNumber())
                .andExpect(jsonPath("[0].coreField.id").isNumber())
                .andExpect(jsonPath("[0].coreField.lastUpdated").isNumber())
                .andExpect(jsonPath("[0].coreField.creationDate").isNumber())
                .andExpect(jsonPath("[0].coreField.intern").isBoolean())
                .andExpect(jsonPath("[0].coreField.intern").value(true))
                .andExpect(jsonPath("[0].coreField.source").isString())
                .andExpect(jsonPath("[0].coreField.source").value("source"))
                .andExpect(jsonPath("[0].coreField.rating").isNumber())
                .andExpect(jsonPath("[0].coreField.imageSource").isString())
                .andExpect(jsonPath("[0].coreField.imageSource").value("imageSource"))
                .andExpect(jsonPath("[0].coreField.headline").isString())
                .andExpect(jsonPath("[0].coreField.headline").value("Demo headline"))
                .andExpect(jsonPath("[0].coreField.teaser").isString())
                .andExpect(jsonPath("[0].coreField.teaser").value("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
                //compare the industries to evaluate if list is sorted correct (the newest element first)
                .andExpect(jsonPath("[0].coreField.industry").isString())
                .andExpect(jsonPath("[0].coreField.industry").value("Technology Hardware and Equipment"))
                .andExpect(jsonPath("[1].coreField.industry").isString())
                .andExpect(jsonPath("[1].coreField.industry").value("Electronic and Electrical Equipment"))
                .andExpect(jsonPath("[2].coreField.industry").isString())
                .andExpect(jsonPath("[2].coreField.industry").value("Industrial Engineering"))
                .andExpect(jsonPath("[0].coreField.tags").isArray())
                .andExpect(jsonPath("[0].coreField.tags", hasSize(2)))
                .andExpect(jsonPath("[0].coreField.tags[*]", containsInAnyOrder("Tag1", "Tag2")))
                .andExpect(jsonPath("[0].coreField.type").isString())
                .andExpect(jsonPath("[0].coreField.type").value("Company"));

        deleteAllItems();
    }*/
}