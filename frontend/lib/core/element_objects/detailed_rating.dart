class DetailedRating {
  int id;
  num degreeOfInnovation;
  num disruptionPotential;
  num usecases;

  DetailedRating(
      {required this.id,
      required this.degreeOfInnovation,
      required this.disruptionPotential,
      required this.usecases});

  factory DetailedRating.fromJson(Map<String, dynamic> json) {
    return DetailedRating(
      id: json['id'] ?? 0,
      degreeOfInnovation: json['degreeOfInnovation'] ?? 0.0,
      disruptionPotential: json['disruptionPotential'] ?? 0.0,
      usecases: json['useCases'] ?? 0.0,
    );
  }
}
