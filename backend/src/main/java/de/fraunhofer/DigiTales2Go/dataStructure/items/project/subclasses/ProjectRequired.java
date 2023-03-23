package de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.ReadinessProject;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.ProjectRequiredCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases.Runtime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Project required.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_requireds")
public class ProjectRequired {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private String description = "";
    private ReadinessProject readiness;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "runtime_id")
    private Runtime runtime = new Runtime();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();


    /**
     * Constructor for Demo
     *
     * @param description the description
     * @param readiness   the readiness
     * @param runtime     the runtime
     * @param contacts    the contacts
     */
    public ProjectRequired(String description, ReadinessProject readiness, Runtime runtime, List<Contact> contacts) {
        this.description = description;
        this.readiness = readiness;
        this.runtime = runtime;
        this.contacts = contacts;
    }

    /**
     * Constructor from CreationDTO
     *
     * @param required the required
     */
    public ProjectRequired(ProjectRequiredCreationDTO required) {
        this.description = required.getDescription();
        this.readiness = required.getReadiness();
        this.runtime = new Runtime(required.getRuntime());
        this.contacts = required.getContacts();
    }

    /**
     * Constructor from EditingDTO
     *
     * @param required  the required
     * @param runtimeId the runtime id
     */
    public ProjectRequired(ProjectRequiredCreationDTO required, Long runtimeId) {
        this.description = required.getDescription();
        this.readiness = required.getReadiness();
        this.runtime = new Runtime(required.getRuntime(), runtimeId);
        this.contacts = required.getContacts();
    }
}
