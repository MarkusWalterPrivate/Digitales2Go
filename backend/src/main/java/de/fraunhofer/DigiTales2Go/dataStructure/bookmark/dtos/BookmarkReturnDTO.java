package de.fraunhofer.DigiTales2Go.dataStructure.bookmark.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.Bookmark;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.Data;

@Data
public class BookmarkReturnDTO {

    private Long id;

    private ItemDTO item;

    private long creationDate;

    public BookmarkReturnDTO(Bookmark bookmark) {
        this.id = bookmark.getId();
        this.creationDate = bookmark.getCreationDate();
    }

    public long getItemCreationDate() {
        return item.getCreationDate();
    }

    public double getRating() {
        return item.getRating();
    }
}
