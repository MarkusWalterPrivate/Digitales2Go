/// It's a stateful widget that creates a form for creating events
import 'dart:core';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/appBar.dart';
import 'package:frontend/core/atomic_objects/event.dart';
import 'package:frontend/widgets/uploadDTO/uploadFormLayout.dart';

class DynamicFormEvent extends StatefulWidget {
  var date = <TextEditingController>[];
  var dateInMs = <TextEditingController>[];
  var location = <TextEditingController>[];
  var description = <TextEditingController>[];
  var imageSource = <TextEditingController>[];
  var website = <TextEditingController>[];
  var cards = <Card>[];
  String title;

  DynamicFormEvent({
    Key? key,
    required this.date,
    required this.dateInMs,
    required this.location,
    required this.description,
    required this.imageSource,
    required this.website,
    required this.cards,
    required this.title,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _DynamicFormEvent();
}

class _DynamicFormEvent extends State<DynamicFormEvent> {
  /// It creates a new card with a date picker, a text field for the location, a
  /// text field for the description, a text field for the image source and a text
  /// field for the website
  ///
  /// Returns:
  ///   A Card with a Column as child. The Column has a Text and a DatePicker as
  /// children.
  Card createCard() {
    var dateController = TextEditingController();
    var dateInMsController = TextEditingController();
    var locationController = TextEditingController();
    var descriptionController = TextEditingController();
    var imageSourceController = TextEditingController();
    var websiteController = TextEditingController();

    widget.date.add(dateController);
    widget.dateInMs.add(dateInMsController);
    widget.location.add(locationController);
    widget.description.add(descriptionController);
    widget.imageSource.add(imageSourceController);
    widget.website.add(websiteController);

    return Card(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Text('Event: ${widget.cards.length + 1}'),
          DatePicker(
            dateInput: dateController,
            label: "Datum",
            onChanged: (dateInMS, date) {
              dateController.text = date!;
              dateInMsController.text = dateInMS.toString();
            },
          ),
          DTOTextField(controller: locationController, label: "Ort"),
          DTOTextField(
              controller: descriptionController, label: "Beschreibung"),
          DTOTextField(controller: imageSourceController, label: "Bildlink"),
          DTOTextField(controller: websiteController, label: "Webseite"),
        ],
      ),
    );
  }

  @override
  void initState() {
    super.initState();
    widget.cards.add(createCard());
  }

  /// _onDone() takes the text from the TextEditingControllers and puts them into a
  /// list of Event objects
  _onDone() {
    List<Event> entries = [];
    for (int i = 0; i < widget.cards.length; i++) {
      var dateInMs = widget.dateInMs[i].text;
      var location = widget.location[i].text;
      var description = widget.description[i].text;
      var imageSource = widget.imageSource[i].text;
      var website = widget.website[i].text;

      print(dateInMs);
      print(location);
      print(description);
      print(imageSource);
      print(website);

      entries.add(Event.forMap(
          date: int.tryParse(dateInMs),
          location: location,
          description: description,
          imageSource: imageSource,
          website: website));
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
