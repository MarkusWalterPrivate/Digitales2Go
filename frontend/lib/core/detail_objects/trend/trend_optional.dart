import 'package:frontend/core/atomic_objects/contact.dart';

import '../../atomic_objects/externalItems.dart';

class TrendOptional {
  int id;
  List<Contact> contacts;
  List<ExternalItems> externalItems;

  TrendOptional(
      {required this.id, required this.contacts, required this.externalItems});

  factory TrendOptional.fromJson(Map<String, dynamic> json) {
    var contacts = json['contacts'] as List;
    List<Contact> contactList =
        contacts.map((e) => Contact.fromJson(e)).toList();
    var externalItems = json['externalItems'] as List;
    List<ExternalItems> externalItemsList =
        externalItems.map((e) => ExternalItems.fromJson(e)).toList();
    return TrendOptional(
      id: json['id'] as int,
      contacts: contactList,
      externalItems: externalItemsList,
    );
  }
}
