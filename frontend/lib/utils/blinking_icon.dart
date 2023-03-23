import 'package:flutter/material.dart';

class BlinkIcon extends StatefulWidget {
  BlinkIcon({Key? key, required this.icon}) : super(key: key);
  IconData icon;
  @override
  _BlinkIconState createState() => _BlinkIconState();
}

class _BlinkIconState extends State<BlinkIcon>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<Color?> _colorAnimation;

  @override
  void initState() {
    _controller =
        AnimationController(vsync: this, duration: Duration(milliseconds: 500));
    _colorAnimation = ColorTween(begin: Colors.blue, end: Colors.orange)
        .animate(CurvedAnimation(parent: _controller, curve: Curves.linear));
    _controller.addStatusListener((status) {
      if (status == AnimationStatus.completed) {
        _controller.reverse();
      } else if (status == AnimationStatus.dismissed) {
        _controller.forward();
      }
      setState(() {});
    });
    _controller.forward();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: _controller,
      builder: (context, child) {
        return Icon(
          widget.icon,
          color: _colorAnimation.value,
        );
      },
    );
  }
}
