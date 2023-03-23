/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;

/**
 * The enum Type.
 *
 * @author Markus Walter
 */
public enum Type {
    NOTSET("Nicht gesetzt"),
    /**
     * Trend typeText.
     */
    TREND("Trend"),
    /**
     * Company typeText.
     */
    COMPANY("Firma"),
    /**
     * Technology typeText.
     */
    TECHNOLOGY("Technologie"),
    /**
     * Project typeText.
     */
    PROJECT("Projekt");

    private final String typeText;

    Type(String type) {
        this.typeText = type;
    }

    /**
     * Gets typeText.
     *
     * @return the typeText
     */
    public String getType() {
        return typeText;
    }
}
