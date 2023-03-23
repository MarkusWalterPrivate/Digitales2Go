import 'package:frontend/core/detail_objects/project/project_optional.dart';
import 'package:frontend/core/detail_objects/project/project_required.dart';

import '../../atomic_objects/event.dart';
import '../../atomic_objects/item.dart';
import '../../element_objects/coreField.dart';
import '../../element_objects/detailed_rating.dart';

class ProjectDetail {
  int id;
  CoreField coreField;
  DetailedRating detailedRating;
  ProjectRequired projectRequired;
  ProjectOptional? projectOptional;
  List<Event> events;
  List<String> comments;
  List<Item> internalProjects;
  List<Item> internalCompanies;
  List<Item> internalTechnologies;
  List<Item> internalTrends;

  ProjectDetail(
      {required this.id,
      required this.coreField,
      required this.detailedRating,
      required this.projectRequired,
      this.projectOptional,
      required this.events,
      required this.comments,
      required this.internalProjects,
      required this.internalCompanies,
      required this.internalTechnologies,
      required this.internalTrends});

  factory ProjectDetail.fromJson(Map<String, dynamic> json) {
    var events = json['events'] as List;
    List<Event> eventList = events.map((e) => Event.fromJson(e)).toList();
    var internalProjects = json['internalProjects'] as List;
    List<Item> internalProjectsList =
        internalProjects.map((e) => Item.fromJson(e)).toList();
    var internalCompanies = json['internalCompanies'] as List;
    List<Item> internalCompaniesList =
        internalCompanies.map((e) => Item.fromJson(e)).toList();
    var internalTechnologies = json['internalTechnologies'] as List;
    List<Item> internalTechnologiesList =
        internalTechnologies.map((e) => Item.fromJson(e)).toList();
    var internalTrends = json['internalTrends'] as List;
    List<Item> internalTrendsList =
        internalTrends.map((e) => Item.fromJson(e)).toList();
    return ProjectDetail(
      id: json['id'] as int,
      coreField: CoreField.fromJson(json['coreField']),
      detailedRating: DetailedRating.fromJson(json['detailedRating']),
      projectRequired: ProjectRequired.fromJson(json['projectRequired']),
      projectOptional: ProjectOptional.fromJson(json['projectOptional']),
      events: eventList,
      comments: List.from(json['comments']),
      internalProjects: internalProjectsList,
      internalCompanies: internalCompaniesList,
      internalTechnologies: internalTechnologiesList,
      internalTrends: internalTrendsList,
    );
  }
}
