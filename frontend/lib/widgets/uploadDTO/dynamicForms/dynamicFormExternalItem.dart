import 'package:flutter/material.dart';
import 'package:frontend/appBar.dart';
import 'package:frontend/core/atomic_objects/contact.dart';
import 'package:frontend/widgets/uploadDTO/uploadFormLayout.dart';

import '../../../core/atomic_objects/externalItems.dart';

class DynamicFormExternalItem extends StatefulWidget {
  var chosenType = <TextEditingController>[];
  var text = <TextEditingController>[];
  var cards = <Card>[];
  String title;

  DynamicFormExternalItem({
    Key? key,
    required this.chosenType,
    required this.text,
    required this.cards,
    required this.title,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _DynamicFormExternalItem();
}

class _DynamicFormExternalItem extends State<DynamicFormExternalItem> {
  /// It creates a card with a dropdown menu and a text field
  ///
  /// Returns:
  ///   A card with a dropdown menu and a textfield.
  Card createCard() {
    List<String> type = ["Company", "Trend", "Technology", "Projects"];
    var text = TextEditingController();
    var chosenType = TextEditingController();
    widget.text.add(text);
    widget.chosenType.add(chosenType);

    return Card(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Text('Externes Element ${widget.cards.length + 1}'),
          DropDownMenu(
              dropDownList: type,
              onChanged: (String? newValue) {
                  chosenType.text = newValue!;
              },
              label: 'Wähle Typ aus'),
          DTOTextFieldExpanded(controller: text, label: "Weitere Beschreibung"),
        ],
      ),
    );
  }

  @override
  void initState() {
    super.initState();
    widget.cards.add(createCard());
  }

  /// _onDone() is a function that takes the text and type from the text fields and
  /// dropdown menus and puts them into a list of ExternalItems objects
  _onDone() {
    print(widget.chosenType.length);
    List<ExternalItems> entries = [];
    for (int i = 0; i < widget.cards.length; i++) {
      var text = widget.text[i].text;
      var chosenType = widget.chosenType[i].text;
      entries.add(ExternalItems.forMap(text: text, type: chosenType));
    }
    Navigator.pop(context, entries);
  }

  /// It returns a Scaffold with a CustomAppBar, a Column with an Expanded widget
  /// containing a Column with an Expanded widget containing a ListView.builder and
  /// a Padding widget containing an ElevatedButton
  ///
  /// Args:
  ///   context (BuildContext): The BuildContext of the parent that contains the
  /// widget.
  ///
  /// Returns:
  ///   A Scaffold with a CustomAppBar, a Column with an Expanded Column with an
  /// Expanded ListView.builder and a Padding with an ElevatedButton.
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: CustomAppBar(UniqueKey(), widget.title, userToken: '', isFeedPage: false),
        body: Padding(
          padding:
              const EdgeInsets.only(top: 25, left: 30, right: 30, bottom: 55),
          child: Column(
            children: <Widget>[
              Expanded(
                child: Column(
                  children: <Widget>[
                    Expanded(
                      child: ListView.builder(
                        shrinkWrap: true,
                        itemCount: widget.cards.length,
                        itemBuilder: (BuildContext context, int index) {
                          return widget.cards[index];
                        },
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: ElevatedButton(
                        style: ButtonStyle(
                            minimumSize:
                                MaterialStateProperty.all(const Size(150, 50))),
                        child: Row(
                          mainAxisSize: MainAxisSize.min,
                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                          children: const [
                            Icon(Icons.add),
                            Text('Neue erstellen')
                          ],
                        ),
                        onPressed: () =>
                            setState(() => widget.cards.add(createCard())),
                      ),
                    )
                  ],
                ),
              ),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
            onPressed: _onDone, child: const Icon(Icons.done)));
  }
}
