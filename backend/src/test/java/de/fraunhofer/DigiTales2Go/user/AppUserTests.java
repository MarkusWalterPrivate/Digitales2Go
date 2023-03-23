package de.fraunhofer.DigiTales2Go.user;

import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.user.dtos.AppUserCreationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class AppUserTests {
     private final Industry industry = Industry.ALTERNATEENERGY;

/*
     @Test
    void createWithFullConstructor() {
         String password = "aisdasd";
         String email = "EmailAddress";
         String company = "MyCompany";
         String job = "MyJob";
         String lastName = "FirstName";
         String firstName = "FirstName";
         AppUser appUser = new AppUser(new AppUserCreationDTO(firstName, lastName, email, company, job, industry, password));

         String title = "Title";
         assertEquals(title, appUser.getLastName());
        assertEquals(firstName, appUser.getFirstName());
        assertEquals(job, appUser.getJob());
        assertEquals(email, appUser.getEmail());
    }

 */
}
