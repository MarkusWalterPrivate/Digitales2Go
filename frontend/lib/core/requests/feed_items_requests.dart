import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:frontend/core/atomic_objects/return_dto.dart';
import 'package:frontend/core/detail_objects/bookmarked_item.dart';
import 'package:frontend/utils/toast_util.dart';

import '../../constants/url.dart';
import '../../widgets/feed/feed_item.dart';
import 'package:http/http.dart' as http;

class Requester {
  // TODO: rewrite method with additional attribute for paging
  static Future<FeedReturnDTO> getNewItems(String userToken) async {
    final responseHot = await http.post(
      Uri.parse('${Constants.getBackendUrl()}/api/v2/feed/new'),
      headers: {
        'Authorization': 'Bearer $userToken',
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, dynamic>{
        "totalItems": 0,
        "itemsPerPage": 100,
        "page": 0,
        "sorting": "NOTSET"
      }),
    );

    if (responseHot.statusCode == 200) {

      return FeedReturnDTO.fromJson(jsonDecode(utf8.decode(responseHot.bodyBytes)));
    } else {
      print("Status: " + responseHot.statusCode.toString());
      throw Exception('Could not load new feed');
    }
  }

  static Future<FeedReturnDTO> getHotItems(String userToken) async {
    final responseHot = await http
        .post(Uri.parse('${Constants.getBackendUrl()}/api/v2/feed/hot'),
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, dynamic>{
        "totalItems": 0,
        "itemsPerPage": 100,
        "page": 0,
        "sorting": "NOTSET"
      }),
    );

    if (responseHot.statusCode == 200) {
      return FeedReturnDTO.fromJson(jsonDecode(utf8.decode(responseHot.bodyBytes)));
    } else {
      print("Status: " + responseHot.statusCode.toString());
       throw Exception('Could not load hot feed');
    }
  }

   static Future<FeedReturnDTO> getInterestItems(String userToken) async {
    final responseInterest = await http
        .post(Uri.parse('${Constants.getBackendUrl()}/api/v2/feed/interest'),
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, dynamic>{
        "totalItems": 0,
        "itemsPerPage": 100,
        "page": 0,
        "sorting": "NOTSET"
      }),
    );

    if (responseInterest.statusCode == 200) {
      return FeedReturnDTO.fromJson(jsonDecode(utf8.decode(responseInterest.bodyBytes)));
    } else {
      print("Status: " + responseInterest.statusCode.toString());
       throw Exception('Could not load hot feed');
    }
  }

  static Future<void> setItemRelevant(String userToken, int itemID) async {
    final response = await http.post(
        Uri.parse("${Constants.getBackendUrl()}/api/v2/swipe/upvote/$itemID"),
        headers: {
          'Authorization': 'Bearer $userToken',
          'Accept': 'application/json; charset=UTF-8'
        });
    if (response.statusCode == 200) {
      print("------Set item as relevant---------------");
    } else {
      print("Could not set item as relevant");
    }
  }

  static Future<void> setItemIrrelevant(String userToken, int itemID) async {
    final response = await http.post(
        Uri.parse("${Constants.getBackendUrl()}/api/v2/swipe/downvote/$itemID"),
        headers: {
          'Authorization': 'Bearer $userToken',
          'Accept': 'application/json; charset=UTF-8'
        });
    if (response.statusCode == 200) {
      print("---------------Set item as irrelevant----------------");
    } else {
      print("Could not set item as irrelevant");
    }
  }

  static Future<void> setItemSkip(String userToken, int itemID) async {
    final response = await http.post(
        Uri.parse("${Constants.getBackendUrl()}/api/v2/swipe/ignore/$itemID"),
        headers: {
          'Authorization': 'Bearer $userToken',
          'Accept': 'application/json; charset=UTF-8'
        });
    if (response.statusCode == 200) {
      print(
          "-----------------------Set item as ignored-----------------------");
    } else {
      print("Could not set item as irrelevant");
    }
  }

  static Future<bool> setItemBookmarked(String userToken, int itemID,
      bool showToasts, BuildContext? contextForToast) async {
    final Color errorColor = Color(0xffffba1b1b);
    final toast = FToast().init(contextForToast!);
    final response = await http.post(
        Uri.parse("${Constants.getBackendUrl()}/api/v2/bookmark/$itemID"),
        headers: {
          'Authorization': 'Bearer $userToken',
          'Accept': 'application/json; charset=UTF-8'
        });
    if (response.statusCode == 201) {
      if (showToasts == true) {
        DisplayToast.showToast(
            toast,
            ToastUtil(
              toastColor: Colors.grey,
              toastText: 'Element wurde als Lesezeichen hinzugefügt',
              icon: const Icon(Icons.bookmark, color: Colors.white),
            ),
            ToastGravity.CENTER);
      }
      return Future.value(true);
    } else {
      if (showToasts == true) {
        DisplayToast.showToast(
            toast,
            ToastUtil(
              toastColor: errorColor,
              toastText:
                  'Element konnte nicht als Lesezeichen hinzugefügt werden',
              icon: const Icon(Icons.error_outline, color: Colors.white),
            ),
            ToastGravity.CENTER);
      }
      return Future.value(false);
    }
  }

  static Future<bool> deleteItemBookmarked(String userToken, int itemID,
      bool showToasts, BuildContext? contextForToast) async {
    final Color errorColor = Color(0xffffba1b1b);
    final toast = FToast().init(contextForToast!);
    final response = await http.delete(
        Uri.parse("${Constants.getBackendUrl()}/api/v2/bookmark/$itemID"),
        headers: {
          'Authorization': 'Bearer $userToken',
          'Accept': 'application/json; charset=UTF-8'
        });
    if (response.statusCode == 200) {
      if (showToasts == true) {
        DisplayToast.showToast(
            toast,
            ToastUtil(
              toastColor: Colors.grey,
              toastText: 'Element wurde aus den Lesezeichen entfernt',
              icon: const Icon(Icons.bookmark, color: Colors.white),
            ),
            ToastGravity.CENTER);
      }
      return Future.value(false);
    } else {
      if (showToasts == true) {
        DisplayToast.showToast(
            toast,
            ToastUtil(
              toastColor: errorColor,
              toastText:
                  'Element konnte nicht aus den Lesezeichen entfernt werden',
              icon: const Icon(Icons.error_outline, color: Colors.white),
            ),
            ToastGravity.CENTER);
      }
      return Future.value(true);
    }
  }

  // TODO: rewrite method to get bool if specific item is bookmarked
  static Future<bool> getItemBookmarked(String userToken, int itemID) async {
    bool existent = false;
    List<BookmarkedItem> itemsBookmarked = [];
    final response = await http.get(
        Uri.parse("${Constants.getBackendUrl()}/api/v2/bookmark/"),
        headers: {
          'Authorization': 'Bearer $userToken',
          'Accept': 'application/json; charset=UTF-8'
        });
    if (response.statusCode == 200) {
      itemsBookmarked = (json.decode(response.body) as List)
          .map((item) => BookmarkedItem.fromJson(item))
          .toList();
      for (var id in itemsBookmarked) {
        if (id.item.id == itemID) {
          existent = true;
        } else {
          existent = false;
        }
      }
      return existent;
    } else {
      print(response.statusCode);
      return Future.value(false);
    }
  }

  static Future<FeedItem> getFeedItemById(int id) async {
    final response = await http
        .get(Uri.parse('${Constants.getBackendUrl()}/api/v2/feed/$id'));
    if (response.statusCode == 200) {
      print(response.body);
      return FeedItem.fromJson(json.decode(response.body), '');
    } else {
      throw Exception('Could not get Feeditem with id: $id');
    }
  }
}
