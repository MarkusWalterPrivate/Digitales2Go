class Size {
  int id;
  String teamSize;
  int year;
  String reference;

  Size(
      {required this.id,
      required this.teamSize,
      required this.year,
      required this.reference});

  factory Size.fromJson(Map<String, dynamic> json) {
    return Size(
        id: json['id'] as int,
        teamSize: json['teamSize'],
        year: json['year'],
        reference: json['reference'],
    );
  }

}