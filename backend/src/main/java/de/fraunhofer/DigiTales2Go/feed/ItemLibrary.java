package de.fraunhofer.DigiTales2Go.feed;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The type Item library.
 * This Class stored all the SwipeDTOs and manipulates them for the controllers
 *
 * @author Markus Walter
 */
@Service
@Slf4j
public class ItemLibrary {
    private static final String ERROR = "Item with Id %s not found";

    private final List<ItemDTO> itemList = new ArrayList<>();

    private final List<ItemDTO> waitingForApproval = new ArrayList<>();

    /**
     * Adds a new Tech to List of itemList
     *
     * @param newTech the new tech
     */
    public void addTechSwipeDTO(Technology newTech) {
        ItemDTO newDTO = new ItemDTO(newTech);
        if (newTech.getCoreField().isApproved()) {
            itemList.add(newDTO);
        } else {
            waitingForApproval.add(newDTO);
        }

    }


    /**
     * Adds a new Trend to List of itemList
     *
     * @param newTrend the new trend
     */
    public void addTrendSwipeDTO(Trend newTrend) {
        ItemDTO newDTO = new ItemDTO(newTrend);
        if (newTrend.getCoreField().isApproved()) {
            itemList.add(newDTO);
        } else {
            waitingForApproval.add(newDTO);
        }
    }

    /**
     * Adds a new StartUp to List of itemList
     *
     * @param newCompany the new company
     */
    public void addCompanySwipeDTO(Company newCompany) {
        ItemDTO newDTO = new ItemDTO(newCompany);
        if (newCompany.getCoreField().isApproved()) {
            itemList.add(newDTO);
        } else {
            waitingForApproval.add(newDTO);
        }
    }

    /**
     * Adds a new Project to List of itemList
     *
     * @param newProject the new project
     */
    public void addProjectSwipeDTO(Project newProject) {
        ItemDTO newDTO = new ItemDTO(newProject);
        if (newProject.getCoreField().isApproved()) {
            itemList.add(newDTO);
        } else {
            waitingForApproval.add(newDTO);
        }
    }


    /**
     * Removes a Trend/Tech/StartUp/Project with given id
     *
     * @param id the id
     * @throws NoSuchElementException the no such element exception
     */
    public void removeSwipeDTO(long id) throws NoSuchElementException {
        List<ItemDTO> iterableItemList = new ArrayList<>(itemList);
        log.debug("Listsize {}", iterableItemList.size());
        for (ItemDTO tech : iterableItemList
        ) {
            log.debug("Item id = {}", tech.getId());
            if (tech.getId() == id) {
                log.debug("Found item");
                itemList.remove(tech);
                log.debug("Item removed from itemlist");
                return;
            }
        }
        List<ItemDTO> iterableWaitingForApprovalList = new ArrayList<>(waitingForApproval);
        log.debug("Listsize {}", iterableWaitingForApprovalList.size());
        for (ItemDTO tech : iterableWaitingForApprovalList
        ) {
            log.debug("Item id = {}", tech.getId());
            if (tech.getId() == id) {
                log.debug("Found item");
                waitingForApproval.remove(tech);
                log.debug("Item removed from approvalList");
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(ERROR, id));
    }

    /**
     * Edits a SwipeDTO that already exists fot that Tech with new params
     *
     * @param changedTech the changed tech
     * @throws NoSuchElementException the no such element exception
     */
    public void updateTechSwipeDTO(Technology changedTech) throws NoSuchElementException {
        for (ItemDTO tech : itemList
        ) {
            if (tech.getId() == changedTech.getId()) {
                tech.setCoreField(new CoreFieldReturnDTO(changedTech.getCoreField()));
                return;
            }
        }
        for (ItemDTO tech : waitingForApproval
        ) {
            if (tech.getId() == changedTech.getId()) {
                tech.setCoreField(new CoreFieldReturnDTO(changedTech.getCoreField()));
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(ERROR, changedTech.getId()));
    }

    /**
     * Edits a SwipeDTO that already exists fot that Trend with new params
     *
     * @param changedTrend the changed trend
     * @throws NoSuchElementException the no such element exception
     */
    public void updateTrendSwipeDTO(Trend changedTrend) throws NoSuchElementException {
        for (ItemDTO item : itemList
        ) {
            if (item.getId() == changedTrend.getId()) {
                item.setCoreField(new CoreFieldReturnDTO(changedTrend.getCoreField()));
                return;
            }
        }
        for (ItemDTO item : waitingForApproval) {
            if (item.getId() == changedTrend.getId()) {
                item.setCoreField(new CoreFieldReturnDTO(changedTrend.getCoreField()));
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(ERROR, changedTrend.getId()));
    }

    /**
     * Edits a SwipeDTO that already exists fot that StartUp with new params
     *
     * @param changedCompany the changed company
     * @throws NoSuchElementException the no such element exception
     */
    public void updateCompanySwipeDTO(Company changedCompany) throws NoSuchElementException {
        for (ItemDTO item : itemList
        ) {
            if (item.getId() == changedCompany.getId()) {
                item.setCoreField(new CoreFieldReturnDTO(changedCompany.getCoreField()));
                return;
            }
        }
        for (ItemDTO item : waitingForApproval
        ) {
            if (item.getId() == changedCompany.getId()) {
                item.setCoreField(new CoreFieldReturnDTO(changedCompany.getCoreField()));
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(ERROR, changedCompany.getId()));
    }

    /**
     * Edits a SwipeDTO that already exists fot that Project with new params
     *
     * @param changedProject the changed project
     * @throws NoSuchElementException the no such element exception
     */
    public void updateProjectSwipeDTO(Project changedProject) throws NoSuchElementException {
        for (ItemDTO item : itemList
        ) {
            if (item.getId() == changedProject.getId()) {
                item.setCoreField(new CoreFieldReturnDTO(changedProject.getCoreField()));
                return;
            }
        }
        for (ItemDTO item : waitingForApproval
        ) {
            if (item.getId() == changedProject.getId()) {
                item.setCoreField(new CoreFieldReturnDTO(changedProject.getCoreField()));
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(ERROR, changedProject.getId()));
    }

    /**
     * Gets swipe dto.
     *
     * @param id the id
     * @return SwipeDTO with given id
     * @throws NoSuchElementException the no such element exception
     */
    public ItemDTO getItemDTO(Long id) throws NoSuchElementException {
        for (ItemDTO item : itemList) {
            if (item.getId() == id) {
                return item;
            }
        }
        for (ItemDTO item : waitingForApproval) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(ERROR, id));
    }

    /**
     * Like swipe dto.
     *
     * @param id the id
     */
    public void likeSwipeDTO(Long id) {
        for (ItemDTO item : itemList) {
            if (item.getId() == id) {
                item.like();
                return;
            }
        }
        for (ItemDTO item : waitingForApproval) {
            if (item.getId() == id) {
                item.like();
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(ERROR, id));
    }

    /**
     * Dislike swipe dto.
     *
     * @param id the id
     */
    public void dislikeSwipeDTO(Long id) {
        for (ItemDTO item : itemList) {
            if (item.getId() == id) {
                item.dislike();
                return;
            }
        }
        for (ItemDTO item : waitingForApproval) {
            if (item.getId() == id) {
                item.dislike();
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(ERROR, id));
    }

    /**
     * Approve.
     *
     * @param id the id
     */
    public void approve(Long id) {
        List<ItemDTO> waiting = new ArrayList<>(waitingForApproval);
        boolean itemFound = false;
        for (ItemDTO item : waiting) {
            if (item.getId() == id) {
                itemList.add(item);
                waitingForApproval.remove(item);
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(ERROR, id));
        }

    }

    /**
     * Gets item list.
     *
     * @return the item list
     */
    public List<ItemDTO> getItemList() {
        return itemList;
    }

    /**
     * Gets waiting for approval.
     *
     * @return the waiting for approval
     */
    public List<ItemDTO> getWaitingForApproval() {
        return waitingForApproval;
    }
}
