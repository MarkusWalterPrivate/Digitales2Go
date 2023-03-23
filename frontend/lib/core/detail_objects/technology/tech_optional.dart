import 'package:frontend/core/element_objects/coreField.dart';

import '../../atomic_objects/contact.dart';
import '../../atomic_objects/externalItems.dart';

class TechOptional {
  int? id;
  List<Contact>? contacts;
  List<String>? references;
  List<ExternalItems>? externalItems;
  List<String>? industriesWithUse;

  TechOptional(
      {this.id,
      this.contacts,
      this.references,
      this.externalItems,
      this.industriesWithUse});

  factory TechOptional.fromJson(Map<String, dynamic> json) {
    var contacts = json['contacts'] as List;
    List<Contact> contactList =
        contacts.map((e) => Contact.fromJson(e)).toList();
        var externalItems = json['externalItems'] as List;
    List<ExternalItems> externalItemsList =
        externalItems.map((e) => ExternalItems.fromJson(e)).toList();
    return TechOptional(
        id: json['id'] ?? 0,
        contacts: contactList,
        references: List.from(json['references']),
        externalItems: externalItemsList,
        industriesWithUse: List.from(json['industriesWithUse']));
  }
}
