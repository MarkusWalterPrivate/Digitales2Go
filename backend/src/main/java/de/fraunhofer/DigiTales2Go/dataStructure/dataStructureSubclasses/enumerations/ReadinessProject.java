/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;

/**
 * The enum Readiness project.
 *
 * @author Markus Walter
 */
public enum ReadinessProject {
    /**
     * Development readiness project.
     */
    DEVELOPMENT("In Entwicklung"),
    /**
     * Finished readiness project.
     */
    FINISHED("Fertig"),
    //Gedanke: bei Projekten ohne Endziel, z.B. Svalbard Global Seed Vault
    ONGOING("Fortlauend"),
    NOTSTARTED("Ausstehend"),
    NOTSET("Nicht gesetzt");

    private final String readiness;

    public String getReadiness() {
        return readiness;
    }

    ReadinessProject(String readiness) {
        this.readiness = readiness;
    }

}
