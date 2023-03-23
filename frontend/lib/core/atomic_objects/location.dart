class Location {
  String? country;
  String? city;

  Location({this.country, this.city});

  factory Location.fromJson(Map<String, dynamic> json) {
    return Location(
      country: json['country'],
      city: json['city'],
    );
  }
}