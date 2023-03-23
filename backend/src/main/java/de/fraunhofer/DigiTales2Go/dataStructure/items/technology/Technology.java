/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.technology;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.subclasses.TechOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.subclasses.TechRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Technology.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "technologys")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "core_field_id", referencedColumnName = "id")
    private CoreField coreField;
    @OneToOne(cascade = CascadeType.ALL)
    private DetailedRating detailedRating = new DetailedRating();
    @OneToOne(cascade = CascadeType.ALL)
    private TechRequired techRequired = new TechRequired();
    @OneToOne(cascade = CascadeType.ALL)
    private TechOptional techOptional = new TechOptional();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "technology_project",
            joinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id")
    )
    private Set<Project> internalProjects = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "technology_trend",
            joinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "trend_id", referencedColumnName = "id")
    )
    private Set<Trend> internalTrends = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "technology_technology",
            joinColumns = @JoinColumn(name = "owning_technology_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id")
    )
    private Set<Technology> internalTechnologies = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "technology_company",
            joinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id")
    )
    private Set<Company> internalCompanies = new HashSet<>();
    @OneToMany
    private List<Comment> comments = new ArrayList<>(); //Needs to be implemented later
    @OneToMany
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();


    /**
     * Instantiates a new Technology.
     *
     * @param coreField            the core field
     * @param detailedRating       the detailed priorRating
     * @param techRequired         the tech required
     * @param techOptional         the tech optional
     * @param internalProjects     the internal projects
     * @param internalTrends       the internal trends
     * @param internalTechnologies the internal technologies
     * @param internalCompanies    the internal companies
     * @param comments             the comments
     */
    public Technology(CoreField coreField, DetailedRating detailedRating, TechRequired techRequired, TechOptional techOptional, List<Project> internalProjects, List<Trend> internalTrends, List<Technology> internalTechnologies, List<Company> internalCompanies, List<Comment> comments) {
        this.coreField = coreField;
        this.detailedRating = detailedRating;
        this.techRequired = techRequired;
        this.techOptional = techOptional;
        this.internalProjects = new HashSet<>(internalProjects);
        this.internalTrends = new HashSet<>(internalTrends);
        this.internalTechnologies = new HashSet<>(internalTechnologies);
        this.internalCompanies = new HashSet<>(internalCompanies);
        this.comments = comments;
    }


    /**
     * Like.
     */
    public void like() {
        this.coreField.upvote();
    }

    /**
     * Dislike.
     */
    public void dislike() {
        this.coreField.downvote();
    }

    public void approve() {
        this.coreField.approve();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
