import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/core/atomic_objects/contact.dart';
import 'package:frontend/core/atomic_objects/externalItems.dart';
import 'package:frontend/widgets/uploadDTO/dynamicForms/dynamicFormContact.dart';
import 'package:frontend/widgets/uploadDTO/dynamicForms/dynamicFormExternalItem.dart';
import 'package:frontend/widgets/uploadDTO/dynamicForms/dynamicFormOneTextField.dart';
import 'package:frontend/widgets/uploadDTO/uploadFormLayout.dart';
import 'package:textfield_tags/textfield_tags.dart';

import '../../../core/enums/enum_util.dart';
import '../../../core/enums/industry.dart';
import '../api.dart';

class CompanyCreationDTO extends StatefulWidget {
  CompanyCreationDTO({Key? key, required this.userToken}) : super(key: key);
  String userToken;
  @override
  State<StatefulWidget> createState() => _CompanyCreationDTO();
}

class _CompanyCreationDTO extends State<CompanyCreationDTO> {
  final scakey = GlobalKey<_CompanyCreationDTO>();

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

  //company required
  TextEditingController description = TextEditingController();
  TextEditingController useCases = TextEditingController();
  List<String> teamSizeEnum = ["TINY", "SMALL", "MEDIUM", "LARGE"];
  String chosenTeamSize = " ";
  TextEditingController year = TextEditingController();
  TextEditingController reference = TextEditingController();
  TextEditingController website = TextEditingController();
  TextEditingController foundationYear = TextEditingController();

  //company optional
  //external item
  var chosenTypeExternalItems = <TextEditingController>[];
  var textExternalItems = <TextEditingController>[];
  var cardsExternalItems = <Card>[];
  List<ExternalItems> externalItems = [];

  List<String> targetMarket = [
    "DE",
    "EU",
    "US",
    "NAMEERIKA",
    "CHINA",
    "AFRICA",
    "SEA",
    "GLOBAL"
  ];
  List<String> chosenTargetMarket = [];
  List<String> alignment = ["B2B", "B2C", "B2B2C", "B2E", "B2G"];
  List<String> chosenAlignment = [];

  var partnersInput = <TextEditingController>[];
  var cardsPartner = <Card>[];
  List<String> partner = [];

  var investorInput = <TextEditingController>[];
  var cardsInvestors = <Card>[];
  List<String> investor = [];

  //companyOptionalFields
  TextEditingController country = TextEditingController();
  TextEditingController city = TextEditingController();
  List<String> readiness = [
    "THEORY",
    "DEVELOPMENT",
    "READY",
    "COMMERCIAL",
    "DATED"
  ];
  late String chosenReadiness = "bjjlk";

  //numberOfCustomer
  TextEditingController valueNumberOfCustomer = TextEditingController();
  TextEditingController yearNumberOfCustomer = TextEditingController();
  TextEditingController referenceNumberOfCustomer = TextEditingController();

  //Revenue
  TextEditingController valueRevenue = TextEditingController();
  TextEditingController yearRevenue = TextEditingController();
  TextEditingController referenceRevenue = TextEditingController();

  //Profit
  TextEditingController valueProfit = TextEditingController();
  TextEditingController yearProfit = TextEditingController();
  TextEditingController referenceProfit = TextEditingController();

  late Future<List<dynamic>> internalTechnologies =
      InternalApi.internalTechnologies;
  List<String> chosenInternalTechnologies = [];

  late Future<List<dynamic>> internalProjects = InternalApi.internalProjects;
  List<String> chosenInternalProjects = [];

  late Future<List<dynamic>> internalTrends = InternalApi.internalTrends;
  List<String> chosenInternalTrends = [];

  late Future<List<dynamic>> internalCompanies = InternalApi.internalCompanies;
  List<String> chosenInternalCompanies = [];

  //contact dynamic
  var nameContact = <TextEditingController>[];
  var emailContact = <TextEditingController>[];
  var telephoneContact = <TextEditingController>[];
  var organisationContact = <TextEditingController>[];
  var cardsContact = <Card>[];
  List<Contact> contact = [];

  @override
  void initState() {
    super.initState();
    industries = IndustryString.getIndustryString();
    industries.sort((a, b) => a.toString().compareTo(b.toString()));
  }

  /// It creates a form with a lot of different fields
  ///
  /// Args:
  ///   context (BuildContext): The BuildContext of the parent that contains the
  /// Form.
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

        ///company required
        DTOTextFieldExpanded(controller: description, label: "Beschreibung *"),
        DTOTextFieldExpanded(controller: useCases, label: "Anwendungsfall *"),
        TextLabel.addTextLabel("Team *"),
        DropDownMenu(
            dropDownList: teamSizeEnum,
            onChanged: (String? newValue) {
              setState(
                () {
                  chosenTeamSize = newValue!;
                },
              );
            },
            label: 'Größe des Teams *'),
        DTOTextField(
            controller: year,
            label: "Jahr *",
            keyBoardType: TextInputType.datetime),
        DTOTextField(controller: reference, label: "Referenzen *"),
        FixedPadding.addPadding(),

        DTOTextField(controller: website, label: "Webseite *"),
        DTOTextField(
            controller: foundationYear,
            label: "Gründungsjahr *",
            keyBoardType: TextInputType.datetime),

        ///optional
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

        FixedPadding.addPadding(),

        MultiDropDown(
            optionList: targetMarket,
            onChanged: (newValue) {
              setState(
                () {
                  chosenTargetMarket = newValue;
                },
              );
            },
            label: 'Wähle Zielmärkte aus',
            chosenElements: chosenTargetMarket),

        MultiDropDown(
            optionList: targetMarket,
            chosenElements: chosenAlignment,
            onChanged: (newValue) {
              setState(
                () {
                  chosenAlignment = newValue;
                },
              );
            },
            label: 'Wähle Ausrichtung aus'),

        ///Partner
        TextLabel.addTextLabel("Partner"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              partner = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormOneTextField(
                      textField: partnersInput,
                      texFieldLabel: "Partner",
                      title: "neue Partner",
                      cards: cardsPartner),
                ),
              );
            },
          ),
        ),

        ///Partner
        TextLabel.addTextLabel("Investoren"),
        FixedPadding.addPaddingWidget(
          ElevatedButton(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const [Icon(Icons.add), Text('Hinzufügen')],
            ),
            onPressed: () async {
              investor = await Navigator.push(
                context,
                MaterialPageRoute(
                  maintainState: true,
                  builder: (context) => DynamicFormOneTextField(
                      textField: investorInput,
                      texFieldLabel: "Investor",
                      title: "neue Investoren",
                      cards: cardsPartner),
                ),
              );
            },
          ),
        ),

        //company optional
        TextLabel.addTextLabel("Standort"),
        DTOTextField(controller: country, label: "Land"),
        DTOTextField(controller: city, label: "Stadt"),
        DropDownMenu(
            dropDownList: readiness,
            onChanged: (String? newValue) => setState(
                  () {
                    chosenReadiness = newValue!;
                  },
                ),
            label: 'Wie einsatzbereit ist das Unternehmen? *'),

        TextLabel.addTextLabel("Kunden"),
        DTOTextField(
            controller: valueNumberOfCustomer, label: "Anzahl der Kunden"),
        DTOTextField(
            controller: yearNumberOfCustomer,
            label: "Jahr",
            keyBoardType: TextInputType.datetime),
        DTOTextField(
            controller: referenceNumberOfCustomer, label: "Referenzen"),

        TextLabel.addTextLabel("Umsatz"),
        DTOTextField(controller: valueRevenue, label: "Umsatz"),
        DTOTextField(controller: yearRevenue, label: "Jahr"),
        DTOTextField(controller: referenceRevenue, label: "Referenzen"),

        TextLabel.addTextLabel("Gewinn"),
        DTOTextField(controller: valueProfit, label: "Gewinn"),
        DTOTextField(controller: yearProfit, label: "Jahr"),
        DTOTextField(controller: referenceProfit, label: "Referenzen"),

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
              if (checkIfRequiredFilled()) {
                await InternalApi.postDTO(
                        "http://localhost:8080/api/v2/company/",
                        createBodyMap(),
                        widget.userToken)
                    .then((value) {
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
        'type': "COMPANY"
      },
      "detailedRating": {
        "degreeOfInnovation": degreeOfInnovation,
        "disruptionPotential": disruptionPotential,
        "useCases": useCase
      },
      "companyRequired": {
        "description": description.text,
        "useCases": useCases.text,
        "teamSize": {
          "teamSize": chosenTeamSize,
          "year": year.text,
          "reference": reference.text
        },
        "website": website.text,
        "foundationYear": foundationYear.text,
      },
      "companyOptional": {
        "externalProjects": [],
        "companyOptionalLists": {
          "contacts": [],
          "targetMarkets": chosenTargetMarket,
          "alignments": chosenAlignment,
          "partners": [],
          "investors": []
        },
        "companyOptionalFields": {
          "location": {"country": country.text, "city": city.text},
          "productReadiness": "COMMERCIAL",
          "numberOfCustomers": {
            "value": valueNumberOfCustomer.text,
            "year": yearNumberOfCustomer.text,
            "reference": referenceNumberOfCustomer.text
          },
          "revenue": {
            "value": valueRevenue.text,
            "year": yearRevenue.text,
            "reference": referenceRevenue.text
          },
          "profit": {
            "value": valueProfit.text,
            "year": yearProfit.text,
            "reference": referenceProfit.text
          }
        }
      },
      "internalProjects": internalProjectsID,
      "internalTrends": internalTrendsID,
      "internalTechnologies": internalTechnologiesID,
      "internalCompany": internalCompaniesID
    };
    var mapContact =
        mapBody["companyOptional"]["companyOptionalLists"]["contacts"];
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
    var mapExternalItem = mapBody["companyOptional"]["externalProjects"];
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
    var mapPartner =
        mapBody["companyOptional"]["companyOptionalLists"]["partners"];
    for (int i = 0; i < cardsPartner.length; i++) {
      String partner = partnersInput[i].text;
      if ((partner != "" || partner.isNotEmpty)) {
        mapPartner.add(partner);
      }
    }
    var mapInvestors =
        mapBody["companyOptional"]["companyOptionalLists"]["investors"];
    for (int i = 0; i < cardsInvestors.length; i++) {
      String investor = investorInput[i].text;
      if ((investor != "" || investor.isNotEmpty)) {
        mapInvestors.add(investor);
      }
    }

    print(mapBody);
    return mapBody;
  }

  bool checkIfRequiredFilled() {
    if (imageSource.text.isEmpty ||
        headline.text.isEmpty ||
        teaser.text.isEmpty ||
        chosenIndustry.isEmpty ||
        !tags!.hasTags ||
        degreeOfInnovation == 0 ||
        disruptionPotential == 0 ||
        useCase == 0 ||
        description.text.isEmpty ||
        useCases.text.isEmpty ||
        chosenTeamSize.isEmpty ||
        year.text.isEmpty ||
        reference.text.isEmpty ||
        website.text.isEmpty ||
        foundationYear.text.isEmpty) {
      return false;
    }
    return true;
  }
}
