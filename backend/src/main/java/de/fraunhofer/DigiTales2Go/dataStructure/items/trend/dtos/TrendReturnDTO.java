package de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses.TrendOptionalReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendRequired;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Trend return dto.
 *
 * @author Markus Walter
 */
@Data
public class TrendReturnDTO {


    private long id;
    private CoreFieldReturnDTO coreField;
    private DetailedRating detailedRating;
    private TrendRequired trendRequired;
    private TrendOptionalReturnDTO trendOptional;
    private List<ItemDTO> internalProjects = new ArrayList<>();
    private List<ItemDTO> internalTrends = new ArrayList<>();
    private List<ItemDTO> internalTechnologies = new ArrayList<>();
    private List<ItemDTO> internalCompanies = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>(); //Needs to be implemented later

    /**
     * Constructor
     *
     * @param trend the trend
     */
    public TrendReturnDTO(Trend trend) {

        this.id = trend.getId();
        this.coreField = new CoreFieldReturnDTO(trend.getCoreField());
        this.detailedRating = trend.getDetailedRating();
        this.trendRequired = trend.getTrendRequired();
        this.trendOptional = new TrendOptionalReturnDTO(trend.getTrendOptional());
        this.comments = trend.getComments();
        this.internalProjects = generateProjectReturnList(new ArrayList<>(trend.getInternalProjects()));
        this.internalTrends = generateTrendReturnList(new ArrayList<>(trend.getInternalTrends()));
        this.internalTechnologies = generateTechReturnList(new ArrayList<>(trend.getInternalTechnologies()));
        this.internalCompanies = generateCompanyReturnList(new ArrayList<>(trend.getInternalCompanies()));
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