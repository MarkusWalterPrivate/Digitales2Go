/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.items.trend;

import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.TrendCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.TrendReturnDTO;
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
 * The type Trend controller.
 *
 * @author Markus Walter
 */
@RestController
@CrossOrigin
@Transactional
public class TrendController {
    @Autowired
    TrendRepository trendRep;
    @Autowired
    TrendService trendService;


    /**
     * Gets all trends.
     *
     * @return the all trends
     */

    @GetMapping("/trend/")
    public List<TrendReturnDTO> getAllTrends() {
        List<Trend> trends = (List<Trend>) trendRep.findAll();
        List<TrendReturnDTO> trendsToReturn = new ArrayList<>();
        for (Trend trend : trends
        ) {
            trendsToReturn.add(new TrendReturnDTO(trend));
        }
        return trendsToReturn;
    }

    /**
     * Gets trend.
     *
     * @param id the id
     * @return the trend
     */
    @GetMapping("/trend/{id}")
    public TrendReturnDTO getTrend(@PathVariable("id") long id) {
        Trend foundTrend = trendRep.findById(id);
        if (foundTrend != null) {
            return new TrendReturnDTO(foundTrend);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Trend with Id %s not found", id));
    }

    @GetMapping("/shared/trend/{id}")
    public TrendReturnDTO getSharedTrend(@PathVariable("id") long id) {
        return getTrend(id);
    }

    @GetMapping("/trend/ratings/{id}")
    public Set<Rating> getTrendRatings(@PathVariable("id") long id) {
        Trend foundTrend = trendRep.findById(id);
        if (foundTrend != null) {
            return foundTrend.getRatings();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Trend with Id %s not found", id));
    }


    /**
     * Create trend.
     *
     * @param requestBody the request body
     * @return the trend return dto
     */

    @PostMapping("/trend/")
    @ResponseStatus(HttpStatus.CREATED)
    public TrendReturnDTO createTrend(@Valid @RequestBody TrendCreationDTO requestBody) {

        return trendService.createNewTrend(requestBody);
    }

    /**
     * Update trend.
     *
     * @param id          the id
     * @param requestBody the request body
     * @return the trend return dto
     */
    @PutMapping("/trend/{id}")
    public TrendReturnDTO updateTrend(@PathVariable("id") long id, @Valid @RequestBody TrendCreationDTO requestBody) {
        Trend trendToUpdate = trendRep.findById(id);
        if (trendToUpdate != null) {
            return trendService.updateTrend(trendToUpdate, requestBody);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Trend with Id %s not found!", id));
    }

    /**
     * Delete trend.
     *
     * @param id the id
     */
    @DeleteMapping("/trend/{id}")
    public void deleteTrend(@PathVariable("id") long id) {
        Trend trendToRemove = trendRep.findById(id);
        if (trendToRemove!=null) {
            trendService.removeTrend(trendToRemove);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Trend with Id %s not found!", id));
    }
}
