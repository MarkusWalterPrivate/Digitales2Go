package de.fraunhofer.DigiTales2Go.dataStructure.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.RatingEnum;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private AppUser user;

    @Enumerated
    private RatingEnum ratingValue;

    @JsonIgnore
    private Type type;

    private Long itemID;

}
