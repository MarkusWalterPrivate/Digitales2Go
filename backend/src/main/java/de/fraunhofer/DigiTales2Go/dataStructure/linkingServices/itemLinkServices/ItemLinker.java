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

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Item linker.
 */
@Service
@Slf4j
public class ItemLinker {
    //No company rep needed, since comp is always the owned side of relationship -> no saves of company item to update links

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
     * Link company to projects.
     *
     * @param companyToAdd the company to add
     * @param ids          the ids
     */
    public void linkCompanyToProjects(Company companyToAdd, List<Long> ids) {

        for (Long id : ids) {
            Optional<Project> projectToUpdate = projectRepository.findById(id);
            if (projectToUpdate.isPresent()) {
                Project foundProject = projectToUpdate.get();
                Set<Company> companyList = foundProject.getInternalCompanies();
                companyList.add(companyToAdd);
                foundProject.setInternalCompanies(companyList);
                projectRepository.save(foundProject);
                log.info("Linking company {} to Project {}", companyToAdd.getId(), id);
            }
        }
    }

    /**
     * Link company to trends.
     *
     * @param companyToAdd the company to add
     * @param ids          the ids
     */
    public void linkCompanyToTrends(Company companyToAdd, List<Long> ids) {

        for (Long id : ids) {
            Optional<Trend> foundTrend = trendRepository.findById(id);
            if (foundTrend.isPresent()) {
                Trend trendToUpdate = foundTrend.get();

                Set<Company> companyList = trendToUpdate.getInternalCompanies();
                companyList.add(companyToAdd);
                trendToUpdate.setInternalCompanies(companyList);
                trendRepository.save(trendToUpdate);
                log.info("Linking company {} to Trend {}", companyToAdd.getId(), id);
            }
        }

    }

    /**
     * Link company to technologies.
     *
     * @param companyToAdd the company to add
     * @param ids          the ids
     */
    public void linkCompanyToTechnologies(Company companyToAdd, List<Long> ids) {

        for (Long id : ids) {
            Optional<Technology> foundTechnology = technologyRepository.findById(id);
            if (foundTechnology.isPresent()) {
                Technology technologyToUpdate = foundTechnology.get();

                Set<Company> companyList = technologyToUpdate.getInternalCompanies();
                companyList.add(companyToAdd);
                technologyToUpdate.setInternalCompanies(companyList);
                technologyRepository.save(technologyToUpdate);
                log.info("Linking company {} to Tech {}", companyToAdd.getId(), id);
            }
        }
    }

    /**
     * Link company to companies.
     *
     * @param companyToAdd the company to add
     * @param ids          the ids
     */
    public void linkCompanyToCompanies(Company companyToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Company> foundCompany = companyRepository.findById(id);
            if (foundCompany.isPresent()) {
                Company companyToUpdate = foundCompany.get();

                Set<Company> companyList = companyToUpdate.getInternalCompanies();
                companyList.add(companyToAdd);
                companyToUpdate.setInternalCompanies(companyList);
                companyRepository.save(companyToUpdate);
                log.info("Linking company {} to Company {}", companyToAdd.getId(), id);
            }
        }
    }


    /**
     * Link trend to projects.
     *
     * @param trendToAdd the trend to add
     * @param ids        the ids
     */
    public void linkTrendToProjects(Trend trendToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Project> foundProject = projectRepository.findById(id);
            if (foundProject.isPresent()) {
                Project projectToUpdate = foundProject.get();
                Set<Trend> trendList = projectToUpdate.getInternalTrends();
                trendList.add(trendToAdd);
                projectToUpdate.setInternalTrends(trendList);
                projectRepository.save(projectToUpdate);
                log.info("Linking trend {} to Project {}", trendToAdd.getId(), id);
            }
        }
    }

    /**
     * Link trend to companies.
     *
     * @param trendToAdd the trend to add
     * @param ids        the ids
     */
    public void linkTrendToCompanies(Trend trendToAdd, List<Long> ids) {

        for (Long id : ids) {
            Optional<Company> foundCompany = companyRepository.findById(id);
            if (foundCompany.isPresent()) {
                Company companyToUpdate = foundCompany.get();
                Set<Trend> trendList = companyToUpdate.getInternalTrends();
                trendList.add(trendToAdd);
                companyToUpdate.setInternalTrends(trendList);
                companyRepository.save(companyToUpdate);
                log.info("Linking trend {} to Company {}", trendToAdd.getId(), id);
            }
        }
    }

    /**
     * Link trend to technologies.
     *
     * @param trendToAdd the trend to add
     * @param ids        the ids
     */
    public void linkTrendToTechnologies(Trend trendToAdd, List<Long> ids) {

        for (Long id : ids) {
            Optional<Technology> foundTechnology = technologyRepository.findById(id);
            if (foundTechnology.isPresent()) {
                Technology technologyToUpdate = foundTechnology.get();

                Set<Trend> trendList = technologyToUpdate.getInternalTrends();
                trendList.add(trendToAdd);
                technologyToUpdate.setInternalTrends(trendList);
                technologyRepository.save(technologyToUpdate);
                log.info("Linking trend {} to Tech {}", trendToAdd.getId(), id);
            }
        }
    }

    /**
     * Link trend to trends.
     *
     * @param trendToAdd the trend to add
     * @param ids        the ids
     */
    public void linkTrendToTrends(Trend trendToAdd, List<Long> ids) {

        for (Long id : ids) {
            Optional<Trend> foundTrend = trendRepository.findById(id);
            if (foundTrend.isPresent()) {
                Trend trendToUpdate = foundTrend.get();

                Set<Trend> trendList = trendToUpdate.getInternalTrends();
                trendList.add(trendToAdd);
                trendToUpdate.setInternalTrends(trendList);
                trendRepository.save(trendToUpdate);
                log.info("Linking trend {} to Trend {}", trendToAdd.getId(), id);
            }
        }
    }


    /**
     * Link technology to projects.
     *
     * @param technologyToAdd the technology to add
     * @param ids             the ids
     */
    public void linkTechnologyToProjects(Technology technologyToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Project> foundProject = projectRepository.findById(id);
            if (foundProject.isPresent()) {
                Project projectToUpdate = foundProject.get();

                Set<Technology> technologyList = projectToUpdate.getInternalTechnologies();
                technologyList.add(technologyToAdd);
                projectToUpdate.setInternalTechnologies(technologyList);
                projectRepository.save(projectToUpdate);
                log.info("Linking tech {} to Project {}", technologyToAdd.getId(), id);
            }
        }
    }

    /**
     * Link technology to trends.
     *
     * @param technologyToAdd the technology to add
     * @param ids             the ids
     */
    public void linkTechnologyToTrends(Technology technologyToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Trend> foundTrend = trendRepository.findById(id);
            if (foundTrend.isPresent()) {
                Trend trendToUpdate = foundTrend.get();

                Set<Technology> technologyList = trendToUpdate.getInternalTechnologies();
                technologyList.add(technologyToAdd);
                trendToUpdate.setInternalTechnologies(technologyList);
                trendRepository.save(trendToUpdate);
                log.info("Linking tech {} to Trend {}", technologyToAdd.getId(), id);
            }
        }
    }

    /**
     * Link technology to technologies.
     *
     * @param technologyToAdd the technology to add
     * @param ids             the ids
     */
    public void linkTechnologyToTechnologies(Technology technologyToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Technology> foundTechnology = technologyRepository.findById(id);
            if (foundTechnology.isPresent()) {
                Technology technologyToUpdate = foundTechnology.get();

                Set<Technology> technologyList = technologyToUpdate.getInternalTechnologies();
                technologyList.add(technologyToAdd);
                technologyToUpdate.setInternalTechnologies(technologyList);
                technologyRepository.save(technologyToUpdate);
                log.info("Linking tech {} to Tech {}", technologyToAdd.getId(), id);
            }
        }
    }

    /**
     * Link technology to companies.
     *
     * @param technologyToAdd the technology to add
     * @param ids             the ids
     */
    public void linkTechnologyToCompanies(Technology technologyToAdd, List<Long> ids) {

        for (Long id : ids) {
            Optional<Company> foundCompany = companyRepository.findById(id);
            if (foundCompany.isPresent()) {
                Company companyToUpdate = foundCompany.get();

                Set<Technology> technologyList = companyToUpdate.getInternalTechnologies();
                technologyList.add(technologyToAdd);
                companyToUpdate.setInternalTechnologies(technologyList);
                companyRepository.save(companyToUpdate);
                log.info("Linking tech {} to Company {}", technologyToAdd.getId(), id);
            }
        }
    }

    /**
     * Link project to companies.
     *
     * @param projectToAdd the project to add
     * @param ids          the ids
     */
    public void linkProjectToCompanies(Project projectToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Company> foundCompany = companyRepository.findById(id);
            if (foundCompany.isPresent()) {
                Company companyToUpdate = foundCompany.get();

                Set<Project> projectList = companyToUpdate.getInternalProjects();
                projectList.add(projectToAdd);
                companyToUpdate.setInternalProjects(projectList);
                companyRepository.save(companyToUpdate);
                log.info("Linking project {} to Company {}", projectToAdd.getId(), id);
            }
        }
    }

    /**
     * Link project to projects.
     *
     * @param projectToAdd the project to add
     * @param ids          the ids
     */
    public void linkProjectToProjects(Project projectToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Project> foundProject = projectRepository.findById(id);
            if (foundProject.isPresent()) {
                Project projectToUpdate = foundProject.get();

                Set<Project> projectList = projectToUpdate.getInternalProjects();
                projectList.add(projectToAdd);
                projectToUpdate.setInternalProjects(projectList);
                projectRepository.save(projectToUpdate);
                log.info("Linking project {} to Project {}", projectToAdd.getId(), id);
            }
        }
    }

    /**
     * Link project to technologies.
     *
     * @param projectToAdd the project to add
     * @param ids          the ids
     */
    public void linkProjectToTechnologies(Project projectToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Technology> foundTechnology = technologyRepository.findById(id);
            if (foundTechnology.isPresent()) {
                Technology technologyToUpdate = foundTechnology.get();

                Set<Project> projectList = technologyToUpdate.getInternalProjects();
                projectList.add(projectToAdd);
                technologyToUpdate.setInternalProjects(projectList);
                technologyRepository.save(technologyToUpdate);
                log.info("Linking project {} to Tech {}", projectToAdd.getId(), id);
            }

        }
    }

    /**
     * Link project to trends.
     *
     * @param projectToAdd the project to add
     * @param ids          the ids
     */
    public void linkProjectToTrends(Project projectToAdd, List<Long> ids) {
        for (Long id : ids) {
            Optional<Trend> foundTrend = trendRepository.findById(id);
            if (foundTrend.isPresent()) {
                Trend trendToUpdate = foundTrend.get();

                Set<Project> projectList = trendToUpdate.getInternalProjects();
                projectList.add(projectToAdd);
                trendToUpdate.setInternalProjects(projectList);
                trendRepository.save(trendToUpdate);
                log.info("Linking project {} to Trend {}", projectToAdd.getId(), id);
            }
        }
    }

}
