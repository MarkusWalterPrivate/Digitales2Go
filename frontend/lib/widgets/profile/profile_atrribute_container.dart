import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../../core/profile_objects/user.dart';

class ProfileAttributeContainer extends StatefulWidget {
  ProfileAttributeContainer(
      {Key? key,
      required this.user,
      required this.animate,
      required this.onTap})
      : super(key: key);
  User user;
  bool animate;
  Function()? onTap;

  @override
  State<ProfileAttributeContainer> createState() =>
      _ProfileAttributeContainerState();
}

class _ProfileAttributeContainerState extends State<ProfileAttributeContainer> {
  final Color primaryGreen = Color.fromARGB(255, 23, 156, 125);
  String title = "MyTitle";
  bool isEditable = false;
  @override
  Widget build(BuildContext context) {
    // get colors
    final Color primaryColor = Theme.of(context).colorScheme.primary;
    return AnimatedContainer(
      height: widget.animate ? 550 : 200,
      alignment: Alignment.topCenter,
      duration: const Duration(milliseconds: 500),
      child: Card(
        clipBehavior: Clip.antiAlias,
        elevation: 4,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(14)),
        child: Container(
          height: 550,
          width: 400,
          color: primaryColor,
          child: Stack(
            children: [
              Stack(
                children: [
                  Container(
                    height: 200,
                    width: 400,
                    color: Colors.black.withOpacity(0.3),
                    child: Padding(
                      padding: const EdgeInsets.all(15.0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: const [
                              Align(
                                alignment: Alignment.topRight,
                                child: Text(
                                  'DigiTales2Go',
                                  style: TextStyle(
                                      fontSize: 25,
                                      fontWeight: FontWeight.bold,
                                      color: Colors.white,
                                      fontFamily: 'SegeoePrint'),
                                ),
                              ),
                              Image(
                                image: AssetImage(
                                    "assets/images/18_iao_rgb_white.png"),
                                height: 50,
                                width: 125,
                              ),
                            ],
                          ),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                '${widget.user.firstName} ${widget.user.lastName}',
                                style: const TextStyle(
                                  color: Colors.white,
                                ),
                              ),
                              Text(
                                widget.user.email,
                                style: const TextStyle(
                                  color: Colors.white,
                                ),
                              ),
                              GestureDetector(
                                onTap: widget.onTap,
                                child: Row(
                                  children: [
                                    Text(
                                        widget.animate
                                            ? 'Gespeicherte Elemente anzeigen'
                                            : 'Profilinformationen',
                                        style: TextStyle(color: primaryGreen)),
                                    Icon(
                                      widget.animate
                                          ? Icons.arrow_drop_down_rounded
                                          : Icons.arrow_right_rounded,
                                      color: Colors.white,
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              Positioned(
                top: 200,
                left: 0,
                right: 0,
                child: Stack(
                  children: [
                    const Divider(color: Colors.white, thickness: 3),
                    Container(
                      height: 350,
                      width: 400,
                      color: Colors.black.withOpacity(0.3),
                      child: Padding(
                        padding: const EdgeInsets.all(15.0),
                        child: SingleChildScrollView(
                          scrollDirection: Axis.vertical,
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.end,
                            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                            children: [
                              buildInfoContainer(Icons.person_pin_outlined,
                                  widget.user.mandate!, 'Mandat'),
                              buildInfoContainer(Icons.business,
                                  widget.user.company!, 'Firma'),
                              buildInfoContainer(Icons.handyman_outlined,
                                  widget.user.job!, 'Position / Tätigkeit'),
                              buildInfoContainer(Icons.factory_outlined,
                                  widget.user.industry!, 'Industrie'),
                              buildInfoListContainer(
                                  Icons.interests, widget.user.interests!),
                            ],
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget buildInfoContainer(IconData icon, String text, String attribute) {
    if (text == '' || text == 'Not Set') {
      return Padding(
        padding: const EdgeInsets.only(top: 5, bottom: 5),
        child: Container(
            decoration: BoxDecoration(
                borderRadius: const BorderRadius.all(Radius.circular(5)),
                color: Colors.grey[400]),
            padding: const EdgeInsets.all(10),
            child: Center(child: Text(attribute))),
      );
    } else {
      return Padding(
        padding: const EdgeInsets.only(top: 5, bottom: 5),
        child: Container(
          decoration: const BoxDecoration(
              borderRadius: BorderRadius.all(Radius.circular(5)),
              color: Colors.white),
          padding: const EdgeInsets.all(10),
          child: Row(
            children: [
              Icon(icon, color: const Color.fromARGB(255, 23, 156, 125)),
              const SizedBox(width: 10),
              Flexible(
                  child:
                      Text(text, style: const TextStyle(color: Colors.black))),
            ],
          ),
        ),
      );
    }
  }

  Widget buildInfoListContainer(IconData icon, List<String> list) {
    if (list.isEmpty) {
      return Padding(
        padding: const EdgeInsets.only(top: 5, bottom: 5),
        child: Container(
            decoration: BoxDecoration(
                borderRadius: const BorderRadius.all(Radius.circular(5)),
                color: Colors.grey[400]),
            padding: const EdgeInsets.all(10),
            child: const Center(child: Text('Interests'))),
      );
    } else {
      return Padding(
        padding: const EdgeInsets.only(top: 5, bottom: 5),
        child: Container(
          width: 400,
          decoration: const BoxDecoration(
              borderRadius: BorderRadius.all(
                Radius.circular(5),
              ),
              color: Colors.white),
          padding: const EdgeInsets.all(10),
          child: Wrap(
            runSpacing: 3,
            spacing: 5,
            children: [
              Icon(
                icon,
                color: const Color.fromARGB(255, 23, 156, 125),
              ),
              const SizedBox(width: 10),
              for (var item in list)
                Chip(
                  backgroundColor: primaryGreen,
                  label: Text(
                    item,
                    style: const TextStyle(color: Colors.white),
                  ),
                )
            ],
          ),
        ),
      );
    }
  }
}
