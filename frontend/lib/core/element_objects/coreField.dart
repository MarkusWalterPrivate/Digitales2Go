

class CoreField {
  int id; 
  bool? intern;
  String? source;
  int lastUpdated;
  int creationDate;
  double rating;
  String imageSource;
  String headline;
  String teaser;
  String industry;
  List<String> tags;
  String type;

  CoreField({required this.id, this.intern, this.source, required this.lastUpdated, required this.creationDate,required this.rating, required this.imageSource, required this.headline,
      required this.teaser, required this.industry, required this.tags, required this.type});

  factory CoreField.fromJson(Map<String, dynamic> json) {
    return CoreField(
      id: json['id'] ?? 0,
      intern: json['intern'],
      source: json['source'],
      lastUpdated: json['lastUpdated'] as int,
      creationDate: json['creationDate'] as int,
      rating: json['rating'] as double,
      imageSource: json['imageSource']  as String,
      headline: json['headline']  as String,
      teaser: json['teaser']  as String,
      industry: json['industry'],
      tags: List.from(json['tags']),
      type: json['type']
    );
  }
}
