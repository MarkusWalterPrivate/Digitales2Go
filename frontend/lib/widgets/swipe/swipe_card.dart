import 'package:flutter/material.dart';
import 'package:frontend/core/atomic_objects/return_dto.dart';
import 'package:frontend/core/requests/feed_items_requests.dart';
import 'package:frontend/core/swipe_objects/swipe_detail_object.dart';
import 'package:frontend/core/swipe_objects/swipe_logic.dart';

import 'package:frontend/utils/dummy_data.dart';
import 'package:frontend/widgets/detail/company_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/project_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/technology_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/trend_detail_widget_swipe.dart';
import 'package:frontend/widgets/swipe/swipe_card_widget.dart';

import '../../core/detail_objects/project/project_detail_obj.dart';
import '../../utils/size_helper.dart';
import '../detail/project_detail_widget.dart';
import '../feed/feed_item.dart';
import 'swipe_tutorial_card.dart';
import 'swipecards/swipe_cards.dart';

class SwipeWidget extends StatefulWidget {
  SwipeWidget({Key? key, required this.userToken}) : super(key: key);

  String userToken;
  @override
  State<SwipeWidget> createState() => _SwipeWidgetState();
}

class _SwipeWidgetState extends State<SwipeWidget> {
  late Future<FeedReturnDTO> futureFeedItemsList;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    futureFeedItemsList = Requester.getNewItems(widget.userToken);
  }

  @override
  Widget build(BuildContext context) {
    double width = displayWidth(context);
    double height = displayHeight(context);

    return FutureBuilder<FeedReturnDTO>(
        future: futureFeedItemsList,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            return SwipeCardWidget(
              feedItems: snapshot.data!.feed,
              userToken: widget.userToken,
              backOnFinished: false,
            );
          } else if (snapshot.hasError) {
            print(snapshot.error);
            return Text("could not load object");
          } else {
            return CircularProgressIndicator();
          }
        });
  }

  /* TODO: implementiere relevant und irrelevant buttons */

}
