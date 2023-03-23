import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:http/http.dart' as http;
import 'package:textfield_tags/textfield_tags.dart';

import '../../constants/url.dart';
import '../../core/enums/enum_util.dart';
import '../../core/profile_objects/user.dart';
import '../../utils/toast_util.dart';
import '../log-in_and_register/dropdown_menu.dart';
import '../log-in_and_register/tag_field.dart';
import '../log-in_and_register/text_field.dart';

class ProfileEditView extends StatefulWidget {
  ProfileEditView({Key? key, required this.userToken, required this.userId})
      : super(key: key);
  String userToken;
  int userId;
  @override
  State<ProfileEditView> createState() => _ProfileEditViewState();
}

class _ProfileEditViewState extends State<ProfileEditView> {
  final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);
  final Color errorColor = Color(0xffffba1b1b);
  final Color warnColor = Color(0xfffdb913);
  // textEditingController for input values from textFields
  TextEditingController firstNameTEC = TextEditingController();
  TextEditingController lastNameTEC = TextEditingController();
  TextEditingController emailTEC = TextEditingController();
  TextEditingController companyTEC = TextEditingController();
  TextEditingController jobTEC = TextEditingController();
  TextEditingController interestsTEC = TextEditingController();
  TextEditingController passwordTEC = TextEditingController();
  TextEditingController repeatPasswordTEC = TextEditingController();

  late Future<User> futureUser;

  // toast
  final toast = FToast();
  // for tag field
  late double distanceToField;
  late TextfieldTagsController tagInterestsController;
  String dropdownValue = "Branche auswählen...";
  final formKey = GlobalKey<FormState>();

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    distanceToField = MediaQuery.of(context).size.width;
  }

  @override
  void dispose() {
    tagInterestsController.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    futureUser = getUserByID(widget.userId, widget.userToken);
    toast.init(context);
    tagInterestsController = TextfieldTagsController();
  }

  @override
  Widget build(BuildContext context) {
    // get theme colors
    final Color primaryColor = Theme.of(context).primaryColor;
    // get screenSize
    double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
    return Container(
      height: height,
      width: width,
      decoration: BoxDecoration(
        // background
        gradient: LinearGradient(colors: [
          primaryColor.withOpacity(0.4),
          primaryColor.withOpacity(0.6),
          primaryColor.withOpacity(0.8),
          primaryColor.withOpacity(1),
        ], begin: Alignment.topCenter, end: Alignment.bottomCenter),
      ),
      child: FutureBuilder<User>(
        future: futureUser,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            User? user = snapshot.data;
            return Container(
              child: openProfileEditDialog(user!),
            );
          } else if (snapshot.hasError) {
            print(snapshot.error);
            return const Text('Es konnte kein Profil geladen werden');
          } else {
            return const CircularProgressIndicator();
          }
        },
      ),
    );
  }

  Widget openProfileEditDialog(User user) {
    return AlertDialog(
      title: Text(
        'Profil bearbeiten',
        style: TextStyle(color: primaryGreen, fontWeight: FontWeight.bold),
      ),
      content: SingleChildScrollView(
        scrollDirection: Axis.vertical,
        child: Form(
          autovalidateMode: AutovalidateMode.onUserInteraction,
          key: formKey,
          child: SizedBox(
            width: 500,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                buildEditableContainer(
                  TextFieldScheme(
                    icon: Icon(Icons.person, color: primaryGreen),
                    label: (user.firstName),
                    controller: firstNameTEC,
                    labelColor: Colors.black,
                    obscureText: false,
                    validator: (value) {
                      if (!RegExp(r'^[A-Z][a-z]*(-{0,1}[A-Z][a-z]*){0,1}$|^$')
                          .hasMatch(value!)) {
                        return 'Bitte geben sie einen validen Vornamen ein bestehend aus Buchstaben';
                      } else {
                        return null;
                      }
                    },
                  ),
                ),
                const SizedBox(height: 10),
                buildEditableContainer(
                  TextFieldScheme(
                    icon: Icon(Icons.person, color: primaryGreen),
                    label: (user.lastName),
                    controller: lastNameTEC,
                    labelColor: Colors.black,
                    obscureText: false,
                    validator: (value) {
                      if (!RegExp(r'^[A-Z][a-z]*$|^$').hasMatch(value!)) {
                        return "Bitte geben sie einen validen Namen ein bestehend aus Buchstaben";
                      } else {
                        return null;
                      }
                    },
                  ),
                ),
                const SizedBox(height: 10),

                /// TODO: Eeditable email. discuss with POs
                buildEditableContainer(
                  TextFieldScheme(
                    icon: Icon(Icons.business, color: primaryGreen),
                    label: hasString(user.company)
                        ? user.company!
                        : 'Firma hinzufügen',
                    controller: companyTEC,
                    labelColor: Colors.black,
                    obscureText: false,
                  ),
                ),
                const SizedBox(height: 10),
                buildEditableContainer(
                  TextFieldScheme(
                    icon: Icon(Icons.handyman_outlined, color: primaryGreen),
                    label: hasString(user.job)
                        ? user.job!
                        : 'Position / Tätigkeit hinzufügen',
                    controller: jobTEC,
                    labelColor: Colors.black,
                    obscureText: false,
                  ),
                ),
                const SizedBox(height: 10),
                buildEditableContainer(
                  DropdownScheme(
                    validator: null,
                    icon: Icon(
                      Icons.factory_outlined,
                      color: primaryGreen,
                    ),
                    label: hasString(user.industry)
                        ? user.industry!
                        : 'Keine Branche ausgewählt',
                    labelColor: Colors.black,
                    dropdownValue: dropdownValue,
                    onChanged: (String? newValue) => setState(
                      () {
                        dropdownValue = newValue!;
                      },
                    ),
                  ),
                ),
                const SizedBox(height: 10),
                buildEditableContainer(
                  TagFieldScheme(
                    initialTags: user.interests,
                    label: "Interessen",
                    labelColor: Colors.black,
                    icon: Icon(
                      Icons.interests,
                      color: primaryGreen,
                    ),
                    controller: tagInterestsController,
                    distanceToField: distanceToField,
                    validator: (String tag) {
                      if (tagInterestsController.getTags!.contains(tag)) {
                        return 'Du hast dieses Tag bereits benutzt';
                      }
                      return null;
                    }, 
                  ),
                )
              ],
            ),
          ),
        ),
      ),
      actions: [
        TextButton(
            style: ButtonStyle(
              overlayColor: MaterialStateProperty.all(primaryGreen),
            ),
            onPressed: () => Navigator.of(context).pop(),
            child: const Text(
              'Abbrechen',
              style: TextStyle(color: Colors.grey, fontSize: 14),
            )),
        TextButton(
          style: ButtonStyle(
              overlayColor: MaterialStateProperty.all(primaryGreen)),
          onPressed: () {
            if (formKey.currentState!.validate()) {
              updateUserByID(
                  User(
                      id: null,
                      firstName: hasString(firstNameTEC.text)
                          ? firstNameTEC.text
                          : user.firstName,
                      lastName: hasString(lastNameTEC.text)
                          ? lastNameTEC.text
                          : user.lastName,
                      email: user.email,
                      industry: hasString(dropdownValue)
                          ? dropdownValue
                          : user.industry,
                      company: hasString(companyTEC.text)
                          ? companyTEC.text
                          : user.company,
                      job: hasString(jobTEC.text) ? jobTEC.text : user.job,
                      interests: tagInterestsController.getTags),
                  widget.userToken);
              Navigator.of(context).pop();
            } else {
              DisplayToast.showToast(
                  toast,
                  ToastUtil(
                    toastColor: warnColor,
                    toastText: 'Es sind nicht alle Felder richtig ausgefüllt!',
                    icon:
                        const Icon(Icons.warning_outlined, color: Colors.white),
                  ),
                  ToastGravity.TOP);
            }
          },
          child: const Text('Bestätigen'),
        ),
      ],
    );
  }

  /// Get user by id from db
  /// Args:
  ///   userID (int): id of the user
  ///   token (String): the users jwt token
  ///
  /// Returns:
  ///   Future
  Future<User> getUserByID(int userID, String token) async {
    final response = await http
        .get(Uri.parse('${Constants.getBackendUrl()}/api/v2/user/$userID'), headers: {
      'Authorization': 'Bearer $token',
    });

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return User.fromJson(jsonDecode(utf8.decode(response.bodyBytes)));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load user');
    }
  }

  /// Updating user with a given id and his jwt token
  /// Args:
  ///   user (User): user with the updated parameters
  ///   token (String): jwt token of the user
  ///
  /// Returns:
  ///   Future<User>
  Future<User> updateUserByID(User user, String token) async {
    final response = await http.put(
        Uri.parse('${Constants.getBackendUrl()}/api/v2/user/${widget.userId}'),
        headers: {
          'Authorization': 'Bearer $token',
          'Content-Type': 'application/json; charset=UTF-8'
        },
        body: jsonEncode(<String, dynamic>{
          'firstName': user.firstName,
          'lastName': user.lastName,
          'email': user.email,
          'company': user.company,
          'job': user.job,
          'industry': EnumUtil.StringToEnumStringIndustry(user.industry),
          'interests': user.interests,
        }));

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      DisplayToast.showToast(
          toast,
          ToastUtil(
            toastColor: primaryGreen,
            toastText: 'Profil wurde geändert.',
            icon: const Icon(Icons.check, color: Colors.white),
          ),
          ToastGravity.TOP);
      return User.fromJson(jsonDecode(utf8.decode(response.bodyBytes)));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      DisplayToast.showToast(
          toast,
          ToastUtil(
            toastColor: errorColor,
            toastText: 'Profil bearbeiten Fehlgeschlagen!',
            icon: const Icon(Icons.error, color: Colors.white),
          ),
          ToastGravity.TOP);
      throw Exception('Failed to update user');
    }
  }

  /// Builds Container with the given child
  /// Args:
  ///   child (Widget): widget which should be placed in the Container.
  /// Returns:
  ///   (Widget) Container with a child
  Widget buildEditableContainer(Widget child) {
    final Color primaryColor = Color(0xffff00658f);
    return Container(
      padding: const EdgeInsets.all(10),
      decoration: BoxDecoration(
          color: primaryColor.withOpacity(0.6),
          border: Border.all(
            color: Colors.white,
          ),
          borderRadius: const BorderRadius.all(Radius.circular(5))),
      child: child,
    );
  }

  /// Checks whether the given String is '' or 'Not Set' or 'Branche auswählen'
  /// Args:
  ///   string (String?): the String which should be checked
  bool hasString(String? string) {
    if (string == '' ||
        string == 'Not Set' ||
        string == "Branche auswählen...") {
      return false;
    } else {
      return true;
    }
  }
}
