/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.project;


import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.ProjectCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.ProjectReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Project controller.
 *
 * @author Markus Walter
 */

@RestController
@CrossOrigin
@Transactional
@Slf4j
public class ProjectController {
    @Autowired
    ProjectRepository projectRep;
    @Autowired
    ProjectService projectService;


    /**
     * Gets all projects.
     *
     * @return the all projects
     */
    @GetMapping("/project/")
    public List<ProjectReturnDTO> getAllProjects() {
        List<Project> projects = (List<Project>) projectRep.findAll();
        List<ProjectReturnDTO> projectsToReturn = new ArrayList<>();
        for (Project project : projects
        ) {
            projectsToReturn.add(new ProjectReturnDTO(project));
        }
        log.info("List of all Projects is requested");
        return projectsToReturn;
    }

    /**
     * Gets project.
     *
     * @param id the id
     * @return the project
     */
    @GetMapping("/project/{id}")
    public ProjectReturnDTO getProject(@PathVariable("id") long id) {
        Project foundProject = projectRep.findById(id);
        if (foundProject != null) {
            log.info("Project with id {} is requested", id);
            return new ProjectReturnDTO(foundProject);

        }
        log.warn("Project with id{} is not found", id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Project with Id %s not found", id));
    }

    @GetMapping("/shared/project/{id}")
    public ProjectReturnDTO getSharedProject(@PathVariable("id") long id) {
        return getProject(id);
    }

    @GetMapping("/project/ratings/{id}")
    public Set<Rating> getProjectRatings(@PathVariable("id") long id) {
        Project foundProject = projectRep.findById(id);
        if (foundProject != null) {
            log.info("Ratings of Project with id{} are requested", id);
            return foundProject.getRatings();
        }
        log.warn("Project with id {} is not found when Ratings where Requested", id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Project with Id %s not found", id));
    }


    /**
     * Create project.
     *
     * @param requestBody the request body
     * @return the project return dto
     */
    @PostMapping("/project/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectReturnDTO createProject(@Valid @RequestBody ProjectCreationDTO requestBody) {
        return projectService.createNewProject(requestBody);
    }

    /**
     * Update project.
     *
     * @param id          the id
     * @param requestBody the request body
     * @return the project return dto
     */
    @PutMapping("/project/{id}")
    public ProjectReturnDTO updateProject(@PathVariable("id") long id, @Valid @RequestBody ProjectCreationDTO requestBody) {
        Project projectToUpdate = projectRep.findById(id);
        if (projectToUpdate != null) {
            return projectService.updateProject(projectToUpdate, requestBody);
        }
        log.warn("Project with id {} is not found when trying to Edit it", id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Project with Id %s not found!", id));
    }

    /**
     * Delete project.
     *
     * @param id the id
     */
    @DeleteMapping("/project/{id}")
    public void deleteProject(@PathVariable("id") long id) {
        Project projectToDelete = projectRep.findById(id);
        if (projectToDelete != null) {
            projectService.removeProject(projectToDelete);
            log.info("Project was deleted: ID:{}", id);
            return;
        }
        log.warn("Project with id {} is not found when trying to delete it", id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Technology with Id %s not found!", id));
    }
}