/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface AppUser repository.
 *
 * @author Markus Walter
 */
@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {
    /**
     * Find by id user.
     *
     * @param id the id
     * @return the user
     */
    Optional<AppUser> findById(long id);

    Optional<AppUser> findByEmail(String email);
}
