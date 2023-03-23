package de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.requiredSubclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.TeamSizeEnum;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.required.TeamSizeCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Team size.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teamsizes")
public class TeamSize {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TeamSizeEnum teamSize;
    private int year;
    private String reference;


    /**
     * Constructor for Demo
     *
     * @param teamSize  the team size
     * @param year      the year
     * @param reference the reference
     */
    public TeamSize(TeamSizeEnum teamSize, int year, String reference) {
        this.teamSize = teamSize;
        this.year = year;
        this.reference = reference;
    }

    /**
     * Constructor for Creation
     *
     * @param teamSize the team size
     */
    public TeamSize(TeamSizeCreationDTO teamSize) {
        this.teamSize = teamSize.getTeamSize();
        this.year = teamSize.getYear();
        this.reference = teamSize.getReference();
    }

    /**
     * Constructor for Creation
     *
     * @param teamSize the team size
     * @param id       the id
     */
    public TeamSize(TeamSizeCreationDTO teamSize, Long id) {
        this.id = id;
        this.teamSize = teamSize.getTeamSize();
        this.year = teamSize.getYear();
        this.reference = teamSize.getReference();
    }
}
