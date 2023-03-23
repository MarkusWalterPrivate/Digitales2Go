package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Detailed priorRating.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detailed_ratings")
public class DetailedRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double degreeOfInnovation;

    private double disruptionPotential;

    private double useCases;


    /**
     * Instantiates a new Detailed priorRating.
     *
     * @param degreeOfInnovation  the degree of innovation
     * @param disruptionPotential the disruption potential
     * @param useCases            the use cases
     */
    public DetailedRating(double degreeOfInnovation, double disruptionPotential, double useCases) {
        this.degreeOfInnovation = degreeOfInnovation;
        this.disruptionPotential = disruptionPotential;
        this.useCases = useCases;
    }

    /**
     * Instantiates a new Detailed priorRating.
     *
     * @param detailedRating the detailed priorRating
     */
    public DetailedRating(DetailedRatingCreationDTO detailedRating) {
        this.degreeOfInnovation = detailedRating.getDegreeOfInnovation();
        this.disruptionPotential = detailedRating.getDisruptionPotential();
        this.useCases = detailedRating.getUseCases();
    }
}
