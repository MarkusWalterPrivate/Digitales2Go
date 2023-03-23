import 'package:frontend/core/atomic_objects/size.dart';

class CompanyRequired {
  int id;
  String description;
  String useCases;
  Size teamSize;
  String website;
  int foundationYear;

  CompanyRequired(
      {required this.id,
      required this.description,
      required this.useCases,
      required this.teamSize,
      required this.website,
      required this.foundationYear});

  factory CompanyRequired.fromJson(Map<String, dynamic> json) {
    return CompanyRequired(
        id: json['id'] as int,
        description: json['description'],
        useCases: json['useCases'],
        teamSize: Size.fromJson(json['teamSize']),
        website: json['website'],
        foundationYear: json['foundationYear']);
  }
}
