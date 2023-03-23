class ExternalItems {
  int id;
  String type;
  String text;

  ExternalItems({required this.id, required this.type, required this.text});
  ExternalItems.forMap({ this.id = 0,  this.type = "",  this.text = ""});

  factory ExternalItems.fromJson(Map<String, dynamic> json) {
    return ExternalItems(
      id: json['id'],
      type: json['type'],
      text: json['text'],
    );
  }
}
