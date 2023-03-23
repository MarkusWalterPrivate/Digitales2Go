/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.trend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendRequired;
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
 * The type Trend.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trends")
public class Trend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "core_field_id", referencedColumnName = "id")
    private CoreField coreField = new CoreField();
    @OneToOne(cascade = CascadeType.ALL)
    private DetailedRating detailedRating = new DetailedRating();
    @OneToOne(cascade = CascadeType.ALL)
    private TrendRequired trendRequired = new TrendRequired();
    @OneToOne(cascade = CascadeType.ALL)
    private TrendOptional trendOptional = new TrendOptional();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "trend_project",
            joinColumns = @JoinColumn(name = "trend_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id")
    )
    private Set<Project> internalProjects = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "trend_trend",
            joinColumns = @JoinColumn(name = "owning_trend_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "trend_id", referencedColumnName = "id")
    )
    private Set<Trend> internalTrends = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "trend_technologies",
            joinColumns = @JoinColumn(name = "trend_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id")
    )
    private Set<Technology> internalTechnologies = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "trend_company",
            joinColumns = @JoinColumn(name = "trend_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id")
    )
    private Set<Company> internalCompanies = new HashSet<>();


    @OneToMany
    private List<Comment> comments = new ArrayList<>(); //Needs to be implemented later

    @OneToMany
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();


    /**
     * Constructor for Controller
     *
     * @param coreField            the core field
     * @param trendRequired        the trend required
     * @param trendOptional        the trend optional
     * @param internalProjects     the internal projects
     * @param internalTrends       the internal trends
     * @param internalTechnologies the internal technologies
     * @param internalCompanies    the internal companies
     */
    public Trend(CoreField coreField, TrendRequired trendRequired, TrendOptional trendOptional, List<Project> internalProjects, List<Trend> internalTrends, List<Technology> internalTechnologies, List<Company> internalCompanies) {
        coreField.setType(Type.TREND);
        this.coreField = coreField;
        this.trendRequired = trendRequired;
        this.trendOptional = trendOptional;
        this.internalProjects = new HashSet<>(internalProjects);
        this.internalTrends = new HashSet<>(internalTrends);
        this.internalTechnologies = new HashSet<>(internalTechnologies);
        this.internalCompanies = new HashSet<>(internalCompanies);
    }

    /**
     * Increases priorRating by 1
     */
    public void like() {
        this.coreField.upvote();
    }

    /**
     * decreases priorRating by 1
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
