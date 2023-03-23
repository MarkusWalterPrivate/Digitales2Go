import 'package:flutter/material.dart';

class TagUtil {
  /// It returns an Icon based on the tagID.
  ///
  /// Args:
  ///   tagID (int): This is the ID of the tag.
  ///
  /// Returns:
  ///   The icon that is being returned is the icon that is associated with the
  /// tagID.
  static Icon? getTag(String tagName) {
    switch (tagName) {
      case "New":
        {
          return const Icon(Icons.fiber_new);
        }
      case "Trending":
        {
          return const Icon(Icons.trending_up);
        }

      case "Starred":
        {
          return const Icon(Icons.star_rate);
        }

      case "Loved":
        {
          return const Icon(Icons.favorite_border);
        }
      default:
        {
          return null;
        }
    }
  }
}
