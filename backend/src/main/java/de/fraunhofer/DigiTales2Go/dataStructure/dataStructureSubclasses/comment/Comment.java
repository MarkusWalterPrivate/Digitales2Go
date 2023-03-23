package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class for comments, not yet implemented
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String content;

    @ManyToOne
    private AppUser user;

    @JsonIgnore
    private Type type;

    private Long itemID;
}
