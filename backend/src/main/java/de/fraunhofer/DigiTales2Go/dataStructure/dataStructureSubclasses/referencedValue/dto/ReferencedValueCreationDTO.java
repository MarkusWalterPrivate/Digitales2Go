package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Referenced value creation dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferencedValueCreationDTO {

    private String value = "";

    private int year = -1;

    private String reference = "";

}
