package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.Data;

@Data
public class CommentReturnDTO {

    private Long id;

    private String content;

    private AppUser user;

    private ItemDTO item;

    public CommentReturnDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = comment.getUser();
        this.item = null;
    }
}
