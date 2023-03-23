package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue;


import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.dto.ReferencedValueCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "referenced_values")
public class ReferencedValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String value = "";
    private int year = -1;
    @Lob
    private String reference = "";


    /**
     * Constructor for creating an empty existing profit
     *
     * @param id the id
     */
    public ReferencedValue(Long id) {
        this.id = id;
    }

    /**
     * Constructor from CreationDTO
     *
     * @param profit the profit
     */
    public ReferencedValue(ReferencedValueCreationDTO profit) {
        this.value = profit.getValue();
        this.year = profit.getYear();
        this.reference = profit.getReference();
    }

    /**
     * Constructor from CreationDTO
     *
     * @param profit the profit
     */
    public ReferencedValue(ReferencedValueCreationDTO profit, Long id) {
        this.id = id;
        this.value = profit.getValue();
        this.year = profit.getYear();
        this.reference = profit.getReference();
    }
}
