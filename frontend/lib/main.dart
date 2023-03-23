import 'package:flutter/material.dart';
import 'package:frontend/routing/url_strategy_noop.dart'
    if (dart.library.html) 'package:frontend/routing/url_strategy_web.dart';
import 'package:frontend/routing/route_generator.dart';
import 'package:frontend/utils/dummy_data.dart';
import 'package:frontend/widgets/feed/feed.dart';
import 'package:frontend/widgets/feed/feed_row.dart';
import 'package:frontend/widgets/log-in_and_register/log_in.dart';
import 'package:frontend/widgets/log-in_and_register/register.dart';
import 'package:frontend/widgets/profile/profile.dart';
import 'package:json_theme/json_theme.dart';
import 'package:frontend/appBar.dart';
import 'package:flutter/services.dart';
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:jwt_decode/jwt_decode.dart';
import 'package:provider/provider.dart';
import 'core/swipe_objects/card_provider.dart';
import 'widgets/swipe/swipe_card.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  final themeStr = await rootBundle.loadString('assets/theme.json');
  final themeJson = jsonDecode(themeStr);
  final theme = ThemeDecoder.decodeThemeData(themeJson)!;
  usePathUrlStrategy();
  runApp(MyApp(theme: theme));
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key, required this.theme}) : super(key: key);
  final ThemeData? theme;

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: theme,
      initialRoute: "/login",
      onGenerateRoute: RouteGenerator.generateRoute,
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage(
      {Key? key,
      required this.title,
      required this.userID,
      required this.userToken,
      this.tabIndex})
      : super(key: key) {
    userTokenGlobal = userToken;
  }

  final String title;
  final int userID;
  final String userToken;
  final int? tabIndex;

  static String userTokenGlobal = "";

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  bool appBarAccess = true;
  bool bottomNavigationBarAccess = true;
  int _currentIndex = 0;
  String _title = "";
  String userRoleFromToken = "";
  bool isAllowedPostContent = false;

  // userRole error, guest, user, creator, moderator, admin
  String userRoleGlobal = '';

  @override
  initState() {
    getUserRole();
    super.initState();
    onTabTapped(_currentIndex);
    userRoleFromToken = getUserRoleFromToken(widget.userToken);
    isAllowedPostContent = isModeratorOrHigher(userRoleFromToken);
  }

  /// Encodes the given jwt token to get the userRole
  ///
  ///Args:
  /// token (String?): jwt token from logged in user
  ///
  ///Returns:
  ///  String of the userRole
  String getUserRoleFromToken(String? token) {
    if (token == null) {
      return 'ROLE_ERROR';
    } else if (token == '') {
      return 'ROLE_GUEST';
    } else {
      Map<String, dynamic> payload = Jwt.parseJwt(token);
      List<dynamic> roleList = payload.entries.elementAt(2).value;
      String role = roleList.elementAt(0);
      return role;
    }
  }

  /// Will get the userRole from shared_preferences
  /// will return 0 if null
  Future<String> getStringFromSharedPref() async {
    final prefs = await SharedPreferences.getInstance();
    // userRole usage: -1(ROLE_ERROR) = error, 0(ROLE_GUEST) = guest, 1(ROLE_USER) = user, 2(ROLE_CREATOR) = creator, 4(ROLE_MODERATOR) = moderator, 4(ROLE_ADMIN) = admin
    final userRole = prefs.getString('userRole');
    if (userRole == null) {
      return 'ROLE_GUEST';
    }
    return userRole;
  }

  /// Reset the userRole to "TOLE_GUEST"
  Future<void> resetUserRole() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('userRole', 'ROLE_GUEST');
  }

  /// Will update the userRole if needed
  Future<void> getUserRole() async {
    final prefs = await SharedPreferences.getInstance();
    String currentUserRole = await getStringFromSharedPref();
    // get userRole from backend
    String newUserRole = userRoleFromToken;
    prefs.setString('userRole', newUserRole);
    setState(() {
      userRoleGlobal = newUserRole;
    });
  }

  bool isModeratorOrHigher(String userRole) {
    if (userRole == "ROLE_MODERATOR" ||
        userRole == "ROLE_CREATOR" ||
        userRole == "ROLE_ADMIN") {
      return true;
    }
    return false;
  }

  /// A function that is called when a tab is tapped.
  ///
  /// Args:
  ///   context (BuildContext): The current context of the widget.
  ///
  /// Returns:
  ///   A Scaffold widget with a custom app bar, a single child scroll view, a
  /// bottom navigation bar, and a column.
  @override
  Widget build(BuildContext context) {
    final Color errorColor = Theme.of(context).colorScheme.error;
    final Color primaryColor = Theme.of(context).primaryColor;
    // get screenSize
    double heightWitoutPadding = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;

    List<dynamic> screens;
    switch (userRoleGlobal) {
      case 'ROLE_ERROR':
        {
          screens = [
            showErrorRoleDialog(heightWitoutPadding, width, errorColor),
            showErrorRoleDialog(heightWitoutPadding, width, errorColor),
            showErrorRoleDialog(heightWitoutPadding, width, errorColor),
          ];
        }
        break;
      case 'ROLE_GUEST':
        {
          screens = [
            Center(child: (getFeed(widget.userToken))),
            showSignInDialog(heightWitoutPadding, width, Colors.white),
            showSignInDialog(heightWitoutPadding, width, Colors.white),
          ];
        }
        break;
      default:
        {
          screens = [
            Center(child: (getFeed(widget.userToken))),
            Center(child: (SwipeWidget(userToken: widget.userToken))),
            Center(
                child: ProfileView(
              userToken: widget.userToken,
              userId: widget.userID,
            )),
          ];
        }
        break;
    }

    return Scaffold(
      appBar: appBarAccess
          ? CustomAppBar(
              UniqueKey(),
              _title,
              isAllowed: isAllowedPostContent,
              userToken: widget.userToken,
              isFeedPage: false
            )
          : null,
      body: SingleChildScrollView(
          child: Column(
        children: [
          screens[_currentIndex],
        ],
      )),
      bottomNavigationBar: bottomNavigationBarAccess
          ? BottomNavigationBar(
              type: BottomNavigationBarType.fixed,
              currentIndex: _currentIndex,
              showUnselectedLabels: false,
              onTap: (index) {
                setState(() => _currentIndex = index);
                onTabTapped(_currentIndex);
              },
              items: const [
                BottomNavigationBarItem(
                  icon: Icon(
                    Icons.table_rows,
                  ),
                  label: "Feed",
                ),
                BottomNavigationBarItem(
                  icon: Icon(
                    Icons.swipe,
                  ),
                  label: "Swipe",
                ),
                BottomNavigationBarItem(
                  icon: Icon(
                    Icons.person,
                  ),
                  label: "Profile",
                ),
              ],
            )
          : null,
    );
  }

  /// for the case that userRole = "ROLE_ERROR".
  /// Displays a container with the given width and height, in which an alertDialog is centered.
  /// In this alertDialog an error message is displayed and the user has the possibility to
  /// navigate back to the login with a button.
  ///
  /// Args:
  ///   height (double): height of the container
  ///   width (double): width of the container
  ///   backgroundColor (Color): backgroundColor of the alertDialog
  ///
  /// Returns:
  ///   A Widget which exists of a container with a alertDialog which is centered in this container
  Widget showErrorRoleDialog(
      double height, double width, Color backgroundColor) {
    appBarAccess = false;
    bottomNavigationBarAccess = false;
    return Container(
      decoration: BoxDecoration(
        gradient: LinearGradient(colors: [
          Color(0xffff00658f).withOpacity(0.4),
          Color(0xffff00658f).withOpacity(0.6),
          Color(0xffff00658f).withOpacity(0.8),
          Color(0xffff00658f).withOpacity(1),
        ], begin: Alignment.topCenter, end: Alignment.bottomCenter),
      ),
      height: height,
      width: width,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          AlertDialog(
            backgroundColor: backgroundColor,
            title: const Text(
              'Error',
              style:
                  TextStyle(fontWeight: FontWeight.bold, color: Colors.white),
            ),
            content: Row(
              children: [
                const Icon(
                  Icons.error_outline_outlined,
                  color: Colors.white,
                  size: 50,
                ),
                const SizedBox(width: 10),
                RichText(
                  text: const TextSpan(
                      text:
                          'Es scheint etwas schiefgelaufen zu sein. Bitte wenden sie sich an den Support!',
                      style: TextStyle(color: Colors.white, fontSize: 18)),
                )
              ],
            ),
            actions: [
              TextButton(
                style: ButtonStyle(
                    backgroundColor: MaterialStateProperty.resolveWith(
                  (Set<MaterialState> states) {
                    if (states.contains(MaterialState.pressed)) {
                      return backgroundColor.withOpacity(0.5);
                    }
                    return Colors.white; // Use the component's default.
                  },
                )),
                onPressed: () {
                  Navigator.pop(
                    context,
                    MaterialPageRoute(builder: (context) => LogInView()),
                  );
                },
                child: const Text(
                  ' Zurück zum login',
                  style: TextStyle(color: Colors.black, fontSize: 16),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  /// for the case that userRole = "ROLE_GUEST".
  /// Displays a container with the given width and height, in which an alertDialog is centered.
  /// In this alertDialog a message is displayed,
  /// which should prompt the user to register, because he only has access to the feed view.
  /// The user has the possibility to navigate to the registration view or to the feed view with two buttons.
  ///
  /// Args:
  ///   height (double): height of the container
  ///   width (double): width of the container
  ///   backgroundColor (Color): backgroundColor of the alertDialog
  ///
  /// Returns:
  ///   A Widget which exists of a container with a alertDialog which is centered in this container
  Widget showSignInDialog(double height, double width, Color backgroundColor) {
    if (_currentIndex == 0) {
      setState(() {
        appBarAccess = true;
        bottomNavigationBarAccess = true;
      });
    } else {
      setState(() {
        appBarAccess = false;
        bottomNavigationBarAccess = false;
      });
    }
    return Container(
      decoration: BoxDecoration(
        gradient: LinearGradient(colors: [
          Color(0xffff00658f).withOpacity(0.4),
          Color(0xffff00658f).withOpacity(0.6),
          Color(0xffff00658f).withOpacity(0.8),
          Color(0xffff00658f).withOpacity(1),
        ], begin: Alignment.topCenter, end: Alignment.bottomCenter),
      ),
      height: height,
      width: width,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          AlertDialog(
            backgroundColor: backgroundColor,
            title: const Text(
              'Zugriff verweigert!',
              style:
                  TextStyle(fontWeight: FontWeight.bold, color: Colors.black),
            ),
            content: Row(
              children: [
                const Icon(
                  Icons.lock_person_outlined,
                  size: 50,
                  color: Color.fromARGB(255, 23, 156, 125),
                ),
                const SizedBox(width: 10),
                Flexible(
                  child: RichText(
                    text: const TextSpan(
                        text:
                            'Es haben nur registrierte Nutzer Zugriff auf diesen Bereich!',
                        style: TextStyle(color: Colors.black, fontSize: 18)),
                  ),
                )
              ],
            ),
            actions: [
              TextButton(
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.resolveWith(
                    (Set<MaterialState> states) {
                      if (states.contains(MaterialState.pressed))
                        return backgroundColor;
                      return null; // Use the component's default.
                    },
                  ),
                ),
                onPressed: () {
                  setState(() {
                    _currentIndex = 0;
                    appBarAccess = true;
                    bottomNavigationBarAccess = true;
                  });
                },
                child: const Text(
                  'zurück',
                  style: TextStyle(color: Colors.grey, fontSize: 14),
                ),
              ),
              TextButton(
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.resolveWith(
                    (Set<MaterialState> states) {
                      if (states.contains(MaterialState.pressed)) {
                        return backgroundColor;
                      }
                      return null; // Use the component's default.
                    },
                  ),
                ),
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => RegisterView()),
                  );
                },
                child: const Text(
                  'Jetzt registrieren',
                  style: TextStyle(
                      color: Color.fromARGB(255, 23, 156, 125),
                      fontWeight: FontWeight.bold,
                      fontSize: 16),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  /// It changes the title of the app bar based on the index of the tab that is
  /// tapped.
  ///
  /// Args:
  ///   index (int): The index of the tab that was tapped.
  void onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
      switch (index) {
        case 0:
          {
            _title = 'DigiTales2go';
            appBarAccess = true;
          }
          break;
        case 1:
          {
            _title = 'Swipe';
            appBarAccess = false;
          }
          break;
        case 2:
          {
            _title = 'Profil';
            appBarAccess = true;
          }
          break;
      }
    });
  }

  static Feed getFeed(String userToken) {
    FeedRow row1 = getFeedRow("Trending", userToken);
    FeedRow row2 = getFeedRow("Für dich", userToken);
    FeedRow row3 = getFeedRow("Neu", userToken);
    List<FeedRow> rows = [row1, row2, row3];

    return Feed(feedRows: rows, userToken: userToken);
  }

  static FeedRow getFeedRow(String? name, String userToken) {
    return FeedRow(
      feedItems: [],
      feedName: name,
      userToken: userToken,
    );
  }
}
