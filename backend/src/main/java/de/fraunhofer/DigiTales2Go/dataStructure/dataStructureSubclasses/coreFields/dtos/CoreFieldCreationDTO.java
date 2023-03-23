package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The type Core field creation dto.
 *
 * @author Markus Walter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CoreFieldCreationDTO {


    @NotNull
    private boolean intern;
    @NotBlank
    private String source;
    @NotEmpty
    private List<String> imageSource;
    @NotBlank
    private String headline;
    @NotBlank
    private String teaser;
    @NotNull
    private Industry industry;
    @NotNull
    private List<String> tags;
    @NotNull
    private Type type;

}
