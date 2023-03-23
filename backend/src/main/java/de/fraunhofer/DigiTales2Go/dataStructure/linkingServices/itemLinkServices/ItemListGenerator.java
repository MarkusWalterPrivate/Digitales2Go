package de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.itemLinkServices;

import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.CompanyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.ProjectRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.TechnologyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.TrendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Item list generator.
 */
@Service
public class ItemListGenerator {
    /**
     * The Project repository.
     */
    @Autowired
    ProjectRepository projectRepository;
    /**
     * The Company repository.
     */
    @Autowired
    CompanyRepository companyRepository;
    /**
     * The Technology repository.
     */
    @Autowired
    TechnologyRepository technologyRepository;
    /**
     * The Trend repository.
     */
    @Autowired
    TrendRepository trendRepository;

    /**
     * Generate project list from id list.
     *
     * @param idList the id list
     * @return the list
     * @throws ResponseStatusException the response status exception
     */
    public Set<Project> generateProjectList(List<Long> idList) throws ResponseStatusException {
        Set<Project> projectList = new HashSet<>();
        for (Long id : idList
        ) {
            Optional<Project> foundProject = projectRepository.findById(id);
            if (foundProject.isPresent()) {
                projectList.add(foundProject.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Project with Id %s not found in database! Trend was not created", id));
            }
        }
        return projectList;
    }

    /**
     * Generate technology list from id list.
     *
     * @param idList the id list
     * @return the list
     * @throws ResponseStatusException the response status exception
     */
    public Set<Technology> generateTechnologyList(List<Long> idList) throws ResponseStatusException {
        Set<Technology> technologyList = new HashSet<>();
        for (Long id : idList
        ) {
            Optional<Technology> foundTechnology = technologyRepository.findById(id);
            if (foundTechnology.isPresent()) {
                technologyList.add(foundTechnology.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Technology with Id %s not found in database! Trend was not created", id));
            }
        }
        return technologyList;
    }

    /**
     * Generate trend list from id list.
     *
     * @param idList the id list
     * @return the list
     * @throws ResponseStatusException the response status exception
     */
    public Set<Trend> generateTrendList(List<Long> idList) throws ResponseStatusException {
        Set<Trend> trendList = new HashSet<>();
        for (Long id : idList
        ) {
            Optional<Trend> foundTrend = trendRepository.findById(id);
            if (foundTrend.isPresent()) {
                trendList.add(foundTrend.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Trend with Id %s not found in database! Trend was not created", id));
            }
        }
        return trendList;
    }

    /**
     * Generate company list from id list.
     *
     * @param idList the id list
     * @return the list
     * @throws ResponseStatusException the response status exception
     */
    public Set<Company> generateCompanyList(List<Long> idList) throws ResponseStatusException {
        Set<Company> companyList = new HashSet<>();
        for (Long id : idList
        ) {
            Optional<Company> foundCompany = companyRepository.findById(id);
            if (foundCompany.isPresent()) {
                companyList.add(foundCompany.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Company with Id %s not found in database! Trend was not created", id));
            }
        }
        return companyList;
    }
}
