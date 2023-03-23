import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:frontend/widgets/uploadDTO/api.dart';
import 'package:http/http.dart' as http;

import '../../constants/url.dart';

class UploadJSONfile {
  const UploadJSONfile();

  /// It sends a POST request to the server with the file as a parameter.
  ///
  /// Args:
  ///   file: The file to be uploaded.
  ///
  /// Returns:
  ///   A Future<bool>
  Future<bool> sendRequestWeb(file, context) async {
    final stream = http.ByteStream(file.readStream);
    print("file in requrst: $file");
    String? token = await InternalApi.userToken;
    var request = http.MultipartRequest(
        'POST', Uri.parse("${Constants.getBackendUrl()}/api/v2/fileUpload/"));
    request.headers['Authorization'] = 'Bearer $token';
    request.headers['Content-Type'] = 'application/json';

    request.files
        .add(http.MultipartFile('file', stream, file.size, filename: file.name));
    var response = await request.send();
    print("StatusCode: ${response.statusCode}");
    showResult(response.statusCode, context);
    return true;
  }

  /// It sends a POST request to the server with the file as a parameter.
  ///
  /// Args:
  ///   file: The file you want to upload.
  ///
  /// Returns:
  ///   A Future<bool>
  Future<bool> sendRequestMobile(file, context) async {
    String? token = await InternalApi.userToken;
    var request = http.MultipartRequest(
        'POST', Uri.parse("${Constants.getBackendUrl()}/api/v2/fileUpload/"));
    request.files.add(await http.MultipartFile.fromPath('json', file));
    request.headers['Authorization'] = 'Bearer $token';
    var response = await request.send();
    showResult(response.statusCode, context);
    return true;
  }

  /// It opens a file picker and allows the user to select a file.
  void getFileAndSendRequestMobile(context) async {
    FilePickerResult? result =
        await FilePicker.platform.pickFiles(type: FileType.any);
    if (result != null) {
      var file = result.files.single.path;
      sendRequestMobile(file, context);
    } else {}
  }

  /// It opens a file picker and allows the user to select a file.
  void getFileAndSendRequestWeb(context) async {
    FilePickerResult? result = await FilePicker.platform.pickFiles(
      type: FileType.custom,
      allowedExtensions: ['json'],
      withData: false,
      withReadStream: true,
    );
    if (result != null) {
      var file = result.files.single;
      print(file);
      sendRequestWeb(file, context);
    } else {}
  }

  /// If the status code is 200, show a snackbar saying the file was successfully
  /// uploaded. If the status code is anything else, show a snackbar saying an error
  /// has appeared
  ///
  /// Args:
  ///   statusCode: The status code of the response.
  void showResult(statusCode, context) {
    if (statusCode.toString() == "201") {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Datei wurde erfolgreich hochgeladen")));
    } else {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
          content: Text(
              "Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut")));
    }
  }
}
