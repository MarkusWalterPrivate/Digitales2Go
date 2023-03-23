package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_CREATOR("ROLE_CREATOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
