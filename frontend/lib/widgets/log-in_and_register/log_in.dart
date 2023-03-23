import 'dart:convert';

import 'package:email_validator/email_validator.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:frontend/core/profile_objects/user.dart';
import 'package:frontend/widgets/log-in_and_register/register.dart';
import 'package:frontend/widgets/log-in_and_register/text_field.dart';
import 'package:http/http.dart' as http;
import '../../constants/url.dart';
import '../../main.dart';
import '../../utils/toast_util.dart';

class LogInView extends StatefulWidget {
  LogInView({Key? key}) : super(key: key);
  @override
  State<LogInView> createState() => _LogInViewState();
}

class _LogInViewState extends State<LogInView> {
  final formKey = GlobalKey<FormState>();
  final toast = FToast();
  //Color
  final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);
  final Color errorColor = Color(0xffffba1b1b);
  bool isLoginfailed = false;
  bool isPasswordObscure = true;
  // controller for textfield input
  TextEditingController emailTEC = TextEditingController();
  TextEditingController passwordTEC = TextEditingController();

  @override
  void initState() {
    super.initState();
    toast.init(context);
  }

  @override
  Widget build(BuildContext context) {
    final Color primaryColor = Theme.of(context).primaryColor;
    final Color errorColor = Theme.of(context).colorScheme.error;

    // get screenSize
    double height = MediaQuery.of(context).size.height -
        MediaQuery.of(context).padding.bottom;
    double width = MediaQuery.of(context).size.width;

    return MaterialApp(
      home: MediaQuery(
        data: const MediaQueryData(),
        child: Scaffold(
          body: Container(
            height: height,
            width: width,
            decoration: BoxDecoration(
              gradient: LinearGradient(colors: [
                primaryColor.withOpacity(0.4),
                primaryColor.withOpacity(0.6),
                primaryColor.withOpacity(0.8),
                primaryColor.withOpacity(1),
              ], begin: Alignment.topCenter, end: Alignment.bottomCenter),
            ),
            child: SingleChildScrollView(
              padding: const EdgeInsets.symmetric(
                horizontal: 25,
                // vertical: 50,
              ),
              child: Form(
                autovalidateMode: AutovalidateMode.onUserInteraction,
                key: formKey,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    const SizedBox(height: 60),
                    Wrap(
                      alignment: WrapAlignment.center,
                      children: [
                        Container(
                          padding: const EdgeInsets.only(top: 12),
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
                              padding: const EdgeInsets.only(top: 28, left: 10),
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
                    Visibility(
                      visible: isLoginfailed,
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
                            padding: const EdgeInsets.all(15.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                RichText(
                                  text: TextSpan(
                                    text:
                                        'Ihr Anmeldeversuch ist fehlgeschlagen',
                                    style: TextStyle(
                                      color: errorColor,
                                      fontWeight: FontWeight.bold,
                                      fontSize: 16,
                                    ),
                                  ),
                                ),
                                const Padding(
                                    padding: EdgeInsets.only(top: 10)),
                                RichText(
                                  text: TextSpan(
                                    text:
                                        'Bitte stellen Sie sicher, dass ihre Email Adresse und Passwort korrekt sind.',
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
                    Visibility(
                      visible: isLoginfailed,
                      child: const SizedBox(height: 30),
                    ),
                    SizedBox(
                      width: 500,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          TextFieldScheme(
                            validator: (value) {
                              if (value != null &&
                                  !EmailValidator.validate(value)) {
                                return "Bitte geben sie hier eine korrekte Emailadresse ein";
                              } else {
                                return null;
                              }
                            },
                            controller: emailTEC,
                            label: 'E-Mail',
                            labelColor: Colors.white,
                            icon: const Icon(
                              Icons.email,
                              color: Color.fromARGB(255, 23, 156, 125),
                            ),
                            textInputType: TextInputType.emailAddress,
                            obscureText: false,
                          ),
                          const SizedBox(height: 20),
                          TextFieldScheme(
                            validator: (value) {
                              if (value!.isEmpty || value.length < 5) {
                                return "Bitte geben sie ein Passwort mit mindestens 6 Zeichen ein";
                              } else {
                                return null;
                              }
                            },
                            controller: passwordTEC,
                            label: 'Passwort',
                            labelColor: Colors.white,
                            icon: const Icon(
                              Icons.lock,
                              color: Color.fromARGB(255, 23, 156, 125),
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
                          )
                        ],
                      ),
                    ),
                    Container(
                      width: 500,
                      padding: EdgeInsets.symmetric(vertical: 25),
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
                        onPressed: (() {
                          if (formKey.currentState!.validate()) {
                            login(emailTEC.text, passwordTEC.text);
                          }
                        }),
                        child: const Text(
                          "Login",
                          style: TextStyle(
                              color: Color.fromARGB(255, 23, 156, 125),
                              fontSize: 18,
                              fontWeight: FontWeight.bold),
                        ),
                      ),
                    ),
                    GestureDetector(
                      onTap: (() async => await Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => RegisterView()))),
                      child: RichText(
                        text: const TextSpan(
                          children: [
                            TextSpan(
                                text: 'Noch nicht registriert?',
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 18,
                                    fontWeight: FontWeight.w500)),
                            TextSpan(
                                text: " Jetzt registrieren",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold))
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(height: 10),
                    const SizedBox(
                      width: 500,
                      child: Divider(color: Colors.white, thickness: 5),
                    ),
                    const SizedBox(height: 20),
                    RichText(
                      text: const TextSpan(
                          text: 'Kleiner Einblick gefällig?',
                          style: TextStyle(
                              color: Colors.white,
                              fontSize: 18,
                              fontWeight: FontWeight.w500)),
                    ),
                    Container(
                      width: 500,
                      padding: const EdgeInsets.symmetric(vertical: 10),
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
                        onPressed: (() {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => MyHomePage(
                                      title: "Digitales2go",
                                      userID: 0,
                                      userToken: '')));
                        }),
                        child: const Text(
                          "Als Gast anmelden",
                          style: TextStyle(
                              color: Color.fromARGB(255, 23, 156, 125),
                              fontSize: 18,
                              fontWeight: FontWeight.bold),
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
    );
  }

  Future<UserLoggedIn> login(String email, String password) async {
    try {
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
        setState(() {
          isLoginfailed = true;
        });
        throw Exception('Failed to login.');
      }
    } on Exception {
      DisplayToast.showToast(
          toast,
          ToastUtil(
            toastColor: errorColor,
            toastText:
                'Es konnte keine Verbindung hergestellt werden. Bitte versuchen sie es in ein paar Minuten erneut!',
            icon: const Icon(Icons.error_outline, color: Colors.white),
          ),
          ToastGravity.TOP);
      setState(() {
        emailTEC.text = "";
        passwordTEC.text = "";
      });
      // Navigator.pushReplacement(context,
      //     MaterialPageRoute(builder: (BuildContext context) => super.widget));
      throw Exception(
          'Es konnte keine Verbindung zum Server hergestellt werden!');
    }
  }
}
