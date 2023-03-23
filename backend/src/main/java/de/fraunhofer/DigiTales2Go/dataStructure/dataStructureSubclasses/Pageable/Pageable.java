package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Sorting;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class Pageable {
    private int totalItems =0;
    @Min(1)
    @NotNull
    private int itemsPerPage;
    @Min(0)
    @NotNull
    private int page;

    private Sorting sorting = Sorting.NOTSET;

}
