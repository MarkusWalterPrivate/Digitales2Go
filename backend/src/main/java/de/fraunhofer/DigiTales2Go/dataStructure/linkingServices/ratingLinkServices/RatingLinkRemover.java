package de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.ratingLinkServices;

import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.CompanyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.ProjectRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.TechnologyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.TrendRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

/**
 * The type Rating link remover.
 */
@Service
public class RatingLinkRemover {
    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;
    /**
     * The Technology repository.
     */
    @Autowired
    TechnologyRepository technologyRepository;
    /**
     * The Trend repository.
     */
    @Autowired
    TrendRepository trendRepository;
    /**
     * The Project repository.
     */
    @Autowired
    ProjectRepository projectRepository;
    /**
     * The Company repository.
     */
    @Autowired
    CompanyRepository companyRepository;

    /**
     * Unlink priorRating from user app user.
     *
     * @param rating the priorRating
     * @param user   the user
     * @return the app user
     */
    public AppUser unlinkRatingFromUser(Rating rating, AppUser user) {
        Set<Rating> userRatings = user.getRatings();
        userRatings.remove(rating);
        user.setRatings(userRatings);
        return user;
    }

    /**
     * Unlink priorRating from tech technology.
     *
     * @param rating     the priorRating
     * @param technology the technology
     * @return the technology
     */
    public Technology unlinkRatingFromTech(Rating rating, Technology technology) {
        Set<Rating> techRatings = technology.getRatings();
        techRatings.remove(rating);
        technology.setRatings(techRatings);
        return technology;
    }

    /**
     * Unlink priorRating from trend trend.
     *
     * @param rating the priorRating
     * @param trend  the trend
     * @return the trend
     */
    public Trend unlinkRatingFromTrend(Rating rating, Trend trend) {
        Set<Rating> trendRatings = trend.getRatings();
        trendRatings.remove(rating);
        trend.setRatings(trendRatings);
        return trend;
    }

    /**
     * Unlink priorRating from company company.
     *
     * @param rating  the priorRating
     * @param company the company
     * @return the company
     */
    public Company unlinkRatingFromCompany(Rating rating, Company company) {
        Set<Rating> companyRatings = company.getRatings();
        companyRatings.remove(rating);
        company.setRatings(companyRatings);
        return company;
    }

    /**
     * Unlink priorRating from project project.
     *
     * @param rating  the priorRating
     * @param project the project
     * @return the project
     */
    public Project unlinkRatingFromProject(Rating rating, Project project) {
        Set<Rating> projectRatings = project.getRatings();
        projectRatings.remove(rating);
        project.setRatings(projectRatings);
        return project;
    }

    /**
     * Unlink all ratings from users.
     *
     * @param ratings the ratings
     */
    public void unlinkAllRatingsFromUsers(Set<Rating> ratings) {
        for (Rating rating : ratings) {
            AppUser user = rating.getUser();
            userRepository.save(unlinkRatingFromUser(rating, user));
        }
    }

    /**
     * Unlink all ratings from items.
     *
     * @param ratings the ratings
     */
    public void unlinkAllRatingsFromItems(Set<Rating> ratings) {
        for (Rating rating : ratings) {
            switch (rating.getType().getType()) {
                case "Technology":
                    unlinkTech(rating);
                    break;
                case "Trend":
                    unlinkTrend(rating);
                    break;
                case "Company":
                    unlinkCompany(rating);
                    break;
                case "Project":
                    unlinkProject(rating);
                    break;
                default: throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void unlinkTech(Rating rating){
        Optional<Technology> techToUpdate = technologyRepository.findById(rating.getItemID());
        if (techToUpdate.isPresent()) {
            technologyRepository.save(unlinkRatingFromTech(rating, techToUpdate.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Tech with Id %s not found. Could not update Object belonging to Rating", rating.getItemID()));
        }
    }

    private void unlinkTrend(Rating rating){
        Optional<Trend> trendToUpdate = trendRepository.findById(rating.getItemID());
        if (trendToUpdate.isPresent()) {
            trendRepository.save(unlinkRatingFromTrend(rating, trendToUpdate.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Trend with Id %s not found. Could not update Object belonging to Rating", rating.getItemID()));
        }
    }
    private void unlinkCompany(Rating rating){
        Optional<Company> startUpToUpdate = companyRepository.findById(rating.getItemID());
        if (startUpToUpdate.isPresent()) {
            companyRepository.save(unlinkRatingFromCompany(rating, startUpToUpdate.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Company with Id %s not found. Could not update Object belonging to Rating", rating.getItemID()));
        }
    }

    private void unlinkProject(Rating rating){
        Optional<Project> projectToUpdate = projectRepository.findById(rating.getItemID());
        if (projectToUpdate.isPresent()) {
            projectRepository.save(unlinkRatingFromProject(rating, projectToUpdate.get()));

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Project with Id %s not found. Could not update Object belonging to Rating", rating.getItemID()));
        }
    }
}
