package de.fraunhofer.DigiTales2Go.project;

import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.event.Event;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.ReadinessProject;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases.Runtime;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
 class ProjectTest {

    private final Long id = 42069L;
    private final CoreField coreField = new CoreField(true, "internSauce","ImageSource", "Headline", "Teaser", Industry.FINANCE, Arrays.asList("Tag1", "Tag2"), Type.TREND);


    private final Runtime runtime = new Runtime(1234567L, 12345678L, true);

    private final DetailedRating detailedRating = new DetailedRating(id, 1d, 1d, 1d);

    private final Contact contact = new Contact("Name", "Email", "Telephone", "Organisation");
    private final ArrayList<Contact> contacts = new ArrayList<>(Lists.list(contact));

    private final ProjectRequired projectRequired = new ProjectRequired("Description", ReadinessProject.DEVELOPMENT, runtime, contacts);

    private final ArrayList<Event> events = new ArrayList<>();

    @Test
    void constructMinimalProject () {
        Project project = new Project(coreField, projectRequired, null, events);

        assertEquals(coreField, project.getCoreField());
        assertEquals(projectRequired, project.getProjectRequired());
        assertEquals(events, project.getEvents());
        assertNull(project.getProjectOptional());
    }

    @Test
    void likeProject() {
        Project likeProject = new Project(coreField, projectRequired, null, events);
        Double oldRating = likeProject.getCoreField().getRating();

        likeProject.like();
        assertEquals(oldRating + 1, likeProject.getCoreField().getRating());
    }

    @Test
    void dislikeProject() {
        Project dislikeProject = new Project(coreField, projectRequired, null, events);
        Double oldRating = dislikeProject.getCoreField().getRating();

        dislikeProject.dislike();
        assertEquals(oldRating - 1, dislikeProject.getCoreField().getRating());
    }
}
