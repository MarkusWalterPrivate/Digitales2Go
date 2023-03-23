package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;

/**
 * The enum Alignment.
 *
 * @author Markus Walter
 */
public enum Alignment {
    /**
     * The B 2 b.
     */
    B2B("Business to Business"),
    /**
     * The B 2 c.
     */
    B2C("Business to Customer"),
    /**
     * The B 2 b 2 c.
     */
    B2B2C("Business to Business to Customer"),
    /**
     * The B 2 e.
     */
    B2E("Business to Employee"),
    /**
     * The B 2 g.
     */
    B2G("Business to Government");

    private final String alignmentText;

    Alignment(String alignment) {
        this.alignmentText = alignment;
    }

    /**
     * Gets alignment.
     *
     * @return the alignment
     */
    public String getAlignmentText() {
        return alignmentText;
    }
}
