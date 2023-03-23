import 'package:frontend/core/atomic_objects/ReferencedValue.dart';
import 'package:frontend/core/atomic_objects/projectRelations.dart';
import 'package:frontend/core/element_objects/coreField.dart';

import '../../atomic_objects/externalItems.dart';

class ProjectOptional {
  int? id;
  List<ExternalItems>? externalItems;
  List<String>? publications;
  ProjectRelations? projectRelations;
  ReferencedValue? financing;
  String? website;

  ProjectOptional(
      {this.id,
      this.externalItems,
      this.publications,
      this.projectRelations,
      this.financing,
      this.website});

  factory ProjectOptional.fromJson(Map<String, dynamic> json) {
    var externalItems = json['externalItems'] as List;
    List<ExternalItems> externalItemsList =
        externalItems.map((e) => ExternalItems.fromJson(e)).toList();
    return ProjectOptional(
        id: json['id'] ?? 0,
        externalItems: externalItemsList,
        publications: List.from(json['publications']),
        projectRelations: ProjectRelations.fromJson(json['projectRelations']),
        financing: ReferencedValue.fromJson(json['financing']),
        website: json['website']);
  }
}
