import 'package:frontend/core/enums/alignment.dart';
import 'package:frontend/core/enums/product_readiness.dart';
import 'package:frontend/core/enums/readiness.dart';
import 'package:frontend/core/enums/team_size.dart';
import 'package:frontend/core/enums/usefullness.dart';
import 'package:frontend/core/enums/workload.dart';
import 'package:frontend/core/enums/target_market.dart';


class EnumUtil {
  static String StringToEnumStringIndustry(String? industry) {
    switch (industry) {
      // case "Branche auswählen...":
      //   return "NOTSET";
      case "Software und Computerdienstleistungen":
        return "SOFTWARE";
      case "Technologie Hardware und Ausrüstung":
        return "HARDWARE";
      case "Telekommunikationseinrichtungen":
        return "TELECOMMUNICATIONEQUIPMENT";
      case "Anbieter von Telekommunikationsdiensten":
        return "TELECOMMUNICATION";
      case "Gesundheitsdienstleister":
        return "HEALTHCAREPROVIDER";
      case "Medizinische Geräte und Dienstleistungen":
        return "MEDICALEQUIPMENT";
      case "Pharmazeutika und Biotechnologie":
        return "PHARMA";
      case "Bank":
        return "BANK";
      case "Finanz- und Kreditdienstleistungen":
        return "FINANCE";
      case "Investmentbanking und Brokerage-Dienstleistungen":
        return "INVESTMENT";
      case "Hypotheken-Immobilien-Investmentfonds":
        return "MORTGAGE";
      case "Geschlossene Beteiligungen":
        return "CLOSEDENDINVESTMENT";
      case "Offene und sonstige Anlageformen":
        return "OPENENDINVESTMENT";  
      case "Lebensversicherung":
        return "LIFEINSURANCE";
      case "Versicherungen":
        return "INSURANCE";
      case "Immobilieninvestitionen und Dienstleistungen":
        return "REALESTATESERVICE";
      case "Immobilien-Investmentfonds":
        return "REALESTATEINVESTMENT";
      case "Automobile und Teile":
        return "AUTOMOBILE";
      case "Consumer Services":
        return "CONSUMERSERVICE";
      case "Haushaltswaren und Wohnungsbau":
        return "HOUSEHOLDGOODS";
      case "Freizeitgüter":
        return "LEISUREGOODS";
      case "Persönliche Güter":
        return "PERSONALGOODS";
      case "Medien":
        return "MEDIA";
      case "Wiederverkäufer":
        return "RETAIL";
      case "Reisen und Freizeit":
        return "TRAVEL";
      case "Getränke":
        return "BEVERAGE";
      case "Lebensmittelproduzent":
        return "FOOD";
      case "Tabacco":
        return "TABACCO";
      case "Körperpflege-, Drogerie- und Lebensmittelläden":
        return "PERSONALCARE";
      case "Konstruktion und Materialien":
        return "CONSTRUCTION";
      case "Luft- und Raumfahrt und Verteidigung":
        return "AEROSPACE";
      case "Elektronische und elektrische Geräte":
        return "ELECTRONIC";
      case "Allgemeine Industriezweige":
        return "GENERALINDUSTRY";
      case "Wirtschaftsingenieurwesen":
        return "ENGINEERING";
      case "Industrielle Unterstützungsdienste":
        return "INDUSTRALSUPPORT";
      case "Industrieller Transport":
        return "INDUSTRIALTRANSPORT";
      case "Industrielle Materialien":
        return "INDUSTRIALMATERIALS";
      case "Industriemetalle und Bergbau":
        return "INDUSTRIALMINING";
      case "Chemikalien":
        return "CHEMICALS";
      case "Öl, Gas und Kohle":
        return "OILGASCOAL";
      case "Alternative Energie":
        return "ALTERNATEENERGY";
      case "Elektrizität":
        return "ELECTRICITY";
      case "Gas, Wasser und Multi-Utilities":
        return "GASWATER";
        case "Abfall- und Entsorgungsdienstleistungen":
        return "WASTE";
      default:
        throw Exception("Industry String is null!");
    }
  }

  static String alignmentToString(AlignmentBusiness alignmentBusiness) {
    switch (alignmentBusiness) {
      case AlignmentBusiness.B2B:
        return 'Business-to-Business';
      case AlignmentBusiness.B2C:
        return 'Business-to-Consumer';
    }
  }

  static String targetMarketToString(TargetMarket targetMarket) {
    switch (targetMarket) {
      case TargetMarket.EU:
        return 'Europa';
      case TargetMarket.US:
        return 'Amerika';
    }
  }

  static String teamSizeToString(TeamSize teamSize) {
    switch (teamSize) {
      case TeamSize.TINY:
        return "Sehr kleines Unternehmen";
      case TeamSize.SMALL:
        return "Kleines Unternehmen";
      case TeamSize.MEDIUM:
        return "Normalgroßes Unternehmen";
      case TeamSize.LARGE:
        return "Sehr großes Unternehmen";
    }
  }

  static String readinessToString(Readiness readiness) {
    switch (readiness) {
      case Readiness.THEORY:
        return "Only a Theory at the Moment";
      case Readiness.DEVELOPMENT:
        return "Currently In Development";
      case Readiness.READY:
        return "Ready for Commercial Use";
      case Readiness.COMMERCIAL:
        return "In Commercial Use Currently";
      case Readiness.DATED:
        return "Technology is Dated";
    }
  }

  static String productReadinessToString(ProductReadiness readiness) {
    switch (readiness) {
      case ProductReadiness.THEORY:
        return "Only a Theory at the Moment";
      case ProductReadiness.DEVELOPMENT:
        return "Currently In Development";
      case ProductReadiness.READY:
        return "Ready for Commercial Use";
      case ProductReadiness.COMMERCIAL:
        return "In Commercial Use Currently";
      case ProductReadiness.DATED:
        return "Technology is Dated";
    }
  }

  static String usefullnessToString(Usefullness usefullness) {
    switch (usefullness) {
      case Usefullness.HIGH:
        return "Very high in usefullness";
      case Usefullness.LOW:
        return "Only partially usefull";
      case Usefullness.NONE:
        return "Not Usefull at all";
      case Usefullness.REVOLUTIONARY:
        return "Revolutionary";
    }
  }

  static String workloadToString(Workload workload) {
    switch (workload) {
      case Workload.BACKLOGGED:
        return "Already on backlogged";
      case Workload.FULL:
        return "Not taking any more projcts currently";
      case Workload.TAKING_PROJECTS:
        return "Still taking new projects";
    }
  }
}
