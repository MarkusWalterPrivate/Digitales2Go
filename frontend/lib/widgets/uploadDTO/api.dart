import 'dart:convert';
import 'package:frontend/core/detail_objects/company/company_detail_obj.dart';
import 'package:frontend/core/detail_objects/trend/trend_detail_obj.dart';
import 'package:frontend/main.dart';
import 'package:http/http.dart' as http;
import '../../constants/url.dart';
import '../../core/detail_objects/project/project_detail_obj.dart';
import '../../core/detail_objects/technology/technology_detail_obj.dart';
import '../../core/element_objects/coreField.dart';
import '../../core/profile_objects/user.dart';
import '../../main.dart';

class InternalApi {
  InternalApi();

  static late Future<List<dynamic>> internalTechnologies;
  static late Future<List<dynamic>> internalTrends;
  static late Future<List<dynamic>> internalCompanies;
  static late Future<List<dynamic>> internalProjects;

  /// It takes a url, and a string, and returns a list of objects
  static void connect() {
    internalTechnologies =
        fetchInternalWork("${Constants.getBackendUrl()}/api/v2/tech/", 'technology');
    internalTrends =
        fetchInternalWork("${Constants.getBackendUrl()}/api/v2/trend/", 'trend');
    internalCompanies =
        fetchInternalWork("${Constants.getBackendUrl()}/api/v2/company/", 'company');
    internalProjects =
        fetchInternalWork("${Constants.getBackendUrl()}/api/v2/project/", 'project');
  }

  static String userToken = MyHomePage.userTokenGlobal;

  /// It takes a url and a detailWidget as parameters, makes a GET request to the
  /// url, and returns a list of objects of the type specified by the detailWidget
  ///
  /// Args:
  ///   url (String): The url to fetch the data from.
  ///   detailWidget (String): This is the name of the widget that will be used to
  /// display the data.
  ///
  /// Returns:
  ///   A list of dynamic objects.
  static Future<List<dynamic>> fetchInternalWork(
      String url, String detailWidget) async {
    final response = await http.get(
      Uri.parse(url),
      headers: {'Authorization': 'Bearer $userToken'},
    );
    if (response.statusCode == 200) {
      switch (detailWidget) {
        case 'technology':
          return (jsonDecode(utf8.decode(response.bodyBytes)) as List)
              .map((item) => TechnologyDetail.fromJson(item))
              .toList();
        case 'trend':
          return (jsonDecode(utf8.decode(response.bodyBytes)) as List)
              .map((item) => TrendDetail.fromJson(item))
              .toList();
        case 'company':
          return (jsonDecode(utf8.decode(response.bodyBytes)) as List)
              .map((item) => CompanyDetail.fromJson(item))
              .toList();
        case 'project':
          return (jsonDecode(utf8.decode(response.bodyBytes)) as List)
              .map((item) => ProjectDetail.fromJson(item))
  /// It takes a url and a bodyMapFuture as parameters. It then waits for the
  /// bodyMapFuture to complete and then it gets the token from the getTokenFORTEST
  /// function. It then makes a post request to the url with the bodyMap and the
  /// token. If the response status code is 201, it returns true, otherwise it
  /// returns false.
  ///
  /// Args:
  ///   url (String): The url of the API endpoint.
  ///   bodyMapFuture (Future<Map<String, dynamic>>): This is a Future<Map<String,
  /// dynamic>> object. This is the body of the request.
  ///
  /// Returns:
  ///   A Future<bool>
              .toList();

        default:
          return List.empty();
      }
    } else {
      throw Exception("Failed to load projects. Please reload the site");
    }
  }

  static Future<bool> postDTO(
      String url, Future<Map<String, dynamic>> bodyMapFuture, String userToken) async {
    Map<String, dynamic> bodyMap = await bodyMapFuture;
    final response = await http.post(
      Uri.parse(url),
      headers: {
        "Accept": "application/json",
        "content-type": "application/json",
        'Authorization': 'Bearer $userToken'
      },
      body: jsonEncode(bodyMap),
    );
    if (response.statusCode == 201) {
      print(response.statusCode);
      return true;
    } else {
      return false;
    }
  }

  /// It takes a list of headlines, and returns a list of ids
  ///
  /// Args:
  ///   headlineList (List<String>): A list of headlines that you want to get the id
  /// from.
  ///   internalListFuture (Future<List<dynamic>>): This is the future that returns
  /// the list of all the internal news.
  ///
  /// Returns:
  ///   A list of integers.
  static Future<List<int>> getIdFromHeadline(List<String> headlineList,
      Future<List<dynamic>> internalListFuture) async {
    if (headlineList.isEmpty) {
      return [];
    }
    List<int> idList = [];
    for (var headline in headlineList) {
      idList.add(int.parse(headline.replaceAll(RegExp(r'[^0-9]'), '')));
    }
    return idList;
  }

  
}
