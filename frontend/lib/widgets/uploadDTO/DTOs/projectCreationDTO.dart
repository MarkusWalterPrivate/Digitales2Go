import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/widgets/uploadDTO/dynamicForms/dynamicFormEvent.dart';
import 'package:frontend/widgets/uploadDTO/uploadFormLayout.dart';
import 'package:textfield_tags/textfield_tags.dart';

import '../../../core/atomic_objects/contact.dart';
import '../../../core/atomic_objects/externalItems.dart';
import '../../../core/enums/enum_util.dart';
import '../../../core/enums/industry.dart';
import '../api.dart';
import '../dynamicForms/dynamicFormContact.dart';
import '../dynamicForms/dynamicFormExternalItem.dart';
import '../dynamicForms/dynamicFormOneTextField.dart';

class ProjectCreationDTO extends StatefulWidget {
  ProjectCreationDTO({Key? key, required this.userToken}) : super(key: key);
  String userToken;
  @override
  State<StatefulWidget> createState() => _ProjectCreationDTO();
}

class _ProjectCreationDTO extends State<ProjectCreationDTO> {
  //coreFields
  bool isIntern = false;
  TextEditingController source = TextEditingController();
  TextEditingController imageSource = TextEditingController();
  TextEditingController headline = TextEditingController();
  TextEditingController teaser = TextEditingController();
  late List<String> industries;
  String chosenIndustry = " ";
  TextfieldTagsController? tags = TextfieldTagsController();

  //detailed rating

  double degreeOfInnovation = 0;
  double disruptionPotential = 0;
  double useCase = 0;

  // required
  TextEditingController description = TextEditingController();
  List<String> readiness = [
    "DEVELOPMENT",
    "FINISHED",
  ];
  String chosenReadiness = " ";
  TextEditingController startRunTime = TextEditingController();
  int startRunTimeDate = 0;
  TextEditingController finishRunTime = TextEditingController();
  int finishRunTimeTimeDate = 0;
  bool useOnlyYear = false;

  //contact
  var nameContact = <TextEditingController>[];
  var emailContact = <TextEditingController>[];
  var telephoneContact = <TextEditingController>[];
  var organisationContact = <TextEditingController>[];
  var cardsContact = <Card>[];
  List<Contact> contact = [];

  // optional
//external item
  var chosenTypeExternalItems = <TextEditingController>[];
  var textExternalItems = <TextEditingController>[];
  var cardsExternalItems = <Card>[];
  List<ExternalItems> externalItems = [];

  //publication
  var publicationInput = <TextEditingController>[];
  var cardsPublication = <Card>[];
  List<String> publication = [];

  //fundingSources
  var fundingSourcesInput = <TextEditingController>[];
  var cardsFundingSources = <Card>[];
  List<String> fundingSources = [];

  //promoters
  var promotersInput = <TextEditingController>[];
  var cardsPromoters = <Card>[];
  List<String> promoters = [];

  //projectPartners
  var projectPartnersInput = <TextEditingController>[];
  var cardsProjectPartners = <Card>[];
  List<String> projectPartners = [];

  //usePartner
  var usePartnerInput = <TextEditingController>[];
  var cardsUsePartner = <Card>[];
  List<String> usePartner = [];

  //financing
  TextEditingController valueFinancing = TextEditingController();
  TextEditingController yearFinancing = TextEditingController();
  TextEditingController referenceFinancing = TextEditingController();

  TextEditingController website = TextEditingController();

  //events
  var dateEvent = <TextEditingController>[];
  var dateEventDateInMs = <TextEditingController>[];
  var locationEvent = <TextEditingController>[];
  var descriptionEvent = <TextEditingController>[];
  var imageSourceEvent = <TextEditingController>[];
  var websiteEvent = <TextEditingController>[];
  var cardsEvent = <Card>[];

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
    super.initState();
    industries = IndustryString.getIndustryString();
    industries.sort((a, b) => a.toString().compareTo(b.toString()));
  }

  /// It creates a form for the user to fill out
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
            label: 'Wähle eine Industrie aus *'),

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

        ///project required
        DTOTextFieldExpanded(controller: description, label: "Beschreibung *"),
        DropDownMenu(
            dropDownList: readiness,
            onChanged: (String? newValue) => setState(
                  () {
                    chosenReadiness = newValue!;
                  },
                ),
            label: 'Wie einsatzbereit ist das Projekt? *'),
        TextLabel.addTextLabel("Zeitraum *"),
        DatePicker(
          dateInput: startRunTime,
          label: "Beginn",
          onChanged: (dateInMS, date) {
            setState(
              () {
                startRunTime.text = date!;
                startRunTimeDate = dateInMS;
              },
            );
          },
        ),
        DatePicker(
          dateInput: finishRunTime,
          label: "Ende",
          onChanged: (dateInMS, date) {
            setState(
              () {
                finishRunTime.text = date!;
                finishRunTimeTimeDate = dateInMS;
              },
            );
          },
        ),
        TextLabel.addTextLabel("Nur Jahreszahl benutzen *"),
        RadioBox(
          optionOne: "Ja",
          optionTwo: "Nein",
          groupValue: useOnlyYear,
          valueOne: true,
          valueTwo: false,
          onChanged: (newValue) {
            setState(
              () {
                useOnlyYear = newValue!;
                print(useOnlyYear);
              },
            );
          },
        ),
        FixedPadding.addPadding(),

        /// contacts
        TextLabel.addTextLabel("Kontakt *"),

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

        FixedPadding.addPadding(),

        TextLabel.addTextLabel("Publikationen"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              publication = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormOneTextField(
                      textField: publicationInput,
                      texFieldLabel: "Investor",
                      title: "neue Investoren",
                      cards: cardsPublication),
                ),
              );
            },
          ),
        ),

        TextLabel.addTextLabel("Finanzierungsquellen"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              fundingSources = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormOneTextField(
                      textField: fundingSourcesInput,
                      texFieldLabel: "Finanzierungsquellen",
                      title: "neue Finanzierungsquellen",
                      cards: cardsFundingSources),
                ),
              );
            },
          ),
        ),

        TextLabel.addTextLabel("Projektträger"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              promoters = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormOneTextField(
                      textField: promotersInput,
                      texFieldLabel: "Projektträger",
                      title: "neue Projektträger",
                      cards: cardsFundingSources),
                ),
              );
            },
          ),
        ),

        TextLabel.addTextLabel("Projektpartner"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              projectPartners = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormOneTextField(
                      textField: projectPartnersInput,
                      texFieldLabel: "Projektpartner",
                      title: "neue Projektpartner",
                      cards: cardsProjectPartners),
                ),
              );
            },
          ),
        ),

        TextLabel.addTextLabel("Einsatzpartner"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              usePartner = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormOneTextField(
                      textField: usePartnerInput,
                      texFieldLabel: "Einsatzpartner",
                      title: "neue Einsatzpartner",
                      cards: cardsUsePartner),
                ),
              );
            },
          ),
        ),

        TextLabel.addTextLabel("Finanzierung"),
        DTOTextField(
            controller: valueFinancing,
            label: "Betrag",
            keyBoardType: TextInputType.number),
        DTOTextField(
            controller: yearFinancing,
            label: "Jahr",
            keyBoardType: TextInputType.number),
        DTOTextField(controller: referenceFinancing, label: "Referenzen"),

        FixedPadding.addPadding(),
        DTOTextField(controller: website, label: "Webseite"),

        TextLabel.addTextLabel("Events"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              cardsEvent = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormEvent(
                      date: dateEvent,
                      dateInMs: dateEventDateInMs,
                      location: locationEvent,
                      description: descriptionEvent,
                      imageSource: imageSourceEvent,
                      website: websiteEvent,
                      cards: cardsEvent,
                      title: "neues Event"),
                ),
              );
            },
          ),
        ),

        ///industriesWithUse
        FixedPadding.addPadding(),
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
              print(createBodyMap());
              if (checkIfRequiredFilled() && checkIfContactFilled()) {
                await InternalApi.postDTO(
                        "http://localhost:8080/api/v2/project/",
                        createBodyMap(),
                        widget.userToken)
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

  /// It creates a map of the data that is entered in the form
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
        'type': "PROJECT"
      },
      "projectRequired": {
        "description": description.text,
        "useCases": chosenReadiness,
        "runtime": {
          "start": startRunTimeDate,
          "finished": finishRunTimeTimeDate,
          "useOnlyYear": useOnlyYear,
        },
        "contacts": [],
      },
      "detailedRating": {
        "degreeOfInnovation": degreeOfInnovation,
        "disruptionPotential": disruptionPotential,
        "useCases": useCase
      },
      "projectOptional": {
        "externalProjects": [],
        "publications": [],
        "projectRelations": {
          "fundingSources": [],
          "promoters": [],
          "projectPartners": [],
          "usePartners": []
        },
        "financing": {
          "value": valueFinancing.text,
          "year": yearFinancing.text,
          "reference": referenceFinancing.text
        },
        "website": website.text,
      },
      "internalProjects": internalProjectsID,
      "internalTrends": internalTrendsID,
      "internalTechnologies": internalTechnologiesID,
      "internalCompany": internalCompaniesID,
      "events": []
    };
    var mapContact = mapBody["projectRequired"]["contacts"];
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
    var mapExternalItem = mapBody["projectOptional"]["externalProjects"];
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

    var mapPublication = mapBody["projectOptional"]["publications"];
    for (int i = 0; i < cardsPublication.length; i++) {
      String publication = publicationInput[i].text;
      if ((publication != "" || publication.isNotEmpty)) {
        mapPublication.add(publication);
      }
    }
    var mapFundingSources =
        mapBody["projectOptional"]["projectRelations"]["fundingSources"];
    for (int i = 0; i < cardsFundingSources.length; i++) {
      String fundingSource = fundingSourcesInput[i].text;
      if ((fundingSource != "" || fundingSource.isNotEmpty)) {
        mapFundingSources.add(fundingSource);
      }
    }
    var mapPromoters =
        mapBody["projectOptional"]["projectRelations"]["promoters"];
    for (int i = 0; i < cardsPromoters.length; i++) {
      String promoters = promotersInput[i].text;
      if ((promoters != "" || promoters.isNotEmpty)) {
        mapPromoters.add(promoters);
      }
    }
    var mapProjectPartners =
        mapBody["projectOptional"]["projectRelations"]["projectPartners"];
    for (int i = 0; i < cardsProjectPartners.length; i++) {
      String projectPartners = projectPartnersInput[i].text;
      if ((projectPartners != "" || projectPartners.isNotEmpty)) {
        mapProjectPartners.add(projectPartners);
      }
    }
    var mapUsePartners =
        mapBody["projectOptional"]["projectRelations"]["usePartners"];
    for (int i = 0; i < cardsUsePartner.length; i++) {
      String usePartners = usePartnerInput[i].text;
      if ((usePartners != "" || usePartners.isNotEmpty)) {
        mapUsePartners.add(usePartners);
      }
    }
    var mapEvent = mapBody["events"];
    for (int i = 0; i < cardsEvent.length; i++) {
      Map<String, String> event = {};
      String date = dateEventDateInMs[i].text;
      String location = locationEvent[i].text;
      String description = descriptionEvent[i].text;
      String imageSource = imageSourceEvent[i].text;
      String website = websiteEvent[i].text;
      if ((date != "" || date.isNotEmpty) &&
          (location != "" || location.isNotEmpty) &&
          (description != "" || description.isNotEmpty)) {
        event["date"] = date;
        event["location"] = location;
        event["description"] = description;
        event["imageSource"] = imageSource;
        event["website"] = website;
      }
      if (event.isNotEmpty) {
        mapEvent.add(event);
      }
    }
    print("requestBody: $mapBody");
    return mapBody;
  }

  bool checkIfRequiredFilled() {
    print(checkIfContactFilled());
    if (imageSource.text.isEmpty ||
        headline.text.isEmpty ||
        teaser.text.isEmpty ||
        chosenIndustry.isEmpty ||
        !tags!.hasTags ||
        degreeOfInnovation == 0 ||
        disruptionPotential == 0 ||
        useCase == 0 ||
        description.text.isEmpty ||
        chosenReadiness.isEmpty ||
        startRunTimeDate == 0 ||
        finishRunTimeTimeDate == 0) {
      return false;
    }
    return true;
  }

  bool checkIfContactFilled() {
    for (var value in contact) {
      if (value.name.isNotEmpty &&
          value.telephone.isNotEmpty &&
          value.email.isNotEmpty &&
          value.organisation.isNotEmpty) {
        return true;
      }
    }
    return false;
  }
}
