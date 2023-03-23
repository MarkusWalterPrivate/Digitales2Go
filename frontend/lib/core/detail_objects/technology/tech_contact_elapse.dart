import 'package:flutter/material.dart';
import 'package:expansion_tile_card/expansion_tile_card.dart';
import 'package:frontend/core/detail_objects/technology/technology_detail_obj.dart';
import 'package:frontend/core/element_objects/coreField.dart';
import 'package:frontend/utils/dummy_data.dart';

import '../../atomic_objects/contact.dart';
import '../../atomic_objects/externalItems.dart';
import '../../atomic_objects/item.dart';

///A contact elapse showing all necessary info related to the "technology" category.
///
///It requires a object of the type "TechnologyDetail"
class TechContactElapse extends StatefulWidget {
  TechContactElapse({Key? key, required this.technology}) : super(key: key);
  final GlobalKey<ExpansionTileCardState> cardA = GlobalKey();
  TechnologyDetail technology;
  @override
  State<TechContactElapse> createState() => _techContactElapseState();
}

///The state of the contact elapse for the type "TechnologyDetail"
class _techContactElapseState extends State<TechContactElapse> {
  @override
  Widget build(BuildContext context) {
    return ExpansionTileCard(
      initialPadding: EdgeInsets.only(bottom: 5),
      title: const Text(
        "Kontaktinformationen",
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
      baseColor: Theme.of(context).colorScheme.primaryContainer,
      expandedColor: Theme.of(context).colorScheme.secondaryContainer,
      key: widget.cardA,
      leading: const CircleAvatar(
        child: Icon(
          Icons.group,
        ),
      ),
      subtitle: const Text(
        "Kontakt und weitere Projekte",
        style: TextStyle(fontSize: 12),
      ),
      children: <Widget>[
        const Divider(
          thickness: 1.0,
          height: 1.0,
        ),
        Align(
            alignment: Alignment.topLeft,
            child: Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 16.0,
                vertical: 8.0,
              ),
              // ignore: prefer_interpolation_to_compose_strings

              //All info aligned in a column. Only shown if contact elapse is opened.
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  for (var contact in checkListWithContacts(
                      widget.technology.techOptional!.contacts))
                    Padding(
                        padding: EdgeInsets.only(top: 10, bottom: 10),
                        child: Container(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    contact.name,
                                    style: const TextStyle(
                                        fontSize: 15,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  Text(contact.organisation,
                                      style: const TextStyle(
                                          fontSize: 13,
                                          fontWeight: FontWeight.bold,
                                          color: Colors.grey))
                                ],
                              ),
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Row(
                                    children: [
                                      const Icon(Icons.mail_outline),
                                      const Padding(
                                          padding: EdgeInsets.only(left: 5)),
                                      Text(contact.email)
                                    ],
                                  ),
                                  Row(
                                    children: [
                                      const Icon(Icons.phone),
                                      const Padding(
                                          padding: EdgeInsets.only(left: 5)),
                                      Text(contact.telephone)
                                    ],
                                  )
                                ],
                              )
                            ],
                          ),
                        )),

                  const Divider(
                    color: Colors.black,
                    height: 0,
                    thickness: 1,
                    indent: 10,
                    endIndent: 10,
                  ),

                  const Text("\nProjekte des Fraunhofer IAO \n",
                      style: TextStyle(fontWeight: FontWeight.bold)),
                  for (var link in widget.technology.techRequired.projectsIAO)
                    Text("• $link\n"),
                  const Divider(
                    color: Colors.black,
                    height: 0,
                    thickness: 1,
                    indent: 10,
                    endIndent: 10,
                  ),
                  //TODO: Artikel müssen anklickbar sein
                  const Text(
                    "\nProjekte in dieser App \n",
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                  for (var item in checkListWithInternalItems(
                      widget.technology.internalProjects))
                    Text("• ${item.coreField.headline}\n"),
                  const Divider(
                    color: Colors.black,
                    height: 0,
                    thickness: 1,
                    indent: 10,
                    endIndent: 10,
                  ),
                  const Text("\nExterne Projekte \n",
                      style: TextStyle(fontWeight: FontWeight.bold)),
                  for (var link in checkListWithExternalItems(
                      widget.technology.techOptional!.externalItems))
                    Text("• $link\n")
                ],
              ),
            )),
      ],
    );
  }

  List<Contact> checkListWithContacts(List<Contact>? contacts) {
    if (contacts == null) {
      return List.empty();
    } else {
      return contacts;
    }
  }

  List<CoreField> checkInternalProjects(List<CoreField>? internalProjects) {
    if (internalProjects == null) {
      return List.empty();
    } else {
      return internalProjects;
    }
  }

  List<ExternalItems> checkListWithExternalItems(
      List<ExternalItems>? externalItems) {
    if (externalItems == null) {
      return List.empty();
    } else {
      return externalItems;
    }
  }

  List<Item> checkListWithInternalItems(List<Item>? internalItems) {
    if (internalItems == null) {
      return List.empty();
    } else {
      return internalItems;
    }
  }
}
