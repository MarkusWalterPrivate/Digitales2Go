import 'package:frontend/widgets/feed/feed_item.dart';

class BookmarkedItem {
  int id;
  FeedItem item;
  int creationDate;

  BookmarkedItem(
      {required this.id, required this.item, required this.creationDate});

  factory BookmarkedItem.fromJson(Map<String, dynamic> json) {
    return BookmarkedItem(
        id: json['id'],
        item: FeedItem.fromJson(json['item'], ''),
        creationDate: json['creationDate']);
  }
}
