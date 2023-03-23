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

import '../misc/commentBox.dart';
import 'global_detail.dart';

/// TrendDetailView class
class TrendDetailView extends StatefulWidget {
  TrendDetailView(
      {Key? key,
      this.trendDetail,
      this.id,
      this.token,
      required this.isSharedItem})
      : super(key: key);
  TrendDetail? trendDetail;
  int? id;
  String? token;
  bool isSharedItem;

  @override
  State<TrendDetailView> createState() => _TrendDetailViewState();
}

class _TrendDetailViewState extends State<TrendDetailView> {
  late ScrollController controller;
  bool isReadMore = false;
  late Future<TrendDetail> futureTrendDetail;

  @override
  void initState() {
    controller = ScrollController();
    super.initState();
    futureTrendDetail =
        fetchTrendDetail(widget.token, widget.id!, widget.isSharedItem);
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
        MediaQuery.of(context).padding.bottom;
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
                                        Text(
                                            trendDetail
                                                .trendRequired.discussion,
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
                    const SliverToBoxAdapter(
                      child: Divider(),
                    ),
                    // TODO: implement comment section
                    // SliverToBoxAdapter(
                    //     child: Column(
                    //   crossAxisAlignment: CrossAxisAlignment.start,
                    //   children: [
                    //     CommentBox(
                    //         text: 'Comment 1',
                    //         author: 'Hater',
                    //         dateCommented: "21h"),
                    //     CommentBox(
                    //         text: 'Test Test 1 2',
                    //         author: 'Muddi',
                    //         dateCommented: "19h"),
                    //     CommentBox(
                    //         text: 'Produkt gefällt mir!',
                    //         author: 'Vadder',
                    //         dateCommented: "3h"),
                    //     CommentBox(
                    //         text:
                    //             'Daumen runter, buuuh. Mir hat nichts davon gefallen, deswegen beschwere ich mich in einem unnötig langen unkonstruktiven Kommentar!!1!!!!',
                    //         author: 'Fredrik',
                    //         dateCommented: "3m"),
                    //     CommentBox(
                    //         text: 'Pepeeeega',
                    //         author: 'Dennis P.',
                    //         dateCommented: "16s"),
                    //     CommentBox(
                    //         text: 'POGGGGGG',
                    //         author: 'Markus',
                    //         dateCommented: "Now"),
                    //   ],
                    // )),
                  ],
                ),

                // bottomBar with buttons and commentsection
                // bottomNavigationBar: ScrollToHideWidget(
                //   controller: controller,
                //   child: Container(
                //     height: 100,
                //     decoration:
                //         BoxDecoration(color: Colors.white.withOpacity(0)),
                //     child: Stack(
                //       alignment: Alignment.bottomCenter,
                //       children: [
                //         Container(
                //           height: 85.0,
                //           width: width,
                //           decoration: BoxDecoration(color: primaryColor),
                //           child: Padding(
                //             padding: const EdgeInsets.only(top: 20.0),
                //             child: Row(
                //               mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                //               children: [
                //                 Column(
                //                   mainAxisAlignment: MainAxisAlignment.center,
                //                   children: [
                //                     LikeButton(
                //                       size: 25,
                //                       onTap: (bool isLiked) async {
                //                         await Share.share(
                //                             '''Name: ${trendDetail.coreField.headline}  
                                              
                //                               Link: ${Uri.base.toString()}trend/?id=${trendDetail.id}''',
                //                             subject:
                //                                 'Schau dir diese Technology an!!!');
                //                       },
                //                       isLiked: null,
                //                       likeBuilder: (bool isLiked) {
                //                         return Icon(
                //                           Icons.share,
                //                           color: isLiked
                //                               ? Colors.white
                //                               : Colors.white,
                //                           size: 25,
                //                         );
                //                       },
                //                     ),
                //                     const Padding(
                //                         padding: EdgeInsets.only(bottom: 5)),
                //                     const Text(
                //                       'Share',
                //                       style: TextStyle(color: Colors.white),
                //                     ),
                //                   ],
                //                 ),
                //                 Column(
                //                   mainAxisAlignment: MainAxisAlignment.center,
                //                   children: [
                //                     LikeButton(
                //                       size: 25,
                //                       likeBuilder: (bool isLiked) {
                //                         return Icon(
                //                           Icons.bookmark_add,
                //                           color: isLiked
                //                               ? Colors.orangeAccent
                //                               : Colors.white,
                //                           size: 25,
                //                         );
                //                       },
                //                     ),
                //                     const Padding(
                //                         padding: EdgeInsets.only(bottom: 5)),
                //                     const Text(
                //                       'Save',
                //                       style: TextStyle(color: Colors.white),
                //                     ),
                //                   ],
                //                 ),
                //                 Column(
                //                   mainAxisAlignment: MainAxisAlignment.center,
                //                   children: [
                //                     LikeButton(
                //                       size: 25,
                //                       likeBuilder: (bool isLiked) {
                //                         return Icon(
                //                           Icons.favorite,
                //                           color: isLiked
                //                               ? Colors.pinkAccent
                //                               : Colors.white,
                //                           size: 25,
                //                         );
                //                       },
                //                     ),
                //                     const Padding(
                //                         padding: EdgeInsets.only(bottom: 5)),
                //                     const Text(
                //                       'Like',
                //                       style: TextStyle(color: Colors.white),
                //                     ),
                //                   ],
                //                 ),
                //                 Column(
                //                   mainAxisAlignment: MainAxisAlignment.center,
                //                   children: [
                //                     LikeButton(
                //                       onTap: (isLiked) async {
                //                         scrollUp();
                //                         return null;
                //                       },
                //                       isLiked: null,
                //                       size: 25,
                //                       likeBuilder: (bool isLiked) {
                //                         return Icon(
                //                           Icons.arrow_upward,
                //                           color: isLiked
                //                               ? Colors.white
                //                               : Colors.white,
                //                           size: 25,
                //                         );
                //                       },
                //                     ),
                //                     const Padding(
                //                         padding: EdgeInsets.only(bottom: 5)),
                //                     const Text(
                //                       'Scroll up',
                //                       style: TextStyle(color: Colors.white),
                //                     ),
                //                   ],
                //                 )
                //               ],
                //             ),
                //           ),
                //         ),
                //         Positioned(
                //           top: 0,
                //           child: SizedBox(
                //             height: 25,
                //             width: width,
                //             child: ClipOval(
                //               child: Align(
                //                   child: Container(
                //                 color: backgroundColor,
                //               )),
                //             ),
                //           ),
                //         ),
                //         Positioned(
                //           top: 8,
                //           child: SizedBox(
                //             height: 30.0,
                //             width: width - 175,
                //             child: RaisedButton(
                //               onPressed: () => scrollToCommentSection(height),
                //               shape: RoundedRectangleBorder(
                //                   borderRadius: BorderRadius.circular(80.0)),
                //               padding: const EdgeInsets.all(0.0),
                //               child: Ink(
                //                 decoration: BoxDecoration(
                //                     gradient: const LinearGradient(
                //                       colors: [
                //                         Color(0xff374ABE),
                //                         Color(0xff64B6FF)
                //                       ],
                //                       begin: Alignment.centerLeft,
                //                       end: Alignment.centerRight,
                //                     ),
                //                     borderRadius: BorderRadius.circular(30.0)),
                //                 child: Container(
                //                     constraints: BoxConstraints(
                //                         maxWidth: width - 175, minHeight: 50.0),
                //                     alignment: Alignment.center,
                //                     child: Row(
                //                       mainAxisAlignment:
                //                           MainAxisAlignment.center,
                //                       children: const [
                //                         Icon(
                //                           Icons.comment,
                //                           color: Colors.white,
                //                           size: 18,
                //                         ),
                //                         Padding(
                //                             padding:
                //                                 EdgeInsets.only(right: 10)),
                //                         Text(
                //                           "Comment",
                //                           textAlign: TextAlign.center,
                //                           style: TextStyle(color: Colors.white),
                //                         ),
                //                       ],
                //                     )),
                //               ),
                //             ),
                //           ),
                //         ),
                //       ],
                //     ),
                //   ),
                // ),
              ));
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
