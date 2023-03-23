package de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases;


import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.relations.ProjectsRelationsCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Project relations.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_relations")
public class ProjectRelations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ElementCollection // 1
    @CollectionTable(name = "funding_sources", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "organisations") //
    @Lob
    private List<String> fundingSources = new ArrayList<>();
    @ElementCollection // 1
    @CollectionTable(name = "promoters", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "organisations") //
    @Lob
    private List<String> promoters = new ArrayList<>();
    @ElementCollection // 1
    @CollectionTable(name = "project_partners", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "organisations") //
    @Lob
    private List<String> projectPartners = new ArrayList<>();
    @ElementCollection // 1
    @CollectionTable(name = "use_partners", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "organisations") //
    @Lob
    private List<String> usePartners = new ArrayList<>();


    /**
     * Constructor for emptying existing object
     *
     * @param id the id
     */
    public ProjectRelations(Long id) {
        this.id = id;
    }

    /**
     * Constructor for demo
     *
     * @param fundingSources  the funding sources
     * @param promoters       the promoters
     * @param projectPartners the project partners
     * @param usePartners     the use partners
     */
    public ProjectRelations(List<String> fundingSources, List<String> promoters, List<String> projectPartners, List<String> usePartners) {
        this.fundingSources = fundingSources;
        this.promoters = promoters;
        this.projectPartners = projectPartners;
        this.usePartners = usePartners;
    }

    /**
     * Constructor from Creation DTO, checks for null
     *
     * @param relations the relations
     */
    public ProjectRelations(ProjectsRelationsCreationDTO relations) {
        if (relations.getFundingSources() != null) {
            this.fundingSources = relations.getFundingSources();
        }
        if (relations.getProjectPartners() != null) {
            this.projectPartners = relations.getProjectPartners();
        }
        if (relations.getPromoters() != null) {
            this.promoters = relations.getPromoters();
        }
        if (relations.getUsePartners() != null) {
            this.usePartners = relations.getUsePartners();
        }
    }
}
