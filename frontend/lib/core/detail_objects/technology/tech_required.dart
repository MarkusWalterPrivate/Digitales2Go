class TechRequired {
  int id;
  String description;
  String usecases;
  String discussion;
  List<String> projectsIAO;
  String readiness;

  TechRequired({
      required this.id, required this.description, required this.usecases, required this.discussion, required this.projectsIAO, required this.readiness});
  
    factory TechRequired.fromJson(Map<String, dynamic> json) {
    
    return TechRequired(
      id: json['id'] ?? 0,
      description:  json['description'],
      usecases: json['useCases'],
      discussion: json['discussion'],
      projectsIAO: List.from(json['projectsIAO']),
      readiness: json['readiness'],
    );
  }

}
