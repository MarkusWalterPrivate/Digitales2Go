import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_markdown/flutter_markdown.dart';
import 'package:frontend/core/detail_objects/technology/technology_detail_obj.dart';
import 'package:frontend/core/detail_objects/technology/tech_contact_elapse.dart';
import 'package:frontend/widgets/swipe/swipecards/swipe_cards.dart';
import 'package:intl/intl.dart';
import 'package:like_button/like_button.dart';
import '../../constants/url.dart';
import '../../core/detail_objects/company/scroll_to_hide_widget.dart';
import 'package:share_plus/share_plus.dart';
import 'package:http/http.dart' as http;

import '../feed/feed_item.dart';
import '../misc/commentBox.dart';
import '../swipe/swipe_bar.dart';
import 'global_detail.dart';

/// TechnologyDetailView class
class TechnologyDetailViewSwipe extends StatefulWidget {
  TechnologyDetailViewSwipe(
      {Key? key,
      this.technologyDetail,
      required this.feedItem,
      required this.id,
      required this.token,
      required this.matchEngine,
      required this.isSharedItem})
      : super(key: key);
  TechnologyDetail? technologyDetail;
  int id;
  String token;
  FeedItem feedItem;
  bool isSharedItem;
  MatchEngine matchEngine;

  @override
  State<TechnologyDetailViewSwipe> createState() =>
      _TechnologyDetailViewSwipeState();
}

class _TechnologyDetailViewSwipeState extends State<TechnologyDetailViewSwipe> {
  late ScrollController controller;
  bool isReadMore = false;
  late Future<TechnologyDetail> futureTechnologyDetail;

  @override
  void initState() {
    controller = ScrollController();
    super.initState();
    controller = ScrollController();
    futureTechnologyDetail =
        fetchTechnologyDetail(widget.token, widget.id, widget.isSharedItem);
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

    String markdown = ''' 
    # Heading 1 
    ## Heading 2 
    *This tect will be italic*''';

    return FutureBuilder<TechnologyDetail>(
        future: futureTechnologyDetail,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            // backend data
            TechnologyDetail? technologyDetail = snapshot.data;
            // formatted dates
            DateTime lastUpdated = DateTime.fromMillisecondsSinceEpoch(
                technologyDetail!.coreField.lastUpdated,
                isUtc: true);
            String formattedLastUpdated =
                DateFormat('dd.MM.yyyy').format(lastUpdated);
            DateTime creationDate = DateTime.fromMillisecondsSinceEpoch(
                technologyDetail.coreField.creationDate,
                isUtc: true);
            String formattedCreationDate =
                DateFormat('dd.MM.yyyy').format(creationDate);
            return SizedBox(
                height: height,
                child: Scaffold(
                  body: CustomScrollView(
                    controller: controller,
                    slivers: [
                      // animated appBar
                      ScrollTopBarWidget(coreField: technologyDetail.coreField),

                      // section for 'teaser'
                      TeaserWidget(
                          teaserField: technologyDetail.coreField.teaser),

                      // section for description
                      DescriptionWidget(
                          descriptionField:
                              technologyDetail.techRequired.description,
                          isReadMore: false),

                      // section for tags
                      TagWidget(coreField: technologyDetail.coreField),

                      SliverToBoxAdapter(
                        child: Divider(color: primaryColor, thickness: 2),
                      ),

                      // section for discussion, readiness and usecases
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
                                              'Diskussion',
                                              style: TextStyle(
                                                  fontSize: 15,
                                                  fontWeight: FontWeight.bold),
                                            )),
                                        Text(
                                            technologyDetail
                                                .techRequired.discussion,
                                            textAlign: TextAlign.justify),
                                        const Padding(
                                          padding: EdgeInsets.only(
                                              bottom: 5, top: 5),
                                          child: Divider(),
                                        ),
                                        const Padding(
                                            padding: EdgeInsets.only(bottom: 5),
                                            child: Text(
                                              'Nutzbarkeit',
                                              style: TextStyle(
                                                  fontSize: 15,
                                                  fontWeight: FontWeight.bold),
                                            )),
                                        Text(
                                            technologyDetail
                                                .techRequired.readiness,
                                            textAlign: TextAlign.justify),
                                        const Padding(
                                          padding: EdgeInsets.only(
                                              bottom: 5, top: 5),
                                          child: Divider(),
                                        ),
                                        const Padding(
                                            padding: EdgeInsets.only(bottom: 5),
                                            child: Text(
                                              'Usecases',
                                              style: TextStyle(
                                                  fontSize: 15,
                                                  fontWeight: FontWeight.bold),
                                            )),
                                        Text(
                                            technologyDetail
                                                .techRequired.usecases,
                                            textAlign: TextAlign.justify),
                                      ],
                                    ))),
                          ],
                        ),
                      )),

                      SliverToBoxAdapter(
                        child: Divider(color: primaryColor, thickness: 2),
                      ),

                      // contactElapse
                      SliverToBoxAdapter(
                        child: Padding(
                          padding: const EdgeInsets.all(15),
                          child:
                              TechContactElapse(technology: technologyDetail),
                        ),
                      ),

                      // section for author creationDate and lastUpdated
                      AuthorWidget(coreField: technologyDetail.coreField),
                    ],
                  ),
                  floatingActionButton: ShareScrollDownWidget(
                      coreField: widget.feedItem.coreField,
                      id: widget.id,
                      scrollController: controller),
                  // bottomBar with buttons and commentsection
                  bottomNavigationBar: SwipeBarWidget(
                    feedItem: widget.feedItem,
                    matchEngine: widget.matchEngine,
                    scrollController: controller,
                    width: width,
                    userToken: widget.token,
                  ),
                ));
          } else if (snapshot.hasError) {
            print(snapshot.error);
          }
          //By default, show a loading spinner.
          return const CircularProgressIndicator();
        });
  }

  void scrollUp() {
    const double start = 0;
    controller.jumpTo(start);
  }

  void scrollToCommentSection(double height) {
    controller.jumpTo(height + 100);
  }

  ///Fetch backend Data to show in TechDetail
  Future<TechnologyDetail> fetchTechnologyDetail(
      String? token, int id, bool isSharedItem) async {
    final http.Response response;
    if (isSharedItem == true) {
      response = await http.get(
        Uri.parse('${Constants.getBackendUrl()}/api/v2/shared/tech/$id'),
        headers: {'Accept': 'application/json; charset=UTF-8'},
      );
    } else {
      response = await http
          .get(Uri.parse('${Constants.getBackendUrl()}/api/v2/tech/$id'), headers: {
        'Authorization': 'Bearer $token',
        'Accept': 'application/json; charset=UTF-8'
      });
    }

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return TechnologyDetail.fromJson(jsonDecode(response.body));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load technology');
    }
  }
}
