package de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.event.Event;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.ProjectOptionalReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.ProjectRequiredReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Project return dto.
 *
 * @author Markus Walter
 */
@Data
public class ProjectReturnDTO {

    private long id;

    private CoreFieldReturnDTO coreField;

    private DetailedRating detailedRating;

    private ProjectRequiredReturnDTO projectRequired = new ProjectRequiredReturnDTO();

    private ProjectOptionalReturnDTO projectOptional;

    private List<Event> events = new ArrayList<>();
    private List<ItemDTO> internalProjects = new ArrayList<>();
    private List<ItemDTO> internalTrends = new ArrayList<>();
    private List<ItemDTO> internalTechnologies = new ArrayList<>();
    private List<ItemDTO> internalCompanies = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>(); //Needs to be implemented later

    /**
     * Instantiates a new Project return dto.
     *
     * @param project the project
     */
    public ProjectReturnDTO(Project project) {

        this.id = project.getId();
        this.coreField = new CoreFieldReturnDTO(project.getCoreField());
        this.detailedRating = project.getDetailedRating();
        this.projectRequired = new ProjectRequiredReturnDTO(project.getProjectRequired());
        this.projectOptional = new ProjectOptionalReturnDTO(project.getProjectOptional());
        this.events = project.getEvents();
        this.comments = project.getComments();
        this.internalProjects = generateProjectReturnList(new ArrayList<>(project.getInternalProjects()));
        this.internalTrends = generateTrendReturnList(new ArrayList<>(project.getInternalTrends()));
        this.internalTechnologies = generateTechReturnList(new ArrayList<>(project.getInternalTechnologies()));
        this.internalCompanies = generateCompanyReturnList(new ArrayList<>(project.getInternalCompanies()));
    }

    private List<ItemDTO> generateProjectReturnList(List<Project> projects) {
        List<ItemDTO> returnList = new ArrayList<>();
        for (Project project : projects
        ) {
            returnList.add(new ItemDTO(project));
        }
        return returnList;
    }

    private List<ItemDTO> generateTrendReturnList(List<Trend> trends) {
        List<ItemDTO> returnList = new ArrayList<>();
        for (Trend trend : trends
        ) {
            returnList.add(new ItemDTO(trend));
        }
        return returnList;
    }

    private List<ItemDTO> generateTechReturnList(List<Technology> techs) {
        List<ItemDTO> returnList = new ArrayList<>();
        for (Technology tech : techs
        ) {
            returnList.add(new ItemDTO(tech));
        }
        return returnList;
    }

    private List<ItemDTO> generateCompanyReturnList(List<Company> companies) {
        List<ItemDTO> returnList = new ArrayList<>();
        for (Company company : companies
        ) {
            returnList.add(new ItemDTO(company));
        }
        return returnList;
    }
}
