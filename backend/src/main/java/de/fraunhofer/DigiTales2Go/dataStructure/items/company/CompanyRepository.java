/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.company;

import org.springframework.data.repository.CrudRepository;

/**
 * The interface Company repository.
 *
 * @author Markus Walter
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {
    /**
     * Find by id company.
     *
     * @param id the id
     * @return the company
     */
    Company findById(long id);
}
