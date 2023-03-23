package de.fraunhofer.DigiTales2Go.dataStructure.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookmarks")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long itemId;

    private long creationDate;

    public Bookmark(long itemId, long creationDate) {
        this.itemId = itemId;
        this.creationDate = creationDate;
    }
}
