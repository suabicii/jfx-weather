package pl.michaelslabikovsky.model;

public class Location extends ApiData {

    private final String mainApiPart;
    private final String additionalApiPart;

    /*
     * API parts (production code):
     *   Main: "https://api.openweathermap.org/geo/1.0/direct?q=";
     *   Additional: "&limit=5&appid= + API_KEY";
     * */

    public Location(String mainApiPart, String additionalApiPart) {
        this.mainApiPart = mainApiPart;
        this.additionalApiPart = additionalApiPart;
    }

    @Override
    protected String getMainAPIPart() {
        return mainApiPart;
    }

    @Override
    protected String getAdditionalAPIPart() {
        return additionalApiPart;
    }
}
