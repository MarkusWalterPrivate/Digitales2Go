import 'package:flutter/material.dart';

class Tag extends StatefulWidget {
  Tag({Key? key, required this.text}) : super(key: key);
  String text;


  @override
  State<Tag> createState() => _TagState();
}

class _TagState extends State<Tag> {
  @override
  Widget build(BuildContext context) {
    final Color primaryContainer =
        Theme.of(context).colorScheme.primary;
    return Padding(
      padding: const EdgeInsets.only(
        top: 5,
        left: 5,
        right: 5,
        bottom: 5
      ),
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 4, horizontal: 8),
        decoration: BoxDecoration(
          gradient: LinearGradient(colors: [
          primaryContainer.withOpacity(1),
          primaryContainer.withOpacity(1),
          primaryContainer.withOpacity(1),
          primaryContainer.withOpacity(1),
          primaryContainer.withOpacity(1),
          primaryContainer.withOpacity(1),
          primaryContainer.withOpacity(1),
          primaryContainer.withOpacity(0.8),
          primaryContainer.withOpacity(0.6),
        ], begin: Alignment.topLeft, end: Alignment.bottomRight),
          borderRadius: BorderRadius.circular(5)
        ),
        child: Text(widget.text,
        style: const TextStyle(
          color: Colors.white,
          fontSize: 14,
        ),),
      ),
    );

    //----------------------------TODO decide which type-----------------------------
    // Padding(
    //   padding: const EdgeInsets.all(8.0),
    //   child: Container(
    //     decoration: BoxDecoration(
    //       color: widget.color,
    //       borderRadius: const BorderRadius.all(Radius.circular(8)),
    //     ),
    //     child: Padding(
    //       padding: const EdgeInsets.all(3.0),
    //       child: Text(
    //         widget.text,
    //       ),
    //     ),
    //   ),
    // );
  }
}
