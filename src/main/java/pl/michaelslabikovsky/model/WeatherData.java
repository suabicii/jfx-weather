package pl.michaelslabikovsky.model;

public class WeatherData extends ApiData {

    private final String mainApiPart;
    private final String additionalApiPart;

    /*
    * API parts:
    *   Main: "https://api.openweathermap.org/data/2.5/forecast?q=";
    *   Additional:"&lang=pl&units=metric&appid=";
    * */

    public WeatherData(String mainApiPart, String additionalApiPart) {
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
