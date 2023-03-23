import 'package:flutter/material.dart';
import 'package:frontend/core/enums/industry.dart';

class DropdownScheme extends StatefulWidget {
  DropdownScheme(
      {Key? key,
      required this.label,
      required this.labelColor,
      required this.icon,
      required this.dropdownValue,
      required this.onChanged,
      required this.validator})
      : super(key: key);
  String label;
  Color labelColor;
  Icon icon;
  String dropdownValue;
  Function(String?) onChanged;
  String? Function(String?)? validator;
  @override
  State<DropdownScheme> createState() => _DropdownSchemeState();
}

class _DropdownSchemeState extends State<DropdownScheme> {
  late List<String> listOfIndustries;

  @override
  void initState() {
    super.initState();
    listOfIndustries = IndustryString.getIndustryString();
    listOfIndustries.sort((a, b) => a.toString().compareTo(b.toString()));
  }

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
              color: widget.labelColor,
              fontSize: 16,
              fontWeight: FontWeight.bold),
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
            child: DropdownButtonHideUnderline(
              child: DropdownButtonFormField<String>(
                  validator: widget.validator,
                  isExpanded: true,
                  focusColor: primaryGreen,
                  decoration: InputDecoration(
                    filled: true,
                    fillColor: Colors.white,
                    prefixIcon: widget.icon,
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(5)),
                    enabledBorder: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(5),
                        borderSide: const BorderSide(color: Colors.white)),
                    errorStyle: const TextStyle(fontSize: 14),
                    focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: primaryGreen, width: 3),
                      borderRadius: BorderRadius.circular(5),
                    ),
                  ),
                  hint: const Text(
                    'Branche auswählen...',
                  ),
                  items: listOfIndustries
                      .map<DropdownMenuItem<String>>((String value) {
                    return DropdownMenuItem<String>(
                      value: value,
                      child: Text(
                        value,
                        style: const TextStyle(color: Colors.black),
                      ),
                    );
                  }).toList(),
                  onChanged: widget.onChanged),
            ),
          ),
        )
      ],
    );
  }
}
