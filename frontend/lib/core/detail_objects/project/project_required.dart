import '../../atomic_objects/contact.dart';
import '../../atomic_objects/runtime.dart';

class ProjectRequired {
  int id;
  String description;
  String readiness;
  Runtime runtime;
  List<Contact> contacts;

  ProjectRequired(
      {required this.id,
      required this.description,
      required this.readiness,
      required this.runtime,
      required this.contacts});

  factory ProjectRequired.fromJson(Map<String, dynamic> json) {
    var contacts = json['contacts'] as List;
    List<Contact> contactList =
        contacts.map((e) => Contact.fromJson(e)).toList();
    return ProjectRequired(
      id: json['id'] ?? 0,
      description: json['description'],
      readiness: json['readiness'],
      runtime: Runtime.fromJson(json['runtime']),
      contacts: contactList,
    );
  }
}
