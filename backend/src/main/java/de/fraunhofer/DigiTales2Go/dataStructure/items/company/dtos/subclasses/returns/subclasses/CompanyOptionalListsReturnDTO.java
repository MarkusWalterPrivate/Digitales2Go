package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns.subclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Alignment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Markets;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses.CompanyOptionalLists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Company optional lists return dto.
 */
@Data
@NoArgsConstructor
public class CompanyOptionalListsReturnDTO {
    private Long id;
    private List<Contact> contacts;

    private List<String> targetMarkets;

    private List<String> alignments;

    private List<String> partners;

    private List<String> investors;


    /**
     * Instantiates a new Company optional lists return dto.
     *
     * @param lists the lists
     */
    public CompanyOptionalListsReturnDTO(CompanyOptionalLists lists) {
        this.id = lists.getId();
        this.contacts = lists.getContacts();
        this.targetMarkets = createMarketsList(lists.getTargetMarkets());
        this.alignments = createAlignmentList(lists.getAlignments());
        this.partners = lists.getPartners();
        this.investors = lists.getInvestors();
    }

    private List<String> createAlignmentList(List<Alignment> alignments) {
        List<String> alignmentNames = new ArrayList<>();
        for (Alignment alignment : alignments
        ) {
            alignmentNames.add(alignment.getAlignmentText());

        }
        return alignmentNames;
    }

    private List<String> createMarketsList(List<Markets> markets) {
        List<String> marktesNames = new ArrayList<>();
        for (Markets market : markets
        ) {
            marktesNames.add(market.getMarket());
        }
        return marktesNames;
    }
}
