/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;

/**
 * The enum Readiness.
 *
 * @author Markus Walter
 */
public enum Readiness {
    NOTSET("Nicht gesetzt"),
    /**
     * Theory readinessString.
     */
    THEORY("Theorie belegt"),
    /**
     * Development readinessString.
     */
    DEVELOPMENT("In Entwicklung"),
    /**
     * Ready readinessString.
     */
    READY("Bereit für die Nutzung"),
    /**
     * Commercial readinessString.
     */
    COMMERCIAL("In kommerzieller Nutzung"),
    /**
     * Dated readinessString.
     */
    DATED("Übeholt");

    private final String readinessString;

    public String getReadinessString() {
        return readinessString;
    }

    Readiness(String readinessString) {
        this.readinessString = readinessString;
    }
}
