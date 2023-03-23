package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The type Core field.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "core_fields")
public class CoreField {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long lastUpdated;
    private Long creationDate;
    private Double rating;
    @JsonIgnore
    private Double ratingCount;
    private boolean intern;
    @Lob
    private String source;
    @Lob
    private String imageSource;
    @Lob
    private String headline;

    @Lob
    private String teaser;
    private Industry industry;
    @ElementCollection(fetch = FetchType.EAGER) // 1
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "tag") //
    private List<String> tags;
    private Type type;

    private boolean isApproved = false;


    /**
     * Constructor for Demos
     *
     * @param intern      is the source intern
     * @param source      the source
     * @param imageSource the image source
     * @param headline    the headline
     * @param teaser      the teaser
     * @param industry    the industry
     * @param tags        the tags
     * @param type        the type
     */
    public CoreField(boolean intern, String source, String imageSource, String headline, String teaser, Industry industry, List<String> tags, Type type) {
        Date date = new Date();
        this.lastUpdated = date.getTime();
        this.creationDate = date.getTime();
        this.rating = 0.0;
        this.ratingCount = 0.0;
        this.intern = intern;
        this.source = source;
        this.imageSource = imageSource;
        this.headline = headline;
        this.teaser = teaser;
        this.industry = industry;
        this.tags = tags;
        this.type = type;
    }

    /**
     * Constructor from Creation DTO
     *
     * @param coreField the core field
     */
    public CoreField(CoreFieldCreationDTO coreField) {
        Date date = new Date();
        this.lastUpdated = date.getTime();
        this.creationDate = date.getTime();
        this.intern = coreField.isIntern();
        this.source = coreField.getSource();
        this.rating = 0.0;
        this.ratingCount = 0.0;
        this.imageSource = String.join("|",coreField.getImageSource());
        this.headline = coreField.getHeadline();
        this.teaser = coreField.getTeaser();
        this.industry = coreField.getIndustry();
        this.tags = coreField.getTags();
        this.type = coreField.getType();
    }


    /**
     * Upvote.
     */
    public void upvote() {
        this.rating++;
        this.ratingCount++;
    }

    /**
     * Downvote.
     */
    public void downvote() {
        this.rating--;
        this.ratingCount++;
    }

    public void approve() {
        this.isApproved = true;
    }

}
