class Contact {
  int id;
  String name;
  String email;
  String telephone;
  String organisation;

  Contact({this.id  = 0,required this.name, required this.email, required this.telephone, required this.organisation});
  Contact.forMap({this.id  = 0, this.name ="", this.email ="", this.telephone ="", this.organisation =""});

  factory Contact.fromJson(Map<String, dynamic> json) {
    return Contact(
      id: json['id'] ?? 0,
      name: json['name']  as String,
      email: json['email']  as String,
      telephone: json['telephone'] as String,
      organisation: json['organisation']  as String,
    );
  }
}
