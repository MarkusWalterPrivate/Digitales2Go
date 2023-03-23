package de.fraunhofer.DigiTales2Go.dataStructure.user;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.Bookmark;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.ratingLinkServices.RatingLinkRemover;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.RatingRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.dtos.RatingReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.user.dtos.AppUserDataChangeDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.user.dtos.RatingFeedDTO;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * The type User service.
 */
@Service
@Slf4j
public class UserService {

    @Value("${mail.username}")
    String username;
    @Value("${mail.password}")
    String password;
    @Value("${mail.address}")
    String senderAddress;
    @Value("${mail.smtp.host}")
    String smtpHost;
    @Value("${mail.smtp.port}")
    String smtpPort;

    /**
     * The Item library.
     */
    @Autowired
    ItemLibrary itemLibrary;
    /**
     * The User rep.
     */
    @Autowired
    UserRepository userRep;
    /**
     * The Rating link remover.
     */
    @Autowired
    RatingLinkRemover ratingLinkRemover;
    /**
     * The Rating repository.
     */
    @Autowired
    RatingRepository ratingRepository;

    /**
     * Generate priorRating return list set.
     *
     * @param ratings the ratings
     * @return the set
     */
    public Set<RatingReturnDTO> generateRatingReturnList(Set<Rating> ratings) {
        log.debug("Rating amounts: {}", ratings.size());
        Set<RatingReturnDTO> ratingsToReturn = new HashSet<>();
        for (Rating rating : ratings) {
            RatingReturnDTO newRating = new RatingReturnDTO(rating);
            newRating.setItem(itemLibrary.getItemDTO(rating.getItemID()));
            ratingsToReturn.add(newRating);
        }
        return ratingsToReturn;
    }

    public RatingFeedDTO paginateRatings(Set<Rating> ratingsSet, Pageable params, AppUser user){
        List<Rating> ratings = new ArrayList<>();
        for (Rating rating : ratingsSet) {
            ratings.add(rating);
        }
        RatingFeedDTO returnDTO =new RatingFeedDTO();
        params.setTotalItems(ratings.size());
        if (params.getItemsPerPage()<ratings.size()){
            if((params.getItemsPerPage()* (params.getPage()+1))< ratings.size()){
                log.info("page requested");
                // If any pages before the last are requested
                ratings=ratings.subList(params.getItemsPerPage()* params.getPage(), params.getItemsPerPage()* (params.getPage()+1));

            } else if ((params.getItemsPerPage()* (params.getPage()))>ratings.size()) {
                log.info("last page requested");
                //last page is requested: If page is full itemPerPage*(page) == itemList.size, else its itemList is smaller;
                ratings=ratings.subList(params.getItemsPerPage()* params.getPage(), ratings.size());
            } else {
                log.info("page out of bounds");
                //If more items are requested then available in the feed
                ratings=new ArrayList<>();
            }
        }
        List<RatingReturnDTO> returnFeed= new ArrayList<>();
        for (Rating rating:ratings) {
            returnFeed.add(setFlagsOfSingleItem(rating, user));
        }
        returnDTO.setFeed(returnFeed);

        returnDTO.setPageable(params);
        return returnDTO;
    }
    private RatingReturnDTO setFlagsOfSingleItem (Rating rating, AppUser user){
        RatingReturnDTO returnDTO = new RatingReturnDTO(rating);
        ItemDTO returnItem = new ItemDTO(itemLibrary.getItemDTO(rating.getItemID()));
        returnItem.setRated(true);
        returnItem.setPriorRating(rating.getRatingValue());
        for (Bookmark bookmark : user.getBookmarkedItems()) {
            if (bookmark.getItemId().equals(returnItem.getId())) {
                returnItem.setBookmarked(true);
            }
        }
        returnDTO.setItem(returnItem);
        return returnDTO;
    }

    /**
     * Update user app user.
     *
     * @param appUserToUpdate the app user to update
     * @param requestBody     the request body
     * @return the app user
     */
    public AppUser updateUser(AppUser appUserToUpdate, AppUserDataChangeDTO requestBody) {
        //Check if another User already uses this email
        Optional<AppUser> user = userRep.findByEmail(requestBody.getEmail());
        if (user.isPresent() && !Objects.equals(appUserToUpdate.getEmail(), requestBody.getEmail())) {
            log.error("user with new email already exists");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        appUserToUpdate.setUserData(requestBody);
        log.info("Updating existing User: {}", appUserToUpdate.getEmail());
        return userRep.save(appUserToUpdate);
    }

    /**
     * Delete user.
     *
     * @param user the user
     */
    public void deleteUser(AppUser user) {
        ratingLinkRemover.unlinkAllRatingsFromItems(user.getRatings());
        for (Rating rating : user.getRatings()) {
            ratingRepository.deleteById(rating.getId());
        }
        userRep.deleteById(user.getId());
    }

    //Reused Code from Markus Walter PE2 Project.
    public boolean sendMail(String recipientAddress, String text) {

        // instantiate SMTP properties (taken from
        // https://www.tik.uni-stuttgart.de/en/support/service-manuals/e-mail/)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // get a Session object with the properties
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // create a default MimeMessage object and fill its attributes
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderAddress));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientAddress));
            message.setSubject("Willkommen bei DigiTales2Go!");
            message.setText(text);

            // send message
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            return false;

        }
    }

}
