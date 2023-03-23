import 'package:flutter/material.dart';
import 'package:frontend/widgets/uploadDTO/api.dart';
import 'package:frontend/widgets/uploadDTO/uploadFormLayout.dart';
import 'package:textfield_tags/textfield_tags.dart';

import '../../../core/atomic_objects/contact.dart';
import '../../../core/atomic_objects/externalItems.dart';
import '../../../core/enums/enum_util.dart';
import '../../../core/enums/industry.dart';
import '../dynamicForms/dynamicFormContact.dart';
import '../dynamicForms/dynamicFormExternalItem.dart';
import '../dynamicForms/dynamicFormOneTextField.dart';

class TechCreationDTO extends StatefulWidget {
  TechCreationDTO({Key? key, required this.userToken}) : super(key: key);
  String userToken;
  @override
  State<StatefulWidget> createState() => _TechCreationDTO();
}

class _TechCreationDTO extends State<TechCreationDTO> {
  bool isIntern = false;
  TextEditingController source = TextEditingController();
  TextEditingController imageSource = TextEditingController();
  TextEditingController headline = TextEditingController();
  TextEditingController teaser = TextEditingController();
  late List<String> industries;

  String chosenIndustry = " ";

  TextfieldTagsController? tags;

  //detailed rating
  double degreeOfInnovation = 0;
  double disruptionPotential = 0;
  double useCase = 0;

  TextEditingController description = TextEditingController();
  TextEditingController useCases = TextEditingController();
  TextEditingController discussion = TextEditingController();
  TextfieldTagsController? iaoProjects;
  List<String> readiness = [
    "THEORY",
    "DEVELOPMENT",
    "READY",
    "COMMERCIAL",
    "DATED"
  ];
  String chosenReadiness = " ";

  //contact dynamic
  var nameContact = <TextEditingController>[];
  var emailContact = <TextEditingController>[];
  var telephoneContact = <TextEditingController>[];
  var organisationContact = <TextEditingController>[];
  var cardsContact = <Card>[];
  List<Contact> contact = [];

  //external items
  TextfieldTagsController? references;
  List<String> type = ["Company", "Trend", "Technology", "Projects"];
  String chosenType = "";
  var chosenTypeExternalItems = <TextEditingController>[];
  var textExternalItems = <TextEditingController>[];
  var cardsExternalItems = <Card>[];
  List<ExternalItems> externalItems = [];

  //referencen
  var referencesInput = <TextEditingController>[];
  var cardsRefernence = <Card>[];
  List<String> reference = [];

  TextEditingController industriesWithUseType = TextEditingController();

  late Future<List<dynamic>> internalTechnologies =
      InternalApi.internalTechnologies;
  List<String> chosenInternalTechnologies = [];

  late Future<List<dynamic>> internalProjects = InternalApi.internalProjects;
  List<String> chosenInternalProjects = [];

  late Future<List<dynamic>> internalTrends = InternalApi.internalTrends;
  List<String> chosenInternalTrends = [];

  late Future<List<dynamic>> internalCompanies = InternalApi.internalCompanies;
  List<String> chosenInternalCompanies = [];

  @override
  void initState() {
    tags = TextfieldTagsController();
    iaoProjects = TextfieldTagsController();
    references = TextfieldTagsController();
    industries = IndustryString.getIndustryString();
    industries.sort((a, b) => a.toString().compareTo(b.toString()));
    super.initState();
  }

  /// It creates a form with a lot of different fields and returns it
  ///
  /// Args:
  ///   context (BuildContext): The BuildContext of the widget that includes the
  /// form.
  ///
  /// Returns:
  ///   A Widget
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        /// coreFields
        TextLabel.addTextLabel("Erforderlich *"),
        TextLabel.addTextLabel("Intern"),
        RadioBox(
          optionOne: "Ja",
          optionTwo: "Nein",
          groupValue: isIntern,
          valueOne: true,
          valueTwo: false,
          onChanged: (newValue) {
            setState(
              () {
                isIntern = newValue!;
                print(isIntern);
              },
            );
          },
        ),
        DTOTextField(controller: source, label: 'Quelle *'),
        DTOTextField(controller: imageSource, label: 'Link zum Bild *'),

        DTOTextField(controller: headline, label: 'Überschrift *'),
        DTOTextFieldExpanded(controller: teaser, label: 'Zusammenfassung *'),
        DropDownMenu(
            dropDownList: industries,
            onChanged: (String? newValue) => setState(
                  () {
                    chosenIndustry = newValue!;
                  },
                ),
            label: 'Wähle eine Industrie aus*'),

        TagField(controller: tags, label: 'Stichwörter / Buzzwörter *'),

        ///rating
        RatingBarStar(
            onRatingUpdate: (double value) {
              setState(
                () {
                  degreeOfInnovation = value;
                },
              );
            },
            label: "Grad der Innovation *"),
        RatingBarStar(
            onRatingUpdate: (double value) {
              setState(
                () {
                  disruptionPotential = value;
                },
              );
            },
            label: "Bewertung des Störungspotenzials *"),
        RatingBarStar(
            onRatingUpdate: (double value) {
              setState(
                () {
                  useCase = value;
                },
              );
            },
            label: "Bewertung des Anwendungsfalls *"),

        DTOTextFieldExpanded(controller: description, label: "Beschreibung *"),
        DTOTextFieldExpanded(controller: useCases, label: "Anwendungsfall *"),
        DTOTextFieldExpanded(
            controller: discussion, label: "Vor- und Nachteile *"),
        TagField(
            controller: iaoProjects,
            label: 'Gebe stichwortartig IAO Projekte an *'),
        DropDownMenu(
            dropDownList: readiness,
            onChanged: (String? newValue) => setState(
                  () {
                    chosenReadiness = newValue!;
                  },
                ),
            label: 'Wie fortgeschritten ist die Technologie *'),

        ///techOptional
        /// referneces
        TextLabel.addTextLabel("Referenzen"),
        TagField(
            controller: references,
            label: 'Gebe stichwortartig die Referenzen ein'),
        TextLabel.addTextLabel("Partner"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              reference = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormOneTextField(
                      textField: referencesInput,
                      texFieldLabel: "Referenzen",
                      title: "neue Referenzen",
                      cards: cardsRefernence),
                ),
              );
            },
          ),
        ),

        /// externalItems
        TextLabel.addTextLabel("Externe Elemente"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              externalItems = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormExternalItem(
                      chosenType: chosenTypeExternalItems,
                      text: textExternalItems,
                      cards: cardsExternalItems,
                      title: "externe Elemente"),
                ),
              );
            },
          ),
        ),

        /// contacts
        TextLabel.addTextLabel("Kontakt"),

        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              contact = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormContact(
                      name: nameContact,
                      email: emailContact,
                      telephone: telephoneContact,
                      organisation: organisationContact,
                      cards: cardsContact,
                      title: "Neue Kontakte"),
                ),
              );
            },
          ),
        ),

        ///industriesWithUse
        MultiDropDownPostRequest(
            internalIdeas: internalTechnologies,
            onChanged: (List<String> values) {
              setState(
                () {
                  chosenInternalTechnologies = values;
                },
              );
            },
            chosenElements: chosenInternalTechnologies,
            label: 'Wähle verknüpfte interne Technologien aus'),
        MultiDropDownPostRequest(
            internalIdeas: internalTrends,
            chosenElements: chosenInternalTrends,
            onChanged: (List<String> values) {
              setState(
                () {
                  chosenInternalTrends = values;
                },
              );
            },
            label: 'Wähle verknüpfte interne Trends aus'),
        MultiDropDownPostRequest(
            internalIdeas: internalCompanies,
            chosenElements: chosenInternalCompanies,
            onChanged: (List<String> values) {
              setState(
                () {
                  chosenInternalCompanies = values;
                },
              );
            },
            label: 'Wähle verknüpfte interne Unternehmen aus'),
        MultiDropDownPostRequest(
            internalIdeas: internalProjects,
            chosenElements: chosenInternalProjects,
            onChanged: (List<String> values) {
              setState(
                () {
                  chosenInternalProjects = values;
                },
              );
            },
            label: 'Wähle verknüpfte interne Projekte aus'),
        SubmitButton(
            label: "Submit",
            onPressMethod: () async {
              if (checkIfRequiredFilled()) {
                await InternalApi.postDTO("http://localhost:8080/api/v2/tech/",
                        createBodyMap(), widget.userToken)
                    .then((value) {
                  print("calue : $value");
                  if (value == true) {
                    Navigator.pop(context);
                    ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                        backgroundColor: Colors.green,
                        content: Text("Objekt wurde erfolgreich erstellt")));
                  } else {
                    ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                        backgroundColor: Colors.red,
                        content: Text(
                            "Es ist ein Fehler aufgetreten. Füllen Sie alle Felder mit * aus und versuchen Sie es erneut")));
                  }
                });
              } else {
                ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                    backgroundColor: Colors.amber,
                    content:
                        Text("Fülle alle erforderlichen (*) Felder aus ")));
              }
            })
      ],
    );
  }

  /// It takes the data from the form and puts it into a map
  ///
  /// Returns:
  ///   A map of the body of the request.
  Future<Map<String, dynamic>> createBodyMap() async {
    List<int> internalProjectsID = await InternalApi.getIdFromHeadline(
        chosenInternalProjects, internalProjects);
    List<int> internalTrendsID = await InternalApi.getIdFromHeadline(
        chosenInternalTrends, internalTrends);
    List<int> internalTechnologiesID = await InternalApi.getIdFromHeadline(
        chosenInternalTechnologies, internalTechnologies);
    List<int> internalCompaniesID = await InternalApi.getIdFromHeadline(
        chosenInternalCompanies, internalCompanies);

    Map<String, dynamic> mapBody = {
      'coreField': {
        'intern': isIntern,
        'source': source.text,
        'imageSource': imageSource.text,
        'headline': headline.text,
        'teaser': teaser.text,
        'industry': EnumUtil.StringToEnumStringIndustry(chosenIndustry),
        'tags': tags!.getTags,
        'type': "TECHNOLOGY"
      },
      "techRequired": {
        "description": description.text,
        "useCases": useCases.text,
        "discussion": discussion.text,
        "readiness": chosenReadiness,
        "projectsIAO": iaoProjects!.getTags,
      },
      "detailedRating": {
        "degreeOfInnovation": degreeOfInnovation,
        "disruptionPotential": disruptionPotential,
        "useCases": useCase
      },
      "trendOptional": {
        "contacts": [],
        "references": [],
        "externalProjects": [],
        "industriesWithUse": industriesWithUseType.text,
        "internalProjects": internalProjectsID,
        "internalTrends": internalTrendsID,
        "internalTechnologies": internalTechnologiesID,
        "internalCompany": internalCompaniesID
      },
    };
    var mapContact = mapBody["trendOptional"]["contacts"];
    for (int i = 0; i < cardsContact.length; i++) {
      Map<String, String> contact = {};
      String name = nameContact[i].text;
      String email = emailContact[i].text;
      String telephone = telephoneContact[i].text;
      String organisation = organisationContact[i].text;
      if ((name != "" || name.isNotEmpty) &&
          (email != "" || email.isNotEmpty) &&
          (telephone != "" || telephone.isNotEmpty) &&
          (organisation != "" || organisation.isNotEmpty)) {
        contact["name"] = name;
        contact["email"] = email;
        contact["telephone"] = telephone;
        contact["organisation"] = organisation;
      }
      if (contact.isNotEmpty) {
        mapContact.add(contact);
      }
    }
    var mapExternalItem = mapBody["trendOptional"]["externalProjects"];
    for (int i = 0; i < cardsExternalItems.length; i++) {
      Map<String, String> externalItem = {};
      String chosenType = chosenTypeExternalItems[i].text;
      String text = textExternalItems[i].text;
      if ((chosenType != "" || chosenType.isNotEmpty) &&
          (text != "" || text.isNotEmpty)) {
        externalItem["type"] = chosenType;
        externalItem["text"] = text;
      }
      if (externalItem.isNotEmpty) {
        mapExternalItem.add(externalItem);
      }
    }
    var mapReference = mapBody["trendOptional"]["references"];
    for (int i = 0; i < cardsRefernence.length; i++) {
      String reference = referencesInput[i].text;
      if ((reference != "" || reference.isNotEmpty)) {
        mapReference.add(reference);
      }
    }
    print(mapBody);
    return mapBody;
  }

  /// If any of the required fields are empty, return false
  ///
  /// Returns:
  ///   A boolean value.
  bool checkIfRequiredFilled() {
    if (imageSource.text.isEmpty ||
        headline.text.isEmpty ||
        teaser.text.isEmpty ||
        description.text.isEmpty ||
        discussion.text.isEmpty ||
        readiness.isEmpty ||
        useCases.text.isEmpty ||
        chosenIndustry.isEmpty ||
        !tags!.hasTags) {
      return false;
    }
    return true;
  }
}
