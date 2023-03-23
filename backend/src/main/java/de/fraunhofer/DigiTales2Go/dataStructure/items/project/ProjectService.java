package de.fraunhofer.DigiTales2Go.dataStructure.items.project;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.Bookmark;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.ProjectCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.ProjectReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectRequired;
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
 * The type Project service.
 */
@Service
@Slf4j
public class ProjectService {
    /**
     * The Project rep.
     */
    @Autowired
    ProjectRepository projectRep;
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
     * Create new project
     *
     * @param requestBody the request body
     * @return the project return dto
     */
    public ProjectReturnDTO createNewProject(ProjectCreationDTO requestBody){
        Project newProject = new Project();

        CoreField coreField = new CoreField(requestBody.getCoreField());
        coreField.setType(Type.PROJECT);
        newProject.setCoreField(coreField);

        DetailedRating detailedRating = new DetailedRating(requestBody.getDetailedRating());
        newProject.setDetailedRating(detailedRating);

        ProjectRequired required = new ProjectRequired(requestBody.getProjectRequired());
        newProject.setProjectRequired(required);
        ProjectOptional optional = new ProjectOptional();
        if (requestBody.getProjectOptional() != null) {
            optional = new ProjectOptional(requestBody.getProjectOptional());
        }
        newProject.setProjectOptional(optional);

        if (requestBody.getEvents() != null) {
            newProject.setEvents(requestBody.getEvents());
        } else {
            newProject.setEvents(new ArrayList<>());
        }

        Project savedProject = projectRep.save(setLinkedItems(newProject, requestBody));
        linkProjectInOtherItems(savedProject, requestBody);

        itemLibrary.addProjectSwipeDTO(savedProject);
        log.info("New Project was created: ID: {}, Headline: {}", savedProject.getId(), savedProject.getCoreField().getHeadline());
        return new ProjectReturnDTO(savedProject);
    }

    private void linkProjectInOtherItems(Project savedProject, ProjectCreationDTO requestBody){
        //Add newly created Project to each other item
        if (requestBody.getInternalCompanies() != null) {
            itemLinker.linkProjectToCompanies(savedProject, requestBody.getInternalCompanies());
        }
        if (requestBody.getInternalProjects() != null) {
            itemLinker.linkProjectToProjects(savedProject, requestBody.getInternalProjects());
        }
        if (requestBody.getInternalTrends() != null) {
            itemLinker.linkProjectToTrends(savedProject, requestBody.getInternalTrends());
        }
        if (requestBody.getInternalTechnologies() != null) {
            itemLinker.linkProjectToTechnologies(savedProject, requestBody.getInternalTechnologies());
        }
    }

    private  Project setLinkedItems (Project newProject, ProjectCreationDTO requestBody){
        //Add list of Items to project. Since Project is the owning partner of each relationship nothing else has to be done.
        if (requestBody.getInternalCompanies() != null) {
            newProject.setInternalCompanies(listGenerator.generateCompanyList(requestBody.getInternalCompanies()));
        }
        if (requestBody.getInternalProjects() != null) {
            newProject.setInternalProjects(listGenerator.generateProjectList(requestBody.getInternalProjects()));
        }
        if (requestBody.getInternalTrends() != null) {
            newProject.setInternalTrends(listGenerator.generateTrendList(requestBody.getInternalTrends()));
        }
        if (requestBody.getInternalTechnologies() != null) {
            newProject.setInternalTechnologies(listGenerator.generateTechnologyList(requestBody.getInternalTechnologies()));
        }
        return newProject;
    }

    private void removeOldLinkings(Project projectToUpdate){
        linkRemover.removeProjectFromProjects(projectToUpdate);
        linkRemover.removeProjectFromCompanies(projectToUpdate);
        linkRemover.removeProjectFromTechnologies(projectToUpdate);
        linkRemover.removeProjectFromTrends(projectToUpdate);
    }

    /**
     * Update project
     *
     * @param projectToUpdate the project to update
     * @param requestBody     the request body
     * @return the project return dto
     */
    public ProjectReturnDTO updateProject(Project projectToUpdate, ProjectCreationDTO requestBody){
        //Update Rating to latest in case someone voted and transfer id of coreField from old to new
        CoreField newCoreField = new CoreField(requestBody.getCoreField());
        newCoreField.setRating(projectToUpdate.getCoreField().getRating());
        newCoreField.setCreationDate(projectToUpdate.getCoreField().getCreationDate());
        newCoreField.setId(projectToUpdate.getCoreField().getId());
        projectToUpdate.setCoreField(newCoreField);

        DetailedRating detailedRating = new DetailedRating(requestBody.getDetailedRating());
        detailedRating.setId(projectToUpdate.getDetailedRating().getId());
        projectToUpdate.setDetailedRating(detailedRating);

        //transferId of ProjectRequired from old to new
        ProjectRequired projectRequired = new ProjectRequired(requestBody.getProjectRequired(),
                projectToUpdate.getProjectRequired().getRuntime().getId());
        projectRequired.setId(projectToUpdate.getProjectRequired().getId());
        projectToUpdate.setProjectRequired(projectRequired);

        //Update projectOptional: check each field for null
        ProjectOptional optional;

        if (requestBody.getProjectOptional() != null) {
            optional = new ProjectOptional(requestBody.getProjectOptional(),
                    projectToUpdate.getProjectOptional().getProjectRelations().getId(), projectToUpdate.getProjectOptional().getFinancing().getId());
        } else {
            //Create new Optional fields, but keep ids
            optional = new ProjectOptional(projectToUpdate.getProjectOptional().getProjectRelations().getId(), projectToUpdate.getProjectOptional().getFinancing().getId());
        }
        optional.setId(projectToUpdate.getProjectOptional().getId());
        projectToUpdate.setProjectOptional(optional);


        if (requestBody.getEvents() != null) {
            projectToUpdate.setEvents(requestBody.getEvents());
        } else {
            projectToUpdate.setEvents(new ArrayList<>());
        }

        removeOldLinkings(projectToUpdate);
        Project savedProject = projectRep.save(setLinkedItems(projectToUpdate,requestBody));
        linkProjectInOtherItems(savedProject, requestBody);

        itemLibrary.updateProjectSwipeDTO(savedProject);

        log.info("Project was updated: ID: {}, Headline: {}", savedProject.getId(), savedProject.getCoreField().getHeadline());
        return new ProjectReturnDTO(savedProject);
    }

    /**
     * Remove project.
     *
     * @param projectToDelete the project to delete
     */
    public void removeProject(Project projectToDelete){
        removeOldLinkings(projectToDelete);
        ratingLinkRemover.unlinkAllRatingsFromUsers(projectToDelete.getRatings());
        for (Rating rating : projectToDelete.getRatings()) {
            ratingRepository.deleteById(rating.getId());
        }
        List<AppUser> users = (List<AppUser>) userRepository.findAll();
        for (AppUser user: users) {
            List<Bookmark> bookmarks = new ArrayList<>(user.getBookmarkedItems());
            for (Bookmark bookmark: bookmarks) {
                if (bookmark.getItemId()== projectToDelete.getId()){
                    user.getBookmarkedItems().remove(bookmark);
                }
            }
        }
        itemLibrary.removeSwipeDTO(projectToDelete.getId());
        projectRep.deleteById(projectToDelete.getId());

    }
}
