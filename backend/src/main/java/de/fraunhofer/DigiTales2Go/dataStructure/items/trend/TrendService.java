package de.fraunhofer.DigiTales2Go.dataStructure.items.trend;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.Bookmark;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.TrendCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.TrendReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.itemLinkServices.ItemLinkRemover;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.itemLinkServices.ItemLinker;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.itemLinkServices.ItemListGenerator;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.ratingLinkServices.RatingLinkRemover;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.RatingRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Trend service.
 */
@Service
public class TrendService {
    /**
     * The Trend rep.
     */
    @Autowired
    TrendRepository trendRep;
    /**
     * The Link remover.
     */
    @Autowired
    ItemLinkRemover linkRemover;
    /**
     * The List generator.
     */
    @Autowired
    ItemListGenerator listGenerator;
    /**
     * The Item linker.
     */
    @Autowired
    ItemLinker itemLinker;
    /**
     * The Item library.
     */
    @Autowired
    ItemLibrary itemLibrary;
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

    @Autowired
    UserRepository userRepository;

    /**
     * Create new trend
     *
     * @param requestBody the request body
     * @return the trend return dto
     */
    public TrendReturnDTO createNewTrend(TrendCreationDTO requestBody){
        Trend newTrend = new Trend();

        CoreField coreField = new CoreField(requestBody.getCoreField());
        coreField.setType(Type.TREND);
        newTrend.setCoreField(coreField);

        DetailedRating detailedRating = new DetailedRating(requestBody.getDetailedRating());
        newTrend.setDetailedRating(detailedRating);

        TrendRequired required = new TrendRequired(requestBody.getTrendRequired());
        newTrend.setTrendRequired(required);

        TrendOptional optional = new TrendOptional();
        if (requestBody.getTrendOptional() != null) {
            optional = new TrendOptional(requestBody.getTrendOptional());
        }
        newTrend.setTrendOptional(optional);

        Trend savedTrend = trendRep.save(setLinkedItems(newTrend, requestBody));

        linkTrendInOtherItems(savedTrend, requestBody);

        itemLibrary.addTrendSwipeDTO(savedTrend);

        return new TrendReturnDTO(savedTrend);
    }

    private void linkTrendInOtherItems(Trend savedTrend, TrendCreationDTO requestBody){
        //Add newly created trend to each other item
        if (requestBody.getInternalCompanies() != null) {
            itemLinker.linkTrendToCompanies(savedTrend, requestBody.getInternalCompanies());
        }
        if (requestBody.getInternalTrends() != null) {
            itemLinker.linkTrendToTrends(savedTrend, requestBody.getInternalTrends());
        }
        if (requestBody.getInternalTechnologies() != null) {
            itemLinker.linkTrendToTechnologies(savedTrend, requestBody.getInternalTechnologies());
        }
        if (requestBody.getInternalProjects() != null) {
            itemLinker.linkTrendToProjects(savedTrend, requestBody.getInternalProjects());
        }
    }

    private  Trend setLinkedItems (Trend newTrend, TrendCreationDTO requestBody){
        //Add list of Items to trend, since trend is owning partner in all but project relationship, the three lists get crated
        if (requestBody.getInternalCompanies() != null) {
            newTrend.setInternalCompanies(listGenerator.generateCompanyList(requestBody.getInternalCompanies()));
        }
        if (requestBody.getInternalTrends() != null) {
            newTrend.setInternalTrends(listGenerator.generateTrendList(requestBody.getInternalTrends()));
        }
        if (requestBody.getInternalTechnologies() != null) {
            newTrend.setInternalTechnologies(listGenerator.generateTechnologyList(requestBody.getInternalTechnologies()));
        }
        if (requestBody.getInternalProjects() != null) {
            newTrend.setInternalProjects(listGenerator.generateProjectList(requestBody.getInternalProjects()));
        }
        return newTrend;
    }

    private void removeOldLinkings(Trend trendToUpdate){
        linkRemover.removeTrendFromCompanies(trendToUpdate);
        linkRemover.removeTrendFromProjects(trendToUpdate);
        linkRemover.removeTrendFromTechnologies(trendToUpdate);
        linkRemover.removeTrendFromTrends(trendToUpdate);
    }

    /**
     * Update trend
     *
     * @param trendToUpdate the trend to update
     * @param requestBody   the request body
     * @return the trend return dto
     */
    public TrendReturnDTO updateTrend(Trend trendToUpdate, TrendCreationDTO requestBody){
        //Optional first in case it throws an error: no other fields changed
        //Update trendOptional: Check for each field if null and set as empty list/Field if this is the case
        TrendOptional optional = new TrendOptional();
        if (requestBody.getTrendOptional() != null) {
            optional = new TrendOptional(requestBody.getTrendOptional());
        }
        optional.setId(trendToUpdate.getTrendOptional().getId());
        trendToUpdate.setTrendOptional(optional);

        //Update Rating to latest in case someone voted and transfer id of coreField from old to new
        CoreField newCoreField = new CoreField(requestBody.getCoreField());
        newCoreField.setRating(trendToUpdate.getCoreField().getRating());
        newCoreField.setId(trendToUpdate.getCoreField().getId());
        newCoreField.setCreationDate(trendToUpdate.getCoreField().getCreationDate());
        trendToUpdate.setCoreField(newCoreField);

        DetailedRating detailedRating = new DetailedRating(requestBody.getDetailedRating());
        detailedRating.setId(trendToUpdate.getDetailedRating().getId());
        trendToUpdate.setDetailedRating(detailedRating);

        TrendRequired trendRequired = new TrendRequired(requestBody.getTrendRequired());
        trendRequired.setId(trendToUpdate.getTrendRequired().getId());
        trendToUpdate.setTrendRequired(trendRequired);

        removeOldLinkings(trendToUpdate);
        Trend savedTrend = trendRep.save(setLinkedItems(trendToUpdate, requestBody));
        linkTrendInOtherItems(savedTrend, requestBody);

        itemLibrary.updateTrendSwipeDTO(savedTrend);

        return new TrendReturnDTO(savedTrend);
    }

    /**
     * Remove trend.
     *
     * @param trendToRemove the trend to remove
     */
    public void removeTrend(Trend trendToRemove){

        removeOldLinkings(trendToRemove);
        ratingLinkRemover.unlinkAllRatingsFromUsers(trendToRemove.getRatings());
        for (Rating rating : trendToRemove.getRatings()) {
            ratingRepository.deleteById(rating.getId());
        }
        List<AppUser> users = (List<AppUser>) userRepository.findAll();
        for (AppUser user: users) {
            List<Bookmark> bookmarks = new ArrayList<>(user.getBookmarkedItems());
            for (Bookmark bookmark: bookmarks) {
                if (bookmark.getItemId()== trendToRemove.getId()){
                    user.getBookmarkedItems().remove(bookmark);
                }
            }
        }
        itemLibrary.removeSwipeDTO(trendToRemove.getId());
        trendRep.deleteById(trendToRemove.getId());
    }
}
