

import 'package:flutter/material.dart';
import 'package:frontend/widgets/detail/company_detail_widget.dart';
import 'package:frontend/widgets/detail/project_detail_widget.dart';
import 'package:frontend/widgets/detail/technology_detail_widget.dart';
import 'package:frontend/widgets/detail/trend_detail_widget.dart';
import 'package:frontend/widgets/log-in_and_register/log_in.dart';




class RouteGenerator {
  static Route<dynamic> generateRoute(RouteSettings settings) {
    final args = settings.arguments;
    if (settings.name == '/login') {
      return MaterialPageRoute(builder: (_) => LogInView());
    } else if (settings.name == '/tech/?id=${Uri.base.queryParameters['id']}') {
      int itemId = int.parse(Uri.base.queryParameters['id']!);
      return MaterialPageRoute(
          builder: (_) => TechnologyDetailView(
                isSharedItem: true,
                id: itemId,
              ));
    } else if (settings.name ==
        '/trend/?id=${Uri.base.queryParameters['id']}') {
      int itemId = int.parse(Uri.base.queryParameters['id']!);
      return MaterialPageRoute(
          builder: (_) => TrendDetailView(isSharedItem: true, id: itemId));
    } else if (settings.name ==
        '/company/?id=${Uri.base.queryParameters['id']}') {
      int itemId = int.parse(Uri.base.queryParameters['id']!);
      return MaterialPageRoute(
          builder: (_) => CompanyDetailView(isSharedItem: true, id: itemId));
    } else if (settings.name ==
        '/project/?id=${Uri.base.queryParameters['id']}') {
      int itemId = int.parse(Uri.base.queryParameters['id']!);
      return MaterialPageRoute(
          builder: (_) => ProjectDetailView(isSharedItem: true, id: itemId));
    } else {
      return MaterialPageRoute(builder: (_) => LogInView());
    }
  }
}
