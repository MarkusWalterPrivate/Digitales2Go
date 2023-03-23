package de.fraunhofer.DigiTales2Go.dataStructure.bookmark;

import org.springframework.data.repository.CrudRepository;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    Bookmark findById(long id);
}
