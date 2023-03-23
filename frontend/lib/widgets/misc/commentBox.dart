import 'package:flutter/material.dart';

class CommentBox extends StatefulWidget {
  CommentBox({Key? key, required this.text, required this.author, required this.dateCommented})
      : super(key: key);
  String text;
  String author;
  String dateCommented;

  @override
  State<CommentBox> createState() => _CommentBoxState();
}

class _CommentBoxState extends State<CommentBox> {
  @override
  Widget build(BuildContext context) {
    final Color primaryContainer =
        Theme.of(context).colorScheme.primary;
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 7, horizontal: 15),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
                color: Colors.grey.withOpacity(0.3),
                borderRadius: BorderRadius.circular(5)),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
              Text(
                widget.author,
                style: const TextStyle(
                  color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.bold
                ),
              ),
              const Padding(padding: EdgeInsets.only(bottom: 5)),
              Text(
                widget.text,
                style: const TextStyle(
                  color: Colors.black,
                  fontSize: 14,
                ),
              ),
            ]),
          ),
          Container(padding: const EdgeInsets.only(top: 5),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              const SizedBox(width: 5),
              Text(widget.dateCommented),
              const SizedBox(width: 20),
              Text("Like",
              style: TextStyle(color: primaryContainer),),
              const SizedBox(width: 20),
              Text("Reply",
              style: TextStyle(color: primaryContainer),)
            ],
          ))
        ],
      ),
    );
  }
}
