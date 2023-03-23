class ProjectRelations {
  int id;
  List<String> fundingSources;
  List<String> promoters;
  List<String> projectPartners;
  List<String> usePartners;

  ProjectRelations(
      {
      required this.id,
      required this.fundingSources,
      required this.promoters,
      required this.projectPartners,
      required this.usePartners});

  factory ProjectRelations.fromJson(Map<String, dynamic> json) {
    
    return ProjectRelations(
      id: json['id'] ?? 0,
      fundingSources: List.from(json['fundingSources']),
      promoters: List.from(json['promoters']),
      projectPartners: List.from(json['projectPartners']),
      usePartners: List.from(json['usePartners']),
    );
  }

}