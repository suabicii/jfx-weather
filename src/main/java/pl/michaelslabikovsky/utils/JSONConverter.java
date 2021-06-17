package pl.michaelslabikovsky.utils;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONConverter {

    public static JSONArray convertStringObjectToJSONArray(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(str);
    }

}
