import 'package:frontend/core/atomic_objects/pageable.dart';
import 'package:frontend/core/detail_objects/bookmarked_item.dart';
import 'package:frontend/core/profile_objects/ratings.dart';
import 'package:frontend/widgets/feed/feed_item.dart';

class BookmarkFeedDTO {
  Pageable pageable;
  List<BookmarkedItem> feed;

  BookmarkFeedDTO({required this.feed, required this.pageable});

  factory BookmarkFeedDTO.fromJson(Map<String, dynamic> json) {
    var bookmarkedItemsList = json['feed'] as List;
    List<BookmarkedItem> bookmarkedItems = bookmarkedItemsList.map((e) => BookmarkedItem.fromJson(e)).toList();

    return BookmarkFeedDTO (
      feed: bookmarkedItems,
      pageable: Pageable.fromJson(json['pageable']),
    );
  }
}

class RatingFeedDTO {
  Pageable pageable;
  List<Rating> feed;

  RatingFeedDTO({required this.feed, required this.pageable});

  factory RatingFeedDTO.fromJson(Map<String, dynamic> json) {
    var ratingItemsList = json['feed'] as List;
    List<Rating> ratedItems = ratingItemsList.map((e) => Rating.fromJson(e)).toList();

    return RatingFeedDTO (
      feed: ratedItems,
      pageable: Pageable.fromJson(json['pageable']),
    );
  }
}

class FeedReturnDTO {
  Pageable pageable;
  List<FeedItem> feed;

  FeedReturnDTO({required this.feed, required this.pageable});

  factory FeedReturnDTO.fromJson(Map<String, dynamic> json) {
    var feedItemsList = json['feed'] as List;
    List<FeedItem> ratedItems = feedItemsList.map((e) => FeedItem.fromJson(e, '')).toList();
    return FeedReturnDTO (
      feed: ratedItems,
      pageable: Pageable.fromJson(json['pageable']),
    );
  }
}
