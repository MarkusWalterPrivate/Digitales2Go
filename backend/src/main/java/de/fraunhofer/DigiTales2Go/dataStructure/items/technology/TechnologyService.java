package de.fraunhofer.DigiTales2Go.dataStructure.items.technology;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.Bookmark;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.TechCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.TechReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.subclasses.TechOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.subclasses.TechRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.itemLinkServices.ItemLinkRemover;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.itemLinkServices.ItemLinker;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.itemLinkServices.ItemListGenerator;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.ratingLinkServices.RatingLinkRemover;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.RatingRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Technology service.
 */
@Service
@Slf4j
public class TechnologyService {
    /**
     * The Tech rep.
     */
    @Autowired
    TechnologyRepository techRep;
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
     * Create new tech
     *
     * @param requestBody the request body
     * @return the tech return dto
     */
    public TechReturnDTO createNewTech(TechCreationDTO requestBody){
        Technology newTech = new Technology();

        CoreField coreField = new CoreField(requestBody.getCoreField());
        coreField.setType(Type.TECHNOLOGY);
        newTech.setCoreField(coreField);

        DetailedRating detailedRating = new DetailedRating(requestBody.getDetailedRating());
        newTech.setDetailedRating(detailedRating);

        TechRequired required = new TechRequired(requestBody.getTechRequired());
        newTech.setTechRequired(required);

        TechOptional optional = new TechOptional();
        if (requestBody.getTechOptional() != null) {
            optional = new TechOptional(requestBody.getTechOptional());
        }
        newTech.setTechOptional(optional);

        //Add list of Items to Technology for each relation where tech is owning side


        Technology savedTech = techRep.save(setLinkedItems(newTech, requestBody));

        linkTechInOtherItems(savedTech, requestBody);

        itemLibrary.addTechSwipeDTO(savedTech);
        return new TechReturnDTO(savedTech);
    }

    private void linkTechInOtherItems(Technology savedTech, TechCreationDTO requestBody){
        //Add newly created technology as linked  Items to each item that is owning side of tech.
        if (requestBody.getInternalCompanies() != null) {
            itemLinker.linkTechnologyToCompanies(savedTech, requestBody.getInternalCompanies());
        }
        if (requestBody.getInternalTechnologies() != null) {
            itemLinker.linkTechnologyToTechnologies(savedTech, requestBody.getInternalTechnologies());
        }
        if (requestBody.getInternalTrends() != null) {
            itemLinker.linkTechnologyToTrends(savedTech, requestBody.getInternalTrends());
        }
        if (requestBody.getInternalProjects() != null) {
            itemLinker.linkTechnologyToProjects(savedTech, requestBody.getInternalProjects());
        }
    }

    private Technology setLinkedItems(Technology newTech, TechCreationDTO requestBody){
        if (requestBody.getInternalCompanies() != null) {
            newTech.setInternalCompanies(listGenerator.generateCompanyList(requestBody.getInternalCompanies()));
        }
        if (requestBody.getInternalTechnologies() != null) {
            newTech.setInternalTechnologies(listGenerator.generateTechnologyList(requestBody.getInternalTechnologies()));
        }
        if (requestBody.getInternalTrends() != null) {
            newTech.setInternalTrends(listGenerator.generateTrendList(requestBody.getInternalTrends()));
        }
        if (requestBody.getInternalProjects() != null) {
            newTech.setInternalProjects(listGenerator.generateProjectList(requestBody.getInternalProjects()));
        }
        return newTech;
    }

    private void removeOldLinkings(Technology techToUpdate){
        linkRemover.removeTechnologyFromCompanies(techToUpdate);
        linkRemover.removeTechnologyFromProjects(techToUpdate);
        linkRemover.removeTechnologyFromTechnologies(techToUpdate);
        linkRemover.removeTechnologyFromTrends(techToUpdate);
    }

    /**
     * Update tech
     *
     * @param techToUpdate the tech to update
     * @param requestBody  the request body
     * @return the tech return dto
     */
    public TechReturnDTO updateTech(Technology techToUpdate, TechCreationDTO requestBody){
        //Optional first in case it throws an error: no other fields changed
        TechOptional optional = new TechOptional();

        if (requestBody.getTechOptional() != null) {
            optional = new TechOptional(requestBody.getTechOptional());
        }
        optional.setId(techToUpdate.getTechOptional().getId());
        techToUpdate.setTechOptional(optional);


        //Update Rating to latest in case someone voted and transfer id of coreField from old to new
        CoreField newCoreField = new CoreField(requestBody.getCoreField());
        newCoreField.setRating(techToUpdate.getCoreField().getRating());
        newCoreField.setCreationDate(techToUpdate.getCoreField().getCreationDate());
        newCoreField.setId(techToUpdate.getCoreField().getId());
        techToUpdate.setCoreField(newCoreField);

        TechRequired required = new TechRequired(requestBody.getTechRequired());
        required.setId(techToUpdate.getTechRequired().getId());
        techToUpdate.setTechRequired(required);

        DetailedRating detailedRating = new DetailedRating(requestBody.getDetailedRating());
        detailedRating.setId(techToUpdate.getDetailedRating().getId());
        techToUpdate.setDetailedRating(detailedRating);

        removeOldLinkings(techToUpdate);
        Technology savedTech = techRep.save(setLinkedItems(techToUpdate, requestBody));
        linkTechInOtherItems(savedTech, requestBody);

        itemLibrary.updateTechSwipeDTO(savedTech);

        return new TechReturnDTO(savedTech);
    }

    /**
     * Remove tech.
     *
     * @param techToRemove the tech to remove
     */
    public void removeTech(Technology techToRemove){
        removeOldLinkings(techToRemove);
        ratingLinkRemover.unlinkAllRatingsFromUsers(techToRemove.getRatings());
        for (Rating rating : techToRemove.getRatings()) {
            ratingRepository.deleteById(rating.getId());
        }
        List<AppUser> users = (List<AppUser>) userRepository.findAll();
        for (AppUser user: users) {
            List<Bookmark> bookmarks = new ArrayList<>(user.getBookmarkedItems());
            for (Bookmark bookmark: bookmarks) {
                if (bookmark.getItemId()== techToRemove.getId()){
                    user.getBookmarkedItems().remove(bookmark);
                }
            }
        }
        itemLibrary.removeSwipeDTO(techToRemove.getId());
        techRep.deleteById(techToRemove.getId());
    }
}
