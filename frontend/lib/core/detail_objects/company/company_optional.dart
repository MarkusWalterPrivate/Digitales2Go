import 'package:frontend/core/atomic_objects/company_optional_lists.dart';
import 'package:frontend/core/atomic_objects/company_optional_fields.dart';

import '../../atomic_objects/externalItems.dart';

class CompanyOptional {
  int? id;
  List<ExternalItems>? externalItems;
  CompanyOptionalLists? companyOptionalLists;
  CompanyOptionalFields? companyOptionalFields;

  CompanyOptional(
      {this.id,
      this.externalItems,
      this.companyOptionalLists,
      this.companyOptionalFields});

  factory CompanyOptional.fromJson(Map<String, dynamic> json) {
    var externalItems = json['externalItems'] as List;
    List<ExternalItems> externalItemsList =
        externalItems.map((e) => ExternalItems.fromJson(e)).toList();
    return CompanyOptional(
      id: json['id'] as int,
      externalItems: externalItemsList,
      companyOptionalLists:
          CompanyOptionalLists.fromJson(json['companyOptionalLists']),
      companyOptionalFields:
          CompanyOptionalFields.fromJson(json['companyOptionalFields']),
    );
  }
}
