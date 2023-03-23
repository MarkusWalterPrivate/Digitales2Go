import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:frontend/core/detail_objects/company/scroll_to_hide_widget.dart';
import 'package:like_button/like_button.dart';

import '../../core/requests/feed_items_requests.dart';
import '../feed/feed_item.dart';
import 'swipecards/swipe_cards.dart';

class SwipeBarWidget extends StatefulWidget {
  SwipeBarWidget(
      {Key? key,
      required this.matchEngine,
      required this.scrollController,
      required this.userToken,
      required this.feedItem,
      required this.width})
      : super(key: key);

  String userToken;

  FeedItem feedItem;
  double width;
  ScrollController scrollController = ScrollController();
  MatchEngine matchEngine = MatchEngine();
  @override
  State<SwipeBarWidget> createState() => _SwipeBarWidgetState();
}

class _SwipeBarWidgetState extends State<SwipeBarWidget> {
  @override
  void initState() {
    super.initState();
  }

  Future<bool> bookMark(bool isLiked) {
    widget.feedItem.isBookmarked = !widget.feedItem.isBookmarked!;
    return setItemBookmark(isLiked, widget.userToken, widget.feedItem.id);
  }

  @override
  Widget build(BuildContext context) {
    return ScrollToHideWidget(
      controller: widget.scrollController,
      child: Container(
        height: 76,
        decoration: BoxDecoration(color: Theme.of(context).primaryColor),
        child: Padding(
          padding: const EdgeInsets.only(top: 8.0, bottom: 12),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  IconButton(
                    onPressed: (() async {
                      widget.matchEngine.currentItem?.nope();
                    }),
                    icon: const Icon(
                      Icons.thumb_down,
                      color: Colors.white,
                      size: 25,
                    ),
                    padding: const EdgeInsets.all(4),
                  ),
                  const Text(
                    'Irrelevant',
                    style: TextStyle(color: Colors.white),
                  ),
                ],
              ),

              /* GestureDetector(
                            onTap: (() async {
                              //TODO Next Topic implementation
                            }),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: const [
                                Icon(
                                  Icons.auto_stories,
                                  color: Colors.white,
                                  size: 20,
                                ),
                                Padding(padding: EdgeInsets.only(bottom: 5)),
                                Text(
                                  'Themawechsel',
                                  style: TextStyle(color: Colors.white),
                                ),
                              ],
                            ),
                          ), */
              Padding(
                padding: const EdgeInsets.only(top: 2.0),
                child: Column(children: [
                  LikeButton(
                      padding: const EdgeInsets.all(4),
                      isLiked: widget.feedItem.isBookmarked,
                      size: 25,
                      circleColor: const CircleColor(
                          start: Color(0xff00ddff), end: Color(0xff0099cc)),
                      bubblesColor: const BubblesColor(
                        dotPrimaryColor: Colors.deepOrangeAccent,
                        dotSecondaryColor: Colors.orangeAccent,
                      ),
                      likeBuilder: (bool isLiked) {
                        return Icon(
                          Icons.bookmark,
                          color:
                              isLiked ? Colors.deepOrangeAccent : Colors.grey,
                          size: 25,
                        );
                      },
                      onTap: ((isLiked) => bookMark(isLiked))),
                  const Padding(padding: EdgeInsets.only(bottom: 5)),
                  const Text(
                    'Merke',
                    style: TextStyle(color: Colors.white),
                  )
                ]),
              ),

              /* Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                isBookMarked
                                    ? Icon(
                                        Icons.bookmark_border,
                                        color: Colors.white,
                                        size: 25,
                                      )
                                    : Icon(
                                        Icons.bookmark,
                                        color: Colors.white,
                                        size: 25,
                                      ),
                                Padding(padding: EdgeInsets.only(bottom: 5)),
                                Text(
                                  'Merken',
                                  style: TextStyle(color: Colors.white),
                                ),
                              ],
                            ), */

              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  IconButton(
                    onPressed: (() async {
                      widget.matchEngine.currentItem?.skip();
                    }),
                    icon: const Icon(
                      Icons.skip_next,
                      color: Colors.white,
                      size: 25,
                    ),
                    padding: EdgeInsets.all(4),
                  ),
                  const Text(
                    'Skip',
                    style: TextStyle(color: Colors.white),
                  ),
                ],
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  IconButton(
                    onPressed: (() async {
                      widget.matchEngine.currentItem?.like();
                    }),
                    icon: const Icon(
                      Icons.thumb_up,
                      color: Colors.white,
                      size: 25,
                    ),
                    padding: EdgeInsets.all(4),
                  ),
                  const Text(
                    'Relevant',
                    style: TextStyle(color: Colors.white),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<bool> setItemBookmark(
      bool isLiked, String userToken, int itemID) async {
    bool success = false;
    if (isLiked == false) {
      final bool success =
          await Requester.setItemBookmarked(userToken, itemID, true, context);

      return true;
    } else {
      final bool success = await Requester.deleteItemBookmarked(
          userToken, itemID, true, context);
      return false;
    }

    /// send your request here
    // final bool success= await sendRequest();
    /// if failed, you can do nothing
  }
}

class SwipeBarSmallWidget extends StatelessWidget {
  SwipeBarSmallWidget({
    Key? key,
    required this.matchEngine,
    required this.width,
  }) : super(key: key);
  final MatchEngine matchEngine;
  double width;

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 100,
      width: width,
      child: Align(
        alignment: Alignment.topCenter,
        child: Padding(
          padding: const EdgeInsets.only(top: 32.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              GestureDetector(
                onTap: (() async {
                  matchEngine.currentItem?.nope();
                }),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: const [
                    Icon(
                      Icons.thumb_down,
                      color: Colors.white,
                      size: 25,
                    ),
                    Padding(padding: EdgeInsets.only(bottom: 5)),
                    Text(
                      'Irrelevant',
                      style: TextStyle(
                        decoration: TextDecoration.none,
                        fontSize: 20,
                        color: Colors.white,
                      ),
                    ),
                  ],
                ),
              ),
              GestureDetector(
                onTap: (() async {
                  matchEngine.currentItem?.skip();
                }),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: const [
                    Icon(
                      Icons.skip_next,
                      color: Colors.white,
                      size: 25,
                    ),
                    Padding(padding: EdgeInsets.only(bottom: 5)),
                    Text(
                      'Skip',
                      style: TextStyle(
                        decoration: TextDecoration.none,
                        fontSize: 20,
                        color: Colors.white,
                      ),
                    ),
                  ],
                ),
              ),
              GestureDetector(
                onTap: (() async {
                  matchEngine.currentItem?.like();
                }),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: const [
                    Icon(
                      Icons.thumb_up,
                      color: Colors.white,
                      size: 25,
                    ),
                    Padding(padding: EdgeInsets.only(bottom: 5)),
                    Text(
                      'Relevant',
                      style: TextStyle(
                        decoration: TextDecoration.none,
                        fontSize: 20,
                        color: Colors.white,
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
