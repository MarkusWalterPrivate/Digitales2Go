import 'package:frontend/core/detail_objects/technology/tech_required.dart';
import 'package:frontend/core/element_objects/detailed_rating.dart';
import 'package:frontend/core/detail_objects/technology/tech_optional.dart';

import '../../atomic_objects/item.dart';
import '../../element_objects/coreField.dart';
import 'tech_required.dart';

class TechnologyDetail {
  int id;
  CoreField coreField;
  DetailedRating detailedRating;
  TechRequired techRequired;
  TechOptional? techOptional;
  List<String> comments;
  List<Item> internalProjects;
  List<Item> internalCompanies;
  List<Item> internalTechnologies;
  List<Item> internalTrends;

  TechnologyDetail(
      {required this.id,
      required this.coreField,
      required this.detailedRating,
      required this.techRequired,
      this.techOptional,
      required this.comments,
      required this.internalProjects,
      required this.internalCompanies,
      required this.internalTechnologies,
      required this.internalTrends});

  factory TechnologyDetail.fromJson(Map<String, dynamic> json) {
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
    return TechnologyDetail(
      id: json['id'] as int,
      coreField: CoreField.fromJson(json['coreField']),
      detailedRating: DetailedRating.fromJson(json['detailedRating']),
      techRequired: TechRequired.fromJson(json['techRequired']),
      techOptional: TechOptional.fromJson(json['techOptional']),
      comments: List.from(json['comments']),
      internalProjects: internalProjectsList,
      internalCompanies: internalCompaniesList,
      internalTechnologies: internalTechnologiesList,
      internalTrends: internalTrendsList,
    );
  }
}
