package de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases;

import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.runtime.RuntimeCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Runtime.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "runtimes")
public class Runtime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long start = -1L;
    private Long finished = -1L;
    private boolean useOnlyYear = false;


    /**
     * Instantiates a new Runtime from CreationDTO.
     *
     * @param runtime the runtime
     */
    public Runtime(RuntimeCreationDTO runtime) {
        this.start = runtime.getStart();
        this.finished = runtime.getFinished();
        this.useOnlyYear = runtime.isUseOnlyYear();
    }

    /**
     * Instantiates a new Runtime from editing DTO.
     *
     * @param runtime the runtime
     * @param id      the id
     */
    public Runtime(RuntimeCreationDTO runtime, Long id) {
        this.id = id;
        this.start = runtime.getStart();
        this.finished = runtime.getFinished();
        this.useOnlyYear = runtime.isUseOnlyYear();
    }

    /**
     * Constructor for Demo
     *
     * @param start       the start
     * @param finished    the finished
     * @param useOnlyYear the use only year
     */
    public Runtime(Long start, Long finished, boolean useOnlyYear) {
        this.start = start;
        this.finished = finished;
        this.useOnlyYear = useOnlyYear;
    }
}
