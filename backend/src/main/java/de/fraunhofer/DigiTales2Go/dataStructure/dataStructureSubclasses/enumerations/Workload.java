/**
 * @author Markus Walter
 */

package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;


/**
 * The enum Workload.
 *
 * @author Markus Walter
 */
public enum Workload {
    /**
     * Taking workloadText.
     */
    TAKING("Nimmt Aufträge an"),
    /**
     * Full workloadText.
     */
    FULL("Momentan ausgebucht"),
    /**
     * Backlogged workloadText.
     */
    BACKLOGGED("Langfristig ausgebucht");

    private final String workloadText;

    Workload(String workload) {
        this.workloadText = workload;
    }

    public String getWorkload() {
        return workloadText;
    }
}
