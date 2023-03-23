class TrendRequired {
  int id;
  String description;
  String discussion;

  TrendRequired(
      {required this.id, required this.description, required this.discussion});

  factory TrendRequired.fromJson(Map<String, dynamic> json) {
    return TrendRequired(
      id: json['id'] as int,
      description: json['description'] as String,
      discussion: json['discussion']  as String,
    );
  }
}