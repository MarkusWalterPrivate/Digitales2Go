/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.company;

import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.CompanyCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.CompanyReturnDTO;
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
 * The type Company controller.
 *
 * @author Markus Walter
 */
@CrossOrigin
@RestController
@Transactional
@Slf4j
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyService companyService;


    /**
     * Gets all Companies.
     *
     * @return the all tech
     */
    @GetMapping("/company/")
    public List<CompanyReturnDTO> getAllTech() {
        List<Company> companies = (List<Company>) companyRepository.findAll();
        List<CompanyReturnDTO> returnCompanies = new ArrayList<>();
        for (Company company : companies
        ) {
            returnCompanies.add(new CompanyReturnDTO(company));

        }
        return returnCompanies;
    }

    /**
     * Gets a Company.
     *
     * @param id the id
     * @return the company
     */
    @GetMapping("/company/{id}")
    public CompanyReturnDTO getCompany(@PathVariable("id") long id) {
        Company foundCompany = companyRepository.findById(id);
        if (foundCompany != null) {
            return new CompanyReturnDTO(foundCompany);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Company with Id %s not found", id));
    }

    @GetMapping("/shared/company/{id}")
    public CompanyReturnDTO getSharedCompany(@PathVariable("id") long id) {
        return getCompany(id);
    }

    @GetMapping("/company/ratings/{id}")
    public Set<Rating> getCompanyRatings(@PathVariable("id") long id) {
        Company foundCompany = companyRepository.findById(id);
        if (foundCompany != null) {
            return foundCompany.getRatings();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Company with Id %s not found", id));
    }


    /**
     * Create company
     *
     * @param requestBody the request body
     * @return the company return dto
     */
    @PostMapping("/company/")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyReturnDTO createCompany(@Valid @RequestBody CompanyCreationDTO requestBody) {
        return companyService.createNewCompany(requestBody);
    }

    /**
     * Update company
     *
     * @param id          the id
     * @param requestBody the request body
     * @return the company return dto
     */
    @PutMapping("/company/{id}")
    public CompanyReturnDTO updateCompany(@PathVariable("id") long id, @Valid @RequestBody CompanyCreationDTO requestBody) {
        Company companyToUpdate = companyRepository.findById(id);
        if (companyToUpdate != null) {
            return companyService.updateCompany(companyToUpdate, requestBody);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Company with Id %s not found!", id));
    }

    /**
     * Delete Company.
     *
     * @param id the id
     */
    @DeleteMapping("/company/{id}")
    public void deleteCompany(@PathVariable("id") long id) {
        Company companyToRemove = companyRepository.findById(id);
        if (companyToRemove != null) {
            log.info("Company found");
            companyService.removeCompany(companyToRemove);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Company with Id %s not found!", id));
    }
}