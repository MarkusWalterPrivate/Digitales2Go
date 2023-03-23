package de.fraunhofer.DigiTales2Go.user;


import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.jayway.jsonpath.JsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppUserApiTests {

    @Autowired
    private MockMvc mockMvc;

    //used to sync data between two tests
    private String body = "{\"firstName\" : \"test\",\"lastName\" : \"lastTest\",\"email\": \"1111@VERGEBEN.de\", \"company\": \"digiTales\", \"job\" : \"bla\",\"industry\": \"SOFTWARE\", \"password\": \"1234sicher5678\", \"interests\" : [\"OILGASCOAL\", \"ELECTRICITY\"] }";
    private final String body1 = "{\"firstName\" : \"test\",\"lastName\" : \"lastTest\",\"email\": \"2222@VERGEBEN.de\", \"company\": \"digiTales\", \"job\" : \"bla\",\"industry\": \"SOFTWARE\", \"password\": \"1234sicher5678\", \"interests\" : [\"OILGASCOAL\", \"ELECTRICITY\"] }";
    private final String body2 = "{\"firstName\" : \"test\",\"lastName\" : \"lastTest\",\"email\": \"3333@VERGEBEN.de\", \"company\": \"digiTales\", \"job\" : \"bla\",\"industry\": \"SOFTWARE\", \"password\": \"1234sicher5678\", \"interests\" : [\"OILGASCOAL\", \"ELECTRICITY\"] }";
    private final String body3 = "{\"firstName\" : \"test\",\"lastName\" : \"lastTest\",\"email\": \"4444@VERGEBEN.de\", \"company\": \"digiTales\", \"job\" : \"bla\",\"industry\": \"SOFTWARE\", \"password\": \"1234sicher5678\", \"interests\" : [\"OILGASCOAL\", \"ELECTRICITY\"] }";
    private final String body4 = "{\"firstName\" : \"test\",\"lastName\" : \"lastTest\",\"email\": \"5555@VERGEBEN.de\", \"company\": \"digiTales\", \"job\" : \"bla\",\"industry\": \"SOFTWARE\", \"password\": \"1234sicher5678\", \"interests\" : [\"OILGASCOAL\", \"ELECTRICITY\"] }";
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
    }
    @Test
    void createNewUser() throws Exception{
        //create new user
        MvcResult result = this.mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"firstName\" : \"test\"}"))
                .andExpect(content().json("{\"lastName\" : \"lastTest\"}"))
                .andExpect(content().json("{\"company\": \"digiTales\"}"))
                .andExpect(content().json("{\"job\" : \"bla\"}"))
                .andExpect(content().json("{\"industry\": \"Software und Computerdienstleistungen\"}"))
                .andExpect(content().json("{\"interests\" : [\"Öl, Gas und Kohle\", \"Elektrizität\"]}"))
                .andReturn();
        //save user id
        String response = result.getResponse().getContentAsString();
        int testUserId = JsonPath.parse(response).read("$['id']");
        //delete user
        this.mockMvc.perform(delete("/user/"+testUserId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void deleteUser() throws Exception{
        //Create AppUser
        MvcResult result =  this.mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON).content(body1))
                .andReturn();
        //Save AppUser id
        String response = result.getResponse().getContentAsString();
        int testUserId = JsonPath.parse(response).read("$['id']");
        //Delete AppUser
        this.mockMvc.perform(delete("/user/"+testUserId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }
	@Test
	void getUser() throws Exception{
        //create new user
        MvcResult result = this.mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON).content(body2))
                .andReturn();
        //save user id
        String response = result.getResponse().getContentAsString();
        int testUserId = JsonPath.parse(response).read("$['id']");
        //get AppUser
            this.mockMvc.perform(get("/user/"+testUserId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"firstName\" : \"test\"}"))
                .andExpect(content().json("{\"lastName\" : \"lastTest\"}"))
                .andExpect(content().json("{\"company\": \"digiTales\"}"))
                .andExpect(content().json("{\"job\" : \"bla\"}"))
                .andExpect(content().json("{\"industry\": \"Software und Computerdienstleistungen\"}"))
                .andExpect(content().json("{\"interests\" : [\"Öl, Gas und Kohle\", \"Elektrizität\"]}"))
                .andReturn();
        //Delete AppUser
        this.mockMvc.perform(delete("/user/"+testUserId).header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
	}
    @Test
    void getUserWithFaultyRequest() throws Exception{
        this.mockMvc.perform(get("/user/asdas").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isBadRequest());


    }

    @Test
    void getUnusedUser() throws Exception{
        // if for some reason a user with this ID does exist, change the number
        this.mockMvc.perform(get("/users/1353453423").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void editUser() throws Exception{
        //create user
        MvcResult result = this.mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON).content(body3))
                .andReturn();
        //Save user id
        String response = result.getResponse().getContentAsString();
        int testUserId = JsonPath.parse(response).read("$['id']");
        body = "{\"firstName\" : \"test\", \"job\" : \"kein Bla!\", \"title\" : \"dummy\", \"email\": \"blub@bla.ca\"}";
        //update AppUser
        this.mockMvc.perform(put("/user/"+testUserId).header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(body4))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"firstName\" : \"test\"}"))
                .andExpect(content().json("{\"lastName\" : \"lastTest\"}"))
                .andExpect(content().json("{\"company\": \"digiTales\"}"))
                .andExpect(content().json("{\"job\" : \"bla\"}"))
                .andExpect(content().json("{\"industry\": \"Software und Computerdienstleistungen\"}"))
                .andExpect(content().json("{\"interests\" : [\"Öl, Gas und Kohle\", \"Elektrizität\"]}"))
                .andReturn();
        //delete user
        this.mockMvc.perform(delete("/user/"+testUserId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void editNonExistingUser() throws Exception {
        this.mockMvc.perform(put("/user/1234567980").header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNonExistingUser() throws Exception {
        this.mockMvc.perform(delete("/users/1234567980").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }



}
