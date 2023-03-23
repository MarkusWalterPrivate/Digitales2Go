import 'package:frontend/core/element_objects/coreField.dart';

class Item {
  int id;
  CoreField coreField;

  Item({
    required this.id, required this.coreField
  });

  factory Item.fromJson(Map<String, dynamic> json) {
    return Item(
      id: json['id'],
      coreField: CoreField.fromJson(json['coreField'])
    );
  }

}