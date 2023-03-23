package de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.commentLinkServices;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.Comment;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.CompanyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.ProjectRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.TechnologyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.TrendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Comment linker.
 */
@Service
public class CommentLinker {
    /**
     * The Tech rep.
     */
    @Autowired
    TechnologyRepository techRep;
    /**
     * The Trend rep.
     */
    @Autowired
    TrendRepository trendRep;
    /**
     * The Company rep.
     */
    @Autowired
    CompanyRepository companyRep;
    /**
     * The Project rep.
     */
    @Autowired
    ProjectRepository projectRep;

    /**
     * Link comment and user.
     *
     * @param comment the comment
     */
    public void linkCommentAndUser(Comment comment) {
        comment.getUser().addComment(comment);
    }

    /**
     * Link comment to item.
     *
     * @param comment the comment
     */
    public void linkCommentToItem(Comment comment) {
        switch (comment.getType()) {
            case TECHNOLOGY -> {
                Optional<Technology> tech = techRep.findById(comment.getItemID());
                if (tech.isPresent()){
                    tech.get().addComment(comment);
                }
            }
            case COMPANY -> {
                Optional<Company> company = companyRep.findById(comment.getItemID());
                if (company.isPresent()){
                    company.get().addComment(comment);
                }
            }
            case TREND -> {
                Optional<Trend> trend = trendRep.findById(comment.getItemID());
                if (trend.isPresent()){
                    trend.get().addComment(comment);
                }

            }
            case PROJECT -> {
                Optional<Project> project = projectRep.findById(comment.getItemID());
                if (project.isPresent()){
                    project.get().addComment(comment);
                }
            }
            case NOTSET -> throw new IllegalArgumentException("Cannot comment on a non-exsisting Item");
        }
    }
}
