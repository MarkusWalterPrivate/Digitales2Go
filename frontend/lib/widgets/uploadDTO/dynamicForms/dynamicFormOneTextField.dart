import 'package:flutter/material.dart';
import 'package:frontend/appBar.dart';
import 'package:frontend/core/atomic_objects/contact.dart';
import 'package:frontend/widgets/uploadDTO/uploadFormLayout.dart';

class DynamicFormOneTextField extends StatefulWidget {
  var textField = <TextEditingController>[];
  String title;
  String texFieldLabel;
  var cards = <Card>[];

  DynamicFormOneTextField({
    Key? key,
    required this.textField,
    required this.texFieldLabel,
    required this.title,
    required this.cards,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _DynamicFormOneTextField();
}

class _DynamicFormOneTextField extends State<DynamicFormOneTextField> {
  /// `createCard` creates a new `Card` with a `TextField` and adds the `TextField`
  /// to a list of `TextFields` in the parent widget
  ///
  /// Returns:
  ///   A card with a text field.
  Card createCard() {
    var textField = TextEditingController();

    widget.textField.add(textField);

    return Card(
      child: Column(mainAxisSize: MainAxisSize.min, children: <Widget>[
        Text('${widget.texFieldLabel}: ${widget.cards.length + 1}'),
        DTOTextField(controller: textField, label: "Name"),
      ]),
    );
  }

  @override
  void initState() {
    super.initState();
    widget.cards.add(createCard());
  }

  /// _onDone() is a function that is called when the user presses the "Done"
  /// button. It creates a list of strings, and adds the text from each text field
  /// to the list. Then, it pops the current page off the stack, and passes the list
  /// of strings to the previous page
  _onDone() {
    List<String> entries = [];
    for (int i = 0; i < widget.cards.length; i++) {
      var textField = widget.textField[i].text;
      entries.add(textField);
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
