/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.project;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.event.Event;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
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
 * The type Project.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "core_field_id")
    private CoreField coreField = new CoreField();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detailed_rating_id")
    private DetailedRating detailedRating = new DetailedRating();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_required_id")
    private ProjectRequired projectRequired = new ProjectRequired();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_optional_id")
    private ProjectOptional projectOptional = new ProjectOptional();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<Event> events = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "project_project",
            joinColumns = @JoinColumn(name = "owning_project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id")
    )
    private Set<Project> internalProjects = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "project_trend",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "trend_id", referencedColumnName = "id")
    )
    private Set<Trend> internalTrends = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "project_technology",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id")
    )
    private Set<Technology> internalTechnologies = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "project_company",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id")
    )
    private Set<Company> internalCompanies = new HashSet<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>(); //Needs to be implemented later
    @OneToMany
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();


    /**
     * Instantiates a new Project. Used for Demo Project
     *
     * @param coreField       the core field
     * @param projectRequired the project required
     * @param projectOptional the project optional
     * @param events          the events
     */
    public Project(CoreField coreField, ProjectRequired projectRequired, ProjectOptional projectOptional, List<Event> events) {
        this.coreField = coreField;
        this.projectRequired = projectRequired;
        this.projectOptional = projectOptional;
        this.events = events;
    }


    /**
     * Increases Rating by 1
     */
    public void like() {
        this.coreField.upvote();
    }

    /**
     * Decreases Rating by 1
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
