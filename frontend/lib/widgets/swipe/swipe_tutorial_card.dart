import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:frontend/utils/blinking_icon.dart';
import 'package:frontend/utils/size_helper.dart';
import 'package:frontend/widgets/swipe/swipe_bar.dart';
import 'package:frontend/widgets/swipe/swipecards/swipe_cards.dart';
import 'package:provider/provider.dart';

import '../../core/swipe_objects/card_provider.dart';

class TutorialCard extends StatefulWidget {
  const TutorialCard({Key? key, required this.matchEngine}) : super(key: key);
  final MatchEngine matchEngine;
  @override
  State<TutorialCard> createState() => _TutorialCardState();
}

class _TutorialCardState extends State<TutorialCard> {
  @override
  Widget build(BuildContext context) {
    double height = MediaQuery.of(context).size.height -
        MediaQuery.of(context).padding.bottom -
        80;

    double width = displayWidth(context);

    return BackdropFilter(
      filter: ImageFilter.blur(sigmaX: 5, sigmaY: 5),
      child: Container(
        color: Theme.of(context).primaryColor.withOpacity(0.9),
        /*  decoration: BoxDecoration(
          gradient: const LinearGradient(
            colors: [Color(0xff374ABE), Color(0xff64B6FF)],
            begin: Alignment.centerLeft,
            end: Alignment.centerRight,
          ),
        ), */
        height: height,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Container(
                width: width,
                height: (height / 2.3),
                /* decoration: BoxDecoration(
                    gradient: LinearGradient(
                  begin: Alignment.topRight,
                  end: Alignment.bottomLeft,
                  colors: [
                    Colors.red,
                    Colors.yellow,
                  ],
                ) */
                child: Align(
                  alignment: Alignment.center,
                  child: (Column(
                    children: [
                      Padding(padding: EdgeInsets.only(top: height * 0.1)),
                      Text("WISCHE",
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            decoration: TextDecoration.none,
                            fontSize: 20,
                            color: Colors.black,
                          )),
                      Padding(padding: EdgeInsets.only(bottom: 16)),
                      Text("nach links",
                          style: TextStyle(
                            fontWeight: FontWeight.normal,
                            decoration: TextDecoration.none,
                            fontSize: 20,
                            color: Colors.black,
                          )),
                      Padding(
                        padding: EdgeInsets.only(bottom: 16),
                      ),
                      Text("für irrelevante Themen",
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            fontWeight: FontWeight.normal,
                            decoration: TextDecoration.none,
                            fontSize: 18,
                            color: Colors.black,
                          )),
                      Padding(
                        padding: EdgeInsets.only(bottom: 16),
                      ),
                      Container(
                        height: 32,
                        width: 32,
                        decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            border: Border.all(color: Colors.black, width: 2)),
                        child: Padding(
                          padding: EdgeInsets.only(right: 3),
                          child: Icon(
                            Icons.subdirectory_arrow_left,
                            color: Colors.black,
                          ),
                        ),
                      )
                    ],
                  )),
                )),
            Container(
              width: width,
              height: (height / 2.3),
              //color: Color.fromARGB(255, 23, 156, 125).withOpacity(0.9),
              /* decoration: BoxDecoration(
                  gradient: LinearGradient(
                begin: Alignment.topRight,
                end: Alignment.bottomLeft,
                colors: [
                  Colors.green,
                  Colors.lightGreen,
                ],
              )), */
              child: (Align(
                alignment: Alignment.center,
                child: (Column(
                  children: [
                    Padding(padding: EdgeInsets.only(top: height * 0.1)),
                    Text("WISCHE",
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          decoration: TextDecoration.none,
                          fontSize: 20,
                          color: Colors.black,
                        )),
                    Padding(padding: EdgeInsets.only(bottom: 16)),
                    Text("nach rechts",
                        style: TextStyle(
                          fontWeight: FontWeight.normal,
                          decoration: TextDecoration.none,
                          fontSize: 20,
                          color: Colors.black,
                        )),
                    Padding(
                      padding: EdgeInsets.only(bottom: 16),
                    ),
                    Text("für relevante Themen",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontWeight: FontWeight.normal,
                          decoration: TextDecoration.none,
                          fontSize: 18,
                          color: Colors.black,
                        )),
                    Padding(
                      padding: EdgeInsets.only(bottom: 16),
                    ),
                    Container(
                      height: 32,
                      width: 32,
                      decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          border: Border.all(color: Colors.black, width: 2)),
                      child: Padding(
                        padding: EdgeInsets.only(left: 5),
                        child: Icon(
                          Icons.subdirectory_arrow_right,
                        ),
                      ),
                    )
                  ],
                )),
              )),
            ),
            SwipeBarSmallWidget(
              matchEngine: widget.matchEngine,
              width: width,
            ),
          ],
        ),
      ),
    );
  }
}
