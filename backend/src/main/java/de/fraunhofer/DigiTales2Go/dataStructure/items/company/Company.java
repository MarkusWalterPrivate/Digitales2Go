/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.CompanyOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.CompanyRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
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
 * The type Company.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companys")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "core_field_id", referencedColumnName = "id")
    private CoreField coreField = new CoreField();
    @OneToOne(cascade = CascadeType.ALL)
    private DetailedRating detailedRating = new DetailedRating();
    @OneToOne(cascade = CascadeType.ALL)
    private CompanyRequired companyRequired = new CompanyRequired();
    @OneToOne(cascade = CascadeType.ALL)
    private CompanyOptional companyOptional = new CompanyOptional();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "company_project",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id")
    )
    private Set<Project> internalProjects = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "company_trend",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "trend_id", referencedColumnName = "id")
    )
    private Set<Trend> internalTrends = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "company_technology",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id")
    )
    private Set<Technology> internalTechnologies = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "company_company",
            joinColumns = @JoinColumn(name = "owning_company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id")
    )
    private Set<Company> internalCompanies = new HashSet<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    @OneToMany
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();


    /**
     * Constructor for Controller
     *
     * @param coreField       the core field
     * @param companyRequired the company required
     * @param companyOptional the company optional
     */
    public Company(CoreField coreField, DetailedRating detailedRating, CompanyRequired companyRequired, CompanyOptional companyOptional) {
        this.coreField = coreField;
        this.detailedRating = detailedRating;
        this.companyRequired = companyRequired;
        this.companyOptional = companyOptional;

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
