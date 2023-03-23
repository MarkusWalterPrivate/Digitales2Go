import 'package:flutter/cupertino.dart';

import '../../widgets/detail/company_detail_widget_swipe.dart';
import '../../widgets/detail/project_detail_widget_swipe.dart';
import '../../widgets/detail/technology_detail_widget_swipe.dart';
import '../../widgets/detail/trend_detail_widget_swipe.dart';
import '../../widgets/feed/feed_item.dart';
import '../../widgets/swipe/swipecards/swipe_cards.dart';
import '../requests/feed_items_requests.dart';

class SwipeLogic {
  static List<SwipeItem> getSwipeItems(
      List<FeedItem> feedItems, String userToken) {
    List<SwipeItem> swipeItems = [];
    feedItems.forEach((element) {
      SwipeItem item = SwipeItem(
        feedItem: element,
        likeAction: () => {
          Requester.setItemRelevant(userToken, element.id),
          SwipeSession.swipeTutorialDone()
        },
        nopeAction: () => {
          Requester.setItemIrrelevant(userToken, element.id),
          SwipeSession.swipeTutorialDone()
        },
        skipAction: () => {
          Requester.setItemSkip(userToken, element.id),
          SwipeSession.swipeTutorialDone()
        },
      );
      swipeItems.add(item);
    });
    return swipeItems;
  }

  static Widget getDetailViewFromFeedItem(
      FeedItem feedItem, MatchEngine matchEngine, String userToken) {
    Widget widget;

    switch (feedItem.coreField.type) {
      case "Firma":
        widget = CompanyDetailViewSwipe(
          feedItem: feedItem,
          matchEngine: matchEngine,
          isSharedItem: false,
          id: feedItem.id,
          token: userToken,
        );
        break;
      //swipeItem.detailObject.companyView;

      case "Projekt":
        widget = ProjectDetailViewSwipe(
          feedItem: feedItem,
          matchEngine: matchEngine,
          isSharedItem: false,
          id: feedItem.id,
          token: userToken,
        );
        break;

      case "Technologie":
        widget = TechnologyDetailViewSwipe(
          feedItem: feedItem,
          matchEngine: matchEngine,
          isSharedItem: false,
          id: feedItem.id,
          token: userToken,
        );
        break;

      case "Trend":
        widget = TrendDetailViewSwipe(
            feedItem: feedItem,
            matchEngine: matchEngine,
            isSharedItem: false,
            id: feedItem.id,
            token: userToken);
        break;
      default:
        widget = Container(
          height: 200,
          width: 200,
          child: Text("ERROR something went wrong" +
              feedItem.coreField.type.toString()),
        );
    }

    return widget;
  }
}

class SwipeSession {
  static bool hasSwipedOnce = false;

  static void swipeTutorialDone() {
    if (!hasSwipedOnce) {
      hasSwipedOnce = true;
    } else {}
  }
}
