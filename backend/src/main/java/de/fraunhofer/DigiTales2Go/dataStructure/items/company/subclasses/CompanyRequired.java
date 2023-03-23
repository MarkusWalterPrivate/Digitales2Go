package de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.CompanyRequiredCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.requiredSubclasses.TeamSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Company required.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company_required")
public class CompanyRequired {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private String description;
    @Lob
    private String useCases;
    @OneToOne(cascade = CascadeType.ALL)
    private TeamSize teamSize;
    private String website;
    private int foundationYear;


    /**
     * Constructor for Controller
     *
     * @param description    the description
     * @param useCases       the use cases
     * @param teamSize       the team size
     * @param website        the website
     * @param foundationYear the foundation year
     */
    public CompanyRequired(String description, String useCases, TeamSize teamSize, String website, int foundationYear) {
        this.description = description;
        this.useCases = useCases;
        this.teamSize = teamSize;
        this.website = website;
        this.foundationYear = foundationYear;
    }

    /**
     * Constructor from Creation DTO
     *
     * @param required the required
     */
    public CompanyRequired(CompanyRequiredCreationDTO required) {
        this.description = required.getDescription();
        this.useCases = required.getUseCases();
        this.teamSize = new TeamSize(required.getTeamSize());
        this.website = required.getWebsite();
        this.foundationYear = required.getFoundationYear();
    }

    /**
     * Constructor from Editing DTO
     *
     * @param required   the required
     * @param teamSizeId the team size id
     */
    public CompanyRequired(CompanyRequiredCreationDTO required, Long teamSizeId) {
        this.description = required.getDescription();
        this.useCases = required.getUseCases();
        this.teamSize = new TeamSize(required.getTeamSize(), teamSizeId);
        this.website = required.getWebsite();
        this.foundationYear = required.getFoundationYear();
    }
}
