import 'package:flutter/material.dart';

class TextFieldScheme extends StatefulWidget {
  TextFieldScheme(
      {Key? key,
      required this.label,
      required this.labelColor,
      required this.icon,
      this.textInputType,
      required this.obscureText,
      this.controller,
      this.validator,
      this.suffixIcon,
      this.onChanged})
      : super(key: key);
  String label;
  Color labelColor;
  Icon icon;
  TextInputType? textInputType;
  bool obscureText;
  TextEditingController? controller;
  String? Function(String?)? validator;
  IconButton? suffixIcon;
  Function(String)? onChanged;

  @override
  State<TextFieldScheme> createState() => _TextFieldSchemeState();
}

class _TextFieldSchemeState extends State<TextFieldScheme> {
  @override
  Widget build(BuildContext context) {
    final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);
    final Color errorColor = Theme.of(context).colorScheme.error;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          widget.label,
          style: TextStyle(
              color: widget.labelColor, fontSize: 16, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 10),
        Container(
          decoration: const BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.all(Radius.circular(5))),
          child: Container(
            decoration: BoxDecoration(
                color: errorColor.withOpacity(0.2),
                borderRadius: const BorderRadius.all(Radius.circular(5))),
            child: TextFormField(
              onChanged: widget.onChanged,
              validator: widget.validator,
              controller: widget.controller,
              obscureText: widget.obscureText,
              keyboardType: widget.textInputType,
              decoration: InputDecoration(
                hintText: widget.label,
                suffixIcon: widget.suffixIcon,
                prefixIcon: widget.icon,
                filled: true,
                fillColor: Colors.white,
                errorStyle: const TextStyle(fontSize: 14),
                focusedErrorBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(5),
                    borderSide: BorderSide(color: errorColor)),
                enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(5),
                    borderSide: const BorderSide(color: Colors.white)),
                errorBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(5),
                    borderSide: BorderSide(color: errorColor)),
                focusedBorder: OutlineInputBorder(
                  borderSide: BorderSide(color: primaryGreen, width: 3),
                  borderRadius: BorderRadius.circular(5),
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
