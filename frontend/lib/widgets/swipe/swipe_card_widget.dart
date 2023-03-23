import 'package:flutter/material.dart';
import 'package:frontend/utils/size_helper.dart';
import 'package:frontend/widgets/swipe/swipe_tutorial_card.dart';
import 'package:frontend/widgets/swipe/swipecards/swipe_cards.dart';

import '../../core/requests/feed_items_requests.dart';
import '../../core/swipe_objects/swipe_logic.dart';
import '../../utils/dummy_data.dart';
import '../detail/company_detail_widget_swipe.dart';
import '../detail/project_detail_widget_swipe.dart';
import '../detail/technology_detail_widget_swipe.dart';
import '../detail/trend_detail_widget_swipe.dart';
import '../feed/feed_item.dart';

class SwipeCardWidget extends StatefulWidget {
  SwipeCardWidget(
      {Key? key,
      required this.feedItems,
      required this.userToken,
      this.backOnFinished = true})
      : super(key: key);
  List<FeedItem> feedItems;
  String userToken;

  bool backOnFinished;

  @override
  State<SwipeCardWidget> createState() => _SwipeCardWidgetState();
}

class _SwipeCardWidgetState extends State<SwipeCardWidget> {
  bool isStackDone = false;
  @override
  Widget build(BuildContext context) {
    double width = displayWidth(context);
    double height = displayHeight(context);
    bool hasTutorial = !SwipeSession.hasSwipedOnce;
    List<SwipeItem> swipeItems =
        SwipeLogic.getSwipeItems(widget.feedItems, widget.userToken);
    FeedItem dummy = DummyData.getFeedItem();
    SwipeItem swipeItemDummy = SwipeItem(feedItem: dummy);
    if (hasTutorial) {
      swipeItems.insert(0, swipeItemDummy);
    }
    MatchEngine matchEngine = MatchEngine(swipeItems: swipeItems);
    return Padding(
        padding: const EdgeInsets.all(4.0),
        child: Container(
          width: width,
          height: height - 65,
          child: SwipeCards(
            upSwipeAllowed: false,
            itemBuilder: (BuildContext context, int index) {
              if (index < swipeItems.length) {
                return ClipRRect(
                  borderRadius: BorderRadius.circular(24),
                  child: Container(
                      child: index < 1 && hasTutorial
                          ? TutorialCard(
                              matchEngine: matchEngine,
                            )
                          : SwipeLogic.getDetailViewFromFeedItem(
                              swipeItems[index].feedItem,
                              matchEngine,
                              widget.userToken)

                      /*  CompanyDetailViewSwipe(
                            matchEngine: matchEngine, isSharedItem: true, id: 2), */
                      ),
                );
              } else {
                return Container();
              }
            },
            matchEngine: matchEngine,
            onStackFinished: () {
              if (widget.backOnFinished) {
                Navigator.of(context).pop(true);
              } else {
                content:
                Container(
                  width: width,
                  height: height - 65,
                  decoration: BoxDecoration(
                      color: Theme.of(context).primaryColor,
                      image: DecorationImage(
                          opacity: 0.5,
                          image: NetworkImage(
                              "https://www.pixelstalk.net/wp-content/uploads/2016/07/HD-Peaceful-Wallpapers-620x388.jpg"))),
                  child: Column(children: [
                    Padding(padding: EdgeInsets.only(top: height * 0.3)),
                    (Text("Alle Themen geswiped, schau morgen nochmal vorbei!"))
                  ]),
                );
              }

              null;
              // DummyData.getSwipeItemsGeneral();
            },
          ),
        ));
  }
}
