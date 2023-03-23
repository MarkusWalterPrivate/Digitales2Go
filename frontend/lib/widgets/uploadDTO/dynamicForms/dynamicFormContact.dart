
import 'package:flutter/material.dart';
import 'package:frontend/appBar.dart';
import 'package:frontend/core/atomic_objects/contact.dart';
import 'package:frontend/widgets/uploadDTO/uploadFormLayout.dart';

class DynamicFormContact extends StatefulWidget {
  var name = <TextEditingController>[];
  var email = <TextEditingController>[];
  var telephone = <TextEditingController>[];
  var organisation = <TextEditingController>[];
  var cards = <Card>[];
  String title;

  DynamicFormContact({
    Key? key,
    required this.name,
    required this.email,
    required this.telephone,
    required this.organisation,
    required this.cards,
    required this.title,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _DynamicFormContact();
}

class _DynamicFormContact extends State<DynamicFormContact> {
  /// It creates a new card with a text field for each of the four contact details
  ///
  /// Returns:
  ///   A Card with a Column with a Text and 4 DTOTextFields
  Card createCard() {
    var nameController = TextEditingController();
    var emailController = TextEditingController();
    var telephoneController = TextEditingController();
    var organisationController = TextEditingController();

    widget.name.add(nameController);
    widget.email.add(emailController);
    widget.telephone.add(telephoneController);
    widget.organisation.add(organisationController);

    return Card(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Text('Kontakt ${widget.cards.length + 1}'),
          DTOTextField(controller: nameController, label: "Name"),
          DTOTextField(controller: emailController, label: "E-Mail"),
          DTOTextField(controller: telephoneController, label: "Telefon"),
          DTOTextField(controller: organisationController, label: "Organisation"),
        ],
      ),
    );
  }

  @override
  void initState() {
    super.initState();
    widget.cards.add(createCard());
  }

  /// It creates a list of contacts and then passes it back to the main page.
  _onDone() {
    List<Contact> entries = [];
    for (int i = 0; i < widget.cards.length; i++) {
      var name = widget.name[i].text;
      var email = widget.email[i].text;
      var telephone = widget.telephone[i].text;
      var organisation = widget.organisation[i].text;

      entries.add(Contact.forMap(
          name: name,
          email: email,
          telephone: telephone,
          organisation: organisation));
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
