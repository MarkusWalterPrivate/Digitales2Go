package de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.optional.TechOptionalReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.required.TechRequiredReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Tech return dto.
 *
 * @author Markus Walter
 */
@Data
public class TechReturnDTO {


    private long id;

    private CoreFieldReturnDTO coreField;

    private DetailedRating detailedRating;

    private TechRequiredReturnDTO techRequired;

    private TechOptionalReturnDTO techOptional;

    private List<ItemDTO> internalProjects = new ArrayList<>();
    private List<ItemDTO> internalTrends = new ArrayList<>();
    private List<ItemDTO> internalTechnologies = new ArrayList<>();
    private List<ItemDTO> internalCompanies = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>(); //Needs to be implemented later

    /**
     * Instantiates a new Tech return dto.
     *
     * @param tech the tech
     */
    public TechReturnDTO(Technology tech) {
        this.id = tech.getId();
        this.coreField = new CoreFieldReturnDTO(tech.getCoreField());
        this.detailedRating = tech.getDetailedRating();
        this.techRequired = new TechRequiredReturnDTO(tech.getTechRequired());
        this.techOptional = new TechOptionalReturnDTO(tech.getTechOptional());
        this.comments = tech.getComments();
        this.internalProjects = generateProjectReturnList(new ArrayList<>(tech.getInternalProjects()));
        this.internalTrends = generateTrendReturnList(new ArrayList<>(tech.getInternalTrends()));
        this.internalTechnologies = generateTechReturnList(new ArrayList<>(tech.getInternalTechnologies()));
        this.internalCompanies = generateCompanyReturnList(new ArrayList<>(tech.getInternalCompanies()));
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