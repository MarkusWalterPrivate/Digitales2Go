import 'package:frontend/core/atomic_objects/location.dart';

import 'ReferencedValue.dart';

class CompanyOptionalFields {
  int? id;
  Location? location;
  String? productReadiness;
  ReferencedValue? customer;
  ReferencedValue? revenue;
  ReferencedValue? profit;

  CompanyOptionalFields(
      {this.id,
      this.location,
      this.productReadiness,
      this.customer,
      this.revenue,
      this.profit});

  factory CompanyOptionalFields.fromJson(Map<String, dynamic> json) {
    return CompanyOptionalFields(
      id: json['id'] as int,
      location: Location.fromJson(json['location']),
      productReadiness:  json['productReadiness'],
      customer: ReferencedValue.fromJson(json['numberOfCustomers']),
      revenue: ReferencedValue.fromJson(json['revenue']),
      profit:  ReferencedValue.fromJson(json['profit'])
    );
  }

}
