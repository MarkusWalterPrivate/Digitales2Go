package de.fraunhofer.DigiTales2Go.dataStructure.items.technology;

import org.springframework.data.repository.CrudRepository;

/**
 * The interface Technology repository.
 *
 * @author Markus Walter
 */
public interface TechnologyRepository extends CrudRepository<Technology, Long> {
    /**
     * Find by id technology.
     *
     * @param id the id
     * @return the technology
     */
    Technology findById(long id);
}
