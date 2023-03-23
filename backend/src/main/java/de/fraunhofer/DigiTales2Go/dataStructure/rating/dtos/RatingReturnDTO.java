package de.fraunhofer.DigiTales2Go.dataStructure.rating.dtos;


import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.RatingEnum;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.Data;

@Data
public class RatingReturnDTO {

    private Long id;

    private RatingEnum ratingValue;

    private ItemDTO item;

    public RatingReturnDTO(Rating rating) {
        this.id = rating.getId();
        this.ratingValue = rating.getRatingValue();
    }
}


