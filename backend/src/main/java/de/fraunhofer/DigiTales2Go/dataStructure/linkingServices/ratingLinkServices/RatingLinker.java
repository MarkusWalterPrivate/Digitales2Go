package de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.ratingLinkServices;

import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * The type Rating linker.
 */
@Service
public class RatingLinker {

    /**
     * Link priorRating to user app user.
     *
     * @param rating the priorRating
     * @param user   the user
     * @return the app user
     */
    public AppUser linkRatingToUser(Rating rating, AppUser user) {
        Set<Rating> userRatings = user.getRatings();
        userRatings.add(rating);
        user.setRatings(userRatings);
        return user;
    }

    /**
     * Link priorRating to tech technology.
     *
     * @param rating     the priorRating
     * @param technology the technology
     * @return the technology
     */
    public Technology linkRatingToTech(Rating rating, Technology technology) {
        Set<Rating> techRatings = technology.getRatings();
        techRatings.add(rating);
        technology.setRatings(techRatings);
        return technology;
    }

    /**
     * Link priorRating to trend trend.
     *
     * @param rating the priorRating
     * @param trend  the trend
     * @return the trend
     */
    public Trend linkRatingToTrend(Rating rating, Trend trend) {
        Set<Rating> trendRatings = trend.getRatings();
        trendRatings.add(rating);
        trend.setRatings(trendRatings);
        return trend;
    }

    /**
     * Link priorRating to company company.
     *
     * @param rating  the priorRating
     * @param company the company
     * @return the company
     */
    public Company linkRatingToCompany(Rating rating, Company company) {
        Set<Rating> companyRatings = company.getRatings();
        companyRatings.add(rating);
        company.setRatings(companyRatings);
        return company;
    }

    /**
     * Link priorRating to project project.
     *
     * @param rating  the priorRating
     * @param project the project
     * @return the project
     */
    public Project linkRatingToProject(Rating rating, Project project) {
        Set<Rating> projectRatings = project.getRatings();
        projectRatings.add(rating);
        project.setRatings(projectRatings);
        return project;
    }
}
