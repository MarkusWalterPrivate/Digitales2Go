import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class ScrollToHideWidget extends StatefulWidget {
  final Widget child;
  final ScrollController controller;
  final Duration duration;

  const ScrollToHideWidget({
    Key? key,
    required this.child,
    required this.controller,
    this.duration = const Duration(milliseconds: 200),
  }) : super(key: key);

  @override
  _ScrollToHideWidgetState createState() => _ScrollToHideWidgetState();
}

class _ScrollToHideWidgetState extends State<ScrollToHideWidget> {
  bool isVisible = true;

  @override
  void initState(){
    super.initState();
    widget.controller.addListener(listen);
  }

  @override
  void dispose() {
    widget.controller.removeListener(listen);

    super.dispose();
  }

  void listen() {
    if (widget.controller.position.pixels > 0) {
      hide();
    } else {
      show();
    }
  }

  void show() {
    if (!isVisible) setState(() => isVisible = true);
  }

  void hide () {
    if (isVisible) setState(() => isVisible = false);
  }

  @override
  Widget build(BuildContext context) => AnimatedContainer(
      duration: widget.duration,
      height: isVisible ? 100 : 75,
      child: Wrap(children: [widget.child]));
  
}
