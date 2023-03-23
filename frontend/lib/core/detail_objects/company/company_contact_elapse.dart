import 'package:flutter/material.dart';
import 'package:expansion_tile_card/expansion_tile_card.dart';
import 'package:frontend/core/detail_objects/company/company_detail_obj.dart';
import 'package:frontend/utils/dummy_data.dart';

import '../../atomic_objects/contact.dart';
import '../../atomic_objects/externalItems.dart';
import '../../atomic_objects/item.dart';
import '../../element_objects/coreField.dart';

///A contact elapse showing all necessary info related to the "company" category.
///
///It requires a object of the type "CompanyDetail"
class CompanyContactElapse extends StatefulWidget {
  CompanyContactElapse({Key? key, required this.company}) : super(key: key);
  final GlobalKey<ExpansionTileCardState> cardA = GlobalKey();
  CompanyDetail company;
  @override
  State<CompanyContactElapse> createState() => _companyContactElapseState();
}

///The state of the contact elapse for the type "CompanyDetail"
class _companyContactElapseState extends State<CompanyContactElapse> {
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
                  for (var contact in checkListWithContacts(widget
                      .company.companyOptional?.companyOptionalLists?.contacts))
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

                  const Text("\nProjekte in dieser App \n",
                      style: TextStyle(fontWeight: FontWeight.bold)),
                  for (var link in checkListWithInternalItems(
                      widget.company.internalProjects))
                    Text("• ${link.coreField.headline}\n"),
                  const Divider(
                    color: Colors.black,
                    height: 0,
                    thickness: 1,
                    indent: 10,
                    endIndent: 10,
                  ),
                  //TODO: Artikel müssen anklickbar sein
                  const Text("\nExterne Projekte \n",
                      style: TextStyle(fontWeight: FontWeight.bold)),
                  for (var link in checkListWithExternalItems(
                      widget.company.companyOptional?.externalItems))
                    Text("• $link\n")
                ],
              ),
            )),
      ],
    );
  }

  List<CoreField> checkListOfCoreFields(List<CoreField>? internalProjects) {
    if (internalProjects == null) {
      return List.empty();
    } else {
      return internalProjects;
    }
  }

  List<String> checkListWithStrings(List<String>? externalProjects) {
    if (externalProjects == null) {
      return List.empty();
    } else {
      return externalProjects;
    }
  }

  List<Contact> checkListWithContacts(List<Contact>? contacts) {
    if (contacts == null) {
      return List.empty();
    } else {
      return contacts;
    }
  }

  List<Item> checkListWithInternalItems(List<Item>? internalItems) {
    if (internalItems == null) {
      return List.empty();
    } else {
      return internalItems;
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
}
