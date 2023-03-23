package de.fraunhofer.DigiTales2Go.fileUpload;

import com.jayway.jsonpath.JsonPath;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileControllerTest {
    @Autowired
    MockMvc mockMvc;
    private final String baseDir = "src\\test\\java\\de\\fraunhofer\\DigiTales2Go\\fileUpload\\";
    private static String adminMail;
    private static String adminPassword;
    private String adminToken;
    @BeforeAll
    void init(){
        try {
            adminToken = generateAdminToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void uploadCompany() throws Exception {

        File companyFile = new File(baseDir + "company.json");

        MockMultipartFile file = new MockMultipartFile("file", "company.json"
                ,String.valueOf(MediaType.MULTIPART_FORM_DATA), Files.readAllBytes(companyFile.toPath()));
        MvcResult result = mockMvc.perform(multipart("/fileUpload/").file(file).header("Authorization", "Bearer " + adminToken).header("contentType", "multipart/form-data")).andExpect(status().isCreated()).andReturn();
        String response = result.getResponse().getContentAsString();
        int testId = JsonPath.parse(response).read("$['id']");
        this.mockMvc.perform(delete("/company/" + testId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }

    @Test
    void uploadProject() throws Exception {

        File projectFile = new File(baseDir + "project.json");

        MockMultipartFile file = new MockMultipartFile("file", "project.json"
                ,String.valueOf(MediaType.MULTIPART_FORM_DATA), Files.readAllBytes(projectFile.toPath()));
        MvcResult result = mockMvc.perform(multipart("/fileUpload/").file(file).header("Authorization", "Bearer " + adminToken).header("contentType", "multipart/form-data")).andExpect(status().isCreated()).andReturn();
        String response = result.getResponse().getContentAsString();
        int testId = JsonPath.parse(response).read("$['id']");
        this.mockMvc.perform(delete("/project/" + testId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void uploadTechnology() throws Exception {

        File techFile = new File(baseDir + "technology.json");

        MockMultipartFile file = new MockMultipartFile("file", "technology.json"
                ,String.valueOf(MediaType.MULTIPART_FORM_DATA), Files.readAllBytes(techFile.toPath()));
        MvcResult result = mockMvc.perform(multipart("/fileUpload/").file(file).header("Authorization", "Bearer " + adminToken).header("contentType", "multipart/form-data")).andExpect(status().isCreated()).andReturn();
        String response = result.getResponse().getContentAsString();
        int testId = JsonPath.parse(response).read("$['id']");
        this.mockMvc.perform(delete("/tech/" + testId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }
    @Test
    void uploadTrend() throws Exception {

        File trendFile = new File(baseDir + "trend.json");

        MockMultipartFile file = new MockMultipartFile("file", "trend.json"
                ,String.valueOf(MediaType.MULTIPART_FORM_DATA), Files.readAllBytes(trendFile.toPath()));
        MvcResult result = mockMvc.perform(multipart("/fileUpload/").file(file).header("Authorization", "Bearer " + adminToken).header("contentType", "multipart/form-data")).andExpect(status().isCreated()).andReturn();
        String response = result.getResponse().getContentAsString();
        int testId = JsonPath.parse(response).read("$['id']");
        this.mockMvc.perform(delete("/trend/" + testId).header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk());
    }



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
}
