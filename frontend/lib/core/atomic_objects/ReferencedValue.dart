class ReferencedValue {
  int id;
  String value;
  int year;
  String reference;

  ReferencedValue(
      {required this.id,
      required this.value,
      required this.year,
      required this.reference});

  factory ReferencedValue.fromJson(Map<String, dynamic> json) {
    return ReferencedValue(
      id: json['id'],
      value: json['value'],
      year: json['year'],
      reference: json['reference'],
    );
  }
}
