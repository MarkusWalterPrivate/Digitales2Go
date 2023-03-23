/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.Bookmark;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Role;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import de.fraunhofer.DigiTales2Go.dataStructure.user.dtos.AppUserCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.user.dtos.AppUserDataChangeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type AppUser.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    @Email()
    @Column(unique = true)
    private String email;
    private String company;
    private String job;
    private String industry;

    private String mandate;
    @JsonIgnore
    private String password;

    private Role role = Role.ROLE_USER;
    @OneToMany
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "user_interest", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "interest")
    private List<String> interests = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_bookmarked",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bookmark_id", referencedColumnName = "id"))
    private List<Bookmark> bookmarkedItems = new ArrayList<>();


    public AppUser(AppUserCreationDTO user) {
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.industry = user.getIndustry().getIndustryName();
        this.lastName = user.getLastName();
        this.company = user.getCompany();
        this.job = user.getJob();
        this.mandate = user.getEmail().substring(user.getEmail().indexOf("@") + 1);
        List<String> newInterests = new ArrayList<>();
        for (Industry industry: user.getInterests()) {
            newInterests.add(industry.getIndustryName());
        }
        this.interests = newInterests;
    }

    public void setUserData(AppUserDataChangeDTO userData) {
        this.firstName = userData.getFirstName();
        this.email = userData.getEmail();
        this.industry = userData.getIndustry().getIndustryName();
        this.lastName = userData.getLastName();
        this.company = userData.getCompany();
        this.job = userData.getJob();
        this.mandate = userData.getEmail().substring(userData.getEmail().indexOf("@") + 1);
        List<String> newInterests = new ArrayList<>();
        for (Industry industry: userData.getInterests()) {
            newInterests.add(industry.getIndustryName());
        }
        this.interests = newInterests;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addBookmarked(Bookmark bookmark) {
        bookmarkedItems.add(bookmark);
    }

    public void removeBookmark(Bookmark bookmark) throws ResponseStatusException {
        if (bookmarkedItems.contains(bookmark)) {
            bookmarkedItems.remove(bookmark);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Bookmark with Id %s not found", bookmark.getId()));
        }
    }
}
