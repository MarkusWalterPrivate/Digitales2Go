import 'package:flutter/material.dart';
import 'package:frontend/widgets/approve_items.dart/approve_items_view.dart';
import 'package:frontend/widgets/log-in_and_register/log_in.dart';
import 'package:frontend/widgets/uploadDTO/uploadForm.dart';

class CustomAppBar extends StatelessWidget with PreferredSizeWidget {
  CustomAppBar(Key key, this.title,
      {this.isAllowed = false,
      required this.userToken,
      required this.isFeedPage})
      : super(key: key);

  final String title;
  bool isAllowed;
  String userToken;
  bool isFeedPage;

  /// It creates a custom AppBar with a title.
  ///
  /// Args:
  ///   context (BuildContext): The context of the widget.
  ///
  /// Returns:
  ///   A widget that is an AppBar with a title.
  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: title == 'Profil'
          ? Row(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                IconButton(
                    onPressed: () {
                      Navigator.of(context).pushAndRemoveUntil(
                          MaterialPageRoute(builder: (context) => LogInView()),
                          (Route<dynamic> route) => false);
                    },
                    icon: const Icon(
                      Icons.logout_outlined,
                      size: 30,
                    ))
              ],
            )
          : null,
      title: Text(
        title,
        style: TextStyle(
            fontSize: title == 'DigiTales2go' ? 30 : 28,
            fontFamily: title == 'DigiTales2go' ? 'SegeoePrint' : ''),
      ),
      flexibleSpace: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            fit: BoxFit.cover,
            colorFilter: ColorFilter.mode(
                Colors.black.withOpacity(0.3), BlendMode.dstATop),
            image: const AssetImage('assets/images/appBarBackground.jpg'),
          ),
        ),
      ),
      centerTitle: true,
      automaticallyImplyLeading: false,
      leadingWidth: 250,
      actions: [
        showAboutButton(context),
        showPopMenu(isAllowed, context, userToken)
      ],
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(75);

  Widget showAboutButton(context) {
    return IconButton(
        onPressed: () {
          showDialog(
              context: context,
              builder: (context) => AlertDialog(
                    title: const Text("About/Impressum"),
                    content: const Text(
                        "DigiTales2Go ist ein Angebot des\nFraunhofer IAO\nNobelstraße 12\n70569 Stuttgart\n\nEmail: stupross22@iao.fraunhofer.de\nTel.: +49 711 970-01\n\nEs werden Inhalte von Drittanbietern dargestellt.\nWir übernehmen keinerlei Gewähr für Richtigkeit\nder dargetellten Daten. Wenden Sie sich bei Fragen\noder Beschwerden an die zugehörige Stelle."),
                    actions: [
                      TextButton(
                        child: const Text("OK"),
                        onPressed: () => Navigator.pop(context),
                      )
                    ],
                  ));
        },
        icon: const Icon(Icons.info_rounded, size: 26));
  }

  Widget showPopMenu(bool isAllowed, context, String userToken) {
    if (isAllowed) {
      return PopupMenuButton(
          iconSize: 26,
          // add icon, by default "3 dot" icon
          // icon: Icon(Icons.book)
          itemBuilder: (context) {
            return [
              const PopupMenuItem<int>(
                value: 0,
                child: Text("Erstellen neuer Objekte"),
              ),
              const PopupMenuItem<int>(
                value: 1,
                child: Text("Item Anfragen genehmigen"),
              ),
            ];
          },
          onSelected: (value) {
            if (value == 0) {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => ShowUploadForm(userToken: userToken)),
              );
            } else if (value == 1) {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) =>
                          ApproveItemsView(userToken: userToken)));
            }
          });
    } else {
      return const SizedBox.shrink();
    }
  }
}
