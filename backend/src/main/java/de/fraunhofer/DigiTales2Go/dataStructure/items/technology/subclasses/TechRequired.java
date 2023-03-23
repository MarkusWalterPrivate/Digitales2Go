package de.fraunhofer.DigiTales2Go.dataStructure.items.technology.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Readiness;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.required.TechRequiredCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tech required.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tech_required")
public class TechRequired {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private String description = "";
    @Lob
    private String useCases = "";
    @Lob
    private String discussion = "";
    @ElementCollection
    @CollectionTable(name = "iao_projects", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "projects")
    @Lob
    private List<String> projectsIAO = new ArrayList<>();
    @Enumerated
    private Readiness readiness = Readiness.NOTSET;


    /**
     * Constrcutor from CreationDTO
     *
     * @param required the required
     */
    public TechRequired(TechRequiredCreationDTO required) {
        this.description = required.getDescription();
        this.discussion = required.getDiscussion();
        this.projectsIAO = required.getProjectsIAO();
        this.readiness = required.getReadiness();
        this.useCases = required.getUseCases();

    }
}
