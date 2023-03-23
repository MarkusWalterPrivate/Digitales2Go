import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'feed_item.dart';

class FeedRow extends StatefulWidget {
  const FeedRow(
      {Key? key,
      required this.feedItems,
      this.feedName,
      required this.userToken})
      : super(key: key);

  final List<FeedItem> feedItems;
  final String? feedName;
  final String userToken;

  @override
  State<FeedRow> createState() => _FeedRowState();
}

class _FeedRowState extends State<FeedRow> with SingleTickerProviderStateMixin {
  /// Creating a scroll controller, an animation controller, and an animation.
  final ScrollController _scrollController = ScrollController();
  bool isTapped = false;

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
    _scrollController.dispose();
  }

  bool hasFeedRowName(feedName) {
    if (feedName == null) {
      return false;
    } else {
      return true;
    }
  }

  /// It creates a column with a title and a horizontal scrollable list of feed
  /// items.
  ///
  /// Args:
  ///   context (BuildContext): The context of the widget.
  ///
  /// Returns:
  ///   A column with a title, a scrollbar, and a singlechildscrollview.
  @override
  Widget build(BuildContext context) {
    double maxWidth = MediaQuery.of(context).size.width;
    
    return Listener(
      onPointerDown: (PointerDownEvent details) {
        setState(() {
          isTapped = true;
        });
      },
      child: Column(
        children: [
          hasFeedRowName(widget.feedName)
              ? Container(
                color: Colors.grey[200],
                child: Align(
                    alignment: Alignment.bottomLeft,
                    child: Padding(
                        padding: const EdgeInsets.all(20.0),
                        child: Text(
                          widget.feedName!,
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 20,
                          ),
                        ))),
              )
              : SizedBox(
                  height: 0,
                  width: maxWidth,
                ),
          Stack(
            children: [
              Padding(
                padding: const EdgeInsets.only(bottom: 0),
                child: (SingleChildScrollView(
                    controller: _scrollController,
                    scrollDirection: Axis.horizontal,
                    child: (Column(
                      children: [
                        Container(
                            color: Colors.grey[300],
                            child: (Row(
                              children: [
                                for (FeedItem i in widget.feedItems)
                                  FeedItemWidget(
                                    userToken: widget.userToken,
                                    feedItem: i,
                                    feedItems: widget.feedItems,
                                  )
                              ],
                            ))),
                      ],
                    )))),
              ),
              Visibility(
                visible: !isTapped,
                maintainSize: true,
                maintainState: true,
                maintainAnimation: true,
                child: Container(
                  margin: EdgeInsets.only(left: (maxWidth - 60)),
                  width: 75,
                  height: 320,
                  decoration: const BoxDecoration(
                    gradient: LinearGradient(
                      stops: [0.0, 0.1, 0.2, 1],
                      colors: [
                        Colors.white10,
                        Colors.white60,
                        Colors.white70,
                        Color.fromARGB(255, 255, 255, 255),
                      ],
                    ),
                  ),
                ),
              )
            ],
          ),
        ],
      ),
    );
  }
}
