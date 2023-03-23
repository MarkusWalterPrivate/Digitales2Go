package de.fraunhofer.DigiTales2Go.dataStructure.items.company;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.Bookmark;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.CompanyCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.CompanyReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.CompanyOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.CompanyRequired;
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
 * The type Company service.
 */
@Service
@Slf4j
public class CompanyService {
    /**
     * The Company repository.
     */
    @Autowired
    CompanyRepository companyRepository;
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
     * Create new company
     *
     * @param requestBody the request body
     * @return the company return dto
     */
    public CompanyReturnDTO createNewCompany(CompanyCreationDTO requestBody){
        Company newCompany = new Company();

        CoreField coreField = new CoreField(requestBody.getCoreField());
        coreField.setType(Type.COMPANY);
        newCompany.setCoreField(coreField);

        DetailedRating detailedRating = new DetailedRating(requestBody.getDetailedRating());
        newCompany.setDetailedRating(detailedRating);

        CompanyRequired required = new CompanyRequired(requestBody.getCompanyRequired());
        newCompany.setCompanyRequired(required);

        CompanyOptional options2Update = new CompanyOptional();
        if (requestBody.getCompanyOptional() != null) {
            options2Update = new CompanyOptional(requestBody.getCompanyOptional());
        }
        newCompany.setCompanyOptional(options2Update);



        Company savedCompany = companyRepository.save(setLinkedItems(newCompany, requestBody));
        linkCompanyInOtherItems(savedCompany, requestBody);

        itemLibrary.addCompanySwipeDTO(savedCompany);

        return new CompanyReturnDTO(savedCompany);
    }

    private void linkCompanyInOtherItems(Company savedCompany, CompanyCreationDTO requestBody){
        //Add newly created company as linked  Items to each item with owning side of relationship
        if (requestBody.getInternalCompanies() != null) {
            itemLinker.linkCompanyToCompanies(savedCompany, requestBody.getInternalCompanies());
        }
        if (requestBody.getInternalProjects() != null) {
            itemLinker.linkCompanyToProjects(savedCompany, requestBody.getInternalProjects());
        }
        if (requestBody.getInternalTrends() != null) {
            itemLinker.linkCompanyToTrends(savedCompany, requestBody.getInternalTrends());
        }
        if (requestBody.getInternalTechnologies() != null) {
            itemLinker.linkCompanyToTechnologies(savedCompany, requestBody.getInternalTechnologies());
        }
    }

    private  Company setLinkedItems (Company newCompany, CompanyCreationDTO requestBody){
        //Add list of linked companies to company
        if (requestBody.getInternalCompanies() != null) {
            newCompany.setInternalCompanies(listGenerator.generateCompanyList(requestBody.getInternalCompanies()));
        }
        if (requestBody.getInternalProjects() != null) {
            newCompany.setInternalProjects(listGenerator.generateProjectList(requestBody.getInternalProjects()));
        }
        if (requestBody.getInternalTrends() != null) {
            newCompany.setInternalTrends(listGenerator.generateTrendList(requestBody.getInternalTrends()));
        }
        if (requestBody.getInternalTechnologies() != null) {
            newCompany.setInternalTechnologies(listGenerator.generateTechnologyList(requestBody.getInternalTechnologies()));
        }
        return newCompany;
    }

    private void removeOldLinkings(Company companyToUpdate){
        linkRemover.removeCompanyFromCompanies(companyToUpdate);
        linkRemover.removeCompanyFromProjects(companyToUpdate);
        linkRemover.removeCompanyFromTechnologies(companyToUpdate);
        linkRemover.removeCompanyFromTrends(companyToUpdate);
    }

    /**
     * Update company
     *
     * @param companyToUpdate the company to update
     * @param requestBody     the request body
     * @return the company return dto
     */
    public CompanyReturnDTO updateCompany(Company companyToUpdate, CompanyCreationDTO requestBody){
        //Update Rating to latest in case someone voted and transfer id of coreField from old to new
        CoreField newCoreField = new CoreField(requestBody.getCoreField());
        newCoreField.setRating(companyToUpdate.getCoreField().getRating());
        newCoreField.setCreationDate(companyToUpdate.getCoreField().getCreationDate());
        newCoreField.setId(companyToUpdate.getCoreField().getId());
        companyToUpdate.setCoreField(newCoreField);

        DetailedRating detailedRating = new DetailedRating(requestBody.getDetailedRating());
        detailedRating.setId(companyToUpdate.getDetailedRating().getId());
        companyToUpdate.setDetailedRating(detailedRating);

        //transfer Id of companyRequired
        CompanyRequired companyRequired = new CompanyRequired(requestBody.getCompanyRequired(), companyToUpdate.getCompanyRequired().getTeamSize().getId());
        companyRequired.setId(companyToUpdate.getCompanyRequired().getId());
        companyToUpdate.setCompanyRequired(companyRequired);

        CompanyOptional options2Update;

        //used to update ids for CompanyOptionalFields
        Long[] fieldIds = new Long[5];
        fieldIds[0] = companyToUpdate.getCompanyOptional().getCompanyOptionalFields().getId();
        fieldIds[1] = companyToUpdate.getCompanyOptional().getCompanyOptionalFields().getLocation().getId();
        fieldIds[2] = companyToUpdate.getCompanyOptional().getCompanyOptionalFields().getNumberOfCustomers().getId();
        fieldIds[3] = companyToUpdate.getCompanyOptional().getCompanyOptionalFields().getRevenue().getId();
        fieldIds[4] = companyToUpdate.getCompanyOptional().getCompanyOptionalFields().getProfit().getId();

        if (requestBody.getCompanyOptional() != null) {
            options2Update = new CompanyOptional(requestBody.getCompanyOptional(), fieldIds,
                    companyToUpdate.getCompanyOptional().getCompanyOptionalLists().getId());
            options2Update.setId(companyToUpdate.getCompanyOptional().getId());

            //Update Company projects, keep id
        } else {
            //Create new Optional fields, but keep ids
            options2Update = new CompanyOptional(fieldIds, companyToUpdate.getCompanyOptional().getCompanyOptionalLists().getId());
            options2Update.setId(companyToUpdate.getCompanyOptional().getId());
        }
        companyToUpdate.setCompanyOptional(options2Update);

        removeOldLinkings(companyToUpdate);
        Company savedCompany = companyRepository.save(setLinkedItems(companyToUpdate,requestBody));
        linkCompanyInOtherItems(companyToUpdate, requestBody);

        itemLibrary.updateCompanySwipeDTO(savedCompany);

        return new CompanyReturnDTO(savedCompany);
    }

    /**
     * Remove company.
     *
     * @param companyToRemove the company to remove
     */
    public void removeCompany(Company companyToRemove){
        removeOldLinkings(companyToRemove);
        log.info("Links removed");
        ratingLinkRemover.unlinkAllRatingsFromUsers(companyToRemove.getRatings());
        for (Rating rating : companyToRemove.getRatings()) {
            ratingRepository.deleteById(rating.getId());
        }
        List<AppUser> users = (List<AppUser>) userRepository.findAll();
        for (AppUser user: users) {
            List<Bookmark> bookmarks = new ArrayList<>(user.getBookmarkedItems());
            for (Bookmark bookmark: bookmarks) {
                if (bookmark.getItemId()== companyToRemove.getId()){
                    user.getBookmarkedItems().remove(bookmark);
                }
            }
        }
        log.info("Ratings removed");
        itemLibrary.removeSwipeDTO(companyToRemove.getId());
        log.info("Deleted from feed");
        companyRepository.deleteById(companyToRemove.getId());
        log.info("Deleted from rep");


    }
}
