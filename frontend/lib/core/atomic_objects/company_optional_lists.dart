import 'package:frontend/core/atomic_objects/contact.dart';

class CompanyOptionalLists {
  int? id;
  List<Contact>? contacts;
  List<String>? targetMarkets;
  List<String>? partners;
  List<String>? alignments;
  List<String>? investors;

  CompanyOptionalLists(
      {this.id,
      this.contacts,
      this.targetMarkets,
      this.partners,
      this.alignments,
      this.investors});

  factory CompanyOptionalLists.fromJson(Map<String, dynamic> json) {
    var contacts = json['contacts'] as List;
    List<Contact> contactList = contacts.map((e) => Contact.fromJson(e)).toList();
    return CompanyOptionalLists(
      id: json['id'] as int,
      contacts:  contactList,
      targetMarkets: List.from(json['targetMarkets']),
      alignments:  List.from(json['alignments']),
      partners:  List.from(json['partners']),
      investors: List.from(json['investors'])
    );
  }

}