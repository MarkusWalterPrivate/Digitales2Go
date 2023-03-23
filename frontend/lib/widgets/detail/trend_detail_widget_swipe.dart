import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:like_button/like_button.dart';
import 'package:share_plus/share_plus.dart';
import '../../constants/url.dart';
import '../../core/detail_objects/trend/scroll_to_hide_widget.dart';
import '../../core/detail_objects/trend/trend_contact_elapse.dart';
import '../../core/detail_objects/trend/trend_detail_obj.dart';
import 'package:http/http.dart' as http;

import '../feed/feed_item.dart';
import '../misc/commentBox.dart';
import '../swipe/swipe_bar.dart';
import '../swipe/swipecards/swipe_cards.dart';
import 'global_detail.dart';

/// TrendDetailView class
class TrendDetailViewSwipe extends StatefulWidget {
  TrendDetailViewSwipe(
      {Key? key,
      this.trendDetail,
      required this.feedItem,
      required this.id,
      required this.token,
      required this.matchEngine,
      required this.isSharedItem})
      : super(key: key);
  TrendDetail? trendDetail;
  int id;
  String token;
  FeedItem feedItem;
  bool isSharedItem;
  MatchEngine matchEngine;

  @override
  State<TrendDetailViewSwipe> createState() => _TrendDetailViewSwipeState();
}

class _TrendDetailViewSwipeState extends State<TrendDetailViewSwipe> {
  late ScrollController controller;
  bool isReadMore = false;
  late Future<TrendDetail> futureTrendDetail;

  @override
  void initState() {
    controller = ScrollController();
    super.initState();
    futureTrendDetail =
        fetchTrendDetail(widget.token, widget.id, widget.isSharedItem);
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // main colors
    final Color backgroundColor = Theme.of(context).backgroundColor;
    final Color primaryColor = Theme.of(context).primaryColor;
    final Color primaryContainer =
        Theme.of(context).colorScheme.primaryContainer;
    final Color secondaryContainer =
        Theme.of(context).colorScheme.secondaryContainer;

    // get screenSize
    double height = MediaQuery.of(context).size.height -
        MediaQuery.of(context).padding.bottom -
        80;
    double width = MediaQuery.of(context).size.width;
    // return Futurebuilder
    return FutureBuilder<TrendDetail>(
      future: futureTrendDetail,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          // backend data
          TrendDetail? trendDetail = snapshot.data;
          // formatted dates
          DateTime lastUpdated = DateTime.fromMillisecondsSinceEpoch(
              trendDetail!.coreField.lastUpdated,
              isUtc: true);
          String formattedLastUpdated =
              DateFormat('dd.MM.yyyy').format(lastUpdated);
          DateTime creationDate = DateTime.fromMillisecondsSinceEpoch(
              trendDetail.coreField.creationDate,
              isUtc: true);
          String formattedCreationDate =
              DateFormat('dd.MM.yyyy').format(creationDate);
          return SizedBox(
            height: height,
            child: Scaffold(
              body: CustomScrollView(
                controller: controller,
                slivers: [
                  //  animated appBar
                  ScrollTopBarWidget(coreField: trendDetail.coreField),

                  // section for 'teaser'
                  TeaserWidget(teaserField: trendDetail.coreField.teaser),

                  // section for description
                  DescriptionWidget(
                      descriptionField: trendDetail.trendRequired.description,
                      isReadMore: false),

                  // section for tags
                  TagWidget(coreField: trendDetail.coreField),

                  SliverToBoxAdapter(
                    child: Divider(color: primaryColor, thickness: 2),
                  ),

                  // section for discussion
                  SliverToBoxAdapter(
                    child: Padding(
                      padding: const EdgeInsets.all(15),
                      child: Column(
                        children: [
                          Padding(
                              padding: const EdgeInsets.only(bottom: 5),
                              child: Align(
                                  alignment: Alignment.topLeft,
                                  child: Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      const Padding(
                                          padding: EdgeInsets.only(bottom: 5),
                                          child: Text(
                                            'Diskussionen',
                                            style: TextStyle(
                                                fontSize: 15,
                                                fontWeight: FontWeight.bold),
                                          )),
                                      Text(trendDetail.trendRequired.discussion,
                                          textAlign: TextAlign.justify),
                                    ],
                                  ))),
                        ],
                      ),
                    ),
                  ),

                  SliverToBoxAdapter(
                    child: Divider(color: primaryColor, thickness: 2),
                  ),

                  // contactElapse
                  SliverToBoxAdapter(
                    child: Padding(
                      padding: EdgeInsets.all(15),
                      child: TrendContactElapse(trend: trendDetail),
                    ),
                  ),

                  // section for author creationDate and lastUpdated
                  AuthorWidget(coreField: trendDetail.coreField),
                ],
              ),
              floatingActionButton: ShareScrollDownWidget(
                  coreField: widget.feedItem.coreField,
                  id: widget.id,
                  scrollController: controller),
              // bottomBar with buttons and commentsection
              bottomNavigationBar: SwipeBarWidget(
                  feedItem: widget.feedItem,
                  userToken: widget.token,
                  matchEngine: widget.matchEngine,
                  scrollController: controller,
                  width: width),
            ),
          );
        } else if (snapshot.hasError) {
          return Text('${snapshot.error}');
        }
        //By default, show a loading spinner.
        return const CircularProgressIndicator();
      },
    );
  }

  void scrollUp() {
    const double start = 0;
    controller.jumpTo(start);
  }

  void scrollToCommentSection(double height) {
    controller.jumpTo(height + 100);
  }

  ///Fetch backend Data to show in TrendDetail
  Future<TrendDetail> fetchTrendDetail(
      String? token, int id, bool isSharedItem) async {
    final http.Response response;
    if (isSharedItem == true) {
      response = await http.get(
        Uri.parse('${Constants.getBackendUrl()}/api/v2/shared/trend/$id'),
        headers: {'Accept': 'application/json; charset=UTF-8'},
      );
    } else {
      response = await http
          .get(Uri.parse('${Constants.getBackendUrl()}/api/v2/trend/$id'), headers: {
        'Authorization': 'Bearer $token',
        'Accept': 'application/json; charset=UTF-8'
      });
    }

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return TrendDetail.fromJson(jsonDecode(response.body));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load album');
    }
  }
}
