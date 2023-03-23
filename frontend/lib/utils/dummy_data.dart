import 'package:frontend/core/atomic_objects/ReferencedValue.dart';
import 'package:frontend/core/atomic_objects/company_optional_lists.dart';
import 'package:frontend/core/atomic_objects/company_optional_fields.dart';
import 'package:frontend/core/atomic_objects/event.dart';
import 'package:frontend/core/atomic_objects/location.dart';
import 'package:frontend/core/atomic_objects/size.dart';
import 'package:frontend/core/detail_objects/company/company_required.dart';
import 'package:frontend/core/detail_objects/technology/tech_optional.dart';
import 'package:frontend/core/detail_objects/technology/tech_required.dart';
import 'package:frontend/core/element_objects/detailed_rating.dart';
import 'package:frontend/core/detail_objects/technology/technology_detail_obj.dart';
import 'package:frontend/core/enums/industry.dart';
import 'package:frontend/core/swipe_objects/swipe_detail_object.dart';
import 'package:frontend/widgets/detail/company_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/project_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/technology_detail_widget_swipe.dart';
import 'package:frontend/widgets/detail/trend_detail_widget_swipe.dart';
import 'package:frontend/widgets/feed/feed.dart';
import 'package:frontend/widgets/feed/feed_item.dart';
import 'package:frontend/widgets/feed/feed_row.dart';
import 'package:frontend/core/detail_objects/company/company_optional.dart';
import '../core/atomic_objects/contact.dart';
import '../core/atomic_objects/projectRelations.dart';
import '../core/atomic_objects/runtime.dart';
import '../core/detail_objects/company/company_detail_obj.dart';
import '../core/detail_objects/project/project_detail_obj.dart';
import '../core/detail_objects/project/project_optional.dart';
import '../core/detail_objects/project/project_required.dart';
import '../core/detail_objects/trend/trend_detail_obj.dart';
import '../core/detail_objects/trend/trend_optional.dart';
import '../core/detail_objects/trend/trend_required.dart';
import '../core/element_objects/coreField.dart';
import '../widgets/swipe/swipecards/swipe_cards.dart';

class DummyData {
  //This Method needs to be redone with updates
  static FeedItem getFeedItem() {
    CoreField coreField = CoreField(
        id: 2,
        intern: true,
        source: "interne Quelle aka Harriet",
        lastUpdated: 1651356000000,
        creationDate: 1654440936139,
        rating: 5.0,
        imageSource:
            "https://www.wirtschaftswissen.de/fileadmin/_processed_/4/3/csm_Projektmanagement_Kaffee_Fotolia_74388703_Subscription_Monthly_M_38fdf0e531.jpg",
        headline: "Projekt DigiTales2Go",
        teaser:
            "Fraunhofer IAO: Die StuProfis arbeiten and er wohl wahrscheinlich besten App, die jemals geschrieben wurde",
        industry: 'Software and Computer Services',
        tags: [
          "Krebs",
          "Robotic",
          "Innovation",
          "Robotic2",
          "Robotic3",
          "Robotic4"
        ],
        type: 'Project');
    return FeedItem(
        coreField: coreField,
        creationDate: 0,
        id: 23213124131,
        industry: "Aerospace",
        ratingCount: 0,
        isBookmarked: false,
        rated: false,
        userToken: "",
        rating: 0.0);
  }

  static List<SwipeItem> getSwipeItems() {
    List<SwipeItem> swipeItems = [];
    for (int i = 0; i < 5; i++) {
      SwipeItem swipeItem = SwipeItem(
        feedItem: DummyData.getFeedItem(),
        content: getProject(),
        likeAction: () {
          print("like");
        },
        nopeAction: () {
          print("dislike");
        },
        skipAction: () {
          print("skip");
        },
        superlikeAction: () {
          print("superlike");
        },
      );
      swipeItems.add(swipeItem);
    }
    return swipeItems;
  }

/*   static List<SwipeItem> getSwipeItemsGeneral() {
    List<SwipeDetailObject> detailObjects = DummyData.getSwipeObjects();
    List<SwipeItem> swipeItems = [];
    for (int i = 0; i < 5; i++) {
      SwipeItem swipeItem = SwipeItem(
        detailObject: detailObjects[i],
        detailType: detailObjects[i].detailType,
        likeAction: () {
          print("like");
        },
        nopeAction: () {
          print("dislike");
        },
        skipAction: () {
          print("skip");
        },
        superlikeAction: () {
          print("superlike");
        },
      );
      swipeItems.add(swipeItem);
    }
    return swipeItems;
  } */
/* 
  static List<SwipeDetailObject> getSwipeObjects() {
    MatchEngine matchEngine = DummyData.getMatchEngine();

    SwipeDetailObject obj1 = SwipeDetailObject.company(DummyData.getCompany());
    SwipeDetailObject obj2 = SwipeDetailObject.project(DummyData.getProject());
    SwipeDetailObject obj3 = SwipeDetailObject.trend(DummyData.getTrend());
    SwipeDetailObject obj4 =
        SwipeDetailObject.technology(DummyData.getTechnology());

    return [obj1, obj2, obj3, obj4, obj1];
  } */

  static MatchEngine getMatchEngine() {
    MatchEngine matchEngine;
    matchEngine = MatchEngine(swipeItems: DummyData.getSwipeItems());
    return matchEngine;
  }

  static ProjectDetail getProject() {
    CoreField coreField = CoreField(
        id: 2,
        intern: true,
        source: "interne Quelle aka Harriet",
        lastUpdated: 1651356000000,
        creationDate: 1654440936139,
        rating: 5.0,
        imageSource:
            "https://www.wirtschaftswissen.de/fileadmin/_processed_/4/3/csm_Projektmanagement_Kaffee_Fotolia_74388703_Subscription_Monthly_M_38fdf0e531.jpg",
        headline: "Projekt DigiTales2Go",
        teaser:
            "Fraunhofer IAO: Die StuProfis arbeiten and er wohl wahrscheinlich besten App, die jemals geschrieben wurde",
        industry: 'Software and Computer Services',
        tags: [
          "Krebs",
          "Robotic",
          "Innovation",
          "Robotic2",
          "Robotic3",
          "Robotic4"
        ],
        type: 'Project');

    Contact contact1 = Contact(
        id: 1,
        name: "Walter van der Seen",
        email: "WalterS@web.de",
        telephone: "52321123",
        organisation: "Fraunhofer IAO");
    Contact contact2 = Contact(
        id: 2,
        name: "Alina Beckroder",
        email: "123diealina@web.de",
        telephone: "2313123",
        organisation: "Universität Stuttgart VISUS");

    ProjectRequired projectRequired = ProjectRequired(
        id: 1,
        description:
            "Die Wissenschaftler vom Davis Cancer Center, einer Abteilung der Universität in Kalifornien, verzeichnen im Bereich der Krebsforschung und Bekämpfung weiterhin große Fortschritte. Einem aktuellen Bericht der Fachzeitschrift Nature zufolge, können Tumore in naher Zukunft noch effektiver analysiert und zielgerichteter bekämpft werden. Die Behandlung mit Nanobots soll schon bald eine Alternative zur Chemotherapie darstellen und diese vielleicht auch bereits in den kommenden Jahren komplett ersetzen.",
        readiness: 'Developement',
        runtime: Runtime(id: 1, start: 1651356000000, finish: 1654440936139, useOnlyYear: false),
        contacts: [contact1, contact2]);

    DetailedRating detailedRatingProj = DetailedRating(
        id: 1, degreeOfInnovation: 2, disruptionPotential: 4, usecases: 5);
    ProjectOptional projectOptional = ProjectOptional(
        id: 1,
        externalItems: [],
        publications: [
          "Da gabs mal so nen Buch",
          "war ganz spannend, das Buch"
        ],
        projectRelations: ProjectRelations(
            id: 1,
            fundingSources: ["Red Bull"],
            promoters: ["unge Placement", "MaiLab"],
            projectPartners: ["Universität Stuttgart", "FH Konstanz"],
            usePartners: ["Nestle"]),
        financing: ReferencedValue(
            id: 1, reference: 'link', value: '51351', year: 2020),
        website: "www.super-tolle.website");
    List<Event> events = [
      Event(
          id: 1,
          date: 781999,
          location: "Stuttgart 70173 / Königstraße",
          description:
              "Fette Hausparty für Promotion und so. Jeder kann kommen, Eskalation, X-Factor like. Beginn sooo 20 uhr, open end versteht sich.",
          imageSource:
              "https://www.d-live.de/fileadmin/user_upload/mitsubishi-electric-halle/bilder/firmen-events/mitsubishi-electric-halle-firmen-event-09.jpg",
          website: "campus.uni-stuttgart.de")
    ];

    return ProjectDetail(
        id: 213213,
        coreField: coreField,
        detailedRating: detailedRatingProj,
        projectRequired: projectRequired,
        projectOptional: projectOptional,
        events: events,
        comments: [],
        internalCompanies: [],
        internalProjects: [],
        internalTechnologies: [],
        internalTrends: []);
  }
}
