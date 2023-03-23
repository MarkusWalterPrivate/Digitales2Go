package de.fraunhofer.DigiTales2Go.dataStructure.user.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.rating.dtos.RatingReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import lombok.Data;

import java.util.List;

@Data
public class RatingFeedDTO {
    Pageable pageable;
    List<RatingReturnDTO> feed;
}
