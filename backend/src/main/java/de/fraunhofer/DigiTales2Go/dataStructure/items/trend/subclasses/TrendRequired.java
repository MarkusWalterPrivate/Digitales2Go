package de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses;


import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses.TrendRequiredCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * The type Trend required.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trend_required")
public class TrendRequired {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @Lob
    private String description = "";
    @NotEmpty
    @Lob
    private String discussion = "";


    /**
     * Normal Constructor for Controller etc
     *
     * @param description the description
     * @param discussion  the discussion
     */
    public TrendRequired(String description, String discussion) {
        this.description = description;
        this.discussion = discussion;
    }

    /**
     * Constructor from Creation DTO
     *
     * @param required the required
     */
    public TrendRequired(TrendRequiredCreationDTO required) {
        this.description = required.getDescription();
        this.discussion = required.getDiscussion();
    }
}
