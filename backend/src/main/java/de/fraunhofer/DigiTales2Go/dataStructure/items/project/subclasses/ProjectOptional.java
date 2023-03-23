package de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.ReferencedValue;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.ProjectOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases.ProjectRelations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Project optional.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_optionals")
public class ProjectOptional {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ExternalItem> externalItems = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "publications", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "publications")
    @Lob
    private List<String> publications = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_relations_id", referencedColumnName = "id")
    private ProjectRelations projectRelations = new ProjectRelations();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_financing", referencedColumnName = "id")
    private ReferencedValue financing = new ReferencedValue();
    private String website = "";


    /**
     * Instantiates a new Project optional. Used to empty an existing ProjectOptional object
     *
     * @param relationsId the relations id
     */
    public ProjectOptional(Long relationsId, Long financingId) {
        this.projectRelations = new ProjectRelations(relationsId);
        this.financing = new ReferencedValue(financingId);
    }

    /**
     * Constructor from Create DTO used by editing, checks each value for null
     *
     * @param optional the optional
     */
    public ProjectOptional(ProjectOptionalCreationDTO optional, Long relationsId, Long financingId) {


        if (optional.getExternalItems() != null) {
            this.externalItems = optional.getExternalItems();
        }

        if (optional.getProjectRelations() != null) {
            this.projectRelations = new ProjectRelations(optional.getProjectRelations());
        }
        this.projectRelations.setId(relationsId);

        if (optional.getPublications() != null) {
            this.publications = optional.getPublications();
        }

        if (optional.getFinancing() != null) {
            this.financing = new ReferencedValue(optional.getFinancing());

        }
        this.financing.setId(financingId);

        if (optional.getWebsite() != null) {
            this.website = optional.getWebsite();
        }
    }

    /**
     * Constructor from Create DTO, checks each value for null
     *
     * @param optional the optional
     */
    public ProjectOptional(ProjectOptionalCreationDTO optional) {


        if (optional.getExternalItems() != null) {
            this.externalItems = optional.getExternalItems();
        }

        if (optional.getProjectRelations() != null) {
            this.projectRelations = new ProjectRelations(optional.getProjectRelations());
        }

        if (optional.getPublications() != null) {
            this.publications = optional.getPublications();
        }

        if (optional.getFinancing() != null) {
            this.financing = new ReferencedValue(optional.getFinancing());

        }

        if (optional.getWebsite() != null) {
            this.website = optional.getWebsite();
        }
    }

}
