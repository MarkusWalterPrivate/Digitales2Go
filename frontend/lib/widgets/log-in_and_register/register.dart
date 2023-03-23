import 'dart:convert';

import 'package:email_validator/email_validator.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:frontend/core/enums/enum_util.dart';
import 'package:frontend/widgets/log-in_and_register/dropdown_menu.dart';
import 'package:frontend/widgets/log-in_and_register/log_in.dart';
import 'package:frontend/widgets/log-in_and_register/tag_field.dart';
import 'package:frontend/widgets/log-in_and_register/text_field.dart';
import 'package:http/http.dart' as http;
import 'package:frontend/core/profile_objects/user.dart';
import 'package:textfield_tags/textfield_tags.dart';

import 'package:url_launcher/url_launcher.dart';
import '../../constants/url.dart';
import '../../core/enums/industry.dart';
import '../../main.dart';
import '../../utils/toast_util.dart';

class RegisterView extends StatefulWidget {
  RegisterView({Key? key}) : super(key: key);
  @override
  State<RegisterView> createState() => _RegisterViewState();
}

class _RegisterViewState extends State<RegisterView> {
  // bool value for checkbox
  bool acceptTerms = false;
  bool showAcceptTermsHint = false;
  bool isPasswordObscure = true;
  String dropdownValuePostRequest = "Branche auswählen...";
  final formKey = GlobalKey<FormState>();

  // for tag field
  late double distanceToField;
  late TextfieldTagsController tagInterestsController;

  // textEditingController for input values from textFields
  TextEditingController firstNameTEC = TextEditingController();
  TextEditingController lastNameTEC = TextEditingController();
  TextEditingController emailTEC = TextEditingController();
  TextEditingController companyTEC = TextEditingController();
  TextEditingController jobTEC = TextEditingController();
  TextEditingController interestsTEC = TextEditingController();
  TextEditingController passwordTEC = TextEditingController();
  TextEditingController repeatPasswordTEC = TextEditingController();

  // bool wheter email is used or not
  bool isEmailAlreadyUsed = false;

  final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);
  final Color errorColor = Color(0xffffba1b1b);
  final Color warnColor = Color(0xfffdb913);

  // toast
  final toast = FToast();

  late List<String> tags;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    distanceToField = MediaQuery.of(context).size.width;
  }

  @override
  void dispose() {
    super.dispose();
    tagInterestsController.dispose();
  }

  @override
  void initState() {
    super.initState();
    toast.init(context);
    tagInterestsController = TextfieldTagsController();
  }

  @override
  Widget build(BuildContext context) {
    // get theme colors
    final Color primaryColor = Theme.of(context).primaryColor;
    // get screenSize
    double height = MediaQuery.of(context).size.height -
        MediaQuery.of(context).padding.bottom;
    double width = MediaQuery.of(context).size.width;

    return MaterialApp(
      home: MediaQuery(
        data: const MediaQueryData(),
        child: Scaffold(
          resizeToAvoidBottomInset: true,
          body: Center(
            child: Container(
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
              child: SingleChildScrollView(
                padding:
                    const EdgeInsets.symmetric(horizontal: 25, vertical: 120),
                child: Form(
                  autovalidateMode: AutovalidateMode.onUserInteraction,
                  key: formKey,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      // headline
                      Wrap(
                        alignment: WrapAlignment.center,
                        children: [
                          Container(
                            padding: const EdgeInsets.only(top: 22),
                            child: const Text(
                              'DigiTales2go',
                              style: TextStyle(
                                  color: Colors.white,
                                  fontSize: 35,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: 'SegeoePrint'),
                            ),
                          ),
                          Wrap(
                            children: [
                              Container(
                                padding:
                                    const EdgeInsets.only(top: 28, left: 10),
                                child: const Text(
                                  'by',
                                  style: TextStyle(
                                      color: Colors.white, fontSize: 25),
                                ),
                              ),
                              const Padding(
                                padding: EdgeInsets.only(left: 10.0),
                                child: Image(
                                  image: AssetImage(
                                      "assets/images/18_iao_rgb_white.png"),
                                  height: 100,
                                  width: 150,
                                ),
                              )
                            ],
                          ),
                        ],
                      ),
                      const SizedBox(height: 40),
                      Container(
                        width: 500,
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius:
                              const BorderRadius.all(Radius.circular(5)),
                          boxShadow: [
                            BoxShadow(
                                color: primaryColor,
                                blurRadius: 6,
                                offset: const Offset(0, 2))
                          ],
                        ),
                        child: Container(
                          width: 500,
                          decoration: BoxDecoration(
                            border: Border.all(color: primaryColor),
                            borderRadius:
                                const BorderRadius.all(Radius.circular(5)),
                            color: primaryColor,
                          ),
                          child: Padding(
                            padding: const EdgeInsets.only(
                              top: 15,
                              bottom: 15,
                              right: 15,
                              left: 7,
                            ),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.start,
                              children: [
                                const Icon(Icons.tips_and_updates,
                                    color: Colors.white, size: 22),
                                const SizedBox(width: 10),
                                Flexible(
                                  child: RichText(
                                    text: const TextSpan(
                                      text:
                                          'Mit einem Sternchen (*) markierte Felder sind Pflichtfelder und müssen ausgefüllt werden!',
                                      style: TextStyle(
                                        color: Colors.white,
                                        fontSize: 16,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: TextFieldScheme(
                          validator: (value) {
                            if (value!.isEmpty ||
                                !RegExp(r'^[A-Z][a-z]*(-{0,1}[A-Z][a-z]*){0,1}$')
                                    .hasMatch(value)) {
                              return 'Bitte geben Sie einen validen Vornamen ein bestehend aus Buchstaben';
                            } else {
                              return null;
                            }
                          },
                          controller: firstNameTEC,
                          label: 'Vorname*',
                          labelColor: Colors.white,
                          icon: Icon(
                            Icons.person,
                            color: primaryGreen,
                          ),
                          obscureText: false,
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: TextFieldScheme(
                          validator: (value) {
                            if (value!.isEmpty ||
                                !RegExp(r'^[A-Z][a-z]*$').hasMatch(value)) {
                              return "Bitte geben Sie einen validen Namen ein bestehend aus Buchstaben";
                            } else {
                              return null;
                            }
                          },
                          controller: lastNameTEC,
                          label: 'Nachname*',
                          labelColor: Colors.white,
                          icon: Icon(
                            Icons.person,
                            color: primaryGreen,
                          ),
                          obscureText: false,
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: TextFieldScheme(
                          validator: (value) {
                            if (value != null &&
                                !EmailValidator.validate(value)) {
                              return validationStringEmail();
                            } else {
                              return null;
                            }
                          },
                          controller: emailTEC,
                          label: 'E-Mail*',
                          labelColor: Colors.white,
                          icon: Icon(
                            Icons.email,
                            color: primaryGreen,
                          ),
                          textInputType: TextInputType.emailAddress,
                          obscureText: false,
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: TextFieldScheme(
                          validator: null,
                          controller: companyTEC,
                          label: 'Firma',
                          labelColor: Colors.white,
                          icon: Icon(
                            Icons.business,
                            color: primaryGreen,
                          ),
                          obscureText: false,
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: TextFieldScheme(
                          validator: null,
                          controller: jobTEC,
                          label: 'Position / Tätigkeit',
                          labelColor: Colors.white,
                          icon: Icon(
                            Icons.handyman_outlined,
                            color: primaryGreen,
                          ),
                          obscureText: false,
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: DropdownScheme(
                          validator: (value) {
                            if (value == null ||
                                value == 'Branche auswählen...') {
                              return "Bitte geben Sie eine Branche an";
                            } else {
                              return null;
                            }
                          },
                          icon: Icon(
                            Icons.factory_outlined,
                            color: primaryGreen,
                          ),
                          label: 'Branche*',
                          labelColor: Colors.white,
                          dropdownValue: dropdownValuePostRequest,
                          onChanged: (String? newValue) => setState(
                            () {
                              dropdownValuePostRequest = newValue!;
                            },
                          ),
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: TagFieldScheme(
                          label: "Interessen",
                          labelColor: Colors.white,
                          icon: Icon(
                            Icons.interests,
                            color: primaryGreen,
                          ),
                          controller: tagInterestsController,
                          initialTags: const [],
                          distanceToField: distanceToField,
                          validator: (String tag) {
                            if (tagInterestsController.getTags!.contains(tag)) {
                              return 'Du hast dieses Tag bereits benutzt';
                            } else if (!IndustryString.getIndustryString()
                                .contains(tag)) {
                              return 'Nur vorgeschlagene Tags werden akzeptiert';
                            }
                            return null;
                          },
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: TextFieldScheme(
                          validator: (value) {
                            if (value!.isEmpty || value.length < 5) {
                              return "Bitte geben Sie ein Passwort mit mindestens 6 Zeichen ein";
                            } else {
                              return null;
                            }
                          },
                          controller: passwordTEC,
                          label: 'Passwort',
                          labelColor: Colors.white,
                          icon: Icon(
                            Icons.lock,
                            color: primaryGreen,
                          ),
                          obscureText: isPasswordObscure,
                          suffixIcon: IconButton(
                            icon: isPasswordObscure
                                ? const Icon(Icons.visibility_off,
                                    color: Colors.grey)
                                : const Icon(Icons.visibility,
                                    color: Colors.grey),
                            onPressed: () => setState(() {
                              isPasswordObscure = !isPasswordObscure;
                            }),
                          ),
                        ),
                      ),
                      const SizedBox(height: 10),
                      SizedBox(
                        width: 500,
                        child: TextFieldScheme(
                          validator: (value) {
                            if (value!.isEmpty || value != passwordTEC.text) {
                              return "Die eingegebenen Passwörter müssen übereinstimmen!";
                            } else {
                              return null;
                            }
                          },
                          label: 'Passwort wiederholen',
                          labelColor: Colors.white,
                          icon: Icon(
                            Icons.lock,
                            color: primaryGreen,
                          ),
                          obscureText: isPasswordObscure,
                        ),
                      ),
                      const SizedBox(height: 30),
                      // dataSecurity hint
                      GestureDetector(
                        onTap: () => print('jo datenschutz'),
                        child: SizedBox(
                          width: 500,
                          child: RichText(
                            text: TextSpan(
                              children: [
                                const TextSpan(
                                  text: "Sie müssen der ",
                                  style: TextStyle(color: Colors.white),
                                ),
                                TextSpan(
                                    text: 'Datenschutzerklärung',
                                    style: const TextStyle(
                                        color: Colors.white,
                                        fontWeight: FontWeight.bold,
                                        decoration: TextDecoration.underline),
                                    recognizer: TapGestureRecognizer()
                                      ..onTap = () async {
                                        var url =
                                            'https://www.digital.iao.fraunhofer.de/de/datenschutzerklaerung.html';
                                        if (await canLaunchUrl(
                                            Uri.parse(url))) {
                                          await launchUrl(Uri.parse(url));
                                        } else {
                                          DisplayToast.showToast(
                                              toast,
                                              ToastUtil(
                                                toastColor: errorColor,
                                                toastText:
                                                    'Link konnte nicht geöffnet werden',
                                                icon: const Icon(Icons.error,
                                                    color: Colors.white),
                                              ),
                                              ToastGravity.TOP);
                                        }
                                      }),
                                const TextSpan(
                                  text:
                                      ' zustimmen, wenn Sie sich resgistrieren möchten!',
                                  style: TextStyle(color: Colors.white),
                                )
                              ],
                            ),
                          ),
                        ),
                      ),
                      const SizedBox(height: 10),
                      // checkbox
                      Container(
                        width: 500,
                        alignment: Alignment.topLeft,
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Container(
                              height: 20,
                              alignment: Alignment.topLeft,
                              child: Theme(
                                data: ThemeData(
                                    unselectedWidgetColor: Colors.white),
                                child: Checkbox(
                                  checkColor: primaryColor,
                                  activeColor: Colors.white,
                                  value: acceptTerms,
                                  onChanged: (acceptTerms) {
                                    setState(
                                      () {
                                        this.acceptTerms = acceptTerms!;
                                      },
                                    );
                                  },
                                ),
                              ),
                            ),
                            const Flexible(
                              child: Text(
                                'Ich habe die Datenschutzerklärung gelesen und akzeptiert',
                                style: TextStyle(color: Colors.white),
                              ),
                            ),
                          ],
                        ),
                      ),
                      const SizedBox(height: 10),
                      Visibility(
                        visible: showAcceptTermsHint,
                        child: Container(
                          width: 500,
                          decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius:
                                const BorderRadius.all(Radius.circular(5)),
                            boxShadow: [
                              BoxShadow(
                                  color: errorColor,
                                  blurRadius: 6,
                                  offset: const Offset(0, 2))
                            ],
                          ),
                          child: Container(
                            width: 500,
                            decoration: BoxDecoration(
                              border: Border.all(color: errorColor),
                              borderRadius:
                                  const BorderRadius.all(Radius.circular(5)),
                              color: errorColor.withOpacity(0.1),
                            ),
                            child: Padding(
                              padding: const EdgeInsets.only(
                                top: 15,
                                bottom: 15,
                                right: 15,
                                left: 7,
                              ),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.start,
                                children: [
                                  Icon(Icons.arrow_upward_rounded,
                                      color: errorColor, size: 20),
                                  const SizedBox(width: 10),
                                  RichText(
                                    text: TextSpan(
                                      text:
                                          'Bitte akzeptieren Sie die Datenschutzerklärung!',
                                      style: TextStyle(
                                        color: errorColor,
                                        fontSize: 16,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ),
                      // register button
                      const SizedBox(height: 20),
                      Container(
                        width: 500,
                        padding: const EdgeInsets.symmetric(vertical: 25),
                        child: ElevatedButton(
                          style: ButtonStyle(
                            elevation: MaterialStateProperty.all(5),
                            padding: MaterialStateProperty.all(
                                const EdgeInsets.all(15)),
                            shape: MaterialStateProperty.all(
                                RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(15))),
                            backgroundColor:
                                MaterialStateProperty.all(Colors.white),
                          ),
                          onPressed: () {
                            if (formKey.currentState!.validate()) {
                              if (acceptTerms == true) {
                                createUser(
                                    User(
                                        id: null,
                                        firstName: firstNameTEC.text,
                                        lastName: lastNameTEC.text,
                                        email: emailTEC.text,
                                        industry: dropdownValuePostRequest,
                                        company: companyTEC.text,
                                        job: jobTEC.text,
                                        interests:
                                            tagInterestsController.getTags),
                                    passwordTEC.text);
                              } else {
                                setState(() {
                                  showAcceptTermsHint = true;
                                });
                              }
                            } else {
                              DisplayToast.showToast(
                                  toast,
                                  ToastUtil(
                                    toastColor: warnColor,
                                    toastText:
                                        'Bitte Überprüfen Sie ihre Eingaben und stellen Sie sicher, dass keine roten Meldungen mehr angezeigt werden',
                                    icon: const Icon(
                                      Icons.warning,
                                      color: Colors.white,
                                    ),
                                  ),
                                  ToastGravity.TOP);
                            }
                          },
                          child: Text(
                            "Registrieren",
                            style: TextStyle(
                                color: primaryGreen,
                                fontSize: 18,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                      GestureDetector(
                        onTap: (() async => await Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => LogInView()))),
                        child: RichText(
                          text: const TextSpan(
                            children: [
                              TextSpan(
                                text: 'Du hast bereits einen Account?',
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 18,
                                    fontWeight: FontWeight.w500),
                              ),
                              TextSpan(
                                text: " Jetzt einloggen",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  Future<User> createUser(User user, String password) async {
    final response = await http.post(
      Uri.parse('${Constants.getBackendUrl()}/api/v2/auth/register'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, dynamic>{
        'firstName': user.firstName,
        'lastName': user.lastName,
        'email': user.email,
        'company': user.company,
        'job': user.job,
        'industry':
            EnumUtil.StringToEnumStringIndustry(dropdownValuePostRequest),
        'interests': user.interests,
        'password': password,
      }),
    );

    if (response.statusCode == 201) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      DisplayToast.showToast(
          toast,
          ToastUtil(
            toastColor: primaryGreen,
            toastText: 'Sie haben sich erfolgreich registriert',
            icon: const Icon(Icons.check, color: Colors.white),
          ),
          ToastGravity.TOP);
      login(emailTEC.text, passwordTEC.text);
      return User.fromJson(jsonDecode(utf8.decode(response.bodyBytes)));
    } else if (response.statusCode == 400) {
      setStateOfEmail();
      throw Exception('Email is already used!');
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      DisplayToast.showToast(
          toast,
          ToastUtil(
            toastColor: errorColor,
            toastText: 'Die registrierung ist fehlgeschlagen',
            icon: const Icon(Icons.error, color: Colors.white),
          ),
          ToastGravity.TOP);
      throw Exception('Failed to create user.');
    }
  }

  void setStateOfEmail() {
    setState(() {
      emailTEC.text = "";
      showAcceptTermsHint = false;
      isEmailAlreadyUsed = true;
    });
  }

  String validationStringEmail() {
    if (isEmailAlreadyUsed == false) {
      return 'Bitte geben Sie hier eine korrekte E-Mail-Ad­res­se ein';
    } else {
      return 'Es existiert bereits ein Nutzer mit dieser E-Mail-Adresse';
    }
  }

  Future<UserLoggedIn> login(String email, String password) async {
    final response = await http.post(
      Uri.parse('${Constants.getBackendUrl()}/api/v2/auth/login'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, dynamic>{
        'email': email,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      UserLoggedIn userLoggedIn =
          UserLoggedIn.fromJson(jsonDecode(utf8.decode(response.bodyBytes)));
      // ignore: use_build_context_synchronously
      Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(
              builder: (context) => MyHomePage(
                  title: "Digitales2go",
                  userID: userLoggedIn.id,
                  userToken: userLoggedIn.token)),
          (Route<dynamic> route) => false);
      return userLoggedIn;
    } else {
      throw Exception('Failed to login.');
    }
  }
}
