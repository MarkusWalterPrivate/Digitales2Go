package de.fraunhofer.DigiTales2Go.dataStructure.bookmark.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import lombok.Data;

import java.util.List;

@Data
public class BookmarkFeedDTO {
    Pageable pageable;
    List<BookmarkReturnDTO> feed;
}
