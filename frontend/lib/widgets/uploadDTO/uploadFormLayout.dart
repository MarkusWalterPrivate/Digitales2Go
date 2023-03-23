import 'dart:math';
import 'dart:ui' as ui;

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:intl/intl.dart';
import 'package:markdown_editable_textinput/format_markdown.dart';
import 'package:markdown_editable_textinput/markdown_text_input.dart';
import 'package:multiselect/multiselect.dart';
import 'package:textfield_tags/textfield_tags.dart';

class FixedPadding {
  static Padding addPaddingWidget(widget) {
    return Padding(padding: const EdgeInsets.only(top: 20), child: widget);
  }

  static Padding addPadding() {
    return const Padding(padding: EdgeInsets.only(top: 40));
  }
}

class TextLabel {
  static Padding addTextLabel(label) {
    return FixedPadding.addPaddingWidget(Text(label));
  }
}

class AdjustableScrollController extends ScrollController {
  AdjustableScrollController([int extraScrollSpeed = 40]) {
    super.addListener(() {
      ScrollDirection scrollDirection = super.position.userScrollDirection;
      if (scrollDirection != ScrollDirection.idle) {
        double scrollEnd = super.offset +
            (scrollDirection == ScrollDirection.reverse
                ? extraScrollSpeed
                : -extraScrollSpeed);
        scrollEnd = min(super.position.maxScrollExtent,
            max(super.position.minScrollExtent, scrollEnd));
        jumpTo(scrollEnd);
      }
    });
  }
}

class MultiDropDown extends StatefulWidget {
  MultiDropDown(
      {Key? key,
      required this.optionList,
      required this.onChanged,
      required this.chosenElements,
      required this.label})
      : super(key: key);

  final List<String> optionList;
  final String label;
  Function(List<String>) onChanged;
  List<String> chosenElements;

  @override
  State<StatefulWidget> createState() => _MultiDropDown();
}

class _MultiDropDown extends State<MultiDropDown> {
  @override
  Widget build(BuildContext context) {
    return FixedPadding.addPaddingWidget(
      DropDownMultiSelect(
        decoration: InputDecoration(
          labelText: widget.label,
          border: const OutlineInputBorder(
            borderSide: BorderSide(),
          ),
        ),
        onChanged: widget.onChanged,
        options: widget.optionList,
        selectedValues: widget.chosenElements,
      ),
    );
  }
}

class MultiDropDownPostRequest extends StatefulWidget {
  MultiDropDownPostRequest(
      {Key? key,
      required this.internalIdeas,
      required this.onChanged,
      required this.chosenElements,
      required this.label})
      : super(key: key);

  final Future<List<dynamic>> internalIdeas;
  final String label;
  Function(List<String>) onChanged;
  List<String> chosenElements;

  @override
  State<StatefulWidget> createState() => _MultiDropDownPostRequest();
}

class _MultiDropDownPostRequest extends State<MultiDropDownPostRequest> {
  @override
  Widget build(BuildContext context) {
    return FixedPadding.addPaddingWidget(
      FutureBuilder<List<dynamic>>(
        future: widget.internalIdeas,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            print(snapshot.data);
            List<dynamic>? fetchedData = snapshot.data;
            List<String> headlineTechnologyList = [];
            fetchedData
                ?.map((item) => headlineTechnologyList
                    .add("Id ${item.id}: ${item.coreField.headline}"))
                .toList();
            return DropDownMultiSelect(
              decoration: InputDecoration(
                labelText: widget.label,
                border: const OutlineInputBorder(
                  borderSide: BorderSide(),
                ),
              ),
              onChanged: widget.onChanged,
              options: headlineTechnologyList,
              selectedValues: widget.chosenElements,
            );
          } else if (snapshot.hasError) {
            print(snapshot.error);
            print(snapshot.stackTrace);
            throw Exception(snapshot.error);
          } else {
            return const CircularProgressIndicator();
          }
        },
      ),
    );
  }
}

class DTOTextField extends StatefulWidget {
  DTOTextField(
      {Key? key,
      required this.controller,
      required this.label,
      this.keyBoardType = TextInputType.text})
      : super(key: key);

  final String label;
  final TextEditingController controller;
  final TextInputType? keyBoardType;

  @override
  State<StatefulWidget> createState() => _TextField();
}

class _TextField extends State<DTOTextField> {
  @override
  Widget build(BuildContext context) {
    var inputKind = null;
    if (widget.keyBoardType == TextInputType.number) {
      inputKind = <TextInputFormatter>[FilteringTextInputFormatter.digitsOnly];
    }
    return FixedPadding.addPaddingWidget(
      TextFormField(
        keyboardType: widget.keyBoardType,
        controller: widget.controller,
        inputFormatters: inputKind,
        decoration: InputDecoration(
          labelText: widget.label,
          border: const OutlineInputBorder(
            borderSide: BorderSide(),
          ),
        ),
      ),
    );
  }
}

class DTOTextFieldExpanded extends StatefulWidget {
  const DTOTextFieldExpanded(
      {Key? key, required this.controller, required this.label})
      : super(key: key);

  final String label;
  final TextEditingController controller;

  @override
  State<StatefulWidget> createState() => _DTOTextFieldExpanded();
}

class _DTOTextFieldExpanded extends State<DTOTextFieldExpanded> {
  @override
  Widget build(BuildContext context) {
    return FixedPadding.addPaddingWidget(
      MarkdownTextInput(
            (String value) => setState(() => {}),
        widget.controller.text,
        label: widget.label,
        maxLines: 10,
        actions: MarkdownType.values,
        controller: widget.controller,
      ),
    );
  }
}

class DropDownMenu extends StatefulWidget {
  DropDownMenu(
      {Key? key,
      required this.dropDownList,
      required this.label,
      required this.onChanged})
      : super(key: key);
  final String label;
  final List<String> dropDownList;
  Function(String?) onChanged;

  @override
  State<StatefulWidget> createState() => _DropDownMenu();
}

class _DropDownMenu extends State<DropDownMenu> {
  @override
  Widget build(BuildContext context) {
    return FixedPadding.addPaddingWidget(DropdownButtonFormField<String>(
        isExpanded: true,
        value: null,
        decoration: InputDecoration(
          labelText: widget.label,
          border: const OutlineInputBorder(
            borderSide: BorderSide(),
          ),
        ),
        hint: const Text(
          'Branche auswählen...',
        ),
        items: widget.dropDownList.map((String value) {
          return DropdownMenuItem<String>(
            value: value,
            child: Text(value),
          );
        }).toList(),
        onChanged: widget.onChanged));
  }
}

class SubmitButton extends StatefulWidget {
  SubmitButton({Key? key, required this.label, required this.onPressMethod})
      : super(key: key);

  String label;
  VoidCallback? onPressMethod;

  @override
  State<StatefulWidget> createState() => _SubmitButton();
}

class _SubmitButton extends State<SubmitButton> {
  @override
  Widget build(BuildContext context) {
    return FixedPadding.addPaddingWidget(OutlinedButton(
        style: ButtonStyle(
          minimumSize: MaterialStateProperty.all(const Size(200, 50)),
          textStyle: MaterialStateProperty.all(
            const TextStyle(fontSize: 18),
          ),
        ),
        onPressed: widget.onPressMethod,
        child: Text(widget.label)));
  }
}

class RatingBarStar extends StatefulWidget {
  RatingBarStar({Key? key, required this.onRatingUpdate, required this.label})
      : super(key: key);
  Function(double) onRatingUpdate;
  String label;

  @override
  State<StatefulWidget> createState() => _RatingBarStar();
}

class _RatingBarStar extends State<RatingBarStar> {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        FixedPadding.addPaddingWidget(Text(widget.label)),
        const Padding(padding: EdgeInsets.only(bottom: 3)),
        RatingBar.builder(
          initialRating: 0,
          minRating: 1,
          maxRating: 5,
          direction: Axis.horizontal,
          allowHalfRating: true,
          itemCount: 5,
          itemPadding: const EdgeInsets.symmetric(horizontal: 3.0),
          itemBuilder: (context, _) => const Icon(
            Icons.star,
            color: Colors.amber,
          ),
          onRatingUpdate: widget.onRatingUpdate,
        )
      ],
    );
  }
}

class DatePicker extends StatefulWidget {
  DatePicker(
      {Key? key,
      required this.dateInput,
      required this.label,
      required this.onChanged})
      : super(key: key);
  TextEditingController dateInput;
  String label;
  Function(int, String?) onChanged;

  @override
  State<StatefulWidget> createState() => _DatePicker();
}

class _DatePicker extends State<DatePicker> {
  @override
  Widget build(BuildContext context) {
    return TextField(
      controller: widget.dateInput,
      //editing controller of this TextField
      decoration: InputDecoration(
          icon: const Icon(Icons.calendar_today), //icon of text field
          labelText: widget.label //label text of field
          ),
      readOnly: true,
      //set it true, so that user will not able to edit text
      onTap: () async {
        DateTime? pickedDate = await showDatePicker(
            context: context,
            initialDate: DateTime.now(),
            firstDate: DateTime(1975),
            //DateTime.now() - not to allow to choose before today.
            lastDate: DateTime(2100));

        if (pickedDate != null) {
          int pickedDateMilliseconds = pickedDate.millisecondsSinceEpoch.abs();
          widget.onChanged(pickedDateMilliseconds,
              DateFormat("dd-MM-yyyy").format(pickedDate));
        } else {}
      },
    );
  }
}

class RadioBox extends StatefulWidget {
  RadioBox({
    Key? key,
    required this.optionOne,
    required this.optionTwo,
    required this.groupValue,
    required this.valueOne,
    required this.valueTwo,
    required this.onChanged,
  }) : super(key: key);
  String optionOne;
  String optionTwo;
  var groupValue;
  Function(dynamic) onChanged;
  var valueOne;
  var valueTwo;

  @override
  State<StatefulWidget> createState() => _RadioBox();
}

class _RadioBox extends State<RadioBox> {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Radio(
                    value: widget.valueOne,
                    groupValue: widget.groupValue,
                    onChanged: widget.onChanged),
                Text(widget.optionOne)
              ],
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Radio(
                    value: widget.valueTwo,
                    groupValue: widget.groupValue,
                    onChanged: widget.onChanged),
                Text(widget.optionTwo)
              ],
            )
          ],
        )
      ],
    );
  }
}

/**
 * FixedPadding.addPaddingWidget(ListTile(
    title: Text(widget.optionOne),
    leading: Radio(
    value: widget.valueOne,
    groupValue: widget.groupValue,
    onChanged: widget.onChanged),
    )),
    ListTile(
    title: Text(widget.optionTwo),
    leading: Radio(
    value: widget.valueTwo,
    groupValue: widget.groupValue,
    onChanged: widget.onChanged),
    ),
 */
class TagField extends StatefulWidget {
  TagField({Key? key, required this.controller, required this.label})
      : super(key: key);
  TextfieldTagsController? controller;
  String label;

  @override
  State<StatefulWidget> createState() => _TagField();
}

class _TagField extends State<TagField> {
  @override
  Widget build(BuildContext context) {
    final double distanceToField = MediaQuery.of(context).size.width;
    final Color primaryColor = Theme.of(context).primaryColor;
    return FixedPadding.addPaddingWidget(TextFieldTags(
      textfieldTagsController: widget.controller,
      textSeparators: const [",", ";", " "],
      letterCase: LetterCase.normal,
      validator: (String tag) {
        if (widget.controller!.getTags!.contains(tag)) {
          return 'Already entered that';
        }
        return null;
      },
      inputfieldBuilder: (context, tec, fn, error, onChanged, onSubmitted) {
        return ((context, sc, tags, onTagDelete) {
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 1.0),
            child: TextField(
              controller: tec,
              focusNode: fn,
              decoration: InputDecoration(
                border: OutlineInputBorder(
                  borderSide: BorderSide(color: primaryColor, width: 3.0),
                ),
                hintText: widget.controller!.hasTags ? '' : widget.label,
                errorText: error,
                prefixIconConstraints:
                    BoxConstraints(maxWidth: distanceToField * 0.74),
                prefixIcon: tags.isNotEmpty
                    ? SingleChildScrollView(
                        controller: sc,
                        scrollDirection: Axis.horizontal,
                        child: Row(
                            children: tags.map((String tag) {
                          return Container(
                            decoration: BoxDecoration(
                              borderRadius: const BorderRadius.all(
                                Radius.circular(20.0),
                              ),
                              color: primaryColor,
                            ),
                            margin: const EdgeInsets.only(right: 10.0),
                            padding: const EdgeInsets.symmetric(
                                horizontal: 10.0, vertical: 4.0),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                InkWell(
                                  child: Text(
                                    tag,
                                    style: const TextStyle(color: Colors.white),
                                  ),
                                  onTap: () {},
                                ),
                                const SizedBox(width: 4.0),
                                InkWell(
                                  child: const Icon(
                                    Icons.cancel,
                                    size: 14.0,
                                    color: Color.fromARGB(255, 233, 233, 233),
                                  ),
                                  onTap: () {
                                    onTagDelete(tag);
                                  },
                                )
                              ],
                            ),
                          );
                        }).toList()),
                      )
                    : null,
              ),
              onChanged: onChanged,
              onSubmitted: onSubmitted,
            ),
          );
        });
      },
    ));
  }
}
