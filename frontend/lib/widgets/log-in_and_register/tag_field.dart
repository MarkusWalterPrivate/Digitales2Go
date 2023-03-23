import 'package:flutter/material.dart';
import 'package:textfield_tags/textfield_tags.dart';

import '../../core/enums/industry.dart';

class TagFieldScheme extends StatefulWidget {
  TagFieldScheme(
      {Key? key,
      required this.label,
      required this.labelColor,
      required this.icon,
      this.controller,
      required this.validator,
      required this.distanceToField,
      required this.initialTags})
      : super(key: key);
  String label;
  Color labelColor;
  Icon icon;
  TextfieldTagsController? controller;
  String? Function(String) validator;
  double distanceToField;
  List<String>? initialTags;

  @override
  State<TagFieldScheme> createState() => _TagFieldSchemeState();
}

class _TagFieldSchemeState extends State<TagFieldScheme> {
  late List<String> listOfIndustries;
  String selected = "";
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
            child: TextFieldTags(
              textfieldTagsController: widget.controller,
              initialTags: widget.initialTags,
              textSeparators: const [';', ','],
              letterCase: LetterCase.normal,
              validator: widget.validator,
              inputfieldBuilder:
                  (context, tec, fn, error, onChanged, onSubmitted) {
                return ((context, sc, tags, onTagDelete) {
                  return Autocomplete<String>(
                    optionsBuilder: (TextEditingValue textEditingValue) {
                      return listOfIndustries
                          .where((String option) => option
                              .toLowerCase()
                              .startsWith(textEditingValue.text.toLowerCase()))
                          .toList();
                    },
                    displayStringForOption: (String option) => selected,
                    fieldViewBuilder: (context,
                        tec,
                        fn,
                        onSubmitted) {
                      return TextField(
                        controller: tec,
                        focusNode: fn,
                        decoration: InputDecoration(
                          filled: true,
                          fillColor: Colors.white,
                          border: const OutlineInputBorder(
                            borderSide: BorderSide(
                              color: Color.fromARGB(255, 74, 137, 92),
                              width: 3.0,
                            ),
                          ),
                          errorStyle: const TextStyle(fontSize: 14),
                          focusedBorder: OutlineInputBorder(
                            borderSide:
                                BorderSide(color: primaryGreen, width: 3),
                            borderRadius: BorderRadius.circular(5),
                          ),
                          focusedErrorBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(5),
                              borderSide: BorderSide(color: errorColor)),
                          enabledBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(5),
                              borderSide:
                                  const BorderSide(color: Colors.white)),
                          errorBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(5),
                              borderSide: BorderSide(color: errorColor)),
                          helperStyle: const TextStyle(
                            color: Color.fromARGB(255, 74, 137, 92),
                          ),
                          hintText:
                              widget.controller!.hasTags ? '' : widget.label,
                          errorText: error,
                          // prefixIconConstraints: BoxConstraints(
                              // maxWidth: widget.distanceToField * 0.3),
                          prefixIcon: tags.isNotEmpty
                              ? Wrap(
                                // spacing: 10,
                                // runSpacing: 10,
                                direction: Axis.vertical,
                                  children: tags.map((String tag) {
                                return Padding(
                                  padding: const EdgeInsets.only(top: 5, bottom: 5, left: 5),
                                  child: Container(
                                    decoration: BoxDecoration(
                                      borderRadius: const BorderRadius.all(
                                        Radius.circular(20.0),
                                      ),
                                      color: primaryGreen,
                                    ),
                                    margin: const EdgeInsets.symmetric(
                                        horizontal: 5.0),
                                    padding: const EdgeInsets.symmetric(
                                        horizontal: 10.0, vertical: 5.0),
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.spaceBetween,
                                      children: [
                                        InkWell(
                                          child: Text(
                                            '#$tag',
                                            style: const TextStyle(
                                                color: Colors.white),
                                          ),
                                          onTap: () {
                                            print("$tag selected");
                                          },
                                        ),
                                        const SizedBox(width: 4.0),
                                        InkWell(
                                          child: const Icon(Icons.cancel,
                                              size: 14.0,
                                              color: Colors.white),
                                          onTap: () {
                                            onTagDelete(tag);
                                          },
                                        )
                                      ],
                                    ),
                                  ),
                                );
                              }).toList())
                              : Padding(
                                  padding:
                                      const EdgeInsets.only(left: 8, right: 8),
                                  child: widget.icon
                                  // Icon(Icons.interests, color: primaryGreen)
                                  ),
                        ),
                        onChanged: onChanged,
                        // onSubmitted: onSubmitted,
                      );
                    },
                    optionsViewBuilder: (context,
                        AutocompleteOnSelected<String> onSelected,
                        Iterable<String> options) {
                      return Align(
                        alignment: Alignment.topLeft,
                        child: Material(
                          child: Container(
                            width: 500,
                            color: Colors.white,
                            child: ListView.builder(
                              padding: const EdgeInsets.all(10.0),
                              itemCount: options.length,
                              itemBuilder: (BuildContext context, int index) {
                                final String option = options.elementAt(index);
                                return GestureDetector(
                                  onTap: () {
                                    onSelected(option);
                                    setState(() {
                                      onSubmitted!(option);
                                    });
                                  },
                                  child: ListTile(
                                    title: Text(option,
                                        style: const TextStyle(
                                            color: Colors.black)),
                                  ),
                                );
                              },
                            ),
                          ),
                        ),
                      );
                    },
                  );
                });
              },
            ),
          ),
        ),
      ],
    );
  }
}