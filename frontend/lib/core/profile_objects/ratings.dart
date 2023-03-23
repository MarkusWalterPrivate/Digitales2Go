import 'package:frontend/core/profile_objects/user.dart';
import 'package:frontend/widgets/feed/feed_item.dart';

class Rating {
  int id;
  String ratingValue;
  FeedItem item;


  Rating(
      {required this.id,
      required this.ratingValue,
      required this.item});

  factory Rating.fromJson(Map<String, dynamic> json) {
    return Rating(
      id: json['id'],
      ratingValue: json['ratingValue'],
      item: FeedItem.fromJson(json['item'], ""));
  }
}
