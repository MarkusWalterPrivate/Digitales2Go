import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:frontend/core/detail_objects/bookmarked_item.dart';
import 'package:frontend/core/profile_objects/ratings.dart';
import 'package:frontend/core/profile_objects/user.dart';
import 'package:frontend/utils/dummy_data.dart';
import 'package:frontend/widgets/profile/profile_atrribute_container.dart';
import 'package:frontend/widgets/profile/profile_edit.dart';
import 'package:http/http.dart' as http;

import '../../constants/url.dart';
import '../../core/atomic_objects/return_dto.dart';
import '../../core/detail_objects/technology/technology_detail_obj.dart';
import '../feed/feed_item.dart';
import '../feed/feed_row.dart';

class ProfileView extends StatefulWidget {
  ProfileView({Key? key, required this.userToken, required this.userId})
      : super(key: key);
  String userToken;
  int userId;
  @override
  State<ProfileView> createState() => _ProfileViewwState();
}

class _ProfileViewwState extends State<ProfileView>
    with TickerProviderStateMixin {
  bool isSavedItemsVisible = false;
  late Future<User> futureUser;
  late Future<FeedRow> futureFeedRowBookmarked;
  late Future<List<FeedRow>> futureFeedRows;

  @override
  void initState() {
    super.initState();
    futureUser = getUserByID(widget.userId, widget.userToken);
    futureFeedRowBookmarked = fetchProfileRows(widget.userToken);
    futureFeedRows = fetchRatings(widget.userToken);
  }

  final Color errorColor = Color(0xffffba1b1b);
  final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);

  @override

  /// Building the ui
  Widget build(BuildContext context) {
    TabController tabController = TabController(length: 4, vsync: this);
    // main colors
    final Color primaryColor = Theme.of(context).primaryColor;
    // screensize
    double width = MediaQuery.of(context).size.width;
    double height = MediaQuery.of(context).size.height -
        kBottomNavigationBarHeight -
        kToolbarHeight;
    return FutureBuilder<User>(
      future: futureUser,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          User? user = snapshot.data;
          return Stack(
            children: [
              Container(
                height: height,
                width: width,
                decoration: const BoxDecoration(
                    image: DecorationImage(
                        image: NetworkImage(
                            'https://www.ullrich-schoen.de/wp-content/uploads/2020/02/Fraunhofer1.jpg'),
                        fit: BoxFit.cover)),
              ),
              Container(
                height: height,
                width: width,
                decoration: BoxDecoration(color: primaryColor.withOpacity(0.7)),
                child: Stack(
                  children: [
                    AnimatedPositioned(
                      duration: const Duration(milliseconds: 500),
                      top: isSavedItemsVisible ? height : height / 2,
                      left: 0,
                      right: 0,
                      child: AnimatedOpacity(
                        opacity: isSavedItemsVisible ? 0 : 1,
                        curve: Curves.easeInOut,
                        duration: const Duration(milliseconds: 500),
                        child: SizedBox(
                          width: width,
                          height: height / 2,
                          child: SingleChildScrollView(
                            child: Column(
                              children: [
                                SizedBox(
                                  height: 50,
                                  child: TabBar(
                                    controller: tabController,
                                    labelColor: Colors.white,
                                    unselectedLabelColor: Colors.white,
                                    indicator: BoxDecoration(
                                        color: primaryColor,
                                        borderRadius: const BorderRadius.only(
                                            topLeft: Radius.circular(5),
                                            topRight: Radius.circular(5))),
                                    tabs: const [
                                      Tab(icon: Icon(Icons.bookmark)),
                                      Tab(icon: Icon(Icons.thumb_up)),
                                      Tab(icon: Icon(Icons.thumb_down)),
                                      Tab(icon: Icon(Icons.skip_next)),
                                    ],
                                  ),
                                ),
                                Container(
                                  alignment: Alignment.centerLeft,
                                  color: Colors.white,
                                  width: width,
                                  height: height / 2 - 50,
                                  child: TabBarView(
                                    controller: tabController,
                                    children: [
                                      FutureBuilder<FeedRow>(
                                          future: futureFeedRowBookmarked,
                                          builder: (context,
                                              snapShotFeedBookmarked) {
                                            if (snapShotFeedBookmarked
                                                .hasData) {
                                              if (snapShotFeedBookmarked
                                                  .data!.feedItems.isEmpty) {
                                                return const Center(
                                                  child: Text(
                                                      'Elemente können hier über über das Lesezeichen Symbol in der Swipe-Ansicht hinzugefügt werden, oder über die Feed-Ansicht in dem man das entprechende Item gedrückt hält!'),
                                                );
                                              } else {
                                                return FeedRow(
                                                    feedItems:
                                                        snapShotFeedBookmarked
                                                            .data!.feedItems,
                                                    userToken:
                                                        widget.userToken);
                                              }
                                            } else if (snapshot.hasError) {
                                              return const Text(
                                                  'Es konnten keine mit Lesezeichen versehen Items geladen werden');
                                            } else {
                                              return const CircularProgressIndicator();
                                            }
                                          }),
                                      FutureBuilder<List<FeedRow>>(
                                          future: futureFeedRows,
                                          builder:
                                              (context, snapShotFeedRelevant) {
                                            if (snapShotFeedRelevant.hasData) {
                                              if (snapShotFeedRelevant
                                                  .data![0].feedItems.isEmpty) {
                                                return const Center(
                                                  child: Text(
                                                      'Elemente können in der Swipe-Ansicht, über das "Daumen hoch" Symbol, oder durch das Wischen nach rechts einer Karte, hinzugefügt werden!'),
                                                );
                                              } else {
                                                return snapShotFeedRelevant
                                                    .data![0];
                                              }
                                            } else if (snapshot.hasError) {
                                              return const Text(
                                                  'Es konnten keine von ihnen als relvant gekennzeichneten Items geladen werden');
                                            } else {
                                              return const CircularProgressIndicator();
                                            }
                                          }),
                                      FutureBuilder<List<FeedRow>>(
                                          future: futureFeedRows,
                                          builder: (context,
                                              snapShotFeedIrrelevant) {
                                            if (snapShotFeedIrrelevant
                                                .hasData) {
                                              if (snapShotFeedIrrelevant
                                                  .data![1].feedItems.isEmpty) {
                                                return const Center(
                                                  child: Text(
                                                      'Elemente können in der Swipe-Ansicht, über das "Daumen runter" Symbol, oder durch das Wischen nach links einer Karte, hinzugefügt werden!'),
                                                );
                                              } else {
                                                return snapShotFeedIrrelevant
                                                    .data![1];
                                              }
                                            } else if (snapshot.hasError) {
                                              return const Text(
                                                  'Es konnten keine von ihnen als irrelvant gekennzeichneten Items geladen werden');
                                            } else {
                                              return const CircularProgressIndicator();
                                            }
                                          }),
                                      FutureBuilder<List<FeedRow>>(
                                          future: futureFeedRows,
                                          builder:
                                              (context, snapShotFeedIgnored) {
                                            if (snapShotFeedIgnored.hasData) {
                                              if (snapShotFeedIgnored
                                                  .data![2].feedItems.isEmpty) {
                                                return const Center(
                                                  child: Text(
                                                      'Elemente können in der Swipe-Ansicht über das "Skip" Symbol ,hinzugefügt werden!'),
                                                );
                                              } else {
                                                return snapShotFeedIgnored
                                                    .data![2];
                                              }
                                            } else if (snapshot.hasError) {
                                              return const Text(
                                                  'Es konnten keine von ihnen als geskipped gekennzeichneten Items geladen werden');
                                            } else {
                                              return const CircularProgressIndicator();
                                            }
                                          }),
                                    ],
                                  ),
                                )
                              ],
                            ),
                          ),
                        ),
                      ),
                    ),
                    RefreshIndicator(
                      onRefresh: () => reloadUser(),
                      child: AnimatedPositioned(
                        duration: const Duration(milliseconds: 500),
                        top: isSavedItemsVisible ? 50 : 0,
                        left: 0,
                        right: 0,
                        child: SizedBox(
                          width: width,
                          height:
                              isSavedItemsVisible ? height - 100 : height / 2,
                          child: SingleChildScrollView(
                            child: Column(
                              children: [
                                Padding(
                                  padding: const EdgeInsets.all(15.0),
                                  child: ProfileAttributeContainer(
                                    user: User(
                                      id: user!.id,
                                      firstName: user.firstName,
                                      lastName: user.lastName,
                                      email: user.email,
                                      mandate: user.mandate,
                                      industry: user.industry,
                                      company: user.company,
                                      job: user.job,
                                      interests: user.interests,
                                    ),
                                    animate: isSavedItemsVisible,
                                    onTap: () {
                                      setState(
                                        () {
                                          isSavedItemsVisible =
                                              !isSavedItemsVisible;
                                        },
                                      );
                                    },
                                  ),
                                ),
                                isSavedItemsVisible
                                    ? AnimatedOpacity(
                                        duration:
                                            const Duration(milliseconds: 500),
                                        opacity: isSavedItemsVisible ? 1 : 0,
                                        child: SizedBox(
                                          width: 400,
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                left: 16, right: 16),
                                            child: ElevatedButton(
                                              style: ButtonStyle(
                                                backgroundColor:
                                                    MaterialStateProperty.all(
                                                        Colors.white),
                                              ),
                                              onPressed: () {
                                                Navigator.push(
                                                  context,
                                                  MaterialPageRoute(
                                                    builder: ((context) =>
                                                        ProfileEditView(
                                                            userId:
                                                                widget.userId,
                                                            userToken: widget
                                                                .userToken)),
                                                  ),
                                                );
                                              },
                                              child: Row(
                                                mainAxisAlignment:
                                                    MainAxisAlignment.center,
                                                children: [
                                                  Text(
                                                    'Profil bearbeiten',
                                                    style: TextStyle(
                                                        color: primaryGreen,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                  const SizedBox(width: 10),
                                                  Icon(Icons.edit_outlined,
                                                      color: primaryGreen),
                                                ],
                                              ),
                                            ),
                                          ),
                                        ),
                                      )
                                    : const SizedBox(),
                              ],
                            ),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          );
        } else if (snapshot.hasError) {
          print(snapshot.error);
          return const Text('Es konnte kein Profil geladen werden');
        } else {
          return const CircularProgressIndicator();
        }
      },
    );
  }

  /// refresh user on the profile view with the refreshindicator
  /// Returns:
  ///   Future<void>
  Future<void> reloadUser() {
    Future<User> futureUserReloaded =
        getUserByID(widget.userId, widget.userToken);
    if (futureUserReloaded != null) {
      setState(() {
        futureUser = futureUserReloaded;
      });
    }
    return futureUserReloaded;
  }

  /// Get user by id from db
  /// Args:
  ///   userID (int): id of the user
  ///   token (String): the users jwt token
  ///
  /// Returns:
  ///   Future
  Future<User> getUserByID(int userID, String userToken) async {
    final response = await http.get(
        Uri.parse('${Constants.getBackendUrl()}/api/v2/user/$userID'),
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          'Authorization': 'Bearer $userToken',
        });

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return User.fromJson(jsonDecode(utf8.decode(response.bodyBytes)));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load user');
    }
  }

  Future<FeedRow> fetchProfileRows(String userToken) async {
    final responseBookmarked = await http.post(
        Uri.parse('${Constants.getBackendUrl()}/api/v2/bookmark/'),
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          'Authorization': 'Bearer $userToken',
        },
        body: jsonEncode(<String, dynamic>{
        "totalItems": 0,
        "itemsPerPage": 100,
        "page": 0,
        "sorting": "NOTSET"
      }),
    );
    if (responseBookmarked.statusCode == 200) {
      // First get the BokkmardItem Obejcts in a list
      
     BookmarkFeedDTO bookmarkFeedDTO = BookmarkFeedDTO.fromJson(jsonDecode(utf8.decode(responseBookmarked.bodyBytes)));
      List<FeedItem> filteredFeedItems = [];

      // now get the Corfields vom the List<BookmarkedItem>
      for (var item in bookmarkFeedDTO.feed) {
        filteredFeedItems.add(item.item);
      }
      return FeedRow(
          feedItems: filteredFeedItems,
          feedName: null,
          userToken: widget.userToken);
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load feedrow');
    }
  }

  Future<List<FeedRow>> fetchRatings(String userToken) async {
    List<FeedItem> relevantItems = [];
    List<FeedItem> irrelevantItems = [];
    List<FeedItem> ignoredItems = [];
    List<Rating> ratings;
    final responseRatings = await http.post(
        Uri.parse('${Constants.getBackendUrl()}/api/v2/user/ratings/'),
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
    if (responseRatings.statusCode == 200) {
      RatingFeedDTO ratingFeedDTO = RatingFeedDTO.fromJson(jsonDecode(utf8.decode(responseRatings.bodyBytes)));
      for (var rating in ratingFeedDTO.feed) {
        if (rating.ratingValue == "LIKE") {
          relevantItems.add(rating.item);
        } else if (rating.ratingValue == 'DISLIKE') {
          irrelevantItems.add(rating.item);
        } else if (rating.ratingValue == 'SKIP') {
          ignoredItems.add(rating.item);
        }
      }
      List<FeedRow> feedRows = [
        FeedRow(feedItems: relevantItems, userToken: userToken),
        FeedRow(feedItems: irrelevantItems, userToken: userToken),
        FeedRow(feedItems: ignoredItems, userToken: userToken)
      ];
      return feedRows;
    } else {
      throw Exception('Failed to load ratings');
    }
  }
}
