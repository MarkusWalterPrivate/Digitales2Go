package de.fraunhofer.DigiTales2Go.feed;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.CompanyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.ProjectRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.TechnologyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.TrendRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import de.fraunhofer.DigiTales2Go.feed.DTOs.FeedReturnDTO;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


/**
 * The type feed controller.
 *
 * @author Markus Walter
 */
@RestController
@CrossOrigin
@Transactional
@Slf4j
public class FeedController {

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
    /**
     * The User rep.
     */
    @Autowired
    UserRepository userRep;
    /**
     * The Feed service.
     */
    @Autowired
    FeedService feedService;


    /**
     * Init.
     */
    @PostConstruct
    public void init() {


        // if Data already exists, create list from it

        List<Technology> techs = (List<Technology>) techRep.findAll();
        for (Technology tech : techs) {
            itemLibrary.addTechSwipeDTO(tech);
        }
        List<Trend> trends = (List<Trend>) trendRep.findAll();
        for (Trend trend : trends) {
            itemLibrary.addTrendSwipeDTO(trend);
        }

        List<Company> companies = (List<Company>) companyRep.findAll();
        for (Company company : companies) {
            itemLibrary.addCompanySwipeDTO(company);
        }
        List<Project> projects = (List<Project>) projectRep.findAll();
        for (Project project : projects) {
            itemLibrary.addProjectSwipeDTO(project);
        }

    }

    /**
     * Gets item.
     *
     * @param id           the id
     * @param loggedInUser the logged in user
     * @return the item
     */
    @GetMapping("/feed/{id}")
    public ItemDTO getItem(@PathVariable("id") long id, @AuthenticationPrincipal User loggedInUser) {
        if (loggedInUser != null) {
            Optional<AppUser> user = userRep.findByEmail(loggedInUser.getUsername());
            if (user.isPresent()) {
                return feedService.setFlagsOfSingleItem(new ItemDTO(itemLibrary.getItemDTO(id)), user.get());
            }
        }
        return itemLibrary.getItemDTO(id);
    }

    /**
     * Gets hot tech sorted by ratingCount of items.
     *
     * @param loggedInUser the logged in user
     * @return the hot tech
     */
    @PostMapping("/feed/hot")
    public FeedReturnDTO getHotItems(@AuthenticationPrincipal User loggedInUser, @Valid @RequestBody Pageable pageable) {
        if (loggedInUser != null) {
            Optional<AppUser> optionalAppUser = userRep.findByEmail(loggedInUser.getUsername());
            if (optionalAppUser.isPresent()) {
                return feedService.generateHotFeed(optionalAppUser.get(), pageable);
            }
        }
        return feedService.generateHotFeed(null, pageable);
    }

    /**
     * Gets interesting tech only returning fitting items filtered by matching industry.
     *
     * @param loggedInUser the logged in user
     * @return the interesting tech
     */
    @PostMapping("/feed/interest")
    public FeedReturnDTO getInterestingItems(@AuthenticationPrincipal User loggedInUser, @Valid @RequestBody Pageable pageable) {
        if (loggedInUser != null) {
            Optional<AppUser> optionalAppUser = userRep.findByEmail(loggedInUser.getUsername());
            if (optionalAppUser.isPresent()) {
                return feedService.generateInterestFeed(optionalAppUser.get(), pageable);
            }
        }
        return feedService.generateInterestFeed(null, pageable);
    }

    /**
     * Gets new tech sorted by creationDate.
     *
     * @param loggedInUser the logged in user
     * @return the new tech
     */
    @PostMapping("/feed/new")
    public FeedReturnDTO getNewItems(@AuthenticationPrincipal User loggedInUser, @Valid @RequestBody Pageable pageable) {
        if (loggedInUser != null) {
            Optional<AppUser> optionalAppUser = userRep.findByEmail(loggedInUser.getUsername());
            if (optionalAppUser.isPresent()) {
                return feedService.generateNewFeed(optionalAppUser.get(), pageable);
            }
        }
        return feedService.generateNewFeed(null, pageable);
    }

    /**
     * Gets unapproved.
     *
     * @return the unapproved
     */
    @PostMapping("/feed/unapproved") // definitely has to be refined later...
    public FeedReturnDTO getUnapproved(@Valid @RequestBody Pageable pageable) {
        log.info("Unapproved feed requested");
        return feedService.paginateList(itemLibrary.getWaitingForApproval(), pageable);
    }


    /**
     * Approve item.
     *
     * @param id the id
     */
    @PostMapping("/approve/{id}")
    public void approveItem(@PathVariable("id") long id) {
        itemLibrary.approve(id);
        feedService.approveItemInDB(id);
    }
    // methods needed to keep DTO and main class in sync


}
