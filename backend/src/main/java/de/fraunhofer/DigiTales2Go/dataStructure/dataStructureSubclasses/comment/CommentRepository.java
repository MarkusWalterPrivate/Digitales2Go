package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<Comment> findById(long id);
}
