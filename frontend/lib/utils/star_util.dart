import 'package:flutter/material.dart';

class StarUtil {
  static Row getStarRating(num rating) {
    Row row;
    var starList = [];

    for (var i = 0; i < 5; i++) {
      if (rating > .75) {
        starList.add(Icon(
          Icons.star,
          size: 15,
          color: Colors.amber[300],
        ));
      } else if (rating > 0.25) {
        starList.add( Icon(
          Icons.star_half,
          size: 15,
          color: Colors.amber[300],
        ));
      } else {
        starList.add( Icon(
          Icons.star_border,
          size: 15,
          color: Colors.amber[300],
        ));
      }
      rating--;
    }

    return Row(
      children: [
        for (var icon in starList)
          Padding(child: icon, padding: const EdgeInsets.only(right: 4))
      ],
    );
  }

  static Row getStarRatingWithTitle(num rating, String title) {
    Row row;
    var starList = [];

    for (var i = 0; i < 5; i++) {
      if (rating > .75) {
        starList.add( Icon(
          Icons.star,
          size: 12,
          color: Colors.amber[300],
        ));
      } else if (rating > 0.25) {
        starList.add( Icon(
          Icons.star_half,
          size: 12,
          color: Colors.amber[300],
        ));
      } else {
        starList.add( Icon(
          Icons.star_border,
          size: 14,
          color: Colors.amber[300],
        ));
      }
      rating--;
    }

    return Row(
      children: [
        Text(
          "$title ",
          style: const TextStyle(fontWeight: FontWeight.bold),
        ),
        for (var icon in starList)
          Padding(child: icon, padding: const EdgeInsets.only(right: 4))
      ],
    );
  }
}
