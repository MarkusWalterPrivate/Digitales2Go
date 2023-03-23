import 'package:frontend/core/detail_objects/trend/trend_optional.dart';
import 'package:frontend/core/detail_objects/trend/trend_required.dart';
import 'package:frontend/core/element_objects/coreField.dart';
import 'package:frontend/core/element_objects/detailed_rating.dart';

import '../../atomic_objects/item.dart';

class TrendDetail {
  int id;
  CoreField coreField;
  DetailedRating detailedRating;
  TrendRequired trendRequired;
  TrendOptional? trendOptional;
  List<String> comments;
  List<Item> internalProjects;
  List<Item> internalCompanies;
  List<Item> internalTechnologies;
  List<Item> internalTrends;

  TrendDetail(
      {required this.id,
      required this.coreField,
      required this.detailedRating,
      required this.trendRequired,
      this.trendOptional,
      required this.comments,
      required this.internalProjects,
      required this.internalCompanies,
      required this.internalTechnologies,
      required this.internalTrends});

  factory TrendDetail.fromJson(Map<String, dynamic> json) {
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
    return TrendDetail(
      id: json['id'] as int,
      coreField: CoreField.fromJson(json['coreField']),
      detailedRating: DetailedRating.fromJson(json['detailedRating']),
      trendRequired: TrendRequired.fromJson(json['trendRequired']),
      trendOptional: TrendOptional.fromJson(json['trendOptional']),
      comments: List.from(json['comments']),
      internalProjects: internalProjectsList,
      internalCompanies: internalCompaniesList,
      internalTechnologies: internalTechnologiesList,
      internalTrends: internalTrendsList,
    );
  }
}
