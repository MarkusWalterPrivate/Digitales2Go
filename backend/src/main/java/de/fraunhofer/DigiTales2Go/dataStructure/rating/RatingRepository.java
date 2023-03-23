package de.fraunhofer.DigiTales2Go.dataStructure.rating;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
    Optional<Rating> findById(long id);
}
