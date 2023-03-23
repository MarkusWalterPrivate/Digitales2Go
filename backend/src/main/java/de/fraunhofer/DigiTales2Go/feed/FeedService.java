package de.fraunhofer.DigiTales2Go.feed;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.Bookmark;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.industrySupers.IndustryGeneralizations;
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
import de.fraunhofer.DigiTales2Go.feed.DTOs.FeedReturnDTO;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * The type Feed service.
 */
@Service
@Slf4j

public class FeedService {
    /**
     * The User rep.
     */
    @Autowired
    UserRepository userRep;
    /**
     * The Item library.
     */
    @Autowired
    ItemLibrary itemLibrary;
    /**
     * The Tech rep.
     */
    @Autowired
    TechnologyRepository techRep;
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
     * The Project rep.
     */
    @Autowired
    ProjectRepository projectRep;

    public FeedReturnDTO paginateList(List<ItemDTO> itemList, Pageable params){
        FeedReturnDTO returnDTO = new FeedReturnDTO();
        params.setTotalItems(itemList.size());
        if (params.getItemsPerPage()<itemList.size()){
            if((params.getItemsPerPage()* (params.getPage()+1))< itemList.size()){
                log.info("page requested");
                // If any pages before the last are requested
                returnDTO.setFeed(itemList.subList(params.getItemsPerPage()* params.getPage(), params.getItemsPerPage()* (params.getPage()+1)));

            } else if ((params.getItemsPerPage()* (params.getPage()))<itemList.size()) {
                log.info("last page requested");
                //last page is requested: If page is full itemPerPage*(page) == itemList.size, else its itemList is smaller;
                returnDTO.setFeed(itemList.subList(params.getItemsPerPage()* params.getPage(), itemList.size()));
            } else {
                log.info("page out of bounds");
                //If more items are requested then available in the feed
                returnDTO.setFeed(new ArrayList<>());
            }
        }else {
            returnDTO.setFeed(itemList);
        }
        returnDTO.setPageable(params);
        return returnDTO;
    }
    /**
     * Sets bookmark and priorRating flag.
     *
     * @param unflaggedFeed the unflagged feed
     * @param user          the user
     * @return the bookmark and priorRating flag
     */
    private List<ItemDTO> setBookmarkAndRatingFlag(List<ItemDTO> unflaggedFeed, AppUser user) {
        if (user != null){
            List<ItemDTO> flaggedFeed = new ArrayList<>();

            log.debug("Generating flags for feed");
            for (ItemDTO item : unflaggedFeed) {
                log.debug("Generating flags for Item {}", item.getId());
                //Due to pointer shenanigans, we have to create a new ItemDTO in order to set the flags by user basis
                ItemDTO itemToReturn = new ItemDTO(item);
                setFlagsOfSingleItem(itemToReturn, user);
                log.debug("Adding flagged item to return feed");
                flaggedFeed.add(itemToReturn);
            }
            return flaggedFeed;
        }else {
            return unflaggedFeed;
        }

    }

    /**
     * Set flags of single item item dto.
     *
     * @param returnItem the return item
     * @param user       the user
     * @return the item dto
     */
    public ItemDTO setFlagsOfSingleItem (ItemDTO returnItem, AppUser user){
        log.info("Checking ratings for Item: {}", returnItem.getId());
        for (Rating rating : user.getRatings()) {
            log.info("Rating checked: {}", rating.getId());
            log.info("Item rated: {}", rating.getItemID());
            if (rating.getItemID().equals(returnItem.getId())) {
                log.info("Rating item id matches item id");
                returnItem.setRated(true);
                returnItem.setPriorRating(rating.getRatingValue());
            }
        }
        log.info("Checking Bookmarks for Item: {}", returnItem.getId());
        for (Bookmark bookmark : user.getBookmarkedItems()) {
            log.info("Bookmark checked: {}", bookmark.getId());
            log.info("Item bookmarked: {}", bookmark.getItemId());
            if (bookmark.getItemId().equals(returnItem.getId())) {
                log.info("Item is bookmarked");
                returnItem.setBookmarked(true);
            }
        }
        return returnItem;
    }

    /**
     * Generate hot feed list.
     *
     * @return the list
     */
    public FeedReturnDTO generateHotFeed(AppUser user, Pageable pageable) {
        log.info("Generating Hot feed");
        List<ItemDTO> unsortedList = new ArrayList<>(itemLibrary.getItemList());
        unsortedList.sort(Comparator.comparing(ItemDTO::getRatingCount));
        Collections.reverse(unsortedList);
        log.info("feed sorted, starting pagination");
        FeedReturnDTO returnDTO = paginateList(unsortedList, pageable);
        log.info("Feed paginated, setting flags");
        returnDTO.setFeed(setBookmarkAndRatingFlag(returnDTO.getFeed(), user));
        return returnDTO;
    }

    /**
     * Generate new feed list.
     *
     * @return the list
     */
    public FeedReturnDTO generateNewFeed(AppUser user, Pageable pageable) {
        List<ItemDTO> unsortedList = new ArrayList<>(itemLibrary.getItemList());
        if (user!=null){
            List<Rating> ratedList = new ArrayList<>(user.getRatings());
            for (Rating rating : ratedList) {
                ItemDTO removeItem = itemLibrary.getItemDTO(rating.getItemID());
                unsortedList.remove(removeItem);
            }
        }
        unsortedList.sort(Comparator.comparing(ItemDTO::getCreationDate));
        Collections.reverse(unsortedList);
        FeedReturnDTO returnDTO = paginateList(unsortedList, pageable);
        if (user != null){
            returnDTO.setFeed(setBookmarkAndRatingFlag(returnDTO.getFeed(), user));
        }
        return returnDTO;
    }

    /**
     * Generate interest feed list.
     *
     * @param user the user
     * @return the list
     */
    public FeedReturnDTO generateInterestFeed(AppUser user, Pageable pageable) {
        List<ItemDTO> unsortedList = new ArrayList<>(itemLibrary.getItemList());
        if (user !=null){
            List<Rating> ratedList = new ArrayList<>(user.getRatings());
            for (Rating rating : ratedList) {
                ItemDTO removeItem = itemLibrary.getItemDTO(rating.getItemID());
                unsortedList.remove(removeItem);
            }
            unsortedList = filterListByInterest(unsortedList, user);
        }
        FeedReturnDTO returnDTO = paginateList(unsortedList, pageable);
        returnDTO.setFeed(setBookmarkAndRatingFlag(returnDTO.getFeed(), user));

        return returnDTO;
    }


    private List<ItemDTO> filterListByInterest(List<ItemDTO> unsortedList, AppUser user) {
        List<ItemDTO> sortedList = new ArrayList<>();

        String workplace = user.getIndustry();
        List<String> parentSector = IndustryGeneralizations.getParentSector(workplace);
        List<String> masterSector = IndustryGeneralizations.getMasterSector(workplace);

        //First we add all Items matching the industry the user works in and remove them from the list of porential items
        sortedList.addAll(unsortedList.stream().filter(item -> (item.getIndustry().equals(workplace))).toList());
        unsortedList.removeAll(unsortedList.stream().filter(item -> (item.getIndustry().equals(workplace))).toList());

        //Then we add all Items matching the interest and remove them from the list of potential items
        for (String interest : user.getInterests()) {
            sortedList.addAll(unsortedList.stream().filter(item -> (item.getIndustry().equals(interest))).toList());
            unsortedList.removeAll(unsortedList.stream().filter(item -> (item.getIndustry().equals(interest))).toList());
        }

        //Lastly, we add all items related to the industry the user works in
        sortedList.addAll(unsortedList.stream().filter(item -> parentSector.contains(item.getIndustry())).toList());
        unsortedList.removeAll(unsortedList.stream().filter(item -> parentSector.contains(item.getIndustry())).toList());

        sortedList.addAll(unsortedList.stream().filter(item -> masterSector.contains(item.getIndustry())).toList());
        unsortedList.removeAll(unsortedList.stream().filter(item -> masterSector.contains(item.getIndustry())).toList());

        return sortedList;
    }

    /**
     * Approve item in db.
     *
     * @param id the id
     */
    public void approveItemInDB (Long id){
        switch (itemLibrary.getItemDTO(id).getCoreField().getType()) {
            case "Technologie":
                Optional<Technology> techToUpdate = techRep.findById(id);
                if (techToUpdate.isPresent()) {
                    techToUpdate.get().approve();
                    techRep.save(techToUpdate.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Technology with Id %s not found. Could not save Rating!", id));
                }
                break;
            case "Trend":
                Optional<Trend> trendToUpdate = trendRep.findById(id);
                if (trendToUpdate.isPresent()) {
                    trendToUpdate.get().approve();
                    trendRep.save(trendToUpdate.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Trend with Id %s not found. Could not save Rating!", id));
                }
                break;
            case "Firma":
                Optional<Company> companyToUpdate = companyRep.findById(id);
                if (companyToUpdate.isPresent()) {
                    companyToUpdate.get().approve();
                    companyRep.save(companyToUpdate.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Company with Id %s not found. Could not save Rating!", id));
                }
                break;
            case "Projekt":
                Optional<Project> projectToUpdate = projectRep.findById(id);
                if (projectToUpdate.isPresent()) {
                    projectToUpdate.get().approve();
                    projectRep.save(projectToUpdate.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Project with Id %s not found. Could not save Rating!", id));
                }
                break;
            default: throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
