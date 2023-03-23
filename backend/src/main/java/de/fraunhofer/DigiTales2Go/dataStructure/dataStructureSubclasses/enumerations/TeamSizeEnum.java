/**
 * @author Markus Walter
 */

package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;


/**
 * The enum Team size enum.
 *
 * @author Markus Walter
 */
public enum TeamSizeEnum {
    /**
     * Tiny team size enum.
     */
    TINY("Kleinstunternehmen"),
    /**
     * Small team size enum.
     */
    SMALL("Kleines Unternehmen"),
    /**
     * Medium team size enum.
     */
    MEDIUM("Mittelstand"),
    /**
     * Large team size enum.
     */
    LARGE("Großunternehmen");

    private final String size;

    TeamSizeEnum(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
