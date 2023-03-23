package de.fraunhofer.DigiTales2Go.dataStructure.bookmark;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.dtos.BookmarkFeedDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin
@Slf4j
public class BookmarkController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemLibrary itemLibrary;

    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    BookmarkService bookmarkService;

    @PostMapping("/bookmark/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void bookmarkItem(@PathVariable("id") long itemId, @AuthenticationPrincipal User loggedInUser) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(loggedInUser.getUsername());
        if (optionalUser.isPresent()) {
            List<Bookmark> bookmarks = optionalUser.get().getBookmarkedItems();
            for (Bookmark bookmark : bookmarks) {
                if (bookmark.getItemId() == itemId) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, ("Item already bookmarked"));
                }
            }
            Bookmark bookmark = new Bookmark(itemId, System.currentTimeMillis());
            itemLibrary.getItemDTO(itemId);
            bookmarkRepository.save(bookmark);
            AppUser user = optionalUser.get();
            user.addBookmarked(bookmark);
            userRepository.save(user);
        }
    }

    @DeleteMapping("/bookmark/{id}")
    public void deleteBookmark(@PathVariable("id") long itemId, @AuthenticationPrincipal User loggedInUser) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(loggedInUser.getUsername());
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            itemLibrary.getItemDTO(itemId);
            List<Bookmark> bookmarkWithId = user.getBookmarkedItems().stream().filter(bookmark -> bookmark.getItemId() == itemId).toList();
            user.removeBookmark(bookmarkWithId.get(0));
            userRepository.save(user);
        }
    }

    @PostMapping ("/bookmark/")
    public BookmarkFeedDTO getBookmarks(@AuthenticationPrincipal User loggedInUser, @Valid @RequestBody Pageable pageable) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(loggedInUser.getUsername());
        if (optionalUser.isPresent()) {
            log.info("User existed");
            AppUser user = optionalUser.get();
            List<Bookmark> unsortedBookmarks = user.getBookmarkedItems();
            log.info("found this many bookmarks: {}", unsortedBookmarks.size());
            log.info("Controller ItemsPerPage: {}",  pageable.getItemsPerPage());
            return bookmarkService.paginateBookmarks(unsortedBookmarks, pageable, optionalUser.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ("User not found"));
        }
    }



}
