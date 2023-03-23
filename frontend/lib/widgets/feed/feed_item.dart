import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:frontend/core/element_objects/coreField.dart';
import 'package:frontend/utils/size_helper.dart';
import 'package:frontend/utils/tag_util.dart';
import 'package:frontend/widgets/swipe/swipe_card_widget.dart';
import '../../core/requests/feed_items_requests.dart';

/// A single item represented in feed view
///
/// Args:
///   int id,
///   CoreField coreField: containing all required informations to display
class FeedItemWidget extends StatefulWidget {
  FeedItemWidget(
      {Key? key,
      required this.feedItem,
      this.feedItems,
      required this.userToken})
      : super(key: key);
  List<FeedItem>? feedItems;
  final String userToken;

  FeedItem feedItem;
  @override
  State<FeedItemWidget> createState() => _FeedItemWidgetState();
}

/*   MatchEngine matchEngine =
      MatchEngine(swipeItems: SwipeWidget.getSwipeItems([],userToken)); */
//factory FeedItem.fromJson(json, String userToken);

/// It creates a widget that displays a card with a title, description, image and
/// tags.
///
/// Args:
///   context (BuildContext): The BuildContext of the parent widget.
///
/// Returns:
///   A widget
class _FeedItemWidgetState extends State<FeedItemWidget> {
  late bool isBookmarkSet;
  late bool isBookmarked;

  @override
  void initState() {
    super.initState();
    isBookmarked = widget.feedItem.isBookmarked!;
    isBookmarkSet = isBookmarked;
  }

  @override
  Widget build(BuildContext context) {
    final toast = FToast().init(context);
    final Color errorColor = Color(0xffffba1b1b);
    final Color primaryColor = Theme.of(context).primaryColor;
    final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);
    double width = displayWidth(context);
    double height = displayHeight(context);

    //Get Icons from Util per ID
    List<Icon> icons = [];
    for (String tag in widget.feedItem.coreField.tags) {
      Icon? icon = TagUtil.getTag(tag);
      if (icon != null) {
        icons.add(icon);
      }
    }

    // Navigation to corresponding detail page
    return GestureDetector(
      onLongPress: () async {
        if (isBookmarked == false) {
          await Requester.setItemBookmarked(
                  widget.feedItem.userToken, widget.feedItem.id, true, context)
              .then((value) {
            setState(() {
              isBookmarkSet = value;
              isBookmarked = isBookmarkSet;
            });
            widget.feedItem.isBookmarked = isBookmarked;
          });
        } else {
          await Requester.deleteItemBookmarked(
                  widget.feedItem.userToken, widget.feedItem.id, true, context)
              .then((value) {
            setState(() {
              isBookmarkSet = value;
              isBookmarked = isBookmarkSet;
            });
          });
          widget.feedItem.isBookmarked = isBookmarked;
        }
      },
      onTap: () async {
        List<FeedItem> feedItems = [];
        feedItems += widget.feedItems!;
        feedItems.removeWhere((value) => value.id == widget.feedItem.id);
        if (widget.feedItems != null) {
          feedItems = [widget.feedItem] + feedItems;
        } else {
          feedItems = [widget.feedItem];
        }
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => SwipeCardWidget(
                    feedItems: feedItems, userToken: widget.userToken)));
      },
      child: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Container(
          padding: const EdgeInsets.all(8.0),
          width: 180,
          height: 300,
          alignment: Alignment.topLeft,
          decoration: const BoxDecoration(
            borderRadius: BorderRadius.all(Radius.circular(10)),
            color: Colors.white,
          ),
          child: Align(
            alignment: Alignment.topLeft,
            child: (Column(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // headline and icons
                Wrap(
                  alignment: WrapAlignment.start,
                  children: [
                    Padding(
                      padding: const EdgeInsets.only(bottom: 5, right: 5),
                      child: RichText(
                        text: TextSpan(
                          style: TextStyle(
                              fontSize: 14,
                              fontWeight: FontWeight.bold,
                              color: primaryColor),
                          children: [
                            TextSpan(text: widget.feedItem.coreField.headline),
                            WidgetSpan(
                              child: Wrap(
                                alignment: WrapAlignment.start,
                                children: [
                                  for (Icon icon in icons)
                                    Icon(
                                      icon.icon,
                                      size: 15,
                                      color: primaryColor,
                                    )
                                ],
                              ),
                            )
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                // image and teaser
                Align(
                  alignment: Alignment.centerLeft,
                  child: Column(
                    children: [
                      SizedBox(
                        width: 200,
                        height: 100,
                        child: FeedImage(
                            image: widget.feedItem.coreField.imageSource),
                      ),
                      const Padding(padding: EdgeInsets.all(5)),
                      SizedBox(
                        width: 200,
                        height: 100,
                        child: Column(
                          children: [
                            SizedBox(
                              width: 200,
                              height: 70,
                              child: Text(
                                  widget.feedItem.coreField.teaser.length > 200
                                      ? '${widget.feedItem.coreField.teaser.substring(0, 200)}...'
                                      : widget.feedItem.coreField.teaser,
                                  maxLines: 5,
                                  overflow: TextOverflow.clip,
                                  style: const TextStyle(fontSize: 10)),
                            ),
                            SizedBox(
                              width: 200,
                              height: 30,
                              child: Column(
                                children: [
                                  const Divider(color: Colors.black),
                                  Align(
                                      alignment: Alignment.bottomLeft,
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceBetween,
                                        children: [
                                          Text(
                                            // feedItem.coreField.type,
                                            widget.feedItem.coreField.type,
                                            style: const TextStyle(
                                                color: Colors.grey,
                                                fontSize: 8,
                                                fontWeight: FontWeight.bold),
                                          ),
                                          isBookmarkSet
                                              ? Icon(
                                                  Icons.bookmark,
                                                  color: primaryColor,
                                                  size: 14,
                                                )
                                              : Icon(
                                                  Icons.bookmark_border,
                                                  color: primaryColor,
                                                  size: 14,
                                                )
                                        ],
                                      ))
                                ],
                              ),
                            ),
                          ],
                        ),
                      )
                    ],
                  ),
                )
              ],
            )),
          ),
        ),
      ),
    );
  }

  Future<bool> updateItemStatus(int itemId) async {
    FeedItem updatedFeedItemFuture;
    updatedFeedItemFuture = await Requester.getFeedItemById(itemId);
    if (updatedFeedItemFuture.isBookmarked == true) {
      return Future.value(true);
    } else {
      return Future.value(false);
    }
  }
}

class FeedImage extends StatelessWidget {
  const FeedImage({Key? key, required this.image}) : super(key: key);
  final String image;

  /// It returns a container with a width of 150, a height of 84, and a border
  /// radius of 5. It also has an image that is a network image with a fit of
  /// BoxFit.fitWidth
  ///
  /// Args:
  ///   context (BuildContext): BuildContext
  ///
  /// Returns:
  ///   A container with a width of 150, height of 84, and a border radius of 5. The
  /// image is being fit to the width of the container.
  /// It returns a container with a width of 150, a height of 84, and a border
  /// radius of 5. It also has an image that is a network image with a fit of
  /// BoxFit.fitWidth.
  ///
  /// Args:
  ///   context (BuildContext): BuildContext
  ///
  /// Returns:
  ///   A container with a width of 150, height of 84, and a border radius of 5. The
  /// image is being fit to the width of the container.
  @override
  Widget build(BuildContext context) {
    return Container(
        width: 150,
        height: 84,
        decoration: BoxDecoration(
            borderRadius: const BorderRadius.all(Radius.circular(5)),
            image: DecorationImage(
                image: NetworkImage(image), fit: BoxFit.fitHeight)));
  }
}

class FeedItem {
  final int id;
  final CoreField coreField;
  final int creationDate;
  final double ratingCount;
  final String industry;
  bool? isBookmarked;
  bool? rated;
  String userToken;
  double rating;

  FeedItem(
      {required this.id,
      required this.coreField,
      required this.rating,
      required this.creationDate,
      required this.ratingCount,
      required this.industry,
      this.isBookmarked,
      this.rated,
      required this.userToken});

  factory FeedItem.fromJson(Map<String, dynamic> json, String userToken) {
    return FeedItem(
      id: json['id'] as int,
      coreField: CoreField.fromJson(json['coreField']),
      rating: json['rating'],
      creationDate: json['creationDate'],
      ratingCount: json['ratingCount'],
      industry: json['industry'],
      isBookmarked: json['bookmarked'] ??= false,
      rated: json['rated'] ??= false,
      userToken: userToken,
    );
  }
}
