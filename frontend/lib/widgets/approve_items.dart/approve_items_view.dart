import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:frontend/widgets/feed/feed_item.dart';
import 'package:http/http.dart' as http;

import '../../appBar.dart';
import '../../constants/url.dart';
import '../../core/atomic_objects/return_dto.dart';
import '../../utils/toast_util.dart';

class ApproveItemsView extends StatefulWidget {
  ApproveItemsView({Key? key, required this.userToken}) : super(key: key);
  String userToken;
  @override
  State<StatefulWidget> createState() => _approveItemsViewState();
}

class _approveItemsViewState extends State<ApproveItemsView> {
  late Future<FeedReturnDTO> futureUnapprovedItemsList;
  final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);
  final Color errorColor = Color(0xffffba1b1b);

  FToast toast = FToast();

  @override
  void initState() {
    super.initState();
    futureUnapprovedItemsList = fetchUnapprovedItems(widget.userToken);
    toast.init(context);
  }

  @override
  Widget build(BuildContext context) {
    final Color primaryColor = Theme.of(context).primaryColor;
    double width = MediaQuery.of(context).size.width;
    double height = MediaQuery.of(context).size.height - kToolbarHeight;
    return Scaffold(
      appBar: CustomAppBar(UniqueKey(), 'Item Anträge',
          userToken: '', isAllowed: true, isFeedPage: false),
      body: Container(
        height: height,
        width: width,
        alignment: Alignment.center,
        child: FutureBuilder<FeedReturnDTO>(
            future: futureUnapprovedItemsList,
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                List<FeedItem> unapprovedItems = snapshot.data!.feed;
                bool isApproved = false;
                return ListView.builder(
                    itemCount: unapprovedItems.length,
                    itemBuilder: (context, index) {
                      return Align(
                        alignment: Alignment.center,
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Container(
                            width: 500,
                            padding: const EdgeInsets.all(15),
                            decoration: BoxDecoration(
                              color: primaryColor.withOpacity(0.6),
                              borderRadius:
                                  const BorderRadius.all(Radius.circular(10)),
                            ),
                            child: Column(
                              children: [
                                FeedItemWidget(
                                  userToken: widget.userToken,
                                  feedItem: unapprovedItems.elementAt(index),
                                  /* id: unapprovedItems.elementAt(index).id,
                                    isBookmarked: false,
                                    coreField: unapprovedItems
                                        .elementAt(index)
                                        .coreField,
                                    userToken: unapprovedItems
                                        .elementAt(index)
                                        .userToken */
                                ),
                                ElevatedButton(
                                    style: ElevatedButton.styleFrom(
                                      onPrimary: primaryGreen,
                                      primary: Colors.white,
                                      fixedSize: const Size(180, 40),
                                    ),
                                    onPressed: () async {
                                      isApproved = await approveItem(
                                        widget.userToken,
                                        unapprovedItems.elementAt(index).id,
                                      );
                                      if (isApproved == true) {
                                        setState(() {
                                          unapprovedItems.removeAt(index);
                                        });
                                      }
                                    },
                                    child: const Text('Genehmigen'))
                              ],
                            ),
                          ),
                        ),
                      );
                    });
              } else if (snapshot.hasError) {
                print(snapshot.error);
                return Text('could not load items');
              } else {
                return const CircularProgressIndicator();
              }
            }),
      ),
    );
  }

  Future<FeedReturnDTO> fetchUnapprovedItems(String userToken) async {
    final response = await http.post(
        Uri.parse('${Constants.getBackendUrl()}/api/v2/feed/unapproved'),
        headers: {
          'Authorization': 'Bearer $userToken',
          'Content-Type': 'application/json; charset=UTF-8'
        },
        body: jsonEncode(<String, dynamic>{
        "totalItems": 0,
        "itemsPerPage": 100,
        "page": 0,
        "sorting": "NOTSET"
      }),
    );

    if (response.statusCode == 200) {
      return FeedReturnDTO.fromJson(jsonDecode(utf8.decode(response.bodyBytes)));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      print('StatusCode: ${response.statusCode}');
      throw Exception('Failed to load items');
    }
  }

  Future<bool> approveItem(String userToken, int itemId) async {
    final response = await http.post(
        Uri.parse('${Constants.getBackendUrl()}/api/v2/approve/$itemId'),
        headers: {
          'Authorization': 'Bearer $userToken',
          'Accept': 'application/json; charset=UTF-8'
        });

    if (response.statusCode == 200) {
      DisplayToast.showToast(
          toast,
          ToastUtil(
            toastColor: primaryGreen,
            toastText: 'Genehmigt',
            icon: const Icon(Icons.check, color: Colors.white),
          ),
          ToastGravity.TOP);
      return Future.value(true);
    } else {
      print('Could not approve item');
      DisplayToast.showToast(
          toast,
          ToastUtil(
            toastColor: errorColor,
            toastText: 'Genehmigen Fehlgeschlagen',
            icon: const Icon(Icons.error, color: Colors.white),
          ),
          ToastGravity.TOP);
      return Future.value(false);
    }
  }
}
