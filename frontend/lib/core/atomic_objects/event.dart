class Event {
  int id;
  int? date;
  String location;
  String description;
  String imageSource;
  String website;

  Event(
      {required this.id,
      required this.date,
      required this.location,
      required this.description,
      required this.imageSource,
      required this.website});

  Event.forMap(
      { this.id = 0,
      required this.date,
      required this.location,
      required this.description,
      required this.imageSource,
      required this.website});

  factory Event.fromJson(Map<String, dynamic> json) {
    return Event(
      id: json['id'] as int,
      date:  json['date'] as int,
      location: json['location'],
      description: json['description'],
      imageSource: json['imageSource'],
      website:  json['website']
    );
  }

}