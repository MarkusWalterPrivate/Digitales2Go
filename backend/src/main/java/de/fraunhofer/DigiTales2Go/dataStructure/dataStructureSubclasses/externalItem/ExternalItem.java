package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "external_items")
public class ExternalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Type type = Type.NOTSET;
    @Lob
    @NotBlank
    private String text;


    public ExternalItem(Type type, String text) {
        this.type = type;
        this.text = text;
    }
}
