/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.trend;

import org.springframework.data.repository.CrudRepository;

/**
 * The interface Trend repository.
 *
 * @author Markus Walter
 */
public interface TrendRepository extends CrudRepository<Trend, Long> {
    /**
     * Find by id trend.
     *
     * @param id the id
     * @return the trend
     */
    Trend findById(long id);
}
