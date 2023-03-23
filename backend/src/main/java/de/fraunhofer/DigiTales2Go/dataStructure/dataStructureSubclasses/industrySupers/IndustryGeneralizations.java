package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.industrySupers;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class IndustryGeneralizations {

    //Supersectors represent a generalization of Sectors(Industry enum)
    private static List<String> automobilesAndPartsSuperSector;
    private static List<String> bankSuperSector;
    private static List<String> basicResourcesSuperSector;
    private static List<String> chemicalsSuperSector;
    private static List<String> constructionAndMaterialsSuperSector;
    private static List<String> consumerProductsAndServicesSuperSector;
    private static List<String> energySuperSector;
    private static List<String> financialServicesSuperSector;
    private static List<String> foodBeverageTabaccoStaplesSuperSector;
    private static List<String> healthCareSuperSector;
    private static List<String> industrialGoodsAndServicesSuperSector;
    private static List<String> insuranceSuperSector;
    private static List<String> mediaSuperSector;
    private static List<String> personalCareDrugGroceryStoresSuperSector;
    private static List<String> realEstateSuperSector;
    private static List<String> retailSuperSector;
    private static List<String> technologySuperSector;
    private static List<String> telecommunicationSuperSector;
    private static List<String> travelAndLeisureSuperSector;
    private static List<String> utilitiesSuperSector;

    //Mastersectors represent a generalization of Supersectors
    private static final List<String> basicMaterialsMasterSector = new ArrayList<>();
    private static final List<String> consumerDiscretionaryMasterSector = new ArrayList<>();
    private static final List<String> consumerStaplesMasterSector = new ArrayList<>();
    private static final List<String> energyMasterSector = new ArrayList<>();
    private static final List<String> financialsMasterSector = new ArrayList<>();
    private static final List<String> healthCareMasterSector = new ArrayList<>();
    private static final List<String> industrialsMasterSector = new ArrayList<>();
    private static final List<String> realEstateMasterSector = new ArrayList<>();
    private static final List<String> technologyMasterSector = new ArrayList<>();
    private static final List<String> telecommunicationMasterSector = new ArrayList<>();
    private static final List<String> utilitiesMasterSector = new ArrayList<>();

    private static final List<List<String>> masterSectors = new ArrayList<>();
    private static final List<List<String>> superSectors = new ArrayList<>();

    private static void initSuperSectors() {
        automobilesAndPartsSuperSector = List.of(Industry.AUTOMOBILE.getIndustryName());
        bankSuperSector = List.of(Industry.BANK.getIndustryName());
        basicResourcesSuperSector = List.of(Industry.INDUSTRIALMATERIALS.getIndustryName(),
                Industry.INDUSTRIALMINING.getIndustryName(),
                Industry.PRECIOUSMINING.getIndustryName());
        chemicalsSuperSector = List.of(Industry.CHEMICALS.getIndustryName());
        constructionAndMaterialsSuperSector = List.of(Industry.CONSTRUCTION.getIndustryName());
        consumerProductsAndServicesSuperSector = List.of(Industry.CONSUMERSERVICE.getIndustryName(),
                Industry.HOUSEHOLDGOODS.getIndustryName(),
                Industry.LEISUREGOODS.getIndustryName(),
                Industry.PERSONALGOODS.getIndustryName());
        energySuperSector = List.of(Industry.OILGASCOAL.getIndustryName(),
                Industry.ALTERNATEENERGY.getIndustryName());
        financialServicesSuperSector = List.of(Industry.FINANCE.getIndustryName(),
                Industry.INVESTMENT.getIndustryName(),
                Industry.MORTGAGE.getIndustryName(),
                Industry.CLOSEDENDINVESTMENT.getIndustryName(),
                Industry.OPENENDINVESTMENT.getIndustryName());
        foodBeverageTabaccoStaplesSuperSector = List.of(Industry.BEVERAGE.getIndustryName(),
                Industry.FOOD.getIndustryName(),
                Industry.TABACCO.getIndustryName());
        healthCareSuperSector = List.of(Industry.HEALTHCAREPROVIDER.getIndustryName(),
                Industry.MEDICALEQUIPMENT.getIndustryName(),
                Industry.PHARMA.getIndustryName());
        industrialGoodsAndServicesSuperSector = List.of(Industry.AEROSPACE.getIndustryName(),
                Industry.ELECTRONIC.getIndustryName(),
                Industry.GENERALINDUSTRY.getIndustryName(),
                Industry.ENGINEERING.getIndustryName(),
                Industry.INDUSTRALSUPPORT.getIndustryName(),
                Industry.INDUSTRIALTRANSPORT.getIndustryName());
        insuranceSuperSector = List.of(Industry.LIFEINSURANCE.getIndustryName(),
                Industry.INSURANCE.getIndustryName());
        mediaSuperSector = List.of(Industry.MEDIA.getIndustryName());
        personalCareDrugGroceryStoresSuperSector = List.of(Industry.PERSONALCARE.getIndustryName());
        realEstateSuperSector = List.of(Industry.REALESTATEINVESTMENT.getIndustryName(),
                Industry.REALESTATESERVICE.getIndustryName());
        retailSuperSector = List.of(Industry.RETAIL.getIndustryName());
        technologySuperSector = List.of(Industry.SOFTWARE.getIndustryName(),
                Industry.HARDWARE.getIndustryName());
        telecommunicationSuperSector = List.of(Industry.TELECOMMUNICATIONEQUIPMENT.getIndustryName(),
                Industry.TELECOMMUNICATION.getIndustryName());
        travelAndLeisureSuperSector = List.of(Industry.TRAVEL.getIndustryName());
        utilitiesSuperSector = List.of(Industry.ELECTRICITY.getIndustryName(),
                Industry.GASWATER.getIndustryName(),
                Industry.WASTE.getIndustryName());

        superSectors.add(automobilesAndPartsSuperSector);
        superSectors.add(bankSuperSector);
        superSectors.add(basicResourcesSuperSector);
        superSectors.add(chemicalsSuperSector);
        superSectors.add(constructionAndMaterialsSuperSector);
        superSectors.add(consumerProductsAndServicesSuperSector);
        superSectors.add(energySuperSector);
        superSectors.add(financialServicesSuperSector);
        superSectors.add(foodBeverageTabaccoStaplesSuperSector);
        superSectors.add(healthCareSuperSector);
        superSectors.add(industrialGoodsAndServicesSuperSector);
        superSectors.add(insuranceSuperSector);
        superSectors.add(mediaSuperSector);
        superSectors.add(personalCareDrugGroceryStoresSuperSector);
        superSectors.add(realEstateSuperSector);
        superSectors.add(retailSuperSector);
        superSectors.add(technologySuperSector);
        superSectors.add(telecommunicationSuperSector);
        superSectors.add(travelAndLeisureSuperSector);
        superSectors.add(utilitiesSuperSector);
    }

    private static void initMasterSectors() {
        basicMaterialsMasterSector.addAll(basicResourcesSuperSector);
        basicMaterialsMasterSector.addAll(chemicalsSuperSector);

        consumerDiscretionaryMasterSector.addAll(automobilesAndPartsSuperSector);
        consumerDiscretionaryMasterSector.addAll(consumerProductsAndServicesSuperSector);
        consumerDiscretionaryMasterSector.addAll(mediaSuperSector);
        consumerDiscretionaryMasterSector.addAll(travelAndLeisureSuperSector);

        consumerStaplesMasterSector.addAll(foodBeverageTabaccoStaplesSuperSector);
        consumerStaplesMasterSector.addAll(personalCareDrugGroceryStoresSuperSector);

        energyMasterSector.addAll(energySuperSector);

        financialsMasterSector.addAll(bankSuperSector);
        financialsMasterSector.addAll(financialServicesSuperSector);
        financialsMasterSector.addAll(insuranceSuperSector);

        healthCareMasterSector.addAll(healthCareSuperSector);

        industrialsMasterSector.addAll(industrialGoodsAndServicesSuperSector);

        realEstateMasterSector.addAll(realEstateSuperSector);

        technologyMasterSector.addAll(technologySuperSector);

        telecommunicationMasterSector.addAll(telecommunicationSuperSector);

        utilitiesMasterSector.addAll(utilitiesSuperSector);

        masterSectors.add(basicMaterialsMasterSector);
        masterSectors.add(consumerDiscretionaryMasterSector);
        masterSectors.add(consumerStaplesMasterSector);
        masterSectors.add(energyMasterSector);
        masterSectors.add(financialsMasterSector);
        masterSectors.add(healthCareMasterSector);
        masterSectors.add(industrialsMasterSector);
        masterSectors.add(realEstateMasterSector);
        masterSectors.add(technologyMasterSector);
        masterSectors.add(telecommunicationMasterSector);
        masterSectors.add(utilitiesMasterSector);
    }

    public static List<String> getParentSector(String interest) {
        initSuperSectors();
        initMasterSectors();

        log.info("Inherited: {}", interest);
        log.info("Supersector: {}", technologySuperSector);
        List<String> parentSector = new ArrayList<>(superSectors.stream().filter(sSector -> sSector.contains(interest)).collect(Collectors.toList()).get(0));
        parentSector.remove(interest);

        return parentSector;
    }

    public static List<String> getMasterSector(String interest) {
        initSuperSectors();
        initMasterSectors();
        List<String> masterSector;

        masterSector = masterSectors.stream().filter(mSector -> mSector.contains(interest)).collect(Collectors.toList()).get(0);
        masterSector.removeAll(getParentSector(interest));
        masterSector.remove(interest);

        return masterSector;
    }

}
