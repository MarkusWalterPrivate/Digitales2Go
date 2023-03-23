package de.fraunhofer.DigiTales2Go.dataStructure.rating;


import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.RatingEnum;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * The type Rating controller.
 */
@RestController
@Transactional
@Slf4j
public class RatingController {

    /**
     * The Item library.
     */
    @Autowired
    ItemLibrary itemLibrary;
    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;
    /**
     * The Rating repository.
     */
    @Autowired
    RatingRepository ratingRepository;
    /**
     * The Rating service.
     */
    @Autowired
    RatingService ratingService;


    /**
     * Like item item dto.
     *
     * @param id           the id
     * @param loggedInUser the logged in user
     * @return the item dto
     */
    @PostMapping("/swipe/upvote/{id}")
    public void likeItem(@PathVariable("id") long id, @AuthenticationPrincipal User loggedInUser) {

        Optional<AppUser> user = userRepository.findByEmail(loggedInUser.getUsername());
        log.info("User searched: {}", loggedInUser.getUsername());
        if (user.isPresent()) {
            log.info("User is found");
            boolean wasRated = ratingService.updatePreviousRatings(id, user.get(), RatingEnum.LIKE);
            log.info("liking item in Library");
            itemLibrary.likeSwipeDTO(id);
            log.info("Getting Item from library");
            ItemDTO item = itemLibrary.getItemDTO(id);
            log.info("Item is of Type {}", item.getCoreField().getType());
            if (!wasRated) {
                ratingService.createNewRating(id, item.getCoreField().getType(), user.get(), RatingEnum.LIKE);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Dislike item item dto.
     *
     * @param id           the id
     * @param loggedInUser the logged in user
     * @return the item dto
     */
    @PostMapping("/swipe/downvote/{id}")
    public void dislikeItem(@PathVariable("id") long id, @AuthenticationPrincipal User loggedInUser) {

        Optional<AppUser> appUser = userRepository.findByEmail(loggedInUser.getUsername());
        if (appUser.isPresent()) {
            boolean wasRated = ratingService.updatePreviousRatings(id, appUser.get(), RatingEnum.DISLIKE);
            log.info("User found");
            itemLibrary.dislikeSwipeDTO(id);
            ItemDTO item = itemLibrary.getItemDTO(id);
            if (!wasRated) {
                ratingService.createNewRating(id, item.getCoreField().getType(), appUser.get(), RatingEnum.DISLIKE);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Ignore item.
     *
     * @param id           the id
     * @param loggedInUser the logged in user
     */
    @PostMapping("/swipe/ignore/{id}")
    public void ignoreItem(@PathVariable("id") long id, @AuthenticationPrincipal User loggedInUser) {

        Optional<AppUser> user = userRepository.findByEmail(loggedInUser.getUsername());
        if (user.isPresent()) {
            boolean wasRated = ratingService.updatePreviousRatings(id, user.get(), RatingEnum.SKIP);
            ItemDTO item = itemLibrary.getItemDTO(id);
            if (!wasRated) {
                ratingService.createNewRating(id, item.getCoreField().getType(), user.get(), RatingEnum.SKIP);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Delete priorRating.
     *
     * @param id           the id
     * @param loggedInUser the logged in user
     */
    @DeleteMapping("/swipe/delete/{id}")
    public void deleteRating(@PathVariable("id") long id, @AuthenticationPrincipal User loggedInUser) {

        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            ratingService.removeRating(rating.get(), loggedInUser.getUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
