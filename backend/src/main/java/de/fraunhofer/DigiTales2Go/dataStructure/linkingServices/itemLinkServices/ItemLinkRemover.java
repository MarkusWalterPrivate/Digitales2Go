package de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.itemLinkServices;

import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.CompanyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.ProjectRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.TechnologyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.TrendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type Item link remover.
 * WARNING: in the methods we create a List to iterate over first in order to avoid concurrency Exceptions. DO NOT CHANGE THIS!!!!
 */
@Service
@Slf4j
public class ItemLinkRemover {
    /**
     * The Project repository.
     */
    @Autowired
    ProjectRepository projectRepository;
    /**
     * The Trend repository.
     */
    @Autowired
    TrendRepository trendRepository;
    /**
     * The Technology repository.
     */
    @Autowired
    TechnologyRepository technologyRepository;

    /**
     * The Company repository.
     */
    @Autowired
    CompanyRepository companyRepository;


    /**
     * Remove company from projects.
     *
     * @param companyToRemove the company to remove
     */
    public void removeCompanyFromProjects(Company companyToRemove) {
        List<Project> projectsToEdit = new ArrayList<>(companyToRemove.getInternalProjects());
        for (Project project : projectsToEdit) {
            Set<Company> companies = project.getInternalCompanies();
            companies.remove(companyToRemove);
            project.setInternalCompanies(companies);
            projectRepository.save(project);
            log.info("Removing linked Company {} from Project {}", companyToRemove.getId(), project.getId());
        }
    }

    /**
     * Remove company from trends.
     *
     * @param companyToRemove the company to remove
     */
    public void removeCompanyFromTrends(Company companyToRemove) {
        List<Trend> trendsToEdit = new ArrayList<>(companyToRemove.getInternalTrends());
        for (Trend trend : trendsToEdit) {
            Set<Company> companies = trend.getInternalCompanies();
            companies.remove(companyToRemove);
            trend.setInternalCompanies(companies);
            trendRepository.save(trend);
            log.info("Removing linked Company {} from Trend {}", companyToRemove.getId(), trend.getId());
        }
    }

    /**
     * Remove company from technologies.
     *
     * @param companyToRemove the company to remove
     */
    public void removeCompanyFromTechnologies(Company companyToRemove) {
        List<Technology> technologiesToEdit = new ArrayList<>(companyToRemove.getInternalTechnologies());
        for (Technology technology : technologiesToEdit) {
            Set<Company> companies = technology.getInternalCompanies();
            companies.remove(companyToRemove);
            technology.setInternalCompanies(companies);
            technologyRepository.save(technology);
            log.info("Removing linked Company {} from Tech {}", companyToRemove.getId(), technology.getId());
        }
    }

    /**
     * Remove company from companies.
     *
     * @param companyToRemove the company to remove
     */
    public void removeCompanyFromCompanies(Company companyToRemove) {
        List<Company> companiesToEdit = new ArrayList<>(companyToRemove.getInternalCompanies());
        for (Company company : companiesToEdit) {
            Set<Company> internalCompanies = company.getInternalCompanies();
            internalCompanies.remove(companyToRemove);
            company.setInternalCompanies(internalCompanies);
            companyRepository.save(company);
            log.info("Removing linked Company {} from Company {}", companyToRemove.getId(), company.getId());
        }
    }

    /**
     * Remove technology from technologies.
     *
     * @param technologyToRemove the technology to remove
     */
    public void removeTechnologyFromTechnologies(Technology technologyToRemove) {
        List<Technology> technologiesToEdit = new ArrayList<>(technologyToRemove.getInternalTechnologies());
        for (Technology technology : technologiesToEdit) {
            Set<Technology> internalTechnologies = technology.getInternalTechnologies();
            internalTechnologies.remove(technologyToRemove);
            technology.setInternalTechnologies(internalTechnologies);
            technologyRepository.save(technology);
            log.info("Removing linked Tech {} from Tech {}", technologyToRemove.getId(), technology.getId());
        }
    }

    /**
     * Remove technology from trends.
     *
     * @param technologyToRemove the technology to remove
     */
    public void removeTechnologyFromTrends(Technology technologyToRemove) {
        List<Trend> trendsToEdit = new ArrayList<>(technologyToRemove.getInternalTrends());
        for (Trend trend : trendsToEdit) {
            Set<Technology> internalTechnologies = trend.getInternalTechnologies();
            internalTechnologies.remove(technologyToRemove);
            trend.setInternalTechnologies(internalTechnologies);
            trendRepository.save(trend);
            log.info("Removing linked Tech {} from Trend {}", technologyToRemove.getId(), trend.getId());
        }
    }

    /**
     * Remove technology from projects.
     *
     * @param technologyToRemove the technology to remove
     */
    public void removeTechnologyFromProjects(Technology technologyToRemove) {
        List<Project> projectsToEdit = new ArrayList<>(technologyToRemove.getInternalProjects());
        for (Project project : projectsToEdit) {
            Set<Technology> internalTechnologies = project.getInternalTechnologies();
            internalTechnologies.remove(technologyToRemove);
            project.setInternalTechnologies(internalTechnologies);
            projectRepository.save(project);
            log.info("Removing linked Tech {} from Project {}", technologyToRemove.getId(), project.getId());
        }
    }

    /**
     * Remove technology from companies.
     *
     * @param technologyToRemove the technology to remove
     */
    public void removeTechnologyFromCompanies(Technology technologyToRemove) {
        List<Company> companiesToEdit = new ArrayList<>(technologyToRemove.getInternalCompanies());
        for (Company company : companiesToEdit) {
            Set<Technology> technologies = company.getInternalTechnologies();
            technologies.remove(technologyToRemove);
            company.setInternalTechnologies(technologies);
            companyRepository.save(company);
            log.info("Removing linked Tech {} from Company", technologyToRemove.getId(), company.getId());
        }
    }

    /**
     * Remove trend from trends.
     *
     * @param trendToRemove the trend to remove
     */
    public void removeTrendFromTrends(Trend trendToRemove) {
        List<Trend> trendsToEdit = new ArrayList<>(trendToRemove.getInternalTrends());
        for (Trend trend : trendsToEdit) {
            Set<Trend> internalTrends = trend.getInternalTrends();
            internalTrends.remove(trendToRemove);
            trend.setInternalTrends(internalTrends);
            trendRepository.save(trend);
            log.info("Removing linked Trend {} from Trend {}", trendToRemove.getId(), trend.getId());
        }
    }

    /**
     * Remove trend from projects.
     *
     * @param trendToRemove the trend to remove
     */
    public void removeTrendFromProjects(Trend trendToRemove) {
        List<Project> projectsToEdit = new ArrayList<>(trendToRemove.getInternalProjects());

        for (Project project : projectsToEdit) {
            Set<Trend> trends = project.getInternalTrends();
            trends.remove(trendToRemove);
            project.setInternalTrends(trends);
            projectRepository.save(project);
            log.info("Removing linked Trend {} from Project {}", trendToRemove.getId(), project.getId());
        }
    }

    /**
     * Remove trend from companies.
     *
     * @param trendToRemove the trend to remove
     */
    public void removeTrendFromCompanies(Trend trendToRemove) {
        List<Company> companiesToEdit = new ArrayList<>(trendToRemove.getInternalCompanies());

        for (Company company : companiesToEdit) {
            Set<Trend> trends = company.getInternalTrends();
            trends.remove(trendToRemove);
            company.setInternalTrends(trends);
            companyRepository.save(company);
            log.info("Removing linked Trend {} from Company {}", trendToRemove.getId(), company.getId());
        }
    }

    /**
     * Remove trend from technologies.
     *
     * @param trendToRemove the trend to remove
     */
    public void removeTrendFromTechnologies(Trend trendToRemove) {
        List<Technology> technologiesToEdit = new ArrayList<>(trendToRemove.getInternalTechnologies());

        for (Technology technology : technologiesToEdit) {
            Set<Trend> trends = technology.getInternalTrends();
            trends.remove(trendToRemove);
            technology.setInternalTrends(trends);
            technologyRepository.save(technology);
            log.info("Removing linked Trend {} from Tech {}", trendToRemove.getId(), technology.getId());
        }
    }

    /**
     * Remove project from projects.
     *
     * @param projectToRemove the project to remove
     */
    public void removeProjectFromProjects(Project projectToRemove) {
        List<Project> projectsToEdit = new ArrayList<>(projectToRemove.getInternalProjects());
        for (Project project : projectsToEdit) {
            Set<Project> internalProjects = project.getInternalProjects();
            internalProjects.remove(projectToRemove);
            project.setInternalProjects(internalProjects);
            projectRepository.save(project);
            log.info("Removing linked Project {} from Project {}", projectToRemove.getId(), project.getId());
        }
    }

    /**
     * Remove project from companies.
     *
     * @param projectToRemove the project to remove
     */
    public void removeProjectFromCompanies(Project projectToRemove) {
        List<Company> companiesToEdit = new ArrayList<>(projectToRemove.getInternalCompanies());

        for (Company company : companiesToEdit) {
            Set<Project> projects = company.getInternalProjects();
            projects.remove(projectToRemove);
            company.setInternalProjects(projects);
            companyRepository.save(company);
            log.info("Removing linked Project {} from Company {}", projectToRemove.getId(), company.getId());
        }
    }

    /**
     * Remove project from technologies.
     *
     * @param projectToRemove the project to remove
     */
    public void removeProjectFromTechnologies(Project projectToRemove) {
        List<Technology> technologiesToEdit = new ArrayList<>(projectToRemove.getInternalTechnologies());

        for (Technology technology : technologiesToEdit) {
            Set<Project> projects = technology.getInternalProjects();
            projects.remove(projectToRemove);
            technology.setInternalProjects(projects);
            technologyRepository.save(technology);
            log.info("Removing linked Project {} from Tech {}", projectToRemove.getId(), technology.getId());
        }
    }

    /**
     * Remove project from trends.
     *
     * @param projectToRemove the project to remove
     */
    public void removeProjectFromTrends(Project projectToRemove) {
        List<Trend> trendsToEdit = new ArrayList<>(projectToRemove.getInternalTrends());

        for (Trend trend : trendsToEdit) {
            Set<Project> projects = trend.getInternalProjects();
            projects.remove(projectToRemove);
            trend.setInternalProjects(projects);
            trendRepository.save(trend);
            log.info("Removing linked Project {} from Trend {}", projectToRemove.getId(), trend.getId());
        }
    }
}
