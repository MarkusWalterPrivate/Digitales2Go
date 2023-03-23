package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns.CompanyOptionalReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns.subclasses.CompanyRequiredReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Company return dto.
 *
 * @author Markus Walter
 */
@Data
public class CompanyReturnDTO {
    private Long id;
    private CoreFieldReturnDTO coreField;
    private DetailedRating detailedRating;
    private CompanyRequiredReturnDTO companyRequired;
    private CompanyOptionalReturnDTO companyOptional;
    private List<ItemDTO> internalProjects = new ArrayList<>();
    private List<ItemDTO> internalTrends = new ArrayList<>();
    private List<ItemDTO> internalTechnologies = new ArrayList<>();
    private List<ItemDTO> internalCompanies = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>(); //Needs to be implemented later

    /**
     * Constrcutor from Company class
     *
     * @param company the company
     */
    public CompanyReturnDTO(Company company) {
        this.id = company.getId();
        this.detailedRating = company.getDetailedRating();
        this.coreField = new CoreFieldReturnDTO(company.getCoreField());
        this.companyRequired = new CompanyRequiredReturnDTO(company.getCompanyRequired());
        this.companyOptional = new CompanyOptionalReturnDTO(company.getCompanyOptional());
        this.comments = company.getComments();
        this.internalProjects = generateProjectReturnList(new ArrayList<>(company.getInternalProjects()));
        this.internalTrends = generateTrendReturnList(new ArrayList<>(company.getInternalTrends()));
        this.internalTechnologies = generateTechReturnList(new ArrayList<>(company.getInternalTechnologies()));
        this.internalCompanies = generateCompanyReturnList(new ArrayList<>(company.getInternalCompanies()));
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
