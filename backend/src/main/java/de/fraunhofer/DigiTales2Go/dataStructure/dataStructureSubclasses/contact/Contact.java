package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * The type Contact.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    private String telephone = "";
    @NotBlank
    private String organisation;


    /**
     * Constructor for Controller
     *
     * @param name         the name
     * @param email        the email
     * @param telephone    the telephone
     * @param organisation the organisation
     */
    public Contact(String name, String email, String telephone, String organisation) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.organisation = organisation;
    }
}
