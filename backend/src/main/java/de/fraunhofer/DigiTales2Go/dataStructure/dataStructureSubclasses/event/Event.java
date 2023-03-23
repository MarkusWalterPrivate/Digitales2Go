package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type Event.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Long date;
    @NotBlank
    @Lob
    private String location;
    @NotBlank
    @Lob
    private String description;
    @Lob
    private String imageSource = "";
    private String website = "";


    /**
     * Instantiates a new Event for controller.
     *
     * @param date        the date
     * @param location    the location
     * @param description the description
     * @param imageSource the image source
     * @param website     the website
     */
    public Event(Long date, String location, String description, String imageSource, String website) {
        this.date = date;
        this.location = location;
        this.description = description;
        this.imageSource = imageSource;
        this.website = website;
    }

}
