package de.fraunhofer.DigiTales2Go.feed.DTOs;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import lombok.Data;

import java.util.List;

@Data
public class FeedReturnDTO {
    private Pageable pageable;
    private List<ItemDTO> feed;
}
