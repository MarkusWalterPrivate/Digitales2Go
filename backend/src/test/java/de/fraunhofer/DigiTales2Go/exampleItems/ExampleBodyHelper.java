package de.fraunhofer.DigiTales2Go.exampleItems;

import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExampleBodyHelper {

    private final static HashMap<String, String> companyCoreFieldValues = new HashMap<>();
    private final static HashMap<String, String> companyRequiredValues = new HashMap<>();
    private final static HashMap<String, String> companyTeamSizeValues = new HashMap<>();

    private final static HashMap<String, String> companyProjectsValues = new HashMap<>();
    private final static HashMap<String, String> companyOptionalListsValues = new HashMap<>();
    private final static HashMap<String, String> companyOptionalFieldsValues = new HashMap<>();
    private static String fullBody;

    @BeforeAll
    private static void init() {

        companyCoreFieldValues.put("intern", "true");
        companyCoreFieldValues.put("source", "source");
        companyCoreFieldValues.put("imageSource", "imageSource");
        companyCoreFieldValues.put("headline", "Demo headline");
        companyCoreFieldValues.put("teaser", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr.");
        companyCoreFieldValues.put("industry", "AEROSPACE");
        companyCoreFieldValues.put("tag1", "Tag1");
        companyCoreFieldValues.put("tag2", "Tag2");
        companyCoreFieldValues.put("type", "COMPANY");

        companyRequiredValues.put("description", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore");
        companyRequiredValues.put("useCases", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        companyRequiredValues.put("website", "tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo");
        companyTeamSizeValues.put("teamSize", "TINY");
        companyTeamSizeValues.put("reference", "tempor invidunt utlabore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo");

        companyProjectsValues.put("projects", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr");
        companyProjectsValues.put("internalProjects", null);
        companyProjectsValues.put("externalProjects", null);

        companyOptionalFieldsValues.put("locationCountry", "Germany");
        companyOptionalFieldsValues.put("locationCity", "Stuttgart");

        companyOptionalFieldsValues.put("productReadiness", "THEORY");

        companyOptionalFieldsValues.put("numberOfCustomersReference", "invidunt utlabore et dolore magna aliquyam erat, sed diam");

        companyOptionalFieldsValues.put("revenueReference", "invidunt utlabore et dolore magna aliquyam erat, sed diam");

        companyOptionalFieldsValues.put("profitReference", "invidunt utlabore et dolore magna aliquyam erat, sed diam");

        companyOptionalListsValues.put("contactsName", "Hans");
        companyOptionalListsValues.put("contactsEmail", "hans@Wurst.der");
        companyOptionalListsValues.put("contactsTelephone", "+(049)123 45678789");
        companyOptionalListsValues.put("contactsOrganisation", "Metzgerei Hans");

        companyOptionalListsValues.put("targetMarkets1", "\"GERMANY\"");
        companyOptionalListsValues.put("targetMarkets2", "\"USA\"");

        companyOptionalListsValues.put("alignment1", "\"B2B\"");
        companyOptionalListsValues.put("alignment2", "\"B2G\"");

        companyOptionalListsValues.put("partner1", "\"Metzgerei Hans\"");
        companyOptionalListsValues.put("partner2", "\"Metzgerei Horst\"");

        companyOptionalListsValues.put("investor1", "\"Metzgerei Hans\"");
        companyOptionalListsValues.put("investor2", "\"Metzgerei Horst\"");


        String allOptionals =
                "   \"companyOptional\": {" +
                        "        \"companyProjects\": {" +
                        "           \"projects\": \"" + companyProjectsValues.get("projects") + "\"," +
                        "           \"internalProjects\": " + companyProjectsValues.get("internalProjects") + "," +
                        "           \"externalProjects\": " + companyProjectsValues.get("externalProjects") + "" +
                        "        }," +
                        "        \"companyOptionalLists\": {" +
                        "            \"contacts\": [" +
                        "                {" +
                        "                   \"name\": \"" + companyOptionalListsValues.get("contactsName") + "\"," +
                        "                   \"email\": \"" + companyOptionalListsValues.get("contactsEmail") + "\"," +
                        "                   \"telephone\": \"" + companyOptionalListsValues.get("contactsTelephone") + "\"," +
                        "                   \"organisation\": \"" + companyOptionalListsValues.get("contactsOrganisation") +"\"" +
                        "               }" +
                        "           ]," +
                        "            \"targetMarkets\": [" +
                        "                "+ companyOptionalListsValues.get("targetMarkets1")+","
                        +companyOptionalListsValues.get("targetMarkets2")+
                        "           ]," +
                        "            \"alignments\": [" +
                        "                "+ companyOptionalListsValues.get("alignment1")+","
                        +companyOptionalListsValues.get("alignment2")+
                        "           ]," +
                        "            \"partners\": [" +
                        "                "+ companyOptionalListsValues.get("partner1")+","
                        +companyOptionalListsValues.get("partner2")+
                        "           ]," +
                        "            \"investors\": [" +
                        "                "+ companyOptionalListsValues.get("investor1")+","
                        +companyOptionalListsValues.get("investor2")+
                        "           ]" +
                        "        }," +
                        "        \"companyOptionalFields\": {" +
                        "            \"location\": {"+
                        "                   \"country\": \"" + companyOptionalFieldsValues.get("locationCountry") + "\"," +
                        "                   \"city\": \"" + companyOptionalFieldsValues.get("locationCity") + "\"" +
                        "               }," +
                        "            \"productReadiness\": \"" + companyOptionalFieldsValues.get("productReadiness") + "\"," +
                        "            \"numberOfCustomers\": {" +
                        "                 \"value\": \"42\"," +
                        "                 \"year\": 2020," +
                        "                 \"reference\": \"" + companyOptionalFieldsValues.get("numberOfCustomersReference") + "\"" +
                        "               }," +
                        "            \"revenue\": {" +
                        "                 \"value\": \"42\"," +
                        "                 \"year\": 2020," +
                        "                 \"reference\": \"" + companyOptionalFieldsValues.get("revenueReference") + "\"" +
                        "               }," +
                        "            \"profit\": {" +
                        "                 \"value\": \"42\"," +
                        "                 \"year\": 2020," +
                        "                 \"reference\": \"" + companyOptionalFieldsValues.get("profitReference") + "\"" +
                        "               }" +
                        "        }" +

                        "   }";

        fullBody = buildBody(allOptionals);
    }

    private static String buildBody(String optionalValues){
        return "{" +
                "   \"coreField\": {" +
                "       \"intern\": " + companyCoreFieldValues.get("intern") + "," +
                "       \"source\": \"" + companyCoreFieldValues.get("source") + "\"," +
                "       \"imageSource\": [\"" + companyCoreFieldValues.get("imageSource") + "\"]," +
                "       \"headline\": \"" + companyCoreFieldValues.get("headline") + "\"," +
                "       \"teaser\": \"" + companyCoreFieldValues.get("teaser") + "\"," +
                "       \"industry\": \"" + companyCoreFieldValues.get("industry") + "\"," +
                "       \"tags\": [" +
                "           \"" + companyCoreFieldValues.get("tag1") + "\"," +
                "           \"" + companyCoreFieldValues.get("tag2") + "\"" +
                "       ]," +
                "       \"type\": \"" + companyCoreFieldValues.get("type") + "\"" +
                "   }," +
                "   \"detailedRating\":{"+
                "       \"degreeOfInnovation\": 1,"+
                "       \"disruptionPotential\": 1,"+
                "       \"useCases\": 1"+
                "   }," +
                "   \"companyRequired\": {" +
                "       \"description\": \"" + companyRequiredValues.get("description") + "\"," +
                "       \"useCases\": \"" + companyRequiredValues.get("useCases") + "\"," +
                "       \"teamSize\": {" +
                "           \"teamSize\": \"" + companyTeamSizeValues.get("teamSize") + "\"," +
                "           \"year\": 2020," +
                "           \"reference\": \"" + companyTeamSizeValues.get("reference") + "\"" +
                "       }," +
                "       \"website\": \"" + companyRequiredValues.get("website") + "\"," +
                "       \"foundationYear\": 2000" +
                "   }," +
                optionalValues+"}";

    }

    public static List<String> getFullBodies() {
        init();
        List<String> bodyList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if(i == 1) {
                fullBody = fullBody.replace("AEROSPACE", "GENERALINDUSTRY");
            } else if(i == 2) {
                fullBody = fullBody.replace("GENERALINDUSTRY", "CHEMICALS");
            } else if(i == 3) {
                fullBody = fullBody.replace("CHEMICALS", "OILGASCOAL");
            } else if(i == 4) {
                fullBody = fullBody.replace("OILGASCOAL", "ALTERNATEENERGY");
            } else if(i == 5) {
                fullBody = fullBody.replace("ALTERNATEENERGY", "ELECTRICITY");
            } else if(i == 6) {
                fullBody = fullBody.replace("ELECTRICITY", "ENGINEERING");
            } else if(i == 7) {
                fullBody = fullBody.replace("ENGINEERING", "ELECTRONIC");
            } else if(i == 8) {
                fullBody = fullBody.replace("ELECTRONIC", "HARDWARE");
            }
            bodyList.add(fullBody);
        }
        return bodyList;
    }

}
