import 'package:frontend/core/atomic_objects/item.dart';
import 'package:frontend/core/detail_objects/company/company_required.dart';
import 'package:frontend/core/element_objects/coreField.dart';
import 'package:frontend/core/element_objects/detailed_rating.dart';

import 'company_optional.dart';

class CompanyDetail {
  int id;
  CoreField coreField;
  DetailedRating detailedRating;
  CompanyRequired companyRequired;
  CompanyOptional? companyOptional;
  List<String> comments;
  List<Item> internalProjects;
  List<Item> internalCompanies;
  List<Item> internalTechnologies;
  List<Item> internalTrends;

  CompanyDetail(
      {required this.id,
      required this.coreField,
      required this.detailedRating,
      required this.companyRequired,
      this.companyOptional,
      required this.comments,
      required this.internalProjects,
      required this.internalCompanies,
      required this.internalTechnologies,
      required this.internalTrends});

  factory CompanyDetail.fromJson(Map<String, dynamic> json) {
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

    return CompanyDetail(
      id: json['id'] as int,
      coreField: CoreField.fromJson(json['coreField']),
      detailedRating: DetailedRating.fromJson(json['detailedRating']),
      companyRequired: CompanyRequired.fromJson(json['companyRequired']),
      companyOptional: CompanyOptional.fromJson(json['companyOptional']),
      comments: List.from(json['comments']),
      internalProjects: internalProjectsList,
      internalCompanies: internalCompaniesList,
      internalTechnologies: internalTechnologiesList,
      internalTrends: internalTrendsList,
    );
  }
}
