package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class CommentCreationDTO {

    @NotNull
    @Valid
    private Type type;

    @NotNull
    @Valid
    private String content;

    @NotNull
    @Valid
    private long itemId;

}
