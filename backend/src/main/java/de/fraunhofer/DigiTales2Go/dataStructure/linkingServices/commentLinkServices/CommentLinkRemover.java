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
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Comment link remover.
 */
@Service
public class CommentLinkRemover {

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
     * Remove comment from user.
     *
     * @param comment the comment
     */
    public void removeCommentFromUser(Comment comment) {
        AppUser user = comment.getUser();

        List<Comment> updatedComments = user.getComments();
        updatedComments.remove(comment);

        user.setComments(updatedComments);
    }


    /**
     * Remove comment from item.
     *
     * @param comment the comment
     */
    public void removeCommentFromItem(Comment comment) {

        switch (comment.getType()) {
            case TECHNOLOGY -> {
                Optional<Technology> tech = techRep.findById(comment.getItemID());
                if (tech.isPresent()){
                    List<Comment> updatedComments = tech.get().getComments();
                    updatedComments.remove(comment);
                    tech.get().setComments(updatedComments);
                }
            }
            case COMPANY -> {
                Optional<Company> company = companyRep.findById(comment.getItemID());
                if (company.isPresent()){
                    List<Comment> updatedComments = company.get().getComments();
                    updatedComments.remove(comment);
                    company.get().setComments(updatedComments);
                }
            }
            case TREND -> {
                Optional<Trend> trend = trendRep.findById(comment.getItemID());
                if (trend.isPresent()){
                    List<Comment> updatedComments = trend.get().getComments();
                    updatedComments.remove(comment);
                    trend.get().setComments(updatedComments);
                }
            }
            case PROJECT -> {
                Optional<Project> project = projectRep.findById(comment.getItemID());
                if (project.isPresent()){
                    List<Comment> updatedComments = project.get().getComments();
                    updatedComments.remove(comment);
                    project.get().setComments(updatedComments);
                }
            }
            case NOTSET -> throw new IllegalArgumentException("Cannot comment on a non-exsisting Item");
        }
    }
}
