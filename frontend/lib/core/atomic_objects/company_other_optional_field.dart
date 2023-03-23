import 'package:frontend/core/atomic_objects/location.dart';
import 'package:frontend/core/enums/product_readiness.dart';

import 'ReferencedValue.dart';

class CompanyOtherOptionalField {

  Location location;
  ProductReadiness productReadiness;
  ReferencedValue customer;
  ReferencedValue revenue;
  ReferencedValue profit;

  CompanyOtherOptionalField(
    this.location, this.productReadiness, this.customer, this.revenue, this.profit

  );
}