package pl.michaelslabikovsky.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONConverter {

    public static JSONArray convertStringObjectToJSONArray(String str) {
        JSONObject jsonObject = new JSONObject(str);
        return jsonObject.getJSONArray("list");
    }

}
