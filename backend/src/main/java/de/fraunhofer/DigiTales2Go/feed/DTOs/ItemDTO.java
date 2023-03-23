/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.feed.DTOs;


import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.RatingEnum;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import lombok.Data;

/**
 * The type Item dto.
 *
 * @author Markus Walter
 */
@Data
public class ItemDTO {
    private long id; // Not auto generated since they will be synced with the Technology
    private CoreFieldReturnDTO coreField; //hold all information

    private boolean isBookmarked = false;

    private boolean isRated = false;

    private RatingEnum priorRating = RatingEnum.NOTSET;

    /**
     * Constructor from Technologies
     *
     * @param technology the technology
     */
    public ItemDTO(Technology technology) {
        this.id = technology.getId();
        this.coreField = new CoreFieldReturnDTO(technology.getCoreField());
    }


    /**
     * Constructor from Trend
     *
     * @param trend the trend
     */
    public ItemDTO(Trend trend) {
        this.id = trend.getId();
        this.coreField = new CoreFieldReturnDTO(trend.getCoreField());
    }


    /**
     * Constructor from Company
     *
     * @param company the company
     */
    public ItemDTO(Company company) {
        this.id = company.getId();
        this.coreField = new CoreFieldReturnDTO(company.getCoreField());
    }


    /**
     * Constructor from Project
     *
     * @param project the project
     */
    public ItemDTO(Project project) {
        this.id = project.getId();
        this.coreField = new CoreFieldReturnDTO(project.getCoreField());
    }


    /**
     * Instantiates a new Item dto.
     *
     * @param id          the id
     * @param information the information
     */
    public ItemDTO(long id, CoreField information) {
        this.id = id;
        this.coreField = new CoreFieldReturnDTO(information);
    }

    /**
     * Instantiates a new Item dto.
     *
     * @param itemDTO the item dto
     */
    public ItemDTO(ItemDTO itemDTO) {
        this.coreField = itemDTO.getCoreField();
        this.id = itemDTO.getId();
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Long getCreationDate() {
        return this.coreField.getCreationDate();
    }

    /**
     * Gets priorRating count.
     *
     * @return the priorRating count
     */
    public Double getRatingCount() {
        return this.coreField.getRatingCount();
    }

    /**
     * Gets priorRating.
     *
     * @return the priorRating
     */
    public Double getRating() {
        return this.coreField.getRating();
    }

    /**
     * Gets industry.
     *
     * @return the industry
     */
    public String getIndustry() {
        return this.coreField.getIndustry();
    }

    /**
     * Like.
     */
    public void like() {
        this.coreField.upvote();
    }

    /**
     * Dislike.
     */
    public void dislike() {
        this.coreField.downvote();
    }

}
