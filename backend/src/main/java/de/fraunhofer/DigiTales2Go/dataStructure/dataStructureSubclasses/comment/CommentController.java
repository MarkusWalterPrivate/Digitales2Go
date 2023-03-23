package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.dtos.CommentCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.comment.dtos.CommentReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Type;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.Company;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.CompanyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.Project;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.ProjectRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.Technology;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.TechnologyRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.Trend;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.TrendRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.commentLinkServices.CommentLinkRemover;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.commentLinkServices.CommentLinker;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@Slf4j
public class CommentController {

    @Autowired
    TechnologyRepository techRep;
    @Autowired
    TrendRepository trendRep;
    @Autowired
    CompanyRepository companyRep;
    @Autowired
    ProjectRepository projectRep;
    @Autowired
    ItemLibrary itemLibrary;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentLinker commentLinker;
    @Autowired
    CommentLinkRemover commentLinkRemover;

    @PostMapping("/comment/")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentReturnDTO addComment(@Valid @RequestBody CommentCreationDTO requestBody, @AuthenticationPrincipal User loggedInUser) {

        Comment comment = new Comment();

        Optional<AppUser> optionalCommentingUser = userRepository.findByEmail(loggedInUser.getUsername());

        Type type = requestBody.getType();

        if (optionalCommentingUser.isPresent()) {
            AppUser commentingUser = optionalCommentingUser.get();
            comment.setType(type);
            comment.setContent(requestBody.getContent());
            comment.setItemID(requestBody.getItemId());
            comment.setUser(commentingUser);
            Comment savedCommend = commentRepository.save(comment);
            commentLinker.linkCommentAndUser(savedCommend);
            commentLinker.linkCommentToItem(savedCommend);
            return new CommentReturnDTO(comment);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/comment/{id}")
    public List<CommentReturnDTO> getComments(@PathVariable("id") long itemId) {
        String type = itemLibrary.getItemDTO(itemId).getCoreField().getType();

        List<Comment> comments;
        switch (type) {
            case "Technology" -> {
                Technology tech = techRep.findById(itemId);
                comments = tech.getComments();
            }
            case "Company" -> {
                Company company = companyRep.findById(itemId);
                comments = company.getComments();
            }
            case "Trend" -> {
                Trend trend = trendRep.findById(itemId);
                comments = trend.getComments();
            }
            case "Project" -> {
                Project project = projectRep.findById(itemId);
                comments = project.getComments();
            }
            default -> throw new IllegalArgumentException("Cannot comment on a non-exsisting Item");
        }
        List<CommentReturnDTO> returnComments = new ArrayList<>();

        for (Comment comment : comments) {
            CommentReturnDTO tempComment = new CommentReturnDTO(comment);
            tempComment.setItem(itemLibrary.getItemDTO(itemId));
            returnComments.add(tempComment);
        }
        return returnComments;
    }

    @PutMapping("/comment/{id}")
    public CommentReturnDTO editComment(@Valid @RequestBody String requestBody, @PathVariable("id") long commentId) {

        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(requestBody);
            return new CommentReturnDTO(comment);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Comment with id %s not found", commentId));
        }
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable("id") long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            commentLinkRemover.removeCommentFromUser(comment);
            commentLinkRemover.removeCommentFromItem(comment);
            commentRepository.deleteById(commentId);
        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Comment with id %s not found", commentId));
        }
    }
}
