import 'package:flutter/material.dart';
import 'package:expansion_tile_card/expansion_tile_card.dart';
import 'package:frontend/core/detail_objects/trend/trend_detail_obj.dart';
import 'package:frontend/utils/dummy_data.dart';

import '../../atomic_objects/contact.dart';

///A contact elapse showing all necessary info related to the "trend" category.
///
///It requires a object of the type "TrendDetail"
class TrendContactElapse extends StatefulWidget {
  TrendContactElapse({Key? key, required this.trend}) : super(key: key);
  final GlobalKey<ExpansionTileCardState> cardA = GlobalKey();
  TrendDetail trend;
  @override
  State<TrendContactElapse> createState() => _trendContactElapseState();
}

///The state of the contact elapse for the type "TrendDetail"
class _trendContactElapseState extends State<TrendContactElapse> {
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
                  for (var contact in widget.trend.trendOptional!.contacts)
                    Padding(
                        padding: const EdgeInsets.only(top: 10, bottom: 10),
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
                                  ),
                                  Row(
                                    children: [
                                      const Icon(Icons.business_outlined),
                                      const Padding(
                                          padding: EdgeInsets.only(left: 5)),
                                      Text(contact.organisation)
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

                  //TODO: Artikel müssen anklickbar sein
                  const Text(
                    "\nProjekte in KuglBlitz \n",
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                  for (var item in widget.trend.internalProjects)
                    Text("• ${item}\n"),
                  const Divider(
                    color: Colors.black,
                    height: 0,
                    thickness: 1,
                    indent: 10,
                    endIndent: 10,
                  ),
                  const Text("\nExterne Projekte \n",
                      style: TextStyle(fontWeight: FontWeight.bold)),
                  for (var link in widget.trend.trendOptional!.externalItems)
                    Text("• $link\n")
                ],
              ),
            )),
      ],
    );
  }
}
