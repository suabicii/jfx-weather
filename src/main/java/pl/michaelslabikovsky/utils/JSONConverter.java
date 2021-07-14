package pl.michaelslabikovsky.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONConverter {

    public static JSONArray convertStringObjectToJSONArrayWithWeatherData(String str) {
        JSONObject jsonObject = new JSONObject(str);
        return jsonObject.getJSONArray("list");
    }

    public static JSONArray convertStringObjectToJSONArray(String str) {
        return new JSONArray(str);
    }
}
