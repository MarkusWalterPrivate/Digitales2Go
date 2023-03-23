package de.fraunhofer.DigiTales2Go.dataStructure.bookmark;

import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.dtos.BookmarkFeedDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.bookmark.dtos.BookmarkReturnDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Sorting;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.Rating;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.feed.DTOs.ItemDTO;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class BookmarkService {
    @Autowired
    ItemLibrary itemLibrary;

    public BookmarkFeedDTO paginateBookmarks(List<Bookmark> bookmarks, Pageable params, AppUser user){
        List<BookmarkReturnDTO> sortedBookmarks = new ArrayList<>();
        BookmarkFeedDTO returnDTO = new BookmarkFeedDTO();
        for (Bookmark bookmark : bookmarks) {

            BookmarkReturnDTO returnBookmark = new BookmarkReturnDTO(bookmark);
            ItemDTO item;
            try {
                item = new ItemDTO(itemLibrary.getItemDTO(bookmark.getItemId())); //New ItemDTO in order to set Bookmarked Flag on user basis.
                returnBookmark.setItem(setFlagsOfSingleItem(item, user));
                log.info("Bookmark of item: {}", returnBookmark.getItem().getId());
                sortedBookmarks.add(returnBookmark);
            }catch(Exception e){
                log.error("Item belonging to bookmark not found");
            }
        }
        log.info("PaginateBookmarks ItemsPerPage: {}", params.getItemsPerPage());
        sortBookmarks(sortedBookmarks, params.getSorting());
        params.setTotalItems(sortedBookmarks.size());
        log.info("PaginateBookmarks 2 ItemsPerPage: {}", params.getItemsPerPage());
        if (params.getItemsPerPage()<sortedBookmarks.size()){
            if((params.getItemsPerPage()* (params.getPage()+1))< sortedBookmarks.size()){
                log.info("page requested");
                // If any pages before the last are requested
                returnDTO.setFeed(sortedBookmarks.subList(params.getItemsPerPage()* params.getPage(), params.getItemsPerPage()* (params.getPage()+1)));

            } else if ((params.getItemsPerPage()* params.getPage())<sortedBookmarks.size()) {
                log.info("last page requested");
                //last page is requested: If page is full itemPerPage*(page) is itemList.size, else its itemList is smaller;
                returnDTO.setFeed(sortedBookmarks.subList(params.getItemsPerPage()* params.getPage(), sortedBookmarks.size()));
            } else {
                log.info("page out of bounds");
                //If more items are requested then available in the feed
                returnDTO.setFeed(new ArrayList<>());
            }
        }else {
            returnDTO.setFeed(sortedBookmarks);
        }
        log.info("PaginateBookmarks 3 ItemsPerPage: {}", params.getItemsPerPage());
        returnDTO.setPageable(params);
        log.info("PaginateBookmarks 4 ItemsPerPage: {}", returnDTO.getPageable().getItemsPerPage());
        return returnDTO;
    }
    private ItemDTO setFlagsOfSingleItem (ItemDTO returnItem, AppUser user){
        for (Rating rating : user.getRatings()) {
            if (rating.getItemID().equals(returnItem.getId())) {
                returnItem.setRated(true);
                returnItem.setPriorRating(rating.getRatingValue());
            }
        }
        for (Bookmark bookmark : user.getBookmarkedItems()) {
            if (bookmark.getItemId().equals(returnItem.getId())) {
                returnItem.setBookmarked(true);
            }
        }
        return returnItem;
    }
    private List<BookmarkReturnDTO> sortBookmarks(List<BookmarkReturnDTO> bookmarks, Sorting sortingOrder) {
        if (sortingOrder != null) {
            log.info("sortingOrder is: {}", sortingOrder);
            switch (sortingOrder) {
                case NEWEST -> {
                    bookmarks.sort(Comparator.comparing(BookmarkReturnDTO::getCreationDate));
                    Collections.reverse(bookmarks);
                }
                case NEWESTITEM -> {
                    bookmarks.sort(Comparator.comparing(BookmarkReturnDTO::getItemCreationDate));
                    Collections.reverse(bookmarks);
                }
                case OLDESTITEM ->
                    bookmarks.sort(Comparator.comparing(BookmarkReturnDTO::getItemCreationDate));

                case HIGHESTRATING -> {
                    bookmarks.sort(Comparator.comparing(BookmarkReturnDTO::getRating));
                    Collections.reverse(bookmarks);
                }
                case LOWESTRATING ->
                    bookmarks.sort(Comparator.comparing(BookmarkReturnDTO::getRating));

                default ->         log.info("sortingOrder is: null");
            }
        }

        log.info("sorted");
        return bookmarks;
    }
}
