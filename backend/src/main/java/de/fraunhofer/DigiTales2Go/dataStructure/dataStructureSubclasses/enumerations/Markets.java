package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;

/**
 * The enum Markets.
 *
 * @author Markus Walter
 */
public enum Markets {
    /**
     * Germany markets.
     */
    GERMANY("Deutschland"),
    /**
     * Eu markets.
     */
    EU("Europäische Union"),
    /**
     * Europe markets.
     */
    EUROPE("Europa"),
    /**
     * Usa markets.
     */
    USA("USA"),
    /**
     * Northamerica markets.
     */
    NORTHAMERICA("Nordamerika"),
    /**
     * Southamerica markets.
     */
    SOUTHAMERICA("Südamerika"),
    /**
     * Afrika markets.
     */
    AFRIKA("Afrika"),
    /**
     * Middleeast markets.
     */
    NEAREAST("Naher Osten"),
    MIDDLEEAST("Mittlerer Osten"),
    /**
     * Centralasia markets.
     */
    CENTRALASIA("Zentralasien"),
    /**
     * Southeastasia markets.
     */
    SOUTHEASTASIA("Südostasien"),

    CHINA("China"),

    INDIA("Indien"),

    AUSTRALIA("Australien"),

    GLOBAL("Global");

    private final String market;

    Markets(String market) {
        this.market = market;
    }

    public String getMarket() {
        return market;
    }
}
