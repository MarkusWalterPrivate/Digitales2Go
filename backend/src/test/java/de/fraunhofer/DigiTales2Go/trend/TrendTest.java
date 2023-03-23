package de.fraunhofer.DigiTales2Go.trend;

import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.CoreField;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRating;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.subclasses.TrendRequired;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

 class TrendTest {

    private final Long id = 42069L;
    private final CoreField coreField = new CoreField(true, "internSauce","ImageSource", "Headline", "Teaser", Industry.FINANCE, Arrays.asList("Tag1", "Tag2"), Type.TREND);


    private final DetailedRating detailedRating = new DetailedRating(id, 1d, 1d, 1d);

    private final TrendRequired trendRequired = new TrendRequired("Description", "Discussion");

    private final List<Comment> comments = new ArrayList<>();

    private final Trend fullBackUpTrend = new Trend(id, coreField, detailedRating , trendRequired, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(),  comments, new HashSet<>());
    @Test
    void createBackupTrend() {
        Trend backUpTrend = fullBackUpTrend;

        assertEquals(backUpTrend.getId(), id);
        assertEquals(backUpTrend.getCoreField(), coreField);
        assertEquals(backUpTrend.getDetailedRating(), detailedRating);
        assertEquals(backUpTrend.getTrendRequired(), trendRequired);
        assertNull(backUpTrend.getTrendOptional());
        assertEquals(backUpTrend.getComments(), comments);
    }

    @Test
    void likeTrend() {
        Trend likeTrend = fullBackUpTrend;
        Double oldRating = likeTrend.getCoreField().getRating();

        likeTrend.like();
        assertEquals(oldRating + 1, likeTrend.getCoreField().getRating());
    }

    @Test
    void dislikeTrend() {
        Trend dislikeTrend = fullBackUpTrend;
        Double oldRating = dislikeTrend.getCoreField().getRating();

        dislikeTrend.dislike();
        assertEquals(oldRating - 1, dislikeTrend.getCoreField().getRating());
    }

    @Test
    void setAttributes () {
        Trend nullTrend = new Trend();

        nullTrend.setId(id);
        nullTrend.setCoreField(coreField);
        nullTrend.setDetailedRating(detailedRating);
        nullTrend.setTrendRequired(trendRequired);
        nullTrend.setComments(comments);

        assertEquals(fullBackUpTrend.getId(), nullTrend.getId());
        assertEquals(fullBackUpTrend.getCoreField(), nullTrend.getCoreField());
        assertEquals(fullBackUpTrend.getDetailedRating(), nullTrend.getDetailedRating());
        assertEquals(fullBackUpTrend.getTrendRequired(), nullTrend.getTrendRequired());
        assertEquals(fullBackUpTrend.getComments(), nullTrend.getComments());
    }
}
