import 'package:frontend/core/profile_objects/ratings.dart';

class User {
  int? id;
  String firstName;
  String lastName;
  String email;
  String? industry;
  String? company;
  String? job;
  String? mandate;
  List<String>? interests;

  User(
      {this.id,
      required this.firstName,
      required this.lastName,
      required this.email,
      this.industry,
      this.company,
      this.job,
      this.mandate,
      this.interests});

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
        id: json['id'] ?? 0,
        firstName: json['firstName'] as String,
        lastName: json['lastName'] as String,
        email: json['email'] as String,
        industry: json['industry'],
        company: json['company'],
        job: json['job'],
        mandate: json['mandate'],
        interests: List.from(json['interests']));
  }
}

class UserLoggedIn {
  int id;
  String token;

  UserLoggedIn({required this.id, required this.token});

  factory UserLoggedIn.fromJson(Map<String, dynamic> json) {
    return UserLoggedIn(
      id: json['id'],
      token: json['token'],
    );
  }
}
