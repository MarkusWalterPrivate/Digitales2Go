package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;

/**
 * The enum Industry.
 *
 * @author Markus Walter
 */
public enum Industry {
    NOTSET("Nicht gesetzt"),
    /**
     * The Software.
     */
    SOFTWARE("Software und Computerdienstleistungen"),
    /**
     * The Hardware.
     */
    HARDWARE("Technologie Hardware und Ausrüstung"),
    /**
     * The Telecommunicationequipment.
     */
    TELECOMMUNICATIONEQUIPMENT("Telekommunikationseinrichtungen"),
    /**
     * The Telecommunication.
     */
    TELECOMMUNICATION("Anbieter von Telekommunikationsdiensten"),
    /**
     * The Healthcareprovider.
     */
    HEALTHCAREPROVIDER("Gesundheitsdienstleister"),
    /**
     * The Medicalequipment.
     */
    MEDICALEQUIPMENT("Medizinische Geräte und Dienstleistungen"),
    /**
     * The Pharma.
     */
    PHARMA("Pharmazeutika und Biotechnologie"),
    /**
     * Bank industryString.
     */
    BANK("Bank"),
    /**
     * The Finance.
     */
    FINANCE("Finanz- und Kreditdienstleistungen"),
    /**
     * The Investment.
     */
    INVESTMENT("Investmentbanking und Brokerage-Dienstleistungen"),
    /**
     * The Mortgage.
     */
    MORTGAGE("Hypotheken-Immobilien-Investmentfonds"),
    /**
     * The Closedendinvestment.
     */
    CLOSEDENDINVESTMENT("Geschlossene Beteiligungen"),
    /**
     * The Openendinvestment.
     */
    OPENENDINVESTMENT("Open End and Miscellaneous Investment Vehicles"),
    /**
     * The Lifeinsurance.
     */
    LIFEINSURANCE("Lebensversicherung"),
    /**
     * The Insurance.
     */
    INSURANCE("Versicherungen"),
    /**
     * The Realestateservice.
     */
    REALESTATESERVICE("Immobilieninvestitionen und Dienstleistungen"),
    /**
     * The Realestateinvestment.
     */
    REALESTATEINVESTMENT("Immobilien-Investmentfonds"),
    /**
     * The Automobile.
     */
    AUTOMOBILE("Automobile und Teile"),
    /**
     * The Consumerservice.
     */
    CONSUMERSERVICE("Consumer Services"),
    /**
     * The Householdgoods.
     */
    HOUSEHOLDGOODS("Haushaltswaren und Wohnungsbau"),
    /**
     * The Leisuregoods.
     */
    LEISUREGOODS("Freizeitgüter"),
    /**
     * The Personalgoods.
     */
    PERSONALGOODS("Persönliche Güter"),
    /**
     * Media industryString.
     */
    MEDIA("Medien"),
    /**
     * Retail industryString.
     */
    RETAIL("Wiederverkäufer"),
    /**
     * The Travel.
     */
    TRAVEL("Reisen und Freizeit"),
    /**
     * Beverage industryString.
     */
    BEVERAGE("Getränke"),
    /**
     * The Food.
     */
    FOOD("Lebensmittelproduzent"),
    /**
     * Tabacco industryString.
     */
    TABACCO("Tabacco"),
    /**
     * The Personalcare.
     */
    PERSONALCARE("Körperpflege-, Drogerie- und Lebensmittelläden"),
    /**
     * The Construction.
     */
    CONSTRUCTION("Konstruktion und Materialien"),
    /**
     * The Aerospace.
     */
    AEROSPACE("Luft- und Raumfahrt und Verteidigung"),
    /**
     * The Electronic.
     */
    ELECTRONIC("Elektronische und elektrische Geräte"),
    /**
     * The Generalindustry.
     */
    GENERALINDUSTRY("Allgemeine Industriezweige"),
    /**
     * The Engineering.
     */
    ENGINEERING("Wirtschaftsingenieurwesen"),
    /**
     * The Industralsupport.
     */
    INDUSTRALSUPPORT("Industrielle Unterstützungsdienste"),
    /**
     * The Industrialtransport.
     */
    INDUSTRIALTRANSPORT("Industrieller Transport"),
    /**
     * The Industrialmaterials.
     */
    INDUSTRIALMATERIALS("Industrielle Materialien"),
    /**
     * The Industrialmining.
     */
    INDUSTRIALMINING("Industriemetalle und Bergbau"),
    /**
     * The Industrialmining.
     */
    PRECIOUSMINING("Precious Metals and Mining"),
    /**
     * Chemicals industryString.
     */
    CHEMICALS("Chemikalien"),
    /**
     * The Oilgascoal.
     */
    OILGASCOAL("Öl, Gas und Kohle"),
    /**
     * The Alternateenergy.
     */
    ALTERNATEENERGY("Alternative Energie"),
    /**
     * Electricity industryString.
     */
    ELECTRICITY("Elektrizität"),
    /**
     * The Gaswater.
     */
    GASWATER("Gas, Wasser und Multi-Utilities"),
    /**
     * The Waste.
     */
    WASTE("Abfall- und Entsorgungsdienstleistungen");

    private final String industryString;

    Industry(String industryString) {
        this.industryString = industryString;
    }

    /**
     * Gets industryString.
     *
     * @return the industryString
     */
    public String getIndustryName() {
        return industryString;
    }


}
