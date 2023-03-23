import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_markdown/flutter_markdown.dart';
import 'package:intl/intl.dart';
import 'package:frontend/widgets/misc/tag.dart';
import 'package:like_button/like_button.dart';
import '../../constants/url.dart';
import '../../core/detail_objects/company/scroll_to_hide_widget.dart';
import '../../core/detail_objects/project/project_contact_elapse.dart';
import '../../core/detail_objects/project/project_detail_obj.dart';
import '../../utils/star_util.dart';
import 'package:share_plus/share_plus.dart';
import 'package:http/http.dart' as http;

import '../misc/commentBox.dart';
import 'global_detail.dart';

/// ProjectDetailView
class ProjectDetailView extends StatefulWidget {
  ProjectDetailView(
      {Key? key,
      this.projectDetail,
      this.id,
      this.token,
      required this.isSharedItem})
      : super(key: key);
  ProjectDetail? projectDetail;
  int? id;
  String? token;
  bool isSharedItem;

  @override
  State<ProjectDetailView> createState() => _ProjectDetailViewState();
}

class _ProjectDetailViewState extends State<ProjectDetailView>
    with SingleTickerProviderStateMixin {
  late AnimationController animationController;
  late ScrollController controller;
  bool isReadMore = false;
  int _index = 0;
  late Future<ProjectDetail> futureProjectDetail;

  @override
  void initState() {
    animationController =
        AnimationController(vsync: this, duration: const Duration(seconds: 1));
    animationController.addListener(() {
      setState(() {});
    });
    controller = ScrollController();
    super.initState();
    futureProjectDetail =
        fetchProjectDetail(widget.token, widget.id!, widget.isSharedItem);
  }

  @override
  void dispose() {
    animationController.dispose();
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
    return FutureBuilder<ProjectDetail>(
      future: futureProjectDetail,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          // backend data
          ProjectDetail? projectDetail = snapshot.data;
          // formatted dates
          DateTime lastUpdated = DateTime.fromMillisecondsSinceEpoch(
              projectDetail!.coreField.lastUpdated,
              isUtc: true);
          String formattedLastUpdated =
              DateFormat('dd.MM.yyyy').format(lastUpdated);
          DateTime creationDate = DateTime.fromMillisecondsSinceEpoch(
              projectDetail.coreField.creationDate,
              isUtc: true);
          String formattedCreationDate =
              DateFormat('dd.MM.yyyy').format(creationDate);
          DateTime started = DateTime.fromMillisecondsSinceEpoch(
              projectDetail.projectRequired.runtime.start,
              isUtc: true);
          String formattedStarted = DateFormat('dd.MM.yyyy').format(started);
          // Es wird kein geigneter Long übergeben
          // später nochmal abändern
          DateTime finish = DateTime.fromMillisecondsSinceEpoch(
              projectDetail.projectRequired.runtime.finish,
              isUtc: true);
          String formattedFinish = DateFormat('dd.MM.yyyy').format(finish);
          return SizedBox(
            height: height,
            child: Scaffold(
              body: CustomScrollView(
                controller: controller,
                slivers: [
                  //  animated appBar
                  ScrollTopBarWidget(coreField: projectDetail.coreField),

                  // section for 'teaser'
                  TeaserWidget(teaserField: projectDetail.coreField.teaser),

                  // section for description
                  DescriptionWidget(
                      descriptionMarkdown:
                          projectDetail.projectRequired.description,
                      descriptionField:
                          projectDetail.projectRequired.description,
                      isReadMore: false),

                  // section for tags
                  TagWidget(coreField: projectDetail.coreField),

                  // section about partners
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
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: [
                                    Table(
                                      columnWidths: const {
                                        0: FractionColumnWidth(0.1),
                                        1: FractionColumnWidth(0.05),
                                        2: FractionColumnWidth(0.85)
                                      },
                                      defaultVerticalAlignment:
                                          TableCellVerticalAlignment.middle,
                                      children: <TableRow>[
                                        TableRow(children: <Widget>[
                                          const Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 12),
                                                  child:
                                                      Icon(Icons.date_range))),
                                          const Align(),
                                          Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 12),
                                                child: Text(
                                                  '$formattedStarted bis $formattedFinish',
                                                  style: const TextStyle(
                                                      fontSize: 14),
                                                ),
                                              ))
                                        ]),
                                        TableRow(children: <Widget>[
                                          const Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 12),
                                                  child: Icon(Icons
                                                      .people_outline_rounded))),
                                          const Align(),
                                          Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 12),
                                                child: Column(
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment
                                                            .start,
                                                    children: [
                                                      for (var link
                                                          in projectDetail
                                                              .projectOptional!
                                                              .projectRelations!
                                                              .projectPartners)
                                                        Padding(
                                                            padding:
                                                                const EdgeInsets
                                                                        .only(
                                                                    bottom: 5),
                                                            child: Text(
                                                                "• $link")),
                                                    ]),
                                              ))
                                        ]),
                                        TableRow(children: <Widget>[
                                          const Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 12),
                                                  child: Icon(Icons
                                                      .shopping_cart_outlined))),
                                          const Align(),
                                          Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 12),
                                                child: Column(
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment
                                                            .start,
                                                    children: [
                                                      for (var link
                                                          in projectDetail
                                                              .projectOptional!
                                                              .projectRelations!
                                                              .usePartners)
                                                        Padding(
                                                            padding:
                                                                const EdgeInsets
                                                                        .only(
                                                                    bottom: 5),
                                                            child: Text(
                                                                "• $link")),
                                                    ]),
                                              ))
                                        ]),
                                        TableRow(children: <Widget>[
                                          const Align(
                                            alignment: Alignment.topLeft,
                                            child: Padding(
                                                padding:
                                                    EdgeInsets.only(bottom: 15),
                                                child: Icon(
                                                    Icons.attach_money_sharp)),
                                          ),
                                          const Align(),
                                          Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 15),
                                                child: Column(
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment
                                                            .start,
                                                    children: [
                                                      Row(children: [
                                                        Text(
                                                            '${projectDetail.projectOptional!.financing!.value.toString()}\$',
                                                            style: const TextStyle(
                                                                fontWeight:
                                                                    FontWeight
                                                                        .bold)),
                                                        const Text(' von:'),
                                                      ]),
                                                      const Padding(
                                                          padding:
                                                              EdgeInsets.only(
                                                                  bottom: 5)),
                                                      for (var link
                                                          in projectDetail
                                                              .projectOptional!
                                                              .projectRelations!
                                                              .fundingSources)
                                                        Text("• $link")
                                                    ]),
                                              ))
                                        ]),
                                        TableRow(children: <Widget>[
                                          const Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 12),
                                                  child: Icon(Icons
                                                      .announcement_outlined))),
                                          const Align(),
                                          Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 12),
                                                child: Column(
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment
                                                            .start,
                                                    children: [
                                                      for (var link
                                                          in projectDetail
                                                              .projectOptional!
                                                              .projectRelations!
                                                              .promoters)
                                                        Padding(
                                                            padding:
                                                                const EdgeInsets
                                                                        .only(
                                                                    bottom: 5),
                                                            child: Text(
                                                                "• $link")),
                                                    ]),
                                              ))
                                        ]),
                                      ],
                                    ),
                                  ],
                                ))),
                      ],
                    ),
                  )),

                  SliverToBoxAdapter(
                    child: Divider(color: primaryColor, thickness: 2),
                  ),

                  // pageView events
                  // -------- dummy data ------ for design purpose
                  SliverToBoxAdapter(
                    child: Padding(
                        padding:
                            const EdgeInsets.only(top: 15, left: 15, right: 15),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: const [
                            Text(
                              'Anstehende Events: \n',
                              style: TextStyle(
                                  fontSize: 16, fontWeight: FontWeight.bold),
                            ),
                          ],
                        )),
                  ),

                  // PageView with all events planned in the future
                  SliverToBoxAdapter(
                    child: Padding(
                      padding: const EdgeInsets.only(bottom: 10),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          SizedBox(
                            height: 250, // card height
                            child: PageView.builder(
                              itemCount: 10,
                              controller: PageController(viewportFraction: 0.8),
                              onPageChanged: (int index) =>
                                  setState(() => _index = index),
                              itemBuilder: (_, i) {
                                return Transform.scale(
                                    scale: i == _index ? 1 : 0.9,
                                    child: Padding(
                                        padding: const EdgeInsets.all(5.0),
                                        child: Stack(children: [
                                          Container(
                                            height: 250,
                                            decoration: BoxDecoration(
                                                borderRadius:
                                                    BorderRadius.circular(10),
                                                image: DecorationImage(
                                                  image: NetworkImage(
                                                    projectDetail.events.first
                                                        .imageSource,
                                                  ),
                                                  fit: BoxFit.cover,
                                                )),
                                          ),
                                          Align(
                                            alignment: Alignment.topCenter,
                                            child: Container(
                                              padding: EdgeInsets.all(10),
                                              height: 250,
                                              decoration: BoxDecoration(
                                                  borderRadius:
                                                      BorderRadius.circular(10),
                                                  color: Colors.black
                                                      .withOpacity(0.5)),
                                              child: Column(
                                                mainAxisAlignment:
                                                    MainAxisAlignment.center,
                                                children: [
                                                  Table(
                                                    columnWidths: const {
                                                      0: FractionColumnWidth(
                                                          0.1),
                                                      1: FractionColumnWidth(
                                                          0.9)
                                                    },
                                                    // defaultColumnWidth: ,
                                                    defaultVerticalAlignment:
                                                        TableCellVerticalAlignment
                                                            .top,
                                                    children: <TableRow>[
                                                      const TableRow(
                                                          children: <Widget>[
                                                            Align(
                                                                alignment:
                                                                    Alignment
                                                                        .topLeft,
                                                                child: Padding(
                                                                    padding: EdgeInsets.only(
                                                                        bottom:
                                                                            10),
                                                                    child: Icon(
                                                                        Icons
                                                                            .date_range,
                                                                        color: Colors
                                                                            .white,
                                                                        size:
                                                                            20.0))),
                                                            Align(
                                                                alignment: Alignment
                                                                    .centerLeft,
                                                                child: Padding(
                                                                    padding: EdgeInsets.only(
                                                                        bottom:
                                                                            5),
                                                                    // Here should be a long formatted into a date
                                                                    child: Text(
                                                                        "17.06.2022",
                                                                        style: TextStyle(
                                                                            color:
                                                                                Colors.white))))
                                                          ]),
                                                      TableRow(
                                                          children: <Widget>[
                                                            const Align(
                                                                alignment:
                                                                    Alignment
                                                                        .topLeft,
                                                                child: Padding(
                                                                  padding: EdgeInsets
                                                                      .only(
                                                                          bottom:
                                                                              5),
                                                                  child: Icon(
                                                                      Icons
                                                                          .location_pin,
                                                                      color: Colors
                                                                          .white,
                                                                      size:
                                                                          20.0),
                                                                )),
                                                            Align(
                                                                alignment: Alignment
                                                                    .centerLeft,
                                                                child: Padding(
                                                                    padding: const EdgeInsets
                                                                            .only(
                                                                        bottom:
                                                                            5),
                                                                    child: Text(
                                                                        projectDetail
                                                                            .events
                                                                            .first
                                                                            .location,
                                                                        style: const TextStyle(
                                                                            color:
                                                                                Colors.white))))
                                                          ]),
                                                      TableRow(
                                                          children: <Widget>[
                                                            const Align(
                                                                alignment:
                                                                    Alignment
                                                                        .topLeft,
                                                                child: Padding(
                                                                  padding: EdgeInsets
                                                                      .only(
                                                                          bottom:
                                                                              5),
                                                                  child: Icon(
                                                                      Icons
                                                                          .info_outline,
                                                                      color: Colors
                                                                          .white,
                                                                      size:
                                                                          20.0),
                                                                )),
                                                            Align(
                                                                alignment: Alignment
                                                                    .centerLeft,
                                                                child: Padding(
                                                                    padding: const EdgeInsets
                                                                            .only(
                                                                        bottom:
                                                                            5),
                                                                    child: Text(
                                                                        projectDetail
                                                                            .events
                                                                            .first
                                                                            .description,
                                                                        textAlign:
                                                                            TextAlign
                                                                                .justify,
                                                                        style: const TextStyle(
                                                                            color:
                                                                                Colors.white))))
                                                          ]),
                                                    ],
                                                  ),
                                                  Text(
                                                      projectDetail
                                                          .events.first.website,
                                                      style: const TextStyle(
                                                          color: Colors.white))
                                                ],
                                              ),
                                            ),
                                          ),
                                        ])));
                              },
                            ),
                          ),
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
                      child: ProjectContactElapse(project: projectDetail),
                    ),
                  ),

                  // section for 'fundingSource', 'financing', 'promoters'
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
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Table(
                                      columnWidths: const {
                                        0: FractionColumnWidth(0.2),
                                        1: FractionColumnWidth(0.05),
                                        2: FractionColumnWidth(0.75)
                                      },
                                      defaultVerticalAlignment:
                                          TableCellVerticalAlignment.top,
                                      children: <TableRow>[
                                        TableRow(children: <Widget>[
                                          Align(
                                            alignment: Alignment.topLeft,
                                            child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 15),
                                                child: Text(
                                                  '${projectDetail.projectOptional!.financing!.value.toString()}\$:',
                                                  style: const TextStyle(
                                                      fontSize: 14,
                                                      fontWeight:
                                                          FontWeight.bold),
                                                )),
                                          ),
                                          const Align(),
                                          Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 15),
                                                child: Column(
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment
                                                            .start,
                                                    children: [
                                                      for (var link
                                                          in projectDetail
                                                              .projectOptional!
                                                              .projectRelations!
                                                              .fundingSources)
                                                        Text("• $link")
                                                    ]),
                                              ))
                                        ]),
                                        TableRow(children: <Widget>[
                                          const Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 12),
                                                  child: Text(
                                                    'Werbung:',
                                                    style: TextStyle(
                                                        fontSize: 13,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ))),
                                          const Align(),
                                          Align(
                                              alignment: Alignment.topLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 12),
                                                child: Column(
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment
                                                            .start,
                                                    children: [
                                                      for (var link
                                                          in projectDetail
                                                              .projectOptional!
                                                              .projectRelations!
                                                              .promoters)
                                                        Padding(
                                                            padding:
                                                                const EdgeInsets
                                                                        .only(
                                                                    bottom: 5),
                                                            child: Text(
                                                                "• $link")),
                                                    ]),
                                              ))
                                        ]),
                                      ],
                                    ),
                                  ],
                                ))),
                      ],
                    ),
                  )),

                  // section for author creationDate and lastUpdated
                  AuthorWidget(coreField: projectDetail.coreField),
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
              //     decoration: BoxDecoration(color: Colors.white.withOpacity(0)),
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
              //                             '''Name: ${projectDetail.coreField.headline}  
                                              
              //                                 Link: ${Uri.base.toString()}project/?id=${projectDetail.id}''',
              //                             subject:
              //                                 'Schau dir dieses Projekt an!!!');
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
              //                       mainAxisAlignment: MainAxisAlignment.center,
              //                       children: const [
              //                         Icon(
              //                           Icons.comment,
              //                           color: Colors.white,
              //                           size: 18,
              //                         ),
              //                         Padding(
              //                             padding: EdgeInsets.only(right: 10)),
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
            ),
          );
        } else if (snapshot.hasError) {
          print(snapshot.error);
          print(snapshot.stackTrace);
          return Column(children: [
            Text('${snapshot.error}'),
          ]);
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

  ///Fetch backend Data to show in ProjectDetail
  Future<ProjectDetail> fetchProjectDetail(
      String? token, int id, bool isSharedItem) async {
    final http.Response response;
    if (isSharedItem == true) {
      response = await http.get(
          Uri.parse('${Constants.getBackendUrl()}/api/v2/shared/project/$id'),
          headers: {'Accept': 'application/json; charset=UTF-8'});
    } else {
      response = await http
          .get(Uri.parse('${Constants.getBackendUrl()}/api/v2/project/$id'), headers: {
        'Authorization': 'Bearer $token',
        'Accept': 'application/json; charset=UTF-8'
      });
    }

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return ProjectDetail.fromJson(jsonDecode(response.body));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load project');
    }
  }
}
