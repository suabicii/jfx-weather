package pl.michaelslabikovsky.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONConverter {

    public static JSONObject convertStringToJSONObject(String str) {
        return new JSONObject(str);
    }

    public static JSONArray convertStringToJSONArray(String str) {
        return new JSONArray(str);
    }
}
