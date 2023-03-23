/* import 'package:frontend/core/detail_objects/company/company_detail_obj.dart';
import 'package:frontend/core/detail_objects/project/project_detail_obj.dart';
import 'package:frontend/core/detail_objects/technology/technology_detail_obj.dart';
import 'package:frontend/core/detail_objects/trend/trend_detail_obj.dart';
import 'package:frontend/widgets/detail/company_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/project_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/technology_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/trend_detail_widget_swipe.dart';
import 'package:frontend/widgets/swipe/swipecards/swipe_cards.dart';

import '../../utils/dummy_data.dart';

enum DetailType { company, project, technology, trend }

class SwipeDetailObject {
  DetailType detailType = DetailType.trend;
  ProjectDetail? projectDetail;
  CompanyDetail? companyDetail;
  TechnologyDetail? technologyDetail;
  TrendDetail? trendDetail;

  SwipeDetailObject.project(ProjectDetail projectDetail) {
    detailType = DetailType.project;
    projectDetail = projectDetail;
  }

  SwipeDetailObject.company(CompanyDetail companyDetail) {
    detailType = DetailType.company;
    companyDetail = companyDetail;
  }
  SwipeDetailObject.technology(TechnologyDetail technologyDetail) {
    detailType = DetailType.technology;
    technologyDetail = technologyDetail;
  }
  SwipeDetailObject.trend(TrendDetail trendDetail) {
    detailType = DetailType.trend;
    trendDetail = trendDetail;
  }
  SwipeDetailObject.empty() {
    detailType = DetailType.project;
  }
}
 */