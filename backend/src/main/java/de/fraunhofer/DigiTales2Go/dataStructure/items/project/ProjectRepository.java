/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.project;

import org.springframework.data.repository.CrudRepository;

/**
 * The interface Project repository.
 *
 * @author Markus Walter
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {
    /**
     * Find by id project.
     *
     * @param id the id
     * @return the project
     */
    Project findById(long id);
}
