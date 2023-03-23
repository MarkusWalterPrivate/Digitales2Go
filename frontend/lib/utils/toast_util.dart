import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class ToastUtil extends StatefulWidget {
  ToastUtil({Key? key, required this.toastColor, required this.icon, required this.toastText}) : super(key: key);
  Color toastColor;
  Icon icon;
  String toastText;
  @override
  State<ToastUtil> createState() => _ToastUtilState();
}

class _ToastUtilState extends State<ToastUtil> {
  final toast = FToast();
  @override
  void initState() {
    super.initState();
    toast.init(context);
  }

  @override
  Widget build(BuildContext context) {
    return buildToast(widget.toastColor, widget.toastText, widget.icon);
  }
  static Widget buildToast(Color toastColor, String toastText, Icon icon) => Container(
        padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
        decoration: BoxDecoration(
          borderRadius: const BorderRadius.all(
            Radius.circular(5),
          ),
          color: toastColor,
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            icon,
            const SizedBox(width: 12.0),
            Flexible(
              child: Text(
                toastText,
                style: const TextStyle(color: Colors.white, fontSize: 20),
              ),
            ),
          ],
        ),
      );
   void showTopToast(Color color, String text, Icon icon) => toast.showToast(
        child: buildToast(color, text, icon),
        toastDuration: const Duration(seconds: 1),
        gravity: ToastGravity.TOP,
      );

}

class DisplayToast {
  static showToast(FToast toast, Widget widget, ToastGravity toastGravity) {
    toast.showToast(child: widget,
    toastDuration: const Duration(milliseconds: 1500),
    gravity: toastGravity);
  }
}
