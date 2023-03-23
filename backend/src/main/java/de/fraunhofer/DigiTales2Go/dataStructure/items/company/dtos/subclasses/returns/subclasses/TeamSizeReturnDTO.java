package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns.subclasses;


import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.requiredSubclasses.TeamSize;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Team size return dto.
 */
@Data
@NoArgsConstructor
public class TeamSizeReturnDTO {

    private Long id;

    private String teamSize;

    private int year;

    private String reference;


    /**
     * Instantiates a new Team size return dto.
     *
     * @param teamSize the team size
     */
    public TeamSizeReturnDTO(TeamSize teamSize) {
        this.id = teamSize.getId();
        this.teamSize = teamSize.getTeamSize().getSize();
        this.year = teamSize.getYear();
        this.reference = teamSize.getReference();
    }

}
