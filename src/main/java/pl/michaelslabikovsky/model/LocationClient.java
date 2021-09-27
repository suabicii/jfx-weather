package pl.michaelslabikovsky.model;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.michaelslabikovsky.utils.JSONConverter;

import java.io.IOException;

public class LocationClient extends Location {

    private JSONArray resultsJSONArray;

    public LocationClient(String mainApiPart, String additionalApiPart) {
        super(mainApiPart, additionalApiPart);
    }

    public void loadLocationData(String searchFieldValue) throws IOException {
        connectToApi(searchFieldValue, getMainAPIPart(), getAdditionalAPIPart());
        String result = getResult();
        resultsJSONArray = JSONConverter.convertStringToJSONArray(result);
    }

    public int getLocationDataAmount() {
        return resultsJSONArray.length();
    }

    public String getFoundLocation(int arrayIndex, String searchFieldValue) {
        String finalResult = "";

        if (resultsJSONArray.length() > 0) {
            JSONObject resultPart = resultsJSONArray.getJSONObject(arrayIndex);
            if (resultPart.has("local_names")) {
                if (resultPart.getJSONObject("local_names").has("pl")) {
                    String polishLocalName = resultPart.getJSONObject("local_names").getString("pl");
                    finalResult += polishLocalName.contains("Województwo") ? searchFieldValue + ", " + polishLocalName : polishLocalName;
                } else {
                    finalResult += resultPart.getJSONObject("local_names").getString("feature_name");
                }
            } else {
                finalResult += resultPart.getString("name");
            }

            finalResult += ", " + resultPart.getString("country");

            if (resultPart.has("state")) {
                finalResult += ", " + resultPart.getString("state");
            }
        }

        return finalResult;
    }
}
