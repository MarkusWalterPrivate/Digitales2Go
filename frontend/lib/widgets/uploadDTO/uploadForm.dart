import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:frontend/widgets/uploadDTO/api.dart';
import 'package:frontend/widgets/uploadDTO/DTOs/technologyCreationDTO.dart';
import 'package:frontend/widgets/uploadDTO/DTOs/trendCreationDTO.dart';
import 'package:frontend/widgets/uploadDTO/uploadFormLayout.dart';
import 'package:frontend/widgets/uploadDTO/uploadJson.dart';
import 'package:http/http.dart' as http;
import '../../appBar.dart';
import '../../core/profile_objects/user.dart';
import 'DTOs/companyCreationDTO.dart';
import 'DTOs/projectCreationDTO.dart';

class ShowUploadForm extends StatefulWidget {
  ShowUploadForm({Key? key, required this.userToken}) : super(key: key);
  String userToken;
  @override
  State<StatefulWidget> createState() => _showUploadForm();
}

class _showUploadForm extends State<ShowUploadForm> {
  UploadJSONfile jsoNfile = const UploadJSONfile();

  List<String> dtoTypeList = ["Technologie", "Trend", "Unternehmen", "Projekt"];
  ValueNotifier<String> chosenDTO = ValueNotifier<String>("");

  @override
  void initState() {
    super.initState();
    InternalApi.connect();
  }

  /// > The function `getFileAndSendRequestWeb()` is called when the user clicks the
  /// button. It opens a file picker, and when the user selects a file, it sends a
  /// POST request to the server with the file as the body
  ///
  /// Args:
  ///   context (BuildContext): The BuildContext of the widget that will be
  /// returned.
  ///
  /// Returns:
  ///   A Scaffold widget with a custom app bar and a button that allows the user to
  /// upload a JSON file.
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(UniqueKey(), "Upload", isAllowed: true, userToken: widget.userToken, isFeedPage: false),
      body: ListView(
        children: [
          Padding(
            padding: const EdgeInsets.only(top: 55),
            child: Align(
              alignment: Alignment.topCenter,
              child: OutlinedButton(
                style: ButtonStyle(
                  minimumSize: MaterialStateProperty.all(const Size(200, 50)),
                  textStyle: MaterialStateProperty.all(
                    const TextStyle(fontSize: 18),
                  ),
                ),
                onPressed: () async {
                  if (kIsWeb) {
                    jsoNfile.getFileAndSendRequestWeb(context);
                  } else {
                    jsoNfile.getFileAndSendRequestMobile(context);
                  }
                },
                child: const Text("JSON Datei hochladen"),
              ),
            ),
          ),
          Padding(
            padding:
                const EdgeInsets.only(top: 25, left: 30, right: 30, bottom: 55),
            child: Column(
              children: [
                DropDownMenu(
                  dropDownList: dtoTypeList,
                  label: "Wähle eine Objektvorlage aus",
                  onChanged: (value) {
                    setState(
                      () {
                        chosenDTO.value = value!;
                      },
                    );
                  },
                ),
                ValueListenableBuilder(
                    valueListenable: chosenDTO,
                    builder: (context, value, child) =>
                        choseDTOType(value.toString())),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget choseDTOType(String dtoType) {
    switch (dtoType) {
      case "Technologie":
        return TechCreationDTO(userToken: widget.userToken);
      case "Trend":
        return TrendCreationDTO(userToken: widget.userToken);
      case "Unternehmen":
        return CompanyCreationDTO(userToken: widget.userToken);
      case "Projekt":
        return ProjectCreationDTO(userToken: widget.userToken);

      default:
        return Column(
          children: const [],
        );
    }
  }
}
