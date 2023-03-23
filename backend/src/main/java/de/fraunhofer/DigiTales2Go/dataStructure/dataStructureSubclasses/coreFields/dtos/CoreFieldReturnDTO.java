package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;

@Data
public class CoreFieldReturnDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long lastUpdated;
    private Long creationDate;

    private boolean intern;
    private String source;
    private Double rating;
    @JsonIgnore
    private Double ratingCount;
    private List<String> imageSource;

    private String headline;

    private String teaser;

    private String industry;

    private List<String> tags;

    private String type;
    @JsonIgnore
    private boolean approved;

    /**
     * empty default constructor is necessary for JPA
     */
    public CoreFieldReturnDTO(CoreField coreField) {
        this.id = coreField.getId();
        this.creationDate = coreField.getCreationDate();
        this.lastUpdated = coreField.getLastUpdated();
        this.rating = coreField.getRating();
        this.ratingCount = coreField.getRatingCount();
        this.intern = coreField.isIntern();
        this.source = coreField.getSource();
        this.imageSource = Arrays.asList(coreField.getImageSource().split("\\|"));
        this.headline = coreField.getHeadline();
        this.teaser = coreField.getTeaser();
        this.industry = coreField.getIndustry().getIndustryName();
        this.tags = coreField.getTags();
        this.type = coreField.getType().getType();
        this.approved = coreField.isApproved();
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

}
