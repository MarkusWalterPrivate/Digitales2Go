package de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.optionalSubclasses;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Alignment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Markets;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.optional.CompanyOptionalListsCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Company optional lists.
 *
 * @author Markus Walter
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_optional_lists")
public class CompanyOptionalLists {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();
    @ElementCollection // 1
    @CollectionTable(name = "company_markets", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "markets") //
    private List<Markets> targetMarkets = new ArrayList<>();
    @ElementCollection // 1
    @CollectionTable(name = "company_alignments", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "alignments") //
    private List<Alignment> alignments = new ArrayList<>();
    @ElementCollection // 1
    @CollectionTable(name = "company_partners", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "partners") //
    private List<String> partners = new ArrayList<>();
    @ElementCollection // 1
    @CollectionTable(name = "company_investors", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "investors") //
    private List<String> investors = new ArrayList<>();


    /**
     * Constructor for creating an epmty existing List object
     *
     * @param id the id
     */
    public CompanyOptionalLists(Long id) {
        this.id = id;
    }

    /**
     * Constructor for Demo
     *
     * @param contacts      the contacts
     * @param targetMarkets the target markets
     * @param alignments    the alignments
     * @param partners      the partners
     * @param investors     the investors
     */
    public CompanyOptionalLists(List<Contact> contacts, List<Markets> targetMarkets, List<Alignment> alignments, List<String> partners, List<String> investors) {
        this.contacts = contacts;
        this.targetMarkets = targetMarkets;
        this.alignments = alignments;
        this.partners = partners;
        this.investors = investors;
    }

    /**
     * Constructor from Creation DTO
     *
     * @param optionalLists the optional lists
     */
    public CompanyOptionalLists(CompanyOptionalListsCreationDTO optionalLists) {
        if (optionalLists.getContacts() != null) {
            this.contacts = optionalLists.getContacts();
        }
        if (optionalLists.getTargetMarkets() != null) {
            this.targetMarkets = optionalLists.getTargetMarkets();
        }
        if (optionalLists.getAlignments() != null) {
            this.alignments = optionalLists.getAlignments();
        }
        if (optionalLists.getPartners() != null) {
            this.partners = optionalLists.getPartners();
        }
        if (optionalLists.getInvestors() != null) {
            this.investors = optionalLists.getInvestors();
        }
    }

    /**
     * Constructor from Editing DTO
     *
     * @param optionalLists the optional lists
     * @param listsId       the lists id
     */
    public CompanyOptionalLists(CompanyOptionalListsCreationDTO optionalLists, Long listsId) {
        this.id = listsId;
        if (optionalLists.getContacts() != null) {
            this.contacts = optionalLists.getContacts();
        }
        if (optionalLists.getTargetMarkets() != null) {
            this.targetMarkets = optionalLists.getTargetMarkets();
        }
        if (optionalLists.getAlignments() != null) {
            this.alignments = optionalLists.getAlignments();
        }
        if (optionalLists.getPartners() != null) {
            this.partners = optionalLists.getPartners();
        }
        if (optionalLists.getInvestors() != null) {
            this.investors = optionalLists.getInvestors();
        }
    }
}
