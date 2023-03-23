import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:frontend/core/atomic_objects/return_dto.dart';
import 'package:frontend/core/requests/feed_items_requests.dart';
import 'package:frontend/widgets/feed/feed_item.dart';
import '../../constants/url.dart';
import 'feed_row.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/foundation.dart';

///Representing the Feedview with FeedRows containing FeedItems
///
///Args:
///   List<FeedRow> feedRows: The FeedRows to show in the Feed
class Feed extends StatefulWidget {
  Feed({Key? key, required this.feedRows, required this.userToken})
      : super(key: key);
  final List<FeedRow> feedRows;
  String userToken;
  @override
  State<Feed> createState() => _FeedState();
}

class _FeedState extends State<Feed> {
  late Future<List<FeedRow>> futureFeedRows;
  final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);

  @override
  void initState() {
    super.initState();
    futureFeedRows = fetchFeedRows(widget.userToken);
  }

  /// > The function returns a column widget that contains a column widget that
  /// contains a list of feed rows
  ///
  /// Args:
  ///   context (BuildContext): The context of the widget.
  ///
  /// Returns:
  ///   A column of feed rows.
  @override
  Widget build(BuildContext context) {
    final Color primaryColor = Theme.of(context).primaryColor;
    return FutureBuilder<List<FeedRow>>(
        future: futureFeedRows,
        builder: (context, snapShot) {
          if (snapShot.hasData) {
            return Column(
              children: [
                Container(
                    child: Column(
                  children: [
                    for (FeedRow row in snapShot.data!)
                      Column(
                        children: [
                          FeedRow(
                              feedItems: row.feedItems,
                              feedName: row.feedName,
                              userToken: widget.userToken),
                          if (kIsWeb)
                            Divider(
                              thickness: 2,
                              color: primaryColor,
                              height: 1,
                            )
                          else
                            Divider(
                              thickness: 3,
                              color: primaryColor,
                              height: 1,
                            )
                        ],
                      ),
                  ],
                ))
              ],
            );
          } else if (snapShot.hasError) {
            return Column(
              children: [
                Container(
                    child: Column(
                  children: [
                    for (FeedRow row in widget.feedRows)
                      Column(
                        children: [
                          FeedRow(
                            feedItems: row.feedItems,
                            feedName: row.feedName,
                            userToken: widget.userToken,
                          ),
                        ],
                      ),
                  ],
                ))
              ],
            );
          } else {
            return const CircularProgressIndicator();
          }
        });
  }

  /// Fetching all FeedRows from backend
  ///
  /// Returns:
  ///   A List of FeedRows
  Future<List<FeedRow>> fetchFeedRows(String userToken) async {
    FeedReturnDTO hotFeed = await Requester.getHotItems(userToken);
    FeedReturnDTO newsFeed = await Requester.getNewItems(userToken);
    FeedReturnDTO interestFeed = await Requester.getInterestItems(userToken);

    FeedRow hotRow = FeedRow(feedItems: hotFeed.feed, userToken: userToken, feedName: 'Trending');
    FeedRow newRow = FeedRow(feedItems: newsFeed.feed, userToken: userToken, feedName: 'Neu');
    FeedRow interestRow =
        FeedRow(feedItems: interestFeed.feed, userToken: userToken, feedName: 'Für dich');

    if (hotRow.feedItems.isNotEmpty | newRow.feedItems.isNotEmpty ||
        interestRow.feedItems.isNotEmpty) {
      List<FeedRow> feedRows = [hotRow, newRow, interestRow];
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return feedRows;
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load Feeditems');
    }
  }
}
