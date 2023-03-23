package de.fraunhofer.DigiTales2Go.dataStructure.items.technology;


import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.TechCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.TechReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
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
 * The type Technology controller.
 *
 * @author Markus Walter
 */
@RestController
@CrossOrigin
@Transactional
public class TechnologyController {
    @Autowired
    TechnologyRepository techRep;
    @Autowired
    TechnologyService technologyService;


    /**
     * Gets all tech.
     *
     * @return the all tech
     */
    @GetMapping("/tech/")
    public List<TechReturnDTO> getAllTech() {
        List<Technology> techs = (List<Technology>) techRep.findAll();
        List<TechReturnDTO> techsToReturn = new ArrayList<>();
        for (Technology tech : techs
        ) {
            techsToReturn.add(new TechReturnDTO(tech));
        }
        return techsToReturn;
    }

    /**
     * Gets tech.
     *
     * @param id the id
     * @return the tech
     */
    @GetMapping("/tech/{id}")
    public TechReturnDTO getTech(@PathVariable("id") long id) {
        Technology foundTech = techRep.findById(id);
        if (foundTech != null) {
            return new TechReturnDTO(foundTech);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Technology with Id %s not found", id));
    }

    @GetMapping("/shared/tech/{id}")
    public TechReturnDTO getSharedTech(@PathVariable("id") long id) {
        return getTech(id);
    }

    @GetMapping("/tech/ratings/{id}")
    public Set<Rating> getTechRating(@PathVariable("id") long id) {
        Technology foundTech = techRep.findById(id);
        if (foundTech != null) {
            return foundTech.getRatings();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Technology with Id %s not found", id));
    }


    /**
     * Create tech.
     *
     * @param requestBody the request body
     * @return the tech return dto
     */
    @PostMapping("/tech/")
    @ResponseStatus(HttpStatus.CREATED)
    public TechReturnDTO createTech(@Valid @RequestBody TechCreationDTO requestBody) {
        return technologyService.createNewTech(requestBody);
    }

    /**
     * Update tech.
     *
     * @param id          the id
     * @param requestBody the request body
     * @return the tech return dto
     */
    @PutMapping("/tech/{id}")
    public TechReturnDTO updateTech(@PathVariable("id") long id, @Valid @RequestBody TechCreationDTO requestBody) {
        Technology techToUpdate = techRep.findById(id);
        if (techToUpdate != null) {
            return technologyService.updateTech(techToUpdate, requestBody);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Technology with Id %s not found!", id));
    }

    /**
     * Delete tech.
     *
     * @param id the id
     */
    @DeleteMapping("/tech/{id}")
    public void deleteTech(@PathVariable("id") long id) {
        Technology techToRemove = techRep.findById(id);
        if (techToRemove != null) {
            technologyService.removeTech(techToRemove);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Technology with Id %s not found!", id));
    }
}