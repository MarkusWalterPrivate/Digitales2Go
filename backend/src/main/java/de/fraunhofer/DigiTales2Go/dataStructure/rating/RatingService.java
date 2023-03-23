package de.fraunhofer.DigiTales2Go.dataStructure.rating;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.RatingEnum;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.CompanyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.ProjectRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.TechnologyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.TrendRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.ratingLinkServices.RatingLinkRemover;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.ratingLinkServices.RatingLinker;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * The type Rating service.
 */
@Service
@Slf4j
public class RatingService {
    /**
     * The Item library.
     */
    @Autowired
    ItemLibrary itemLibrary;
    /**
     * The Rating repository.
     */
    @Autowired
    RatingRepository ratingRepository;
    /**
     * The Tech rep.
     */
    @Autowired
    TechnologyRepository techRep;
    /**
     * The Project rep.
     */
    @Autowired
    ProjectRepository projectRep;
    /**
     * The Trend rep.
     */
    @Autowired
    TrendRepository trendRep;
    /**
     * The Company rep.
     */
    @Autowired
    CompanyRepository companyRep;
    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;
    /**
     * The Rating linker.
     */
    @Autowired
    RatingLinker ratingLinker;
    /**
     * The Rating link remover.
     */
    @Autowired
    RatingLinkRemover ratingLinkRemover;


    /**
     * Update previous ratings boolean.
     *
     * @param id        the itemId
     * @param user      the user
     * @param newRating the new priorRating
     * @return the boolean
     */
    public boolean updatePreviousRatings(Long id, AppUser user, RatingEnum newRating) {
        log.info("Checking old ratings, if item has been rated before");
        for (Rating oldRating : user.getRatings()) {
            log.info("Old priorRating checked");
            if (oldRating.getItemID().equals(id)) {
                log.info("Old priorRating for Item found");
                if (oldRating.getRatingValue().equals(newRating)) {
                    log.info("Rating stays the same, nothing changed");
                    return true;
                }
                switch (oldRating.getRatingValue()) {
                    case LIKE -> {
                        log.info("Was liked");
                        setNewRating(oldRating, newRating, id);
                        updateRatingValueFromItemInDB(id, oldRating.getType(), RatingEnum.DISLIKE);
                        //Remove old priorRating
                        itemLibrary.dislikeSwipeDTO(id);
                    }
                    case DISLIKE -> {
                        log.info("Was Disliked changing value");
                        setNewRating(oldRating, newRating, id);
                        updateRatingValueFromItemInDB(id, oldRating.getType(), RatingEnum.LIKE);
                        //Remove old priorRating
                        itemLibrary.likeSwipeDTO(id);
                    }
                    case SKIP -> {
                        log.info("Was skipped, changing value");
                        setNewRating(oldRating, newRating, id);
                    }
                    default ->
                        log.info("Something is wron, I can feel it");
                }
                log.info("Saving Rating");
                ratingRepository.save(oldRating);
                return true;
            }
        }
        return false;
    }

    private Rating setNewRating(Rating oldRating, RatingEnum newRating, Long id) {
        switch (newRating) {
            case LIKE -> {
                oldRating.setRatingValue(RatingEnum.LIKE);
                updateRatingValueFromItemInDB(id, oldRating.getType(), RatingEnum.LIKE);
                itemLibrary.likeSwipeDTO(id);
            }
            case DISLIKE -> {
                oldRating.setRatingValue(RatingEnum.DISLIKE);
                updateRatingValueFromItemInDB(id, oldRating.getType(), RatingEnum.DISLIKE);
                itemLibrary.dislikeSwipeDTO(id);
            }
            case SKIP ->
                oldRating.setRatingValue(RatingEnum.SKIP);
            default ->
                oldRating.setRatingValue(RatingEnum.NOTSET);

        }
        return oldRating;
    }

    private void updateRatingValueFromItemInDB(Long id, Type type, RatingEnum ratingType) {
        switch (type) {
            case TECHNOLOGY -> {
                log.info("Searching for Tech");
                Optional<Technology> techToUpdate = techRep.findById(id);
                if (techToUpdate.isPresent()) {
                    log.info("Tech found");
                    techRep.save(updateTechRating(techToUpdate.get(), ratingType));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
            }
            case TREND -> {
                log.info("searching for trend");
                Optional<Trend> trendToUpdate = trendRep.findById(id);
                if (trendToUpdate.isPresent()) {
                    log.info("trend found");
                    trendRep.save(updateTrendRating(trendToUpdate.get(), ratingType));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
            }
            case COMPANY -> {
                log.info("searching for company");
                Optional<Company> companyToUpdate = companyRep.findById(id);
                if (companyToUpdate.isPresent()) {
                    log.info("Company found");
                    companyRep.save(updateCompanyRating(companyToUpdate.get(), ratingType));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
            }
            case PROJECT -> {
                log.info("searching for project");
                Optional<Project> projectToUpdate = projectRep.findById(id);
                if (projectToUpdate.isPresent()) {
                    log.info("Project found");
                    projectRep.save(updateProjectRating(projectToUpdate.get(), ratingType));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
            }
            default -> throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    private Technology updateTechRating( Technology techToUpdate, RatingEnum ratingType){
        switch (ratingType) {
            case LIKE ->
                    techToUpdate.like();
            case DISLIKE ->
                    techToUpdate.dislike();
            default -> {/*Nothing to do here*/}
        }
        return techToUpdate;
    }
    private Trend updateTrendRating(Trend trendToUpdate, RatingEnum ratingType){
        switch (ratingType) {
            case LIKE ->
                    trendToUpdate.like();
            case DISLIKE ->
                    trendToUpdate.dislike();
            default -> {/*Nothing to do here*/}
        }
        return trendToUpdate;
    }
    private Company updateCompanyRating(Company companyToUpdate, RatingEnum ratingType){
        switch (ratingType) {
            case LIKE ->
                    companyToUpdate.like();
            case DISLIKE ->
                    companyToUpdate.dislike();
            default -> {/*Nothing to do here*/}
        }
        return companyToUpdate;
    }
    private Project updateProjectRating(Project projectToUpdate, RatingEnum ratingType){
        switch (ratingType) {
            case LIKE ->
                    projectToUpdate.like();
            case DISLIKE ->
                    projectToUpdate.dislike();
            default -> {/*Nothing to do here*/}
        }
        return projectToUpdate;
    }

    /**
     * Create new priorRating.
     *
     * @param id         the itemId
     * @param type       the itemType
     * @param user       the user
     * @param ratingEnum the priorRating enum
     */
    public void createNewRating(Long id, String type, AppUser user, RatingEnum ratingEnum) {
        Rating rating;
        switch (type) {
            case "Technologie":
                log.info("Searching for Tech");
                Optional<Technology> techToUpdate = techRep.findById(id);
                if (techToUpdate.isPresent()) {
                    log.info("Tech found");
                    rating = new Rating(null, user, ratingEnum, Type.TECHNOLOGY, id);
                    ratingRepository.save(rating);
                    techRep.save(ratingLinker.linkRatingToTech(rating, updateTechRating(techToUpdate.get(), ratingEnum)));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                break;
            case "Trend":
                log.info("searching for trend");
                Optional<Trend> trendToUpdate = trendRep.findById(id);
                if (trendToUpdate.isPresent()) {
                    log.info("trend found");
                    rating = new Rating(null, user, ratingEnum, Type.TREND, id);
                    ratingRepository.save(rating);
                    trendRep.save(ratingLinker.linkRatingToTrend(rating, updateTrendRating(trendToUpdate.get(), ratingEnum)));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                break;
            case "Firma":
                log.info("searching for company");
                Optional<Company> companyToUpdate = companyRep.findById(id);
                if (companyToUpdate.isPresent()) {
                    log.info("Company found");
                    rating = new Rating(null, user, ratingEnum, Type.COMPANY, id);
                    ratingRepository.save(rating);
                    companyRep.save(ratingLinker.linkRatingToCompany(rating, updateCompanyRating(companyToUpdate.get(), ratingEnum)));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                break;
            case "Projekt":
                log.info("searching for project");
                Optional<Project> projectToUpdate = projectRep.findById(id);
                if (projectToUpdate.isPresent()) {
                    log.info("Project found");
                    rating = new Rating(null, user, ratingEnum, Type.PROJECT, id);
                    ratingRepository.save(rating);
                    projectRep.save(ratingLinker.linkRatingToProject(rating, updateProjectRating(projectToUpdate.get(), ratingEnum)));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                break;
            default:
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userRepository.save(ratingLinker.linkRatingToUser(rating, user));
    }

    /**
     * Remove priorRating.
     *
     * @param rating   the priorRating
     * @param userMail the user mail
     */
    public void removeRating(Rating rating, String userMail) {
        if (userMail.equals(rating.getUser().getEmail())) {
            ratingLinkRemover.unlinkRatingFromUser(rating, rating.getUser());
            switch (rating.getType()) {
                case TECHNOLOGY:
                    removeRatingFromTech(rating);
                    break;
                case TREND:
                    removeRatingFromTrend(rating);
                    break;
                case COMPANY:
                    removeRatingFromCompany(rating);
                    break;
                case PROJECT:
                    removeRatingFromProject(rating);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    private void removeRatingFromTech(Rating rating){
        Optional<Technology> techToUpdate = techRep.findById(rating.getItemID());
        if (techToUpdate.isPresent()) {
            techRep.save(ratingLinkRemover.unlinkRatingFromTech(rating, techToUpdate.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    private void removeRatingFromTrend(Rating rating){
        Optional<Trend> trendToUpdate = trendRep.findById(rating.getItemID());
        if (trendToUpdate.isPresent()) {
            trendRep.save(ratingLinkRemover.unlinkRatingFromTrend(rating, trendToUpdate.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    private void removeRatingFromCompany(Rating rating){
        Optional<Company> startUpToUpdate = companyRep.findById(rating.getItemID());
        if (startUpToUpdate.isPresent()) {
            companyRep.save(ratingLinkRemover.unlinkRatingFromCompany(rating, startUpToUpdate.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    private void removeRatingFromProject(Rating rating){
        Optional<Project> projectToUpdate = projectRep.findById(rating.getItemID());
        if (projectToUpdate.isPresent()) {
            projectRep.save(ratingLinkRemover.unlinkRatingFromProject(rating, projectToUpdate.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
