import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_markdown/flutter_markdown.dart';
import 'package:frontend/core/element_objects/coreField.dart';
import 'package:frontend/widgets/misc/tag.dart';
import 'package:intl/intl.dart';
import 'package:like_button/like_button.dart';
import 'package:share_plus/share_plus.dart';

import '../../constants/url.dart';

class TeaserWidget extends StatelessWidget {
  const TeaserWidget({Key? key, required this.teaserField}) : super(key: key);
  final String teaserField;
  @override
  Widget build(BuildContext context) {
    return SliverToBoxAdapter(
      child: Container(
        color: Theme.of(context).primaryColor,
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
                      Text(
                        teaserField,
                        style:
                            const TextStyle(color: Colors.white, fontSize: 18),
                        textAlign: TextAlign.center,
                      ),
                    ],
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}

class DescriptionWidget extends StatefulWidget {
  DescriptionWidget(
      {Key? key,
      required this.descriptionField,
      required this.isReadMore,
      this.descriptionMarkdown})
      : super(key: key);
  final String descriptionField;
  String? descriptionMarkdown;
  bool isReadMore;
  @override
  State<DescriptionWidget> createState() => _descriptionWidgetState();
}

class ShareScrollDownWidget extends StatelessWidget {
  ShareScrollDownWidget({
    Key? key,
    required this.coreField,
    required this.id,
    required this.scrollController,
  }) : super(
          key: key,
        );
  CoreField coreField;
  int id;
  ScrollController scrollController;
  @override
  Widget build(BuildContext context) {
    final Color primaryColor = Theme.of(context).primaryColor;

    return Wrap(direction: Axis.vertical, children: [
      Container(
        height: 48,
        width: 48,
        margin: const EdgeInsets.all(8),
        child: FloatingActionButton(
          backgroundColor: primaryColor.withOpacity(0.5),
          hoverColor: primaryColor,
          onPressed: () => {scrollController.jumpTo(240)},
          child: Icon(Icons.keyboard_double_arrow_down),
        ),
      ),
      Container(
        height: 48,
        width: 48,
        margin: const EdgeInsets.all(8),
        child: FloatingActionButton(
          backgroundColor: primaryColor.withOpacity(0.5),
          hoverColor: primaryColor,
          onPressed: () => {
            Share.share('''Name: ${coreField.headline}  
                                              
              Link: ${Constants.getWebUrl()}project/?id=$id''',
                subject: 'Schau dir dieses Projekt an!')
          },
          child: Padding(
            padding: const EdgeInsets.only(right: 2.0),
            child: Icon(Icons.share),
          ),
        ),
      ),
    ]);
  }
}

class _descriptionWidgetState extends State<DescriptionWidget> {
  @override
  Widget build(BuildContext context) {
    return SliverToBoxAdapter(
      child: Column(
        children: [
          SizedBox(height: 180, child: Markdown(data: widget.descriptionField)),
          // Padding(
          //   padding:
          //       const EdgeInsets.only(top: 15, bottom: 5, left: 15, right: 15),
          //   child: Text(
          //     widget.descriptionField,
          //     textAlign: TextAlign.justify,
          //     overflow: widget.isReadMore
          //         ? TextOverflow.visible
          //         : TextOverflow.ellipsis,
          //     maxLines: widget.isReadMore ? null : 6,
          //   ),
          // ),
          // ElevatedButton(
          //     style: ElevatedButton.styleFrom(
          //         primary: Theme.of(context).backgroundColor,
          //         elevation: 0,
          //         onPrimary: Theme.of(context).primaryColor,
          //         shadowColor: Colors.white,
          //         textStyle: const TextStyle(fontSize: 14)),
          //     onPressed: () =>
          //         setState(() => widget.isReadMore = !widget.isReadMore),
          //     child: Text(
          //         widget.isReadMore ? 'Weniger anzeigen' : 'Mehr anzeigen')),
        ],
      ),
    );
  }
}

class TagWidget extends StatelessWidget {
  const TagWidget({Key? key, required this.coreField}) : super(key: key);
  final CoreField coreField;
  @override
  Widget build(BuildContext context) {
    return SliverToBoxAdapter(
      child: Padding(
        padding: const EdgeInsets.only(left: 10, top: 10),
        child: Align(
          alignment: Alignment.topLeft,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Tag(text: coreField.industry),
              const SizedBox(width: 20),
              SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: ((Row(
                  children: [for (var item in coreField.tags) Tag(text: item)],
                ))),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class AuthorWidget extends StatelessWidget {
  const AuthorWidget({Key? key, required this.coreField}) : super(key: key);
  final CoreField coreField;

  @override
  Widget build(BuildContext context) {
    DateTime lastUpdated =
        DateTime.fromMillisecondsSinceEpoch(coreField.lastUpdated, isUtc: true);
    String formattedLastUpdated = DateFormat('dd.MM.yyyy').format(lastUpdated);
    DateTime creationDate = DateTime.fromMillisecondsSinceEpoch(
        coreField.creationDate,
        isUtc: true);
    String formattedCreationDate =
        DateFormat('dd.MM.yyyy').format(creationDate);
    return SliverToBoxAdapter(
        child: Padding(
            padding: const EdgeInsets.only(
              left: 15,
              top: 5,
              right: 15,
              bottom: 5,
            ),
            child: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.only(bottom: 5),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text.rich(
                        TextSpan(
                          style: const TextStyle(
                            fontSize: 14,
                          ),
                          children: [
                            WidgetSpan(
                              child: Icon(
                                Icons.person,
                                size: 16,
                                color: Theme.of(context).primaryColor,
                              ),
                            ),
                            TextSpan(
                                text: ' ${coreField.source} ',
                                style: const TextStyle(fontSize: 12)),
                            WidgetSpan(
                              child: Icon(
                                Icons.date_range,
                                size: 16,
                                color: Theme.of(context).primaryColor,
                              ),
                            ),
                            TextSpan(
                                text: ' $formattedCreationDate ',
                                style: const TextStyle(fontSize: 12)),
                            WidgetSpan(
                              child: Icon(
                                Icons.update,
                                size: 16,
                                color: Theme.of(context).primaryColor,
                              ),
                            ),
                            TextSpan(
                                text: ' $formattedLastUpdated',
                                style: const TextStyle(fontSize: 12)),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            )));
  }
}

class ScrollTopBarWidget extends StatelessWidget {
  const ScrollTopBarWidget({Key? key, required this.coreField})
      : super(key: key);
  final CoreField coreField;
  @override
  Widget build(BuildContext context) {
    Color primaryColor = Theme.of(context).primaryColor;
    return SliverAppBar(
      pinned: true,
      snap: false,
      floating: false,
      expandedHeight: 230.0,
      flexibleSpace: FlexibleSpaceBar(
        background: Container(
            decoration: BoxDecoration(
                image: DecorationImage(
                    image: NetworkImage(coreField.imageSource),
                    fit: BoxFit.cover)),
            child: Container(
              decoration: BoxDecoration(
                gradient: LinearGradient(colors: [
                  primaryColor.withOpacity(0.0),
                  primaryColor.withOpacity(0.1),
                  primaryColor.withOpacity(0.2),
                  primaryColor.withOpacity(0.3),
                  primaryColor.withOpacity(0.4),
                  primaryColor.withOpacity(0.5),
                  primaryColor.withOpacity(0.6),
                  primaryColor.withOpacity(0.7),
                  primaryColor.withOpacity(0.8),
                  primaryColor.withOpacity(0.9),
                  primaryColor.withOpacity(1),
                ], stops: const [
                  0.0,
                  0.1,
                  0.2,
                  0.3,
                  0.4,
                  0.5,
                  0.6,
                  0.7,
                  0.8,
                  0.9,
                  1
                ], begin: Alignment.topCenter, end: Alignment.bottomCenter),
              ),
            )),
        title: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            Wrap(
              children: [
                Text(
                  coreField.headline,
                  style: const TextStyle(fontSize: 24, color: Colors.white),
                ),
              ],
            )
          ],
        ),
        centerTitle: false,
      ),
    );
  }
}
