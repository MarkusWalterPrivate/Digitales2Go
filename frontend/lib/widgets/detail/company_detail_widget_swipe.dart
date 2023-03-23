import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:frontend/widgets/misc/commentBox.dart';
import 'package:frontend/widgets/swipe/swipe_bar.dart';
import 'package:intl/intl.dart';
import 'package:like_button/like_button.dart';
import '../../constants/url.dart';
import '../../core/detail_objects/company/company_contact_elapse.dart';
import '../../core/detail_objects/company/company_detail_obj.dart';
import 'package:share_plus/share_plus.dart';
import 'package:http/http.dart' as http;

import '../../core/detail_objects/company/scroll_to_hide_widget.dart';
import '../feed/feed_item.dart';
import '../swipe/swipecards/swipe_cards.dart';
import 'global_detail.dart';

/// CompanyDetailView class
class CompanyDetailViewSwipe extends StatefulWidget {
  CompanyDetailViewSwipe(
      {Key? key,
      this.companyDetail,
      required this.feedItem,
      required this.id,
      required this.token,
      required this.matchEngine,
      required this.isSharedItem})
      : super(key: key);
  CompanyDetail? companyDetail;
  FeedItem feedItem;
  int id;
  String token;
  bool isSharedItem;
  MatchEngine matchEngine;
  @override
  State<CompanyDetailViewSwipe> createState() => _CompanyDetailViewSwipeState();
}

class _CompanyDetailViewSwipeState extends State<CompanyDetailViewSwipe> {
  late ScrollController controller;
  bool isButtonLabelVisible = true;
  bool isReadMore = false;
  bool isTeamSizeInfoClicked = false;
  bool isCustomerInfoClicked = false;
  bool isRevenueInfoClicked = false;
  bool isProfitInfoClicked = false;

  late Future<CompanyDetail> futureCompanyDetail;
  @override
  void initState() {
    super.initState();
    controller = ScrollController();
    futureCompanyDetail =
        fetchCompanyDetail(widget.token, widget.id, widget.isSharedItem);
  }

  @override
  void dispose() {
    controller.dispose();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // main colors
    final Color backgroundColor = Theme.of(context).backgroundColor;
    final Color primaryColor = Theme.of(context).primaryColor;
    // final Color primaryColor = Color(0xff1c3f52);
    final Color primaryContainer =
        Theme.of(context).colorScheme.primaryContainer;
    final Color secondaryContainer =
        Theme.of(context).colorScheme.secondaryContainer;
    // get screenSize
    double height = MediaQuery.of(context).size.height -
        MediaQuery.of(context).padding.bottom;
    double width = MediaQuery.of(context).size.width;

    return FutureBuilder<CompanyDetail>(
        future: futureCompanyDetail,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            // backend data
            CompanyDetail? companyDetail = snapshot.data;
            // formatted dates
            DateTime lastUpdated = DateTime.fromMillisecondsSinceEpoch(
                companyDetail!.coreField.lastUpdated,
                isUtc: true);
            String formattedLastUpdated =
                DateFormat('dd.MM.yyyy').format(lastUpdated);
            DateTime creationDate = DateTime.fromMillisecondsSinceEpoch(
                companyDetail.coreField.creationDate,
                isUtc: true);
            DateTime year = DateTime.fromMillisecondsSinceEpoch(
                companyDetail.companyRequired.teamSize.year,
                isUtc: true);
            String teamSizeYear = DateFormat('yyyy').format(year);
            String formattedCreationDate =
                DateFormat('dd.MM.yyyy').format(creationDate);
            return SizedBox(
              height: height,
              child: Scaffold(
                body: CustomScrollView(
                  controller: controller,
                  slivers: [
                    //  animated appBar
                    ScrollTopBarWidget(coreField: companyDetail.coreField),

                    // section for 'teaser'
                    TeaserWidget(teaserField: companyDetail.coreField.teaser),

                    // section for description
                    DescriptionWidget(
                        descriptionMarkdown:
                            companyDetail.companyRequired.description,
                        descriptionField:
                            companyDetail.companyRequired.description,
                        isReadMore: false),

                    // section for quick information about company
                    SliverToBoxAdapter(
                      child: Padding(
                        padding: const EdgeInsets.all(10.0),
                        child: IntrinsicHeight(
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            children: [
                              Column(children: [
                                Text(
                                    companyDetail.companyRequired.foundationYear
                                        .toString(),
                                    style: TextStyle(
                                        color: primaryColor,
                                        fontSize: 14,
                                        fontWeight: FontWeight.bold)),
                                Text('Gründungsjahr',
                                    style: TextStyle(
                                        color: Colors.grey[400], fontSize: 10))
                              ]),
                              const VerticalDivider(
                                thickness: 1,
                              ),
                              Column(children: [
                                Text(
                                    '${companyDetail.companyOptional!.companyOptionalFields!.revenue!.value.toString()}  ',
                                    style: TextStyle(
                                        color: primaryColor,
                                        fontSize: 14,
                                        fontWeight: FontWeight.bold)),
                                Text('Umsatz',
                                    style: TextStyle(
                                        color: Colors.grey[400], fontSize: 10))
                              ]),
                              const VerticalDivider(
                                thickness: 1,
                              ),
                              Column(children: [
                                Text(
                                    '${companyDetail.companyOptional!.companyOptionalFields!.profit!.value.toString()} ',
                                    style: TextStyle(
                                        color: primaryColor,
                                        fontSize: 14,
                                        fontWeight: FontWeight.bold)),
                                Text('Gewinn',
                                    style: TextStyle(
                                        color: Colors.grey[400], fontSize: 10))
                              ]),
                            ],
                          ),
                        ),
                      ),
                    ),

                    // section for tags
                    TagWidget(coreField: companyDetail.coreField),

                    // section for all information about company, displayed in table
                    SliverToBoxAdapter(
                        child: Padding(
                      padding: const EdgeInsets.all(15),
                      child: Column(
                        children: [
                          Padding(
                              padding: const EdgeInsets.only(bottom: 5),
                              child: Align(
                                  alignment: Alignment.topLeft,
                                  child: Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Table(
                                        columnWidths: const {
                                          0: FractionColumnWidth(0.25),
                                          1: FractionColumnWidth(0.05),
                                          2: FractionColumnWidth(0.7)
                                        },
                                        defaultVerticalAlignment:
                                            TableCellVerticalAlignment.top,
                                        children: <TableRow>[
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Gründungsjahr:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Text(
                                                    companyDetail
                                                        .companyRequired
                                                        .foundationYear
                                                        .toString(),
                                                    style: const TextStyle(
                                                      fontSize: 12,
                                                    ),
                                                  ),
                                                ))
                                          ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Größe:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Row(
                                                    children: [
                                                      if (companyDetail
                                                              .companyRequired
                                                              .teamSize
                                                              .teamSize ==
                                                          'Tiny')
                                                        const Text("XS",
                                                            style: TextStyle(
                                                                fontSize: 15,
                                                                fontWeight:
                                                                    FontWeight
                                                                        .w900))
                                                      else
                                                        const Text("XS",
                                                            style: TextStyle(
                                                                fontSize: 12)),
                                                      const Spacer(),
                                                      if (companyDetail
                                                              .companyRequired
                                                              .teamSize
                                                              .teamSize ==
                                                          'Small')
                                                        const Text("S",
                                                            style: TextStyle(
                                                                fontSize: 15,
                                                                fontWeight:
                                                                    FontWeight
                                                                        .w900))
                                                      else
                                                        const Text("S",
                                                            style: TextStyle(
                                                                fontSize: 12)),
                                                      const Spacer(),
                                                      if (companyDetail
                                                              .companyRequired
                                                              .teamSize
                                                              .teamSize ==
                                                          'Medium')
                                                        const Text("M",
                                                            style: TextStyle(
                                                                fontSize: 15,
                                                                fontWeight:
                                                                    FontWeight
                                                                        .w900))
                                                      else
                                                        const Text("M",
                                                            style: TextStyle(
                                                                fontSize: 12)),
                                                      const Spacer(),
                                                      if (companyDetail
                                                              .companyRequired
                                                              .teamSize
                                                              .teamSize ==
                                                          'Large')
                                                        Text("L",
                                                            style: TextStyle(
                                                                fontSize: 15,
                                                                fontWeight:
                                                                    FontWeight
                                                                        .w900,
                                                                color:
                                                                    primaryColor))
                                                      else
                                                        const Text("L",
                                                            style: TextStyle(
                                                                fontSize: 12)),
                                                      const Spacer(),
                                                      IconButton(
                                                        padding:
                                                            EdgeInsets.zero,
                                                        constraints:
                                                            const BoxConstraints(),
                                                        icon: const Icon(
                                                            Icons.info_outline),
                                                        color: primaryColor,
                                                        tooltip: 'More info',
                                                        iconSize: 20,
                                                        onPressed: () {
                                                          setState(() {
                                                            isTeamSizeInfoClicked =
                                                                !isTeamSizeInfoClicked;
                                                          });
                                                        },
                                                      ),
                                                      const SizedBox(width: 15)
                                                    ],
                                                  ),
                                                ))
                                          ]),
                                          if (isTeamSizeInfoClicked)
                                            TableRow(children: <Widget>[
                                              const Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding: EdgeInsets.only(
                                                        bottom: 7),
                                                    child: Text(
                                                      '',
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight:
                                                              FontWeight.bold),
                                                    ),
                                                  )),
                                              const Align(),
                                              Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 7),
                                                    child: Text(
                                                        "Jahr: $teamSizeYear",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                  ))
                                            ]),
                                          if (isTeamSizeInfoClicked)
                                            TableRow(children: <Widget>[
                                              const Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding: EdgeInsets.only(
                                                        bottom: 7),
                                                    child: Text(
                                                      '',
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight:
                                                              FontWeight.bold),
                                                    ),
                                                  )),
                                              const Align(),
                                              Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 7),
                                                    child: Text(
                                                        "Referenz: ${companyDetail.companyRequired.teamSize.reference}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                  )),
                                            ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Website:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Text(
                                                      companyDetail
                                                          .companyRequired
                                                          .website,
                                                      style: const TextStyle(
                                                        fontSize: 12,
                                                      )),
                                                )),
                                          ]),
                                          const TableRow(children: <Widget>[
                                            Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Projekte:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text('234',
                                                      style: TextStyle(
                                                        fontSize: 12,
                                                      )),
                                                )),
                                          ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Usecases:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Text(
                                                      companyDetail
                                                          .companyRequired
                                                          .useCases,
                                                      style: const TextStyle(
                                                        fontSize: 12,
                                                      )),
                                                )),
                                          ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Zielmärkte:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Column(
                                                      crossAxisAlignment:
                                                          CrossAxisAlignment
                                                              .start,
                                                      children: [
                                                        for (var targetMarket
                                                            in checkListWithStrings(
                                                                companyDetail
                                                                    .companyOptional!
                                                                    .companyOptionalLists!
                                                                    .targetMarkets))
                                                          Text(targetMarket,
                                                              style:
                                                                  const TextStyle(
                                                                      fontSize:
                                                                          12))
                                                      ]),
                                                )),
                                          ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Ausrichtung:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Column(
                                                      crossAxisAlignment:
                                                          CrossAxisAlignment
                                                              .start,
                                                      children: [
                                                        for (var alignment
                                                            in checkListWithStrings(
                                                                companyDetail
                                                                    .companyOptional!
                                                                    .companyOptionalLists!
                                                                    .alignments))
                                                          Text(alignment,
                                                              style:
                                                                  const TextStyle(
                                                                      fontSize:
                                                                          12))
                                                      ]),
                                                )),
                                          ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Partner:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Column(
                                                      crossAxisAlignment:
                                                          CrossAxisAlignment
                                                              .start,
                                                      children: [
                                                        for (var partner
                                                            in checkListWithStrings(
                                                                companyDetail
                                                                    .companyOptional!
                                                                    .companyOptionalLists!
                                                                    .partners))
                                                          Text(partner,
                                                              style:
                                                                  const TextStyle(
                                                                      fontSize:
                                                                          12))
                                                      ]),
                                                )),
                                          ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Investoren:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Column(
                                                      crossAxisAlignment:
                                                          CrossAxisAlignment
                                                              .start,
                                                      children: [
                                                        for (var investor
                                                            in checkListWithStrings(
                                                                companyDetail
                                                                    .companyOptional!
                                                                    .companyOptionalLists!
                                                                    .investors))
                                                          Text(investor,
                                                              style:
                                                                  const TextStyle(
                                                                      fontSize:
                                                                          12))
                                                      ]),
                                                )),
                                          ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Standort:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Text(
                                                      "Stadt: ${companyDetail.companyOptional?.companyOptionalFields?.location?.city}\nLand: ${companyDetail.companyOptional?.companyOptionalFields?.location?.country}",
                                                      style: const TextStyle(
                                                        fontSize: 12,
                                                      )),
                                                )),
                                          ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Phase:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Text(
                                                      companyDetail
                                                              .companyOptional
                                                              ?.companyOptionalFields
                                                              ?.productReadiness ??
                                                          '',
                                                      style: const TextStyle(
                                                        fontSize: 12,
                                                      )),
                                                )),
                                          ]),
                                          TableRow(
                                            children: <Widget>[
                                              const Align(
                                                alignment: Alignment.topLeft,
                                                child: Text(
                                                  'Kunden:',
                                                  style: TextStyle(
                                                      fontSize: 12,
                                                      fontWeight:
                                                          FontWeight.bold),
                                                ),
                                              ),
                                              const Align(),
                                              Align(
                                                alignment: Alignment.centerLeft,
                                                child: Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          bottom: 7),
                                                  child: Row(
                                                    children: [
                                                      const Text(
                                                        "9999",
                                                        style: TextStyle(
                                                          fontSize: 12,
                                                        ),
                                                      ),
                                                      const Spacer(),
                                                      IconButton(
                                                        padding:
                                                            EdgeInsets.zero,
                                                        constraints:
                                                            const BoxConstraints(),
                                                        icon: const Icon(
                                                            Icons.info_outline),
                                                        color: primaryColor,
                                                        tooltip: 'More info',
                                                        iconSize: 20,
                                                        onPressed: () {
                                                          setState(() {
                                                            isCustomerInfoClicked =
                                                                !isCustomerInfoClicked;
                                                          });
                                                        },
                                                      ),
                                                      const SizedBox(width: 15)
                                                    ],
                                                  ),
                                                ),
                                              ),
                                            ],
                                          ),
                                          if (isCustomerInfoClicked)
                                            TableRow(children: <Widget>[
                                              const Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding: EdgeInsets.only(
                                                        bottom: 7),
                                                    child: Text(
                                                      '',
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight:
                                                              FontWeight.bold),
                                                    ),
                                                  )),
                                              const Align(),
                                              Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 7),
                                                    child: Text(
                                                        "Jahr: ${companyDetail.companyOptional?.companyOptionalFields?.customer?.year}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                  ))
                                            ]),
                                          if (isCustomerInfoClicked)
                                            TableRow(children: <Widget>[
                                              const Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding: EdgeInsets.only(
                                                        bottom: 7),
                                                    child: Text(
                                                      '',
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight:
                                                              FontWeight.bold),
                                                    ),
                                                  )),
                                              const Align(),
                                              Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 7),
                                                    child: Text(
                                                        "Referenz: ${companyDetail.companyOptional?.companyOptionalFields?.customer?.reference}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                  )),
                                            ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Umsatz:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                              alignment: Alignment.centerLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 7),
                                                child: Row(
                                                  children: [
                                                    Text(
                                                        "${companyDetail.companyOptional?.companyOptionalFields?.revenue?.value}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                    const Spacer(),
                                                    IconButton(
                                                      padding: EdgeInsets.zero,
                                                      constraints:
                                                          const BoxConstraints(),
                                                      icon: const Icon(
                                                          Icons.info_outline),
                                                      color: primaryColor,
                                                      tooltip: 'More info',
                                                      iconSize: 20,
                                                      onPressed: () {
                                                        setState(() {
                                                          isRevenueInfoClicked =
                                                              !isRevenueInfoClicked;
                                                        });
                                                      },
                                                    ),
                                                    const SizedBox(width: 15)
                                                  ],
                                                ),
                                              ),
                                            ),
                                          ]),
                                          if (isRevenueInfoClicked)
                                            TableRow(children: <Widget>[
                                              const Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding: EdgeInsets.only(
                                                        bottom: 7),
                                                    child: Text(
                                                      '',
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight:
                                                              FontWeight.bold),
                                                    ),
                                                  )),
                                              const Align(),
                                              Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 7),
                                                    child: Text(
                                                        "Jahr: ${companyDetail.companyOptional?.companyOptionalFields?.revenue?.year}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                  ))
                                            ]),
                                          if (isRevenueInfoClicked)
                                            TableRow(children: <Widget>[
                                              const Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding: EdgeInsets.only(
                                                        bottom: 7),
                                                    child: Text(
                                                      '',
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight:
                                                              FontWeight.bold),
                                                    ),
                                                  )),
                                              const Align(),
                                              Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 7),
                                                    child: Text(
                                                        "Referenz: ${companyDetail.companyOptional?.companyOptionalFields?.revenue?.reference}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                  )),
                                            ]),
                                          TableRow(children: <Widget>[
                                            const Align(
                                                alignment: Alignment.topLeft,
                                                child: Padding(
                                                  padding: EdgeInsets.only(
                                                      bottom: 7),
                                                  child: Text(
                                                    'Gewinn:',
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight:
                                                            FontWeight.bold),
                                                  ),
                                                )),
                                            const Align(),
                                            Align(
                                              alignment: Alignment.centerLeft,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    bottom: 7),
                                                child: Row(
                                                  children: [
                                                    Text(
                                                        "${companyDetail.companyOptional?.companyOptionalFields?.profit?.value}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                    const Spacer(),
                                                    IconButton(
                                                      padding: EdgeInsets.zero,
                                                      constraints:
                                                          const BoxConstraints(),
                                                      icon: const Icon(
                                                          Icons.info_outline),
                                                      color: primaryColor,
                                                      tooltip: 'More info',
                                                      iconSize: 20,
                                                      onPressed: () {
                                                        setState(() {
                                                          isProfitInfoClicked =
                                                              !isProfitInfoClicked;
                                                        });
                                                      },
                                                    ),
                                                    const SizedBox(width: 15)
                                                  ],
                                                ),
                                              ),
                                            ),
                                          ]),
                                          if (isProfitInfoClicked)
                                            TableRow(children: <Widget>[
                                              const Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding: EdgeInsets.only(
                                                        bottom: 7),
                                                    child: Text(
                                                      '',
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight:
                                                              FontWeight.bold),
                                                    ),
                                                  )),
                                              const Align(),
                                              Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 7),
                                                    child: Text(
                                                        "Jahr: ${companyDetail.companyOptional?.companyOptionalFields?.profit?.year}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                  ))
                                            ]),
                                          if (isProfitInfoClicked)
                                            TableRow(children: <Widget>[
                                              const Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding: EdgeInsets.only(
                                                        bottom: 7),
                                                    child: Text(
                                                      '',
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight:
                                                              FontWeight.bold),
                                                    ),
                                                  )),
                                              const Align(),
                                              Align(
                                                  alignment:
                                                      Alignment.centerLeft,
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 7),
                                                    child: Text(
                                                        "Referenz: ${companyDetail.companyOptional?.companyOptionalFields?.profit?.reference}",
                                                        style: const TextStyle(
                                                          fontSize: 12,
                                                        )),
                                                  )),
                                            ]),
                                        ],
                                      ),
                                    ],
                                  ))),
                        ],
                      ),
                    )),

                    // contactElapse
                    SliverToBoxAdapter(
                      child: Padding(
                        padding: const EdgeInsets.only(
                            left: 15, right: 15, bottom: 15),
                        child: CompanyContactElapse(company: companyDetail),
                      ),
                    ),

                    // section for author creationDate and lastUpdated
                    SliverToBoxAdapter(
                      child: Padding(
                          padding: const EdgeInsets.only(
                            left: 15,
                            top: 5,
                            right: 15,
                            bottom: 5,
                          ),
                          child: Column(
                            children: [
                              Padding(
                                padding: const EdgeInsets.only(bottom: 5),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: [
                                    Text.rich(
                                      TextSpan(
                                        style: const TextStyle(
                                          fontSize: 14,
                                        ),
                                        children: [
                                          WidgetSpan(
                                            child: Icon(
                                              Icons.person,
                                              size: 16,
                                              color: primaryColor,
                                            ),
                                          ),
                                          TextSpan(
                                              text:
                                                  ' ${companyDetail.coreField.source} ',
                                              style: const TextStyle(
                                                  fontSize: 12)),
                                          WidgetSpan(
                                            child: Icon(
                                              Icons.date_range,
                                              size: 16,
                                              color: primaryColor,
                                            ),
                                          ),
                                          TextSpan(
                                              text: ' $formattedCreationDate ',
                                              style: const TextStyle(
                                                  fontSize: 12)),
                                          WidgetSpan(
                                            child: Icon(
                                              Icons.update,
                                              size: 16,
                                              color: primaryColor,
                                            ),
                                          ),
                                          TextSpan(
                                              text: ' $formattedLastUpdated',
                                              style: const TextStyle(
                                                  fontSize: 12)),
                                        ],
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          )),
                    ),
                  ],
                ),
                floatingActionButton: ShareScrollDownWidget(
                    coreField: widget.feedItem.coreField,
                    id: widget.id,
                    scrollController: controller),
                // bottomBar with buttons and commentsection
                bottomNavigationBar: SwipeBarWidget(
                    feedItem: widget.feedItem,
                    userToken: widget.token,
                    matchEngine: widget.matchEngine,
                    scrollController: controller,
                    width: width),
              ),
            );
          } else if (snapshot.hasError) {
            return Text('${snapshot.error}');
          }
          //By default, show a loading spinner.
          return const CircularProgressIndicator();
        });
  }

  void scrollUp() {
    const double start = 0;
    controller.jumpTo(start);
  }

  void scrollToCommentSection(double height) {
    controller.jumpTo(height + 100);
  }

  List<String> checkListWithStrings(List<String>? list) {
    if (list == null) {
      return List.empty();
    } else {
      return list;
    }
  }

  ///Fetch backend Data to show in CompanyDetail
  Future<CompanyDetail> fetchCompanyDetail(
      String? token, int id, bool isSharedItem) async {
    final http.Response response;
    if (isSharedItem == true) {
      response = await http.get(
          Uri.parse('${Constants.getBackendUrl()}/api/v2/shared/company/$id'),
          headers: {'Accept': 'application/json; charset=UTF-8'});
    } else {
      response = await http.get(
          Uri.parse('${Constants.getBackendUrl()}/api/v2/company/$id'),
          headers: {
            'Authorization': 'Bearer $token',
            'Accept': 'application/json; charset=UTF-8'
          });
    }

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return CompanyDetail.fromJson(jsonDecode(response.body));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load company');
    }
  }
}
